
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.DataMarkInfoQuery;
import com.raycloud.overseas.erp.data.common.pojo.DataMarkInfo;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */

@Repository
public class DataMarkInfoDAO  extends BaseDAO{


	public Integer addDataMarkInfo(DataMarkInfo dataMarkInfo){
		return (Integer) getSqlMapClientTemplate().insert("DataMarkInfo.insertDataMarkInfo", dataMarkInfo);
	}

    public void batchAddDataMarkInfo(List<DataMarkInfo> dataMarkInfoList){
        batchInsert("DataMarkInfo.insertDataMarkInfo", dataMarkInfoList);
    }

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public DataMarkInfo getDataMarkInfoByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        DataMarkInfo result = (DataMarkInfo)  getSqlMapClientTemplate().queryForObject(
                "DataMarkInfo.getDataMarkInfoByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<DataMarkInfo> getDataMarkInfoByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<DataMarkInfo>) getSqlMapClientTemplate().queryForList("DataMarkInfo.getDataMarkInfosByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("DataMarkInfo.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("DataMarkInfo.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateDataMarkInfoByKey(DataMarkInfo dataMarkInfo){
		return  getSqlMapClientTemplate().update("DataMarkInfo.updateDataMarkInfoByKey", dataMarkInfo);
	}

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public void batchUpdateDataMarkInfoByKey(List<DataMarkInfo> dataMarkInfoList){
        batchUpdate("DataMarkInfo.updateDataMarkInfoByKey", dataMarkInfoList);
    }

    @SuppressWarnings("unchecked")
    public Result<DataMarkInfo> getDataMarkInfoListWithPage(DataMarkInfoQuery dataMarkInfoQuery){
	    Result<DataMarkInfo> rs = new Result<DataMarkInfo>(dataMarkInfoQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("DataMarkInfo.getDataMarkInfoListCount",dataMarkInfoQuery)));
            if(dataMarkInfoQuery.getFields()!=null && dataMarkInfoQuery.getFields()!=""){
                rs.setItems((List<DataMarkInfo>) getSqlMapClientTemplate().queryForList("DataMarkInfo.getDataMarkInfoListWithPageFields", dataMarkInfoQuery));
            }else{
                rs.setItems((List<DataMarkInfo>) getSqlMapClientTemplate().queryForList("DataMarkInfo.getDataMarkInfoListWithPage", dataMarkInfoQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<DataMarkInfo> getDataMarkInfoList(DataMarkInfoQuery dataMarkInfoQuery){
    if(dataMarkInfoQuery.getFields()!=null&&dataMarkInfoQuery.getFields()!=""){
        return (List<DataMarkInfo>) getSqlMapClientTemplate().queryForList("DataMarkInfo.getDataMarkInfoListFields", dataMarkInfoQuery);
    }
		return (List<DataMarkInfo>) getSqlMapClientTemplate().queryForList("DataMarkInfo.getDataMarkInfoList", dataMarkInfoQuery);
	}


}