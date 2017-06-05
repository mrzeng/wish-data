package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 新增产品请求
 */
public class EventItemIdRequest extends Request {

    private String merchantId;

    private String date;

    private String eventType;//里程碑类型

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean validate(Response response) {
        if(merchantId == null){
            response.setResult(400);
            response.setMessage("店铺id不能为空");
            return false;
        }
        if(date == null){
            response.setResult(400);
            response.setMessage("日期不能为空");
            return false;
        }
        if(eventType == null){
            response.setResult(400);
            response.setMessage("里程碑类型不能为空");
            return false;
        }
        return true;
    }
}
