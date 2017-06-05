<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.common.pojo.PushRule" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.PushRuleService" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.UserDataService" %>
<%@ page import="com.raycloud.overseas.erp.data.domain.UserData" %>
<%@ page import="com.raycloud.overseas.erp.data.vo.Response" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.raycloud.overseas.erp.data.common.query.PushRuleQuery" %>
<%@ page import="com.raycloud.overseas.erp.data.common.common.Result" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.PushWorkBenchService" %>
<%@ page import="com.raycloud.overseas.erp.data.common.pojo.PushTotal1" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.raycloud.overseas.erp.data.common.query.PushTotal1Query" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application);
    PushWorkBenchService pushWorkBenchService = applicationContext.getBean(PushWorkBenchService.class);
    UserDataService userDataService = applicationContext.getBean(UserDataService.class);
    UserData userData = userDataService.getUserDataByKey(88);
    Response responseVo = new Response();
    String type = request.getParameter("type");
    if(type.equals("add")){
        PushTotal1 pushTotal1 = new PushTotal1();
        pushTotal1.setPushTotal1(55);
        pushTotal1.setPushTime(new Date());
        pushTotal1.setFounderId(userData.getFounderId());
        pushTotal1.setPushTotal7(55);
        JSONArray array = new JSONArray();
        JSONObject param = new JSONObject();
        param.put("rule_id",16);
        param.put("rule_name","hello");
        param.put("total",15);
        array.add(param);
        pushTotal1.setRuleResultDetail(array.toJSONString());
        System.out.println(JSON.toJSONString(pushTotal1));
        pushWorkBenchService.addPushTotal1(pushTotal1,userData);
    }else if(type.equals("query1")){
        PushTotal1Query pushTotal1Query = new PushTotal1Query();
        System.out.println(JSON.toJSONString(pushTotal1Query));
        List<PushTotal1> result = pushWorkBenchService.getPushTotal1List(pushTotal1Query,userData);
        responseVo.setData(result);
    }else if(type.equals("query7")){
        PushTotal1Query pushTotal1Query = new PushTotal1Query();
        System.out.println(JSON.toJSONString(pushTotal1Query));
        List<PushTotal1> result = pushWorkBenchService.getPushTotal1List(pushTotal1Query,userData);
        responseVo.setData(result);
    }
    System.out.println(JSON.toJSONString(responseVo));
%>
