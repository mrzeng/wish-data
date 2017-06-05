package com.raycloud.overseas.erp.data.services.context;

import com.alibaba.fastjson.JSON;
import com.raycloud.overseas.erp.data.common.dao.FocusItemDAO;
import com.raycloud.overseas.erp.data.common.dao.PushItemDAO;
import com.raycloud.overseas.erp.data.common.dao.PushTotal1DAO;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.query.FocusItemQuery;
import com.raycloud.overseas.erp.data.common.query.PushItemQuery;
import com.raycloud.overseas.erp.data.common.query.PushTotal1Query;
import com.raycloud.overseas.erp.data.common.spring.SpringUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.core.WishMerchantSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.services.api.PushItemMerchantMapService;
import com.raycloud.overseas.erp.data.services.api.PushRuleService;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;

import java.util.*;

/**
 * Created by zhanxf on 17/4/25.
 */
public class RefreshItemContext {

    public RefreshItemContext() {
    }

    public RefreshItemContext(UserData userData) {
        this.userData = userData;
    }

    //操作日期
    public String date = DateUtil.getCurrentDate();

    //操作用户
    public UserData userData;

    //装载推送宝贝的店铺id列表
    public List<String> flushMerchantIdList = new ArrayList<String>();

    //历史推送宝贝
    public Map<String,PushItem> oldPushItemDomainMap = new HashMap<String, PushItem>();

    //新增推送宝贝
    public Map<String,PushItem> addPushItemDomainMap = new HashMap<String, PushItem>();

    //待更新推送宝贝
    public Map<String,PushItem> updatePushItemDomainMap = new HashMap<String, PushItem>();

    //待更新店铺
    public Map<String,MerchantDomain> merchantDomainMap = new HashMap<String, MerchantDomain>();

    public List<PushRule> pushRuleList;

    public List<PushItemRuleTrace> ruleTraceItemList = new ArrayList<PushItemRuleTrace>();

    /**
     * 更新宝贝数据入库
     */
    public void itemIntoDB(){
        //添加新的推送宝贝
        if(addPushItemDomainMap.size()>0){
            List<PushItem> pushItemDomainList = new ArrayList<PushItem>(addPushItemDomainMap.size());
            for(String itemId:addPushItemDomainMap.keySet()){
                PushItem pushItem = addPushItemDomainMap.get(itemId);
                pushItem.setMerchantName(merchantDomainMap.get(pushItem.getMerchantId()).getMerchantNick());
                pushItem.setTableId(TB.getTableId(TB.PI_FIELD,userData));
                pushItem.setFounderId(userData.getFounderId());
                pushItemDomainList.add(pushItem);
            }
            pushItemDAO.batchInsertPushItem(pushItemDomainList);
        }

        Set<String> solrDataUpdItemSet = oldPushItemDomainMap.keySet();
        //刷新推送宝贝solr统计数据
        List<PushItem> pushItemDomainList2 = new ArrayList<PushItem>(updatePushItemDomainMap.size());
        //规则获取的更新宝贝数据已经刷新过推送数据,so进行剔除
        solrDataUpdItemSet.removeAll(updatePushItemDomainMap.keySet());
        if(solrDataUpdItemSet!=null && solrDataUpdItemSet.size()>0){
            WishItemSolrQuery flushSolrDataQuery = new WishItemSolrQuery();
            flushSolrDataQuery.setItemIdCollection(solrDataUpdItemSet);
            flushSolrDataQuery.setStart(0);
            flushSolrDataQuery.setRows(solrDataUpdItemSet.size());
            List<ItemDomain> flushItemDomainList = wishItemSolrService.blockQueryDocs(flushSolrDataQuery).getItems();
            for(ItemDomain itemDomain : flushItemDomainList){
                PushItem pushItem = oldPushItemDomainMap.get(itemDomain.getItemId());
                pushItem.setTableId(TB.getTableId(TB.PI_FIELD,userData));
                refreshPushItemSolrData(pushItem,itemDomain);
                pushItemDomainList2.add(pushItem);
            }
        }

        //更新旧推送宝贝
        if(updatePushItemDomainMap.size()>0){
            for(String itemId:updatePushItemDomainMap.keySet()){
                PushItem pushItem = updatePushItemDomainMap.get(itemId);
                pushItem.setTableId(TB.getTableId(TB.PI_FIELD,userData));
                pushItemDomainList2.add(pushItem);
            }
        }
        pushItemDAO.batchUpdatePushItem(pushItemDomainList2);
    }


