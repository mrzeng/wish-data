package com.raycloud.overseas.erp.data.services.impl;


import com.alibaba.asyncload.AsyncLoadConfig;
import com.alibaba.asyncload.AsyncLoadExecutor;
import com.alibaba.asyncload.impl.AsyncLoadEnhanceProxy;
import com.google.common.collect.Maps;
import com.raycloud.overseas.data.api.dubbo.service.IEtlInfoService;
import com.raycloud.overseas.data.commom.domain.wish.domain.Industry;
import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.api.dubbo.service.IIndustryService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.api.IndustryService;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.QuickQueryBizDataDAO;
import com.raycloud.overseas.erp.data.common.dao.UserCommonCatDAO;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.UserCommonCatQuery;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.common.util.SortUtil;


import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.domain.GuessItem;
import com.raycloud.overseas.erp.data.domain.GuessMerchant;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.core.WishMerchantSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.services.api.*;
import com.raycloud.overseas.erp.data.services.task.IndustryBasicInfoTask;
import com.raycloud.overseas.erp.data.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhanxf on 16/7/17.
 */
@Service("localIndustryService")
public class LocalIndustryServiceImpl implements ILocalIndustryService,IndustryService {



    private static Logger logger= Logger.getLogger(LocalIndustryServiceImpl.class);

    @Resource
    private String env;

    @Resource
    private WishCategoryService wishCategoryService;

    @Resource
    private WishItemSolrService wishItemSolrService;

    @Resource
    private QuickQueryBizDataDAO quickQueryBizDataDAO;

    @Resource
    private WishMerchantSolrService wishMerchantSolrService;

    @Resource
    private AsyncLoadExecutor asyncLoadExecutor;

    @Resource
    private AsyncLoadConfig asyncLoadConfig;

    @Autowired
    private IIndustryService mjIndustryService;

    @Resource
    private IEtlInfoService mjEtlInfoService;

    @Resource
    private UserCommonCatDAO userCommonCatDAO;

    @Resource
    private GuessItemService guessItemService;

    @Resource
    private GuessMerchantService guessMerchantService;

    public static String ITEM_CHANGE_TIME = null;

    public static String INDUSTRY_CHANGE_TIME = null;

    public static Long ITEM_FLUSH_TIME = null;

    public static Long INDUSTRY_FLUSH_TIME = null;

