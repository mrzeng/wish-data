<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.PushItemService" %>
<%@ page import="com.raycloud.overseas.erp.data.domain.UserData" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.UserDataService" %>
<%@ page import="com.raycloud.overseas.erp.data.vo.Response" %>
<%@ page import="com.raycloud.overseas.erp.data.common.query.PushItemQuery" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.raycloud.overseas.erp.data.common.common.Result" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
    System.out.println("=======");
    PushItemService pushItemService= (PushItemService) ac.getBean(PushItemService.class);
    UserDataService userDataService= (UserDataService) ac.getBean(UserDataService.class);
    UserData userData = userDataService.getUserDataByKey(88);
    Response responseVo = new Response();
    String type = request.getParameter("type");
    pushItemService.refreshPushItemList(userData);
//    if(type.equals("query")){
//        PushItemQuery pushItemQuery = new PushItemQuery();
//        pushItemQuery.setRules("7");
//        pushItemQuery.setPageNo(8);
//        pushItemQuery.setPageSize(1);
//        System.out.println(JSON.toJSONString(pushItemQuery));
//        Result result = pushItemService.getPushItemListWithPage(pushItemQuery,userData);
//        responseVo.setData(result);
//    }else if(type.equals("refresh")){
//        pushItemService.refreshPushItemList(userData);
//    }
//    System.out.println(JSON.toJSONString(responseVo));

//    UserData userData = userDataService.getUserDataByKey(88);
//    pushItemService.refreshPushItemList(userData);
//    pushItemService.initPushItemList(88L);
//    pushItemService.refreshPushItemList(88L);
//    pushItemService.timerRefreshPushItemTask();
//    System.out.println(pushItemService);
//    pushItemService.do1();
//    ILocalIndustryService localIndustryService = (ILocalIndustryService) ac.getBean(ILocalIndustryService.class);
//
//    IRecordService recordService = (IRecordService) ac.getBean(IRecordService.class);
//    recordService.recordUnInDBProduct("2016-01-01");
//    localIndustryService.guessItem();
//    pushItemService.timerRefreshPushItemTask();
//    pushItemService.timerCancleItemFocusTask();
%>
