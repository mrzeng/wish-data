package com.raycloud.overseas.erp.data.services.api;
import java.util.List;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.GuessMerchantQuery;
import com.raycloud.overseas.erp.data.domain.GuessMerchant;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-27
 */
public interface GuessMerchantService{
    /**
     * 基本插入
     * @return
     */
	public Integer addGuessMerchant(GuessMerchant guessMerchant);
	
    /**
     * 根据主键查询
     */
	public GuessMerchant getGuessMerchantByKey(String merchantId);
    /**
     * 根据主键批量查询
     */
    public List<GuessMerchant> getGuessMerchantByKeys(List<String> merchantIdList);

    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(String merchantId);
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deleteByKeys(List<String> merchantIdList);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateGuessMerchantByKey(GuessMerchant guessMerchant);
    /**
     * 根据条件查询分页查询
     * @param guessMerchantQuery 查询条件
     * @return
     */
    public Result<GuessMerchant> getGuessMerchantListWithPage(GuessMerchantQuery guessMerchantQuery);
    /**
     * 根据条件查询
     * @param guessMerchantQuery 查询条件
     * @return
     */
    public List<GuessMerchant> getGuessMerchantList(GuessMerchantQuery guessMerchantQuery);

    /**
     * 定时刷新猜你喜欢店铺
     */
    public void timerGuessMerchantTask();

    /**
     * 获取用户猜你喜欢的店铺列表
     * @param userId
     * @return
     */
    public List<GuessMerchant> getGuessMerchantList(Long userId);


}
