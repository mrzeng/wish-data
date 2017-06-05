package com.raycloud.overseas.erp.data.services.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.api.dubbo.service.IItemService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.pojo.SearchItem;
import com.raycloud.overseas.erp.data.common.query.SearchItemQuery;
import com.raycloud.overseas.erp.data.common.util.*;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.request.RecordRequest;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.services.api.ILocalIndustryService;
import com.raycloud.overseas.erp.data.services.api.ILocalItemService;
import com.raycloud.overseas.erp.data.services.api.IRecordService;
import com.raycloud.overseas.erp.data.services.api.SearchItemService;
import com.raycloud.overseas.erp.data.vo.RecordItemVo;
import com.raycloud.overseas.erp.platform.dubbo.api.PlatformWishApi;
import com.raycloud.overseas.erp.platform.dubbo.vo.PlatformResponse;
import com.raycloud.overseas.erp.platform.dubbo.vo.WishRequest;
import com.raycloud.overseas.usercenter.web.api.exception.AuthException;
import com.raycloud.overseas.usercenter.web.api.pojo.Shop;
import com.raycloud.overseas.usercenter.web.api.service.IShopService;
import com.raycloud.overseas.usercenter.web.api.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;


/**
 *  @Description 收录服务类,伲补爬虫漏掉的数据
 *  @Author zhanxf
 *  @Date 16/12/28 14:29
 */
@Service
public class RecordServiceImpl  implements IRecordService {

    private static Logger logger= Logger.getLogger(RecordServiceImpl.class);

    @Autowired
    private IShopService shopService;

    @Autowired
    private WishItemSolrService wishItemSolrService;

    @Autowired
    private PlatformWishApi platformWishApi;

    @Resource
    private SearchItemService searchItemService;

    @Autowired
    private com.raycloud.overseas.data.api.dubbo.service.IShopService mjShopService;

    @Autowired
    private ILocalIndustryService localIndustryService;

    @Resource
    private String productAddress;

    private String sweeper_session;//登录wish平台所需的session
    /**
     * 分页获取wish店铺宝贝列表
     *
     * @param shop
     * @param start
     * @return
     */
    @Override
    public LinkedHashMap<String, Object> getWishProductByPage(Shop shop, int start,String since) throws BizException {

        WishRequest request = new WishRequest("product/multi-get");
        request.setParam("access_token", shop.getAccessToken());
        request.setParam("start", start + "");
        request.setParam("limit", 250 + "");
        if(!StringUtils.isEmpty(since)){
            request.setParam("since", since);
        }

        PlatformResponse platformResponse = null;
        try{
            platformResponse = platformWishApi.wishGetCallApi(request);
            if (platformResponse.getResult() != 100) {
                if (platformResponse.getResult() == ExceptionCode.AUTH_ERROR) {
                    logger.error("需要重新获取token.");
                        shop.setExpireTime(null);
                        shop = shopService.ensureAuth(shop);
                        request.setParam("access_token", shop.getAccessToken());
                        platformResponse = platformWishApi.wishGetCallApi(request);
                } else {
                    logger.error("调用香港服务器wishGetapi获取商品列表出错,shopId:{},errMsg:{}", shop.getShopId(), platformResponse.getMessage());
                    throw new BizException(platformResponse.getResult(), platformResponse.getMessage());
                }
            }
            return (LinkedHashMap<String, Object>) platformResponse.getData();
        }catch (AuthException e) {
            logger.error("店铺授权出错,shopId:{}",shop.getShopId(),e);
        }catch (Exception e){
            logger.error("店铺授权出错,shopId:{}",shop.getShopId(),e);
        }

        return null;

    }

    /**
     * 收录所有爬虫未抓取到的宝贝
     * @param since 刷新时间
     */
    @Override
    public void recordUnInDBProduct(String since) {

        List<Shop> shopList = getAllShops(2);

        if(shopList.size()>0){
            if(!ListUtil.isBlank(shopList)){
                Map<Long,Shop> shopMap = new HashMap<Long, Shop>();
                for(Shop shop : shopList){
                    shopMap.put(shop.getShopId(),shop);
                }
                Set<SearchItem> searchItemSet = new HashSet<SearchItem>();
                for(Shop shop : shopList){
                    List<String> itemIdList = getUnRecordItemIdList(shop,since);
                    if(!ListUtil.isBlank(itemIdList)){
                        for(String itemId : itemIdList){
                            SearchItem searchItem = new SearchItem();
                            searchItem.setItemId(itemId);
                            searchItem.setStatus(0);
                            searchItem.setCreated(new Date());
                            searchItem.setType(2);
                            searchItem.setUserId(shop.getBindingUserId()+"");
                            searchItemSet.add(searchItem);
                        }
                    }
                }
                List<SearchItem> searchItemList = new ArrayList<SearchItem>(searchItemSet.size());
                searchItemList.addAll(searchItemSet);
                searchItemService.batchAddSearchItem(searchItemList);
                logger.biz("共有{}个宝贝未入库",searchItemList.size());
            }
        }

    }

