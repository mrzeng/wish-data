package com.raycloud.overseas.erp.data.services.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.commom.domain.wish.domain.Shop;
import com.raycloud.overseas.data.api.dubbo.service.IShopService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.FocusMarkMapDAO;
import com.raycloud.overseas.erp.data.common.dao.FocusMerchantDAO;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.FocusMerchantQuery;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.core.WishMerchantSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.services.api.ILocalIndustryService;
import com.raycloud.overseas.erp.data.services.api.ILocalMerchantService;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.services.util.MappingUtil;
import com.raycloud.overseas.erp.data.vo.MerchantChartVo;
import com.raycloud.overseas.erp.data.vo.MerchantEvent;
import com.raycloud.overseas.erp.data.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhanxf on 16/7/17.
 */
@Service
public class LocalMerchantServiceImpl implements ILocalMerchantService {

    private static Logger logger= Logger.getLogger(LocalMerchantServiceImpl.class);

    @Autowired
    private FocusMerchantDAO focusMerchantDAO;

    @Autowired
    private WishCategoryService wishCategoryService;

    @Autowired
    private IShopService mjShopService;

    @Autowired
    private WishMerchantSolrService wishMerchantSolrService;

    @Autowired
    private ILocalIndustryService localIndustryService;

    @Autowired
    private WishItemSolrService wishItemSolrService;

    @Autowired
    private FocusMarkMapDAO focusMarkMapDAO;

    @Override
    public List<FocusMerchant> getFocusMerchantList(FocusMerchantQuery focusMerchantQuery) {
        return focusMerchantDAO.getFocusMerchantList(focusMerchantQuery);
    }

    @Override
    public FocusMerchant getFocusMerchantByKey(UserData userData, String merchantId       ) {
        return focusMerchantDAO.getFocusMerchantByKey(userData.getFounderId(),userData.getUserId()+"", merchantId);
    }

    /**
     * 关注宝贝列表
     *
     * @param userData
     * @param ids
     * @param focus
     * @return
     */
    @Override
    public Response focusMerchants(UserData userData, List<String> ids,Integer focus){
        Response response = new Response();

        for(String id : ids){
            try {
                focusMerchant(userData,id,focus);
            } catch (BizException e) {
                logger.error(MessageFormat.format("关注失败,店铺id:{0},用户id:{1},focus状态:{}",id,userData.getUserId(),focus),e);
            }
        }
        return response;
    }

