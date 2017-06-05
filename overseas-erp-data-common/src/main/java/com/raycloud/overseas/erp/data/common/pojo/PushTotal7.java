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
public class PushTotal7  extends BasePojo {

    public PushTotal7() {
    }

    public PushTotal7(Integer type, Integer pushTotal, String pushResultDetail, Integer uncollectTotal, Integer founderId,Integer userId) {
        this.type = type;
        this.pushTotal = pushTotal;
        this.pushResultDetail = pushResultDetail;
        this.uncollectTotal = uncollectTotal;
        this.founderId = founderId;
        this.userId = userId;
    }

    /**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * id
     */
    private Integer id;
	/**
     * 1:今日概况，2：昨日概况，3近3日概况，4 近7日概况
     */
    private Integer type;
	/**
     * 推送总数
     */
    private Integer pushTotal;
	/**
     * 推送规则结果明细
     */
    private String pushResultDetail;

    private JSONArray pushResultDetailObj;
	/**
     * 待采集产品数量
     */
    private Integer uncollectTotal;
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

    public JSONArray getPushResultDetailObj() {
        return pushResultDetailObj;
    }

    public void setPushResultDetailObj(JSONArray pushResultDetailObj) {
        this.pushResultDetailObj = pushResultDetailObj;
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

   /**
    * @return type 1:今日概况，2：昨日概况，3近3日概况，4 近7日概况
    */
    public Integer getType() {
       return type;
    }
   /**
    * @param type 1:今日概况，2：昨日概况，3近3日概况，4 近7日概况
    */
    public void setType(Integer type) {
       this.type = type;
    }

   /**
    * @return pushTotal 推送总数
    */
    public Integer getPushTotal() {
       return pushTotal;
    }
   /**
    * @param pushTotal 推送总数
    */
    public void setPushTotal(Integer pushTotal) {
       this.pushTotal = pushTotal;
    }

   /**
    * @return pushResultDetail 推送规则结果明细
    */
    public String getPushResultDetail() {
       return pushResultDetail;
    }
   /**
    * @param pushResultDetail 推送规则结果明细
    */
    public void setPushResultDetail(String pushResultDetail) {
       this.pushResultDetail = pushResultDetail;
    }

   /**
    * @return uncollectTotal 待采集产品数量
    */
    public Integer getUncollectTotal() {
       return uncollectTotal;
    }
   /**
    * @param uncollectTotal 待采集产品数量
    */
    public void setUncollectTotal(Integer uncollectTotal) {
       this.uncollectTotal = uncollectTotal;
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