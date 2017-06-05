package com.raycloud.overseas.erp.data.web.controller;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.data.common.pojo.MerchantDomain;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.services.api.CommonService;
import com.raycloud.overseas.erp.data.services.api.ILocalIndustryService;
import com.raycloud.overseas.erp.data.services.api.ILocalItemService;
import com.raycloud.overseas.erp.data.services.api.ILocalMerchantService;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping("/data/data/merchant")
public class MerchantController extends BaseController {

    private static Logger logger= Logger.getLogger(MerchantController.class);

    @Autowired
    private ILocalMerchantService localMerchantService;

    @Autowired
    private ILocalItemService localItemService;

    @Autowired
    private ILocalIndustryService localIndustryService;

    @Autowired
    private CommonService commonService;


    /**
     * 获取关注店铺列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public Response getFocusMerchantList(FocusMerchantListRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        request.setUserData(getUser());
        if(request.validate(response)){
            try{
                response.setData(localMerchantService.getFocusMerchantList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("getFocusMerchantList异常,请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getFocusMerchantList参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    /**
     * 获取关注店铺列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMark")
    public Response addMark(Integer markId,String ids,int type,boolean selected){
        Response response = new Response();
        if(markId == null){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("标签不能为空");
        }
        if(StringUtils.isBlank(ids)){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("店铺id或宝贝id不能为空");
        }

        try{
            commonService.addFocusMarks(markId,ListUtil.strToList(ids,","),type,selected,getUser());
        }catch (Exception e){
            response.setResult(ExceptionCode.SERVICE_ERROR);
            response.setMessage("标签添加失败");
            logger.error("标签添加失败",e);
        }

        return response;
    }


    /**
     * 获取店铺详情
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response getMerchantDetail(MerchantDetailRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        request.setUserData(getUser());
        if(request.validate(response)){
            try{
                MerchantDomain merchantDomain = localMerchantService.getMerchantDetailVo(request);
                if(merchantDomain == null){
                    response.setData("数据获取异常，请联系我们的在线技术支持。");
                    response.setResult(ExceptionCode.NULL_ERROR);
                    response.setRedirect("http://overseas.superseller.cn/index.html#/data/shop_me/?pageNo=1");
                }else{
                    response.setData(merchantDomain);
                }
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                response.setRedirect("http://overseas.superseller.cn/index.html#/data/shop_me/?pageNo=1");
                logger.error("getMerchantDetail异常,请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getMerchantDetail参数验证失败"+ JSON.toJSONString(response));
        }
        return response;
    }

    /**
     * 获取店铺经营数据
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/chart")
    public Response getMerchantChart(MerchantChartRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        if(request.validate(response)){
            try{
                response.setData(localMerchantService.getMerchantChartVo(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getMerchantChart参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    /**
     * 获取产品行业分析数据图表
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/catChart")
    public Response getCatChart(MerchantCatChartRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        if(request.validate(response)){
            try{
                response.setData(localMerchantService.getCatChart(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getCatChartl参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    /**
     * 获取店铺热销产品列表
     * @param request
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/hotItemList")
    public Response getHotItemList(MerchantHotItemListRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        if(request.validate(response)){
            try{
                request.setStartTime(localIndustryService.getDataRefreshTime(DataUtil.ITEM_MAX_DATE));
                request.setEndTime(localIndustryService.getDataRefreshTime(DataUtil.ITEM_MAX_DATE));
                response.setData(localMerchantService.getHotItemList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getHotItemList参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/focus")
    public Response focus(OperFocusRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        UserData userData = getUser();

        request.setUserId(Integer.parseInt(getUser().getUserId()+""));
        if(request.validate(response)){
            try{
                List<String> ids = ListUtil.strToList(request.getIds(),",");
                if(request.getType() == 1){
                    if(request.getFocus() == 1){
                        localMerchantService.focusMerchants(userData,ids,request.getFocus());
                    }else{
                        for(int i = 0;i<ids.size();i++){
                            localMerchantService.focusMerchant(userData,ids.get(i),request.getFocus());
                        }
                    }
                }else{
                    List<List<String>> miList = DataUtil.splitItemIdAndMerchantId(ids);
                    localItemService.focusItemList(miList.get(0),miList.get(1),request.getFocus(),null,userData);
                }
            }catch (RpcException e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("关注失败,id"+request.getIds(),e);
            }catch (BizException e) {
                response.setResult(e.getErrorCode());
                response.setMessage(e.getErrorMsg());
                logger.error("关注失败,id"+request.getIds(),e);
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("关注失败,id"+request.getIds(),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getMerchantDetail参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/newItemList")
    public Response getNewItem(MerchantNewItemRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        if(request.validate(response)){
            try{
                response.setData(localMerchantService.getNewItemList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getNewItem参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }

    /**
     * 统计店铺里程碑信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/itemEvent")
    public Response getMerchantEventInfo(MerchantItemEventRequest request){
        Response response = new Response();
        response.setApi_name(request.getApi_name());
        if(request.validate(response)){
            try{
                response.setData(localMerchantService.getMerchantEventList(request));
            }catch (Exception e) {
                response.setResult(ExceptionCode.NULL_ERROR);
                response.setMessage("数据获取异常，请联系我们的在线技术支持。");
                logger.error("请求参数:"+ JSON.toJSONString(request),e);
            }
        }else{
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("参数验证失败");
            logger.error("getMerchantEventInfo参数验证失败"+ JSON.toJSONString(response));
        }

        return response;
    }
}
