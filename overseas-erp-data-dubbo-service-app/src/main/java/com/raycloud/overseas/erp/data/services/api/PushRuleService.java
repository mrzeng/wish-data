package com.raycloud.overseas.erp.data.services.api;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.common.pojo.PushRule;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushRuleQuery;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-04-08
 */
public interface PushRuleService{

    public void saveAndUseRule(PushRule pushRule,UserData userData) throws BizException;

    /**
     * 查询用户规则筛选范围
     * @param userData
     * @return
     */
    public Map<String,Object> queryRuleItemRange(UserData userData);

    /**
     * 查询使用中的规则列表
     * @return
     */
    public List<PushRule> queryUsingRuleList(UserData userData);

    /**
     * 基本插入
     * @return
     */
	public String addPushRule(PushRule pushRule, UserData userData);
	
    /**
     * 根据主键查询
     */
	public PushRule getPushRuleByKey(String id);
    /**
     * 根据主键批量查询
     */
    public List<PushRule> getPushRuleByKeys(List<String> idList);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updatePushRuleByKey(PushRule pushRule, UserData userData);

    /**
     * 更新推送规则状态
     * @param idList
     * @param status
     */
    public void updatePushRuleStatus(List<String> idList,Integer status,UserData userData) throws BizException;

    /**
     *
     * @param idList
     * @param userData
     */
    public void deletePushRule(List<String> idList,UserData userData);



    /**
     * 根据条件查询分页查询
     * @param pushRuleQuery 查询条件
     * @return
     */
    public Result<PushRule> getPushRuleListWithPage(PushRuleQuery pushRuleQuery, UserData userData);
    /**
     * 根据条件查询
     * @param pushRuleQuery 查询条件
     * @return
     */
    public List<PushRule> getPushRuleList(PushRuleQuery pushRuleQuery, UserData userData);

    /**
     * 更新规则启用状态
     * @param id
     * @param status
     * @param userData
     */
    public void updateRuleStatus(List<String> id,Integer status,UserData userData);

    /**
     * 校验推送结果
     * @param strategyType
     * @param strategyParam
     * @param userData
     * @return
     */
    public void checkPushResult(Integer strategyType,JSONObject strategyParam,UserData userData) throws BizException;

    /**
     * 解析推送规则
     * @param strategyType
     * @param strategyParam
     * @param userData
     * @return
     */
    public List<WishItemSolrQuery> parseRule(Integer strategyType, JSONObject strategyParam,UserData userData);

    /**
     * 初始化查询的id集合
     * @param strategyType
     * @param markId
     * @param userData
     * @return
     */
    public List<String> getIdList(Integer strategyType,Integer markId,UserData userData);

    /**
     * 执行查询wish宝贝列表
     * @param wishItemSolrQueryList
     * @return
     */
    public List<ItemDomain> execQuerys(List<WishItemSolrQuery> wishItemSolrQueryList,Integer type,UserData userData);

    public void addDefaultRules(UserData userData);

    /**
     * 获取推送规则
     * @param ruleId
     * @param userData
     * @return
     */
    public PushRule getRule(String ruleId,UserData userData);

    /**
     *  更新total7的值
     * @param pushRuleList
     * @param userData
     */
    public void updateTotal7(List<PushRule> pushRuleList,String date,UserData userData);
}
