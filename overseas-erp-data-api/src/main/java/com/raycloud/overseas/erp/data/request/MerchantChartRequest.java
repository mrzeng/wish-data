package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 店铺经营数据查询请求
 */
public class MerchantChartRequest extends Request {

    private String merchantId;//店铺id

    private String startTime;

    private String endTime;

    private int type;//店铺统计类型,1:店铺经营数据1,2:店铺经营数据2

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public boolean validate(Response response){
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
