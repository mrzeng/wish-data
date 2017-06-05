package com.raycloud.overseas.erp.data.web.util;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.impl.rocketmq.ONSChannel;
import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.data.common.constant.AppKeyConstant;
import com.raycloud.overseas.erp.data.common.constant.ClientNameEnum;
import com.raycloud.overseas.erp.data.web.listener.DataHanderLisenter;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Properties;

/**
 * OnsMessgerClient.java
 * Created by zhongliang
 * Created on 2016/2/25 下午4:06
 * Copyright(c)2014  版权所有
 */
@Component
public class OnsMessgerClient {

    static final Logger logger = Logger.getLogger(OnsMessgerClient.class);

    @Resource
    private DataHanderLisenter dataHanderLisenter;

    @Resource
    private String onsMasterCid;

    @Resource
    private String onsMasterTopic;

    //下面是我把ONS转为tmc的代码

    @PostConstruct
    public void afterPropertiesSet() throws Exception {



        String secret = AppKeyConstant.getAppSecret(ClientNameEnum.SUPERBOSS);
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, onsMasterCid);// ons控制台订阅管理中获取ConsumerID,
        // 找我分配,这里的ConsumerId =metaq的 group
        properties.put(PropertyKeyConst.AccessKey, AppKeyConstant.APP_KEY_SUPERBOSS);// 应用appkey
        properties.put(PropertyKeyConst.SecretKey, secret);// 应用密钥
        properties.put(PropertyKeyConst.OnsChannel, ONSChannel.CLOUD); // cloud为聚石塔标识
        final Consumer consumer = ONSFactory.createConsumer(properties);
        logger.biz("消费端:{},topic:{}",onsMasterCid,onsMasterTopic);
        //订阅的参数 rmq_sys_jst_+appkey + 消息类型 ||多个分割 ,不支持正则. 千万不要用*
        consumer.subscribe(onsMasterTopic, "data-include_product",dataHanderLisenter);
        // messageImplListener.moniterTradeModifiedTids();
        consumer.start();
    }


    @PreDestroy
    public void checkOns() {

    }

    /**
     * 延迟时间计算
     */
    private Integer getDelayType(Message message, long delayTime) {
        Integer delayType = 0;
        if (delayTime > 1000) {
            delayType = 1;
        }
        if (delayTime > 3000) {
            delayType = 2;
        }
        if (delayTime > 5000) {
            delayType = 3;
        }
        if (delayTime > 10000) {
            delayType = 4;
        }
        if (delayTime > 30000) {
            delayType = 5;
        }
        return delayType;
    }

}
