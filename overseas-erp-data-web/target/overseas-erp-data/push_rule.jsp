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
<%@ page import="java.util.Map" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application);
    PushRuleService pushRuleService = applicationContext.getBean(PushRuleService.class);
    UserDataService userDataService = applicationContext.getBean(UserDataService.class);
    UserData userData = userDataService.getUserDataByKey(88);
    Response responseVo = new Response();
    String type = request.getParameter("type");
    if(type.equals("add")){
        PushRule pushRule = new PushRule();
        pushRule.setRuleName("上新产品");
        pushRule.setRuleDesc("上新产品描述");
        pushRule.setRuleStatus(1);
        pushRule.setStrategyType(1);
        JSONObject param = new JSONObject();
        param.put("markId",16);
        JSONObject amount = new JSONObject();
        amount.put("amount",30);
        amount.put("status",1);
        JSONObject amount7_range = new JSONObject();
        amount7_range.put("amount7_start",1);
        amount7_range.put("amount7_end",10);
        amount7_range.put("status",1);
        JSONObject gen_item = new JSONObject();
        gen_item.put("gen_item",10);
        gen_item.put("status",1);
        JSONObject gen_time = new JSONObject();
        gen_time.put("gen_time",3);
        gen_time.put("status",0);
        JSONObject amount_rate_item = new JSONObject();
        amount_rate_item.put("amount_rate_item",3);
        amount_rate_item.put("status",1);
        JSONObject amount_rate_range = new JSONObject();
        amount_rate_range.put("amount_rate_range_start",20);
        amount_rate_range.put("amount_rate_range_end",30);
        amount_rate_range.put("status",1);
        param.put("amount",amount);
        param.put("amount7_range",amount7_range);
        param.put("gen_item",gen_item);
        param.put("gen_time",gen_time);
        param.put("amount_rate_item",amount_rate_item);
        param.put("amount_rate_range",amount_rate_range);
        pushRule.setStrategyParam(param.toJSONString());
        System.out.println(JSON.toJSONString(pushRule));
        pushRuleService.saveAndUseRule(pushRule,userData);
    }else if(type.equals("query")){
        PushRuleQuery pushRuleQuery = new PushRuleQuery();
        pushRuleQuery.setRuleStatus(1);
        pushRuleQuery.setSearchKey("上新产品");
        pushRuleQuery.setPushTotal7Start(1);
        pushRuleQuery.setPushTotal7End(60);
        pushRuleQuery.setPageNo(1);
        pushRuleQuery.setPageSize(10);
        System.out.println(JSON.toJSONString(pushRuleQuery));
        Result result = pushRuleService.getPushRuleListWithPage(pushRuleQuery,userData);
        responseVo.setData(result);
    }else if(type.equals("itemRange")){
        Map<String,Object> map = pushRuleService.queryRuleItemRange(userData);
        responseVo.setData(map);
    }
    System.out.println(JSON.toJSONString(responseVo));
%>
