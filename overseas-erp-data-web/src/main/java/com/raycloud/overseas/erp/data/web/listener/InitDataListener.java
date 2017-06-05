package com.raycloud.overseas.erp.data.web.listener;

import com.raycloud.overseas.erp.data.common.util.HttpClientUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * 初始化一些数据。
 * @date   : 2014年3月7日 下午2:11:43
 * @author : hebad90@163.com
 */
public class InitDataListener implements ServletContextListener {

	//private Logger logger = Logger.getLogger(InitDataListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//1. 初始化根路径。
		HttpClientUtil.WEB_ROOT= sce.getServletContext().getRealPath(File.separator);
		//logger.biz("根路径初始化完成---:" + Constant.WEB_APP_PATH );

        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
//        String app_key_flag=applicationContext.getBean("appType").toString();
//        if("qn".equals(app_key_flag)){
//            com.raycloud.template.common.Constant.APP_KEY = "21674128";
//            com.raycloud.template.common.Constant.APP_SECRET = "501c89abb5c06a089463e07455b97328";
//        }


		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
