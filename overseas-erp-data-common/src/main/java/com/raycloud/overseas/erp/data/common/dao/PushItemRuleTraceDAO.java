
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushItemRuleTraceQuery;
import com.raycloud.overseas.erp.data.common.pojo.PushItemRuleTrace;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-13
 */

@Repository
public class PushItemRuleTraceDAO  extends BaseDAO{


	public Integer addPushItemRuleTrace(PushItemRuleTrace pushItemRuleTrace){
		return (Integer) getSqlMapClientTemplate().insert("PushItemRuleTrace.insertPushItemRuleTrace", pushItemRuleTrace);
	}

    public void batchAddPushItemRuleTrace(List<PushItemRuleTrace> pushItemRuleTraceList){
        batchInsert("PushItemRuleTrace.insertPushItemRuleTrace", pushItemRuleTraceList);
    }

    /**
    * 根据主键查找
    */
    public PushItemRuleTrace getPushItemRuleTraceByKey(     Integer ruleId  ,   Date insertDate       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("insertDate", insertDate);
        PushItemRuleTrace result = (PushItemRuleTrace)  getSqlMapClientTemplate().queryForObject(
                "PushItemRuleTrace.getPushItemRuleTraceByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     */
    public  List<PushItemRuleTrace> getPushItemRuleTraceByKeys(    Integer ruleId,List<Date> insertDateList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("keys", insertDateList);
        return (List<PushItemRuleTrace>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getPushItemRuleTracesByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     */
    public Integer deleteByKey(     Integer ruleId  ,   Date insertDate       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("insertDate", insertDate);
        return getSqlMapClientTemplate().delete("PushItemRuleTrace.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deleteByKeys(    Integer ruleId,List<Date> insertDateList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        params.put("keys", insertDateList);

        return getSqlMapClientTemplate().delete("PushItemRuleTrace.deleteByKeys", params);
    }


    @SuppressWarnings("unchecked")
    public void deletePushItemRuleTrace(PushItemRuleTraceQuery pushItemRuleTraceQuery){
        getSqlMapClientTemplate().delete("PushItemRuleTrace.deletePushItemRuleTrace", pushItemRuleTraceQuery);
    }

    /**
     * 根据主键更新
     * @return
     */
    public Integer updatePushItemRuleTraceByKey(PushItemRuleTrace pushItemRuleTrace){
		return  getSqlMapClientTemplate().update("PushItemRuleTrace.updatePushItemRuleTraceByKey", pushItemRuleTrace);
	}

    public void batchUpdatePushItemRuleTrace(List<PushItemRuleTrace> pushItemRuleTraceList){
        batchUpdate("PushItemRuleTrace.updatePushItemRuleTraceByKey", pushItemRuleTraceList);
    }
    @SuppressWarnings("unchecked")
    public Result<PushItemRuleTrace> getPushItemRuleTraceListWithPage(PushItemRuleTraceQuery pushItemRuleTraceQuery){
	    Result<PushItemRuleTrace> rs = new Result<PushItemRuleTrace>(pushItemRuleTraceQuery);
		            rs.setTotal(Long.parseLong(""+getSqlMapClientTemplate().queryForObject("PushItemRuleTrace.getPushItemRuleTraceListCount",pushItemRuleTraceQuery)));
            if(pushItemRuleTraceQuery.getFields()!=null && pushItemRuleTraceQuery.getFields()!=""){
                rs.setItems((List<PushItemRuleTrace>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getPushItemRuleTraceListWithPageFields", pushItemRuleTraceQuery));
            }else{
                rs.setItems((List<PushItemRuleTrace>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getPushItemRuleTraceListWithPage", pushItemRuleTraceQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<PushItemRuleTrace> getPushItemRuleTraceList(PushItemRuleTraceQuery pushItemRuleTraceQuery){
    if(pushItemRuleTraceQuery.getFields()!=null&&pushItemRuleTraceQuery.getFields()!=""){
        return (List<PushItemRuleTrace>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getPushItemRuleTraceListFields", pushItemRuleTraceQuery);
    }
		return (List<PushItemRuleTrace>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getPushItemRuleTraceList", pushItemRuleTraceQuery);
	}

    @SuppressWarnings("unchecked")
    public List<String> getItemIdList(PushItemRuleTraceQuery pushItemRuleTraceQuery){
        return (List<String>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getItemIdList", pushItemRuleTraceQuery);
    }

    @SuppressWarnings("unchecked")
    public Integer getItemIdCount(PushItemRuleTraceQuery pushItemRuleTraceQuery){
        return (Integer) getSqlMapClientTemplate().queryForObject("PushItemRuleTrace.getItemCount", pushItemRuleTraceQuery);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> getItemCountWithGroup(PushItemRuleTraceQuery pushItemRuleTraceQuery){
        return (List<Map<String,Object>>) getSqlMapClientTemplate().queryForList("PushItemRuleTrace.getItemCountWithGroup", pushItemRuleTraceQuery);
    }
}