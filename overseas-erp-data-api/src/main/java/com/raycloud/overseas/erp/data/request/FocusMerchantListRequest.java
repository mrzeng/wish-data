package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 查询关注店铺列表请求
 */
public class FocusMerchantListRequest extends ListRequest {

    private UserData userData;

    private Integer focus;

    private TraceOrOrder traceOrOrder = TraceOrOrder.amount_7;//追踪或排序用

    private String sortType = "DESC";

    private String markIds;//标签列表

    private Integer collected;//0未采集,1采集

    private String merchantId;

    private String merchantName;

    private String searchKey;

    private String startTime;

    private String endTime;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public TraceOrOrder getTraceOrOrder() {
        return traceOrOrder;
    }

    public void setTraceOrOrder(TraceOrOrder traceOrOrder) {
        this.traceOrOrder = traceOrOrder;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
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
