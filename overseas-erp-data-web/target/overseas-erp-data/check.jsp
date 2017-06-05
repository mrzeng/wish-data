<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.raycloud.overseas.erp.data.common.dao.WishCategoryDAO" %>
<%@ page import="com.raycloud.overseas.erp.data.domain.WishCategory" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
    WishCategoryDAO dataDao= (WishCategoryDAO) ac.getBean(WishCategoryDAO.class);
    WishCategory wishCategory = dataDao.getWishCategoryByKey("f329d8314cbe40df9445ef244e386760");
    if(wishCategory!=null){
        response.getWriter().write("true");
        return;
    }else {
            response.getWriter().write("false");
            return;
    }
%>
