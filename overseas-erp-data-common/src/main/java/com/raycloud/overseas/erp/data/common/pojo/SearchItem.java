package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-01
 */
public class SearchItem  extends BasePojo {
    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -8666830122652021049L;

    private Long fkId = 4L;

    /**
     * id
     */
    private Integer id;
	/**
     * 产品id
     */
    private String itemId;
	/**
     * 店铺名称
     */
    private String merchantName;
	/**
     * 创建时间
     */
    private Date created;
	/**
     * 更新时间
     */
    private Date updated;
	/**
     * 0等待收录,1:收录成功,2:wish中暂未收到该产品
     */
    private Integer status;
	/**
     * 用户id
     */
    private String userId;
	/**
     * 1:收录店铺，2收录产品
     */
    private Integer type;


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
    * @return itemId 产品id
    */
    public String getItemId() {
       return itemId;
    }
   /**
    * @param itemId 产品id
    */
    public void setItemId(String itemId) {
       this.itemId = itemId;
    }

   /**
    * @return merchantName 店铺名称
    */
    public String getMerchantName() {
       return merchantName;
    }
   /**
    * @param merchantName 店铺名称
    */
    public void setMerchantName(String merchantName) {
       this.merchantName = merchantName;
    }

   /**
    * @return created 创建时间
    */
    public Date getCreated() {
       return created;
    }
   /**
    * @param created 创建时间
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

   /**
    * @return status 0等待收录,1:收录成功,2:wish中暂未收到该产品
    */
    public Integer getStatus() {
       return status;
    }
   /**
    * @param status 0等待收录,1:收录成功,2:wish中暂未收到该产品
    */
    public void setStatus(Integer status) {
       this.status = status;
    }

   /**
    * @return userId 用户id
    */
    public String getUserId() {
       return userId;
    }
   /**
    * @param userId 用户id
    */
    public void setUserId(String userId) {
       this.userId = userId;
    }

   /**
    * @return type 1:收录店铺，2收录产品
    */
    public Integer getType() {
       return type;
    }
   /**
    * @param type 1:收录店铺，2收录产品
    */
    public void setType(Integer type) {
       this.type = type;
    }

    @Override
    public Long getFkId() {
        return fkId;
    }

    @Override
    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }
}