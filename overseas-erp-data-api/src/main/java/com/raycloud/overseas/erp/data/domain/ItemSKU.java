package com.raycloud.overseas.erp.data.domain;

import java.io.Serializable;

/**
 * Created by zhanxf on 16/8/15.
 */
public class ItemSKU implements Serializable{

    private static final long serialVersionUID = -4478307117816178647L;
    private String pic;//照片序号

    private String sku;

    private String color;

    private String size;

    private Double price;

    public ItemSKU() {
    }

    public ItemSKU(String pic, String sku, String color, String size, Double price) {
        this.pic = pic;
        this.sku = sku;
        this.color = color;
        this.size = size;
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
