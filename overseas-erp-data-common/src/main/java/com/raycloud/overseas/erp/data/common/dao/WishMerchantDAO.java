package com.raycloud.overseas.erp.data.common.dao;


import com.raycloud.overseas.erp.data.common.pojo.MerchantDomain;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.request.SearchListRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zhanxf
 */
@Repository
public class WishMerchantDAO extends BaseDAO {

    public List<MerchantDomain> getBaseMerchantListByCondition(SearchListRequest request) {

        request.setFirstResult((request.getPageNo()-1)*request.getPageSize());

        return  (List<MerchantDomain>) getSqlMapClientTemplate().queryForList("merchant.selectMerchantList",request);
    }

    /**
     * 根据类型获取店铺
     *
     * @param type
     * @param sort
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<MerchantDomain> getMerchantListByType(String date, String type, String sort, int pageNo, int pageSize) {

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("maxInsertDate",date);
        map.put("order",type);
        map.put("sortType",type);
        map.put("firstResult",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("fkId","3");
        return  (List<MerchantDomain>) getSqlMapClientTemplate().queryForList("merchant.selectGuessMerchantList",map);
    }

    public Long getBaseMerchantCountByCondition(SearchListRequest request) {
        return  (Long) getSqlMapClientTemplate().queryForObject("merchant.countMerchantList",request);
    }

    public List<WishCategory> getAllMJWishCats() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fkId","3");
        return  (List<WishCategory>) getSqlMapClientTemplate().queryForList("merchant.allCats",map);
    }
}
