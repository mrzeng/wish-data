package com.raycloud.overseas.erp.data.services.api;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal1;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal7;
import com.raycloud.overseas.erp.data.common.query.PushTotal1Query;
import com.raycloud.overseas.erp.data.common.query.PushTotal7Query;
import com.raycloud.overseas.erp.data.domain.UserData;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-04-08
 */
public interface PushWorkBenchService {

    /**
     * 创建7日推送数据
     * @param date
     * @param userData
     */
    public void createPushTotal7(Date date,UserData userData);


    /**
     * 创建每日推送数据
     * @param date
     * @param userData
     */
    public void createPushTotal1(Date date, UserData userData);

    /**
     * 基本插入
     * @return
     */
	public Integer addPushTotal1(PushTotal1 pushTotal1, UserData userData);
	
    /**
     * 根据主键删除
     * @return
     */
	public Integer deletePushTotal1ByKey(Integer id);
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deletePushTotal1ByKeys(List<Integer> idList);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updatePushTotal1ByKey(PushTotal1 pushTotal1, UserData userData);
    /**
     * 根据条件查询
     * @param pushTotal1Query 查询条件
     * @return
     */
    public List<PushTotal1> getPushTotal1List(PushTotal1Query pushTotal1Query, UserData userData);


    /**
     * 基本插入
     * @return
     */
    public Integer addPushTotal7(PushTotal7 pushTotal7, UserData userData);

    /**
     * 根据主键删除
     * @return
     */
    public Integer deletePushTotal7ByKey(Integer id);
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deletePushTotal7ByKeys(List<Integer> idList);

    /**
     * 根据主键更新
     * @return
     */
    public Integer updatePushTotal7ByKey(PushTotal7 pushTotal7, UserData userData);


    /**
     * 根据条件查询
     * @param pushTotal7Query 查询条件
     * @return
     */
    public List<PushTotal7> getPushTotal7List(PushTotal7Query pushTotal7Query, UserData userData);

    /**
     * 刷新推送统计数据
     * @param userData
     */
    public void refreshPushTotal(UserData userData);

    /**
     * 统计推送数据
     * @param userId
     * @return
     */
    public Map<String,Object> getPushCollectCount(Integer userId);

    /**
     * 根据类型查询推送统计
     * @param type(1今天,2昨天,3近3天,7近7天)
     * @return
     */
    public PushTotal7 queryPushTotal7ByType(int type,UserData userData);
}
