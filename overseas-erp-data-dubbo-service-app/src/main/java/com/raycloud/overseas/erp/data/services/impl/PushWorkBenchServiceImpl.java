package com.raycloud.overseas.erp.data.services.impl;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.dao.*;
import com.raycloud.overseas.erp.data.common.pojo.PushItem;
import com.raycloud.overseas.erp.data.common.pojo.PushRule;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal1;
import com.raycloud.overseas.erp.data.common.query.*;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.services.api.PushWorkBenchService;

import javax.annotation.Resource;

import com.raycloud.overseas.erp.data.services.api.UserDataService;
import com.raycloud.overseas.erp.data.services.context.UserSysConfigContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raycloud.overseas.erp.data.common.pojo.PushTotal7;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-04-08
 */
@Service("PushWorkBenchService")
public class PushWorkBenchServiceImpl implements PushWorkBenchService {

	private static Logger logger= Logger.getLogger(PushWorkBenchServiceImpl.class);

	@Resource
	PushTotal7DAO pushTotal7DAO;

	@Resource
	PushTotal1DAO pushTotal1DAO;

	@Resource
	PushRuleDAO pushRuleDAO;

	@Resource
	PushItemDAO pushItemDAO;

	@Resource
	PushItemRuleTraceDAO pushItemRuleTraceDAO;

	@Autowired
	UserDataService userDataService;

