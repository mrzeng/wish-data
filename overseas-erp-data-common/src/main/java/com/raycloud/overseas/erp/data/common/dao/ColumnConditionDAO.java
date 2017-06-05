
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.ColumnConditionQuery;
import com.raycloud.overseas.erp.data.common.pojo.ColumnCondition;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-02
 */

@Repository
public class ColumnConditionDAO  extends BaseDAO{


	public Integer addColumnCondition(ColumnCondition columnCondition){
		return (Integer) getSqlMapClientTemplate().insert("ColumnCondition.insertColumnCondition", columnCondition);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public ColumnCondition getColumnConditionByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        ColumnCondition result = (ColumnCondition)  getSqlMapClientTemplate().queryForObject(
                "ColumnCondition.getColumnConditionByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<ColumnCondition> getColumnConditionByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<ColumnCondition>) getSqlMapClientTemplate().queryForList("ColumnCondition.getColumnConditionsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     Integer id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("ColumnCondition.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<Integer> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("ColumnCondition.deleteByKeys", params);
    }

    /**
     * 批量删除
     * @return
     * @throws SQLException
     */
    public void deleteColumnConditionList(ColumnConditionQuery columnConditionQuery ){

        getSqlMapClientTemplate().delete("ColumnCondition.deleteColumnConditionList", columnConditionQuery);
    }
    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateColumnConditionByKey(ColumnCondition columnCondition){
		return  getSqlMapClientTemplate().update("ColumnCondition.updateColumnConditionByKey", columnCondition);
	}

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public void batchUpdateColumnConditionByKey(List<ColumnCondition> columnConditionList){
        batchUpdate("ColumnCondition.updateColumnConditionByKey", columnConditionList);
    }

    @SuppressWarnings("unchecked")
    public Result<ColumnCondition> getColumnConditionListWithPage(ColumnConditionQuery columnConditionQuery){
	    Result<ColumnCondition> rs = new Result<ColumnCondition>(columnConditionQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("ColumnCondition.getColumnConditionListCount",columnConditionQuery)));
            if(columnConditionQuery.getFields()!=null && columnConditionQuery.getFields()!=""){
                rs.setItems((List<ColumnCondition>) getSqlMapClientTemplate().queryForList("ColumnCondition.getColumnConditionListWithPageFields", columnConditionQuery));
            }else{
                rs.setItems((List<ColumnCondition>) getSqlMapClientTemplate().queryForList("ColumnCondition.getColumnConditionListWithPage", columnConditionQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<ColumnCondition> getColumnConditionList(ColumnConditionQuery columnConditionQuery){
    if(columnConditionQuery.getFields()!=null&&columnConditionQuery.getFields()!=""){
        return (List<ColumnCondition>) getSqlMapClientTemplate().queryForList("ColumnCondition.getColumnConditionListFields", columnConditionQuery);
    }
		return (List<ColumnCondition>) getSqlMapClientTemplate().queryForList("ColumnCondition.getColumnConditionList", columnConditionQuery);
	}


}