package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 查询关注店铺列表请求
 */
public class MerchantHotItemListRequest extends ListRequest {

    private String merchantId;

    private String startTime;

    private String endTime;

    private TraceOrOrder traceOrOrder = TraceOrOrder.amount_7;//追踪或排序用

    private String sortType = "DESC";

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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


    @Override
    public boolean validate(Response response){
        if(merchantId == null){
            response.setResult(400);
            response.setMessage("店铺id不能为空");
            return false;
        }
        return true;
    }
}
