package com.raycloud.overseas.erp.data.domain;

import java.math.BigDecimal;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class GuessItem  extends BasePojo {

    private Long fkId = 1L;

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = -1225452860951918619L;

    public GuessItem() {
    }

    /**
     * 产品id
     */
    private String itemId;
	/**
     * 产品图片url
     */
    private String itemUrl;
	/**
     * 产品推荐指数
     */
    private Integer star;
	/**
     * 推荐理由
     */
    private String recommand;
	/**
     * cat_names
     */
    private String catNames;
	/**
     * amount_7
     */
    private Integer amount7;
	/**
     * price
     */
    private Double price;
	/**
     * wish_feight_price
     */
    private Double wishFeightPrice;


   /**
    * @return itemId 产品id
    */
    public String getItemId() {
       return itemId;
    }
   /**
    * @param itemId 产品id
    */
    public void setItemId(String itemId) {
       this.itemId = itemId;
    }

   /**
    * @return itemUrl 产品图片url
    */
    public String getItemUrl() {
       return itemUrl;
    }
   /**
    * @param itemUrl 产品图片url
    */
    public void setItemUrl(String itemUrl) {
       this.itemUrl = itemUrl;
    }

   /**
    * @return star 产品推荐指数
    */
    public Integer getStar() {
       return star;
    }
   /**
    * @param star 产品推荐指数
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
    * @return catNames cat_names
    */
    public String getCatNames() {
       return catNames;
    }
   /**
    * @param catNames cat_names
    */
    public void setCatNames(String catNames) {
       this.catNames = catNames;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWishFeightPrice() {
        return wishFeightPrice;
    }

    public void setWishFeightPrice(Double wishFeightPrice) {
        this.wishFeightPrice = wishFeightPrice;
    }

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }
}