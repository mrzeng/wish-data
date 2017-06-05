package com.raycloud.overseas.erp.data.common.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-08
 */
public class PushRule  extends BasePojo {


    /**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 推送规则id
     */
    private String id;

	/**
     * rule_name
     */
    private String ruleName;
	/**
     * rule_desc
     */
    private String ruleDesc;
	/**
     * 7
     */
    private Integer pushTotal7;
	/**
     * 添加时间
     */
    private Date created;
	/**
     * 更新时间
     */
    private Date updated;
	/**
     * 0 禁用，1启用
     */
    private Integer ruleStatus;
	/**
     * rule_delete
     */
    private Boolean ruleDelete;
	/**
     * 推送策略（1关注店铺-全部,2关注店铺-标签，3关注产品-全部，4关注店铺标签）
     */
    private Integer strategyType;
	/**
     * 策略参数
     */
    private String strategyParam;

    private JSONObject strategyParamObj;

	/**
     * 用户id
     */
    private Integer userId;
	/**
     * 创始人id
     */
    private Integer founderId;

    private Integer running;

    public Integer getRunning() {
        return running;
    }

    public void setRunning(Integer running) {
        this.running = running;
    }

    public Boolean getRuleDelete() {
        return ruleDelete;
    }

    public void setRuleDelete(Boolean ruleDelete) {
        this.ruleDelete = ruleDelete;
    }

    public JSONObject getStrategyParamObj() {
        return strategyParamObj;
    }

    public void setStrategyParamObj(JSONObject strategyParamObj) {
        this.strategyParamObj = strategyParamObj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
    * @return ruleName rule_name
    */
    public String getRuleName() {
       return ruleName;
    }
   /**
    * @param ruleName rule_name
    */
    public void setRuleName(String ruleName) {
       this.ruleName = ruleName;
    }

   /**
    * @return ruleDesc rule_desc
    */
    public String getRuleDesc() {
       return ruleDesc;
    }
   /**
    * @param ruleDesc rule_desc
    */
    public void setRuleDesc(String ruleDesc) {
       this.ruleDesc = ruleDesc;
    }

   /**
    * @return pushTotal7 7
    */
    public Integer getPushTotal7() {
       return pushTotal7;
    }
   /**
    * @param pushTotal7 7
    */
    public void setPushTotal7(Integer pushTotal7) {
       this.pushTotal7 = pushTotal7;
    }

   /**
    * @return created 添加时间
    */
    public Date getCreated() {
       return created;
    }
   /**
    * @param created 添加时间
    */
    public void setCreated(Date created) {
       this.created = created;
    }

   /**
    * @return updated 更新时间
    */
    public Date getUpdated() {
       return updated;
    }
   /**
    * @param updated 更新时间
    */
    public void setUpdated(Date updated) {
       this.updated = updated;
    }

    public Integer getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    /**
    * @return strategyType 推送策略（1关注店铺-全部,2关注店铺-标签，3关注产品-全部，4关注店铺标签）
    */
    public Integer getStrategyType() {
       return strategyType;
    }
   /**
    * @param strategyType 推送策略（1关注店铺-全部,2关注店铺-标签，3关注产品-全部，4关注店铺标签）
    */
    public void setStrategyType(Integer strategyType) {
       this.strategyType = strategyType;
    }

   /**
    * @return strategyParam 策略参数
    */
    public String getStrategyParam() {
       return strategyParam;
    }
   /**
    * @param strategyParam 策略参数
    */
    public void setStrategyParam(String strategyParam) {
       this.strategyParam = strategyParam;
    }

   /**
    * @return userId 用户id
    */
    public Integer getUserId() {
       return userId;
    }
   /**
    * @param userId 用户id
    */
    public void setUserId(Integer userId) {
       this.userId = userId;
    }

   /**
    * @return founderId 创始人id
    */
    public Integer getFounderId() {
       return founderId;
    }
   /**
    * @param founderId 创始人id
    */
    public void setFounderId(Integer founderId) {
       this.founderId = founderId;
    }

    public  String rule(String date){
        StringBuffer sb = new StringBuffer();
//        sb.append(userId);
//        sb.append(RULE_SPLIT);
//        sb.append(founderId);
//        sb.append(RULE_SPLIT);
        sb.append("&");
        sb.append(id);
        sb.append("*");
        sb.append(date);
        sb.append("*");
        sb.append(ruleName);
        sb.append(";");
        return sb.toString();
    }
}