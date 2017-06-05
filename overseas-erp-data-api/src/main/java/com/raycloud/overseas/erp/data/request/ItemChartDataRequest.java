package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 商品一段时间内销售及收藏数据请求
 */
public class ItemChartDataRequest extends Request {

    private String itemId;//商品id

    private String merchantId;

    private String startTime;

    private String endTime;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public boolean validate(Response response){
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
        if(itemId == null){
            response.setResult(400);
            response.setMessage("产品id不能为空");
            return false;
        }
        return true;
    }
}
