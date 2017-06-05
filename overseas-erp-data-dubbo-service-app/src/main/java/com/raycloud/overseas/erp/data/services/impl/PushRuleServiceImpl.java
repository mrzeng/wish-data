package com.raycloud.overseas.erp.data.services.impl;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.dao.*;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.*;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.query.AbstractSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.services.api.CommonService;
import com.raycloud.overseas.erp.data.services.api.PushItemService;
import com.raycloud.overseas.erp.data.services.api.PushRuleService;
import javax.annotation.Resource;

import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.services.context.UserSysConfigContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.raycloud.overseas.erp.data.common.common.Result;
import org.springframework.util.StringUtils;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-04-08
 */
@Service("PushRuleService")
public class PushRuleServiceImpl implements PushRuleService {

	private static Logger logger= Logger.getLogger(PushRuleServiceImpl.class);

	@Resource
	PushRuleDAO pushRuleDAO;

	@Resource
	FocusItemDAO focusItemDAO;

	@Resource
	FocusMerchantDAO focusMerchantDAO;

	@Resource
	FocusMarkMapDAO focusMarkMapDAO;

	@Resource
	WishItemSolrService wishItemSolrService;

	@Resource
	DataMarkInfoDAO dataMarkInfoDAO;

	@Resource
	PushItemService pushItemService;

	@Resource
	CommonService commonService;

	@Resource
	private PushItemRecycleDAO pushItemRecycleDAO;

	@Resource
	PushItemRuleTraceDAO pushItemRuleTraceDAO;

	@Resource
	WishCategoryService wishCategoryService;


	/**
	 * 查询使用中的规则列表
	 *
	 * @return
	 */
	@Override
	public List<PushRule> queryUsingRuleList(UserData userData) {
		PushRuleQuery pushRuleQuery = new PushRuleQuery();
		pushRuleQuery.setUserId(userData.getUserId());
		pushRuleQuery.setFounderId(userData.getFounderId());
		pushRuleQuery.setRuleStatus(1);
		pushRuleQuery.setRuleDelete(false);
		return pushRuleDAO.getPushRuleList(pushRuleQuery);
	}

