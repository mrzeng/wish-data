package com.raycloud.overseas.erp.data.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.session.Session;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.services.api.UserDataService;
import com.raycloud.overseas.erp.data.services.task.KF5Task;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenglingfang on 16/6/22.
 */
@Controller
@RequestMapping("/data")
public class IndexController extends BaseController {

    private static Logger logger= Logger.getLogger(IndexController.class);

    @Resource
    protected Session ocsSession;

    @Resource(name = "kf5")
    private KF5Task kf5Task;

    @Resource
    private UserDataService userDataService;

    @ResponseBody
    @RequestMapping(value = "/index/index", method = RequestMethod.GET)
    public Response index(){
        Response response = new Response();
        response.setApi_name("/index/index.json");

        UserData curUser = this.getUser();
        if(curUser == null){
            response.setResult(ExceptionCode.AUTH_ERROR1);
            response.setMessage("该用户未登录,请重新登录");
        }else {

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("uname",curUser.getUname());
            map.put("email",curUser.getEmail());
            map.put("mobile",curUser.getMobile());
            map.put("user_id",curUser.getUserId());
            response.setData(map);
        }

        return response;
    }

    /**
     * 是否显示新手引导
     * @param modelName 模块名
     */
    @RequestMapping(value = "/reg/isShowGuide")
    @ResponseBody
    public Response showGuide(String modelName){
        Response response = new Response();
        response.setApi_name("/reg/isShowGuide");

        if (StringUtils.isBlank(modelName)){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("模块信息不能为空");
            return response;
        }

        try{

            UserData userAccount = getUser();
            String guideInfo = userAccount.getGuideInfo();
            java.util.List<String> modelNames = ListUtil.strToList(modelName,",");

            JSONObject json = null;
            JSONObject showJson = new JSONObject();
            if(StringUtils.isEmpty(guideInfo)){
                for(String _modelName : modelNames){
                    showJson.put(_modelName,true);
                }
            }else{
                json = JSON.parseObject(guideInfo);
                for(String _modelName : modelNames){
                    if(!json.containsKey(_modelName)){
                        showJson.put(_modelName,true);
                    }else{
                        Object obj = json.get(_modelName);
                        if(obj instanceof Boolean){
                            showJson.put(_modelName,obj);
                        }else{
                            showJson.put(_modelName,false);
                        }
                    }
                }

            }
            response.setData(showJson);
        }catch (Exception e){
            logger.error("会话中心异常:" , e);
            response.setResult(ExceptionCode.SYS_ERROR);
            response.setMessage("会话中心异常");
        }

        return response;
    }

    /**
     * 是否显示新手引导
     * @param modelName 模块名
     */
    @RequestMapping(value = "/reg/setGuide")
    @ResponseBody
    public Response setGuide(String modelName,Boolean show){
        Response response = new Response();
        response.setApi_name("/reg/setGuide");

        if (StringUtils.isBlank(modelName)){
            response.setResult(ExceptionCode.PARAMETER_ERROE);
            response.setMessage("模块信息不能为空");
            return response;
        }

        try{

            UserData userAccount = getUser();
            String guideInfo = userAccount.getGuideInfo();
            java.util.List<String> modelNames = ListUtil.strToList(modelName,",");

            JSONObject json = null;

            if(StringUtils.isEmpty(guideInfo)){
                json = new JSONObject();
            }else{
                json = JSON.parseObject(guideInfo);
            }
            for(String _modelName : modelNames){
                json.put(_modelName,show);
            }
            response.setData(json);
            userAccount.setGuideInfo(JSON.toJSONString(json));
//            ocsSession.setSuperObj(userAccount, getRequest(), getResponse(), Long.valueOf(userAccount.getId()), Constants.SESSION_USER);
            userDataService.updateUserDataByKey(userAccount);
        }catch (Exception e){
            logger.error("会话中心异常:" , e);
            response.setResult(ExceptionCode.SYS_ERROR);
            response.setMessage("会话中心异常");
        }

        return response;
    }

    /**
     * 查询当前用户所有新手引导信息
     */
    @RequestMapping(value = "/queryGuideList")
    @ResponseBody
    public Response queryGuideList(){
        Response response = new Response();
        response.setApi_name("/reg/queryGuideList");

        try{

            UserData userAccount = getUser();
            response.setData(userAccount.getGuideInfo());
        }catch (Exception e){
            logger.error("会话中心异常:" , e);
            response.setResult(ExceptionCode.SYS_ERROR);
            response.setMessage("会话中心异常");
        }

        return response;
    }

    /**
     * 查询消息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPostList", method = RequestMethod.GET)
    public String getPostList(String callback){
        Response response = new Response();
        response.setData(kf5Task.getPostList());

        //jsonp
        if(!org.springframework.util.StringUtils.isEmpty(callback)){
            return callback+"("+ JSON.toJSONString(response)+")";
        }
        return JSON.toJSONString(response);

    }
}
