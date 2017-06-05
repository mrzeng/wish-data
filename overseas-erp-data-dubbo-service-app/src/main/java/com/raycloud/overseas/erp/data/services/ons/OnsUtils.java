package com.raycloud.overseas.erp.data.services.ons;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.impl.rocketmq.ONSChannel;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.constant.AppKeyConstant;
import com.raycloud.overseas.erp.data.common.constant.ClientNameEnum;
import com.raycloud.overseas.erp.data.domain.UserData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Properties;

/**
 * OnsUtils.java
 * Created by zhongliang
 * Created on 2016/8/4 下午4:15
 * Copyright(c)2014  版权所有
 */

@Component()
@Scope(value = "singleton")
public class OnsUtils {


    private static Logger logger = Logger.getLogger(OnsUtils.class);

    private static Producer producer;

    private static String topic;

    @Resource
    private String onsWebSocketPid;

    @Resource
    private String onsWebSocketTopic;

    public OnsUtils() {


    }

    @PostConstruct
    public void init(){
        String secret = AppKeyConstant.getAppSecret(ClientNameEnum.SUPERBOSS);
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, onsWebSocketPid);
        properties.put(PropertyKeyConst.AccessKey, AppKeyConstant.APP_KEY_SUPERBOSS);
        properties.put(PropertyKeyConst.SecretKey, secret);
        properties.put(PropertyKeyConst.OnsChannel, ONSChannel.CLOUD);
        producer = ONSFactory.createProducer(properties);
        //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        topic = onsWebSocketTopic;
        System.out.println("初始化PID:"+onsWebSocketPid+",TOPIC:"+onsWebSocketTopic);
        producer.start();
    }

    public  void sendOns(String tag,PopMsgVo popMsgVo){
        try{
            logger.biz("pop:{}",JSON.toJSONString(popMsgVo));
            byte[] bytes = JSON.toJSONString(popMsgVo).getBytes();
            Message msg = new Message(
                    //Message Topic
                    topic,
                    //Message Tag,
                    //可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
                    tag,
                    //Message Body
                    //任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
                    bytes
            );
            SendResult send = producer.send(msg);
            logger.biz("发送信息{}",JSON.toJSONString(send));
        }catch(Exception e){
            logger.error("推送异常",e);
            logger.error("{},ONS消息推送异常:{}",tag,e);
        }
    }

    public void sendOns(String tag,Object body, UserData userData){
        PopMsgVo popMsgVo = new PopMsgVo();
        popMsgVo.setUserId(userData.getUserId()+"");
        popMsgVo.setCreated(new Date());
        popMsgVo.setStatus("0");
        popMsgVo.setTag(tag);
        popMsgVo.setType(1);
        popMsgVo.setBody(body);
        popMsgVo.setSuccess("success");
        sendOns(tag,popMsgVo);
        logger.biz("发生成功,tag:{},用户id:{}",tag,userData.getUserId());
    }

}