    /**
     * 映射solr数据
     * @param pushItem
     * @param itemDomain
     */
    public void refreshPushItemSolrData(PushItem pushItem,ItemDomain itemDomain){
        pushItem.setGenTime(itemDomain.getGenTime());
        pushItem.setItemName(itemDomain.getItemName());
        pushItem.setGrowth(itemDomain.getGrowth());
        pushItem.setRateScore(itemDomain.getRateScore());
        pushItem.setOriginalPrice(itemDomain.getOriginalPrice());
        pushItem.setSellerPrice(itemDomain.getSellerPrice());
        pushItem.setSellerFeightPrice(itemDomain.getSellerFeightPrice());
        pushItem.setSellerTotalPrice(itemDomain.getSellerPrice()+itemDomain.getSellerFeightPrice());
        pushItem.setWishPrice(itemDomain.getWishPrice());
        pushItem.setWishFeightPrice(itemDomain.getWishFeightPrice());
        pushItem.setWishTotalPrice(itemDomain.getWishPrice()+itemDomain.getWishFeightPrice());
        pushItem.setAmount7(itemDomain.getAmount7());
        pushItem.setAmount(itemDomain.getAmount());
        pushItem.setNewSave7(itemDomain.getNewSave7());
        pushItem.setNewSave(itemDomain.getNewSave());
        pushItem.setRateNum7(itemDomain.getRateNum7());
        pushItem.setRateNum(itemDomain.getRateNum());
        pushItem.setCatIds(itemDomain.getCatIds());
        pushItem.setCatNames(wishCategoryService.getCategoryNames(itemDomain.getCatIds()));
    }

    /**
     * 删除过期推送数据和统计数据
     */
    public void deleteExpireData(){
        PushItemQuery deleteQuery = new PushItemQuery();
        deleteQuery.setUserId(userData.getUserId());
        deleteQuery.setFounderId(userData.getFounderId());
        deleteQuery.setTableId(TB.getTableId(TB.PI_FIELD,userData));
        String date8Str = DateUtil.getDate(DateUtil.addDays(DateUtil.getDate(date,"yyyy-MM-dd"),-7),"yyyy-MM-dd");
        deleteQuery.setPushTimeEnd(date8Str);
        pushItemDAO.deletePushItem(deleteQuery);

        PushTotal1Query pushTotal1Query = new PushTotal1Query();
        pushTotal1Query.setUserId(userData.getUserId());
        pushTotal1Query.setFounderId(userData.getFounderId());
        pushTotal1Query.setPushTimeEnd(date8Str);
        pushTotal1DAO.deletePushTotal1(pushTotal1Query);
    }

    /**
     * 加载历史宝贝,并获取历史宝贝所使用的店铺数据
     */
    public void loadHistoryData(){
        PushItemQuery pushItemQuery = new PushItemQuery();
        pushItemQuery.setUserId(userData.getUserId());
        pushItemQuery.setFounderId(userData.getFounderId());
        pushItemQuery.setTableId(TB.getTableId(TB.PI_FIELD,userData));
        List<PushItem> oldPushItemList = pushItemDAO.getPushItemList(pushItemQuery);
        for(PushItem pushItem : oldPushItemList){
            flushMerchantIdList.add(pushItem.getMerchantId());
            oldPushItemDomainMap.put(pushItem.getItemId(),pushItem);
        }
    }

