package com.raycloud.overseas.erp.data.search.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class SolrWishMerchant implements Serializable {

    private static final long serialVersionUID = 6241943453692401805L;

    @Field
    private String merchant_id;//店铺id

    @Field
    private String merchant_nick;//店铺别名

    @Field
    private Integer item_count;//宝贝数量

    @Field
    private Integer amount;//7日销量

    @Field
    private Integer new_item_count;//7日上新宝贝

    @Field
    private String cat_ids;//类目列表

    @Field
    private Double price;//7日销售额

    @Field
    private Integer wish_save;//7日收藏量

    @Field
    private String merchant_logo;//店铺log

    private String hilghtTitle;

    public String getMerchant_logo() {
        return merchant_logo;
    }

    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_nick() {
        return merchant_nick;
    }

    public void setMerchant_nick(String merchant_nick) {
        this.merchant_nick = merchant_nick;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNew_item_count() {
        return new_item_count;
    }

    public void setNew_item_count(Integer new_item_count) {
        this.new_item_count = new_item_count;
    }

    public String getCat_ids() {
        return cat_ids;
    }

    public void setCat_ids(String cat_ids) {
        this.cat_ids = cat_ids;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getWish_save() {
        return wish_save;
    }

    public void setWish_save(Integer wish_save) {
        this.wish_save = wish_save;
    }

    public String getHilghtTitle() {
        return hilghtTitle;
    }

    public void setHilghtTitle(String hilghtTitle) {
        this.hilghtTitle = hilghtTitle;
    }
}
