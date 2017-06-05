package com.raycloud.overseas.erp.data.services.context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.erp.data.common.spring.SpringUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.services.api.UserDataService;
import com.raycloud.overseas.usercenter.web.api.pojo.Shop;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanxf on 17/3/16.
 */
public class UserSysConfigContext {

    public final static String PUSH_RULE_USED = "PUSH_RULE_USED";

    public final static String ITEM_REFRESH = "ITEM_REFRESH";

    public static JSONObject getSysConfig(UserData userData){
        String sysConfig = userData.getSysConfig();
        if(StringUtils.isEmpty(sysConfig)){
            return null;
        }else{
            return JSON.parseObject(sysConfig);
        }
    }


    /*****************************设置推送规则******************************/
    /**
     * 校验用户是否初始化过推送数据
     * @param userData
     * @return
     */
    public static boolean checkPushRuleUsed(UserData userData){
        JSONObject sysConfig = getSysConfig(userData);
        if(sysConfig == null || !sysConfig.containsKey(PUSH_RULE_USED)){
            return false;
        }
        return true;
    }



    public static void setPushRuleUsed(UserData userData){
        JSONObject sysConfig = getSysConfig(userData);
        if(sysConfig == null){
            sysConfig = new JSONObject();
        }
        sysConfig.put(PUSH_RULE_USED,1);
        userData.setSysConfig(sysConfig.toJSONString());
        UserDataService userDataService = (UserDataService) SpringUtil.getBean("UserDataService");
        userDataService.updateUserDataByKey(userData);
    }

    /**
     * 校验用户推送数据是否刷新过
     * @param userData
     * @return
     */
    public static boolean checkItemRefreshed(UserData userData){
        JSONObject sysConfig = getSysConfig(userData);
        if(sysConfig == null || !sysConfig.containsKey(ITEM_REFRESH)){
            return false;
        }
        String date = DateUtil.getCurrentDate();
        return date.equals(sysConfig.get(ITEM_REFRESH));
    }

    public static void setItemRefresh(UserData userData){
        JSONObject sysConfig = getSysConfig(userData);
        if(sysConfig == null){
            sysConfig = new JSONObject();
        }
        sysConfig.put(ITEM_REFRESH,DateUtil.getCurrentDate());
        userData.setSysConfig(sysConfig.toJSONString());
        UserDataService userDataService = (UserDataService) SpringUtil.getBean("UserDataService");
        userDataService.updateUserDataByKey(userData);
    }

//    /**
//     * 校验推送规则是否正在运行
//     * @param userData
//     * @return
//     */
//    public static boolean checkPushRuleRunning(UserData userData){
//        JSONObject sysConfig = getSysConfig(userData);
//        if(sysConfig == null || !sysConfig.containsKey(PUSH_RULE_USED)){
//            return false;
//        }
//        return true;
//    }
//
//    public static void setPushRuleUsed(UserData userData){
//        JSONObject sysConfig = getSysConfig(userData);
//        if(sysConfig == null){
//            sysConfig = new JSONObject();
//        }
//        sysConfig.put("PUSH_RULE_USED",1);
//        userData.setSysConfig(sysConfig.toJSONString());
//        UserDataService userDataService = (UserDataService) SpringUtil.getBean("UserDataService");
//        userDataService.updateUserDataByKey(userData);
//    }

}
