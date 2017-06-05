package com.raycloud.overseas.erp.data.common.session.impl;

import com.raycloud.bizlogger.Logger;
import com.raycloud.handle.impl.SuperMemSessionHandleImpl;
import com.raycloud.oscsession.context.SessionContext;
import com.raycloud.overseas.erp.common.domain.UserAccount;
import com.raycloud.overseas.erp.data.common.session.Session;
import com.raycloud.overseas.usercenter.web.api.vo.OcsSessionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InvalidClassException;
import java.io.Serializable;

/**
 * 集中式的session管理。
 * @date   : 2014年05月21日 下午9:36:03
 * @author : yangsen
 */
@Component("ocsSession")
public class OcsSession implements Session {
	
	private Logger logger= Logger.getLogger(OcsSession.class);
	
	@Autowired
	@Qualifier("memSession")
	private SuperMemSessionHandleImpl memSessionHandle;

	@Autowired
	OcsSessionConfig config;

	@PostConstruct
	public void init(){
		String appKey = config.getAppKey();
		String superAppKey = config.getSuperAppKey();
		String supercookieopen = config.getSupercookieopen();
		String superdomainhost= config.getSuperdomainhost();
		SessionContext.SESSION_APP_KEY = appKey;
		System.out.println("初始化 appkey :"+ superAppKey + " domain :" +superdomainhost);
		if(superAppKey != null) {
			SessionContext.SUPER_SESSION_APP_KEY = superAppKey;
			if(superAppKey!=null && !superAppKey.equals("1115")) {
				SessionContext.SUPER_COOKIE_NAME = SessionContext.SUPER_COOKIE_NAME+superAppKey;
				SessionContext.SUPER_AGENT_NAME = SessionContext.SUPER_AGENT_NAME+superAppKey;
				SessionContext.COOKIE_NAME = SessionContext.COOKIE_NAME+superAppKey;
				SessionContext.REFRESHTIME_SESSIONID = SessionContext.REFRESHTIME_SESSIONID+superAppKey;
				SessionContext.DZ_USER = SessionContext.DZ_USER+superAppKey;
				SessionContext.ATT_NAME_COOKIE = SessionContext.ATT_NAME_COOKIE+superAppKey;
			}
		}
		if(supercookieopen != null) {
			SessionContext.SUPER_COOKIE_OPEN = Boolean.valueOf(supercookieopen);
		}
		if(superdomainhost != null) {
			SessionContext.SUPER_DOMAIN_HOST = superdomainhost;
		}
	}


	/**
	 * 只允许获取用户使用
	 * @param value
	 * @param request
	 * @param response
	 * @param visitorId
	 * @param key
	 * @throws Exception
	 */
	@Override
	public void setSuperObj(Serializable value, HttpServletRequest request, HttpServletResponse response, Long visitorId, String key) throws Exception {

		try {
			long s1 = System.currentTimeMillis();
			memSessionHandle.setSuperObj(request, response, key, visitorId, value);
			logger.biz("[" + visitorId + "],setAttribute;key=[" + key + "]耗时：[" + (System.currentTimeMillis() - s1) + " ms]");
		} catch (Exception e) {
			logger.error("OcsSession => setAttribute出错。 ", e);
			throw e;
		}
	}

	/**
	 * 只允许获取用户使用
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserAccount getSuperObj(HttpServletRequest request, String key) throws Exception {
		try {
			long s1 = System.currentTimeMillis();
			UserAccount result = (UserAccount) memSessionHandle.getSuperObj(request, key);
			//logger.biz("getAttribute,key["+key+"],耗时：["+(System.currentTimeMillis()-s1)+" ms]");
			return result;
		} catch (InvalidClassException e) {
			logger.warn("获取值的时候由于classpath中的类序列号与此时ocs的不一致，导致反序列化失败。[可忽略。]");
			return null;
		} catch (Exception e) {
			logger.error("OcsSession => getAttribute出错。 ", e);
			throw e;
		}
	}

	@Override
	public void removeSuperObj(HttpServletRequest request, HttpServletResponse response, String attName){

		try {
			memSessionHandle.removeSuperObj(request,response,attName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存储会话级别缓存
	 * @param value : 需要存放的对象（需要实现序列化。）
	 * @param request
	 * @param response
	 * @param visitorId : taobaoId
	 * @param key
	 * @throws Exception
	 */
	@Override
	public void setAttribute(Serializable value, HttpServletRequest request, HttpServletResponse response, Long visitorId, String key) throws Exception  {
		try {
			long s1=System.currentTimeMillis();
			//memSessionHandle.setObj(request, response, key, visitorId, value);
			memSessionHandle.setSessionUserObj(request, response, key, visitorId, value);
			logger.biz("["+visitorId+"],setAttribute;key=["+key+"]耗时：["+(System.currentTimeMillis()-s1)+" ms]");
		} catch (Exception e) {
			logger.error("OcsSession => setAttribute出错。 ",e);
			throw e;
		}
	}

