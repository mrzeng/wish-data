package com.raycloud.overseas.erp.data.web.controller;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;


import com.raycloud.overseas.erp.data.common.pojo.DataMarkInfo;
import com.raycloud.overseas.erp.data.common.pojo.FocusItem;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.common.query.FocusItemQuery;
import com.raycloud.overseas.erp.data.common.util.HttpClientUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.request.FocusItemListRequest;
import com.raycloud.overseas.erp.data.request.ItemChartDataRequest;
import com.raycloud.overseas.erp.data.request.ItemDetailRequest;
import com.raycloud.overseas.erp.data.services.api.CommonService;
import com.raycloud.overseas.erp.data.services.api.ILocalItemService;
import com.raycloud.overseas.erp.data.services.context.CollectContext;
import com.raycloud.overseas.erp.data.services.ons.OnsUtils;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;

import com.raycloud.overseas.erp.services.product.wish.IWishDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/data/data/item")
public class ItemController extends BaseController {

    private static Logger logger= Logger.getLogger(ItemController.class);

    @Autowired
    private ILocalItemService localItemService;

    @Resource
    CommonService commonService;

    @Autowired
    private OnsUtils onsUtils;

    @Autowired
    private IWishDataService wishDataService;

    /**
     * 获取关注产品列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public Response getFocusItemList(FocusItemListRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        request.setUserData(getUser());
        if(request.validate(response)){
            try{
                response.setData(localItemService.getFocusItemList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }



    /**
     * 获取产品详情
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response getItemDetail(ItemDetailRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        UserData userData = getUser();
        request.setUserData(userData);
        if(request.validate(response)){
            try{
                response.setData(localItemService.getItemDetail(request));
            }catch (BizException e) {
                response.setResult(e.getErrorCode());
                response.setMessage(e.getErrorMsg());
                response.setRedirect("http://overseas.superseller.cn/index.html#/data/item_me/?pageNo=1");
                return response;
            }catch (RpcException e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            logger.error("参数验证失败"+ JSON.toJSONString(response));
        }
        return response;
    }

    /**
     * 获取产品经营数据
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/chart")
    public Response getItemChart(ItemChartDataRequest request) {
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        if(request.validate(response)){
            try{

//                if(org.springframework.util.StringUtils.isEmpty(request.getMerchantId())){
//                    request.setMerchantId(localItemService.getMerchantIdByItem(request.getItemId()));
//                }
                response.setData(localItemService.getItemChart(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            logger.error("参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }


    @ResponseBody
    @RequestMapping(value = "/itemEventList")
    public Response getItemEventList(String itemIdList){

        Response response = new Response();
        if(!StringUtils.isEmpty(itemIdList)){
            List<String> _itemIdList = ListUtil.strToList(itemIdList,",");
            try{
                List<List<String>> miList = DataUtil.splitItemIdAndMerchantId(_itemIdList);
//                if(itemIdList.length()<=25){//兼容工作台老的数据格式
//                    miList = new ArrayList<List<String>>();
//                    List<String> itemList = new ArrayList<String>();
//                    List<String> merchantList = new ArrayList<String>();
//                    itemList.add(itemIdList);
//                    merchantList.add(localItemService.getMerchantIdByItem(itemIdList));
//                    miList.add(itemList);
//                    miList.add(merchantList);
//                }else{
//
//                }

                response.setData(localItemService.getItemsEventList(miList.get(0),miList.get(1)));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(_itemIdList),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("参数验证失败"+ JSON.toJSONString(response));
        }


        return response;


    }

    /**
     * 采集到本地
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public Response saveItem(String itemId,String merchantId){
        Response response = new Response();
        UserData userData = getUser();
        if(!StringUtils.isEmpty(itemId)){
            try{
                localItemService.collectToLocalByFocus(itemId,merchantId,userData);
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("采集失败");
                logger.error("采集失败",e);
            }
        }else{
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("宝贝id不能为空");
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/fetchMerchantId",method = RequestMethod.GET)
    public Response fetchMerchantId(String itemId){
        Response response = new Response();
        if(!StringUtils.isEmpty(itemId)){
            try{
                response.setData(localItemService.getMerchantIdByItem(itemId));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("采集失败");
                logger.error("采集失败",e);
            }
        }else{
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("宝贝id不能为空");
        }
        return response;
    }

    /**
     * 采集推送宝贝
     * entry SEARCH_COLLECT
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/collectItems",method = RequestMethod.POST)
    public Response collectItems(String fc,String ufc,String fuc,String ufuc) {
        Response response = new Response();
        response.setApi_name("");
        final UserData userData = getUser();
        response.setData("success");
        if(commonService.checkSlowOpIsRunning(DataConstant.ASYN_ITEM_COLLECT,userData)){
            return response;
        }
        final List<String> fcList = ListUtil.strToList(fc,",");
        final List<String> ufcList = ListUtil.strToList(ufc,",");
        final List<String> ufucList = ListUtil.strToList(ufuc,",");
        final List<String> fucList = ListUtil.strToList(fuc,",");

        List<String> list = new ArrayList<String>();
        int sum = 0;

        if(!ListUtil.isBlank(ufucList)){
            sum+=ufucList.size();
            list.addAll(ufucList);
        }
        if(!ListUtil.isBlank(fucList)){
            sum+=fucList.size();
            list.addAll(fucList);
        }
        if(!ListUtil.isBlank(ufcList)){
            list.addAll(ufcList);
        }
        int len = ListUtil.countListLen(fcList,ufcList,ufucList,fucList);
        final boolean needBean = len>1;
        if(len==0){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("采集宝贝不能为空");
            return response;
        }
        try {
            final List<List<String>> miList = DataUtil.splitItemIdAndMerchantId(list);
            Map<String,String> map = wishDataService.checkItems(miList.get(0),userData.getLongFounderId());
            final int preconsume = sum-map.size();
            if(needBean){
                commonService.preConsumeBeans(DataConstant.ASYN_ITEM_COLLECT,userData,preconsume);
            }
            commonService.setSynProcess(0,DataConstant.ASYN_ITEM_COLLECT,userData,null);
            DataUtil.cachedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    localItemService.collectToLocalByEntry(miList.get(0),miList.get(1),needBean,userData);
                    try {
                        if(needBean){
                            commonService.afterConsumeBeans(DataConstant.ASYN_ITEM_COLLECT,userData,preconsume);
                        }
                    } catch (BizException e) {
                        logger.error("消费出错",e);
                    }
                }
            });
        } catch (BizException e1) {
            if(e1.getErrorCode()==ExceptionCode.SYS_ERROR_MQ8){
                response.setResult(100);
                response.setData(e1.getErrorMsg());
            }else{
                response.setResult(e1.getErrorCode());
                response.setMessage(e1.getErrorMsg());
                logger.error("批量采集失败",e1);
            }
        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("批量采集失败");
            logger.error("批量采集失败",e);
        }

        return response;
    }

//    /**
//     * 查询产品相关热销产品
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/queryRelatedHotItems")
//    public Response queryRelatedHotItems(String itemId, Integer days, String sortField, Integer pageNo, Integer pageSize){
//        Response response = new Response();
//        if(StringUtils.isEmpty(itemId)){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("产品id不能为空");
//        }
//
//        if(days == null){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("天数不能为空");
//        }
//
//        try{
//            response.setData(localItemService.queryRelatedHotItems(itemId,days,sortField,pageNo,pageSize));
//        }catch (Exception e) {
//            response.setResult(ExceptionCode.NULL_ERROR);
//            response.setMessage("系统繁忙");
//            logger.error("请求宝贝id:"+ itemId,e);
//        }
//
//        return response;
//    }
//
//
//    /**
//     * 查询某产品相关的飙升产品
//     * @param itemId 产品id
//     * @param days 7:按近7天销量查询，30:按近30天销量查询
//     * @param sortField 排序字段 amount(销量)/price(销售额)/amountDiff(近7天与上一个7天销量差额)，默认倒序排序，不传则无排序规则
//     * @param pageNo 页码
//     * @param pageSize 每页数据条数
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/queryItemRelatedIndustrySurgeItemList")
//    public Response queryItemRelatedIndustrySurgeItemList(String itemId, Integer days, String sortField, Integer pageNo, Integer pageSize){
//        Response response = new Response();
//        if(StringUtils.isEmpty(itemId)){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("产品id不能为空");
//        }
//
//        if(days == null){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("天数不能为空");
//        }
//
//        try{
//            response.setData(localItemService.queryItemRelatedIndustrySurgeItemList(itemId,days,sortField,pageNo,pageSize));
//        }catch (Exception e) {
//            response.setResult(ExceptionCode.NULL_ERROR);
//            response.setMessage("系统繁忙");
//            logger.error("请求宝贝id:"+ itemId,e);
//        }
//
//        return response;
//    }
//
//    /**
//     * 查询某产品相关的标签，按标签热度倒序排序
//     * 这里的业务逻辑是产品定的：查询产品对应level最高行业的热销产品的所有标签
//     * @param itemId 产品id
//     * @param tagType 标签类型：0-wish推荐标签，1-卖家自定义标签，注意：目前只有卖家自定义标签数据！
//     * @param pageNo 页码
//     * @param pageSize 每页数据条数
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/queryItemRelatedIndustryHotItemTagList")
//    public Response queryItemRelatedIndustryHotItemTagList(String itemId, Integer tagType, Integer pageNo, Integer pageSize){
//        Response response = new Response();
//        if(StringUtils.isEmpty(itemId)){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("产品id不能为空");
//        }
//
//        if(tagType == null){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("标签类型不能为空");
//        }
//
//        try{
//            response.setData(localItemService.queryItemRelatedIndustryHotItemTagList(itemId,tagType,pageNo,pageSize));
//        }catch (Exception e) {
//            response.setResult(ExceptionCode.NULL_ERROR);
//            response.setMessage("系统繁忙");
//            logger.error("请求宝贝id:"+ itemId,e);
//        }
//        return response;
//    }

    @ResponseBody
    @RequestMapping(value = "/noticeTest")
    public Response noticeTest(){
        Response response = new Response();
        UserData userData = getUser();
        try{
            onsUtils.sendOns(DataConstant.COLLECT_FINISH_NOTICE,"hello",userData);
            System.out.println("=========");
        }catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("系统繁忙");
        }
        return response;
    }

}
