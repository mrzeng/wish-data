package com.raycloud.overseas.erp.data.services.impl;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.dao.ComplementItemDAO;
import com.raycloud.overseas.erp.data.common.pojo.ComplementItem;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.services.api.ComplementItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 补全宝贝内容
 */
@Service("ComplementItemService")
public class ComplementItemServiceImpl implements ComplementItemService {

	private static Logger logger= Logger.getLogger(ComplementItemServiceImpl.class);

	@Resource
	ComplementItemDAO complementItemDAO;

    /**
     * 插入数据库
     * @return
     */
	public Integer addComplementItem(ComplementItem complementItem){
		return complementItemDAO.addComplementItem(complementItem);
	}


    public ComplementItem getItemById(String itemid){
		return complementItemDAO.getItemById(itemid);
    }

	@Override
	public void batchAddComplementItemList(List<ComplementItem> complementItemList) {
		complementItemDAO.batchAddComplementItem(complementItemList);
	}

	@Override
	public void storeItemPermanent(List<String> itemIdList, UserData userData) {
		if(ListUtil.isBlank(itemIdList)){
			return;
		}
		List<ComplementItem> complementItemList = new ArrayList<ComplementItem>();
		for(String itemId : itemIdList){
			ComplementItem complementItem = complementItemDAO.getItemById(itemId);
			if(complementItem == null){
				complementItem = new ComplementItem();
				complementItem.setItemid(itemId);
				complementItem.setStatus(0);
				complementItem.setUserId(userData.getUserId()+"");
				complementItemList.add(complementItem);
				logger.biz("宝贝归档,用户id:{},宝贝id:{}",userData.getUserId(),itemId);
			}
		}
		batchAddComplementItemList(complementItemList);
	}
}