    @Override
    public Result getFocusMerchantList(FocusMerchantListRequest request) throws BizException {
        if(!StringUtils.isEmpty(request.getSearchKey())){
            request.setSearchKey(request.getSearchKey().trim());
            if(DataUtil.judgeIsId(request.getSearchKey())){
                request.setMerchantId(request.getSearchKey());
            }else{
                request.setMerchantName(request.getSearchKey());
            }
        }

        FocusMerchantQuery focusMerchantQuery = new FocusMerchantQuery();
        focusMerchantQuery.setFocus(request.getFocus());
        focusMerchantQuery.setUserId(request.getUserData().getUserId()+"");
        focusMerchantQuery.setFounderId(request.getUserData().getFounderId());
        focusMerchantQuery.setMerchantId(request.getMerchantId());
        focusMerchantQuery.setMerchantName(request.getMerchantName());
        focusMerchantQuery.setUpdatedStart(DateUtil.getDate(request.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
        focusMerchantQuery.setUpdatedEnd(DateUtil.getDate(request.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
        focusMerchantQuery.setPageNo(request.getPageNo());
        focusMerchantQuery.setPageSize(request.getPageSize());
        focusMerchantQuery.setMarkIdList(ListUtil.strToList(request.getMarkIds(),","));
        focusMerchantQuery.orderbyUpdated(false);
        Result result = focusMerchantDAO.getFocusMerchantListWithPage(focusMerchantQuery);
        List<FocusMerchant> focusMerchantList = result.getItems();

        if(ListUtil.isBlank(focusMerchantList)){
            return result;
        }

        List<String> focusMerchantIdList = new ArrayList<String>();
        for(FocusMerchant focusMerchant : focusMerchantList){
            focusMerchantIdList.add(focusMerchant.getMerchantId());
        }

        WishMerchantSolrQuery wishMerchantSolrQuery = new WishMerchantSolrQuery();
        wishMerchantSolrQuery.setMerchantIdCollection(focusMerchantIdList);
        wishMerchantSolrQuery.setStart(0);
        wishMerchantSolrQuery.setRows(focusMerchantIdList.size());
        List<MerchantDomain> solrMerchantList = wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery).getItems();
        if(!ListUtil.isBlank(solrMerchantList)){
            Map<String,MerchantDomain> merchantDomainMap = new HashMap<String, MerchantDomain>();
            Map<String,List<Integer>> markMap = focusMarkMapDAO.getMarkIdMap(request.getUserData().getUserId(),request.getUserData().getFounderId(), DataConstant.MARK_MERCHANT);
            for(MerchantDomain merchantDomain : solrMerchantList){
                merchantDomain.setMarkIds(markMap.get(merchantDomain.getMerchantId()));
                merchantDomainMap.put(merchantDomain.getMerchantId(),merchantDomain);
            }
            List<MerchantDomain> showList = new ArrayList<MerchantDomain>();
            for(String merchantId : focusMerchantIdList){
                if(merchantDomainMap.containsKey(merchantId)){
                    showList.add(merchantDomainMap.get(merchantId));
                }
            }
            result.setItems(showList);
        }
        return result;
    }

    @Override
    public void focusMerchant(UserData userData,String merchantId,Integer focus) throws BizException {

            FocusMerchantQuery focusMerchantQuery = new FocusMerchantQuery();
            focusMerchantQuery.setMerchantId(merchantId);
            focusMerchantQuery.setUserId(userData.getUserId()+"");
            focusMerchantQuery.setFounderId(userData.getFounderId());
            FocusMerchant focusMerchant = focusMerchantDAO.getFocusMerchantByKey(userData.getFounderId(),userData.getUserId()+"",merchantId);

            if(focusMerchant == null){

                Shop shop = mjShopService.queryShopInfoById(merchantId);

                if(shop == null){
                    logger.biz("店铺被删除,店铺id:{},用户id:{}",merchantId,userData.getUserId());
                    throw new BizException("暂未查询到该店铺信息,请联系技术支持");
                }

                focusMerchant = new FocusMerchant();
                focusMerchant.setCreated(new Date());
                focusMerchant.setMerchantId(merchantId);
                focusMerchant.setUpdated(new Date());
                focusMerchant.setUserId(userData.getUserId()+"");
                focusMerchant.setFounderId(userData.getFounderId());
                focusMerchant.setFocus(focus);
                focusMerchant.setMerchantName(shop.getMerchantNick());
                focusMerchantDAO.addFocusMerchant(focusMerchant);
            }else{
                focusMerchant.setFocus(focus);
                focusMerchant.setUpdated(new Date());
                focusMerchantDAO.updateFocusMerchantByKey(focusMerchant);
            }
    }

    /**
     * 获取店铺详情
     *
     * @param request
     * @return
     */
    @Override
    public MerchantDomain getMerchantDetailVo(MerchantDetailRequest request){
        Shop shop = null;
        Long startL = System.currentTimeMillis();
        try{
            shop = mjShopService.handleShopDetailInfoById(request.getMerchantId());
        }catch (Exception e){
            logger.error("获取店铺详情异常",e);
            return null;
        }

        if(shop == null){
            return null;
        }

        MerchantDomain merchantDetailVo = MappingUtil.mappingMerchantDomain(shop);

        FocusMerchant focusMerchant = focusMerchantDAO.getFocusMerchantByKey(request.getUserData().getFounderId(),request.getUserData().getUserId()+"",request.getMerchantId());

        merchantDetailVo.setFocus(focusMerchant == null?0:focusMerchant.getFocus());

        logger.biz("获取店铺详情数据,用户id:{},店铺id:{},耗时{}ms",request.getUserData().getUserId(), request.getMerchantId(),(System.currentTimeMillis()-startL));

        return merchantDetailVo;
    }

    /**
     * 获取店铺经营数据echart图表
     *
     * @param request
     * @return
     */
    @Override
    public MerchantChartVo getMerchantChartVo(MerchantChartRequest request){
        MerchantChartVo merchantChartVo = new MerchantChartVo();
        List<Long> dateList = DateUtil.getDateList(request.getStartTime(),request.getEndTime());

            long start = System.currentTimeMillis();
            List<Shop> shopList = mjShopService.queryShopOfferListById(request.getMerchantId(),request.getStartTime(),request.getEndTime());

            Map<Long,Shop> dayShopMap = Maps.newHashMap();
            if(!ListUtil.isBlank(shopList)){
                for(Shop shop : shopList){
                    dayShopMap.put(shop.getInsertDate().getTime(),shop);
                }
            }

            for(int i = 0;i<dateList.size();i++){

                Object[] saleCountObjs = new Object[2];
                Object[] saveObjs = new Object[2];
                Object[] amountObjs = new Object[2];
                Object[] rateObjs = new Object[2];

                saleCountObjs[0] = dateList.get(i);
                saveObjs[0] = dateList.get(i);
                amountObjs[0] = dateList.get(i);
                rateObjs[0] = dateList.get(i);

                //时间选的跨度超过可采集的数据时
                if(!dayShopMap.containsKey(dateList.get(i))){
                    saleCountObjs[1] = 0;
                    saveObjs[1] = 0;
                    amountObjs[1] = 0;
                    rateObjs[1] = 0;
                }else{
                    Shop shop = dayShopMap.get(dateList.get(i));
                    saleCountObjs[1] = shop.getAmount();
                    saveObjs[1] = shop.getWishSave();
                    amountObjs[1] = shop.getPrice();
                    rateObjs[1] = shop.getRateNum();
                }

                merchantChartVo.getSaleCount().add(saleCountObjs);
                merchantChartVo.getAmount().add(amountObjs);
                merchantChartVo.getRateNum().add(rateObjs);
                merchantChartVo.getSave().add(saveObjs);
            }

            logger.biz("获取店铺经营数据,店铺id:{},耗时{}ms", request.getMerchantId(),(System.currentTimeMillis()-start));

        return merchantChartVo;
    }

    /**
     * 获取产品行业分析数据图表
     *
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> getCatChart(MerchantCatChartRequest request){

        Map<String,Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();

            Shop _shop = mjShopService.queryShopInfoById(request.getMerchantId());
            //获取店铺类目
           String catIds = _shop.getCatIds();
            List<String> catIdList = ListUtil.strToList(catIds,";");

            List<Long> dateList = DateUtil.getDateList(request.getStartTime(),request.getEndTime());
            List<String> times = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(Long date:dateList){
                times.add(sdf.format(date));
            }

            map.put("date",times);
            map.put("data",dataList);
            if(ListUtil.isBlank(catIdList)){
                return map;
            }

            WishCategoryQuery wishCategoryQuery = new WishCategoryQuery();
            wishCategoryQuery.setCatIdList(catIdList);
            List<WishCategory> wishCategoryList = wishCategoryService.getWishCategoryList(wishCategoryQuery);

            Map<Long,Shop> shopMap = new HashMap<Long, Shop>();
            for(WishCategory cat : wishCategoryList){
                List<Shop> shopList = mjShopService.queryShopSingleCatInfoByMerchantId(request.getMerchantId(),cat.getCatId(),request.getStartTime(),request.getEndTime());
                if(!ListUtil.isBlank(shopList)){
                    for(Shop shop : shopList){
                        shopMap.put(shop.getInsertDate().getTime(),shop);
                    }
                }
                Map<String,Object> catDataMap = new HashMap<String, Object>();
                List<Object> catDataList = new ArrayList<Object>();
                catDataMap.put("name",cat.getChineseName());
                catDataMap.put("englishName",cat.getEnglishName());
                catDataMap.put("data",catDataList);
                for(long date : dateList){
                    if(shopMap.containsKey(date)){
                        switch (request.getTraceOrOrder()){
                            case item_count:
                                catDataList.add(shopMap.get(date).getItemCount());
                                break;
                            case avg_price:
                                catDataList.add(shopMap.get(date).getAvgPrice());
                                break;
                            case saleCount:
                                catDataList.add(shopMap.get(date).getAmount());
                                break;
                            case amount:
                                catDataList.add(shopMap.get(date).getPrice());
                                break;
                            case save:
                                catDataList.add(shopMap.get(date).getWishSave());
                                break;
                        }
                    }else{
                        catDataList.add(0);
                    }
                }
                dataList.add(catDataMap);
            }
        DataUtil.sortSubCatsData(dataList);
        return map;
    }

    /**
     * 获取店铺热销产品列表
     * @param request
     * @return
     */
    @Override
    public Result getHotItemList(MerchantHotItemListRequest request) throws BizException {
        Result result = new Result();
        result.setPageSize(request.getPageSize());
        result.setPageNo(request.getPageNo());
        long startL = System.currentTimeMillis();

        WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
        wishItemSolrQuery.setMerchantId(request.getMerchantId());
        wishItemSolrQuery.setSortField(TraceOrOrder.amount_7.toString());
        wishItemSolrQuery.setOrder("DESC");
        wishItemSolrQuery.setStart(0);
        wishItemSolrQuery.setRows(50);
        List<ItemDomain> itemDomainList = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();

        if(!ListUtil.isBlank(itemDomainList)){
            DataUtil.sortItemDomain(itemDomainList,request.getTraceOrOrder(),request.getSortType());
            List<ItemDomain> showItemVos = itemDomainList.subList((request.getPageNo()-1)*request.getPageSize(),Math.min(request.getPageNo()*request.getPageSize(),itemDomainList.size()));
            if(!ListUtil.isBlank(showItemVos)){
                for(ItemDomain itemDomain : showItemVos){
                    itemDomain.setCatNames(wishCategoryService.getCategoryNames(itemDomain.getCatIds()));
                }
            }
            result.setItems(showItemVos);
            result.setTotal(Long.parseLong(itemDomainList.size()+""));

        }
        logger.biz("获取店铺热销数据,店铺id:{},耗时{}ms",request.getMerchantId(),(System.currentTimeMillis()-startL));
        return result;
    }


    @Override
    public Result getNewItemList(MerchantNewItemRequest request) throws BizException {

        Result result = new Result();
        result.setPageNo(request.getPageNo());
        result.setPageSize(request.getPageSize());

        WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
        wishItemSolrQuery.setMerchantId(request.getMerchantId());
        wishItemSolrQuery.setSortField(TraceOrOrder.gen_time.toString());
        wishItemSolrQuery.setOrder("DESC");
        wishItemSolrQuery.setStart((request.getPageNo()-1)*request.getPageSize());
        wishItemSolrQuery.setRows(request.getPageSize());
        wishItemSolrQuery.setGenTimeStart(DateUtil.getDate(request.getStartTime()+" 00:00:00","yyyy-MM-dd HH:mm:ss"));
        wishItemSolrQuery.setGenTimeEnd(DateUtil.getDate(request.getEndTime()+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
        List<ItemDomain> itemList = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();
        result.setItems(itemList);
        result.setTotal(Long.parseLong(itemList.size()+""));
        return result;
    }

    /**
     * 获取店铺里程碑列表列表
     *
     * @param request
     * @return
     */
    @Override
    public Result getMerchantEventList(MerchantItemEventRequest request){
        Result result = new Result();
        result.setPageNo(request.getPageNo());
        result.setPageSize(request.getPageSize());
        List<Shop> merchantMarkList = mjShopService.queryShopMilstoneInfoListById(request.getMerchantId(),request.getStartTime(),request.getEndTime());

        if(!ListUtil.isBlank(merchantMarkList)){
            List<MerchantEvent> merchantEventList = new ArrayList<MerchantEvent>();
            for(Shop merchantMark:merchantMarkList){

                MerchantEvent _merchantEvent =MappingUtil.mappingMerchantEvent(merchantMark);
                if(!_merchantEvent.isFilter()){
                    merchantEventList.add(_merchantEvent);
                }
            }
            DataUtil.sortMerchantEvent(merchantEventList,"DESC");

            List<MerchantEvent> shows = new ArrayList<MerchantEvent>();

            //后台分页
            for (int i = (request.getPageNo() - 1) * request.getPageSize(); i < request.getPageNo() * request.getPageSize(); i++) {
                if (i < merchantEventList.size()) {
                    shows.add(merchantEventList.get(i));
                }
            }
            result.setItems(shows);
            result.setTotal(Long.parseLong(merchantEventList.size()+""));
        }
        return result;
    }

    /**
     * 查询店铺滞销宝贝（近30天销量为零）
     *
     * @param merchantId 店铺id
     * @param pageNo     页码
     * @param pageSize   每页数据条数
     * @return
     */
    @Override
    public Result queryUnsalableItemsbyMerchantId(String merchantId, Integer pageNo, Integer pageSize) {
        List<Item> items = null;//mjShopService.queryUnsalableItemsbyMerchantId(merchantId,pageNo,pageSize);
        Result result = new Result();
        result.setPageSize(pageSize);
        result.setPageNo(pageNo);
        if(!ListUtil.isBlank(items)){
            List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>();
            for(Item item : items){
                ItemDomain itemDomain = MappingUtil.mappingItemDomain(item);
                itemDomain.setCatNames(wishCategoryService.getCategoryNames(itemDomain.getCatIds()));
                itemDomainList.add(itemDomain);
            }
            result.setItems(itemDomainList);
        }
        return result;
    }



}
