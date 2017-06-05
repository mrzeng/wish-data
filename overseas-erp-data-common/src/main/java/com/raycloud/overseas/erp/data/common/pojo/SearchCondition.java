package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-02
 */
public class SearchCondition  extends BasePojo {

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -3529706863428481161L;

	/**
     * id
     */
    private Integer id;
	/**
     * 搜索名称
     */
    private String name;
	/**
     * 搜索描述
     */
    private String description;
	/**
     * 搜索内容
     */
    private String filter;
	/**
     * 用户id
     */
    private Integer userId;
	/**
     * 团队创始人id
     */
    private Integer teamFounderId;
	/**
     * 模块名
     */
    private String status;
	/**
     * 创建时间
     */
    private Date createdAt;
	/**
     * 更新时间
     */
    private Date updatedAt;


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
    * @return name 搜索名称
    */
    public String getName() {
       return name;
    }
   /**
    * @param name 搜索名称
    */
    public void setName(String name) {
       this.name = name;
    }

   /**
    * @return description 搜索描述
    */
    public String getDescription() {
       return description;
    }
   /**
    * @param description 搜索描述
    */
    public void setDescription(String description) {
       this.description = description;
    }

   /**
    * @return filter 搜索内容
    */
    public String getFilter() {
       return filter;
    }
   /**
    * @param filter 搜索内容
    */
    public void setFilter(String filter) {
       this.filter = filter;
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
    * @return teamFounderId 团队创始人id
    */
    public Integer getTeamFounderId() {
       return teamFounderId;
    }
   /**
    * @param teamFounderId 团队创始人id
    */
    public void setTeamFounderId(Integer teamFounderId) {
       this.teamFounderId = teamFounderId;
    }

   /**
    * @return status 模块名
    */
    public String getStatus() {
       return status;
    }
   /**
    * @param status 模块名
    */
    public void setStatus(String status) {
       this.status = status;
    }

   /**
    * @return createdAt 创建时间
    */
    public Date getCreatedAt() {
       return createdAt;
    }
   /**
    * @param createdAt 创建时间
    */
    public void setCreatedAt(Date createdAt) {
       this.createdAt = createdAt;
    }

   /**
    * @return updatedAt 更新时间
    */
    public Date getUpdatedAt() {
       return updatedAt;
    }
   /**
    * @param updatedAt 更新时间
    */
    public void setUpdatedAt(Date updatedAt) {
       this.updatedAt = updatedAt;
    }

}