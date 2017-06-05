package com.raycloud.overseas.erp.data.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.pojo.DataMarkInfo;
import com.raycloud.overseas.erp.data.common.pojo.SearchCondition;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.services.api.CommonService;
import com.raycloud.overseas.erp.data.services.api.ILocalItemService;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.erp.data.web.base.BaseController;
import com.raycloud.overseas.usercenter.web.api.vo.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanxf on 17/2/10.
 */
@Controller
@RequestMapping("/data/data")
public class CommonController extends BaseController{

    @Autowired
    private CommonService commonService;

    /**
     * 保存筛选项
     * @param searchCondition
     * @return
     */
    @RequestMapping(value = "/common/saveFilterCondition",method = RequestMethod.POST)
    @ResponseBody
    public Response saveFilterCondition(SearchCondition searchCondition){
        Response response = new Response();
        response.setApi_name("");
        UserData curUser = getUser();
        ResultObject result = commonService.saveFilterCondition(searchCondition, curUser);
        response.setData(result.getData());
        return response;
    }

    /**
     * 删除筛选项
     * @param id
     * @return
     */
    @RequestMapping(value = "/common/deleteFilterCondition",method = RequestMethod.POST)
    @ResponseBody
    public Response deleteFilterCondition(String id){
        Response response = new Response();
        response.setApi_name("");
        UserData curUser = getUser();
        if (StringUtils.isEmpty(id)){
            response.setMessage("没有参数");
            response.setResult(ExceptionCode.PARAMETER_ERROE1);
            return  response;
        }

        commonService.deleteFilterCondition(id, curUser);
        return response;
    }

    /**
     * 查询筛选项
     * @return
     */
    @RequestMapping(value = "/common/queryFilterCondition",method = RequestMethod.GET)
    @ResponseBody
    public Response queryFilterCondition(String status){
        Response response = new Response();
        response.setApi_name("");
        UserData curUser = getUser();
        response.setData(commonService.querySearchCondition(curUser, status));
        return response;
    }

    /**
     * 保存 显示列 信息
     * @param param
     * @param status
     * @return
     */
    @RequestMapping(value = "/common/saveColumnCondition",method = RequestMethod.POST)
    @ResponseBody
    public Response saveColumnCondition(String param, String status){
        Response response = new Response();
        response.setApi_name("");
        UserData curUser = getUser();
        commonService.saveColumnCondition(param, status, curUser);
        response.setData("success");
        return response;
    }

    /**
     * 保存 显示列 信息
     * @return
     */
    @RequestMapping(value = "/common/getColumnCondition",method = RequestMethod.GET)
    @ResponseBody
    public Response saveColumnCondition(String status){
        Response response = new Response();
        response.setApi_name("");
        UserData userData = getUser();
        response.setData(commonService.getColumnCondition( status, userData));
        return response;
    }

    /**
     * 保存标签信息 type 0訂單，1包裹,2:产品,3:店铺
     * @param
     * @return
     */
    @RequestMapping(value = "/item/saveLabelInfo")
    @ResponseBody
    public Response saveLabelInfo(String markInfoList, String type){
        Response response = new Response();
        response.setApi_name("");
        UserData curUser = getUser();
        if(markInfoList!=null){

            List<DataMarkInfo> dataMarkInfoList= JSONObject.parseArray(markInfoList, DataMarkInfo.class);
            commonService.saveMarkInfo(dataMarkInfoList, curUser,type);
            response.setData(commonService.labelManageShow(curUser,type));
        }
        return response;
    }

    /**
     * 标签列表
     * @return
     */
    @RequestMapping(value = "/item/labelManageShow")
    @ResponseBody
    public Response labelManageShow(String type){

        Response response = new Response();
        response.setApi_name("");
        UserData userData = getUser();
        response.setData(commonService.labelManageShow(userData,type));
        return response;
    }

    /**
     * 获取订单详情数据
     * @param
     * @return
     */
    @RequestMapping(value = "/common/querySynProcess",method = RequestMethod.GET)
    @ResponseBody
    public Response querySynProcess(String type){
        Response response = new Response();
        response.setApi_name("");
        UserData userData = getUser();
        Map<String,Object> synMap = commonService.querySynResult(type,userData);
        if(synMap == null){
            response.setResult(ExceptionCode.NULL_ERROR);
            response.setMessage("查询失败");
        }else{
            response.setData(synMap);
        }
        return response;
    }
}
