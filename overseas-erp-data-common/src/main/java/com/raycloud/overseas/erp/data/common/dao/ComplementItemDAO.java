package com.raycloud.overseas.erp.data.common.dao;


import com.raycloud.overseas.erp.data.common.pojo.ComplementItem;
import java.util.List;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Repository
public class ComplementItemDAO  extends BaseDAO {


    public Integer addComplementItem(ComplementItem complementItem){
        return (Integer) getSqlMapClientTemplate().insert("ComplementItem.insertComplementItem", complementItem);
    }

    public void batchAddComplementItem(List<ComplementItem> complementItemList){
        batchInsert("ComplementItem.insertComplementItem", complementItemList);
    }

    public ComplementItem getItemById(String itemid){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemid", itemid);
        params.put("fkId", 4L);
        ComplementItem result = (ComplementItem)  getSqlMapClientTemplate().queryForObject("ComplementItem.getItemById", params);
        return result;
    }
            
}