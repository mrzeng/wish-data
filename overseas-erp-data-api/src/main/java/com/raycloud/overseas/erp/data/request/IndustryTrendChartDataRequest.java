package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 行业趋势图
 */
public class IndustryTrendChartDataRequest extends Request {

    private String id;//行业id

    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public String setStartTime(String startTime) {
        this.startTime = startTime;
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean validate(Response response) {
        if(id == null){
            response.setResult(400);
            response.setMessage("行业类目id不能为空");
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
