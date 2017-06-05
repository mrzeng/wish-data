package com.raycloud.overseas.erp.data.services.impl;

import com.alibaba.asyncload.AsyncLoadConfig;
import com.alibaba.asyncload.AsyncLoadExecutor;
import com.alibaba.asyncload.impl.AsyncLoadEnhanceProxy;
import com.alibaba.fastjson.JSON;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.*;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.*;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.services.api.*;
import com.raycloud.overseas.erp.data.services.context.CollectContext;
import com.raycloud.overseas.erp.data.services.context.RefreshItemContext;
import com.raycloud.overseas.erp.data.services.context.UserSysConfigContext;
import com.raycloud.overseas.erp.data.services.task.PushItemTask;
import com.raycloud.overseas.erp.services.product.wish.IWishDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-13
 */
@Service("PushItemService")
public class PushItemServiceImpl  implements PushItemService {

	private static Logger logger= Logger.getLogger(PushItemMerchantMapServiceImpl.class);

	@Resource
	PushItemDAO pushItemDAO;

	@Autowired
	PushItemMerchantMapService pushItemMerchantMapService;

	@Autowired
	private WishCategoryService wishCategoryService;

	@Resource
	private AsyncLoadExecutor asyncLoadExecutor;

	@Resource
	private AsyncLoadConfig asyncLoadConfig;

	@Resource
	private UserDataService userDataService;

	@Autowired
	private ILocalItemService localItemService;

	@Resource
	private PushRuleDAO pushRuleDAO;

	@Resource
	private PushRuleService pushRuleService;

	@Resource
	private PushItemRuleTraceDAO pushItemRuleTraceDAO;

	@Resource
	private PushWorkBenchService pushWorkBenchService;

	@Resource
	private PushItemRecycleDAO pushItemRecycleDAO;

	@Resource
	private CommonService commonService;

	@Autowired
	private IWishDataService wishDataService;

	@Autowired
	private FocusItemDAO focusItemDAO;

	@Override
	public void removePushItemList(UserData userData,List<String> itemIdList) {
		Long startL = System.currentTimeMillis();
		if(ListUtil.isBlank(itemIdList)){
			logger.biz("待移入的推送宝贝个数:0,用户id:{}",userData.getUserId());
			return;
		}
		List<PushItemRecycle> pushItemRecycleList = new ArrayList<PushItemRecycle>();
		PushItemQuery pushItemQuery1 = new PushItemQuery();
		for(String itemId:itemIdList){
			pushItemRecycleList.add(new PushItemRecycle(itemId,userData.getUserId(),userData.getFounderId()));
		}
		pushItemQuery1.setItemIdList(itemIdList);
		pushItemQuery1.setUserId(userData.getUserId());
		pushItemQuery1.setFounderId(userData.getFounderId());
		pushItemQuery1.setTableId(TB.getTableId(TB.PI_FIELD,userData));
		pushItemDAO.deletePushItem(pushItemQuery1);
		pushItemRecycleDAO.batchAddPushItemRecycle(pushItemRecycleList);

		pushWorkBenchService.refreshPushTotal(userData);
		logger.biz("推送宝贝移入回收站,宝贝个数:{},用户id:{},耗时{}ms",itemIdList.size(),userData.getUserId(),System.currentTimeMillis()-startL);
	}

	public Integer addPushItem(PushItem pushItem){
		return pushItemDAO.addPushItem(pushItem);
	}


	public Result<PushItem> getPushItemListWithPage(PushItemQuery pushItemQuery,UserData userData) throws BizException {

		pushItemQuery.setUserId(userData.getUserId());
		pushItemQuery.setFounderId(userData.getFounderId());
		pushItemQuery.setTableId(TB.getTableId(TB.PI_FIELD,userData));
		Result<PushItem> rs = null;
		Long startL = System.currentTimeMillis();
		if(!StringUtils.isEmpty(pushItemQuery.getCatId())){
			WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(pushItemQuery.getCatId());
			if(wishCategory!=null && wishCategory.getLevel()>0){
				pushItemQuery.setCatIds(wishCategory.getCatId());
			}
		}
		dealComplexQuery(pushItemQuery);

		rs = pushItemDAO.getPushItemListWithPage(pushItemQuery);

		List<PushItem> pushItemList = rs.getItems();
		if(!ListUtil.isBlank(pushItemList)){
			for(PushItem pushItem:pushItemList){
				pushItem.setPushTime(pushItem.getPushTime().substring(0,10));
				if(!StringUtils.isEmpty(pushItem.getGenTime())){
					pushItem.setGenTime(pushItem.getGenTime().substring(0,10));
				}
				pushItem.setItemLogoUrl("http://contestimg.wish.com/api/webimage/"+pushItem.getItemId()+"-small.jpg");
				String[] rules = pushItem.getRules().split(";");
				StringBuffer sb = new StringBuffer();
				for(int i = 0;i<rules.length;i++){
					if(i<rules.length-1){
						sb.append(rules[i].split("\\*")[2]);
						sb.append(",");
					}else{
						sb.append(rules[i].split("\\*")[2]);
					}
				}
				pushItem.setRules(sb.toString());
			}
		}
		logger.biz("获取推送列表,用户id:{},耗时{}ms",pushItemQuery.getUserId(),(System.currentTimeMillis()-startL));
		return rs;
	}

