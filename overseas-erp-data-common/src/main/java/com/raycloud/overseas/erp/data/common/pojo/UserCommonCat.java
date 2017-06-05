package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class UserCommonCat  extends BasePojo {


    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -3562592164819241947L;

    /**
     * id
     */
    private String id;
	/**
     * 行业id
     */
    private String catId;
	/**
     * 行业名称
     */
    private String catName;
	/**
     * 用户id
     */
    private Integer userId;
	/**
     * 平台类型
     */
    private Integer type;
	/**
     * created
     */
    private Date created;
	/**
     * cat_index
     */
    private Integer catIndex;


   /**
    * @return id id
    */
    public String getId() {
       return id;
    }
   /**
    * @param id id
    */
    public void setId(String id) {
       this.id = id;
    }

   /**
    * @return catId 行业id
    */
    public String getCatId() {
       return catId;
    }
   /**
    * @param catId 行业id
    */
    public void setCatId(String catId) {
       this.catId = catId;
    }

   /**
    * @return catName 行业名称
    */
    public String getCatName() {
       return catName;
    }
   /**
    * @param catName 行业名称
    */
    public void setCatName(String catName) {
       this.catName = catName;
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
    * @return type 平台类型
    */
    public Integer getType() {
       return type;
    }
   /**
    * @param type 平台类型
    */
    public void setType(Integer type) {
       this.type = type;
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
    * @return catIndex cat_index
    */
    public Integer getCatIndex() {
       return catIndex;
    }
   /**
    * @param catIndex cat_index
    */
    public void setCatIndex(Integer catIndex) {
       this.catIndex = catIndex;
    }

}