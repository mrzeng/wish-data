package com.raycloud.overseas.erp.data.request;

import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 收录请求
 */
public class RecordRequest extends Request {

    private String merchantName;//收录店铺

    private String itemId;//收录产品

    private Integer userId;//用户id

    private String merchantId;//店铺id

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean validate(Response response){
        if(itemId==null&&merchantName==null){
            response.setResult(400);
            response.setMessage("请先填入产品id或店铺名称");
            return false;
        }
        return super.validate(response);
    }
}
