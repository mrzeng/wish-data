package com.raycloud.overseas.erp.data.web.controller;


import com.alibaba.fastjson.JSON;
import com.raycloud.bizlogger.Logger;


import com.raycloud.overseas.erp.data.common.pojo.UserCommonCat;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.services.api.*;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/data/data/industry")
public class IndustryController extends BaseController {

    private static Logger logger= Logger.getLogger(IndustryController.class);

    @Autowired
    private ILocalIndustryService localIndustryService;

    @Autowired
    private SearchItemService searchItemService;

    @Autowired
    private IRecordService recordService;

    @Autowired
    private WishCategoryService wishCategoryService;

    /****************************************基础数据***********************************************/

    /**
     * 获取后端数据最新更新时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/maxDate")
    public Response getMaxDate(Integer type){
        Response response = new Response();
        response.setApi_name("");
        try {
            if(type == null){
                response.setMessage("类型不能为空");
                response.setResult(ExceptionCode.PARAMETER_ERROE);
                return response;
            }
            response.setData(localIndustryService.getDataRefreshTime(type.intValue()));
        } catch (Exception e) {
            logger.error("数据最新时间接口调用异常",e);
            response.setResult(ExceptionCode.SYS_ERROR_MAIJIA);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
        }
        return response;
    }

    /**
     * 收录店铺或产品
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recordItem")
    public Response recordSearch(RecordRequest request) {
        Response response = new Response();
        response.setApi_name("");
        UserData userAccount = getUser();
        request.setUserId(Integer.parseInt(userAccount.getUserId()+""));
        if(request.validate(response)){
            try {
                logger.biz("用户:{}进行宝贝收录操作",userAccount.getUserId());
                response.setData(recordService.getRecordResponse(request));
            } catch (BizException e) {
                response.setResult(e.getErrorCode());
                response.setMessage(e.getMessage());
                logger.error("用户id:"+userAccount.getUserId()+"请求参数:"+ JSON.toJSONString(request),e);
            } catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("用户id:"+userAccount.getUserId()+"请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("recordSearch参数验证失败"+ JSON.toJSONString(response));
        }
        return response;
    }

    /****************************************行业基本数据***********************************************/

    /**
     * 行业分析详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response getIndustryDetail(String id,Integer level){
        Response response = new Response();
        UserData userData = getUser();
        try{
            response.setData(localIndustryService.getIndustryDetail(id,level));
        }catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MAIJIA);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("获取行业详情异常,id:"+id+",用户id:"+userData.getUserId(),e);
        }
        return response;
    }

    /**
     * 获取行业趋势echart图
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/trend")
    public Response getIndustryTrend(String id,String startTime,String endTime){
        Response response = new Response();
        UserData userData = getUser();
        try{
            response.setData(localIndustryService.getIndustryTrend(id,startTime,endTime));
        }catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("获取行业趋势异常,id:"+id+",用户id:"+userData.getUserId(),e);
        }

        return response;
    }

    /**
     * 获取子行业分析数据图表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/subCatChart")
    public Response getSubCatChart(String id,String startTime,String endTime,TraceOrOrder traceOrOrder){
        Response response = new Response();
        UserData userData = getUser();
            try{
                response.setData(localIndustryService.getSubCatChart(id,startTime,endTime,traceOrOrder));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("获取行业趋势异常,id:"+id+",用户id:"+userData.getUserId(),e);
            }

        return response;
    }

    /**
     * 获取子行业分析数据图表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allCat")
    public Response getAllCat()  {
        Response response = new Response();
        response.setApi_name("");
        response.setData(localIndustryService.getAllCat());
        return response;
    }

    /**
     * 行业分析详情
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/defaultCat")
    public Response getDefaultCat() {
        Response response = new Response();
        response.setApi_name("");
        response.setData(localIndustryService.getDefaultCategory());
        return response;
    }


    /**
     * 获取关注店铺列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveCommonCat")
    public Response saveCommonCat(String catId,int catIndex){
        Response response = new Response();
        if(StringUtils.isBlank(catId)){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("行业ID不能为空");
        }

        Map<String,WishCategory> wishCategoryMap = wishCategoryService.getWishCategorMap();
        WishCategory wishCategory = wishCategoryMap.get(catId);
        if(wishCategory == null){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("无效行业");
        }

        try{
            UserData userAccount = getUser();
            UserCommonCat userCommonCat = new UserCommonCat();
            userCommonCat.setUserId(userAccount.getUserId());
            userCommonCat.setCatId(catId);
            String catName = wishCategoryService.getCategoryNames(wishCategory.getCatId());
            userCommonCat.setCatName(catName);
            userCommonCat.setType(1);
            userCommonCat.setCatIndex(catIndex);
            boolean result = localIndustryService.insertCommonCat(userCommonCat);
            if(!result){
                response.setMessage("该行业已添加");
                response.setResult(ExceptionCode.SERVICE_ERROR);
            }
            logger.biz("用户{}添加常用行业{}",userAccount.getUserId(),catName);
        }catch (Exception e){
            response.setResult(ExceptionCode.SERVICE_ERROR);
            response.setMessage("添加失败");
            logger.error("添加行业失败",e);
        }

        return response;
    }

    /**
     * 查询用户常用行业
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryCommonCat")
    public Response queryCommonCat(){
        Response response = new Response();

        try{
            response.setData(localIndustryService.queryCommonCat(getUser().getUserId(),1));
        }catch (Exception e){
            response.setResult(ExceptionCode.SERVICE_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("QUERY_FAIL",e);
        }

        return response;
    }

    /****************************************行业产品店铺列表***********************************************/


//    /**
//     * 获取子行业分析数据图表
//     * @param request
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/guessMerchant")
//    public Response getGuessMerchantList(GuessMerchantListRequest request){
//        Response response = new Response();
//        response.setApi_name("");
//        request.setUserId(Integer.parseInt(getUser().getUserId()+""));
//        if(request.validate(response)){
//            try{
//                response.setData(dataService.getGuessMerchantList(request));
//            }catch (Exception e){
//                response.setResult(ExceptionCode.NULL_ERROR);
//                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
//            }
//        }else{
//            logger.error("getGuessMerchantList参数验证失败"+ JSON.toJSONString(response));
//        }
//        return response;
//    }


//
//    /**
//     * 获取子行业分析数据图表
//     * @param request
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/guessItem")
//    public Response getGuessItemList(GuessItemListRequest request){
//        Response response = new Response();
//        response.setApi_name("");
//        request.setUserId(Integer.parseInt(getUser().getUserId()+""));
//        if(request.validate(response)){
//            try{
//                response.setData(dataService.getGuessItemList(request));
//            }catch (Exception e){
//                response.setResult(ExceptionCode.NULL_ERROR);
//                response.setMessage("未知异常");
//            }
//        }else{
//            logger.error("getGuessItemList参数验证失败"+ JSON.toJSONString(response));
//        }
//        return response;
//    }