	/**
	 * 更新推送规则状态
	 * @param idList
	 * @param status
	 * @param userData
     */
	@Override
	public void updatePushRuleStatus(final List<String> idList, final Integer status, final UserData userData) throws BizException {
		if(!ListUtil.isBlank(idList)) {
			final List<PushRule> pushRuleList = new ArrayList<PushRule>();

			PushRuleQuery pushRuleQuery = new PushRuleQuery();
			pushRuleQuery.setUserId(userData.getUserId());
			pushRuleQuery.setFounderId(userData.getFounderId());
			List<PushRule> allRule = pushRuleDAO.getPushRuleList(pushRuleQuery);
			for(PushRule pushRule : allRule){
				if(pushRule.getRunning() == 1){
					throw new BizException(ExceptionCode.SYS_ERROR_MQ5,"策略运行中,请稍后...");
				}
				for(String id : idList){
					if(pushRule.getId().equals(id)){
						if (status == 0) {
							pushRule.setRuleStatus(status);
						}else if (status == 1) {
							pushRule.setRuleStatus(status);
							pushRule.setRunning(1);
						}else {
							pushRule.setRuleDelete(true);
						}
						pushRuleList.add(pushRule);
						break;
					}
				}
			}
			pushRuleDAO.batchUpdatePushRule(pushRuleList);
			logger.biz("更新推送规则状态,用户id:{}", userData.getUserId());
			if(status == 1){
				DataUtil.cachedThreadPool.submit(new Runnable() {
					@Override
					public void run() {
						try{
							pushItemService.refreshPushItemList(userData);
						}catch (Exception e){
							logger.error("更新失败",e);
						}finally {
							for (String id : idList) {
								PushRule dbPushRule = pushRuleDAO.getPushRuleByKey(id);
								dbPushRule.setRunning(0);
								pushRuleDAO.updatePushRuleByKey(dbPushRule);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * @param idList
	 * @param userData
	 */
	@Override
	public void deletePushRule(List<String> idList, UserData userData) {
		if(!ListUtil.isBlank(idList)) {
			PushRuleQuery pushRuleQuery = new PushRuleQuery();
			pushRuleQuery.setUserId(userData.getUserId());
			pushRuleQuery.setFounderId(userData.getFounderId());
			pushRuleQuery.setKeys(idList);
			List<PushRule> deleteRules = pushRuleDAO.getPushRuleList(pushRuleQuery);
			for(PushRule pushRule : deleteRules){
				pushRule.setRuleDelete(true);
			}
			pushRuleDAO.batchUpdatePushRule(deleteRules);
			logger.biz("删除推送规则,用户id:{}", userData.getUserId());
		}
	}

	@Override
	public void saveAndUseRule(final PushRule pushRule, final UserData userData) throws BizException {
		commonService.setSynProcess(0,DataConstant.ASYN_PUSH_RULE_SAVE,userData,null);

		pushRule.setRunning(1);
		pushRule.setRuleStatus(1);
		pushRule.setRuleDelete(false);
		pushRule.setUserId(userData.getUserId());
		pushRule.setFounderId(userData.getFounderId());
		if(pushRule.getId()!=null){
			PushRule dbPushRule = pushRuleDAO.getPushRuleByKey(pushRule.getId());
			if(dbPushRule!=null && dbPushRule.getRunning() == 1){
				throw new BizException(ExceptionCode.SYS_ERROR_MQ5,"规则正在运行,请稍后...");
			}
			pushRule.setRuleName(dbPushRule.getRuleName());
			pushRule.setUpdated(new Date());
			pushRuleDAO.updatePushRuleByKey(pushRule);
			logger.biz("用户更新推送规则:{},用户id:{}",pushRule.getRuleName(),userData.getUserId());
		}else{
			pushRule.setCreated(new Date());
			pushRule.setId(UUID.randomUUID().toString().replaceAll("-",""));
			pushRuleDAO.addPushRule(pushRule);
			logger.biz("用户添加推送规则:{},用户id:{}",pushRule.getRuleName(),userData.getUserId());
		}
		commonService.setSynProcess(30,DataConstant.ASYN_PUSH_RULE_SAVE,userData,null);
			final String ruleId = pushRule.getId();
			DataUtil.cachedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					try{
						pushItemService.refreshPushItemList(userData);
					}catch (Exception e){
						logger.error("更新失败",e);
					}finally {
						PushRule dbPushRule = pushRuleDAO.getPushRuleByKey(ruleId);
						dbPushRule.setRunning(0);
						pushRuleDAO.updatePushRuleByKey(dbPushRule);
						commonService.setSynProcess(100,DataConstant.ASYN_PUSH_RULE_SAVE,userData,null);
					}
				}
			});

		logger.biz("用户推送规则:{}保存并启用,用户id:{}",pushRule.getRuleName(),userData.getUserId());

	}

	/**
     * 推送规则插入数据库
     * @return
     */
	public String addPushRule(PushRule pushRule, UserData userData){
		pushRule.setFounderId(userData.getFounderId());
		pushRule.setUserId(userData.getUserId());
		if(pushRule.getId()!=null){
			pushRule.setUpdated(new Date());
			pushRuleDAO.updatePushRuleByKey(pushRule);
			logger.biz("用户更新推送规则:{},用户id:{}",pushRule.getRuleName(),userData.getUserId());
			return pushRule.getId();
		}else{
			pushRule.setCreated(new Date());
			pushRule.setId(UUID.randomUUID().toString().replaceAll("-",""));
			pushRuleDAO.addPushRule(pushRule);
			logger.biz("用户添加推送规则:{},用户id:{}",pushRule.getRuleName(),userData.getUserId());
			return pushRule.getId();
		}
	}
    /**
     * 根据主键查找
     */
	public PushRule getPushRuleByKey(     String id       ){
		return pushRuleDAO.getPushRuleByKey(    id       );
	}
    public List<PushRule> getPushRuleByKeys(    List<String> idList    ){
        return pushRuleDAO.getPushRuleByKeys(    idList    );
    }
    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(     String id       ){
        return pushRuleDAO.deleteByKey(id);
	}

    public Integer deleteByKeys(    List<String> idList    ){
        return pushRuleDAO.deleteByKeys(    idList    );
    }

    /**
     * 根据主键更新
     * @return
     */
	public Integer updatePushRuleByKey(PushRule pushRule, UserData userData){
	   return pushRuleDAO.updatePushRuleByKey(pushRule);
	}

	public Result<PushRule> getPushRuleListWithPage(PushRuleQuery pushRuleQuery, UserData userData){

		if(!UserSysConfigContext.checkPushRuleUsed(userData)){
			addDefaultRules(userData);

		}
		pushRuleQuery.setFounderId(userData.getFounderId());
		pushRuleQuery.setUserId(userData.getUserId());
		pushRuleQuery.setRuleDelete(false);
		Result result = pushRuleDAO.getPushRuleListWithPage(pushRuleQuery);
		List<PushRule> pushRuleList = result.getItems();
		if(!ListUtil.isBlank(pushRuleList)){
			for(PushRule pushRule : pushRuleList){
				if(!StringUtils.isEmpty(pushRule.getRuleDesc())&&pushRule.getRuleDesc().indexOf("超级店长示例规则")>-1){
					pushRule.setRuleName(pushRule.getRuleName()+"</br>"+"(默认规则)");
				}
				pushRule.setStrategyParamObj(JSON.parseObject(pushRule.getStrategyParam()));
			}
		}
		return result;
	}
    
    public List<PushRule> getPushRuleList(PushRuleQuery pushRuleQuery, UserData userData){

        return pushRuleDAO.getPushRuleList(pushRuleQuery);
	}

	/**
	 * 更新规则启用状态
	 *
	 * @param idList
	 * @param status
	 * @param userData
	 */
	@Override
	public void updateRuleStatus(List<String> idList, Integer status, UserData userData) {
		List<PushRule> pushRuleList = getPushRuleByKeys(idList);
		if(!ListUtil.isBlank(idList)){
			for(PushRule pushRule : pushRuleList){
				if(status == 0){
					pushRule.setRuleStatus(0);
				}else if(status == 1){
					pushRule.setRuleStatus(1);
				}else if(status == 2){
					pushRule.setRuleDelete(true);
				}
			}
			pushRuleDAO.batchUpdatePushRule(pushRuleList);
		}
	}

	/**
	 * 校验推送结果
	 *
	 * @param strategyType
	 * @param strategyParam
	 * @param userData
	 * @return
	 */
	@Override
	public void checkPushResult(final Integer strategyType, JSONObject strategyParam, final UserData userData) throws BizException {

		if(commonService.checkSlowOpIsRunning(DataConstant.ASYN_PUSH_RULE_CHECK,userData)){
			throw new BizException(ExceptionCode.PARAMETER_ERROE5,"规则正在校验中");
		}
		commonService.setSynProcess(0,DataConstant.ASYN_PUSH_RULE_CHECK,userData,null);

		final List<WishItemSolrQuery> wishItemSolrQueryList = parseRule(strategyType,strategyParam,userData);
		commonService.setSynProcess(30,DataConstant.ASYN_PUSH_RULE_CHECK,userData,null);
		DataUtil.cachedThreadPool.submit(new Runnable() {
			@Override
			public void run() {
					Long startL = System.currentTimeMillis();
				List<ItemDomain> itemDomainList = null;
					try{
						itemDomainList = execQuerys(wishItemSolrQueryList,strategyType,userData);
						logger.biz("校验结果:{}个,用户id:{},耗时{}ms",itemDomainList.size(),userData.getUserId(),System.currentTimeMillis()-startL);

					}catch (Exception e){
						logger.error(e);
					}finally {
						Map<String,Object> param = new HashMap<String, Object>();
						param.put("total",ListUtil.isBlank(itemDomainList)?0:itemDomainList.size());
						commonService.setSynProcess(100,DataConstant.ASYN_PUSH_RULE_CHECK,userData,param);
					}

			}
		});
	}


	/**
	 * 将规则解析成solr查询条件
	 * @param strategyType
	 * @param strategyParam
	 * @param userData
     * @return
     */
	@Override
	public List<WishItemSolrQuery> parseRule(Integer strategyType, JSONObject strategyParam,UserData userData){
		List<WishItemSolrQuery> wishItemSolrQueryList = new ArrayList<WishItemSolrQuery>();
		//创建范围聚合和排序的通用查询选项
		WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
		JSONObject amount = initJSON(strategyParam.getJSONObject("amount"));
		JSONObject amount7_range = initJSON(strategyParam.getJSONObject("amount7_range"));
		JSONObject gen_time = initJSON(strategyParam.getJSONObject("gen_time"));
		JSONObject gen_item = initJSON(strategyParam.getJSONObject("gen_item"));
		JSONObject amount_rate_range = initJSON(strategyParam.getJSONObject("amount_rate_range"));
		JSONObject amount_rate_item = initJSON(strategyParam.getJSONObject("amount_rate_item"));

		wishItemSolrQuery.setStart(0);
		if(strategyType == DataConstant.PUSH_RANGE_FOCUS_MERCHANT || strategyType == DataConstant.PUSH_RANGE_MARK_MERCHANT){
			wishItemSolrQuery.setMerchantIdCollection(getIdList(strategyType,strategyParam.getInteger("markId"),userData));
			if(wishItemSolrQuery.getMerchantIdCollection()==null){
				logger.biz("暂无关注店铺,用户id:{}",userData.getUserId());
				return Collections.EMPTY_LIST;
			}
		}else if(strategyType == DataConstant.PUSH_RANGE_FOCUS_ITEM || strategyType == DataConstant.PUSH_RANGE_MARK_ITEM){
			wishItemSolrQuery.setItemIdCollection(getIdList(strategyType,strategyParam.getInteger("markId"),userData));
			if(wishItemSolrQuery.getItemIdCollection()==null){
				logger.biz("暂无关注宝贝,用户id:{}",userData.getUserId());
				return Collections.EMPTY_LIST;
			}
		}else if(strategyType == DataConstant.PUSH_RANGE_CAT){
			WishCategory wishCategory =wishCategoryService.getWishCategoryByKey(strategyParam.getString("catId"));
			wishItemSolrQuery.setCatId(wishCategory.getCatId());
		}

		boolean sort = false;
		if(amount_rate_item.getInteger("status") == 1){//按增长率排序
			WishItemSolrQuery amountRateSortQuery = new WishItemSolrQuery();
			BeanUtils.copyProperties(wishItemSolrQuery,amountRateSortQuery);
			amountRateSortQuery.setAmountRateStart(0d);
			amountRateSortQuery.setSortAndRowInfo("amount_rate","desc",amount_rate_item.getInteger("amount_rate_item"));
			wishItemSolrQueryList.add(amountRateSortQuery);
			sort=true;
		}
		if(gen_item.getInteger("status") == 1){//按上新时间排序
			WishItemSolrQuery genTimeSortQuery = new WishItemSolrQuery();
			BeanUtils.copyProperties(wishItemSolrQuery,genTimeSortQuery);
			genTimeSortQuery.setSortAndRowInfo("gen_time","desc",gen_item.getInteger("gen_item"));
			wishItemSolrQueryList.add(genTimeSortQuery);
			sort=true;
		}
		if(amount.getInteger("status") == 1){//按销量排序
			WishItemSolrQuery amountSortQuery = new WishItemSolrQuery();
			BeanUtils.copyProperties(wishItemSolrQuery,amountSortQuery);
			amountSortQuery.setSortAndRowInfo("offer","desc",amount.getInteger("amount"));
			wishItemSolrQueryList.add(amountSortQuery);
			sort=true;
		}
		WishItemSolrQuery rangeQuery = new WishItemSolrQuery();
		BeanUtils.copyProperties(wishItemSolrQuery,rangeQuery);


		if(amount7_range.getInteger("status") == 1){//按销量范围筛选
			if(sort){
				WishItemSolrQuery amountRangeQuery = new WishItemSolrQuery();
				BeanUtils.copyProperties(wishItemSolrQuery,amountRangeQuery);
				amountRangeQuery.setAmount7Start(AbstractSolrQuery.getTotal7Start(amount7_range.getInteger("amount7_start")));
				amountRangeQuery.setAmount7End(AbstractSolrQuery.getTotal7Start(amount7_range.getInteger("amount7_end")));
				setSortAndRowInfo(amountRangeQuery,amount,gen_item,amount_rate_item);
				wishItemSolrQueryList.add(amountRangeQuery);
			}else{
				rangeQuery.setAmount7Start(AbstractSolrQuery.getTotal7Start(amount7_range.getInteger("amount7_start")));
				rangeQuery.setAmount7End(AbstractSolrQuery.getTotal7Start(amount7_range.getInteger("amount7_end")));
			}
		}

		if(amount_rate_range.getInteger("status") == 1){//按增长率范围筛选
			if(sort){
				WishItemSolrQuery amountRateRangeQuery = new WishItemSolrQuery();
				BeanUtils.copyProperties(wishItemSolrQuery,amountRateRangeQuery);
				amountRateRangeQuery.setAmountRateStart(amount_rate_range.getDoubleValue("amount_rate_range_start")*1.0/100);
				amountRateRangeQuery.setAmountRateEnd(amount_rate_range.getDoubleValue("amount_rate_range_end")*1.0/100);
				setSortAndRowInfo(amountRateRangeQuery,amount,gen_item,amount_rate_item);
				wishItemSolrQueryList.add(amountRateRangeQuery);
			}else{
				rangeQuery.setAmountRateStart(amount_rate_range.getDoubleValue("amount_rate_range_start")*1.0/100);
				rangeQuery.setAmountRateEnd(amount_rate_range.getDoubleValue("amount_rate_range_end")*1.0/100);
			}
		}

		if(gen_time.getInteger("status") == 1){//按上新时间范围筛选
			if(sort){
				WishItemSolrQuery genTimeRangeQuery = new WishItemSolrQuery();
				BeanUtils.copyProperties(wishItemSolrQuery,genTimeRangeQuery);
				int genTimeAdd = gen_time.getInteger("gen_time");
				genTimeRangeQuery.setGenTimeStart(DateUtil.addDays(new Date(),genTimeAdd*-1+1));
				genTimeRangeQuery.setGenTimeEnd(new Date());
				setSortAndRowInfo(genTimeRangeQuery,amount,gen_item,amount_rate_item);
				wishItemSolrQueryList.add(genTimeRangeQuery);
			}else{
				int genTimeAdd = gen_time.getInteger("gen_time");
				rangeQuery.setGenTimeStart(DateUtil.addDays(new Date(),genTimeAdd*-1+1));
				rangeQuery.setGenTimeEnd(new Date());
			}
		}

		if(!sort){
			rangeQuery.setSortAndRowInfo("offer","desc",200);
			wishItemSolrQueryList.add(rangeQuery);
		}

		return wishItemSolrQueryList;
	}

	private void setSortAndRowInfo(WishItemSolrQuery wishItemSolrQuery,JSONObject amount,JSONObject gen_item,JSONObject amount_rate_item){
		if(amount.getInteger("status") == 1){
			wishItemSolrQuery.setSortAndRowInfo("offer","desc",amount.getInteger("amount"));
		}else if(amount_rate_item.getInteger("status") == 1){
			wishItemSolrQuery.setSortAndRowInfo("amount_rate","desc",amount_rate_item.getInteger("amount_rate_item"));
		}else if(gen_item.getInteger("status") == 1){
			wishItemSolrQuery.setSortAndRowInfo("gen_time","desc",gen_item.getInteger("gen_item"));
		}else{
			wishItemSolrQuery.setSortAndRowInfo("offer","desc",200);
		}
	}

	private JSONObject initJSON(JSONObject object){
		if(object == null){
			object = new JSONObject();
			object.put("status",0);
		}
		return object;
	}

	/**
	 * 通过规则范围筛选店铺或宝贝id列表
	 * @param strategyType
	 * @param markId
	 * @param userData
     * @return
     */
	@Override
	public List<String> getIdList(Integer strategyType,Integer markId,UserData userData){
		List<String> idList = new ArrayList<String>();
		if(strategyType == 1){
			FocusMerchantQuery focusMerchantQuery = new FocusMerchantQuery();
			focusMerchantQuery.setUserId(userData.getUserId()+"");
			focusMerchantQuery.setFounderId(userData.getFounderId());
			focusMerchantQuery.setFocus(DataConstant.FOCUS);
			focusMerchantQuery.setFields("merchant_id");
			List<FocusMerchant> focusMerchantList = focusMerchantDAO.getFocusMerchantList(focusMerchantQuery);
			if(!ListUtil.isBlank(focusMerchantList)){
				for(FocusMerchant focusMerchant : focusMerchantList){
					idList.add(focusMerchant.getMerchantId());
				}
			}
		}else if(strategyType == 2){
			FocusMarkMapQuery focusMarkMapQuery = new FocusMarkMapQuery();
			focusMarkMapQuery.setFounderId(userData.getFounderId());
			focusMarkMapQuery.setMarkId(markId);
			focusMarkMapQuery.setFields("focus_id");
			List<FocusMarkMap> focusMarkMapList = focusMarkMapDAO.getFocusMarkMapList(focusMarkMapQuery);
			if(!ListUtil.isBlank(focusMarkMapList)){
				for(FocusMarkMap focusMarkMap : focusMarkMapList){
					idList.add(focusMarkMap.getFocusId());
				}
			}
		}else if(strategyType == 3){
			FocusItemQuery focusItemQuery = new FocusItemQuery();
			focusItemQuery.setFounderId(userData.getFounderId());
			focusItemQuery.setUserId(userData.getUserId()+"");
			focusItemQuery.setFocus(DataConstant.FOCUS);
			focusItemQuery.setCollected(DataConstant.UN_COLLECT);
			focusItemQuery.setFields("item_id");
			List<FocusItem> focusItemList = focusItemDAO.getFocusItemList(focusItemQuery);
			if(!ListUtil.isBlank(focusItemList)){
				for(FocusItem focusItem : focusItemList){
					idList.add(focusItem.getItemId());
				}
			}
		}else if(strategyType == 4){
			FocusMarkMapQuery focusMarkMapQuery = new FocusMarkMapQuery();
			focusMarkMapQuery.setFounderId(userData.getFounderId());
			focusMarkMapQuery.setMarkId(markId);
			focusMarkMapQuery.setFields("focus_id");
			List<FocusMarkMap> focusMarkMapList = focusMarkMapDAO.getFocusMarkMapList(focusMarkMapQuery);
			if(!ListUtil.isBlank(focusMarkMapList)){
				for(FocusMarkMap focusMarkMap : focusMarkMapList){
					idList.add(focusMarkMap.getFocusId());
				}
			}
		}
		return ListUtil.isBlank(idList)?null:idList;
	}

	/**
	 * 执行查询wish宝贝列表
	 *
	 * @param wishItemSolrQueryList
	 * @return
	 */
	@Override
	public List<ItemDomain> execQuerys(List<WishItemSolrQuery> wishItemSolrQueryList,Integer type,UserData userData) {
		List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>();
		Map<String,ItemDomain> map = new HashMap<String, ItemDomain>();
		if(!ListUtil.isBlank(wishItemSolrQueryList)){
			int i = 0,row = wishItemSolrQueryList.get(0).getRows();
			Map<String,Integer> itemJoinMap = new HashMap<String, Integer>();
			for(WishItemSolrQuery wishItemSolrQuery : wishItemSolrQueryList){
				Result result = null;
				if(type == 1||type == 2){
					result = wishItemSolrService.blockQueryDocs2(wishItemSolrQuery);
				}else if(type == 3||type == 4){
					result = wishItemSolrService.blockQueryDocs(wishItemSolrQuery);
				}else if(type == 5){
					result = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery);
				}
				List<ItemDomain> itemDomainList1 = result.getItems();
				if(!ListUtil.isBlank(itemDomainList1)){
					for(ItemDomain itemDomain : itemDomainList1){
						map.put(itemDomain.getItemId(),itemDomain);
						if(!itemJoinMap.containsKey(itemDomain.getItemId())){
							itemJoinMap.put(itemDomain.getItemId(),1);
						}else{
							itemJoinMap.put(itemDomain.getItemId(),itemJoinMap.get(itemDomain.getItemId())+1);
						}
					}
				}
			}
			if(itemJoinMap.size()>0){
				Map<String,String> dealMap = pushItemRecycleDAO.getPushItemRecycleIdMap(new PushItemRecycleQuery(userData.getUserId(),userData.getFounderId()));
				FocusItemQuery focusItemQuery = new FocusItemQuery();
				focusItemQuery.setCollected(DataConstant.COLLECT);
				focusItemQuery.setUserId(userData.getUserId()+"");
				focusItemQuery.setFounderId(userData.getFounderId());
				Map<String,String> collectedMap = focusItemDAO.getItemIdMap(focusItemQuery);
				dealMap.putAll(collectedMap);
				for(String itemId : itemJoinMap.keySet()){
					if(i>=row){
						break;
					}
					if(itemJoinMap.get(itemId) == wishItemSolrQueryList.size() && !dealMap.containsKey(itemId)){
						i++;
						itemDomainList.add(map.get(itemId));
					}
				}
			}
		}
		return itemDomainList;
	}

	/**
	 * 查询用户店铺和宝贝拥有的标签
	 *
	 * @param userData
	 * @return
	 */
	@Override
	public Map<String, Object> queryRuleItemRange(UserData userData) {
		DataMarkInfoQuery dataMarkInfoQuery = new DataMarkInfoQuery();
		dataMarkInfoQuery.setFounderId(userData.getFounderId());
		dataMarkInfoQuery.setUserId(userData.getUserId());
		List<DataMarkInfo> dataMarkInfoList = dataMarkInfoDAO.getDataMarkInfoList(dataMarkInfoQuery);
		Map<String,Object> map = new HashMap<String, Object>();
		List<DataMarkInfo> dataMarkInfoList1 = new ArrayList<DataMarkInfo>();
		List<DataMarkInfo> dataMarkInfoList2 = new ArrayList<DataMarkInfo>();
		if(!ListUtil.isBlank(dataMarkInfoList)){
			for(DataMarkInfo dataMarkInfo : dataMarkInfoList){
				if(dataMarkInfo.getType().equals("2")){//产品
					dataMarkInfoList1.add(dataMarkInfo);
				}else if(dataMarkInfo.getType().equals("3")){//店铺
					dataMarkInfoList2.add(dataMarkInfo);
				}
			}
		}
		map.put("iMarkList",dataMarkInfoList1);
		map.put("mMarkList",dataMarkInfoList2);
		return map;
	}

	@Override
	public void addDefaultRules(UserData userData) {
		PushRule pushRule = new PushRule();
		pushRule.setRuleName("上新产品");
		pushRule.setRuleDesc("超级店长示例规则，用于收集关注店铺中的上新产品");
		pushRule.setRuleStatus(0);
		pushRule.setStrategyType(1);
		pushRule.setRuleDelete(false);
		pushRule.setRunning(0);
		pushRule.setPushTotal7(0);
		JSONObject param = new JSONObject();
		JSONObject gen_item = new JSONObject();
		gen_item.put("gen_item",30);
		gen_item.put("status",1);
		param.put("gen_item",gen_item);
		pushRule.setStrategyParam(param.toJSONString());
		addPushRule(pushRule,userData);

		PushRule pushRule2 = new PushRule();
		pushRule2.setRuleName("热销产品");
		pushRule2.setRuleDesc("超级店长示例规则，用于收集关注店铺中的新的热销产品");
		pushRule2.setRuleStatus(0);
		pushRule2.setStrategyType(1);
		pushRule2.setRuleDelete(false);
		pushRule2.setRunning(0);
		pushRule2.setPushTotal7(0);
		JSONObject param2 = new JSONObject();
		JSONObject amount = new JSONObject();
		amount.put("amount",30);
		amount.put("status",1);
		param2.put("amount",amount);
		pushRule2.setStrategyParam(param2.toJSONString());
		addPushRule(pushRule2,userData);

		PushRule pushRule3 = new PushRule();
		pushRule3.setRuleName("飙升产品");
		pushRule3.setRuleDesc("超级店长示例规则，用于收集关注店铺中的新的飙升产品");
		pushRule3.setRuleStatus(0);
		pushRule3.setStrategyType(1);
		pushRule3.setRuleDelete(false);
		pushRule3.setRunning(0);
		pushRule3.setPushTotal7(0);
		JSONObject param3 = new JSONObject();
		JSONObject mount_rate_item = new JSONObject();
		mount_rate_item.put("amount_rate_item",30);
		mount_rate_item.put("status",1);
		param3.put("amount_rate_item",mount_rate_item);
		pushRule3.setStrategyParam(param3.toJSONString());
		addPushRule(pushRule3,userData);

		UserSysConfigContext.setPushRuleUsed(userData);
		logger.biz("添加默认推送规则,用户id:{}",userData.getUserId());
	}

	/**
	 * 获取推送规则
	 *
	 * @param ruleId
	 * @param userData
	 * @return
	 */
	@Override
	public PushRule getRule(String ruleId, UserData userData){

		PushRule pushRule = pushRuleDAO.getPushRuleByKey(ruleId);
		if(pushRule!=null&&pushRule.getRuleDelete()){
			return null;
		}
		if(pushRule!=null){
			pushRule.setStrategyParamObj(JSON.parseObject(pushRule.getStrategyParam()));
		}
		return pushRule;
	}

	/**
	 * 更新total7的值
	 *
	 * @param pushRuleList
	 */
	@Override
	public void updateTotal7(List<PushRule> pushRuleList,String date,UserData userData) {
		if(!ListUtil.isBlank(pushRuleList)){
			PushItemRuleTraceQuery pushItemRuleTraceQuery = new PushItemRuleTraceQuery();
			pushItemRuleTraceQuery.setUserId(userData.getUserId());
			pushItemRuleTraceQuery.setFounderId(userData.getFounderId());
			pushItemRuleTraceQuery.setInsertDateStart(DateUtil.getDate(DateUtil.addDays(new Date(),-6),"yyyy-MM-dd"));
			pushItemRuleTraceQuery.setInsertDateEnd(date);
			for(PushRule pushRule : pushRuleList) {
				pushItemRuleTraceQuery.setRuleId(pushRule.getId());
				pushRule.setPushTotal7(pushItemRuleTraceDAO.getItemIdCount(pushItemRuleTraceQuery));
				pushRuleDAO.updatePushRuleByKey(pushRule);
			}
		}
	}
}
