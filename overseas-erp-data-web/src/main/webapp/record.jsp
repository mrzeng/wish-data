<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.IRecordService" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
    IRecordService recordService= (IRecordService) ac.getBean(IRecordService.class);
    String since = request.getParameter("since");
    recordService.recordUnInDBProduct(since);
%>
