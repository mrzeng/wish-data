package com.raycloud.overseas.erp.data.common.pojo;

import com.raycloud.overseas.erp.data.domain.ItemDomain;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-13
 */
public class PushItem  extends BasePojo implements Serializable{

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -9034836476299672297L;

    /**
     * 宝贝图片
     */
    private String itemLogoUrl;

	/**
     * id
     */
    private Integer id;
	/**
     * 宝贝id
     */
    private String itemId;
	/**
     * 宝贝昵称
     */
    private String itemName;
	/**
     * 用户id
     */
    private Integer userId;
    /**
     * 创始人id
     */
    private Integer founderId;
	/**
     * 是否关注
     */
    private Boolean localFocus;
	/**
     * 推送时间
     */
    private String pushTime;
	/**
     * 店铺id
     */
    private String merchantId;
//	/**
//     * 0:未删除，1已删除
//     */
//    private Boolean deleteFlag;
	/**
     * merchant_name
     */
    private String merchantName;
	/**
     * 行业
     */
    private String catNames;
	/**
     * 上架时间
     */
    private String genTime;
	/**
     * 销量增长率
     */
    private Double growth;
	/**
     * 产品评分
     */
    private Double rateScore;
	/**
     * 吊牌价格
     */
    private Double originalPrice;
	/**
     * 刊登价格
     */
    private Double sellerPrice;
	/**
     * 刊登运费
     */
    private Double sellerFeightPrice;
	/**
     * 刊登总价
     */
    private Double sellerTotalPrice;
	/**
     * wish售价
     */
    private Double wishPrice;
	/**
     * wish运费
     */
    private Double wishFeightPrice;
	/**
     * wish总价
     */
    private Double wishTotalPrice;
	/**
     * 7日日均销量
     */
    private Integer amount7;
	/**
     * 累计销量
     */
    private Integer amount;
	/**
     * 7日日均收藏量
     */
    private Integer newSave7;
	/**
     * 累计收藏量
     */
    private Integer newSave;
	/**
     * 7日日均评论
     */
    private Integer rateNum7;
	/**
     * 累计评论
     */
    private Integer rateNum;
	/**
     * 行业id
     */
    private String catIds;

    private Boolean focus;

    private Boolean collected;

    private String rules;

    public Boolean getFocus() {
        return focus;
    }