    public String getDataRefreshTime(int type) {
        Date date = null;
        long now = System.currentTimeMillis();
        try{
            if(type == DataUtil.INDUSTRY_MAX_DATE){
                if(INDUSTRY_CHANGE_TIME!=null && (now-INDUSTRY_FLUSH_TIME)/(3600*1000) == 0){
                    return INDUSTRY_CHANGE_TIME;
                }else{
                    INDUSTRY_CHANGE_TIME = DateUtil.getDate(mjEtlInfoService.queryLatestInsertDate(type),"yyyy-MM-dd");
                    INDUSTRY_FLUSH_TIME = now;
                    logger.biz("获取行业最新时间:{}",now);
                    return INDUSTRY_CHANGE_TIME;
                }
            }else if(type == DataUtil.ITEM_MAX_DATE){
                if(ITEM_CHANGE_TIME!=null  && (now-ITEM_FLUSH_TIME)/(3600*1000) == 0){
                    return ITEM_CHANGE_TIME;
                }else{
                    ITEM_CHANGE_TIME = DateUtil.getDate(mjEtlInfoService.queryLatestInsertDate(type),"yyyy-MM-dd");
                    ITEM_FLUSH_TIME = now;
                    logger.biz("获取宝贝最新时间:{}",now);
                    return ITEM_CHANGE_TIME;
                }
            }
        }catch (Exception e){
            logger.error("FLUSH_WISH_TIME_EXCEPTION",e);
            logger.biz("异常情况");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR,-3);
            date = calendar.getTime();
            return DateUtil.getDate(date,"yyyy-MM-dd");
        }
        return null;
    }

    /**
     * 查询常用行业
     *
     */
    @Override
    public boolean insertCommonCat(UserCommonCat userCommonCat) {

        UserCommonCatQuery userCommonCatQuery = new UserCommonCatQuery();
        userCommonCatQuery.setUserId(userCommonCat.getUserId());
        userCommonCatQuery.setType(userCommonCat.getType());
        List<UserCommonCat> list = userCommonCatDAO.getUserCommonCatList(userCommonCatQuery);
        boolean update = false;
        if(!ListUtil.isBlank(list)){
            for(UserCommonCat uc : list){
                if(uc.getCatId().equals(userCommonCat.getCatId()) || userCommonCat.getCatId().equals("f329d8314cbe40df9445ef244e386760")){
                    return false;
                }
                if(uc.getCatIndex().intValue() == userCommonCat.getCatIndex().intValue()){
                    userCommonCat.setId(uc.getId());
                    update = true;
                    break;
                }
            }
            if(update){
                userCommonCatDAO.updateUserCommonCatByKey(userCommonCat);
            }
        }
        if(!update){
            userCommonCat.setId(UUID.randomUUID().toString().replaceAll("-",""));
            userCommonCatDAO.addUserCommonCat(userCommonCat);
        }
        return true;
    }

    /**
     * 查询常用行业
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public List<UserCommonCat> queryCommonCat(Integer userId, Integer type) {
        UserCommonCatQuery userCommonCatQuery = new UserCommonCatQuery();
        userCommonCatQuery.setUserId(userId);
        userCommonCatQuery.setType(type);
        return userCommonCatDAO.getUserCommonCatList(userCommonCatQuery);
    }

    @Override
    public IndustryDetailVo getIndustryDetail(String id,Integer level) {
        WishCategory wishCategory = null;

        if(level!=null && level>2){
            wishCategory = wishCategoryService.getLevel2Cat(id);
        }else{
            wishCategory = wishCategoryService.getWishCategoryByKey(id);
        }

        Industry industry = mjIndustryService.queryIndustryMarketFocus(wishCategory.getCatId(),null);


        IndustryDetailVo industryDetailVo = new IndustryDetailVo();
        if(industry!=null){
            industryDetailVo.setCatName(wishCategory.getChineseName());
            industryDetailVo.setEnglishName(wishCategory.getEnglishName());
            industryDetailVo.setShopCount(industry.getShopCount());
            industryDetailVo.setItemCount(industry.getItemCount());
            industryDetailVo.setAvgPrice(industry.getAvgPrice()+"");
            industryDetailVo.setAvgAmount7(industry.getAvgAmount());
            industryDetailVo.setAvgSave7(industry.getAvgWishSave());
            industryDetailVo.setOutRate7(quickQueryBizDataDAO.queryIndustryMovingRate(wishCategory.getCatId())+"");
            industryDetailVo.setId(wishCategory.getId());

            //获取7日日均销售额
            Date endDate = mjEtlInfoService.queryLatestInsertDate(7);
            Date startDate = DateUtil.addDays(endDate,-6);
            List<Industry> industryList = mjIndustryService.queryIndustryBasicInfoTrendByCatId(wishCategory.getCatId(),DateUtil.getDate(startDate,"yyyy-MM-dd"),DateUtil.getDate(endDate,"yyyy-MM-dd"));
            if(!ListUtil.isBlank(industryList)){
                Double sum = 0D;
                for(Industry industry1 : industryList){
                    sum += industry1.getPrice();
                }
                sum/=7;
                BigDecimal sumBD = new BigDecimal(sum);
                industryDetailVo.setAvgPrice7(sumBD.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            }
        }
        return industryDetailVo;
    }

    @Override
    public IndustryTrendVo getIndustryTrend(String id,String startTime,String endTime){
        WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(id);
        IndustryTrendVo trendVo = new IndustryTrendVo();

            List<Long> dateList = DateUtil.getDateList(startTime,endTime);
            List<Industry> industryList = mjIndustryService.queryIndustryBasicInfoTrendByCatId(wishCategory.getCatId(),startTime,endTime);

            Map<Long,Industry> dayIndustryMap = Maps.newHashMap();
            if(!ListUtil.isBlank(industryList)){
                for(Industry industry : industryList){
                    dayIndustryMap.put(industry.getInsertDate().getTime(),industry);
                }
            }

            for(int i = 0;i<dateList.size();i++){

                Object[] saleCountObjs = new Object[2];
                Object[] saveObjs = new Object[2];
                Object[] amountObjs = new Object[2];
                Object[] newCountObjs = new Object[2];

                saleCountObjs[0] = dateList.get(i);
                saveObjs[0] = dateList.get(i);
                amountObjs[0] = dateList.get(i);
                newCountObjs[0] = dateList.get(i);

                //时间选的跨度超过可采集的数据时

                if(!dayIndustryMap.containsKey(dateList.get(i))){
                    saleCountObjs[1] = 0;
                    saveObjs[1] = 0;
                    amountObjs[1] = 0;
                    newCountObjs[1] = 0;
                }else{
                    Industry industry = dayIndustryMap.get(dateList.get(i));
                    saleCountObjs[1] = industry.getAmount();
                    saveObjs[1] = industry.getWishSave();
                    amountObjs[1] = industry.getPrice();
                    newCountObjs[1] = industry.getAddCount();
                }

                trendVo.getSaleCount().add(saleCountObjs);
                trendVo.getSave().add(saveObjs);
                trendVo.getAmount().add(amountObjs);
                trendVo.getNewCount().add(newCountObjs);
            }

        return trendVo;
    }



    /**
     * 获取明星产品列表
     * @param id
     * @param sortField
     * @param order
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Result getStarItemList(String id, TraceOrOrder sortField, String order,int pageNo,int pageSize){

        WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(id);

        WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
        wishItemSolrQuery.setCatId(wishCategory.getLevel()==0?null:wishCategory.getCatId());
        wishItemSolrQuery.setSortField(WishItemSolrQuery.SolrField.amount_7.toString());
        wishItemSolrQuery.setOrder("DESC");
        wishItemSolrQuery.setStart(0);
        wishItemSolrQuery.setRows(200);
        Result result = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery);

        List<ItemDomain> itemDomainList = result.getItems();
        wishCategoryService.addCatNameForItemList(itemDomainList);
//        SortUtil.sortListByProperty(itemDomainList,sortField,order);
        DataUtil.sortItemDomain(itemDomainList,sortField,order);
        result.setItems(itemDomainList.subList((pageNo - 1) * pageSize,Math.min(pageNo * pageSize,itemDomainList.size())));
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotal(Long.parseLong(itemDomainList.size()+""));

        return result;
    }

    /**
     * 获取明星店铺列表
     * @param id
     * @param sortField
     * @param order
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Result getStarMerchantList(String id,TraceOrOrder sortField,String order,int pageNo,int pageSize){

        WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(id);

        WishMerchantSolrQuery wishMerchantSolrQuery = new WishMerchantSolrQuery();
        wishMerchantSolrQuery.setCatId(wishCategory.getLevel()==0?null:wishCategory.getCatId());
        wishMerchantSolrQuery.setSortField("amount");
        wishMerchantSolrQuery.setOrder("DESC");
        wishMerchantSolrQuery.setStart(0);
        wishMerchantSolrQuery.setRows(200);
        Result result = wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery);

        List<MerchantDomain> merchantDomainList = result.getItems();
        for(int i = 0;i<merchantDomainList.size();i++){
            merchantDomainList.get(i).setCatAmountRank(i+1);
        }
        DataUtil.sortMerchantDomain(merchantDomainList,sortField,order);
        result.setItems(merchantDomainList.subList((pageNo - 1) * pageSize,Math.min(pageNo * pageSize,merchantDomainList.size())));
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotal(Long.parseLong(merchantDomainList.size()+""));

        return result;
    }

    @Override
    public Result star(final StarRequest request){
        long startL = System.currentTimeMillis();
        if(request.getType()==1){//行业内明星产品
            return getStarItemList(request.getId(),request.getTraceOrOrder(),request.getSortType(),request.getPageNo(),request.getPageSize());

        }else if(request.getType()==2){//行业内明星店铺
            return getStarMerchantList(request.getId(),request.getTraceOrOrder(),request.getSortType(),request.getPageNo(),request.getPageSize());
        }
        logger.biz("获取明星店铺或产品,类型:{},行业{},耗时{}ms",request.getType(),request.getId(),(System.currentTimeMillis()-startL));
        return null;
    }

    @Override
    public Map<String, Object> getSubCatChart(String id,String startTime,String endTime,TraceOrOrder traceOrOrder){

        Map<String, Object> map = new HashMap<String, Object>();

        WishCategoryQuery wishCategoryQuery = new WishCategoryQuery();
        wishCategoryQuery.setParentId(id);
        List<WishCategory> subCatagoryList = wishCategoryService.getWishCategoryList(wishCategoryQuery);


        List<Long> dateList = DateUtil.getDateList(startTime,endTime);
        List times = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(long dateTime:dateList){
            times.add(sdf.format(new Date(dateTime)));
        }

        List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
        map.put("date",times);
        map.put("data",dataList);
        if(!ListUtil.isBlank(subCatagoryList)){
            for (WishCategory wishCategory : subCatagoryList){
                IndustryBasicInfoTask industryBasicInfoTask = new IndustryBasicInfoTask();

                AsyncLoadEnhanceProxy<IndustryBasicInfoTask> taskAsyncLoadEnhanceProxy = new
                        AsyncLoadEnhanceProxy<IndustryBasicInfoTask>();
                taskAsyncLoadEnhanceProxy.setExecutor(asyncLoadExecutor);
                taskAsyncLoadEnhanceProxy.setConfig(asyncLoadConfig);
                taskAsyncLoadEnhanceProxy.setService(industryBasicInfoTask);
                IndustryBasicInfoTask proxy = taskAsyncLoadEnhanceProxy.getProxy();

                Map<String,Object> catDataMap = proxy.queryIndustryBasicInfo(wishCategory,traceOrOrder.toString(),
                        startTime,endTime,dateList);

                dataList.add(catDataMap);
            }

            DataUtil.sortSubCatsData(dataList);
        }
        return map;
    }

    @Override
    public Result getAmountGrowthItemList(AmountGrowthItemRequest request) {
        WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(request.getId());
        Result result = new Result();
        result.setPageSize(request.getPageSize());
        result.setPageNo(request.getPageNo());


        long startL = System.currentTimeMillis();
        List<String> itemIds = quickQueryBizDataDAO.queryAmountGrowthItemList(wishCategory.getCatId(),request.getMaxInsertDate());
        if(!ListUtil.isBlank(itemIds)){

            WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
            wishItemSolrQuery.setItemIdCollection(itemIds);
            wishItemSolrQuery.setStart(0);
            wishItemSolrQuery.setRows(200);
            result = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery);

            List<ItemDomain> itemDomainList = result.getItems();

            DataUtil.sortItemDomain(itemDomainList,request.getTraceOrOrder(),request.getSortType());

            List<ItemDomain> showList = itemDomainList.subList((request.getPageNo()-1)*request.getPageSize(),Math.min(request.getPageNo()*request.getPageSize(),itemDomainList.size()));

            result.setItems(showList);
            result.setTotal(Long.parseLong(itemDomainList.size()+""));
            logger.biz("获取行业暴增宝贝数据,行业名:{},耗时{}ms,返回结果个数:{}", wishCategory.getChineseName(),(System.currentTimeMillis()-startL),ListUtil.isBlank(itemIds)?0:itemIds.size());

        }

        return result;
    }

    @Override
    public Result getNewItemList(NewItemRequest request) throws BizException {

        WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(request.getId());
        Result result = new Result();
        result.setPageSize(request.getPageSize());
        result.setPageNo(request.getPageNo());

        long startL = System.currentTimeMillis();

        List<Item> itemList = mjIndustryService.queryIndustryNewItemListByCatId(wishCategory.getCatId(),1, 50);
        Map<String,Item> itemMap = new HashMap<String, Item>();
        if(!ListUtil.isBlank(itemList)){
            List<String> itemIdList = new ArrayList<String>();
            for (Item item : itemList){
                itemIdList.add(item.getItemId());
                itemMap.put(item.getItemId(),item);
            }

            WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
            wishItemSolrQuery.setStart(0);
            wishItemSolrQuery.setRows(50);
            wishItemSolrQuery.setItemIdCollection(itemIdList);
            Result result1 = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery);
            List<ItemDomain> itemDomainList = result1.getItems();
            for(ItemDomain itemDomain : itemDomainList){
                itemDomain.setAmount(itemMap.get(itemDomain.getItemId()).getAmount());
            }
            result.setTotal(Long.parseLong(itemList.size()+""));
            result.setItems(itemDomainList);
            logger.biz("获取行业上新宝贝,行业id:{},宝贝个数:{},耗时{}ms",wishCategory.getCatId(),itemList.size(),System.currentTimeMillis()-startL);
        }
        return result;
    }

    @Override
    public List<CategoryVo> getAllCat() {

        List<CategoryVo> categoryVos = new ArrayList<CategoryVo>();

        Map<String,WishCategory> categoryMap = wishCategoryService.getWishCategorMap();

        for(String id : categoryMap.keySet()){
            WishCategory wishCategory = categoryMap.get(id);
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(wishCategory.getId());
            categoryVo.setParentId(wishCategory.getParentId());
            categoryVo.setLevel(wishCategory.getLevel());
            categoryVo.setChineseName(wishCategory.getChineseName());
            categoryVo.setEnglishName(wishCategory.getEnglishName());
            categoryVos.add(categoryVo);
        }
        return categoryVos;
    }

    @Override
    public CategoryVo getDefaultCategory() {

        Map<String,WishCategory> categoryMap = wishCategoryService.getWishCategorMap();

        CategoryVo categoryVo = new CategoryVo();
        for(String id : categoryMap.keySet()){
            WishCategory wishCategory = categoryMap.get(id);
            if(wishCategory.getCatId().equals("0")){
                categoryVo.setId(wishCategory.getId());
                categoryVo.setParentId(wishCategory.getParentId());
                categoryVo.setLevel(wishCategory.getLevel());
                categoryVo.setChineseName(wishCategory.getChineseName());
                categoryVo.setEnglishName(wishCategory.getEnglishName());
                break;
            }
        }

        return categoryVo;
    }

    /**
     * 获取用户显示的猜你喜欢的产品列表
     *
     * @param request
     */
    @Override
    public List<GuessItem> getGuessItemList(GuessItemListRequest request) {
        return guessItemService.getGuessItemList(request.getUserId());
    }

    /**
     * 获取用户显示的猜你喜欢的店铺列表
     *
     * @param request
     * @return
     */
    @Override
    public List<GuessMerchant> getGuessMerchantList(GuessMerchantListRequest request) {
        return guessMerchantService.getGuessMerchantList(request.getUserId());
    }
}
