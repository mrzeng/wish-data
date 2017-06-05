package com.raycloud.overseas.erp.data.vo;

/**
 * Created by zhanxf on 16/7/14.
 */
public class IndustryDetailVo {

    private String id;//wish类目主键id

    private String catId;//行业id

    private String catName;//行业名称

    private String englishName;//

    private String catImgUrl;//行业图片

    private Integer shopCount;//店铺数量

    private Integer itemCount;//产品数量

    private String avgPrice;//平均价格

    private Integer avgAmount7;//近7日日均销量,暂无

    private Integer avgSave7;//近7日日均收藏量,暂无

    private String outRate7;//动销率

    private Double avgPrice7;//7日日均销售额

    public Double getAvgPrice7() {
        return avgPrice7;
    }

    public void setAvgPrice7(Double avgPrice7) {
        this.avgPrice7 = avgPrice7;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public void setAvgAmount7(Integer avgAmount7) {
        this.avgAmount7 = avgAmount7;
    }

    public void setAvgSave7(Integer avgSave7) {
        this.avgSave7 = avgSave7;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImgUrl() {
        return catImgUrl;
    }

    public void setCatImgUrl(String catImgUrl) {
        this.catImgUrl = catImgUrl;
    }

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getAvgAmount7() {
        return avgAmount7;
    }

    public void setAvgAmount7(int avgAmount7) {
        this.avgAmount7 = avgAmount7;
    }

    public Integer getAvgSave7() {
        return avgSave7;
    }

    public void setAvgSave7(int avgSave7) {
        this.avgSave7 = avgSave7;
    }

    public String getOutRate7() {
        return outRate7;
    }

    public void setOutRate7(String outRate7) {
        this.outRate7 = outRate7;
    }
}
