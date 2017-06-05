package com.raycloud.overseas.erp.data.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.common.domain.UserAccount;
import com.raycloud.overseas.erp.data.common.constant.Constants;
import com.raycloud.overseas.erp.data.common.session.Session;
import com.raycloud.overseas.erp.data.common.util.DateUtil;

import com.raycloud.overseas.erp.data.vo.Response;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by forest on 14-9-11.
 */
public class ExceptionInterceptor extends SimpleMappingExceptionResolver {

    private static final Logger logger = Logger.getLogger(ExceptionInterceptor.class);

    @Resource
    private Session session;

    private String defaultEncoding = "UTF-8";

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {

        String url = httpServletRequest.getRequestURL().toString();
        Map<String,String[]> parameters = httpServletRequest.getParameterMap();

        UserAccount user = null;
        String sessionCode = "700";
        try {
            user  = (UserAccount) session.getSuperObj(httpServletRequest, Constants.SESSION_USER);
        }catch (Exception e){
            logger.error("错误拦截器中获取Session中的用户信息时发生了错误.",e);
            //如果从session中获取User出错了，那么认为用户不存在
            sessionCode = "710";
        }

        StringBuffer exceptionMsg = new StringBuffer();
        exceptionMsg.append("\n\t");
        exceptionMsg.append("用户【");
        if(user==null) {
            exceptionMsg.append(sessionCode);
        }else {
            exceptionMsg.append(user.getId());

        }
        exceptionMsg.append("】");
        exceptionMsg.append(DateUtil.getGMTStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        exceptionMsg.append("请求：");
        exceptionMsg.append("\n\t");
        exceptionMsg.append("访问链接：");
        exceptionMsg.append(url);
        exceptionMsg.append("\n\t");
        exceptionMsg.append("访问参数：");
        exceptionMsg.append(JSON.toJSONString(parameters));
        logger.error(exceptionMsg, ex);

        if (httpServletRequest.getRequestURI().endsWith("rjson")) {
            Response response = handleException(httpServletRequest, ex);
            try {
                responseJson(httpServletRequest, httpServletResponse, response);
            }catch (IOException e){
                logger.error("响应JSON异常信息的时候报错了.", e);
            }
            return new ModelAndView();
        }
        return super.doResolveException(httpServletRequest, httpServletResponse, handler, ex);
    }

    /**
     * 分类型处理异常，让异常按照自己的类型返回信息
     * @param httpServletRequest
     * @param ex
     * @return
     */
    private Response handleException(HttpServletRequest httpServletRequest, Exception ex){

        Response response = new Response();
        response.setResult(400);
        response.setMessage("系统处理异常");
        response.setApi_name(httpServletRequest.getParameter("api_name"));
//        if(ex instanceof ParameterException){
//            ParameterException pe = (ParameterException)ex;
//            response.setResult(200);
//          //  response.setMessage(pe.getMessage()+pe.getFields());
//
//        }

        return response;
    }

    /**
     * 以JSON的格式将数据相应到前端，
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @throws IOException
     */
    private void responseJson(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object o) throws IOException{

        httpServletResponse.setContentType("application/json;charset=" + defaultEncoding);
        PrintWriter pw = null;
        try {
            pw = httpServletResponse.getWriter();
            if(o==null) o = new Object();
            if (o instanceof String) {
                pw.print(o);
            } else {
                pw.print(JSONObject.toJSONString(o));
            }
        }finally {
            if(pw!=null)
                pw.close();
        }

    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }
}
