package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-02
 */
public class ColumnCondition  extends BasePojo {



    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = 5622996369474335381L;

	/**
     * id
     */
    private Integer id;
	/**
     * 用户id
     */
    private String userId;
	/**
     * 团队创始人id
     */
    private String founderId;
	/**
     * 模块名
     */
    private String status;
	/**
     * 自定义列
     */
    private String param;
	/**
     * 创建时间
     */
    private Date createTime;


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
    * @return founderId 团队创始人id
    */
    public String getFounderId() {
       return founderId;
    }
   /**
    * @param founderId 团队创始人id
    */
    public void setFounderId(String founderId) {
       this.founderId = founderId;
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
    * @return param 自定义列
    */
    public String getParam() {
       return param;
    }
   /**
    * @param param 自定义列
    */
    public void setParam(String param) {
       this.param = param;
    }

   /**
    * @return createTime 创建时间
    */
    public Date getCreateTime() {
       return createTime;
    }
   /**
    * @param createTime 创建时间
    */
    public void setCreateTime(Date createTime) {
       this.createTime = createTime;
    }

}