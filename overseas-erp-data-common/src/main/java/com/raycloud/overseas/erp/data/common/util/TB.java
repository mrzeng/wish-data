package com.raycloud.overseas.erp.data.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.erp.data.domain.UserData;

import java.text.MessageFormat;

public class TB {

	public static final int PI_SPLIT = 20;//推送宝贝
	public static final String PI_FIELD = "push_item";//推送宝贝




	/**
	 * 获取用户信息储存的配置信息
	 * 
	 * @param uId
	 * @return
	 */
	public static String getStoreConfig(Integer uId) {
		if (uId != null) {
			return "{"+PI_FIELD+":" + uId%PI_SPLIT +"}";
		}
		return null;
	}

	public static void main(String[] args) {
//		System.out.println(getStoreConfig(372635L));
//		String domain = "www.ykmaiz.com";
//		int iVisit = 0;
//		int time = 100;
//		System.out.println(MessageFormat.format("该域名{0}被访问了 {1} 次. 耗时{2}毫秒。", domain, iVisit, time));
	}

	public static Integer ID(String config, String fieldName) {
		if (config != null) {

			JSONObject jsonC = JSON.parseObject(config);
			return jsonC.getInteger(fieldName);
		}
		return null;
	}

	public static int getTableId(String fieldName,UserData userData){
		return TB.ID(TB.getStoreConfig(userData.getFounderId()),fieldName);
	}
}