    /**
     * 重置推送宝贝的店铺列表
     */
    public void resetMerchantList() {
        ListUtil.removeDuplicate(flushMerchantIdList);
        WishMerchantSolrQuery wishMerchantSolrQuery = new WishMerchantSolrQuery();
        if(!ListUtil.isBlank(flushMerchantIdList) && flushMerchantIdList.size()>500){
            flushMerchantIdList = flushMerchantIdList.subList(0,500);
        }
        if(!ListUtil.isBlank(flushMerchantIdList)){
            wishMerchantSolrQuery.setMerchantIdCollection(flushMerchantIdList);
            wishMerchantSolrQuery.setRows(500);//设置上限500个店铺
            List<MerchantDomain> merchantDomainList = wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery).getItems();
            if(!ListUtil.isBlank(merchantDomainList)){
                List<PushItemMerchantMap> pushItemMerchantMapList = new ArrayList<PushItemMerchantMap>(merchantDomainList.size());
                for(MerchantDomain merchantDomain : merchantDomainList){
                    PushItemMerchantMap pushItemMerchantMap = new PushItemMerchantMap();
                    pushItemMerchantMap.setMerchantId(merchantDomain.getMerchantId());
                    pushItemMerchantMap.setMerchantName(merchantDomain.getMerchantNick());
                    pushItemMerchantMap.setUserId(Long.parseLong(userData.getUserId()+""));
                    pushItemMerchantMap.setFounderId(userData.getFounderId());
                    pushItemMerchantMapList.add(pushItemMerchantMap);
                    merchantDomainMap.put(merchantDomain.getMerchantId(),merchantDomain);
                }
                pushItemMerchantMapService.deletePushItemMerchantMapByUserId(userData.getFounderId(),Long.parseLong(userData.getUserId()+""));
                pushItemMerchantMapService.batchInsertPushItemMerchantMap(pushItemMerchantMapList);
            }
        }

    }

    public Map<String,String> _getFocusItemIdMap(PushRule pushRule,List<ItemDomain> itemDomainList){
        Map<String,String> focusItemMap = null;
        //不是以宝贝为维度的筛选
        if(pushRule.getStrategyType()!=3 && pushRule.getStrategyType()!=4){
            //获取关注宝贝列表
            List<String> keys = new ArrayList<String>();
            for(ItemDomain itemDomain : itemDomainList){
                keys.add(itemDomain.getItemId());
            }
            FocusItemQuery focusItemQuery = new FocusItemQuery();
            focusItemQuery.setUserId(userData.getUserId()+"");
            focusItemQuery.setFounderId(userData.getFounderId());
            focusItemQuery.setKeys(keys);
            focusItemQuery.setFocus(1);
            focusItemMap = focusItemDAO.getItemIdMap(focusItemQuery);
        }
        return focusItemMap;
    }

    /**
     * 通过规则加载宝贝容器
     */
    public void loadItemContainerByRule(){
        for(PushRule pushRule : pushRuleList) {
            List<WishItemSolrQuery> wishItemSolrQueryList = pushRuleService.parseRule(pushRule.getStrategyType(), JSON.parseObject(pushRule.getStrategyParam()),userData);
            List<ItemDomain> itemDomainList = pushRuleService.execQuerys(wishItemSolrQueryList,pushRule.getStrategyType(),userData);
            if(!ListUtil.isBlank(itemDomainList)){

                Map<String,String> focusItemMap = _getFocusItemIdMap(pushRule,itemDomainList);
                String rule = pushRule.rule(date);
                for(ItemDomain itemDomain : itemDomainList){
                    boolean focus;
                    if(focusItemMap==null){
                        focus = true;
                    }else{
                        focus = focusItemMap.containsKey(itemDomain.getItemId());
                    }
                    PushItem pushItem;
                    if(!oldPushItemDomainMap.containsKey(itemDomain.getItemId())){
                        if(addPushItemDomainMap.containsKey(itemDomain.getItemId())){
                            pushItem = addPushItemDomainMap.get(itemDomain.getItemId());
                            pushItem.setRules(pushItem.getRules()+rule);
                        }else{
                            pushItem = new PushItem(userData.getUserId(),userData.getFounderId(),itemDomain,focus);
                            pushItem.setRules(rule);
                            pushItem.setPushTime(date);
                            pushItem.setFocus(focus);
                            pushItem.setCatNames(wishCategoryService.getCategoryNames(pushItem.getCatIds()));
                            addPushItemDomainMap.put(itemDomain.getItemId(),pushItem);
                            flushMerchantIdList.add(itemDomain.getMerchantId());
                        }
                        ruleTraceItemList.add(new PushItemRuleTrace(pushRule.getId(),date,itemDomain.getItemId(),userData.getUserId(),userData.getFounderId()));
                    }else{
                        pushItem = oldPushItemDomainMap.get(itemDomain.getItemId());
                        if(pushItem.getRules().indexOf(pushRule.getId())==-1){//被新规则推送
                            pushItem.setPushTime(date);
                            pushItem.setRules(pushItem.getRules()+rule);
                            pushItem.setFocus(focus);
                            ruleTraceItemList.add(new PushItemRuleTrace(pushRule.getId(),date,itemDomain.getItemId(),userData.getUserId(),userData.getFounderId()));
                            flushMerchantIdList.add(itemDomain.getMerchantId());
                            updatePushItemDomainMap.put(pushItem.getItemId(),pushItem);
                        }
                    }
                }
            }
        }
    }

    /**************************Service****************************************/
    WishCategoryService wishCategoryService = (WishCategoryService) SpringUtil.getBean("WishCategoryService");

    PushItemDAO pushItemDAO = (PushItemDAO) SpringUtil.getBean("pushItemDAO");

    WishItemSolrService wishItemSolrService = (WishItemSolrService) SpringUtil.getBean("wishItemSolrService");

    WishMerchantSolrService wishMerchantSolrService = (WishMerchantSolrService) SpringUtil.getBean("wishMerchantSolrService");

    PushItemMerchantMapService pushItemMerchantMapService = (PushItemMerchantMapService) SpringUtil.getBean("PushItemMerchantMapService");

    PushRuleService pushRuleService = (PushRuleService) SpringUtil.getBean("PushRuleService");

    FocusItemDAO focusItemDAO = (FocusItemDAO) SpringUtil.getBean("focusItemDAO");

    PushTotal1DAO pushTotal1DAO = (PushTotal1DAO) SpringUtil.getBean("pushTotal1DAO");

}
