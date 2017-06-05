package com.raycloud.overseas.erp.data.services.impl;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.PushItemMerchantMapDAO;
import com.raycloud.overseas.erp.data.common.pojo.PushItemMerchantMap;
import com.raycloud.overseas.erp.data.common.query.PushItemMerchantMapQuery;
import com.raycloud.overseas.erp.data.services.api.PushItemMerchantMapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-13
 */
@Service("PushItemMerchantMapService")
public class PushItemMerchantMapServiceImpl implements PushItemMerchantMapService {

	@Resource
	PushItemMerchantMapDAO pushItemMerchantMapDAO;

	@Override
	public void deletePushItemMerchantMapByUserId(Integer founderId,Long userId) {
		pushItemMerchantMapDAO.deletePushItemByUserId(founderId,userId);
	}

	public void batchInsertPushItemMerchantMap(List<PushItemMerchantMap> list){
		pushItemMerchantMapDAO.batchInsertPushItemMerchantMap(list);
	}

	public Integer addPushItemMerchantMap(PushItemMerchantMap pushItemMerchantMap){
		return pushItemMerchantMapDAO.addPushItemMerchantMap(pushItemMerchantMap);
	}

	public Result<PushItemMerchantMap> getPushItemMerchantMapListWithPage(PushItemMerchantMapQuery pushItemMerchantMapQuery){
		return pushItemMerchantMapDAO.getPushItemMerchantMapListWithPage(pushItemMerchantMapQuery);
	}
    
    public List<PushItemMerchantMap> getPushItemMerchantMapList(PushItemMerchantMapQuery pushItemMerchantMapQuery){
	   return pushItemMerchantMapDAO.getPushItemMerchantMapList(pushItemMerchantMapQuery);
	}

}
