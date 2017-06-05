package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.request.AmountGrowthItemRequest;
import com.raycloud.overseas.erp.data.request.NewItemRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zhanxf
 */
@Repository
public class WishItemDAO extends BaseDAO{

    /**
     * 行业销量飙升
     * @return
     */
    public List<String> getAmountGrowthItemList(AmountGrowthItemRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId","3");
        map.put("catId",request.getId());
        map.put("maxInsertDate",request.getMaxInsertDate());
        return  (List<String>) getSqlMapClientTemplate().queryForList("itemNS.growthItemList",map);

    }

    /**
     * 获取行业新增产品列表
     *
     * @param request
     * @return
     */
    public List<ItemDomain> getNewItemList(NewItemRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId","3");
        map.put("catId",request.getId());
        map.put("maxInsertDate",request.getMaxInsertDate());
        return  (List<ItemDomain>) getSqlMapClientTemplate().queryForList("itemNS.newItemList",map);
    }

    /**
     * 获取维表宝贝数据
     * @param itemId
     * @param merchantId
     * @return
     */
    public Item queryWishItem(String itemId,String merchantId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId","3");
        map.put("itemId",itemId);
        map.put("merchantId",merchantId);
        return (Item) getSqlMapClientTemplate().queryForObject("WISH_ITEM.queryItemByItemId",map);
    }

    /**
     * 获取维表宝贝数据
     * @param itemId
     * @param merchantId
     * @return
     */
    public Item queryWishItemDetail(String itemId,String merchantId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId","3");
        map.put("itemId",itemId);
        map.put("merchantId",merchantId);
        return (Item) getSqlMapClientTemplate().queryForObject("WISH_ITEM.getItemAdditionalInfoByItemId",map);
    }
}
