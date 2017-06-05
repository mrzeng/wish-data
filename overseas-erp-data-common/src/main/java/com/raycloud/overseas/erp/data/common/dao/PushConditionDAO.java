package com.raycloud.overseas.erp.data.common.dao;


import com.raycloud.overseas.erp.data.common.pojo.PushConditionDomain;
import com.raycloud.overseas.erp.data.common.query.PushConditionQuery;
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
public class PushConditionDAO  extends BaseDAO{


	public Integer addPushCondition(PushConditionDomain pushCondition){
		return (Integer) getSqlMapClientTemplate().insert("PushCondition.insertPushCondition", pushCondition);
	}



    @SuppressWarnings("unchecked")
    public List<PushConditionDomain> getPushConditionList(PushConditionQuery pushConditionQuery){
    if(pushConditionQuery.getFields()!=null&&pushConditionQuery.getFields()!=""){
        return (List<PushConditionDomain>) getSqlMapClientTemplate().queryForList("PushCondition.getPushConditionListFields", pushConditionQuery);
    }
		return (List<PushConditionDomain>) getSqlMapClientTemplate().queryForList("PushCondition.getPushConditionList", pushConditionQuery);
	}

    public List<Long> getAllPushConditionUserIdList(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("fkId","1");
        return (List<Long>) getSqlMapClientTemplate().queryForList("PushCondition.getPushConditionUserIdList", map);
    }
}