
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushRuleQuery;
import com.raycloud.overseas.erp.data.common.pojo.PushRule;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-08
 */

@Repository
public class PushRuleDAO  extends BaseDAO{


	public void addPushRule(PushRule pushRule){
        getSqlMapClientTemplate().insert("PushRule.insertPushRule", pushRule);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public PushRule getPushRuleByKey(     String id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("fkId",1L);
        PushRule result = (PushRule)  getSqlMapClientTemplate().queryForObject(
                "PushRule.getPushRuleByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<PushRule> getPushRuleByKeys(    List<String> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        params.put("fkId",1L);
        return (List<PushRule>) getSqlMapClientTemplate().queryForList("PushRule.getPushRulesByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     String id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("fkId",1L);
        return getSqlMapClientTemplate().delete("PushRule.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<String> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        params.put("fkId",1L);
        return getSqlMapClientTemplate().delete("PushRule.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updatePushRuleByKey(PushRule pushRule){
		return  getSqlMapClientTemplate().update("PushRule.updatePushRuleByKey", pushRule);
	}

    public void batchUpdatePushRule(List<PushRule> pushRuleList){
        batchUpdate("PushRule.updatePushRuleByKey",pushRuleList);
    }

    @SuppressWarnings("unchecked")
    public Result<PushRule> getPushRuleListWithPage(PushRuleQuery pushRuleQuery){
	    Result<PushRule> rs = new Result<PushRule>(pushRuleQuery);
		            rs.setTotal(Long.parseLong(getSqlMapClientTemplate().queryForObject("PushRule.getPushRuleListCount",pushRuleQuery)+""));
            if(pushRuleQuery.getFields()!=null && pushRuleQuery.getFields()!=""){
                rs.setItems((List<PushRule>) getSqlMapClientTemplate().queryForList("PushRule.getPushRuleListWithPageFields", pushRuleQuery));
            }else{
                rs.setItems((List<PushRule>) getSqlMapClientTemplate().queryForList("PushRule.getPushRuleListWithPage", pushRuleQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<PushRule> getPushRuleList(PushRuleQuery pushRuleQuery){
    if(pushRuleQuery.getFields()!=null&&pushRuleQuery.getFields()!=""){
        return (List<PushRule>) getSqlMapClientTemplate().queryForList("PushRule.getPushRuleListFields", pushRuleQuery);
    }
		return (List<PushRule>) getSqlMapClientTemplate().queryForList("PushRule.getPushRuleList", pushRuleQuery);
	}

    public List<Integer> getRuleUserIdList(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("fkId","1");
        return (List<Integer>) getSqlMapClientTemplate().queryForList("PushRule.getRuleUserIdList", map);
    }
}