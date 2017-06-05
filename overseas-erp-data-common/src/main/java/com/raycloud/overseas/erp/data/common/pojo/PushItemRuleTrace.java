package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-13
 */
public class PushItemRuleTrace  extends BasePojo {


    /**
     *序列化ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 规则id
     */
    private String ruleId;
    /**
     * 插入时间
     */
    private String insertDate;
    /**
     * 宝贝id
     */
    private String itemId;
    /**
     * user_id
     */
    private Integer userId;
    /**
     * founder_id
     */
    private Integer founderId;

    public PushItemRuleTrace() {
    }

    public PushItemRuleTrace(String ruleId, String insertDate, String itemId, Integer userId, Integer founderId) {
        this.ruleId = ruleId;
        this.insertDate = insertDate;
        this.itemId = itemId;
        this.userId = userId;
        this.founderId = founderId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * @return insertDate 插入时间
     */
    public String getInsertDate() {
        return insertDate;
    }
    /**
     * @param insertDate 插入时间
     */
    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    /**
     * @return itemId 宝贝id
     */
    public String getItemId() {
        return itemId;
    }
    /**
     * @param itemId 宝贝id
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @return userId user_id
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * @param userId user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return founderId founder_id
     */
    public Integer getFounderId() {
        return founderId;
    }
    /**
     * @param founderId founder_id
     */
    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
    }
}