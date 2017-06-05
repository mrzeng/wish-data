<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.IRecordService" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.GuessItemService" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
    GuessItemService guessItemService= (GuessItemService) ac.getBean(GuessItemService.class);
//    guessItemService.timerGuessItemTask();
    System.out.println("==");
    guessItemService.getGuessItemList(88L);
%>