	@Override
	public Object getAttribute(HttpServletRequest request, String key, Long taobaoId) throws Exception {
		try {
			long s1=System.currentTimeMillis();
			Object result=memSessionHandle.getSessionUserObj(request, key,taobaoId );
			//logger.biz("getAttribute,key["+key+"],耗时：["+(System.currentTimeMillis()-s1)+" ms]");
			return result;
		}catch(InvalidClassException e ) {
			logger.warn("获取值的时候由于classpath中的类序列号与此时ocs的不一致，导致反序列化失败。[可忽略。]");
			return null;
		} catch (Exception e) {
			logger.error("OcsSession => getAttribute出错。 ",e);
			throw e;
		}
	}


	/**
	 * remove
	 * @date   : 2014年05月21日 下午9:36:03
	 * @author : yangsen
	 * @param request
	 * @param attrName
	 * @throws Exception 
	 */
	public void removeAttribute(HttpServletRequest request,  String attrName,Long taobaoId) throws Exception {
		try {
			memSessionHandle.removeSessionUserObj(request, attrName, taobaoId);
		} catch (Exception e) {
			logger.error("OcsSession => removeAttribute出错。 ",e);
			throw e;
		}
	}


	/**
	 * 存储用户级别缓存
	 * @param key
	 * @param taobaoId
	 * @param obj
	 */
	@Override
	public void setUserObj(String key, Long taobaoId, Object obj) {

		try {
			memSessionHandle.setUserObj(key, taobaoId, obj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(taobaoId + ",key设置setUserObj:" + key + ",出错");
		}
	}


	@Override
	public Object getUserObj(String keyName, Long key) {
		try {
			return memSessionHandle.getUserObj(keyName, key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OCS=getUserObj异常:" + e.getMessage());
		}
		return null;
	}

	@Override
	public void removeUserObj(String keyName, Long key) {
		try {
			memSessionHandle.removeUserObj(keyName, key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OCS=removeUserObj异常:" + e.getMessage());
		}

	}

	/**
	 * 存储应用级别缓存
	 * @param appKey
	 * @return
	 */
	@Override
	public Object getAppObj(String appKey) {
		try {
			return memSessionHandle.getAppObj(appKey);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(appKey + ",获得getAppObj,出错");

		}
		return null;
	}

	@Override
	public void setAppObj(String appKey, Object obj) {
		try {
			memSessionHandle.setAppObj(appKey, obj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(appKey + ",设置setAppObj,出错");
		}

	}

	@Override
	public void setAppObj(String appKey, Object obj,int time) {
		try {
			memSessionHandle.setAppObj(appKey,time ,obj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(appKey + ",设置setAppObj,出错");
		}

	}

	@Override
	public void removeAppObj(String appKey) {
		try {
			memSessionHandle.removeAppObj(appKey);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(appKey + ",删除removeAppObj,出错");
		}
	}

	@Override
	public void setUserObj(String key, int time, Object obj) {
		try {
			memSessionHandle.setUserObj(key,-1l,time ,obj);
		} catch (Exception e) {
			logger.error(key + ",设置setAppObj,出错",e);
		}
	}

	@Override
	public Object getUserObj(String key, int time) {
		Object object = null;
		try {
			object = memSessionHandle.getUserObj(key,-1l,time);
		} catch (Exception e) {
			logger.error(key + ",设置setAppObj,出错",e);
		}
		return object;
	}
}
