
package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.erp.data.common.query.PushItemQuery;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushTotal1Query;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal1;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-08
 */

@Repository
public class PushTotal1DAO  extends BaseDAO{


	public Integer addPushTotal1(PushTotal1 pushTotal1){
		return (Integer) getSqlMapClientTemplate().insert("PushTotal1.insertPushTotal1", pushTotal1);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public PushTotal1 getPushTotal1ByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        PushTotal1 result = (PushTotal1)  getSqlMapClientTemplate().queryForObject(
                "PushTotal1.getPushTotal1ByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<PushTotal1> getPushTotal1ByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<PushTotal1>) getSqlMapClientTemplate().queryForList("PushTotal1.getPushTotal1sByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     Integer id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("PushTotal1.deleteByKey", params);
    }

    @SuppressWarnings("unchecked")
    public void deletePushTotal1(PushTotal1Query pushTotal1Query){
        getSqlMapClientTemplate().delete("PushTotal1.deletePushTotal1", pushTotal1Query);
    }

    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<Integer> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("PushTotal1.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updatePushTotal1ByKey(PushTotal1 pushTotal1){
		return  getSqlMapClientTemplate().update("PushTotal1.updatePushTotal1ByKey", pushTotal1);
	}

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public void batchUpdatePushTotal(List<PushTotal1> pushTotal1List){
        batchUpdate("PushTotal1.updatePushTotal1ByKey", pushTotal1List);
    }

    @SuppressWarnings("unchecked")
    public Result<PushTotal1> getPushTotal1ListWithPage(PushTotal1Query pushTotal1Query){
	    Result<PushTotal1> rs = new Result<PushTotal1>(pushTotal1Query);
		            rs.setTotal((Long)  getSqlMapClientTemplate().queryForObject("PushTotal1.getPushTotal1ListCount",pushTotal1Query));
            if(pushTotal1Query.getFields()!=null && pushTotal1Query.getFields()!=""){
                rs.setItems((List<PushTotal1>) getSqlMapClientTemplate().queryForList("PushTotal1.getPushTotal1ListWithPageFields", pushTotal1Query));
            }else{
                rs.setItems((List<PushTotal1>) getSqlMapClientTemplate().queryForList("PushTotal1.getPushTotal1ListWithPage", pushTotal1Query));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<PushTotal1> getPushTotal1List(PushTotal1Query pushTotal1Query){
    if(pushTotal1Query.getFields()!=null&&pushTotal1Query.getFields()!=""){
        return (List<PushTotal1>) getSqlMapClientTemplate().queryForList("PushTotal1.getPushTotal1ListFields", pushTotal1Query);
    }
		return (List<PushTotal1>) getSqlMapClientTemplate().queryForList("PushTotal1.getPushTotal1List", pushTotal1Query);
	}


}