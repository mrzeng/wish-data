package com.raycloud.overseas.erp.data.search.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;


public class SolrWishItem implements Serializable {

    private static final long serialVersionUID = 3919390036669409832L;

    @Field
    private String itemid;//宝贝id

    @Field
    private String itemname;//产品标题

    @Field
    private String merchant_id;//店铺id

    @Field
    private Date insert_date;

    @Field
    private double rate_score;//产品评分

    @Field
    private String cat_ids;//类目id

    @Field
    private Double seller_price;//刊登价格

    @Field
    private Double wish_price;//wish售价

    @Field
    private Boolean already_recommended_flag;//是否wish认证

    @Field
    private Integer amount_7;//7日销量

    @Field
    private Integer wish_save_7;//7日收藏量

    @Field
    private String main_image;//主图

    private String hilghtTitle;

    @Field
    private Date gen_time;//上架时间

    @Field
    private Integer rate_num;//评论数量

    @Field
    private Double seller_freight_price;//卖家运费

    @Field
    private Double wish_freight_price;//wish运费

    @Field
    private Double original_price;//吊牌价

    @Field
    private Integer offer;//累计销量

    @Field
    private Integer rate_num_7;//7日评论

    @Field
    private Integer wish_num;//累计收藏

    @Field
    private Boolean removed_flag;//是否下架/售罄

    @Field
    private Double amount_rate;//销量增长率

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }

    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public Integer getRate_num_7() {
        return rate_num_7;
    }

    public void setRate_num_7(Integer rate_num_7) {
        this.rate_num_7 = rate_num_7;
    }

    public Integer getWish_num() {
        return wish_num;
    }

    public void setWish_num(Integer wish_num) {
        this.wish_num = wish_num;
    }

    public Boolean getRemoved_flag() {
        return removed_flag;
    }

    public void setRemoved_flag(Boolean removed_flag) {
        this.removed_flag = removed_flag;
    }

    public Integer getRate_num() {
        return rate_num;
    }

    public void setRate_num(Integer rate_num) {
        this.rate_num = rate_num;
    }

    public Double getAmount_rate() {
        return amount_rate;
    }

    public void setAmount_rate(Double amount_rate) {
        this.amount_rate = amount_rate;
    }

    public Date getGen_time() {
        return gen_time;
    }

    public void setGen_time(Date gen_time) {
        this.gen_time = gen_time;
    }

    public String getHilghtTitle() {
        return hilghtTitle;
    }

    public void setHilghtTitle(String hilghtTitle) {
        this.hilghtTitle = hilghtTitle;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Date getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(Date insert_date) {
        this.insert_date = insert_date;
    }

    public double getRate_score() {
        return rate_score;
    }

    public void setRate_score(double rate_score) {
        this.rate_score = rate_score;
    }

    public String getCat_ids() {
        return cat_ids;
    }

    public void setCat_ids(String cat_ids) {
        this.cat_ids = cat_ids;
    }

    public Double getSeller_price() {
        return seller_price;
    }

    public void setSeller_price(Double seller_price) {
        this.seller_price = seller_price;
    }

    public Double getWish_price() {
        return wish_price;
    }

    public void setWish_price(Double wish_price) {
        this.wish_price = wish_price;
    }

    public Boolean getAlready_recommended_flag() {
        return already_recommended_flag;
    }

    public void setAlready_recommended_flag(Boolean already_recommended_flag) {
        this.already_recommended_flag = already_recommended_flag;
    }

    public Integer getAmount_7() {
        return amount_7;
    }

    public void setAmount_7(Integer amount_7) {
        this.amount_7 = amount_7;
    }

    public Integer getWish_save_7() {
        return wish_save_7;
    }

    public void setWish_save_7(Integer wish_save_7) {
        this.wish_save_7 = wish_save_7;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public Double getSeller_freight_price() {
        return seller_freight_price;
    }

    public void setSeller_freight_price(Double seller_freight_price) {
        this.seller_freight_price = seller_freight_price;
    }

    public Double getWish_freight_price() {
        return wish_freight_price;
    }

    public void setWish_freight_price(Double wish_freight_price) {
        this.wish_freight_price = wish_freight_price;
    }
}
