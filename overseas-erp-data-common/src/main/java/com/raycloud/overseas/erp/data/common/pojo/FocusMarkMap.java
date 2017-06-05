package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-01
 */
public class FocusMarkMap  extends BasePojo {


    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -6238732302837470505L;
    /**
     * id
     */
    private Integer id;
	/**
     * 宝贝id或店铺id
     */
    private String focusId;
	/**
     * user_id
     */
    private Integer userId;
	/**
     * mark_id
     */
    private Integer markId;
	/**
     * 0：关注店铺，1关注产品
     */
    private Integer focusType;

    private Integer founderId;

    public Integer getFounderId() {
        return founderId;
    }

    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
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
    * @return focusId 宝贝id或店铺id
    */
    public String getFocusId() {
       return focusId;
    }
   /**
    * @param focusId 宝贝id或店铺id
    */
    public void setFocusId(String focusId) {
       this.focusId = focusId;
    }

   /**
    * @return userId user_id
    */
    public Integer getUserId() {
       return userId;
    }
   /**
    * @param userId user_id
    */
    public void setUserId(Integer userId) {
       this.userId = userId;
    }

   /**
    * @return markId mark_id
    */
    public Integer getMarkId() {
       return markId;
    }
   /**
    * @param markId mark_id
    */
    public void setMarkId(Integer markId) {
       this.markId = markId;
    }

   /**
    * @return focusType 0：关注店铺，1关注产品
    */
    public Integer getFocusType() {
       return focusType;
    }
   /**
    * @param focusType 0：关注店铺，1关注产品
    */
    public void setFocusType(Integer focusType) {
       this.focusType = focusType;
    }

}