	/**
	 * 创建7日推送数据
	 *
	 * @param dateD
	 * @param userData
	 */
	@Override
	public void createPushTotal7(Date dateD, UserData userData) {
		PushTotal7Query pushTotal7Query = new PushTotal7Query();
		pushTotal7Query.setFounderId(userData.getFounderId());
		pushTotal7Query.setUserId(userData.getUserId());
		pushTotal7DAO.deletePushTotal7ByKey(pushTotal7Query);

		//推送日期
		String date7Str = DateUtil.getDate(DateUtil.addDays(dateD,-6),"yyyy-MM-dd");
		String date3Str = DateUtil.getDate(DateUtil.addDays(dateD,-2),"yyyy-MM-dd");
		String date1Str = DateUtil.getDate(DateUtil.addDays(dateD,-1),"yyyy-MM-dd");
		String date0Str = DateUtil.getDate(dateD,"yyyy-MM-dd");

		//推送规则详情统计
		JSONArray ruleArray7 = new JSONArray();
		JSONArray ruleArray3 = new JSONArray();
		JSONArray ruleArray1 = new JSONArray();
		JSONArray ruleArray0 = new JSONArray();

		Map<String,Integer> ruleCount0 = new HashMap<String, Integer>();
		Map<String,Integer> ruleCount1 = new HashMap<String, Integer>();
		Map<String,Integer> ruleCount3 = new HashMap<String, Integer>();
		Map<String,Integer> ruleCount7 = new HashMap<String, Integer>();

		//获取规则列表
		PushRuleQuery pushRuleQuery = new PushRuleQuery();
		pushRuleQuery.setUserId(userData.getUserId());
		pushRuleQuery.setFounderId(userData.getFounderId());
		List<PushRule> pushRuleList = pushRuleDAO.getPushRuleList(pushRuleQuery);
		Map<String,PushRule> ruleMap = new HashMap<String, PushRule>();
		if(ListUtil.isBlank(pushRuleList)){
			return;
		}
		for(PushRule pushRule : pushRuleList){
			ruleMap.put(pushRule.getId(),pushRule);
		}

		PushItemRuleTraceQuery pushItemRuleTraceQuery = new PushItemRuleTraceQuery();
		pushItemRuleTraceQuery.setUserId(userData.getUserId());
		pushItemRuleTraceQuery.setFounderId(userData.getFounderId());
		pushItemRuleTraceQuery.setInsertDateStart(date7Str);
		List<Map<String,Object>> traceList = pushItemRuleTraceDAO.getItemCountWithGroup(pushItemRuleTraceQuery);
		if(!ListUtil.isBlank(traceList)){
			for(Map<String,Object> map : traceList){
				String insertDate = (String) map.get("insertDate");
				int total = ((Long) map.get("total")).intValue();
				String ruleId = (String) map.get("ruleId");
				if(date0Str.compareTo(insertDate)==0){
					if(ruleCount0.containsKey(ruleId)){
						ruleCount0.put(ruleId,ruleCount0.get(ruleId)+total);
					}else{
						ruleCount0.put(ruleId,total);
					}
				}
				if(date1Str.compareTo(insertDate)==0){
					if(ruleCount1.containsKey(ruleId)){
						ruleCount1.put(ruleId,ruleCount1.get(ruleId)+total);
					}else{
						ruleCount1.put(ruleId,total);
					}
				}
				if(date3Str.compareTo(insertDate)<=0){
					if(ruleCount3.containsKey(ruleId)){
						ruleCount3.put(ruleId,ruleCount3.get(ruleId)+total);
					}else{
						ruleCount3.put(ruleId,total);
					}
				}
				if(date7Str.compareTo(insertDate)<=0){
					if(ruleCount7.containsKey(ruleId)){
						ruleCount7.put(ruleId,ruleCount7.get(ruleId)+total);
					}else{
						ruleCount7.put(ruleId,total);
					}
				}

			}
			for(String ruleId : ruleCount0.keySet()){
				PushRule rule = ruleMap.get(ruleId);
				JSONObject obj = new JSONObject();
				obj.put("rule_id",ruleId);
				obj.put("rule_name",rule.getRuleName());
				obj.put("total",ruleCount0.get(ruleId));
				ruleArray0.add(obj);
			}

			for(String ruleId : ruleCount1.keySet()){
				PushRule rule = ruleMap.get(ruleId);
				JSONObject obj = new JSONObject();
				obj.put("rule_id",ruleId);
				obj.put("rule_name",rule.getRuleName());
				obj.put("total",ruleCount1.get(ruleId));
				ruleArray1.add(obj);
			}

			for(String ruleId : ruleCount3.keySet()){
				PushRule rule = ruleMap.get(ruleId);
				JSONObject obj = new JSONObject();
				obj.put("rule_id",ruleId);
				obj.put("rule_name",rule.getRuleName());
				obj.put("total",ruleCount3.get(ruleId));
				ruleArray3.add(obj);
			}

			for(String ruleId : ruleCount7.keySet()){
				PushRule rule = ruleMap.get(ruleId);
				JSONObject obj = new JSONObject();
				obj.put("rule_id",ruleId);
				obj.put("rule_name",rule.getRuleName());
				obj.put("total",ruleCount7.get(ruleId));
				ruleArray7.add(obj);
			}
		}

		PushTotal7 pushTotal0 = new PushTotal7(1,pushItemRuleTraceDAO.getItemIdCount(new PushItemRuleTraceQuery(date0Str,null,null,userData.getUserId(),userData.getFounderId())),ruleArray0.toJSONString(),pushItemDAO.getPushItemListCount(new PushItemQuery(date0Str,null,null,userData.getUserId(),userData.getFounderId())),userData.getFounderId(),userData.getUserId());
		PushTotal7 pushTotal1 = new PushTotal7(2,pushItemRuleTraceDAO.getItemIdCount(new PushItemRuleTraceQuery(date1Str,null,null,userData.getUserId(),userData.getFounderId())),ruleArray1.toJSONString(),pushItemDAO.getPushItemListCount(new PushItemQuery(date1Str,null,null,userData.getUserId(),userData.getFounderId())),userData.getFounderId(),userData.getUserId());
		PushTotal7 pushTotal3 = new PushTotal7(3,pushItemRuleTraceDAO.getItemIdCount(new PushItemRuleTraceQuery(null,date3Str,null,userData.getUserId(),userData.getFounderId())),ruleArray3.toJSONString(),pushItemDAO.getPushItemListCount(new PushItemQuery(null,date3Str,null,userData.getUserId(),userData.getFounderId())),userData.getFounderId(),userData.getUserId());
		PushTotal7 pushTotal7 = new PushTotal7(4,pushItemRuleTraceDAO.getItemIdCount(new PushItemRuleTraceQuery( null,date7Str,null,userData.getUserId(),userData.getFounderId())),ruleArray7.toJSONString(),pushItemDAO.getPushItemListCount(new PushItemQuery(null,date7Str,null,userData.getUserId(),userData.getFounderId())),userData.getFounderId(),userData.getUserId());
		pushTotal7DAO.addPushTotal7(pushTotal0);
		pushTotal7DAO.addPushTotal7(pushTotal1);
		pushTotal7DAO.addPushTotal7(pushTotal3);
		pushTotal7DAO.addPushTotal7(pushTotal7);
		logger.biz("生成今日推送概览结果,日期:{},用户id:{}",dateD,userData.getUserId());
	}

