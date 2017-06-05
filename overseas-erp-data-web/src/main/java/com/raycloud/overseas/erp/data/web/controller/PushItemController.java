

package com.raycloud.overseas.erp.data.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.*;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;


import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;

import com.raycloud.overseas.erp.data.services.api.*;
import com.raycloud.overseas.erp.data.services.context.UserSysConfigContext;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Description:
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-13
 */
@Controller
@RequestMapping("/data/data/push")
public class PushItemController extends BaseController{

    private static Logger logger= Logger.getLogger(PushItemController.class);

    @Autowired
    PushItemService pushItemService;

    @Autowired
    PushItemMerchantMapService pushItemMerchantMapService;

    @Autowired
    private ILocalItemService localItemService;

    @Resource
    private PushRuleService pushRuleService;

    @Resource
    private PushWorkBenchService pushWorkBenchService;

    @ResponseBody
    @RequestMapping(value = "/pushList", method = RequestMethod.POST)
    public Response query(PushItemQuery pushItemQuery) {
        Response response = new Response();
        response.setApi_name("");
        UserData userData = getUser();
        pushItemQuery.setMerchantIdList(ListUtil.strToList(pushItemQuery.getMerchantIds(),","));
        try {
            Result<PushItem> rs = pushItemService.getPushItemListWithPage(pushItemQuery,userData);
            response.setData(rs);
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("用户"+userData.getUserId()+"推送宝贝查询失败",e);
        }
        return response;
    }

//    /**
//     * 设置推送条件
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/setPushCondition",method = RequestMethod.POST)
//    public Response setPushCondition(Integer latestGenDays,Integer saleHotCount,Integer amountSurgeCount) {
//        Response response = new Response();
//        response.setApi_name("");
//        if(latestGenDays == null){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("最新上新个数不能为空");
//            logger.error("参数验证失败,用户id:{},error:最新上新天数不能为空");
//        }
//
//        if(saleHotCount == null){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("热销数量不能为空");
//            logger.error("参数验证失败,用户id:{},error:热销产品数量不能为空");
//        }
//
//        if(amountSurgeCount == null){
//            response.setResult(ExceptionCode.PARAMETER_ERROE);
//            response.setMessage("飙升产品不能为空");
//            logger.error("参数验证失败,用户id:{},error:飙升产品不能为空");
//        }
//
//        UserData userData = getUser();
//
//        try {
//            logger.biz("设置推送条件,用户id:{}",userData.getUserId());
//            PushConditionDomain pushConditionDomain = new PushConditionDomain();
//            pushConditionDomain.setAmountSurgeCount(amountSurgeCount);
//            pushConditionDomain.setLatestGenDays(latestGenDays);
//            pushConditionDomain.setSaleHotCount(saleHotCount);
//            pushConditionDomain.setUserId(Long.parseLong(userData.getUserId()+""));
//            pushConditionDomain.setFounderId(userData.getFounderId());
//            pushConditionService.addPushCondition(pushConditionDomain);
//            response.setData("success");
//        } catch (Exception e) {
//            response.setResult(ExceptionCode.NULL_ERROR);
//            response.setMessage("设置失败");
//            logger.error("推送参数设置失败",e);
//        }
//
//        return response;
//    }

    /**
     * 关注推送宝贝
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/focusPushItems",method = RequestMethod.POST)
    public Response focusPushItems(String itemIds) {
        Response response = new Response();
        response.setApi_name("");
        if(itemIds == null){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("宝贝id不能为空");
            logger.error("参数验证失败,用户id:{},error:宝贝id不能为空");
        }
        UserData userData = getUser();

        List<String> itemIdList = ListUtil.strToList(itemIds,",");
        try {
            List<List<String>> miList = DataUtil.splitItemIdAndMerchantId(itemIdList);
            localItemService.focusItemList(miList.get(0),miList.get(1), DataConstant.FOCUS,null,userData);
            response.setData("success");
        } catch (BizException e) {
            response.setResult(e.getErrorCode());
            response.setMessage(e.getErrorMsg());
            logger.error("批量关注失败",e);
        }catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("批量关注失败");
            logger.error("批量关注失败",e);
        }

        return response;
    }

    /**
     * 关注推送宝贝
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStatusList",method = RequestMethod.POST)
    public Response getStatusList(String itemIds) {
        Response response = new Response();
        response.setApi_name("");
        if(itemIds == null){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("宝贝id不能为空");
            logger.error("参数验证失败,用户id:{},error:宝贝id不能为空");
        }
        UserData userData = getUser();
        List<String> itemIdList = ListUtil.strToList(itemIds,",");
        try {
            response.setData(pushItemService.getStatusList(itemIdList,userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("获取状态失败");
            logger.error("获取状态失败",e);
        }

        return response;
    }

    /**
     * 采集推送宝贝
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/collectPushItems",method = RequestMethod.POST)
    public Response collectPushItems(String fc,String ufc,String fuc,String ufuc) {
        Response response = new Response();
        response.setApi_name("");

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
        final List<String> itemIdList = list;
        int len = ListUtil.countListLen(fcList,ufcList,ufucList,fucList);
        final boolean needBean = len>1;
        if(len==0){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("采集宝贝不能为空");
            return response;
        }

        if(!StringUtils.isEmpty(fc)&&!StringUtils.isEmpty(ufc)&&!StringUtils.isEmpty(fuc)&&!StringUtils.isEmpty(ufuc)){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("采集宝贝不能为空");
            logger.error("参数验证失败,用户id:{},error:宝贝id不能为空");
        }
        UserData userData = getUser();
        try {
            final List<List<String>> miList = DataUtil.splitItemIdAndMerchantId(itemIdList);
            pushItemService.collectPushItems(sum,userData,miList.get(0),miList.get(1),needBean);
            response.setData("success");
        }catch (BizException e1) {
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

    /**
     * 移除推送宝贝
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removePushItems",method = RequestMethod.GET)
    public Response removePushItems(String itemIds) {
        Response response = new Response();
        response.setApi_name("");
        if(itemIds == null){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("宝贝id不能为空");
            logger.error("参数验证失败,用户id:{},error:宝贝id不能为空");
        }
        UserData userData = getUser();
        try {
            pushItemService.removePushItemList(userData,ListUtil.strToList(itemIds,","));
            response.setData("success");
        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("批量移入历史推送列表失败");
            logger.error("批量移入历史推送列表失败",e);
        }

        return response;
    }

    /**
     * 移除推送宝贝
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRule",method = RequestMethod.GET)
    public Response getRule(String ruleId) {
        Response response = new Response();
        response.setApi_name("");
        UserData userData = getUser();
        try {
            response.setData(pushRuleService.getRule(ruleId,userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("获取规则失败");
            logger.error("获取规则失败",e);
        }

        return response;
    }

    /**
     * 获取店铺列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMerchantList",method = RequestMethod.GET)
    public Response getMerchantList() {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            PushItemMerchantMapQuery pushItemMerchantMapQuery = new PushItemMerchantMapQuery();
            pushItemMerchantMapQuery.setFounderId(userData.getFounderId());
            pushItemMerchantMapQuery.setUserId(Long.parseLong(userData.getUserId()+""));
            List<PushItemMerchantMap> pushItemMerchantMapList = pushItemMerchantMapService.getPushItemMerchantMapList(pushItemMerchantMapQuery);
            response.setData(pushItemMerchantMapList);
        } catch (Exception e) {
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("获取推送店铺列表失败");
            logger.error("获取推送店铺列表失败",e);
        }

        return response;
    }

    /***************************推送规则*********************************/

