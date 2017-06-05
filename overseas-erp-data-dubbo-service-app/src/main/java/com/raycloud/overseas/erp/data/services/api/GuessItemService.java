package com.raycloud.overseas.erp.data.services.api;
import java.util.List;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.GuessItemQuery;
import com.raycloud.overseas.erp.data.domain.GuessItem;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-27
 */
public interface GuessItemService{
    /**
     * 基本插入
     * @return
     */
	public Integer addGuessItem(GuessItem guessItem);
	
    /**
     * 根据主键查询
     */
	public GuessItem getGuessItemByKey(String itemId);
    /**
     * 根据主键批量查询
     */
    public List<GuessItem> getGuessItemByKeys(List<String> itemIdList);

    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(String itemId);
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deleteByKeys(List<String> itemIdList);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateGuessItemByKey(GuessItem guessItem);
    /**
     * 根据条件查询分页查询
     * @param guessItemQuery 查询条件
     * @return
     */
    public Result<GuessItem> getGuessItemListWithPage(GuessItemQuery guessItemQuery);
    /**
     * 根据条件查询
     * @param guessItemQuery 查询条件
     * @return
     */
    public List<GuessItem> getGuessItemList(GuessItemQuery guessItemQuery);

    /**
     * 定时更新猜你喜欢的宝贝
     */
    public void timerGuessItemTask();

    /**
     * 获取猜你喜欢的宝贝列表
     * @param userId
     * @return
     */
    public List<GuessItem> getGuessItemList(Long userId);
}
