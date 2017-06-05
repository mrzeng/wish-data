package com.raycloud.overseas.erp.data.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.MessageFormat;

public class DataBaseUtil {

	public static final int T_SPLIT = 200;  //trade

	public static final int O_SPLIT = 200;  //order
	
	public static final int L_SPLIT = 4;   //log
	
	public static final int P_SPLIT = 100;

	public static final int POOL_SPLIT = 100;//号码池

	public static final int PACKAGE_SPLIT = 100;//包裹表

	public static final String PACKAGE = "pack";//包裹

	public static final String POOL= "pool";//号码池
	
	public static final int C_SPLIT = 100;
	
	public static final int I_SPLIT = 20;
	
	public static final int DB_SPLIT = 2;

//	public static final int S_SPLIT = 2; // 2张店铺表

	public static final int TS_SPLIT = 10;//团队店铺映射表

	public static final int SL_SPLIT = 10;//shopLog 店铺日志表

	public static final int TMM_SPLIT = 10; // trade_mark_map 订单标签映射表

	public static final int DECLARATION_SPLIT = 200;  //报关表分表

	public static final int PMM_SPLIT = 10; // package_mark_map 订单标签映射表

	public static final int R_SPLIT = 10;

	public static final int E_SPLIT = 10; //10张电子面单报表 表
	public static final int TRADE_LOG = 10; //10张电子面单报表 表

	public static final int LOG_SPLIT = 200; //日志表

	public static final int WALLET_DAY_BILL_SPLIT = 10; //钱包账单分表

	public static final int WALLET_WATER_LOG_SPLIT = 50; //钱包流水分表

	public static final String LOG = "log";

	public static final String DB = "db";

//	public static final String S = "s";  //shop 店铺分库属性

	public static final String TS = "ts";//team_shop_map 分库属性

	public static final String SL = "sl";//shop_log_info 分库属性

	public static final String T = "t";

	public static final String O = "o";

	public static final String TMM = "tmm";//trade_mark_map 分库属性

	public static final String PMM = "pmm";//package_mark_map 分库属性
	
	public static final String L = "l"; //notice_log

	public static final String DECLARATION = "declaration";
	
	public static final String P = "p";
	
	public static final String C = "c";
	
	public static final String I = "i";

    /**
     * 退款订单表
     */
	public static final String R = "r";

    /**
     * 电子面单报表分表信息
     */
	public static final String E = "e";

	/**
	 * 钱包账单
	 */
	public static final String WALLET_BILL = "wallet_bill";

	/**
	 * 钱包流水
	 */
	public static final String WALLET_WATER = "wallet_water";

	/**
	 * 获取用户信息储存的配置信息
	 * 
	 * @param uId
	 * @return
	 */
	public static String getStoreConfig(Integer uId) {
		if (uId != null) {
			return "{db:" + getDateBaseNum(uId) + ",t:"+ getTradeTableNum(uId) + ",o:" + getOrderTableNum(uId)+ ",tmm:" + getTradeMarkMapTableNum(uId) + /* ",l:"+ getLogTableNum
                    (uId)+ ",p:"+ getPrintInfoTableNum(uId)+ ",c:" + getCountTableNum(uId) + ",i:" + getItemTableNum(uId) +"," +
                    "r:"+getRefundTableNum(uId)+ */",ts:" +getTeamShopMapTableNum(uId) + ",declaration:" + getDeclarationTableNum(uId) /*+ ",s:" + getShopTableNum(uId)*/ + ",sl:" + getShopLogTableNum(uId) +
					",pack:" + getPackageTableNum(uId)+",pool:" + getPoolableNum(uId)+",pmm:" + getPackageMarkMapTableNum(uId)+",log:" + getLogInfoTableNum(uId)+
					",wallet_bill:" + getWalletBillNum(uId)+",wallet_water:" + getWalletWaterNum(uId)+"}";
		}
		return null;
	}

	private static Integer getPackageMarkMapTableNum(Integer uId) {
		if(uId != null){
			return uId % PMM_SPLIT;
		}
		return null;
	}

	private static Integer getLogInfoTableNum(Integer uId) {
		if(uId != null){
			return uId % LOG_SPLIT;
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getStoreConfig(372635));
		String domain = "www.ykmaiz.com";
		int iVisit = 0;
		int time = 100;
		System.out.println(MessageFormat.format("该域名{0}被访问了 {1} 次. 耗时{2}毫秒。", domain, iVisit, time));
	}
	
