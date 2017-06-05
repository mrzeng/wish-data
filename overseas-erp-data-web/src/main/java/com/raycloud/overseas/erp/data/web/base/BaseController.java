package com.raycloud.overseas.erp.data.web.base;

import com.alibaba.fastjson.JSON;
import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.common.domain.UserAccount;
import com.raycloud.overseas.erp.data.common.constant.Constants;

import com.raycloud.overseas.erp.data.common.session.Session;
import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.services.api.UserDataService;

import com.raycloud.overseas.usercenter.web.api.service.ITeamService;
import com.raycloud.overseas.usercenter.web.api.vo.TeamRoleInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Description:
 *
 * @author: 钟 亮
 * Date: 14-3-20
 * Time: 下午2:22
 */
public abstract class BaseController {
    private Logger logger = Logger.getLogger(BaseController.class);

    /**
     * 1.定义一个变量保存AJAX访问出错返回的信息。
     * 2.对于其它请求的话web.xml中已经配置了错误页面。
     */
    protected final String AJAX_ERROR_JSP = "";

    protected final String HTTP_ERROR_JSP = "";



    /**
     * 请求值
     *
     * [可选]返回数据方式. 取值 [json | jsonp]
     */
    public String json;
    /**
     * 请求参数值,如果不带id和nike和其他,可以为空
     */
    public String value;


    /**
     * Servlet APIs。
     */
    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
    private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

    /**
     * ocs 会话。
     */
    @Resource
    protected Session ocsSession;

    @Resource
    private UserDataService userDataService;

    @Resource
    private ITeamService teamService;

//    @Resource
//    private UserService userServiceImpl;

    public void writeToClient(Object result) {
        PrintWriter pw;
        try {
            pw = getResponse().getWriter();
            pw.print(JSON.toJSONString(result));
            pw.flush();
            pw.close();
        } catch (IOException e) {
            logger.error("BaseController写到客户端BaseController报错", e);
        }
    }


    public UserAccount getSuperUser(){


        try {
            return ocsSession.getSuperObj(getRequest(), Constants.SESSION_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    /**
//     * 得到创始人信息。
//     * @date   : 2016年6月30日
//     */
//    public UserAccount getCreater()  {
//        UserAccount userDomain=null;
//        try {
//            userDomain = (UserAccount) ocsSession.getUserObj(Constants.APP_TEAM_FOUNDER_INFO,Long.valueOf(getUser().getId()));
//            if(null==userDomain){
//                UserAccount curUser=getUser();
//                if((curUser.getIsTeamFounder()!=null&& curUser.getIsTeamFounder()==1)|| curUser.getTeamFounderId() == null){
//                    ocsSession.setUserObj(Constants.APP_TEAM_FOUNDER_INFO, Long.valueOf(curUser.getId()),curUser);
//                    return curUser;
//                }
//                Integer userId=curUser.getTeamFounderId();
//                userDomain=userInfoService.getUserByUserId(Long.valueOf(userId),Constants.UNIQUE_TABLE);
//            }
//            return userDomain;
//        } catch (Exception e) {
//            logger.error("获取用户会话信息，OSC出错:" ,e);
//        }
//
//        return userDomain;
//    }

    /**
     * 得到用户信息。
     * @date   : 2014年2月24日 下午8:28:47
     */
    public UserData getUser()  {
        UserAccount userDomain=null;
        try {
            userDomain =  ocsSession.getSuperObj(getRequest(), Constants.SESSION_USER);

            if (userDomain != null){
                TeamRoleInfo roleInfo = teamService.getRoleInfo(userDomain.getId());
                UserData userData = userDataService.getUserDataByKey(userDomain.getId().intValue());
                if(userData==null){
                    userData = new UserData();
                    userData.setUserId(userDomain.getId().intValue());
                    userData.setUname(userDomain.getUname());
                    userData.setEmail(userDomain.getEmail());
                    userData.setMobile(userDomain.getMobile());
                    userData.setDbTableConfig(TB.getStoreConfig(userDomain.getId().intValue()));
                    userData.setGuideInfo(userDomain.getGuideInfo());
                    userData.setCreated(new Date());
                    userData.setFounderId(roleInfo.getOwnerId().intValue());
                    userDataService.addUserData(userData);
                }else{
                    if(userData.getFounderId()==null || roleInfo.getOwnerId().intValue() != userData.getFounderId()){
                        userData.setFounderId(roleInfo.getOwnerId().intValue());
                        userDataService.updateUserDataByKey(userData);
                    }
                    return userData;
                }
            }
            return userDataService.getUserDataByKey(userDomain.getId().intValue());
        } catch (Exception e) {
            logger.error("获取用户会话信息，OSC出错:" ,e);
        }

        return null;
    }

    /**
     * 得到用户信息。
     *
     * @date : 2014年2月24日 下午8:28:47
     */
    public UserAccount getUser(HttpServletRequest request) {
        UserAccount userDomain = null;
        try {

            userDomain = (UserAccount) ocsSession.getSuperObj(request, Constants.SESSION_USER);

            return userDomain;
        } catch (Exception e) {
            logger.error("获取用户会话信息，OSC出错:", e);
        }

        return null;
    }
    /**
     * 设置某些service层返回的分页信息-->请求
     * @date   : 2014年2月24日 下午4:10:09
     * @author : zhongliang
     * @param modelResult
     */
//    public void setBaseListRequest(ResultModel modelResult) {
//        getRequest().setAttribute("allCount", modelResult.getAllCount());
//        getRequest().setAttribute("pageNo", modelResult.getPageNo());
//        getRequest().setAttribute("pageCount", modelResult.getPageCount());
//        getRequest().setAttribute("dataList", modelResult.getDataList());
//    }

    /**
     * 获取查询条件。
     * @date   : 2014年2月24日 上午10:38:51
     * @author : zhongliang
     */
    protected String getCondition() {
        return getRequest().getParameter("condition");
    }

    /**
     * 获取 请求对象。
     * @date   : 2014年2月20日 下午4:18:31
     * @author : zhongliang
     */
    protected HttpServletRequest getRequest() {
        return this.request.get();
    }

    /**
     * 获取响应对象。
     * @date   : 2014年2月20日 下午4:18:42
     * @author : zhongliang
     */
    protected HttpServletResponse getResponse() {
        return this.response.get();
    }


    /**
     * 设置Servlet APIs
     * @date   : 2014年2月20日 下午2:35:14
     * @author : zhongliang
     */
    @ModelAttribute
    private void setServletAPIs(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        this.request.set(request);
        this.response.set(response);
    }



    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
