package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 新增产品请求
 */
public class MerchantItemEventRequest extends ListRequest {

    private String merchantId;

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

    @Override
    public boolean validate(Response response) {
        if(merchantId == null){
            response.setResult(400);
            response.setMessage("店铺id不能为空");
            return false;
        }
        if(startTime == null){
            response.setResult(400);
            response.setMessage("开始时间不能为空");
            return false;
        }
        if(endTime == null){
            response.setResult(400);
            response.setMessage("结束时间不能为空");
            return false;
        }
        return true;
    }
}