    /**
     * 销量增长产品列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/amountGrowthItemList")
    public Response getAmountGrowthItemList(AmountGrowthItemRequest request){
        Response response = new Response();
        response.setApi_name("");

        if(request.validate(response)){
            try{
                request.setMaxInsertDate(localIndustryService.getDataRefreshTime(DataUtil.ITEM_MAX_DATE));
                response.setData(localIndustryService.getAmountGrowthItemList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }

        }else{
            logger.error("getAmountGrowthItemList参数验证失败"+ JSON.toJSONString(response));
        }
        return response;
    }

    /**
     * 销量增长产品列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/newItemList")
    public Response getNewItemList(NewItemRequest request){
        Response response = new Response();
        response.setApi_name("");

        if(request.validate(response)){
            try{
                request.setMaxInsertDate(localIndustryService.getDataRefreshTime(DataUtil.ITEM_MAX_DATE));
                response.setData(localIndustryService.getNewItemList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }

        }else{
            logger.error("getNewItemList参数验证失败"+ JSON.toJSONString(response));
        }
        return response;
    }

    /**
     * 高级搜索
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/search")
    public Response getSearchList(SearchListRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());

        try {
            Long startL = System.currentTimeMillis();
            UserData userAccount = getUser();
            request.setUserId(Integer.parseInt(userAccount.getUserId()+""));
            request.setMaxInsertDate(localIndustryService.getDataRefreshTime(DataUtil.ITEM_MAX_DATE)+" 00:00:00");
            response.setData(searchItemService.search(request));
            logger.biz("用户id:{},搜索类型:{},耗时{}ms",request.getUserId(),request.getType()==1?"店铺":"宝贝",(System.currentTimeMillis()-startL));
        } catch (BizException e){
            response.setResult(e.getErrorCode());
            response.setMessage(e.getMessage());
            logger.error("请求参数:"+ JSON.toJSONString(request),e);
        }catch (Exception e){
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("请求参数:"+ JSON.toJSONString(request),e);
        }


        return response;
    }

    /**
     * 获取明星店铺或明星产品列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/star")
    public Response getStar(StarRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        request.setUserId(getUser().getUserId()+"");
        if(request.validate(response)){
            try {

                if(request.getType() == 1){
                    localIndustryService.getStarItemList(request.getId(),request.getTraceOrOrder(),request.getSortType(),request.getPageNo(),request.getPageSize());
                }else{
                    localIndustryService.getStarMerchantList(request.getId(),request.getTraceOrOrder(),request.getSortType(),request.getPageNo(),request.getPageSize());
                }

                response.setData(localIndustryService.star(request));
            } catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);

            }
        }else{
            logger.error("getStar参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    /**
     * 通知更新宝贝更新
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notifyItemUpdate")
    public Response notifyItemUpdate(StarRequest request) {
        Response response = new Response();
        response.setResult(200);
        try {
//            DataUtil.timerService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    localIndustryService.guessItemDataChangeTask();
//                }
//            });

        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("请求参数:"+ JSON.toJSONString(request),e);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/notifyMerchantUpdate")
    public Response notifyMerchantUpdate(){
        Response response = new Response();
        response.setResult(200);
        try {
//            DataUtil.timerService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    localIndustryService.guessMerchantDataChangeTask();
//                }
//            });

        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("notifyMerchantUpdate未知异常",e);
        }
        return response;
    }

    /**********************************后门应对突发情况***************************************************/
    /**
     * 通知更新宝贝更新
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/productGuessItem")
    public Response productGuessItem(){
        Response response = new Response();
        response.setResult(200);
        try {
//            DataUtil.timerService.submit(new Runnable() {
//                @Override
//                public void run() {
////                            localIndustryService.guessItem();
//                            baseDataService.makeWishCatVMId();
//                }
//            });

        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("productGuessItem未知异常",e);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/productGuessMerchant")
    public Response productGuessMerchant(){
        Response response = new Response();
        response.setResult(200);
        try {
//            DataUtil.timerService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    localMerchantService.initFcousData();
//                }
//            });

        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("数据获取异常，请联系我们的在线技术支持。");
            logger.error("productGuessMerchant未知异常",e);
        }
        return response;
    }
}
