package com.raycloud.overseas.erp.data.services.api;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushConditionDomain;
import com.raycloud.overseas.erp.data.common.pojo.PushItem;
import com.raycloud.overseas.erp.data.common.pojo.PushRule;
import com.raycloud.overseas.erp.data.common.query.PushItemQuery;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.services.context.RefreshItemContext;

import java.util.List;
import java.util.Map;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-13
 */
public interface PushItemService{

    public RefreshItemContext createRefreshContext(UserData userData) throws BizException;

    /**
     * 处理复杂查询
     * 1:工作台采集，2工作台概览规则，3工作台每日推送总和点击，4工作台每日推送规则数量名称按钮点击，5推送规则7日总和跳转
     * @param pushItemQuery
     */
    public void dealComplexQuery(PushItemQuery pushItemQuery);

    /**
     * 刷新推送数据
     * @param userData
     */
    public void refreshPushItemList(UserData userData) throws BizException;

    /**
     * 移除推送宝贝到历史推送列表
     * @param itemIdList
     */
    public void removePushItemList(UserData userData, List<String> itemIdList);

    /**
     * 基本插入
     * @return
     */
	public Integer addPushItem(PushItem pushItem);

    /**
     * 根据条件查询分页查询
     * @param pushItemQuery 查询条件
     * @return
     */
    public Result<PushItem> getPushItemListWithPage(PushItemQuery pushItemQuery,UserData userData) throws BizException;
    /**
     * 根据条件查询
     * @param pushItemQuery 查询条件
     * @return
     */
    public List<PushItem> getPushItemList(PushItemQuery pushItemQuery);

    /**
     * 查询状态列表
     * @return
     */
    public List<Map<String,Object>> getStatusList(List<String> itemIdList,UserData userData);

    /**
     * 定时刷新推送宝贝任务
     */
    public void timerRefreshPushItemTask();

    /**
     * 定时删除关注宝贝
     */
    public void timerCancleItemFocusTask();

    /**
     * 采集推送宝贝
     * @param userData
     * @param itemIdList
     */
    public void collectPushItems(int sum,final UserData userData, List<String> itemIdList, List<String> merchantIdList,final boolean needBean) throws BizException;
}
