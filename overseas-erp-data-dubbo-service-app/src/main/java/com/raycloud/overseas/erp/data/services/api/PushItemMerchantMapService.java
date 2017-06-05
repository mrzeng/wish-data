package com.raycloud.overseas.erp.data.services.api;


import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushItemMerchantMap;
import com.raycloud.overseas.erp.data.common.query.PushItemMerchantMapQuery;

import java.util.List;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-13
 */
public interface PushItemMerchantMapService{

    /**
     * 批量删除用户推送宝贝店铺映射宝贝
     * @param userId
     */
    public void deletePushItemMerchantMapByUserId(Integer founderId,Long userId);

    /**
     * 批量插入推送宝贝店铺映射
     * @param list
     */
    public void batchInsertPushItemMerchantMap(List<PushItemMerchantMap> list);

    /**
     * 基本插入
     * @return
     */
	public Integer addPushItemMerchantMap(PushItemMerchantMap pushItemMerchantMap);

    /**
     * 根据条件查询分页查询
     * @param pushItemMerchantMapQuery 查询条件
     * @return
     */
    public Result<PushItemMerchantMap> getPushItemMerchantMapListWithPage(PushItemMerchantMapQuery pushItemMerchantMapQuery);
    /**
     * 根据条件查询
     * @param pushItemMerchantMapQuery 查询条件
     * @return
     */
    public List<PushItemMerchantMap> getPushItemMerchantMapList(PushItemMerchantMapQuery pushItemMerchantMapQuery);
}
