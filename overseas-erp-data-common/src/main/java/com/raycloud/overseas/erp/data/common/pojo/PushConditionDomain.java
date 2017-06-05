package com.raycloud.overseas.erp.data.common.pojo;

import java.io.Serializable;

/**
 * Created by zhanxf on 17/2/10.
 */
public class PushConditionDomain implements Serializable{

    private static final long serialVersionUID = 5192535529323962737L;

    private String fkId = "1";

    private Integer id;

    private Integer latestGenDays;//近几天上新的宝贝

    private Integer saleHotCount;//热销宝贝个数

    private Integer amountSurgeCount;//销量飙升个数

    private Long userId;

    private String created;

    private Integer founderId;

    public Integer getFounderId() {
        return founderId;
    }

    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId;
    }

    public Integer getLatestGenDays() {
        return latestGenDays;
    }

    public void setLatestGenDays(Integer latestGenDays) {
        this.latestGenDays = latestGenDays;
    }

    public Integer getSaleHotCount() {
        return saleHotCount;
    }

    public void setSaleHotCount(Integer saleHotCount) {
        this.saleHotCount = saleHotCount;
    }

    public Integer getAmountSurgeCount() {
        return amountSurgeCount;
    }

    public void setAmountSurgeCount(Integer amountSurgeCount) {
        this.amountSurgeCount = amountSurgeCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