	/**
	 * 创建每日推送数据
	 *
	 * @param dateD
	 * @param userData
	 */
	@Override
	public void createPushTotal1(Date dateD, UserData userData) {
		String date = DateUtil.getDate(dateD,"yyyy-MM-dd");
		PushRuleQuery pushRuleQuery = new PushRuleQuery();
		pushRuleQuery.setFounderId(userData.getFounderId());
		pushRuleQuery.setUserId(userData.getUserId());
//		pushRuleQuery.setRuleStatus(1);
//		pushRuleQuery.setFields("id,rule_name");
		List<PushRule> pushRuleList = pushRuleDAO.getPushRuleList(pushRuleQuery);
		PushTotal1 pushTotal1 = new PushTotal1();
		pushTotal1.setPushTime(date);
		pushTotal1.setUserId(userData.getUserId());
		pushTotal1.setFounderId(userData.getFounderId());
		if(!ListUtil.isBlank(pushRuleList)){
			Map<String,PushRule> ruleMap = new HashMap<String, PushRule>();
			for(PushRule pushRule : pushRuleList){
				ruleMap.put(pushRule.getId(),pushRule);
			}
			PushItemQuery pushItemQuery = new PushItemQuery();
			pushItemQuery.setFounderId(userData.getFounderId());
			pushItemQuery.setUserId(userData.getUserId());
			pushItemQuery.setTableId(TB.getTableId(TB.PI_FIELD,userData));
			pushItemQuery.setPushTimeEqual(date);
			List<PushItem> pushItemList = pushItemDAO.getPushItemList(pushItemQuery);

			Map<String,Integer> ruleCountMap = new HashMap<String, Integer>();
			if(!ListUtil.isBlank(pushItemList)){
				for(PushItem pushItem : pushItemList){
					for(PushRule pushRule : pushRuleList){
						if(!ruleCountMap.containsKey(pushRule.getId())){
							ruleCountMap.put(pushRule.getId(),0);
						}
						if(pushItem.getRules().indexOf(pushRule.rule(date))>-1){
							int count = ruleCountMap.get(pushRule.getId());
							ruleCountMap.put(pushRule.getId(),count+1);
						}
					}
				}
			}else{
				for(PushRule pushRule : pushRuleList){
					ruleCountMap.put(pushRule.getId(),0);
				}
			}

			Iterator<Map.Entry<String,Integer>> it = ruleCountMap.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry<String,Integer> entry = it.next();
				PushRule pushRule = ruleMap.get(entry.getKey());
				if(entry.getValue()==0 &&(pushRule.getRuleDelete()||pushRule.getRuleStatus()==0)){
					it.remove();
				}
			}

			JSONArray ruleResultDetail = new JSONArray();
			for(String ruleId : ruleCountMap.keySet()){
				JSONObject ruleResult = new JSONObject();
				ruleResult.put("rule_id",ruleId);
				ruleResult.put("rule_name",ruleMap.get(ruleId).getRuleName());
				ruleResult.put("total",ruleCountMap.get(ruleId));
				ruleResultDetail.add(ruleResult);
			}
			pushTotal1.setRuleResultDetail(ruleResultDetail.toJSONString());
			pushTotal1.setPushTotal1(pushItemRuleTraceDAO.getItemIdCount(new PushItemRuleTraceQuery(date,null,null,userData.getUserId(),userData.getFounderId())));
			pushTotal1.setPushTotal7(ListUtil.isBlank(pushItemList)?0:pushItemList.size());
		}else{
			pushTotal1.setPushTotal1(0);
			pushTotal1.setPushTotal7(0);
			pushTotal1.setRuleResultDetail(new JSONArray().toJSONString());
		}
		PushTotal1Query pushTotal1Query = new PushTotal1Query();
		pushTotal1Query.setUserId(userData.getUserId());
		pushTotal1Query.setFounderId(userData.getFounderId());
		pushTotal1Query.setPushTimeEqual(date);
		List<PushTotal1> pushTotal1List = pushTotal1DAO.getPushTotal1List(pushTotal1Query);
		if(!ListUtil.isBlank(pushTotal1List)){
			pushTotal1.setId(pushTotal1List.get(0).getId());
			pushTotal1DAO.updatePushTotal1ByKey(pushTotal1);
			logger.biz("刷新每日推送统计,日期:{},用户id:{}",date,userData.getUserId());
		}else{
			pushTotal1DAO.addPushTotal1(pushTotal1);
			logger.biz("添加每日推送统计,日期:{},用户id:{}",date,userData.getUserId());
		}
	}

	/**
	 * 插入数据库
	 * @return
	 */
	public Integer addPushTotal1(PushTotal1 pushTotal1, UserData userData){
		pushTotal1.setFounderId(userData.getFounderId());
		return pushTotal1DAO.addPushTotal1(pushTotal1);
	}

	/**
	 * 根据主键删除
	 * @return
	 */
	public Integer deletePushTotal1ByKey(     Integer id       ){
		return pushTotal1DAO.deleteByKey(    id       );
	}

	public Integer deletePushTotal1ByKeys(    List<Integer> idList    ){
		return pushTotal1DAO.deleteByKeys(    idList    );
	}

	/**
	 * 根据主键更新
	 * @return
	 */
	public Integer updatePushTotal1ByKey(PushTotal1 pushTotal1,UserData userData){
		return pushTotal1DAO.updatePushTotal1ByKey(pushTotal1);
	}

	public List<PushTotal1> getPushTotal1List(PushTotal1Query pushTotal1Query,UserData userData){
		pushTotal1Query.setFounderId(userData.getFounderId());
		pushTotal1Query.setUserId(userData.getUserId());
		pushTotal1Query.orderbyPushTime(true);
		List<PushTotal1> pushTotal1List = pushTotal1DAO.getPushTotal1List(pushTotal1Query);
		if(!ListUtil.isBlank(pushTotal1List)){
			for(PushTotal1 pushTotal1 : pushTotal1List){
				pushTotal1.setRuleResultDetailObj(JSONArray.parseArray(pushTotal1.getRuleResultDetail()));
			}
		}
		return pushTotal1List;
	}

    /**
     * 插入数据库
     * @return
     */
	public Integer addPushTotal7(PushTotal7 pushTotal7, UserData userData){
		return pushTotal7DAO.addPushTotal7(pushTotal7);
	}

    /**
     * 根据主键删除
     * @return
     */
	public Integer deletePushTotal7ByKey(     Integer id       ){
        return pushTotal7DAO.deleteByKey(    id       );
	}

    public Integer deletePushTotal7ByKeys(    List<Integer> idList    ){
        return pushTotal7DAO.deleteByKeys(    idList    );
    }

    /**
     * 根据主键更新
     * @return
     */
	public Integer updatePushTotal7ByKey(PushTotal7 pushTotal7, UserData userData){
		return pushTotal7DAO.updatePushTotal7ByKey(pushTotal7);
	}

	/**
	 * 推送统计
	 * @param pushTotal7Query 查询条件
	 * @param userData
     * @return
     */
    public List<PushTotal7> getPushTotal7List(PushTotal7Query pushTotal7Query, UserData userData){
		pushTotal7Query.setFounderId(userData.getFounderId());
		pushTotal7Query.setUserId(userData.getUserId());
		List<PushTotal7> pushTotal7List = pushTotal7DAO.getPushTotal7List(pushTotal7Query);
		if(!ListUtil.isBlank(pushTotal7List)){
			for(PushTotal7 pushTotal7 : pushTotal7List){
				pushTotal7.setPushResultDetailObj(JSONArray.parseArray(pushTotal7.getPushResultDetail()));
			}
		}
        return pushTotal7List;
	}

	/**
	 * 刷新推送统计数据
	 *
	 * @param userData
	 */
	@Override
	public void refreshPushTotal(UserData userData) {
		Long startL = System.currentTimeMillis();
		PushTotal1Query pushTotal1Query = new PushTotal1Query();
		pushTotal1Query.setUserId(userData.getUserId());
		pushTotal1Query.setFounderId(userData.getFounderId());
		pushTotal1Query.orderbyPushTime(false);
		List<PushTotal1> pushTotal1List = pushTotal1DAO.getPushTotal1List(pushTotal1Query);
		if(ListUtil.isBlank(pushTotal1List)){
			return;
		}

		Map<String,Long> dateItemCountMap = pushItemDAO.getItemCountByTime(userData);
		if(dateItemCountMap==null){
			dateItemCountMap = new HashMap<String, Long>();
		}

		for (int i = 0; i < pushTotal1List.size(); i++) {
			PushTotal1 pushTotal1 = pushTotal1List.get(i);
			if (dateItemCountMap.containsKey(pushTotal1.getPushTime())) {
				pushTotal1.setPushTotal7(dateItemCountMap.get(pushTotal1.getPushTime()).intValue());
			}else{
				pushTotal1.setPushTotal7(0);
			}
		}
		pushTotal1DAO.batchUpdatePushTotal(pushTotal1List);
		logger.biz("刷新推送工作台统计结果,用户id:{},耗时{}ms",userData.getUserId(),System.currentTimeMillis()-startL);
	}

	/**
	 * 统计推送数据
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String, Object> getPushCollectCount(Integer userId) {
		UserData userData = userDataService.getUserDataByKey(userId);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("unCollect",0);
		map.put("ruleSwitch",UserSysConfigContext.checkPushRuleUsed(userData));
		if(userData==null){
			return map;
		}
		PushItemQuery pushItemQuery = new PushItemQuery();
		pushItemQuery.setUserId(userData.getUserId());
		pushItemQuery.setFounderId(userData.getFounderId());
		map.put("unCollect",pushItemDAO.getPushItemListCount(pushItemQuery));
		return map;
	}

	/**
	 * 根据类型查询推送统计
	 *
	 * @param type@return
	 */
	@Override
	public PushTotal7 queryPushTotal7ByType(int type,UserData userData) {
		PushTotal7 pushTotal7 = new PushTotal7();
		PushTotal1Query pushTotal1Query = new PushTotal1Query();
		pushTotal1Query.setUserId(userData.getUserId());
		pushTotal1Query.setFounderId(userData.getFounderId());
		String date = DateUtil.getCurrentDate();
		switch (type){
			case 1:pushTotal1Query.setPushTimeEqual(date);break;
			case 2:pushTotal1Query.setPushTimeEqual(DateUtil.getDate(DateUtil.addDays(new Date(),-1),"yyyy-MM-dd"));break;
			case 3:pushTotal1Query.setPushTimeStart(DateUtil.getDate(DateUtil.addDays(new Date(),-2),"yyyy-MM-dd"));
				pushTotal1Query.setPushTimeEnd(date);
				break;
			case 4:pushTotal1Query.setPushTimeStart(DateUtil.getDate(DateUtil.addDays(new Date(),-6),"yyyy-MM-dd"));
				pushTotal1Query.setPushTimeEnd(date);
				break;
		}
		List<PushTotal1> pushTotal1List = pushTotal1DAO.getPushTotal1List(pushTotal1Query);

		if(!ListUtil.isBlank(pushTotal1List)){
			int pushCount=0,unCollect=0;
			Map<String,JSONObject> map = new LinkedHashMap<String, JSONObject>();
			for(PushTotal1 pushTotal1:pushTotal1List){
				pushCount+=pushTotal1.getPushTotal1();
				unCollect+=pushTotal1.getPushTotal7();
				JSONArray arr = JSONArray.parseArray(pushTotal1.getRuleResultDetail());
				if(arr!=null&&arr.size()>0){
					for(int i=0;i<arr.size();i++){
						JSONObject obj = arr.getJSONObject(i);
						String ruleId = obj.getString("rule_id");
						if(!map.containsKey(ruleId)){
							map.put(ruleId,obj);
						}else{
							JSONObject tmp = map.get(ruleId);
							tmp.put("total",tmp.getIntValue("total")+obj.getIntValue("total"));
						}
					}
				}
			}
			JSONArray newArr = new JSONArray();
			for(String ruleId:map.keySet()){
				newArr.add(map.get(ruleId));
			}
			pushTotal7.setType(type);
			pushTotal7.setPushTotal(pushCount);
			pushTotal7.setUncollectTotal(unCollect);
			pushTotal7.setPushResultDetailObj(newArr);
		}
		return pushTotal7;
	}
}