    public void setFocus(Boolean focus) {
        this.focus = focus;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public PushItem() {
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public PushItem(Integer userId, Integer founderId, ItemDomain itemDomain, Boolean localFocus) {
        this.itemId = itemDomain.getItemId();
        this.itemName = itemDomain.getItemName();
        this.userId = userId;
        this.founderId = founderId;
        this.localFocus = localFocus;
        this.merchantId = itemDomain.getMerchantId();
        this.merchantName = itemDomain.getMerchantName();
        this.genTime = itemDomain.getGenTime();
        this.growth = itemDomain.getGrowth();
        this.rateScore = itemDomain.getRateScore();
        this.originalPrice = itemDomain.getOriginalPrice();
        this.sellerPrice = itemDomain.getSellerPrice();
        this.sellerFeightPrice = itemDomain.getSellerFeightPrice();
        this.sellerTotalPrice = itemDomain.getSellerPrice()+itemDomain.getSellerFeightPrice();
        this.wishPrice = itemDomain.getWishPrice();
        this.wishFeightPrice = itemDomain.getWishFeightPrice();
        this.wishTotalPrice = itemDomain.getWishPrice()+itemDomain.getWishFeightPrice();
        this.amount7 = itemDomain.getAmount7();
        this.amount = itemDomain.getAmount();
        this.newSave7 = itemDomain.getNewSave7();
        this.newSave = itemDomain.getNewSave();
        this.rateNum7 = itemDomain.getRateNum7();
        this.rateNum = itemDomain.getRateNum();
        this.catIds = itemDomain.getCatIds();
    }

    public Integer getFounderId() {
        return founderId;
    }

    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
    }

    public String getItemLogoUrl() {
        return itemLogoUrl;
    }

    public void setItemLogoUrl(String itemLogoUrl) {
        this.itemLogoUrl = itemLogoUrl;
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
    * @return itemId 宝贝id
    */
    public String getItemId() {
       return itemId;
    }
   /**
    * @param itemId 宝贝id
    */
    public void setItemId(String itemId) {
       this.itemId = itemId;
    }

    public Boolean getLocalFocus() {
        return localFocus;
    }

    public void setLocalFocus(Boolean localFocus) {
        this.localFocus = localFocus;
    }

    /**
    * @return itemName 宝贝昵称
    */
    public String getItemName() {
       return itemName;
    }
   /**
    * @param itemName 宝贝昵称
    */
    public void setItemName(String itemName) {
       this.itemName = itemName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

//    public Boolean getLocalCollected() {
//        return localCollected;
//    }
//
//    public void setLocalCollected(Boolean localCollected) {
//        this.localCollected = localCollected;
//    }

//    public Boolean getLocalFocus() {
//        return localFocus;
//    }
//
//    public void setLocalFocus(Boolean localFocus) {
//        this.localFocus = localFocus;
//    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

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


//    public Boolean getDeleteFlag() {
//        return deleteFlag;
//    }
//
//    public void setDeleteFlag(Boolean deleteFlag) {
//        this.deleteFlag = deleteFlag;
//    }

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
    * @return catNames 行业
    */
    public String getCatNames() {
       return catNames;
    }
   /**
    * @param catNames 行业
    */
    public void setCatNames(String catNames) {
       this.catNames = catNames;
    }

   /**
    * @return genTime 上架时间
    */
    public String getGenTime() {
       return genTime;
    }
   /**
    * @param genTime 上架时间
    */
    public void setGenTime(String genTime) {
       this.genTime = genTime;
    }

   /**
    * @return rateScore 产品评分
    */
    public Double getRateScore() {
       return rateScore;
    }
   /**
    * @param rateScore 产品评分
    */
    public void setRateScore(Double rateScore) {
       this.rateScore = rateScore;
    }

   /**
    * @return originalPrice 吊牌价格
    */
    public Double getOriginalPrice() {
       return originalPrice;
    }
   /**
    * @param originalPrice 吊牌价格
    */
    public void setOriginalPrice(Double originalPrice) {
       this.originalPrice = originalPrice;
    }

   /**
    * @return sellerPrice 刊登价格
    */
    public Double getSellerPrice() {
       return sellerPrice;
    }
   /**
    * @param sellerPrice 刊登价格
    */
    public void setSellerPrice(Double sellerPrice) {
       this.sellerPrice = sellerPrice;
    }

   /**
    * @return sellerTotalPrice 刊登总价
    */
    public Double getSellerTotalPrice() {
       return sellerTotalPrice;
    }
   /**
    * @param sellerTotalPrice 刊登总价
    */
    public void setSellerTotalPrice(Double sellerTotalPrice) {
       this.sellerTotalPrice = sellerTotalPrice;
    }

   /**
    * @return wishPrice wish售价
    */
    public Double getWishPrice() {
       return wishPrice;
    }
   /**
    * @param wishPrice wish售价
    */
    public void setWishPrice(Double wishPrice) {
       this.wishPrice = wishPrice;
    }

    public Double getSellerFeightPrice() {
        return sellerFeightPrice;
    }

    public void setSellerFeightPrice(Double sellerFeightPrice) {
        this.sellerFeightPrice = sellerFeightPrice;
    }

    public Double getWishFeightPrice() {
        return wishFeightPrice;
    }

    public void setWishFeightPrice(Double wishFeightPrice) {
        this.wishFeightPrice = wishFeightPrice;
    }

    /**
    * @return wishTotalPrice wish总价
    */
    public Double getWishTotalPrice() {
       return wishTotalPrice;
    }
   /**
    * @param wishTotalPrice wish总价
    */
    public void setWishTotalPrice(Double wishTotalPrice) {
       this.wishTotalPrice = wishTotalPrice;
    }

   /**
    * @return amount7 7日日均销量
    */
    public Integer getAmount7() {
       return amount7;
    }
   /**
    * @param amount7 7日日均销量
    */
    public void setAmount7(Integer amount7) {
       this.amount7 = amount7;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNewSave7() {
        return newSave7;
    }

    public void setNewSave7(Integer newSave7) {
        this.newSave7 = newSave7;
    }

    public Integer getNewSave() {
        return newSave;
    }

    public void setNewSave(Integer newSave) {
        this.newSave = newSave;
    }

    /**
    * @return rateNum7 7日日均评论
    */
    public Integer getRateNum7() {
       return rateNum7;
    }
   /**
    * @param rateNum7 7日日均评论
    */
    public void setRateNum7(Integer rateNum7) {
       this.rateNum7 = rateNum7;
    }

   /**
    * @return rateNum 累计评论
    */
    public Integer getRateNum() {
       return rateNum;
    }
   /**
    * @param rateNum 累计评论
    */
    public void setRateNum(Integer rateNum) {
       this.rateNum = rateNum;
    }

    public Double getGrowth() {
        return growth;
    }

    public void setGrowth(Double growth) {
        this.growth = growth;
    }

    /**
    * @return catIds 行业id
    */
    public String getCatIds() {
       return catIds;
    }
   /**
    * @param catIds 行业id
    */
    public void setCatIds(String catIds) {
       this.catIds = catIds;
    }

//    public boolean localCollectedChange(Boolean localCollected){
//        if(this.localCollected==null || this.localCollected.booleanValue()!=localCollected.booleanValue()){
//            return true;
//        }
//        return false;
//    }

    public boolean localFocusChange(Boolean localFocus){
        if(this.localFocus==null || this.localFocus.booleanValue()!=localFocus.booleanValue()){
            return true;
        }
        return false;
    }
}