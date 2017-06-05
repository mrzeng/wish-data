package com.raycloud.overseas.erp.data.services.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.commom.domain.wish.domain.ItemMilestone;
import com.raycloud.overseas.data.commom.domain.wish.domain.Shop;
import com.raycloud.overseas.data.api.dubbo.service.IItemService;
import com.raycloud.overseas.data.api.dubbo.service.IShopService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.FocusItemDAO;
import com.raycloud.overseas.erp.data.common.dao.FocusMarkMapDAO;
import com.raycloud.overseas.erp.data.common.dao.WishItemDAO;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.FocusItemQuery;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.ItemEvent;
import com.raycloud.overseas.erp.data.domain.ItemSKU;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.services.api.*;
import com.raycloud.overseas.erp.data.services.context.CollectContext;
import com.raycloud.overseas.erp.data.services.ons.OnsUtils;
import com.raycloud.overseas.erp.data.services.util.MappingUtil;
import com.raycloud.overseas.erp.data.vo.ItemChartVo;
import com.raycloud.overseas.erp.services.product.wish.IWishDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhanxf on 16/7/17.
 */
@Service
public class LocalItemServiceImpl implements ILocalItemService {

    private static Logger logger= Logger.getLogger(LocalItemServiceImpl.class);

    @Autowired
    private FocusItemDAO focusItemDAO;

    @Autowired
    private IItemService mjItemService;

    @Autowired
    private WishCategoryService wishCategoryService;

    @Autowired
    private FocusMarkMapDAO focusMarkMapDAO;

    @Autowired
    private ComplementItemService complementItemService;

    @Resource
    private WishItemSolrService wishItemSolrService;

    @Autowired
    private IShopService mjShopService;

    @Autowired
    private IWishDataService wishDataService;

    @Autowired
    private WishItemDAO wishItemDAO;

    @Autowired
    private OnsUtils onsUtils;

    private final static int ONCE_COLLECT_COUNT_MAX = 20;

    @Resource
    CommonService commonService;

    @Override
    public FocusItem getFocusItemByKey(UserData userData, String itemId) {
        return focusItemDAO.getFocusItemByKey(userData.getFounderId(),userData.getUserId()+"", itemId);
    }

    @Override
    public List<FocusItem> getFocusItemList(FocusItemQuery focusItemQuery) {
        return focusItemDAO.getFocusItemList(focusItemQuery);
    }

    @Override
    public void batchUpdateFocusItem(List<FocusItem> focusItemList) {
        focusItemDAO.batchUpdateFocusItemByKey(focusItemList);
    }

