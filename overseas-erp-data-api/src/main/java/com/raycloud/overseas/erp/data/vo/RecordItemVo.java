package com.raycloud.overseas.erp.data.vo;

import java.io.Serializable;

/**
 * Created by zhanxf on 16/7/14.
 * 新增产品详情对象
 */
public class RecordItemVo implements Serializable{

    private String itemId;//产品id

    private String merchantName;//店铺名(后台用)

    private Integer status;//0等待收录,1:收录成功,2:wish中暂未收到该产品或店铺

    private String merchantId;//店铺id

    private String itemName;//产品名称

    private String merchantNick;//店铺名称(显示用)

    private String itemLogoUrl;//图片url

    private String merchantLogoUrl;

    public String getMerchantLogoUrl() {
        return merchantLogoUrl;
    }

    public void setMerchantLogoUrl(String merchantLogoUrl) {
        this.merchantLogoUrl = merchantLogoUrl;
    }

    public String getItemLogoUrl() {
        return itemLogoUrl;
    }

    public void setItemLogoUrl(String itemLogoUrl) {
        this.itemLogoUrl = itemLogoUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMerchantNick() {
        return merchantNick;
    }

    public void setMerchantNick(String merchantNick) {
        this.merchantNick = merchantNick;
    }
}