	public static String UpdateStoreConfig(Integer uId, String oldConfig) {
		if (uId != null && oldConfig != null) {
			oldConfig = oldConfig.substring(0, oldConfig.length() - 1);
			return oldConfig + ",i:" + getItemTableNum(uId) + "}";
		}
		return null;
	}

	/**
	 * 获取数据储存的数据库的编号
	 * 
	 * @param uId
	 * @return
	 */
	public static Integer getDateBaseNum(Integer uId) {
		if (uId != null) {
			// 分库的规则代码，目前有两个库
			return (uId % DB_SPLIT == 0?2:1);
		}
		return null;
	}

	/**
	 * 店铺分表函数
	 * @param uId
	 * @return
	 */
	/*public static Integer getShopTableNum(Integer uId){
		if(uId != null){
			return uId % S_SPLIT;
		}
		return null;
	}
*/
	/**
	 * 团队店铺映射表
	 * @param uId
	 * @return
	 */
	public static Integer getTeamShopMapTableNum(Integer uId){
		if(uId != null){
			return uId % TS_SPLIT;
		}
		return null;
	}
	/**
	 * 包裹映射表
	 * @param uId
	 * @return
	 */
	public static Integer getPackageTableNum(Integer uId){
		if(uId != null){
			return uId % PACKAGE_SPLIT;
		}
		return null;
	}
	/**
	 * 号码池映射表
	 * @param uId
	 * @return
	 */
	public static Integer getPoolableNum(Integer uId){
		if(uId != null){
			return uId % POOL_SPLIT;
		}
		return null;
	}

	public static Integer getShopLogTableNum(Integer uId){
		if(uId != null){
			return uId % SL_SPLIT;
		}
		return null;
	}

	public static Integer getTradeTableNum(Integer uId) {
		if (uId != null) {
			return uId % T_SPLIT;
		}
		return null;

	}

	public static Integer getOrderTableNum(Integer uId) {
		if (uId != null) {
			return uId % O_SPLIT;
		}
		return null;
	}

	public static Integer getTradeMarkMapTableNum(Integer uId){
		if (uId != null) {
			return uId % TMM_SPLIT;
		}
		return null;
	}

	public static Integer getDeclarationTableNum(Integer uId){
		if (uId != null) {
			return uId % DECLARATION_SPLIT;
		}
		return null;
	}


	public static Integer getLogTableNum(Integer uId) {
		if (uId != null) {
			return uId % L_SPLIT;
		}
		return null;

	}
	
	public static Integer getItemTableNum(Integer uId) {
		if (uId != null) {
			return uId % I_SPLIT;
		}
		return null;

	}
	
	public static Integer getPrintInfoTableNum(Integer uId) {
		if (uId != null) {
			return uId % P_SPLIT;
		}
		return null;

	}
	
	public static Integer getCountTableNum(Integer uId) {
		if (uId != null) {
			return uId % C_SPLIT;
		}
		return null;
	}

    public static Integer getRefundTableNum(Integer uId) {
        if (uId != null) {
            return uId % R_SPLIT;
        }
        return null;
    }

    public static Long getElecReportNum(Long uId){
        if (uId != null) {
            return uId % E_SPLIT;
        }
        return null;
    }

	public static Long getTradeLogNum(Long uId) {
		if (uId != null) {
			return uId % TRADE_LOG;
		}
		return null;
	}

	private static Integer getWalletWaterNum(Integer uId) {
		if(uId != null){
			return uId % WALLET_WATER_LOG_SPLIT;
		}
		return null;
	}

	private static Integer getWalletBillNum(Integer uId) {
		if(uId != null){
			return uId % WALLET_DAY_BILL_SPLIT;
		}
		return null;
	}

	public static Integer getFieldValue(String config, String fieldName) {
		if (config != null) {

			JSONObject jsonC = JSON.parseObject(config);
			return jsonC.getInteger(fieldName);
		}
		return null;
	}

	public static String updateUserDB(String config, String fieldName, String value) {
		if(config != null) {
			JSONObject json = JSONObject.parseObject(config);
			json.put(fieldName, value);
			return JSONObject.toJSONString(json);
		}
		return null;
	}
}
