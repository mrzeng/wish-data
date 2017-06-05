<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.IRecordService" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.GuessItemService" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.UserDataService" %>
<%@ page import="com.raycloud.overseas.erp.data.domain.UserData" %>
<%@ page import="com.raycloud.overseas.erp.data.services.ons.OnsUtils" %>
<%@ page import="com.raycloud.overseas.erp.data.constant.DataConstant" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
    UserDataService userDataService= ac.getBean(UserDataService.class);
    OnsUtils onsUtils=  ac.getBean(OnsUtils.class);
    UserData userData = userDataService.getUserDataByKey(88);
    onsUtils.sendOns(DataConstant.COLLECT_FINISH_NOTICE,"hello",userData);
    System.out.println("=========");
%>
