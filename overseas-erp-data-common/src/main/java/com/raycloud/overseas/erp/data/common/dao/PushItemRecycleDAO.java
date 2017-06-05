
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushItemRecycleQuery;
import com.raycloud.overseas.erp.data.common.pojo.PushItemRecycle;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-21
 */

@Repository
public class PushItemRecycleDAO  extends BaseDAO{


	public Integer addPushItemRecycle(PushItemRecycle pushItemRecycle){
		return (Integer) getSqlMapClientTemplate().insert("PushItemRecycle.insertPushItemRecycle", pushItemRecycle);
	}

    public void batchAddPushItemRecycle(List<PushItemRecycle> pushItemRecycleList){
        batchInsert("PushItemRecycle.insertPushItemRecycle", pushItemRecycleList);
    }

    /**
    * 根据主键查找
    */
    public PushItemRecycle getPushItemRecycleByKey(     String id  ,   Integer userId  ,   Integer founderId       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("userId", userId);
        params.put("founderId", founderId);
        PushItemRecycle result = (PushItemRecycle)  getSqlMapClientTemplate().queryForObject(
                "PushItemRecycle.getPushItemRecycleByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     */
    public  List<PushItemRecycle> getPushItemRecycleByKeys(    String id,Integer userId,List<Integer> founderIdList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("userId", userId);
        params.put("keys", founderIdList);
        return (List<PushItemRecycle>) getSqlMapClientTemplate().queryForList("PushItemRecycle.getPushItemRecyclesByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     */
    public Integer deleteByKey(     String id  ,   Integer userId  ,   Integer founderId       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("userId", userId);
        params.put("founderId", founderId);
        return getSqlMapClientTemplate().delete("PushItemRecycle.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deleteByKeys(    String id,Integer userId,List<Integer> founderIdList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("userId", userId);
        params.put("keys", founderIdList);

        return getSqlMapClientTemplate().delete("PushItemRecycle.deleteByKeys", params);
    }


    @SuppressWarnings("unchecked")
    public void deletePushItemRecycle(PushItemRecycleQuery pushItemRecycleQuery){
        getSqlMapClientTemplate().delete("PushItemRecycle.deletePushItemRecycle", pushItemRecycleQuery);
    }

    /**
     * 根据主键更新
     * @return
     */
    public Integer updatePushItemRecycleByKey(PushItemRecycle pushItemRecycle){
		return  getSqlMapClientTemplate().update("PushItemRecycle.updatePushItemRecycleByKey", pushItemRecycle);
	}

    public void batchUpdatePushItemRecycle(List<PushItemRecycle> pushItemRecycleList){
        batchUpdate("PushItemRecycle.updatePushItemRecycleByKey", pushItemRecycleList);
    }
    @SuppressWarnings("unchecked")
    public Result<PushItemRecycle> getPushItemRecycleListWithPage(PushItemRecycleQuery pushItemRecycleQuery){
	    Result<PushItemRecycle> rs = new Result<PushItemRecycle>(pushItemRecycleQuery);
		            rs.setTotal(Long.parseLong(""+getSqlMapClientTemplate().queryForObject("PushItemRecycle.getPushItemRecycleListCount",pushItemRecycleQuery)));
            if(pushItemRecycleQuery.getFields()!=null && pushItemRecycleQuery.getFields()!=""){
                rs.setItems((List<PushItemRecycle>) getSqlMapClientTemplate().queryForList("PushItemRecycle.getPushItemRecycleListWithPageFields", pushItemRecycleQuery));
            }else{
                rs.setItems((List<PushItemRecycle>) getSqlMapClientTemplate().queryForList("PushItemRecycle.getPushItemRecycleListWithPage", pushItemRecycleQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<PushItemRecycle> getPushItemRecycleList(PushItemRecycleQuery pushItemRecycleQuery){
    if(pushItemRecycleQuery.getFields()!=null&&pushItemRecycleQuery.getFields()!=""){
        return (List<PushItemRecycle>) getSqlMapClientTemplate().queryForList("PushItemRecycle.getPushItemRecycleListFields", pushItemRecycleQuery);
    }
		return (List<PushItemRecycle>) getSqlMapClientTemplate().queryForList("PushItemRecycle.getPushItemRecycleList", pushItemRecycleQuery);
	}

    public Map<String,String> getPushItemRecycleIdMap(PushItemRecycleQuery pushItemRecycleQuery){
        return (Map<String,String>) getSqlMapClientTemplate().queryForMap("PushItemRecycle.getPushItemRecycleIdMap", pushItemRecycleQuery,"id","id");
    }

}