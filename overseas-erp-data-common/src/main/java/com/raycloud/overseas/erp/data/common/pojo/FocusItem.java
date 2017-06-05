package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class FocusItem  extends BasePojo {


    private static final long serialVersionUID = -5928669689530709948L;

	/**
     * user_id
     */
    private String userId;
	/**
     * item_id
     */
    private String itemId;
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
    private Integer collected;
	/**
     * item_name
     */
    private String itemName;
	/**
     * 过期时间
     */
    private Date expireTime;

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
    * @return itemId item_id
    */
    public String getItemId() {
       return itemId;
    }
   /**
    * @param itemId item_id
    */
    public void setItemId(String itemId) {
       this.itemId = itemId;
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
    public Integer getCollected() {
       return collected;
    }
   /**
    * @param collected 是否采集，0：未采集，1：采集
    */
    public void setCollected(Integer collected) {
       this.collected = collected;
    }

   /**
    * @return itemName item_name
    */
    public String getItemName() {
       return itemName;
    }
   /**
    * @param itemName item_name
    */
    public void setItemName(String itemName) {
       this.itemName = itemName;
    }

   /**
    * @return expireTime 过期时间
    */
    public Date getExpireTime() {
       return expireTime;
    }
   /**
    * @param expireTime 过期时间
    */
    public void setExpireTime(Date expireTime) {
       this.expireTime = expireTime;
    }

}