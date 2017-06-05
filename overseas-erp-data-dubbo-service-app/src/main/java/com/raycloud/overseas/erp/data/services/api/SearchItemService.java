package com.raycloud.overseas.erp.data.services.api;
import java.util.List;

import com.raycloud.overseas.erp.data.common.pojo.SearchItem;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.SearchItemQuery;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.SearchListRequest;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-03-01
 */
public interface SearchItemService{
    /**
     * 基本插入
     * @return
     */
	public Integer addSearchItem(SearchItem searchItem);

    /**
     * 批量添加收录宝贝
     * @param searchItemList
     */
    public void batchAddSearchItem(List<SearchItem> searchItemList);

    /**
     * 根据主键查询
     */
	public SearchItem getSearchItemByKey(Integer id);
    /**
     * 根据主键批量查询
     */
    public List<SearchItem> getSearchItemByKeys(List<Integer> idList);

    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(Integer id);
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deleteByKeys(List<Integer> idList);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateSearchItemByKey(SearchItem searchItem);
    /**
     * 根据条件查询分页查询
     * @param searchItemQuery 查询条件
     * @return
     */
    public Result<SearchItem> getSearchItemListWithPage(SearchItemQuery searchItemQuery);
    /**
     * 根据条件查询
     * @param searchItemQuery 查询条件
     * @return
     */
    public List<SearchItem> getSearchItemList(SearchItemQuery searchItemQuery);

    /**
     * 检索店铺或产品
     * @param request
     * @return
     */
    public Result search(SearchListRequest request) throws BizException;

    /**
     * 校验用户是否符合搜索条件
     * @return
     */
    public boolean checkUserSearchIegal(Integer userId);

    public void dealMerchantEventRequest(SearchListRequest request);

    /**
     * 获取某种事件类型的宝贝id列表
     * @param merchantId
     * @param eventType
     * @param eventTime
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<String> getItemIdListByEventType(String merchantId,String eventType,String eventTime,Integer pageNo,Integer pageSize);
}
