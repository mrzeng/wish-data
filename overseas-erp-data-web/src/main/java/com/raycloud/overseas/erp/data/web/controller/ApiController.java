

package com.raycloud.overseas.erp.data.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushItem;
import com.raycloud.overseas.erp.data.common.pojo.PushItemMerchantMap;
import com.raycloud.overseas.erp.data.common.pojo.PushRule;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal7;
import com.raycloud.overseas.erp.data.common.query.*;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.search.core.SwitchService;
import com.raycloud.overseas.erp.data.services.api.*;
import com.raycloud.overseas.erp.data.services.context.UserSysConfigContext;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-13
 */
@Controller
@RequestMapping("/data/data/api")
public class ApiController extends BaseController{

    private static Logger logger= Logger.getLogger(ApiController.class);

    @Resource
    private PushWorkBenchService pushWorkBenchService;

    @Resource
    private SwitchService switchService;

    @Autowired
    UserDataService userDataService;

    @Resource
    CommonService commonService;

    /**
     * 微信带采集产品统计接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/dataPushCount",method = RequestMethod.GET)
    public Response queryPushTotal1(String sign ,String userId) {
        Response response = new Response();
        response.setApi_name("");
        if(StringUtils.isEmpty(sign) || !sign.equals("overseas")){
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("认证失败");
            return response;
        }
        try {
            response.setData(pushWorkBenchService.getPushCollectCount(Integer.parseInt(userId)));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }

    /**
     * 微信带采集产品统计接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exceptionDeal",method = RequestMethod.GET)
    public Response exceptionDeal(String type,Integer userId,String op) {
        Response response = new Response();
        response.setApi_name("");

        try {
            UserData userData = userDataService.getUserDataByKey(userId);
            boolean slow = commonService.checkSlowOpIsRunning(DataConstant.ASYN_ITEM_COLLECT,userData);
            logger.biz("校验操作正在运行:{},用户id:{}",slow,userId);
            if(!StringUtils.isEmpty(op)){
                if(op.equals("kill")){
                    commonService.removeSession(type,userData);
                }
            }
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("查询失败");
            logger.error("查询失败",e);
        }
        return response;
    }

    /**
     * 切换solr服务器
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/switchSolr",method = RequestMethod.GET)
    public Response switchSolr(int node ,String command) {
        Response response = new Response();
        try {
            response.setData(switchService.switchNode(node,command));
        } catch (Exception e) {
            response.setResult(ExceptionCode.SYS_ERROR_MQ8);
            response.setMessage("切换异常");
            logger.error("切换异常",e);
        }
        return response;
    }
}