/**
 * Copyright (c) 2008-2011 yeyaomai.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringContextHolder.java 1211 2011-11-25 16:20:45Z gao_wei $
 */
package com.raycloud.overseas.erp.data.common.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;


/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * 
 * @author calvin
 * @author gao_wei
 */
public class SpringContextHolder implements ApplicationContextAware,
		DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory
			.getLogger(SpringContextHolder.class);

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return (T) applicationContext.getBeansOfType(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:"
				+ applicationContext);
		applicationContext = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("注入ApplicationContext到SpringContextHolder:"
				+ applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		Assert.state(applicationContext != null,
				"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
	}

	/**
	 * 判断是否处于某个profile.
	 */
//	public static boolean isContains(String profile) {
//		String[] profiles = SpringContextHolder.getApplicationContext()
//				.getEnvironment().getActiveProfiles();
//		for (String s : profiles) {
//			if (s.equals(profile))
//				return true;
//		}
//		return false;
//	}

}
