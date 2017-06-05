package com.raycloud.overseas.erp.data.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.common.domain.UserAccount;
import com.raycloud.overseas.erp.data.common.constant.Constants;
import com.raycloud.overseas.erp.data.common.session.Session;
import com.raycloud.overseas.erp.data.common.spring.SpringUtil;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.vo.Response;
import com.raycloud.overseas.usercenter.web.api.service.ITeamService;
import com.raycloud.overseas.usercenter.web.api.vo.TeamRoleInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class UserInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(UserInterceptor.class);

    private ITeamService teamService = (ITeamService)SpringUtil.getBean("teamService");

    private Session session = (Session) SpringUtil.getBean("ocsSession");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // logger.biz("本次拦截器拦截的请求:["+request.getRequestURI()+"]");

        /**
         * 思路：
         * 1：对代理服务器不进行拦截
         * 2：对ajax请求的拦截提示
         * 3：对...
         */
        String requestType = request.getHeader("X-Requested-With");
        String uri = request.getRequestURI();
        if(uri.endsWith("loginUserAccount.json")||uri.endsWith("sendMsg.json")||uri.endsWith("regUser.json")
                ||uri.endsWith("findPwd.json")||uri.endsWith("getVerifyCode.json")||uri.endsWith("notifyItemUpdate.json")
                ||uri.endsWith("notifyMerchantUpdate.json")||uri.endsWith("productGuessItem.json")
                ||uri.endsWith("productGuessMerchant.json")||uri.endsWith("dataPushCount.json")
                ||uri.endsWith("switchSolr.json")||uri.indexOf("getPostList")>-1){

            return true;
        }
        try {
            UserAccount userDomain = session.getSuperObj(request, Constants.SESSION_USER);
            if (null == userDomain) {
                //跳到无用户的状态
                //TODO:这里最好要分别下同步或者异步请求，如果用户ajax返回Json
                if (requestType != null) {
                    Response response1 = new Response();
                    response1.setResult(ExceptionCode.AUTH_ERROR);
                    response1.setMessage("会话失效");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(JSON.toJSONString(response1));
                    writer.flush();
                    writer.close();

                } else {
                    Response response1 = new Response();
                    response1.setResult(ExceptionCode.AUTH_ERROR);
                    response1.setMessage("会话失效");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(JSON.toJSONString(response1));
                    writer.flush();
                    writer.close();
                    //  response.setContentType("text/html");
                    // response.sendRedirect("http://overseas.superseller.cn/index.html");
                }
                return false;
            }

            if(uri.indexOf("/data/data")>-1){//

                TeamRoleInfo roleInfo = teamService.getRoleInfo(userDomain.getId());
                if(!roleInfo.isCreator()&&!roleInfo.getModuleAuth().getData()){
                    Response response1 = new Response();
                    response1.setResult(ExceptionCode.SERVICE_NO_DATA_ROLE);
                    response1.setMessage("暂无权限");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(JSON.toJSONString(response1));
                    writer.flush();
                    writer.close();
                    return false;
                }
                request.setAttribute("roleInfo",roleInfo);
            }


            return super.preHandle(request, response, handler);
        } catch (BizException e) {
            logger.error("获取用户信息异常:", e.getMessage());

            if (requestType != null) {
                Response response1 = new Response();
                response1.setResult(ExceptionCode.AUTH_ERROR);
                response1.setMessage("会话失效");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(response1));
                writer.flush();
                writer.close();
            } else {
                response.setContentType("text/html");
                response.sendRedirect("http://overseas0.superseller.cn/index.html");
            }


        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取用户信息异常:", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户信息异常:", e.getMessage());

        }
        return false;
    }



}
