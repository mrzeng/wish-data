package com.raycloud.overseas.erp.data.services.api;


import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.UserDataQuery;
import com.raycloud.overseas.erp.data.domain.UserData;

import java.util.List;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-20
 */
public interface UserDataService {
    /**
     * 基本插入
     * @return
     */
	public Integer addUserData(UserData userData);
	
    /**
     * 根据主键查询
     */
	public UserData getUserDataByKey(Integer userId);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateUserDataByKey(UserData userData);
    /**
     * 根据条件查询分页查询
     * @param userDataQuery 查询条件
     * @return
     */
    public Result<UserData> getUserDataListWithPage(UserDataQuery userDataQuery);
    /**
     * 根据条件查询
     * @param userDataQuery 查询条件
     * @return
     */
    public List<UserData> getUserDataList(UserDataQuery userDataQuery);
}
