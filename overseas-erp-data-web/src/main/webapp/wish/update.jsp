<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.IRecordService" %>
<%@ page import="com.raycloud.overseas.erp.data.services.api.GuessItemService" %>
<%@ page import="com.raycloud.overseas.erp.data.common.dao.FocusMerchantDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
    String type = request.getParameter("type");
    FocusMerchantDAO focusMerchantDAO= (FocusMerchantDAO) ac.getBean(FocusMerchantDAO.class);


%>
