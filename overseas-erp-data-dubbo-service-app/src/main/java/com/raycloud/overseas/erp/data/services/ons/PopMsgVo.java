package com.raycloud.overseas.erp.data.services.ons;

import java.io.Serializable;
import java.util.Date;

/**
 * PopMsg.java
 * Created by zhongliang
 * Created on 2016/8/2 下午6:05
 * Copyright(c)2014  版权所有
 */
public class PopMsgVo implements Serializable {


    private static final long serialVersionUID = 7727788831948930987L;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 消息的内容
     */
    private Object body;

    /**
     * 0 发给自己
     * 1 发给组内
     * 2 全体
     */
    private Integer type;

    /**
     * 消息的类型,例如订单更新类型
     */
    private String tag;

    private Date created;
    /**
     *0:未读,1:已读
     */
    private String status;

    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
