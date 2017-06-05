package com.raycloud.overseas.erp.data.web.listener;

import com.alibaba.cobar.client.support.utils.MapUtils;
import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.data.common.constant.AppKeyConstant;
import com.raycloud.overseas.erp.data.common.constant.ClientNameEnum;
import com.raycloud.secretzk.client.IZkCallBack;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * SecretZkCallback.java
 * Created by zhongliang
 * Created on 2015/6/24 上午7:09
 * Copyright(c)2014  版权所有
 */
public class SecretZkCallback implements IZkCallBack {

    private Logger logger = Logger.getLogger(SecretZkCallback.class);

    @Override
    public boolean changeSecret(Map<String, String> newSecretMap) throws Exception {

        String logPre = "本次secret回调: ";

        if (MapUtils.isNotEmpty(newSecretMap)) {
            for (Map.Entry<String, String> entry : newSecretMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    if (AppKeyConstant.APP_KEY_SUPERBOSS.equalsIgnoreCase(key)) {
                        ClientNameEnum client = ClientNameEnum.SUPERBOSS;
                        //超级店长
                        AppKeyConstant.setAppSecret(client, value);
                        logger.biz(logPre + "超级店长secret回调成功:[" + AppKeyConstant.getAppKey(client) + "]");
                    }
                }
            }
        }

        return false;
    }

}