    /**
     * 获取关注宝贝列表
     * @param request
     * @return
     * @throws BizException
     */
    @Override
    public Result getFocusItemList(FocusItemListRequest request) throws BizException {

        if(!StringUtils.isEmpty(request.getSearchKey())){
            if(DataUtil.judgeIsId(request.getSearchKey())){
                request.setItemId(request.getSearchKey());
            }else{
                request.setItemName(request.getSearchKey());
            }
        }

        FocusItemQuery focusItemQuery = new FocusItemQuery();
        focusItemQuery.setFocus(request.getFocus());
        focusItemQuery.setUserId(request.getUserData().getUserId()+"");
        focusItemQuery.setFounderId(request.getUserData().getFounderId());
        focusItemQuery.setItemId(request.getItemId());
        focusItemQuery.setItemName(request.getItemName());
        focusItemQuery.setUpdatedStart(DateUtil.getDate(request.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
        focusItemQuery.setUpdatedEnd(DateUtil.getDate(request.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
        focusItemQuery.setPageNo(request.getPageNo());
        focusItemQuery.setPageSize(request.getPageSize());
        focusItemQuery.setMarkIdList(ListUtil.strToList(request.getMarkIds(),","));
        focusItemQuery.setCollected(request.getCollected());
        focusItemQuery.orderbyUpdated(false);
        Result result = focusItemDAO.getFocusItemListWithPage(focusItemQuery);
        List<FocusItem> focusItemList = result.getItems();
        boolean reload = false;
        if(!ListUtil.isBlank(focusItemList)){

            List<String> itemIds = new ArrayList<String>();
            Map<String,FocusItem> focusMap = new HashMap<String, FocusItem>();
            for(int i =0 ;i<focusItemList.size();i++){
                itemIds.add(focusItemList.get(i).getItemId());
                focusMap.put(focusItemList.get(i).getItemId(),focusItemList.get(i));
            }

            WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
            wishItemSolrQuery.setItemIdCollection(itemIds);
            wishItemSolrQuery.setRows(0);
            wishItemSolrQuery.setRows(itemIds.size());
            List<ItemDomain> solrItemData = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();
            Map<String,ItemDomain> solrItemMap = new HashMap<String, ItemDomain>();
            if(!ListUtil.isBlank(solrItemData)){
                Map<String,List<Integer>> markMap = focusMarkMapDAO.getMarkIdMap(request.getUserData().getUserId(),request.getUserData().getFounderId(),DataConstant.MARK_ITEM);
                for(ItemDomain itemDomain : solrItemData){
                    itemDomain.setCollected(focusMap.get(itemDomain.getItemId()).getCollected());
                    itemDomain.setMarkIds(markMap.get(itemDomain.getItemId()));
                    itemDomain.setCatNames(wishCategoryService.getCategoryNames(itemDomain.getCatIds()));
                    solrItemMap.put(itemDomain.getItemId(),itemDomain);
                }
            }
            result.getItems().clear();
            for(String itemId : itemIds){
                if(solrItemMap.containsKey(itemId)){
                    result.getItems().add(solrItemMap.get(itemId));
                }else{
                    focusItemDAO.deleteByItemId(itemId);
                    reload = true;
                }
            }
        }

        result.setPageNo(request.getPageNo());
        result.setPageSize(request.getPageSize());
        if(reload){
            result=getFocusItemList(request);
        }
        return result;
    }

    /**
     * 关注宝贝列表
     * @param itemIdList
     * @param focus
     * @param expireTime
     * @param userData
     * @throws BizException
     */
    @Override
    public void focusItemList(List<String> itemIdList,List<String> merchantIdList,Integer focus,Date expireTime,UserData userData) throws BizException {
        if(ListUtil.isBlank(itemIdList)){
            return;
        }
        List<String> storeItemIdList = new ArrayList<String>();
        for(int i=0;i<itemIdList.size();i++){
            String itemId = itemIdList.get(i);
            FocusItem focusItem = focusItemDAO.getFocusItemByKey(userData.getFounderId(),userData.getUserId()+"",itemId);
            if(focusItem == null){
                focusItem = new FocusItem();
                focusItem.setCreated(new Date());
                focusItem.setItemId(itemId);
                focusItem.setUpdated(new Date());
                focusItem.setUserId(userData.getUserId()+"");
                focusItem.setFounderId(userData.getFounderId());
                focusItem.setFocus(focus);
                focusItem.setCollected(DataConstant.UN_COLLECT);
                Item item = mjItemService.queryItemByItemId(itemId,merchantIdList.get(i));
                if(item == null){
                    logger.biz("待关注宝贝在数据库不存在,宝贝id:{},用户id:{}",itemId,userData.getUserId());
                    continue;
                }
                //说明有团队成员,so去查看团内内是否有人采集了该宝贝
                if(userData.getFounderId()!=userData.getUserId().intValue()){
                    FocusItemQuery focusItemQuery = new FocusItemQuery();
                    focusItemQuery.setFounderId(userData.getFounderId());
                    focusItemQuery.setItemId(itemId);
                    focusItemQuery.setCollected(DataConstant.COLLECT);
                    List<FocusItem> focusItemList = focusItemDAO.getFocusItemList(focusItemQuery);
                    if(!ListUtil.isBlank(focusItemList)){
                        focusItem.setCollected(DataConstant.COLLECT);
                    }
                }
                focusItem.setItemName(item.getItemName());

                focusItem.setExpireTime(expireTime);
                focusItemDAO.addFocusItem(focusItem);
                storeItemIdList.add(itemId);
            }else{
                focusItem.setFocus(focus);
                focusItem.setUpdated(new Date());
                focusItemDAO.updateFocusItemByKey(focusItem);
            }
        }
        complementItemService.storeItemPermanent(storeItemIdList,userData);
    }

    /**
     * 通过关注的宝贝详情界面去采集产品
     * @param itemId
     * @param userData
     */
    @Override
    public void collectToLocalByFocus(String itemId,String merchantId, UserData userData) throws BizException {
        List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>();
        ItemDomain itemDomain = getItemForCollect(itemId,merchantId,userData);
        if(itemDomain!=null){
            itemDomainList.add(itemDomain);
        }
        if(ListUtil.isBlank(itemDomainList)){
            logger.biz("采集失败,产品列表为空,产品id:{},用户id{}",itemId,userData.getUserId());
            return;
        }
        List<String> sucList = wishDataService.batchSaveItem(itemDomainList,Long.parseLong(userData.getUserId()+""));
        if(ListUtil.isBlank(sucList)){
            logger.biz("采集失败,产品id:{},用户id{}",itemId,userData.getUserId());
        }else{
            markTeamItemAsCollected(sucList,null,userData);
            logger.biz("采集成功,产品id:{},用户id{}",itemId,userData.getUserId());
        }
    }

    /**
     * 通过推送列表或搜索界面去采集产品到本地
     * @param itemIdList
     * @param userData
     * @throws Exception
     */
    @Override
    public CollectContext collectToLocalByEntry(List<String> itemIdList,List<String> merchantIdList,boolean needBean, UserData userData){
        Date tommorrow = DateUtil.addDays(new Date(),1);
        int vipLevel = commonService.getUserLevel(userData);
        CollectContext collectContext = null;
        try{
            if(ListUtil.isBlank(itemIdList)){
                logger.biz("用户id:{},宝贝id列表为空",userData.getUserId());
                throw new BizException(ExceptionCode.NULL_ERROR,"空宝贝id列表");
            }
            List<ItemDomain> itemDomainList = getItemListForCollect(itemIdList, merchantIdList, userData);
            commonService.setSynProcess(20,DataConstant.ASYN_ITEM_COLLECT,userData,null);
            if(ListUtil.isBlank(itemDomainList)){
                logger.biz("用户id:{},采集失败,产品列表为空,宝贝id列表:{}",userData.getUserId(),JSON.toJSONString(itemIdList));
                throw new BizException(ExceptionCode.NULL_ERROR,"产品列表为空");
            }

            collectContext = new CollectContext(tommorrow,itemDomainList,userData,Collections.EMPTY_LIST,vipLevel,needBean);
            blockCollectToProductDomain(collectContext,itemDomainList,userData);
            collectThenFocus(collectContext);
            logger.biz("共采集产品数:{},采集成功产品数:{},用户id:{}",collectContext.collectItemList.size(),collectContext.sucIdList.size(),userData.getUserId());
            if(collectContext.userLevel==DataConstant.VIP_0){
                commonService.consumeBeans(userData.getUserId(),userData.getFounderId(),collectContext.consume,3009, MessageFormat.format("批量采集{0}个产品",collectContext.sucIdList.size()));
            }
        }catch (Exception e){
            logger.error("宝贝采集异常",e);
            collectContext = new CollectContext(tommorrow,Collections.EMPTY_LIST,userData,Collections.EMPTY_LIST,vipLevel,needBean);
        }finally {
            commonService.setSynProcess(100,DataConstant.ASYN_ITEM_COLLECT,userData,collectContext.createExecInfo());
            collectContext.showFailLog();
            onsUtils.sendOns(DataConstant.COLLECT_FINISH_NOTICE,MessageFormat.format("采集完成,成功{0}个,共消费{1}个超级豆",collectContext.sucIdList.size(),collectContext.consume),userData);
        }
        return collectContext;
    }

    private void blockCollectToProductDomain(CollectContext collectContext,List<ItemDomain> srcItemList,UserData userData){
        if(ListUtil.isBlank(srcItemList)){
            logger.biz("采集失败,产品列表为空,用户id{}",userData.getUserId());
            return;
        }
        if(srcItemList.size()>ONCE_COLLECT_COUNT_MAX){
            collectContext.sucIdList = new ArrayList<String>(srcItemList.size());
            List<List> blocks = DataUtil.blockLargeCollection(srcItemList,ONCE_COLLECT_COUNT_MAX);
            for (int i=0;i<blocks.size();i++) {
                commonService.setSynProcess(20+i*(80/blocks.size()),DataConstant.ASYN_ITEM_COLLECT,userData,collectContext.createExecInfo());
                List<ItemDomain> block_i = blocks.get(i);
                List<String> sucList = null;
                try{
                    sucList = wishDataService.batchSaveItem(block_i,Long.parseLong(userData.getUserId()+""));
                }catch (Exception e){
                    logger.error(MessageFormat.format("采集失败,用户id:{0}",userData.getUserId()),e);
                }finally {
                    if(sucList!=null){
                        collectContext.sucIdList.addAll(sucList);
                    }
                }
            }
        }else{
            List<String> sucList = null;
            try{
                sucList = wishDataService.batchSaveItem(srcItemList,Long.parseLong(userData.getUserId()+""));
            }catch (Exception e){
                logger.error(MessageFormat.format("采集失败,用户id:{0}",userData.getUserId()),e);
            }finally {
                if(sucList!=null){
                    collectContext.sucIdList=sucList;
                }
            }
        }
        collectContext.countConsumeBeans();
    }

    /**
     * 采集完产品后再对产品进行24小时关注操作
     * @param collectContext
     */
    @Override
    public void collectThenFocus(CollectContext collectContext){
        if(ListUtil.isBlank(collectContext.collectItemList)){
            return;
        }
        List<FocusItem> focusItemList = new ArrayList<FocusItem>(collectContext.collectItemList.size());
        for(ItemDomain item : collectContext.collectItemList){
            FocusItem focusItem = focusItemDAO.getFocusItemByKey(collectContext.userData.getFounderId(),collectContext.userData.getUserId()+"",item.getItemId());
            if(focusItem == null){
                focusItem = new FocusItem();
                focusItem.setCreated(new Date());
                focusItem.setItemId(item.getItemId());
                focusItem.setUpdated(new Date());
                focusItem.setUserId(collectContext.userData.getUserId()+"");
                focusItem.setFounderId(collectContext.userData.getFounderId());
                focusItem.setFocus(DataConstant.FOCUS);
                focusItem.setItemName(item.getItemName());
                focusItem.setCollected(DataConstant.COLLECT);
                focusItem.setExpireTime(collectContext.expireTime);
                focusItemList.add(focusItem);
                focusItemDAO.addFocusItem(focusItem);
                logger.biz("新关注宝贝,宝贝id:{},过期时间{},用户id:{}",item.getItemId(),focusItem.getExpireTime(),focusItem.getUserId());
            }else{
                if(focusItem.getFocus() == DataConstant.UN_FOCUS){
                    focusItem.setFocus(DataConstant.FOCUS);
                    focusItem.setExpireTime(collectContext.expireTime);
                }
                focusItem.setCollected(DataConstant.COLLECT);
                focusItemDAO.updateFocusItemByKey(focusItem);
                logger.biz("宝贝关注,宝贝id:{},过期时间{},用户id:{}",item.getItemId(),focusItem.getExpireTime(),focusItem.getUserId());
            }
        }
        complementItemService.storeItemPermanent(collectContext.sucIdList,collectContext.userData);
        markTeamItemAsCollected(collectContext.sucIdList,null,collectContext.userData);
        logger.biz("共成功关注宝贝:{}个,用户id:{}",collectContext.collectItemList.size(),collectContext.userData.getUserId());
    }

    /**
     * 标记团队内所有成员的此次采集的宝贝为已采集
     *
     * @param itemIdList
     * @param expireTime
     * @param userData
     */
    @Override
    public void markTeamItemAsCollected(List<String> itemIdList, Date expireTime, UserData userData) {
        if(ListUtil.isBlank(itemIdList)){
            return;
        }
        FocusItemQuery focusItemQuery = new FocusItemQuery();
        focusItemQuery.setCollected(DataConstant.COLLECT);
        focusItemQuery.setFounderId(userData.getFounderId());
        focusItemQuery.setKeys(itemIdList);
        focusItemQuery.setExpireTime(expireTime);
        focusItemQuery.setUpdated(new Date());
        focusItemDAO.updateFocusItemByQuery(focusItemQuery);
    }

    @Override
    public ItemDomain getItemForCollect(String itemId,String merchantId, UserData userData) throws BizException {
        if(StringUtils.isEmpty(merchantId)){
            merchantId = getMerchantIdByItem(itemId);
        }
        Item item = mjItemService.queryItemByItemId(itemId,merchantId);
        if(item==null){
            logger.biz("宝贝不存在,宝贝id:{},用户id:{}",itemId,userData.getUserId());
            throw new BizException(ExceptionCode.NULL_ERROR,"宝贝"+itemId+"不存在");
        }
        Item itemDetail = getMjWishItemDetail(itemId,item.getMerchantId());
        ItemDomain itemDomain = MappingUtil.mappingItemDomain(item);
        dealItemTags(itemDomain,item);
        dealItemDetail(itemDomain,itemDetail);
        return itemDomain;
    }

    @Override
    public String getMerchantIdByItem(String itemId){
        WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
        wishItemSolrQuery.setItemId(itemId);
        List<ItemDomain> itemDomainList = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();
        if(!ListUtil.isBlank(itemDomainList)){
            return itemDomainList.get(0).getMerchantId();
        }
        return null;
    }

    /**
     * 处理宝贝标签
     *
     * @param itemDomain
     * @param item
     */
    @Override
    public void dealItemTags(ItemDomain itemDomain, Item item) {
        if(item==null || itemDomain == null){
            return;
        }
        //设置标签排序
        List<String> customTagList = ListUtil.strToList(item.getCustomTag(),"\\^");
        List<String> wishTagList = ListUtil.strToList(item.getWishTag(),"\\^");

        if(customTagList!=null){
            List<String> customTagList2 = new ArrayList<String>();
            for(int i = 0;i<customTagList.size();i++){
                customTagList2.add(customTagList.get(i).split("@")[1]);
            }

            List<String> wishTagList2 = new ArrayList<String>();
            for(int i = 0;i<wishTagList.size();i++){
                wishTagList2.add(wishTagList.get(i).split("@")[1]);
            }

            DataUtil.sort(customTagList2,String.class,"ASC");
            DataUtil.sort(wishTagList2,String.class,"ASC");

            itemDomain.setCustomTagList(customTagList2);
            itemDomain.setWishTagList(wishTagList2);
        }

    }

    @Override
    public void dealItemDetail(ItemDomain itemDomain, Item itemDetail) {
        if(itemDomain == null || itemDetail == null){
            return;
        }
        if(!StringUtils.isEmpty(itemDetail.getItemDesc())){
            itemDomain.setItemDesc(itemDetail.getItemDesc().replaceAll("%2C", ","));
        }
        String[] skuSizes = itemDetail.getSkuSize().split(";");
        String[] skuColors = itemDetail.getSkuColor().split(";");
        String[] skuPics = itemDetail.getSkuSequenceId().split(";");
        String[] skus = itemDetail.getSkuManufacturerId().split(";");
        String[] prices = itemDetail.getSkuPrice().split(";");

        setHistoryImageIdList(itemDomain, itemDetail.getSkuImage());
        if (skuSizes != null && skuColors != null && skuPics != null && skus != null && prices != null) {
            List<ItemSKU> itemSKUs = new ArrayList<ItemSKU>();
            for (int i = 0; i < skuPics.length; i++) {
                ItemSKU sku = new ItemSKU();
                if (i < skus.length) {
                    String[] skuMeta = skus[i].split(":");
                    sku.setSku(skuMeta.length > 1 ? skuMeta[1] : null);
                }
                if (i < skuColors.length) {
                    String[] skuColorMeta = skuColors[i].split(":");
                    sku.setColor(skuColorMeta.length > 1 ? skuColorMeta[1] : null);
                }
                if (i < skuSizes.length) {
                    String[] skuSizeMeta = skuSizes[i].split(":");
                    sku.setSize(skuSizeMeta.length > 1 ? skuSizeMeta[1] : null);
                }
                if (i < prices.length) {
                    String[] skuPriceMeta = prices[i].split(":");
                    sku.setPrice(skuPriceMeta.length > 1 ? Double.parseDouble(skuPriceMeta[1]) : null);
                }
                if (i < skuPics.length) {
                    String[] skuPicMeta = skuPics[i].split(":");
                    if (skuPicMeta.length > 1) {
                        sku.setPic("https://contestimg.wish.com/api/webimage/" + itemDomain.getItemId() + "-" + skuPicMeta[1] + "-small.jpg");
                    } else {
                        sku.setPic("https://contestimg.wish.com/api/webimage/" + itemDomain.getItemId() + "-small.jpg");
                    }

                }
                itemSKUs.add(sku);
            }

            itemDomain.setSkuList(itemSKUs);
        }
    }

    @Override
    public ItemDomain getItemDetail(ItemDetailRequest request) throws BizException {

        ItemDomain itemDomain = getItemForCollect(request.getItemId(),request.getMerchantId(),request.getUserData());
        if(itemDomain == null){
            logger.biz("宝贝不存在,宝贝id:{},用户id:{}",request.getItemId(),request.getUserData().getUserId());
            throw new BizException(ExceptionCode.NULL_ERROR,"数据生成中,请稍后再试");
        }

        //设置行业信息
        itemDomain.setWishCategoryList(wishCategoryService.getWishCategoryByCatIds(itemDomain.getCatIds()));

        FocusItem focusItem = focusItemDAO.getFocusItemByKey(request.getUserData().getFounderId(),request.getUserData().getUserId()+"",request.getItemId());
        itemDomain.setFocus(focusItem == null?0:focusItem.getFocus());
        setSuperBossNo(itemDomain,request.getUserData());
        Shop shop = mjShopService.queryShopInfoById(itemDomain.getMerchantId());
        itemDomain.setMerchantName(shop.getMerchantNick());

        return itemDomain;
    }

    @Override
    public void setHistoryImageIdList(ItemDomain itemDomain,String skuImages) {
        List<Integer> historyIdList = new ArrayList<Integer>();
        List<Integer> idList = new ArrayList<Integer>();
        //历史图片
        if(!StringUtils.isEmpty(skuImages)){
            String[] skuImageArr = skuImages.split("\\^");

            if(skuImageArr!=null){
                Map<Integer,Integer> map = new HashMap<Integer, Integer>();
                for(String skuImage : skuImageArr){
                    Integer imgId = Integer.parseInt(skuImage.split("#")[0]+"");
                    map.put(imgId,imgId);
                    idList.add(imgId);
                }
                Collections.sort(idList);
                int max = idList.get(idList.size()-1);
                for(int i = 1;i<=max;i++){
                    if (!map.containsKey(i)){
                        historyIdList.add(i);
                    }
                }
            }
        }
        itemDomain.setImageIdList(idList);
        itemDomain.setHistoryImageIdList(historyIdList);
    }

    @Override
    public ItemChartVo getItemChart(ItemChartDataRequest request){
        ItemChartVo itemChartVo = new ItemChartVo();
        List<Item> itemList = null;

        itemList = mjItemService.queryItemOfferListById(request.getItemId(),request.getMerchantId(),request.getStartTime(),request.getEndTime());

        Map<Long, Item> traceMap = new HashMap<Long,Item>();

        List<Long> dateList = DateUtil.getDateList(request.getStartTime(),request.getEndTime());

        if(!ListUtil.isBlank(itemList)){
            for(Item itemTrace:itemList){
                traceMap.put(itemTrace.getInsertDate().getTime(),itemTrace);
            }
        }

        for(long dateTime:dateList) {

            Object[] amounts = new Object[2];
            Object[] saves = new Object[2];

            Object[] originalPrice = new Object[2];
            Object[] wishPrice = new Object[2];
            Object[] wishFreightPrice = new Object[2];
            Object[] sellerPrice = new Object[2];
            Object[] sellerFreightPrice = new Object[2];


            amounts[0] = dateTime;
            saves[0] = dateTime;

            originalPrice[0] = dateTime;
            wishPrice[0] = dateTime;
            wishFreightPrice[0] = dateTime;
            sellerPrice[0] = dateTime;
            sellerFreightPrice[0] = dateTime;


            if (traceMap.containsKey(dateTime)) {
                Item traceItem = traceMap.get(dateTime);
                amounts[1] = traceItem.getAmount1();
                saves[1] = traceItem.getWishSave1();

                originalPrice[1] = traceItem.getOriginalPrice();
                wishPrice[1] = traceItem.getWishPrice();
                wishFreightPrice[1] = traceItem.getWishFreightPrice();
                sellerPrice[1] = traceItem.getSellerPrice();
                sellerFreightPrice[1] = traceItem.getSellerFreightPrice();

            } else {
                amounts[1] = 0;
                saves[1] = 0;

                originalPrice[1] = 0;
                wishPrice[1] = 0;
                wishFreightPrice[1] = 0;
                sellerPrice[1] = 0;
                sellerFreightPrice[1] = 0;

            }

            itemChartVo.getSaleCount().add(amounts);
            itemChartVo.getSave().add(saves);

            itemChartVo.getOriginalPrice().add(originalPrice);
            itemChartVo.getWishPrice().add(wishPrice);
            itemChartVo.getWishFreightPrice().add(wishFreightPrice);
            itemChartVo.getSellerPrice().add(sellerPrice);
            itemChartVo.getSellerFreightPrice().add(sellerFreightPrice);
        }

        return itemChartVo;
    }

    /**
     * 获取宝贝们的里程碑列表
     *
     * @param itemIdList
     * @return
     */
    @Override
    public Result getItemsEventList(List<String> itemIdList,List<String> merchantIdList){

        long start = System.currentTimeMillis();

        List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>(10);
        for(int i=0;i<itemIdList.size();i++){
            //处理里程碑信|
            List<ItemMilestone> milestones = null;

            milestones = mjItemService.queryItemMilestoneInfoByItemId(itemIdList.get(i),merchantIdList.get(i));

            List<ItemEvent> itemEventList = getItemEventList(milestones);
            DataUtil.sortItemEvent(itemEventList,"DESC");
            ItemDomain itemDomain = new ItemDomain();
            itemDomain.setItemId(itemIdList.get(i));
            itemDomain.setItemEventList(itemEventList);
            itemDomainList.add(itemDomain);
        }

        logger.biz("获取宝贝列表里程碑,宝贝个数:{},耗时{}ms", itemIdList.size(),System.currentTimeMillis()-start);

        Result result = new Result();
        result.setItems(itemDomainList);

        return result;
    }

//    @Override
//    public Result queryRelatedHotItems(String itemId, Integer days, String sortField, Integer pageNo, Integer pageSize) {
//
//        List<Item> items = mjItemService.queryItemRelatedIndustryHotItemList(itemId,days,sortField,pageNo,pageSize);
//        Result result = new Result();
//        result.setPageSize(pageSize);
//        result.setPageNo(pageNo);
//        if(!ListUtil.isBlank(items)){
//            List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>();
//            for(Item item : items){
//                ItemDomain itemDomain = MappingUtil.mappingItemDomain(item);
//                itemDomain.setCatNames(wishCategoryService.getCategoryNames(item.getCatIds()));
//                itemDomain.setAmount(item.getAmount());
//                itemDomain.setPrice(item.getPrice());
//                itemDomainList.add(itemDomain);
//            }
//            result.setItems(itemDomainList);
//        }
//
//        return result;
//    }

//    /**
//     * 查询某产品相关的飙升产品
//     *
//     * @param itemId    产品id
//     * @param days      7:按近7天销量查询，30:按近30天销量查询
//     * @param sortField 排序字段 amount(销量)/price(销售额)/amountDiff(近7天与上一个7天销量差额)，默认倒序排序，不传则无排序规则
//     * @param pageNo    页码
//     * @param pageSize  每页数据条数
//     * @return
//     */
//    @Override
//    public Result queryItemRelatedIndustrySurgeItemList(String itemId, Integer days, String sortField, Integer pageNo, Integer pageSize) {
//        List<Item> items = mjItemService.queryItemRelatedIndustrySurgeItemList(itemId,days,sortField,pageNo,pageSize);
//        Result result = new Result();
//        result.setPageSize(pageSize);
//        result.setPageNo(pageNo);
//        if(!ListUtil.isBlank(items)){
//            List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>();
//            for(Item item : items){
//                ItemDomain itemDomain = MappingUtil.mappingItemDomain(item);
//                itemDomain.setCatNames(wishCategoryService.getCategoryNames(item.getCatIds()));
//                itemDomain.setAmount(item.getAmount());
//                itemDomain.setPrice(item.getPrice());
//                itemDomainList.add(itemDomain);
//            }
//            result.setItems(itemDomainList);
//        }
//
//        return result;
//    }
//
//    /**
//     * 查询某产品相关的标签，按标签热度倒序排序
//     * 这里的业务逻辑是产品定的：查询产品对应level最高行业的热销产品的所有标签
//     *
//     * @param itemId   产品id
//     * @param tagType  标签类型：0-wish推荐标签，1-卖家自定义标签，注意：目前只有卖家自定义标签数据！
//     * @param pageNo   页码
//     * @param pageSize 每页数据条数
//     * @return
//     */
//    @Override
//    public Result queryItemRelatedIndustryHotItemTagList(String itemId, Integer tagType, Integer pageNo, Integer pageSize) {
//        List<Tag> tags = mjItemService.queryItemRelatedIndustryHotItemTagList(itemId,tagType,pageNo,pageSize);
//        Result result = new Result();
//        result.setPageSize(pageSize);
//        result.setPageNo(pageNo);
//        result.setItems(tags);
//
//        return result;
//    }

    /**
     * 获取价格调整里程碑事件
     *
     * @param milestone
     * @return
     */
    @Override
    public List<ItemEvent> getPriceItemEvent(ItemMilestone milestone) {
        JSONObject jsonObject = JSON.parseObject(milestone.getJsonDesc());
        List<ItemEvent> itemEventList = new ArrayList<ItemEvent>(5);
        for(String key : jsonObject.keySet()){
            String oldDesc = jsonObject.getString(key);
            ItemEvent itemEvent = new ItemEvent();
            itemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(milestone.getInsertDate()));
            String[] priceStr = oldDesc.split("从");
            if(key.equals("changeWishPrice")){
                itemEvent.setType("changeWishPrice");
                itemEvent.setEventDesc("Wish将产品销售价格从"+priceStr[1]);
            }else if(key.equals("changeOriginalPrice")){
                itemEvent.setType("changeOriginalPrice");
                itemEvent.setEventDesc("卖家将产品吊牌价格从"+priceStr[1]);
            }else if(key.equals("changeSellerPrice")){
                itemEvent.setType("changeSellerPrice");
                itemEvent.setEventDesc("卖家将产品刊登价格从"+priceStr[1]);
            }else if(key.equals("changeSellerFreightPrice")){
                itemEvent.setType("changeSellerFreightPrice");
                itemEvent.setEventDesc("卖家将产品刊登运费从"+priceStr[1]);
            }else if(key.equals("changeWishFreightPrice")){
                itemEvent.setType("changeWishFreightPrice");
                itemEvent.setEventDesc("Wish将产品运费从"+priceStr[1]);
            }
            itemEventList.add(itemEvent);
        }

        return itemEventList;
    }

    /**
     * 获取标签里程碑事件
     * "onSale": "上架",
     "remove": "下架",
     "delete": "被删除",
     "audit": "被审核",
     "auditPass": "审核通过",

     "authCancle":"取消wish认证",
     "authGet":"获得wish认证",

     "changeWishPrice": "页面价格、运费调整",
     "changeSellerPrice": "卖家后台页面价格、运费调整",
     "changeOriginalPrice": "吊牌价格调整",
     "changeWishFreightPrice": "wish运费",
     "changeSellerFreightPrice": "卖家运费",

     "addCat":"新增行业"
     "deleteCat":"删除行业"

     "addWishTag": "添加wish标签",
     "deleteWishTag": "删除wish标签",
     "addCustomTag": "删除自定义标签",
     "deleteCustomTag": "删除自定义标签",

     "firstSale": "销量破零",
     "firstWishSave": "收藏量破零",
     "firstGoodRate": "获得第一个好评",
     "firstBadRate": "获得第一个差评"
     * @param cat
     * @param date
     * @return
     */
    @Override
    public List<ItemEvent> getCatItemEvent(String cat, Date date) {
        List<ItemEvent> itemEventList = new ArrayList<ItemEvent>(2);
        if(!StringUtils.isEmpty(cat)){
            if(cat.indexOf("!")>-1){
                String[] catArr = cat.split("!");
                if(catArr.length>1){
                    ItemEvent addCustomTagitemEvent = new ItemEvent();
                    addCustomTagitemEvent.setType("addCat");
                    addCustomTagitemEvent.setEventDesc("Wish将产品分配至新行业:"+catArr[1]);
                    addCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));

                    ItemEvent deleteCustomTagitemEvent = new ItemEvent();
                    deleteCustomTagitemEvent.setType("deleteCat");
                    deleteCustomTagitemEvent.setEventDesc("Wish将产品移除出行业:"+catArr[0]);
                    deleteCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    itemEventList.add(addCustomTagitemEvent);
                    itemEventList.add(deleteCustomTagitemEvent);
                }else{
                    ItemEvent addCustomTagitemEvent = new ItemEvent();
                    addCustomTagitemEvent.setType("deleteCat");
                    addCustomTagitemEvent.setEventDesc("Wish将产品移除出行业:"+catArr[0]);
                    addCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    itemEventList.add(addCustomTagitemEvent);
                }
            }else{
                ItemEvent deleteCustomTagitemEvent = new ItemEvent();
                deleteCustomTagitemEvent.setType("addCat");
                deleteCustomTagitemEvent.setEventDesc("Wish将产品分配至新行业:"+cat);
                deleteCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                itemEventList.add(deleteCustomTagitemEvent);
            }
        }
        return itemEventList;
    }

    /**
     * 获取标签里程碑事件
     *
     * @return
     */
    @Override
    public List<ItemEvent> getTagItemEvent(String tag, Date date, String divDesc) {
        List<ItemEvent> itemEventList = new ArrayList<ItemEvent>(2);
        if(!StringUtils.isEmpty(tag)){
            String divType = "CustomTag";
            if(divDesc.equals("Wish")){
                divType = "WishTag";
            }
            if(tag.indexOf("!")>-1){
                String[] customTagArr = tag.split("!");
                if(customTagArr.length>1){
                    ItemEvent addCustomTagitemEvent = new ItemEvent();
                    addCustomTagitemEvent.setType("add"+divType);
                    addCustomTagitemEvent.setEventDesc(divDesc+"新增"+divDesc+"自填标签:"+customTagArr[1]);
                    addCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));

                    ItemEvent deleteCustomTagitemEvent = new ItemEvent();
                    deleteCustomTagitemEvent.setType("delete"+divType);
                    deleteCustomTagitemEvent.setEventDesc(divDesc+"删除旧"+divDesc+"自填标签:"+customTagArr[0]);
                    deleteCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    itemEventList.add(addCustomTagitemEvent);
                    itemEventList.add(deleteCustomTagitemEvent);
                }else{
                    ItemEvent addCustomTagitemEvent = new ItemEvent();
                    addCustomTagitemEvent.setType("delete"+divType);
                    addCustomTagitemEvent.setEventDesc(divDesc+"删除旧"+divDesc+"自填标签:"+customTagArr[0]);
                    addCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    itemEventList.add(addCustomTagitemEvent);
                }
            }else{
                ItemEvent deleteCustomTagitemEvent = new ItemEvent();
                deleteCustomTagitemEvent.setType("add"+divType);
                deleteCustomTagitemEvent.setEventDesc(divDesc+"新增"+divDesc+"自填标签:"+tag);
                deleteCustomTagitemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                itemEventList.add(deleteCustomTagitemEvent);
            }
        }
        return itemEventList;
    }

    /**
     * 将卖家网里程碑类型转换为本地里程碑类型格式进行显示
     *
     * @param markList
     * @return
     */
    @Override
    public List<ItemEvent> getItemEventList(List<ItemMilestone> markList) {
        List<ItemEvent> itemEventList = new ArrayList<ItemEvent>();
        if(markList!=null){
            for(ItemMilestone mark:markList){
                JSONObject jsonObject = JSON.parseObject(mark.getJsonDesc());
                if(mark.getType().equals("changeTag")){
                    if(jsonObject.containsKey("changeWishTag")&&jsonObject.containsKey("changeCustomerTag")){
                        ItemMilestone mark1 = new ItemMilestone();
                        mark1.setType(mark.getType());
                        mark1.setParam1Old(mark.getParam1Old());
                        mark1.setParam1New(mark.getParam1New());
                        mark1.setInsertDate(mark.getInsertDate());
                        itemEventList.addAll(getTagItemEvent(getItemEventDesc(mark1),mark.getInsertDate(),"Wish"));

                        ItemMilestone mark2 = new ItemMilestone();
                        mark2.setType(mark.getType());
                        mark2.setParam1Old(mark.getParam2Old());
                        mark2.setParam1New(mark.getParam2New());
                        mark2.setInsertDate(mark.getInsertDate());
                        itemEventList.addAll(getTagItemEvent(getItemEventDesc(mark2),mark.getInsertDate(),"卖家"));
                    }else{
                        String divDesc = "Wish";
                        if(jsonObject.containsKey("changeCustomerTag")){
                            divDesc = "卖家";
                        }
                        ItemMilestone mark1 = new ItemMilestone();
                        mark1.setType(mark.getType());
                        mark1.setParam1Old(mark.getParam1Old());
                        mark1.setParam1New(mark.getParam1New());
                        mark1.setInsertDate(mark.getInsertDate());
                        itemEventList.addAll(getTagItemEvent(getItemEventDesc(mark1),mark.getInsertDate(),divDesc));
                    }
                }else if(mark.getType().equals("distributeCat")){
                    itemEventList.addAll(getCatItemEvent(getItemEventDesc(mark),mark.getInsertDate()));
                }else if(mark.getType().equals("changePrice")){
                    itemEventList.addAll(getPriceItemEvent(mark));
                }else{
                    ItemEvent itemEvent =  MappingUtil.mappingItemEvent(mark);
                    mark.setType(itemEvent.getType());
                    itemEvent.setEventDesc(getItemEventDesc(mark));
                    itemEventList.add(itemEvent);
                }

            }
        }
        return itemEventList;
    }


    /**
     * 获取里程碑描述
     *
     * @param mark
     * @return
     */
    @Override
    public String getItemEventDesc(ItemMilestone mark) {
        StringBuffer sb = new StringBuffer();
        if(mark.getType().equals("distributeCat")){//里程碑类型特殊处理
            if(mark.getParam1Old()!=null&&!mark.getParam1Old().equals("")){

                List<String> oldCats = wishCategoryService.getCategoryNames2(mark.getParam1Old());
                List<String> oldCats2 = new ArrayList<String>();oldCats2.addAll(oldCats);//备份
                List<String> newCats = wishCategoryService.getCategoryNames2(mark.getParam1New());
                List<String> newCats2 = new ArrayList<String>();newCats2.addAll(newCats);//备份
                oldCats.removeAll(newCats);
                if(!ListUtil.isBlank(oldCats)){
                    sb.append("[");
                    for(int i = 0;i<oldCats.size();i++){
                        String[] catInfo = oldCats.get(i).split("@");
                        sb.append("{\"name\":\""+catInfo[0]+"\",\"id\":\""+catInfo[1]+"\",\"level\":"+catInfo[2]+"}");
                        if(i<oldCats.size()-1){
                            sb.append(",") ;
                        }
                    }
                    sb.append("]");
                    sb.append("!");
                }
                newCats.removeAll(oldCats2);
                if(!ListUtil.isBlank(newCats)){
                    sb.append("[");
                    for(int i = 0;i<newCats.size();i++){
                        String[] catInfo = newCats.get(i).split("@");
                        sb.append("{\"name\":\""+catInfo[0]+"\",\"id\":\""+catInfo[1]+"\",\"level\":"+catInfo[2]+"}");
                        if(i<newCats.size()-1){
                            sb.append(",") ;
                        }
                    }
                    sb.append("]");
                }
            }else{
                List<String> newCats = wishCategoryService.getCategoryNames2(mark.getParam1New());
                sb.append("[");
                for(int i = 0;i<newCats.size();i++){
                    String[] catInfo = newCats.get(i).split("@");
                    sb.append("{\"name\":\""+catInfo[0]+"\",\"id\":\""+catInfo[1]+"\",\"level\":"+catInfo[2]+"}");
                    if(i<newCats.size()-1){
                        sb.append(",") ;
                    }
                }
                sb.append("]");
            }
        }else if(mark.getType().equals("changeTag") ){

            List<String> oldTags = ListUtil.strToList(mark.getParam1Old(),"\\^");
            List<String> old2Tags = new ArrayList<String>();old2Tags.addAll(oldTags);//备份
            List<String> newTags = ListUtil.strToList(mark.getParam1New(),"\\^");
            List<String> new2Tags = new ArrayList<String>();new2Tags.addAll(newTags);//备份
            oldTags.removeAll(newTags);

            if(!ListUtil.isBlank(oldTags)){
                for(int i = 0;i<oldTags.size();i++){
                    sb.append(oldTags.get(i).split("@")[1]+",");
                }
                sb.append("!");
            }

            newTags.removeAll(old2Tags);
            if(!ListUtil.isBlank(newTags)){
                for(int i = 0;i<newTags.size();i++){
                    sb.append(newTags.get(i).split("@")[1]+",");
                }
            }
        }else if(mark.getType().equals("firstSale")){//买家操作
            sb.append("已有买家购买该产品,销量破零");
        }else if(mark.getType().equals("firstWishSave")){
            sb.append("已有买家收藏该产品,收藏量破零");
        }else if(mark.getType().equals("firstGoodRate")){
            sb.append("有买家给出第一个好评");
        }else if(mark.getType().equals("firstBadRate")){
            sb.append("有买家给了第一个差评");
        }else if(mark.getType().equals("authGet")){
            sb.append("产品获得Wish认证");
        }else if(mark.getType().equals("authCancle")){
            sb.append("产品wish认证被取消");
        }else if(mark.getType().equals("audit")){
            sb.append("产品被Wish审核");
        }else if(mark.getType().equals("delete")){
            sb.append("产品被Wish删除啦");
        }else if(mark.getType().equals("remove")){
            sb.append("产品下架/售罄");
        }else if(mark.getType().equals("onSale")){
            sb.append("产品刊登成功,开始上架销售");
        }else{
            sb.append(mark.getDesc());
        }
        return sb.toString();
    }

    /**
     * 获取店铺某种里程碑类型的宝贝id列表
     *
     * @return
     */
    @Override
    public List<String> getItemIdListByEventType(String merchantId,String eventType,String eventTime,Integer pageNo,Integer pageSize){
        long startL = System.currentTimeMillis();
        List<Item> itemList = null;
        long start = System.currentTimeMillis();

        if(eventType.equals("allDistributeCat")){//所有划分行业的宝贝id
            itemList = mjItemService.queryDividedCatItemListbyMerchantId(merchantId,pageNo,pageSize);

        }else if(eventType.equals("allRefer")){//店铺内所有认证的宝贝id
            itemList = mjItemService.queryWishRecommendItemListbyMerchantId(merchantId,pageNo,pageSize);

        }else{
            itemList = mjItemService.queryItemListFromSomeTableByMerchantId(merchantId,eventType,eventTime,pageNo,pageSize);
        }


        if(!ListUtil.isBlank(itemList)){
            long end = System.currentTimeMillis();
            logger.biz("搜索类型:{},搜索个数:{},里程碑类型耗时{}ms",eventType,itemList.size(),(end-start));
            List<String> itemIdList = new ArrayList<String>();
            for(Item item : itemList){
                itemIdList.add(item.getItemId());
            }
            return itemIdList;
        }
        logger.biz("获取宝贝id耗时{}",System.currentTimeMillis()-startL);
        return null;
    }

    /**
     * 设置店长id,前往产品查看用
     * @param itemDomin
     * @param userData
     */
    @Override
    public void setSuperBossNo(ItemDomain itemDomin, UserData userData) {
        String superBossNo = null;
        try{
            superBossNo = wishDataService.isItemExist(itemDomin.getItemId(),Long.parseLong(userData.getUserId()+""),null);
            if(!StringUtils.isEmpty(superBossNo)){
                itemDomin.setSuperbossNo(superBossNo);
                itemDomin.setCollection(true);
            }else{
                itemDomin.setCollection(false);
            }
        }catch (Exception e){
            logger.error(e);
        }
    }

