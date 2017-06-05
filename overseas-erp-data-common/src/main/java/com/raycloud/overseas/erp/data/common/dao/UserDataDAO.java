package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.UserDataQuery;
import com.raycloud.overseas.erp.data.domain.UserData;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-20
 */

@Repository
public class UserDataDAO  extends BaseDAO{


	public Integer addUserData(UserData userData) throws SQLException{
		return (Integer) getSqlMapClientTemplate().insert("UserData.insertUserData", userData);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public UserData getUserDataByKey(     Integer userId       ) throws SQLException {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("fkId",1L);
        map.put("userId",userId);
        UserData result = (UserData)  getSqlMapClientTemplate().queryForObject(
                "UserData.getUserDataByKey", map);

        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<UserData> getUserDataByKeys(    List<Integer> userIdList    ) throws SQLException {
                Map<String, Object> params = new HashMap<String, Object>();
                    params.put("keys", userIdList);
                        return  (List<UserData>) getSqlMapClientTemplate().queryForList("UserData.getUserDatasByKeys", params);
    }



    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateUserDataByKey(UserData userData) throws SQLException{
		return  getSqlMapClientTemplate().update("UserData.updateUserDataByKey", userData);
	}
    @SuppressWarnings("unchecked")
    public Result<UserData> getUserDataListWithPage(UserDataQuery userDataQuery){
	    Result<UserData> rs = new Result<UserData>(userDataQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("UserData.getUserDataListCount",userDataQuery)));
            if(userDataQuery.getFields()!=null && userDataQuery.getFields()!=""){
                rs.setItems((List<UserData>) getSqlMapClientTemplate().queryForList("UserData.getUserDataListWithPageFields", userDataQuery));
            }else{
                rs.setItems((List<UserData>) getSqlMapClientTemplate().queryForList("UserData.getUserDataListWithPage", userDataQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<UserData> getUserDataList(UserDataQuery userDataQuery) throws SQLException{
    if(userDataQuery.getFields()!=null&&userDataQuery.getFields()!=""){
        return (List<UserData>) getSqlMapClientTemplate().queryForList("UserData.getUserDataListFields", userDataQuery);
    }
		return (List<UserData>) getSqlMapClientTemplate().queryForList("UserData.getUserDataList", userDataQuery);
	}


}