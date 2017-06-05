package com.raycloud.overseas.erp.data.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtil.java
 * Created by zhongliang
 * Created on 2016/7/24 下午9:39
 * Copyright(c)2014  版权所有
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    /**
     * spring容器。
     */
    private static ApplicationContext applicationContext;

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

}