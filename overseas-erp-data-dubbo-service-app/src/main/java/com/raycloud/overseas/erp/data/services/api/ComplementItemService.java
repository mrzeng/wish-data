package com.raycloud.overseas.erp.data.services.api;

import com.raycloud.overseas.erp.data.common.pojo.ComplementItem;
import com.raycloud.overseas.erp.data.domain.UserData;

import java.util.List;

/**
 * @author Ou zhouyou (ouzhouyou@gmail.com)
 * @since 2017-02-04
 */
public interface ComplementItemService {
    /**
     * 基本插入
     * @return
     */
	public Integer addComplementItem(ComplementItem complementItem);

    public ComplementItem getItemById(String itemid);

    public void batchAddComplementItemList(List<ComplementItem> complementItemList);

    /**
     * 将关注的宝贝写到卖家网数据库,补全描述数据,以及避免数据被删除
     * @param itemIdList
     * @param userData
     */
    public void storeItemPermanent(List<String> itemIdList, UserData userData);
}
