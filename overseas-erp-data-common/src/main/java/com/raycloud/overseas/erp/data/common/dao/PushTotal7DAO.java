
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushTotal7Query;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal7;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-08
 */

@Repository
public class PushTotal7DAO  extends BaseDAO{


	public Integer addPushTotal7(PushTotal7 pushTotal7){
		return (Integer) getSqlMapClientTemplate().insert("PushTotal7.insertPushTotal7", pushTotal7);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public PushTotal7 getPushTotal7ByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        PushTotal7 result = (PushTotal7)  getSqlMapClientTemplate().queryForObject(
                "PushTotal7.getPushTotal7ByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<PushTotal7> getPushTotal7ByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<PushTotal7>) getSqlMapClientTemplate().queryForList("PushTotal7.getPushTotal7sByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     Integer id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("PushTotal7.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<Integer> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("PushTotal7.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public int deletePushTotal7ByKey(PushTotal7Query pushTotal7Query){
        return  getSqlMapClientTemplate().delete("PushTotal7.deletePushTotal7List",pushTotal7Query);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updatePushTotal7ByKey(PushTotal7 pushTotal7){
		return  getSqlMapClientTemplate().update("PushTotal7.updatePushTotal7ByKey", pushTotal7);
	}

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public void batchUpdatePushTotal7(List<PushTotal7> pushTotal7List){
       batchUpdate("PushTotal7.updatePushTotal7ByKey", pushTotal7List);
    }
    @SuppressWarnings("unchecked")
    public Result<PushTotal7> getPushTotal7ListWithPage(PushTotal7Query pushTotal7Query){
	    Result<PushTotal7> rs = new Result<PushTotal7>(pushTotal7Query);
		            rs.setTotal((Long)  getSqlMapClientTemplate().queryForObject("PushTotal7.getPushTotal7ListCount",pushTotal7Query));
            if(pushTotal7Query.getFields()!=null && pushTotal7Query.getFields()!=""){
                rs.setItems((List<PushTotal7>) getSqlMapClientTemplate().queryForList("PushTotal7.getPushTotal7ListWithPageFields", pushTotal7Query));
            }else{
                rs.setItems((List<PushTotal7>) getSqlMapClientTemplate().queryForList("PushTotal7.getPushTotal7ListWithPage", pushTotal7Query));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<PushTotal7> getPushTotal7List(PushTotal7Query pushTotal7Query){
    if(pushTotal7Query.getFields()!=null&&pushTotal7Query.getFields()!=""){
        return (List<PushTotal7>) getSqlMapClientTemplate().queryForList("PushTotal7.getPushTotal7ListFields", pushTotal7Query);
    }
		return (List<PushTotal7>) getSqlMapClientTemplate().queryForList("PushTotal7.getPushTotal7List", pushTotal7Query);
	}


}