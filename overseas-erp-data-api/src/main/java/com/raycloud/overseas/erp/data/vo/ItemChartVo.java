package com.raycloud.overseas.erp.data.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanxf on 16/7/14.
 */
public class ItemChartVo {

    private List<Object[]> saleCount = new ArrayList<Object[]>();//销量

    private List<Object[]> save = new ArrayList<Object[]>();//收藏量

    private List<Object[]> originalPrice = new ArrayList<Object[]>();//吊牌价

    private List<Object[]> wishPrice = new ArrayList<Object[]>();//wish售价

    private List<Object[]> wishFreightPrice = new ArrayList<Object[]>();//wish运费价格

    private List<Object[]> sellerPrice = new ArrayList<Object[]>();//卖家价格

    private List<Object[]> sellerFreightPrice = new ArrayList<Object[]>();//卖家运费价格

    public List<Object[]> getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(List<Object[]> originalPrice) {
        this.originalPrice = originalPrice;
    }

    public List<Object[]> getWishPrice() {
        return wishPrice;
    }

    public void setWishPrice(List<Object[]> wishPrice) {
        this.wishPrice = wishPrice;
    }

    public List<Object[]> getWishFreightPrice() {
        return wishFreightPrice;
    }

    public void setWishFreightPrice(List<Object[]> wishFreightPrice) {
        this.wishFreightPrice = wishFreightPrice;
    }

    public List<Object[]> getSellerPrice() {
        return sellerPrice;
    }

    public void setSellerPrice(List<Object[]> sellerPrice) {
        this.sellerPrice = sellerPrice;
    }

    public List<Object[]> getSellerFreightPrice() {
        return sellerFreightPrice;
    }

    public void setSellerFreightPrice(List<Object[]> sellerFreightPrice) {
        this.sellerFreightPrice = sellerFreightPrice;
    }

    public List<Object[]> getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(List<Object[]> saleCount) {
        this.saleCount = saleCount;
    }

    public List<Object[]> getSave() {
        return save;
    }

    public void setSave(List<Object[]> save) {
        this.save = save;
    }
}