    //@Scheduled(cron = "0 0 12 * * ?")
    public void timerRecordItemTask(){

            Date yesterday = DateUtil.yesterday();
            String since = DateUtil.getDate(yesterday,"yyyy-MM-dd");
            recordUnInDBProduct(since);

    }

    /**
     * 获取店铺的所有宝贝
     *
     * @param shop
     * @return
     */
    @Override
    public List<String> getNormalShopItemListByAccessToken(Shop shop,String since) throws BizException {

        int start = 1;
        boolean flag = true;
        List<String> itemIdList = new ArrayList<String>();
        while(flag){

            LinkedHashMap<String,Object> linkedHashMap = getWishProductByPage(shop,start,since);

            if(linkedHashMap == null){
                return itemIdList;
            }
            JSONArray dataArr = (JSONArray) linkedHashMap.get("data");

            if(dataArr.size() == 0){
                flag = false;
            }else{
                for(int i = 0;i<dataArr.size();i++){

                    JSONObject obj = dataArr.getJSONObject(i).getJSONObject("Product");
                    itemIdList.add(obj.getString("id"));

                }
                if(itemIdList.size()<250){
                    flag = false;
                }
                start+=250;
            }
        }
        return itemIdList;
    }


    /**
     * 获取大店铺的所有宝贝
     *
     * @param shop
     * @param since
     * @return
     */
    @Override
    public List<String> getLargeShopItemListByAccessToken(Shop shop, String since) {

        logger.biz("开始拉取wish宝贝,model:large,shopId:{}",shop.getShopId());

        List<String> itemIdList = new ArrayList<String>(30000);
        WishRequest request = new WishRequest("product/create-download-job");
        request.setParam("access_token", shop.getAccessToken());
        request.setParam("since", since);
        request.setParam("limit", 500 + "");
        if(!StringUtils.isEmpty(since)){
            request.setParam("since", since);
        }

        PlatformResponse platformResponse = platformWishApi.wishGetCallApi(request);
        if(platformResponse.getData() == null){
            return null;
        }
        LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap<String, Object>) platformResponse.getData();

        String jobId = (String) ((Map<String,Object>)linkedHashMap.get("data")).get("job_id");

        WishRequest jobStatusRequest = new WishRequest("product/get-download-job-status");
        jobStatusRequest.setParam("job_id", jobId);
        jobStatusRequest.setParam("since", since);
        jobStatusRequest.setParam("access_token", shop.getAccessToken());
        boolean flag = true;