//    public Item getMjWishItem(String itemId,String merchantId) {
//        return mjItemService.queryItemByItemId(itemId,merchantId);
//        if(item==null){
//            Date dateD = DateUtil.getDate(date,"yyyy-MM-dd");
//            dateD = DateUtil.addDays(dateD,-1);
//            item = mjItemService.queryItemByItemId(itemId,DateUtil.getDate(dateD,"yyyy-MM-dd"));
//        }
//        if(item==null){
//            WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
//            wishItemSolrQuery.setItemId(itemId);
//            List<ItemDomain> itemDomainList = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();
//            if(ListUtil.isBlank(itemDomainList)){
//                return null;
//            }
//            item = wishItemDAO.queryWishItem(itemId,itemDomainList.get(0).getMerchantId());
//        }
//        return item;
//    }

    public Item getMjWishItemDetail(String itemId, String merchantId) {
        Item itemDetail = mjItemService.getItemAdditionalInfoByItemId(itemId,merchantId);
        if(itemDetail==null){
            itemDetail = wishItemDAO.queryWishItemDetail(itemId,merchantId);
        }
        return itemDetail;
    }

    @Override
    public List<ItemDomain> getItemListForCollect(List<String> itemIdList, List<String> merchantIdList, UserData userData) throws BizException {
        Long startL = System.currentTimeMillis();
        List<Item> itemList = mjItemService.queryItemByItemIds(itemIdList);
        if(!ListUtil.isBlank(itemList)){
            List<Item> itemDetailList = mjItemService.getItemAdditionalInfoByItemIds(itemIdList);
//            if(ListUtil.isBlank(itemDetailList)){
//                return Collections.EMPTY_LIST;
//            }
            Map<String,Item> itemMap = new HashMap<String, Item>();
            Map<String,Item> itemDetailMap = new HashMap<String, Item>();
            for(Item item : itemList){
                itemMap.put(item.getItemId(),item);
            }
            for(Item item : itemDetailList){
                itemDetailMap.put(item.getItemId(),item);
            }
            List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>(itemMap.size());
            for(String itemId:itemMap.keySet()){
                Item item = itemMap.get(itemId);
                ItemDomain itemDomain = MappingUtil.mappingItemDomain(item);
                dealItemTags(itemDomain,item);
                if(itemDetailMap.containsKey(itemId)){
                    Item itemDetail = itemDetailMap.get(itemId);
                    dealItemDetail(itemDomain,itemDetail);
                }
                itemDomainList.add(itemDomain);
            }
            logger.biz("用户:{}获取采集宝贝数据,宝贝个数{},耗时{}ms",userData.getUserId(),itemIdList.size(),System.currentTimeMillis()-startL);
            return itemDomainList;
        }else{
            logger.biz("用户:{}查询宝贝列表为空,原因:{}",userData.getUserId(),ListUtil.isBlank(itemIdList));
        }
        return Collections.EMPTY_LIST;
    }
}
