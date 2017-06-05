package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class FocusMerchant  extends BasePojo {


    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -6289791378743705807L;

    /**
     * user_id
     */
    private String userId;
	/**
     * merchant_id
     */
    private String merchantId;
	/**
     * id
     */
    private Integer id;
	/**
     * focus
     */
    private Integer focus;
	/**
     * updated
     */
    private Date updated;
	/**
     * created
     */
    private Date created;
	/**
     * 标签id(对应mark_info表)
     */
    private Integer markId;
	/**
     * 是否采集，0：未采集，1：采集
     */
    private Long collected;
	/**
     * merchant_name
     */
    private String merchantName;

    private Integer founderId;

    public Integer getFounderId() {
        return founderId;
    }

    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
    }

    /**
    * @return userId user_id
    */
    public String getUserId() {
       return userId;
    }
   /**
    * @param userId user_id
    */
    public void setUserId(String userId) {
       this.userId = userId;
    }

   /**
    * @return merchantId merchant_id
    */
    public String getMerchantId() {
       return merchantId;
    }
   /**
    * @param merchantId merchant_id
    */
    public void setMerchantId(String merchantId) {
       this.merchantId = merchantId;
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
    * @return focus focus
    */
    public Integer getFocus() {
       return focus;
    }
   /**
    * @param focus focus
    */
    public void setFocus(Integer focus) {
       this.focus = focus;
    }

   /**
    * @return updated updated
    */
    public Date getUpdated() {
       return updated;
    }
   /**
    * @param updated updated
    */
    public void setUpdated(Date updated) {
       this.updated = updated;
    }

   /**
    * @return created created
    */
    public Date getCreated() {
       return created;
    }
   /**
    * @param created created
    */
    public void setCreated(Date created) {
       this.created = created;
    }

   /**
    * @return markId 标签id(对应mark_info表)
    */
    public Integer getMarkId() {
       return markId;
    }
   /**
    * @param markId 标签id(对应mark_info表)
    */
    public void setMarkId(Integer markId) {
       this.markId = markId;
    }

   /**
    * @return collected 是否采集，0：未采集，1：采集
    */
    public Long getCollected() {
       return collected;
    }
   /**
    * @param collected 是否采集，0：未采集，1：采集
    */
    public void setCollected(Long collected) {
       this.collected = collected;
    }

   /**
    * @return merchantName merchant_name
    */
    public String getMerchantName() {
       return merchantName;
    }
   /**
    * @param merchantName merchant_name
    */
    public void setMerchantName(String merchantName) {
       this.merchantName = merchantName;
    }

}