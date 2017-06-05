
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.UserCommonCatQuery;
import com.raycloud.overseas.erp.data.common.pojo.UserCommonCat;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */

@Repository
public class UserCommonCatDAO  extends BaseDAO{


	public Integer addUserCommonCat(UserCommonCat userCommonCat){
		return (Integer) getSqlMapClientTemplate().insert("UserCommonCat.insertUserCommonCat", userCommonCat);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public UserCommonCat getUserCommonCatByKey(     String id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        UserCommonCat result = (UserCommonCat)  getSqlMapClientTemplate().queryForObject(
                "UserCommonCat.getUserCommonCatByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<UserCommonCat> getUserCommonCatByKeys(    List<String> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<UserCommonCat>) getSqlMapClientTemplate().queryForList("UserCommonCat.getUserCommonCatsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     String id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("UserCommonCat.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<String> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("UserCommonCat.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateUserCommonCatByKey(UserCommonCat userCommonCat){
		return  getSqlMapClientTemplate().update("UserCommonCat.updateUserCommonCatByKey", userCommonCat);
	}
    @SuppressWarnings("unchecked")
    public Result<UserCommonCat> getUserCommonCatListWithPage(UserCommonCatQuery userCommonCatQuery){
	    Result<UserCommonCat> rs = new Result<UserCommonCat>(userCommonCatQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("UserCommonCat.getUserCommonCatListCount",userCommonCatQuery)));
            if(userCommonCatQuery.getFields()!=null && userCommonCatQuery.getFields()!=""){
                rs.setItems((List<UserCommonCat>) getSqlMapClientTemplate().queryForList("UserCommonCat.getUserCommonCatListWithPageFields", userCommonCatQuery));
            }else{
                rs.setItems((List<UserCommonCat>) getSqlMapClientTemplate().queryForList("UserCommonCat.getUserCommonCatListWithPage", userCommonCatQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<UserCommonCat> getUserCommonCatList(UserCommonCatQuery userCommonCatQuery){
    if(userCommonCatQuery.getFields()!=null&&userCommonCatQuery.getFields()!=""){
        return (List<UserCommonCat>) getSqlMapClientTemplate().queryForList("UserCommonCat.getUserCommonCatListFields", userCommonCatQuery);
    }
		return (List<UserCommonCat>) getSqlMapClientTemplate().queryForList("UserCommonCat.getUserCommonCatList", userCommonCatQuery);
	}


}