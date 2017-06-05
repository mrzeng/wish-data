package com.raycloud.overseas.erp.data.common.constant;



import java.util.HashMap;
import java.util.Map;

/**
 * 应用常量类
 * Created by zhongliang
 * Created on 2015/6/24 上午7:05
 * Copyright(c)2014  版权所有
 */
public final class AppKeyConstant {

    /**
     * 超级店长的appkey
     */
    public static final String APP_KEY_SUPERBOSS = "12011554";

    /**
     * 线上环境
     */
    public static final String PARAMETER_API_SERVICE_URL = "http://gw.api.taobao.com/router/rest";
    /**
     * 沙箱环境
     */
    public static final String PARAMETER_CONTAINER_SANDBOX_URL = "http://container.api.tbsandbox.com/container";

    private static final Map<ClientNameEnum, String> appkeyMap = new HashMap<ClientNameEnum, String>(2);
    private static final Map<ClientNameEnum, String> appSecretMap = new HashMap<ClientNameEnum, String>(2);

    static {
        //初始化店长
        appkeyMap.put(ClientNameEnum.SUPERBOSS, APP_KEY_SUPERBOSS);
        appSecretMap.put(ClientNameEnum.SUPERBOSS, ""); //a6b35e3cff015ed3bf93ba587d68e77c
    }

    public static String getAppSecret(final ClientNameEnum clientNameEnum) {
        return appSecretMap.get(clientNameEnum);
    }

    public static String getAppKey(final ClientNameEnum clientNameEnum) {
        return appkeyMap.get(clientNameEnum);
    }

    public static void setAppSecret(ClientNameEnum clientNameEnum, String newSecret) {
        appSecretMap.put(clientNameEnum, newSecret);
    }

}
