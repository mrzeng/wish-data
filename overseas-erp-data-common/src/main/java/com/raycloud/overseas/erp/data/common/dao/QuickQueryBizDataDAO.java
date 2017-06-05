package com.raycloud.overseas.erp.data.common.dao;



import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.*;

/**
 * zhanxf
 */
@Repository
public class QuickQueryBizDataDAO extends BaseDAO {

    /**
     * 查询行业销量暴增宝贝id列表
     * @param catId
     * @param date
     * @return
     */
    public List<String> queryAmountGrowthItemList(String catId,String date) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId",3L);
        map.put("catId",catId);
        map.put("maxInsertDate",date);
        return  (List<String>) getSqlMapClientTemplate().queryForList("itemNS.growthItemList",map);

    }

    /**
     * 查询行业新增产品列表
     * @param catId
     * @param date
     * @return
     */
    public List<ItemDomain> queryNewItemList(String catId, String date) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId",3L);
        map.put("catId",catId);
        map.put("maxInsertDate",date);
        return  (List<ItemDomain>) getSqlMapClientTemplate().queryForList("itemNS.newItemList",map);

    }

    /**
     * 查询行业7日日均动销率
     * @param catId
     * @return
     */
    public Double queryIndustryMovingRate(String catId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fkId",3L);
        map.put("catId",catId);
        List<Map<String,Object>> list = (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("itemNS.queryIndustryMovingRate",map);
        if(!ListUtil.isBlank(list)){
            Integer itemCount = 0,hotItemCount = 0;
            for(Map<String,Object> map1: list){
                itemCount += (Integer) map1.get("item_count");
                hotItemCount += (Integer) map1.get("hot_item_count");
            }
            return new BigDecimal(hotItemCount*100.0/itemCount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 0D;
    }
}
