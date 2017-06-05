package com.raycloud.overseas.erp.data.common.pojo;


import java.util.Date;

public class ComplementItem extends BasePojo {

    private Long fkId = 4L;

    public ComplementItem() {
    }

    public ComplementItem(String itemid, Date createAt, Integer status, String userId) {
        this.itemid = itemid;
        this.createAt = createAt;
        this.status = status;
        this.userId = userId;
    }

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = 5056951909903221977L;

	/**
     * itemid
     */
    private String itemid;
	/**
     * 插入时间
     */
    private Date createAt;
	/**
     * 0:采集中，1：采集成功，2：采集失败
     */
    private Integer status;
	/**
     * user_id
     */
    private String userId;


   /**
    * @return itemid itemid
    */
    public String getItemid() {
       return itemid;
    }
   /**
    * @param itemid itemid
    */
    public void setItemid(String itemid) {
       this.itemid = itemid;
    }

   /**
    * @return createAt 插入时间
    */
    public Date getCreateAt() {
       return createAt;
    }
   /**
    * @param createAt 插入时间
    */
    public void setCreateAt(Date createAt) {
       this.createAt = createAt;
    }

   /**
    * @return status 0:采集中，1：采集成功，2：采集失败
    */
    public Integer getStatus() {
       return status;
    }
   /**
    * @param status 0:采集中，1：采集成功，2：采集失败
    */
    public void setStatus(Integer status) {
       this.status = status;
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

    @Override
    public Long getFkId() {
        return fkId;
    }

    @Override
    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }
}