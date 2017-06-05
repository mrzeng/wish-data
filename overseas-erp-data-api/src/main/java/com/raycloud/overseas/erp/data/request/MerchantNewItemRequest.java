package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 新增产品请求
 */
public class MerchantNewItemRequest extends ListRequest {

    private TraceOrOrder traceOrOrder;

    private String sortType;

    private String merchantId;//行业id映射

    private String maxInsertDate;//最新插入数据库时间

    private String startTime;

    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMaxInsertDate() {
        return maxInsertDate;
    }

    public void setMaxInsertDate(String maxInsertDate) {
        this.maxInsertDate = maxInsertDate;
    }

    public TraceOrOrder getTraceOrOrder() {
        return traceOrOrder;
    }

    public void setTraceOrOrder(TraceOrOrder traceOrOrder) {
        this.traceOrOrder = traceOrOrder;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public boolean validate(Response response) {
        if(merchantId == null){
            response.setResult(400);
            response.setMessage("店铺id不能为空");
            return false;
        }
        return true;
    }
}
