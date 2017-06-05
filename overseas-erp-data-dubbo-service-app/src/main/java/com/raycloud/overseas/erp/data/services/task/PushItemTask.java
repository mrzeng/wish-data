package com.raycloud.overseas.erp.data.services.task;

import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.spring.SpringUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.services.api.PushItemService;
import com.raycloud.overseas.erp.data.services.api.PushRuleService;

import java.util.List;


/**
 * 推送宝贝任务
 */
public class PushItemTask {

    private static Logger logger = Logger.getLogger(PushItemTask.class);

    private PushItemService pushItemService;

    public PushItemTask() {

        this.pushItemService = (PushItemService) SpringUtil.getBean("PushItemService");
    }


    public Boolean refreshPushItem(UserData userData){

        try {
            pushItemService.refreshPushItemList(userData);
        } catch (Exception e) {
            logger.error("推送数据刷新失败,用户id:"+userData.getUserId(),e);
            return false;
        }
        return true;
    }
}
