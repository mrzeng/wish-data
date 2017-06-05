package com.raycloud.overseas.erp.data.services.impl;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.pojo.FocusItem;
import com.raycloud.overseas.erp.data.common.query.FocusItemQuery;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.services.api.ILocalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * PushItemServiceCancleItemImpl.java
 * Created by zhongliang
 * Created on 2017/3/24 下午7:05
 * Copyright(c)2014  版权所有
 */
@Service
public class PushItemServiceCancleItemImpl {

    private static Logger logger= Logger.getLogger(PushItemServiceCancleItemImpl.class);

    @Autowired
    private ILocalItemService localItemService;
    //	@Scheduled(cron = "5 0/5 * * * ?")
    public void execute(String... strings) throws Exception {
        Calendar calendar = Calendar.getInstance();

        FocusItemQuery focusItemQuery = new FocusItemQuery();
        focusItemQuery.setFocus(1);
        focusItemQuery.setExpireTimeEnd(calendar.getTime());

        List<FocusItem> expireFocusItemList = localItemService.getFocusItemList(focusItemQuery);
        if (!ListUtil.isBlank(expireFocusItemList)) {
            for (FocusItem focusItem : expireFocusItemList) {
                logger.biz("关注宝贝24小时到期,恢复为未关注状态,宝贝id:{},用户id:{}", focusItem.getItemId(), focusItem.getUserId());
                focusItem.setFocus(0);
            }
            logger.biz("本次取消关注宝贝{}个", expireFocusItemList.size());
            localItemService.batchUpdateFocusItem(expireFocusItemList);
        }
    }
}
