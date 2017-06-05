package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushItemMerchantMap;
import com.raycloud.overseas.erp.data.common.query.PushItemMerchantMapQuery;
import com.raycloud.overseas.erp.data.common.query.PushItemQuery;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-13
 */

@Repository
public class PushItemMerchantMapDAO  extends BaseDAO{


	public Integer addPushItemMerchantMap(PushItemMerchantMap pushItemMerchantMap) {
		return (Integer) getSqlMapClientTemplate().insert("PushItemMerchantMap.insertPushItemMerchantMap", pushItemMerchantMap);
	}

    public void batchInsertPushItemMerchantMap(List<PushItemMerchantMap> list){
        batchInsert("PushItemMerchantMap.insertPushItemMerchantMap",list);
    }

    public void deletePushItemByUserId(Integer founderId,Long userId){
        PushItemMerchantMapQuery pushItemMerchantMapQuery = new PushItemMerchantMapQuery();
        pushItemMerchantMapQuery.setFounderId(founderId);
        pushItemMerchantMapQuery.setUserId(userId);
        getSqlMapClientTemplate().delete("PushItemMerchantMap.deletePushItemByUserId",pushItemMerchantMapQuery);
    }

    /**
     * 根据主键更新
     * @return
     * @
     */
    public Integer updatePushItemMerchantMapByKey(PushItemMerchantMap pushItemMerchantMap) {
		return  getSqlMapClientTemplate().update("PushItemMerchantMap.updatePushItemMerchantMapByKey", pushItemMerchantMap);
	}
    @SuppressWarnings("unchecked")
    public Result<PushItemMerchantMap> getPushItemMerchantMapListWithPage(PushItemMerchantMapQuery pushItemMerchantMapQuery){
	    Result<PushItemMerchantMap> rs = new Result<PushItemMerchantMap>(pushItemMerchantMapQuery);
		            rs.setTotal(Long.parseLong(""+  getSqlMapClientTemplate().queryForObject("PushItemMerchantMap.getPushItemMerchantMapListCount",pushItemMerchantMapQuery)));
            if(pushItemMerchantMapQuery.getFields()!=null && pushItemMerchantMapQuery.getFields()!=""){
                rs.setItems((List<PushItemMerchantMap>) getSqlMapClientTemplate().queryForList("PushItemMerchantMap.getPushItemMerchantMapListWithPageFields", pushItemMerchantMapQuery));
            }else{
                rs.setItems((List<PushItemMerchantMap>) getSqlMapClientTemplate().queryForList("PushItemMerchantMap.getPushItemMerchantMapListWithPage", pushItemMerchantMapQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<PushItemMerchantMap> getPushItemMerchantMapList(PushItemMerchantMapQuery pushItemMerchantMapQuery) {
    if(pushItemMerchantMapQuery.getFields()!=null&&pushItemMerchantMapQuery.getFields()!=""){
        return (List<PushItemMerchantMap>) getSqlMapClientTemplate().queryForList("PushItemMerchantMap.getPushItemMerchantMapListFields", pushItemMerchantMapQuery);
    }
		return (List<PushItemMerchantMap>) getSqlMapClientTemplate().queryForList("PushItemMerchantMap.getPushItemMerchantMapList", pushItemMerchantMapQuery);
	}


}