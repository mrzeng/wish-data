package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushItem;
import com.raycloud.overseas.erp.data.common.query.PushItemQuery;
import com.raycloud.overseas.erp.data.common.query.PushItemRuleTraceQuery;
import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.domain.UserData;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-13
 */

@Repository
public class PushItemDAO  extends BaseDAO{


	public Integer addPushItem(PushItem pushItem){
		return (Integer) getSqlMapClientTemplate().insert("PushItem.insertPushItem", pushItem);
	}

    public void batchInsertPushItem(List<PushItem> pushItemList){
        batchInsert("PushItem.insertPushItem",pushItemList);
    }

    public void batchUpdatePushItem(List<PushItem> pushItemList){
        batchUpdate("PushItem.updatePushItemByKey",pushItemList);
    }

    public List<String> getPushItemIdListByUserId(PushItemQuery pushItemQuery ){

        return  (List<String>) getSqlMapClientTemplate().queryForList("PushItem.getPushItemIdListByUserId", pushItemQuery);
    }

    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<PushItem> getPushItemByKeys(List<String> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return  (List<PushItem>) getSqlMapClientTemplate().queryForList("PushItem.getPushItemsByKeys", params);
    }

    @SuppressWarnings("unchecked")
    public void deletePushItem(PushItemQuery pushItemQuery){
        getSqlMapClientTemplate().delete("PushItem.deletePushItem", pushItemQuery);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updatePushItemByKey(PushItem pushItem){
		return  getSqlMapClientTemplate().update("PushItem.updatePushItemByKey", pushItem);
	}
    @SuppressWarnings("unchecked")
    public Result<PushItem> getPushItemListWithPage(PushItemQuery pushItemQuery){
        Result<PushItem> rs = new Result<PushItem>(pushItemQuery);
        rs.setTotal(Long.parseLong(""+getSqlMapClientTemplate().queryForObject("PushItem.getPushItemListCount",pushItemQuery)));
        if(pushItemQuery.getFields()!=null && pushItemQuery.getFields()!=""){
            rs.setItems((List<PushItem>) getSqlMapClientTemplate().queryForList("PushItem.getPushItemListWithPageFields", pushItemQuery));
        }else{
            rs.setItems((List<PushItem>) getSqlMapClientTemplate().queryForList("PushItem.getPushItemListWithPage", pushItemQuery));
        }
        return rs;
	}

    public Integer getPushItemListCount(PushItemQuery pushItemQuery){
        return (Integer) getSqlMapClientTemplate().queryForObject("PushItem.getPushItemListCount",pushItemQuery);
    }

    @SuppressWarnings("unchecked")
    public List<PushItem> getPushItemList(PushItemQuery pushItemQuery){
    if(pushItemQuery.getFields()!=null&&pushItemQuery.getFields()!=""){
        return (List<PushItem>) getSqlMapClientTemplate().queryForList("PushItem.getPushItemListFields", pushItemQuery);
    }
		return (List<PushItem>) getSqlMapClientTemplate().queryForList("PushItem.getPushItemList", pushItemQuery);
	}

    public Map<String,Long> getItemCountByTime(UserData userData){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId",userData.getUserId());
        map.put("founderId",userData.getFounderId());
        map.put("fkId",1L);
        map.put("tableId", TB.getTableId(TB.PI_FIELD,userData));
        return (Map<String,Long>) getSqlMapClientTemplate().queryForMap("PushItem.getItemCountByTime", map,"pushTime","total");
    }

}