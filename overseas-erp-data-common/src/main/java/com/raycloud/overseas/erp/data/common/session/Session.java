package com.raycloud.overseas.erp.data.common.session;






import com.raycloud.overseas.erp.common.domain.UserAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 关于web的session定义一些接口，方便在tomcat session 个和集中式session之间切换。
 * 这里暂时只存放user信息。业务session中的先不考虑放里面。
 * @date   : 2013年12月9日 下午2:39:47
 * @author : hebad90@163.com
 */
public interface Session {

	void setSuperObj(Serializable value, HttpServletRequest request, HttpServletResponse response, Long visitorId, String key) throws Exception;


	UserAccount getSuperObj(HttpServletRequest request, String key) throws Exception;

	void removeSuperObj(HttpServletRequest request, HttpServletResponse response, String attName);

	/**
	 * 存放
	 * @date   : 2013年12月9日 下午4:39:24
	 * @author : hebad90@163.com
	 * @param value : 需要存放的对象（需要实现序列化。）
	 * @param request 
	 * @param response
	 * @param visitorId : taobaoId
	 * @param attrName : 存放的key。
	 * @throws Exception
	 */
	public void setAttribute(Serializable value, HttpServletRequest request, HttpServletResponse response, Long visitorId, String attrName) throws Exception;

	/**
	 * 
	 * @date   : 2013年12月9日 下午4:39:32
	 * @author : hebad90@163.com
	 * @param request
	 * @param attrName : 要去哪个key。
	 * @return
	 * @throws Exception
	 */

	public Object getAttribute(HttpServletRequest request, String attrName, Long taobaoId) throws Exception;



	public void setUserObj(String key, Long taobaoId, Object obj);

	public void setUserObj(String key,int time, Object obj);

	public Object getUserObj(String key,int time);

	Object getUserObj(String keyName, Long key);

	public void removeUserObj(String keyName, Long key);

	public Object getAppObj(String appKey);

	/**
	 * 设置应用级别的缓存 24小时
	 *
	 * @param appKey
	 * @param obj
	 */
	public void setAppObj(String appKey, Object obj);

	void setAppObj(String appKey, Object obj, int time);

	public void removeAppObj(String appKey);




}
