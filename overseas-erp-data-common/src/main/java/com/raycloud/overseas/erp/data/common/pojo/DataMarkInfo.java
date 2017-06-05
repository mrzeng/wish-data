package com.raycloud.overseas.erp.data.common.pojo;


/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class DataMarkInfo  extends BasePojo {

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -7118949957196749895L;

	/**
     * id
     */
    private Integer id;
	/**
     * 标签名称
     */
    private String markName;
	/**
     * 标签颜色
     */
    private String color;
	/**
     * mark_desc
     */
    private String markDesc;
	/**
     * user_id
     */
    private Integer userId;
	/**
     * type
     */
    private String type;
	/**
     * trade_uuid
     */
    private String tradeUuid;
	/**
     * founder_id
     */
    private Integer founderId;


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
    * @return markName 标签名称
    */
    public String getMarkName() {
       return markName;
    }
   /**
    * @param markName 标签名称
    */
    public void setMarkName(String markName) {
       this.markName = markName;
    }

   /**
    * @return color 标签颜色
    */
    public String getColor() {
       return color;
    }
   /**
    * @param color 标签颜色
    */
    public void setColor(String color) {
       this.color = color;
    }

   /**
    * @return markDesc mark_desc
    */
    public String getMarkDesc() {
       return markDesc;
    }
   /**
    * @param markDesc mark_desc
    */
    public void setMarkDesc(String markDesc) {
       this.markDesc = markDesc;
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
    * @return type type
    */
    public String getType() {
       return type;
    }
   /**
    * @param type type
    */
    public void setType(String type) {
       this.type = type;
    }

   /**
    * @return tradeUuid trade_uuid
    */
    public String getTradeUuid() {
       return tradeUuid;
    }
   /**
    * @param tradeUuid trade_uuid
    */
    public void setTradeUuid(String tradeUuid) {
       this.tradeUuid = tradeUuid;
    }

   /**
    * @return founderId founder_id
    */
    public Integer getFounderId() {
       return founderId;
    }
   /**
    * @param founderId founder_id
    */
    public void setFounderId(Integer founderId) {
       this.founderId = founderId;
    }

}