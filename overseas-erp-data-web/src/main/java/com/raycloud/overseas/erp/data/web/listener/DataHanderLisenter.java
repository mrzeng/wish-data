package com.raycloud.overseas.erp.data.web.listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.data.services.api.IRecordService;
import com.raycloud.overseas.usercenter.web.api.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * MymessgeHanderLisenter.java
 * Created by zhongliang
 * Created on 2016/8/3 下午3:01
 * Copyright(c)2014  版权所有
 */
@Component
public class DataHanderLisenter implements MessageListener{


    private static Logger logger = Logger.getLogger(DataHanderLisenter.class);

    @Autowired
    private IRecordService recordService;

    @Override
    public Action consume(Message message, ConsumeContext context) {


        try{
            if(message!=null && message.getTag().equals("data-include_product")){
                String body = new String(message.getBody());
                Shop shop = JSON.parseObject(body, Shop.class);
                logger.biz("接收data-include_product消息,shop{}",JSON.toJSONString(shop));
                recordService.recordUnExistMerchant(String.valueOf(shop.getShopId()),shop.getAccessToken(),String.valueOf(shop.getBindingUserId()));
            }

          }catch(Exception e){
            logger.error("消息接收异常"+JSON.toJSONString(message),e);
          }finally {
            return Action.CommitMessage;

        }

    }

}
