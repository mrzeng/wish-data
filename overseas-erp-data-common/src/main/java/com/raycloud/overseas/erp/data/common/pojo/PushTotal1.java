package com.raycloud.overseas.erp.data.common.pojo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-08
 */
public class PushTotal1  extends BasePojo {


    /**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * id
     */
    private Integer id;
	/**
     * 推送时间
     */
    private String pushTime;
	/**
     * 推送总数
     */
    private Integer pushTotal1;
	/**
     * 推送明细
     */
    private String ruleResultDetail;

    private JSONArray ruleResultDetailObj;



	/**
     * 推送7日宝贝总数
     */
    private Integer pushTotal7;
	/**
     * 创始人id
     */
    private Integer founderId;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public JSONArray getRuleResultDetailObj() {
        return ruleResultDetailObj;
    }

    public void setRuleResultDetailObj(JSONArray ruleResultDetailObj) {
        this.ruleResultDetailObj = ruleResultDetailObj;
    }

    /**
    * @return id id
    */
    public Integer getId() {
       return id;
    }
   /**
    * @param id id
    */
    public void setId(Integer id) {
       this.id = id;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    /**
    * @return pushTotal1 推送总数
    */
    public Integer getPushTotal1() {
       return pushTotal1;
    }
   /**
    * @param pushTotal1 推送总数
    */
    public void setPushTotal1(Integer pushTotal1) {
       this.pushTotal1 = pushTotal1;
    }

   /**
    * @return ruleResultDetail 推送明细
    */
    public String getRuleResultDetail() {
       return ruleResultDetail;
    }
   /**
    * @param ruleResultDetail 推送明细
    */
    public void setRuleResultDetail(String ruleResultDetail) {
       this.ruleResultDetail = ruleResultDetail;
    }

   /**
    * @return pushTotal7 推送7日宝贝总数
    */
    public Integer getPushTotal7() {
       return pushTotal7;
    }
   /**
    * @param pushTotal7 推送7日宝贝总数(现为待处理宝贝个数)
    */
    public void setPushTotal7(Integer pushTotal7) {
       this.pushTotal7 = pushTotal7;
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

}