package com.raycloud.overseas.erp.data.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanxf on 16/7/14.
 */
public class IndustryTrendVo {

    private List<Object[]> amount = new ArrayList<Object[]>();//销售额

    private List<Object[]> saleCount = new ArrayList<Object[]>();//销量

    private List<Object[]> save = new ArrayList<Object[]>();//收藏量

    private List<Object[]> itemCount = new ArrayList<Object[]>() ;//宝贝数量

    private List<Object[]> newCount = new ArrayList<Object[]>();//上新数量

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

    public List<Object[]> getNewCount() {
        return newCount;
    }

    public void setNewCount(List<Object[]> newCount) {
        this.newCount = newCount;
    }

    public List<Object[]> getItemCount() {
        return itemCount;
    }

    public void setItemCount(List<Object[]> itemCount) {
        this.itemCount = itemCount;
    }
}