        try{
            int count = 0;
            while(flag && count<=6){
                PlatformResponse jobStatusResponse = platformWishApi.wishPostCallApi(jobStatusRequest);
                LinkedHashMap<String,Object> statusData = (LinkedHashMap<String,Object>) jobStatusResponse.getData();
                Map<String,Object> statusMap = (Map<String, Object>) statusData.get("data");
                if((Integer)statusData.get("code") == 0){
                    count++;
                    String status = (String) statusMap.get("status");
                    if(status.equals("FINISHED")){
                        logger.biz("拉取wish宝贝完成,准备下载...,shopId:{}",shop.getShopId());
                        String downloadLink = (String) statusMap.get("download_link");
                        itemIdList = downWishItemIdList(downloadLink,"shop_item_"+shop.getShopId()+"_"+since);
                        flag = false;
                    }else{
                        Thread.sleep(5000);
                    }

                }else{
                    flag = false;
                    logger.error("wish宝贝拉取失败,shopId:{}",shop.getShopId());
                }
            }

            if(count>6){//若超过30秒还没处理好,直接取消
                WishRequest jobCancleRequest = new WishRequest("product/cancel-download-job.");
                jobStatusRequest.setParam("job_id", jobId);
                jobStatusRequest.setParam("access_token", shop.getAccessToken());
                platformWishApi.wishPostCallApi(jobCancleRequest);
                logger.error("店铺宝贝拉取超时,shopId:{}",shop.getShopId());
            }

        }catch (Exception e){
            logger.error(e);
        }
        return itemIdList;
    }

    @Override
    public List<String> downWishItemIdList(String url,String fileName){
        logger.biz("开始下载:"+url);
        Long start=System.currentTimeMillis();
        String download = HttpClientUtil.download(url,fileName);
        logger.biz("下载完成,耗时:"+(System.currentTimeMillis()-start) );

        start=System.currentTimeMillis();
        logger.biz("开始解析CSV文件:"+fileName);
        List<Object[]> itemList = CSVUtil.readeCSV(fileName);
        logger.biz("解析完成,共:{}条记录,耗时:{}" ,itemList == null ? 0 :itemList.size(),
                (System.currentTimeMillis()-start));


        //执行完后删除该文件
        String filepath = HttpClientUtil.getFilePath(fileName);
        File file = new File(filepath);
        if (file.exists()){
            file.delete();
        }

        if(!ListUtil.isBlank(itemList)){

            List<String> itemIdList = new ArrayList<String>(itemList.size());
            for(Object[] objects:itemList){
                String itemId = (String) objects[0];
                if(!StringUtils.isEmpty(itemId)){
                    itemIdList.add(itemId);
                }
            }
            return itemIdList;
        }
        return null;
    }


    /**
     * 获取未收录宝贝
     * @return
     */
    @Override
    public List<String> getUnRecordItemIdList(Shop shop,String since) {
        List<String> itemIdList = null;
        try{
            //普通店铺获取宝贝
            itemIdList = getNormalShopItemListByAccessToken(shop,since);
        }catch (BizException e){
            //当店铺宝贝超过25000时,不能通过第一种方式获取店铺内的宝贝
            itemIdList = getLargeShopItemListByAccessToken(shop,since);
        }catch (Exception e){
            logger.error("获取店铺宝贝失败",e);
        }
        if(!ListUtil.isBlank(itemIdList)){
            if(itemIdList.size()>2000){
                return Collections.EMPTY_LIST;
            }

            WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
            wishItemSolrQuery.setItemIdCollection(itemIdList);
            wishItemSolrQuery.setStart(0);
            wishItemSolrQuery.setRows(itemIdList.size());
            wishItemSolrQuery.setFilterField(new String[]{"itemid"});
            com.raycloud.overseas.erp.data.common.common.Result result = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery);
            List<String> solrItemIdList = result.getItems();
            if(solrItemIdList.size() >= itemIdList.size()){
                logger.biz("店铺宝贝已抓全,店铺id:{},用户id:{}",shop.getWishMerchantId(),shop.getBindingUserId());
                return Collections.EMPTY_LIST;
            }else{

                Map<String,String> findItemIdMap = new HashMap<String, String>();
                for(String itemId : solrItemIdList){
                    findItemIdMap.put(itemId,itemId);
                }
                List<String> unRecordList = new ArrayList<String>();
                for(String itemId : itemIdList){
                    if(!findItemIdMap.containsKey(itemId)){
                        unRecordList.add(itemId);
                    }
                }
                logger.biz("店铺未抓全,待收录宝贝个数:{},solr宝贝个数:{},实际宝贝个数:{},店铺id:{},用户id:{},",unRecordList.size(),solrItemIdList.size(),itemIdList.size(),shop.getWishMerchantId(),shop.getBindingUserId());
                return unRecordList;
            }
        }else{
            logger.biz("暂未查到宝贝,店铺id:{}",shop.getWishMerchantId());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Shop> getAllShops(int platformType) {
        int pageNo = 1;
        int pageSize = 100;

        Result<Shop> wishShopResult = new Result<Shop>();
        List<Shop> shopList = new ArrayList<Shop>();
        boolean flag = true;
        while(flag){
            wishShopResult = shopService.getShopListByPage(platformType,pageNo++,pageSize);
            if(wishShopResult == null){
                flag = false;
            }else{
                List<Shop> pageShopList = wishShopResult.getList();
                if(pageShopList == null){
                    flag = false;
                }else{
                    shopList.addAll(pageShopList);
                    if(pageShopList.size()<pageSize){
                        flag = false;
                    }
                }
            }
        }
        logger.biz("总店铺数:{}",shopList.size());
        return shopList;
    }

    @Override
    public void recordUnExistMerchant(String shopId, String accessToken,String userId) throws BizException {
        List<JSONObject> remoteProductList = null;
        try {
            logger.biz("用户{},店铺{},准备进行收录校验..",userId,shopId);
            //获取wish该店铺所有宝贝信息
            remoteProductList = getAllProduct(shopId,userId);
        } catch (BizException e) {
            if (e.getErrorCode() == ExceptionCode.AUTH_ERROR) {
                logger.biz("Wish商铺授权过期，重新授权,ShopId:{}", shopId);
            }
        }
        if(!ListUtil.isBlank(remoteProductList)){
            for(int i = 1;i<=remoteProductList.size();i++){

                JSONObject itemInfo = remoteProductList.get(i-1);
                String itemId= itemInfo.getJSONObject("Product").getString("id");
                RecordRequest request = new RecordRequest();
                request.setItemId(itemId);
                request.setUserId(Integer.parseInt(userId));
                //根据宝贝id获取wish上该店铺信息
                RecordItemVo recordItemVo = getRecordResponse(request);

                //该店铺已经存在于系统中,收录结束
                if(recordItemVo.getStatus() == 1){
                    logger.biz("店铺{}已经存在于系统中,收录结束",recordItemVo.getMerchantName());
                    break;
                }else if(recordItemVo.getStatus() == 0){
                    SearchItemQuery searchItemQuery = new SearchItemQuery();
                    searchItemQuery.setMerchantName(recordItemVo.getMerchantName());
                    List<SearchItem> searchList = searchItemService.getSearchItemList(searchItemQuery);
                    //若该店铺还未被收录
                    if(ListUtil.isBlank(searchList)){
                        SearchItem searchItem = new SearchItem();
                        searchItem.setItemId(null);
                        searchItem.setMerchantName(recordItemVo.getMerchantName());
                        searchItem.setStatus(0);
                        searchItem.setCreated(new Date());
                        searchItem.setType(1);
                        searchItem.setUserId(userId);
                        searchItemService.addSearchItem(searchItem);
                        logger.biz("收录店铺,店铺名:{},用户id:{}",recordItemVo.getMerchantName(),userId);
                    }
                    break;
                }
            }

        }
    }

    /**
     * 检查wish是否有该产品id对应的产品
     *
     * @param merchantName
     * @return
     */
    @Override
    public RecordItemVo checkWishMerchantExist(String merchantName) throws BizException {
        String respContent = requestWishMerchant(merchantName);

        JSONObject respJSON = JSON.parseObject(respContent);
        if(0 != respJSON.getInteger("code")){//session失效,重新获取
            resetSweeperSession();
            respContent = requestWishMerchant(merchantName);
        }

        respJSON = JSON.parseObject(respContent);
        RecordItemVo recordItemVo = new RecordItemVo();
        if(respJSON.getString("msg").equalsIgnoreCase("")&&respJSON.getInteger("code") == 0){
            recordItemVo.setStatus(0);
            JSONObject merchantInfo = respJSON.getJSONObject("data").getJSONObject("merchant_info");
            recordItemVo.setMerchantId(merchantInfo.getString("id"));
            recordItemVo.setMerchantName(merchantInfo.getString("name"));
            recordItemVo.setMerchantNick(merchantInfo.getString("display_name"));
            recordItemVo.setMerchantLogoUrl(merchantInfo.getString("display_pic"));
        }else{
            recordItemVo.setMerchantName(merchantName);
            recordItemVo.setStatus(2);//未找到该产品
        }
        return recordItemVo;
    }


    /**
     * 调用wish产品api获取产品信息
     * @param itemId
     * @return
     * @throws Exception
     */
    private String requestWishItem(String itemId) throws BizException {
        String content = null;
        try{
            String wishUrl = "https://www.wish.com/api/product/get";
            Map<String,String> head = new HashMap<String, String>();
            head.put("Content-Type","application/x-www-form-urlencoded");
            head.put("Accept","*/*");
            head.put("Cookie","_xsrf=1; _appLocale=zh-Hans;sweeper_session="+sweeper_session);
            head.put("User-Agent","Wish/3.14.0 (iPhone; iOS 8.3; Scale/2.00)");
            head.put("Accept-Language","zh-Hans;q=1");
            String data = "_app_type=wish&_capabilities[]=11&_capabilities[]=12&_capabilities[]=13&" +
                    "_capabilities[]=15&_capabilities[]=2&_capabilities[]=21&_capabilities[]=24&_capabilities[]=25&" +
                    "_capabilities[]=4&_capabilities[]=6&_capabilities[]=7&_capabilities[]=8&_capabilities[]=9&" +
                    "_client=iosapp&_version=3.14.0&_xsrf=1&advertiser_id=69DD46F7-056C-47B9-A9C9-59D54786D9E3&" +
                    "app_device_id=b1b92acf752565174c8636c1c6348a4287c75fac&cid="+itemId;
            long startL = System.currentTimeMillis();
            content = HttpClientUtil.sendPostRequest(wishUrl,head,data,"UTF-8","UTF-8");
            logger.biz("requestWishItem接口,参数:{}耗时{}ms",itemId,(System.currentTimeMillis()-startL));
        }catch (Exception e){
            logger.error("获取wish产品"+itemId+"异常",e);
            BizException bizException = new BizException("获取wish产品"+itemId+"异常",e);
            bizException.setErrorCode(ExceptionCode.SERVICE_ERROR);
            throw bizException;
        }

        return content;
    }

    /**
     * 调用wish店铺api获取店铺信息
     * @param merchantName
     * @return
     * @throws Exception
     */
    private String requestWishMerchant(String merchantName) throws BizException {
        String content = null;
        try{
            String wishUrl = "https://www.wish.com/api/merchant";
            Map<String,String> head = new HashMap<String, String>();
            head.put("Content-Type","application/x-www-form-urlencoded");
            head.put("Accept","*/*");
            head.put("Cookie","_xsrf=1; _appLocale=zh-Hans;sweeper_session="+sweeper_session);
            head.put("User-Agent","Wish/3.14.0 (iPhone; iOS 8.3; Scale/2.00)");
            head.put("Accept-Language","zh-Hans;q=1");
            String data = "_app_type=wish&_capabilities[]=11&_capabilities[]=12&_capabilities[]=13&" +
                    "_capabilities[]=15&_capabilities[]=2&_capabilities[]=21&_capabilities[]=24&" +
                    "_capabilities[]=25&_capabilities[]=4&_capabilities[]=6&_capabilities[]=7&" +
                    "_capabilities[]=8&_capabilities[]=9&_client=iosapp&_version=3.14.0&_xsrf=1&" +
                    "advertiser_id=69DD46F7-056C-47B9-A9C9-59D54786D9E3&app_device_id=b1b92acf752565174c8636c1c6348a4287c75fac&count=30&" +
                    "query="+merchantName+"&start=0";

            long startL = System.currentTimeMillis();
            content = HttpClientUtil.sendPostRequest(wishUrl,head,data,"UTF-8","UTF-8");
            logger.biz("requestWishMerchant接口,参数:{}耗时{}ms",merchantName,(System.currentTimeMillis()-startL));
        }catch (Exception e){
            BizException bizException = new BizException("获取wish店铺"+merchantName+"异常",e);
            bizException.setErrorCode(ExceptionCode.SERVICE_ERROR);
            throw bizException;
        }

        return content;
    }

    /**
     * 重置wish session(session过期调用)
     * @return
     */
    private void resetSweeperSession(){
        long startL = System.currentTimeMillis();
        String respContent = HttpClientUtil.sendGetRequest("http://123.59.78.16:8888/wish.json");
        logger.biz("resetSweeperSession耗时{}ms",(System.currentTimeMillis()-startL));
        JSONArray jsonArray = JSONArray.parseArray(respContent);
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            if(jsonObject.get("name").equals("sweeper_session")){
                this.sweeper_session = (String) jsonObject.get("value");
                break;
            }
        }
    }

    /**
     * 检查wish是否有该产品id对应的产品
     *
     * @param itemId
     * @return
     */
    @Override
    public RecordItemVo checkWishItemExist(String itemId) throws BizException {

        String respContent = requestWishItem(itemId);

        JSONObject respJSON = JSON.parseObject(respContent);
        if(0 != respJSON.getInteger("code")){//session失效,重新获取
            resetSweeperSession();
            respContent = requestWishItem(itemId);
        }
        respJSON = JSON.parseObject(respContent);
        RecordItemVo recordItemVo = new RecordItemVo();
        if(respJSON.getString("msg").equalsIgnoreCase("")&&respJSON.getInteger("code") == 0){
            recordItemVo.setStatus(0);
            JSONArray variations = respJSON.getJSONObject("data").getJSONObject("contest").getJSONObject("commerce_product_info").getJSONArray("variations");
            JSONObject variationObj = (JSONObject) variations.get(0);
            recordItemVo.setMerchantId(variationObj.getString("merchant_id"));
            recordItemVo.setMerchantName(variationObj.getString("merchant_name"));
            recordItemVo.setItemId(itemId);
            recordItemVo.setItemName(respJSON.getJSONObject("data").getJSONObject("contest").getString("name"));
            recordItemVo.setMerchantNick(variationObj.getString("merchant"));
            recordItemVo.setItemLogoUrl("https://contestimg.wish.com/api/webimage/"+itemId+"-small.jpg");
        }else if(respJSON.getInteger("code") == 12){
            recordItemVo.setItemId(itemId);
            recordItemVo.setStatus(2);
        }else{
            recordItemVo.setItemId(itemId);
            recordItemVo.setStatus(2);//未找到该产品
        }
        return recordItemVo;
    }


    /**
     * 获取收录产品响应
     *
     * @param request
     * @return
     */
    @Override
    public RecordItemVo getRecordResponse(RecordRequest request) throws BizException {

        RecordItemVo recordItemVo = new RecordItemVo();
        ItemDomain item = null;
        try{
            WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
            wishItemSolrQuery.setItemId(request.getItemId());
            List<ItemDomain> itemDomainList = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();
            if(!ListUtil.isBlank(itemDomainList)){
                item = itemDomainList.get(0);
            }
        }catch (Exception e){
            logger.biz("产品id"+request.getItemId()+"不存在");
        }
        if(item!=null){//该产品已经存在于系统中
            try{
                com.raycloud.overseas.data.commom.domain.wish.domain.Shop shop = mjShopService.queryShopInfoById(item.getMerchantId());
                recordItemVo.setMerchantName(shop.getMerchantName());
                recordItemVo.setMerchantId(shop.getMerchantId());
                recordItemVo.setMerchantNick(shop.getMerchantNick());
                recordItemVo.setItemName(item.getItemName());
                recordItemVo.setItemLogoUrl("https://contestimg.wish.com/api/webimage/"+item.getItemId()+"-small.jpg");
                recordItemVo.setStatus(1);
            }catch (Exception e){
                logger.biz("产品id"+request.getItemId()+",的店铺信息不存在");
                recordItemVo.setStatus(2);
            }
        }else{
            try{
                recordItemVo = checkWishItemExist(request.getItemId());
            }catch (Exception e){
                BizException bizException = new BizException("wish数据获取异常",e);
                bizException.setErrorCode(ExceptionCode.SYS_ERROR_MQ5);
                throw bizException;
            }

            SearchItemQuery searchItemQuery = new SearchItemQuery();
            searchItemQuery.setItemId(request.getItemId());
            List<SearchItem> searchItemList = searchItemService.getSearchItemList(searchItemQuery);
            if( ListUtil.isBlank(searchItemList)){
                SearchItem searchItem = new SearchItem();
                searchItem.setItemId(request.getItemId());
                searchItem.setMerchantName(recordItemVo.getMerchantName());
                searchItem.setStatus(recordItemVo.getStatus());
                searchItem.setCreated(new Date());
                searchItem.setType(2);
                searchItem.setUserId(request.getUserId()+"");
                logger.biz("收录宝贝,宝贝id:{},用户id:{}",request.getItemId(),request.getUserId());
                searchItemService.addSearchItem(searchItem);
            }
        }

        return recordItemVo;
    }

    @Override
    public void recordMerchant(final String shopId, final String accessToken, final String userId) throws BizException {
        DataUtil.cachedThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    recordUnExistMerchant(shopId,accessToken,userId);
                } catch (BizException e) {
                    logger.error("收录店铺失败",e);
                }

            }
        });
    }



    public List<JSONObject> getAllProduct(String shopId,String userId) throws BizException {

        String url = productAddress+"/product/dataCollect/getWishProducts.json";
        Map<String,String> param = new HashMap<String, String>();
        param.put("shopId",shopId);
        param.put("userId",userId);
        try {
            String content = HttpClientUtil.sendPostRequest(url,param,"utf-8","utf-8");
            JSONObject resp = JSONObject.parseObject(content);
            if(resp!=null){
                List<JSONObject> list = (List<JSONObject>) resp.getJSONObject("data").get("products");
                return list;
            }
        } catch (Exception e) {
            BizException bizException = new BizException("请求wish产品异常",e);
            bizException.setErrorCode(ExceptionCode.SERVICE_ERROR);
            throw bizException;
        }

        return null;

    }


}
