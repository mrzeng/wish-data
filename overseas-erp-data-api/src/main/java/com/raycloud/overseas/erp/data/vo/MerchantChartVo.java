package com.raycloud.overseas.erp.data.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanxf on 16/7/14.
 */
public class MerchantChartVo {

    private List<Object[]> amount = new ArrayList<Object[]>();//销售额

    private List<Object[]> saleCount = new ArrayList<Object[]>();//销量

    private List<Object[]> save = new ArrayList<Object[]>();//收藏量

    private List<Object[]> rateNum = new ArrayList<Object[]>();//评论数

    private List<Object[]> newCount = new ArrayList<Object[]>();//上新数量

    private List<Object[]> changPrice = new ArrayList<Object[]>();//改价产品

    private List<Object[]> changeTag = new ArrayList<Object[]>();//标签改动

    private List<Object[]> passAudit = new ArrayList<Object[]>();//通过审核

    private List<Object[]> wishAuth = new ArrayList<Object[]>();//被wish认证的产品

    private List<Object[]> hasCatItemCount = new ArrayList<Object[]>();//以被分配行业的产品数量追踪

    public List<Object[]> getAmount() {
        return amount;
    }

    public void setAmount(List<Object[]> amount) {
        this.amount = amount;
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

    public List<Object[]> getRateNum() {
        return rateNum;
    }

    public void setRateNum(List<Object[]> rateNum) {
        this.rateNum = rateNum;
    }

    public List<Object[]> getNewCount() {
        return newCount;
    }

    public void setNewCount(List<Object[]> newCount) {
        this.newCount = newCount;
    }

    public List<Object[]> getChangPrice() {
        return changPrice;
    }

    public void setChangPrice(List<Object[]> changPrice) {
        this.changPrice = changPrice;
    }

    public List<Object[]> getChangeTag() {
        return changeTag;
    }

    public void setChangeTag(List<Object[]> changeTag) {
        this.changeTag = changeTag;
    }

    public List<Object[]> getPassAudit() {
        return passAudit;
    }

    public void setPassAudit(List<Object[]> passAudit) {
        this.passAudit = passAudit;
    }

    public List<Object[]> getWishAuth() {
        return wishAuth;
    }

    public void setWishAuth(List<Object[]> wishAuth) {
        this.wishAuth = wishAuth;
    }

    public List<Object[]> getHasCatItemCount() {
        return hasCatItemCount;
    }

    public void setHasCatItemCount(List<Object[]> hasCatItemCount) {
        this.hasCatItemCount = hasCatItemCount;
    }
}
