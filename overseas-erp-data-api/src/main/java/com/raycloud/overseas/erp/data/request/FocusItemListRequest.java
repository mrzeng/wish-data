package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;

import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 查询关注产品列表请求
 */
public class FocusItemListRequest extends ListRequest {

    private UserData userData;

    private Integer focus;//0:取消关注,1:关注,2:历史关注

    private TraceOrOrder traceOrOrder ;//追踪或排序用

    private String sortType;

    private String markIds;//标签列表

    private Integer collected;//0未采集,1采集

    private String itemId;//宝贝id

    private String itemName;//宝贝名

    private String searchKey;//宝贝id或宝贝名称

    private String startTime;

    private String endTime;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getMarkIds() {
        return markIds;
    }

    public void setMarkIds(String markIds) {
        this.markIds = markIds;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public TraceOrOrder getTraceOrOrder() {
        return traceOrOrder;
    }

    public void setTraceOrOrder(TraceOrOrder traceOrOrder) {
        this.traceOrOrder = traceOrOrder;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public Integer getCollected() {
        return collected;
    }

    public void setCollected(Integer collected) {
        this.collected = collected;
    }

    @Override
    public boolean validate(Response response){

        return true;
    }
}
