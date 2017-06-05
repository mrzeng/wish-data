package com.raycloud.overseas.erp.data.services.impl;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.UserDataDAO;
import com.raycloud.overseas.erp.data.common.query.UserDataQuery;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.services.api.UserDataService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-20
 */
@Service("UserDataService")
public class UserDataServiceImpl implements UserDataService {

    private static final Log log = LogFactory.getLog(UserDataServiceImpl.class);

	@Resource
	UserDataDAO userDataDAO;

    /**
     * 插入数据库
     * @return
     */
	public Integer addUserData(UserData userData){
		try{
			return userDataDAO.addUserData(userData);
		}catch(SQLException e){
			log.error("dao addUserData error.:" + e.getMessage(), e);
		}
		return 0;
	}
    /**
     * 根据主键查找
     */
	public UserData getUserDataByKey(     Integer userId       ){
		try{
			return userDataDAO.getUserDataByKey(    userId       );
		}catch(SQLException e){
			log.error("dao getUserDatabyKey error.:" + e.getMessage(), e);
		}
		return null;
	}
    public List<UserData> getUserDataByKeys(    List<Integer> userIdList    ){
        try{
            return userDataDAO.getUserDataByKeys(    userIdList    );
        }catch(SQLException e){
            log.error("dao getUserDatasByKeys erorr."+ e.getMessage(),e);
        }
        return null;
    }

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateUserDataByKey(UserData userData){
	    try{
		    return userDataDAO.updateUserDataByKey(userData);
		}catch(SQLException e){
		   log.error("dao updateUserData error.userData:"+ e.getMessage(), e);
	    }
		return -1;
	}

	public Result<UserData> getUserDataListWithPage(UserDataQuery userDataQuery){
		Result<UserData> rs = userDataDAO.getUserDataListWithPage(userDataQuery);
		if(!rs.isSuccess()){
			log.error("get UserData error."+rs.getErrorMsg());
		}
		return rs;
	}	
    
    public List<UserData> getUserDataList(UserDataQuery userDataQuery){
		try{
		   return userDataDAO.getUserDataList(userDataQuery);
		}catch(SQLException e){
		   log.error("get UserData list error." + e.getMessage() , e);
	    }
		return Collections.emptyList();
	}

}
