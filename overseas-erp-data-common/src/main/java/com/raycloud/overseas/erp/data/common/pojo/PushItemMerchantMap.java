package com.raycloud.overseas.erp.data.common.pojo;

import com.raycloud.overseas.erp.data.domain.BasePojo;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-13
 */
public class PushItemMerchantMap  extends BasePojo {



    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -5602189316087013974L;

    private String fkId = "1";

	/**
     * id
     */
    private Integer id;
	/**
     * user_id
     */
    private Long userId;
	/**
     * merchant_id
     */
    private String merchantId;
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

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId;
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
    * @return userId user_id
    */
    public Long getUserId() {
       return userId;
    }
   /**
    * @param userId user_id
    */
    public void setUserId(Long userId) {
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