	/**
	 * 处理复杂查询
	 * 1:工作台采集，2工作台概览规则，3工作台每日推送总和点击，4工作台每日推送规则数量名称按钮点击，5推送规则7日总和跳转
	 * @param pushItemQuery
     */
	@Override
	public void dealComplexQuery(PushItemQuery pushItemQuery){
		if(pushItemQuery.getQueryType()!=null){

			if(pushItemQuery.getQueryType() != 1){//除工作台采集

				PushItemRuleTraceQuery pushItemRuleTraceQuery = new PushItemRuleTraceQuery();
				if(pushItemQuery.getRuleId() == null){
					pushItemRuleTraceQuery.setUserId(pushItemQuery.getUserId());
					pushItemRuleTraceQuery.setFounderId(pushItemQuery.getFounderId());
				}
				pushItemRuleTraceQuery.setRuleId(pushItemQuery.getRuleId());
				pushItemRuleTraceQuery.setInsertDateStart(pushItemQuery.getPushTimeStart());
				pushItemRuleTraceQuery.setInsertDateEnd(pushItemQuery.getPushTimeEnd());
				pushItemRuleTraceQuery.setFields("item_id");
				List<String> itemIdList = pushItemRuleTraceDAO.getItemIdList(pushItemRuleTraceQuery);
				ListUtil.removeDuplicate(itemIdList);
				if(!ListUtil.isBlank(itemIdList)){
					pushItemQuery.setItemIdList(itemIdList);
				}else{
					pushItemQuery.setUserId(-1);
				}

			}
		}
	}

	public List<PushItem> getPushItemList(PushItemQuery pushItemQuery){
	   return pushItemDAO.getPushItemList(pushItemQuery);
	}