    /**
     * 校验规则
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkRule",method = RequestMethod.GET)
    public Response checkRule(Integer strategyType,String strategyParam) {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            JSONObject strategyParamJSON = JSON.parseObject(strategyParam);
            pushRuleService.checkPushResult(strategyType,strategyParamJSON,userData);
        }catch (BizException e) {
            response.setResult(e.getErrorCode());
            response.setMessage(e.getErrorMsg());
            logger.error(e.getErrorMsg()+",用户id:"+userData.getUserId(),e);
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("校验失败");
            logger.error("校验失败",e);
        }
        return response;
    }

    /**
     * 更新推送规则状态
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePushRuleStatus",method = RequestMethod.GET)
    public Response updatePushRuleStatus(String ids,Integer status) {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            List<String> idList = ListUtil.strToList(ids,",");
            if(status == 2){
                pushRuleService.deletePushRule(idList,userData);
            }else{
                pushRuleService.updatePushRuleStatus(idList,status,userData);
                Map<String,String> map = new HashMap<String,String>();
                map.put("excute_time", UserSysConfigContext.checkItemRefreshed(userData)?"tomorrow":"today");
                response.setData(map);
            }
        } catch (BizException e) {
            response.setResult(e.getErrorCode());
            response.setMessage(e.getErrorMsg());
            logger.error("更新规则状态失败,用户id:"+userData.getUserId(),e);
        }catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("更新规则状态失败");
            logger.error("更新规则状态失败",e);
        }
        return response;
    }

    /**
     * 查询规则列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPushRuleListWithPage",method = RequestMethod.GET)
    public Response getPushRuleListWithPage(PushRuleQuery pushRuleQuery) {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            response.setData(pushRuleService.getPushRuleListWithPage(pushRuleQuery,userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }

    /**
     * 保存规则
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPushRule",method = RequestMethod.POST)
    public Response addPushRule(PushRule pushRule) {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {

            pushRuleService.saveAndUseRule(pushRule,userData);
            Map<String,String> map = new HashMap<String,String>();
            map.put("excute_time", UserSysConfigContext.checkItemRefreshed(userData)?"tomorrow":"today");
            response.setData(map);
        }catch (BizException e1) {
            response.setResult(e1.getErrorCode());
            response.setMessage(e1.getErrorMsg());
        }
        return response;
    }


    /**
     * 查询规则列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryUsingRuleList",method = RequestMethod.GET)
    public Response queryUsingRuleList() {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            response.setData(pushRuleService.queryUsingRuleList(userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }

    /**
     * 查询规则列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryRuleItemRange",method = RequestMethod.GET)
    public Response queryRuleItemRange() {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            response.setData(pushRuleService.queryRuleItemRange(userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }

    /******************************推送工作台*****************************************/

    /**
     * 查询推送概览
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPushSimpleTotal",method = RequestMethod.GET)
    public Response queryPushSimpleTotal(int type) {
        Response response = new Response();
        response.setApi_name("");
        UserData userData = getUser();
        try {
            response.setData(pushWorkBenchService.queryPushTotal7ByType(type,userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }

    /**
     * 查询每日推送概览
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryPushTotal1",method = RequestMethod.GET)
    public Response queryPushTotal1() {
        Response response = new Response();
        response.setApi_name("");

        UserData userData = getUser();
        try {
            response.setData(pushWorkBenchService.getPushTotal1List(new PushTotal1Query(),userData));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }
}