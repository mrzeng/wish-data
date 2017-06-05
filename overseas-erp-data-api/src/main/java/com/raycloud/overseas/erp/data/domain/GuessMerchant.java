package com.raycloud.overseas.erp.data.domain;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class GuessMerchant  extends BasePojo {

    private Long fkId = 1L;

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -1954928894533273859L;

    public GuessMerchant() {
    }

    /**
     * 店铺id
     */
    private String merchantId;
	/**
     * 店铺图片url
     */
    private String merchantUrl;
	/**
     * 推荐指数
     */
    private Integer star;
	/**
     * 推荐理由
     */
    private String recommand;
	/**
     * merchant_name
     */
    private String merchantName;
	/**
     * item_count
     */
    private Integer itemCount;
	/**
     * amount_7
     */
    private Integer amount7;


   /**
    * @return merchantId 店铺id
    */
    public String getMerchantId() {
       return merchantId;
    }
   /**
    * @param merchantId 店铺id
    */
    public void setMerchantId(String merchantId) {
       this.merchantId = merchantId;
    }

   /**
    * @return merchantUrl 店铺图片url
    */
    public String getMerchantUrl() {
       return merchantUrl;
    }
   /**
    * @param merchantUrl 店铺图片url
    */
    public void setMerchantUrl(String merchantUrl) {
       this.merchantUrl = merchantUrl;
    }

   /**
    * @return star 推荐指数
    */
    public Integer getStar() {
       return star;
    }
   /**
    * @param star 推荐指数
    */
    public void setStar(Integer star) {
       this.star = star;
    }

   /**
    * @return recommand 推荐理由
    */
    public String getRecommand() {
       return recommand;
    }
   /**
    * @param recommand 推荐理由
    */
    public void setRecommand(String recommand) {
       this.recommand = recommand;
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

   /**
    * @return itemCount item_count
    */
    public Integer getItemCount() {
       return itemCount;
    }
   /**
    * @param itemCount item_count
    */
    public void setItemCount(Integer itemCount) {
       this.itemCount = itemCount;
    }

   /**
    * @return amount7 amount_7
    */
    public Integer getAmount7() {
       return amount7;
    }
   /**
    * @param amount7 amount_7
    */
    public void setAmount7(Integer amount7) {
       this.amount7 = amount7;
    }

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }
}