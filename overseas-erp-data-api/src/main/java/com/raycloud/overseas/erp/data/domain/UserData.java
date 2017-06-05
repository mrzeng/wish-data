package com.raycloud.overseas.erp.data.domain;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-20
 */
public class UserData  extends BasePojo {

    private Long fkId = 1L;



    /**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * user_id
     */
    private Integer userId;
	/**
     * id
     */
    private Integer id;
	/**
     * uname
     */
    private String uname;
	/**
     * mobile
     */
    private String mobile;
	/**
     * email
     */
    private String email;
	/**
     * db_table_config
     */
    private String dbTableConfig;
	/**
     * created
     */
    private Date created;
	/**
     * update_time
     */
    private Date updateTime;

    private String guideInfo;

    private Integer founderId;

    private String sysConfig;

    public Integer getFounderId() {
        return founderId;
    }

    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
    }

    public String getSysConfig() {
        return sysConfig;
    }

    public void setSysConfig(String sysConfig) {
        this.sysConfig = sysConfig;
    }

    public String getGuideInfo() {
        return guideInfo;
    }

    public void setGuideInfo(String guideInfo) {
        this.guideInfo = guideInfo;
    }

    public UserData() {
    }

    public UserData(String uname, String mobile, String email, String dbTableConfig, Integer userId) {
        this.uname = uname;
        this.mobile = mobile;
        this.email = email;
        this.dbTableConfig = dbTableConfig;
        this.userId = userId;
    }

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
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
    * @return uname uname
    */
    public String getUname() {
       return uname;
    }
   /**
    * @param uname uname
    */
    public void setUname(String uname) {
       this.uname = uname;
    }

   /**
    * @return mobile mobile
    */
    public String getMobile() {
       return mobile;
    }
   /**
    * @param mobile mobile
    */
    public void setMobile(String mobile) {
       this.mobile = mobile;
    }

   /**
    * @return email email
    */
    public String getEmail() {
       return email;
    }
   /**
    * @param email email
    */
    public void setEmail(String email) {
       this.email = email;
    }

   /**
    * @return dbTableConfig db_table_config
    */
    public String getDbTableConfig() {
       return dbTableConfig;
    }
   /**
    * @param dbTableConfig db_table_config
    */
    public void setDbTableConfig(String dbTableConfig) {
       this.dbTableConfig = dbTableConfig;
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
    * @return updateTime update_time
    */
    public Date getUpdateTime() {
       return updateTime;
    }
   /**
    * @param updateTime update_time
    */
    public void setUpdateTime(Date updateTime) {
       this.updateTime = updateTime;
    }

    public Long getLongUserId(){
        return Long.parseLong(userId+"");
    }

    public Long getLongFounderId(){
        return Long.parseLong(founderId+"");
    }
}