	/**
	 * 采集推送宝贝
	 * @param userData
	 * @param itemIdList
	 * @throws BizException
     */
	@Override
	public void collectPushItems(int sum,final UserData userData, final List<String> itemIdList, final List<String> merchantIdList,final boolean needBean) throws BizException {
		if(ListUtil.isBlank(itemIdList)){
			throw new BizException(ExceptionCode.NULL_ERROR,"请选择宝贝");
		}
		if(commonService.checkSlowOpIsRunning(DataConstant.ASYN_ITEM_COLLECT,userData)){
			logger.biz("用户id:{},正在采集,请稍后",userData.getUserId());
			return;
		}

		Map<String,String> map = wishDataService.checkItems(itemIdList,userData.getLongFounderId());
		final int preconsume = sum-map.size();
		if(needBean){
			commonService.preConsumeBeans(DataConstant.ASYN_ITEM_COLLECT,userData,preconsume);
		}
		commonService.setSynProcess(0,DataConstant.ASYN_ITEM_COLLECT,userData,null);
		DataUtil.cachedThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				CollectContext collectContext = localItemService.collectToLocalByEntry(itemIdList,merchantIdList,needBean,userData);
				try {
					if(needBean){
						commonService.afterConsumeBeans(DataConstant.ASYN_ITEM_COLLECT,userData,preconsume);
					}
				} catch (BizException e) {
					logger.error("消费出错",e);
				}
				removePushItemList(userData,collectContext.sucIdList);
			}
		});

	}



	/**
	 * 查询状态列表
	 *
	 * @param itemIdList
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getStatusList(List<String> itemIdList,UserData userData) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(!ListUtil.isBlank(itemIdList)){
			Map<String,String> collectedItemIdMap = wishDataService.checkItems(itemIdList,userData.getLongFounderId());
			FocusItemQuery focusItemQuery = new FocusItemQuery();
			focusItemQuery.setUserId(userData.getUserId()+"");
			focusItemQuery.setFounderId(userData.getFounderId());
			focusItemQuery.setKeys(itemIdList);
			focusItemQuery.setFields("item_id,focus");
			List<FocusItem> focusItemList = focusItemDAO.getFocusItemList(focusItemQuery);
			Map<String,FocusItem> focusItemMap = new HashMap<String, FocusItem>();
			if(!ListUtil.isBlank(focusItemList)){
				for(FocusItem focusItem : focusItemList){
					focusItemMap.put(focusItem.getItemId(),focusItem);
				}
			}
			for(String itemId:itemIdList){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("itemId",itemId);
				if(focusItemMap.containsKey(itemId)){
					map.put("focus",focusItemMap.get(itemId).getFocus());
				}else{
					map.put("focus",0);
				}
				if(collectedItemIdMap.containsKey(itemId)){
					map.put("collected",1);
				}else{
					map.put("collected",0);
				}
				list.add(map);
			}
		}
		return list;
	}

	public void timerRefreshPushItemTask(){
			logger.biz("定时刷新用户推送宝贝");
			List<Integer> userIdList = pushRuleDAO.getRuleUserIdList();
			Long startL = System.currentTimeMillis();
			for (Integer userId : userIdList) {

				PushItemTask pushItemTask = new PushItemTask();
				AsyncLoadEnhanceProxy<PushItemTask> pushItemTaskAsyncLoadEnhanceProxy = new
						AsyncLoadEnhanceProxy<PushItemTask>();
				pushItemTaskAsyncLoadEnhanceProxy.setExecutor(asyncLoadExecutor);
				pushItemTaskAsyncLoadEnhanceProxy.setConfig(asyncLoadConfig);
				pushItemTaskAsyncLoadEnhanceProxy.setService(pushItemTask);
				PushItemTask proxy = pushItemTaskAsyncLoadEnhanceProxy.getProxy();
				UserData userData = userDataService.getUserDataByKey(userId);
				proxy.refreshPushItem(userData);
			}
			logger.biz("更新推送宝贝任务结束,用户数:{},耗时:{}",userIdList.size(),(System.currentTimeMillis()-startL));
	}

	public void timerCancleItemFocusTask(){

			Calendar calendar = Calendar.getInstance();

			FocusItemQuery focusItemQuery = new FocusItemQuery();
			focusItemQuery.setFocus(1);
			focusItemQuery.setExpireTimeEnd(calendar.getTime());

			List<FocusItem> expireFocusItemList = localItemService.getFocusItemList(focusItemQuery);
			if(!ListUtil.isBlank(expireFocusItemList)){
				for(FocusItem focusItem : expireFocusItemList){
					logger.biz("关注宝贝24小时到期,恢复为未关注状态,宝贝id:{},用户id:{}",focusItem.getItemId(),focusItem.getUserId());
					focusItem.setFocus(0);
				}
				logger.biz("本次取消关注宝贝{}个",expireFocusItemList.size());
				localItemService.batchUpdateFocusItem(expireFocusItemList);
			}


	}


	public void refreshPushItemList(UserData userData) throws BizException {
		if(UserSysConfigContext.checkItemRefreshed(userData)){
			logger.biz("当日规则已经推送,本次更改将于明天凌晨生效,用户id:{}",userData.getUserId());
			return;
		}
		Long startL = System.currentTimeMillis();
		logger.biz("刷新用户{}智能推送数据",userData.getUserId());
		RefreshItemContext refreshItemContext = createRefreshContext(userData);
		refreshItemContext.deleteExpireData();
		refreshItemContext.loadHistoryData();
		refreshItemContext.loadItemContainerByRule();
		refreshItemContext.resetMerchantList();
		refreshItemContext.itemIntoDB();
		pushItemRuleTraceDAO.batchAddPushItemRuleTrace(refreshItemContext.ruleTraceItemList);
		pushWorkBenchService.createPushTotal1(DateUtil.getDate(refreshItemContext.date,"yyyy-MM-dd"),userData);
		pushWorkBenchService.createPushTotal7(DateUtil.getDate(refreshItemContext.date,"yyyy-MM-dd"),userData);
		pushRuleService.updateTotal7(refreshItemContext.pushRuleList,refreshItemContext.date,userData);
		UserSysConfigContext.setItemRefresh(userData);
		logger.biz("推送宝贝刷新,历史推送宝贝{}个,新增推送宝贝{}个,更新推送宝贝{}个,更新店铺{}个,用户id:{},耗时{}ms",refreshItemContext.oldPushItemDomainMap.size(),
				refreshItemContext.addPushItemDomainMap.size(),refreshItemContext.updatePushItemDomainMap.size(),refreshItemContext.merchantDomainMap.size(),userData.getUserId(),(System.currentTimeMillis()-startL));

	}

	/**
	 * 创建推送上下文
	 * @param userData
	 * @return
	 * @throws BizException
     */
	public RefreshItemContext createRefreshContext(UserData userData) throws BizException {
		PushRuleQuery pushRuleQuery = new PushRuleQuery();
		pushRuleQuery.setRuleStatus(1);
		pushRuleQuery.setRuleDelete(false);
		pushRuleQuery.setUserId(userData.getUserId());
		pushRuleQuery.setFounderId(userData.getFounderId());
		List<PushRule> pushRuleList = pushRuleService.getPushRuleList(pushRuleQuery,userData);
		if(ListUtil.isBlank(pushRuleList)){
			throw new BizException(ExceptionCode.NULL_ERROR,"暂无推送规则");
		}else{
			RefreshItemContext refreshItemContext = new RefreshItemContext(userData);
			refreshItemContext.pushRuleList = pushRuleList;
			return refreshItemContext;
		}
	}
}
