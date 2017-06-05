package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 店铺经营数据查询请求
 */
public class IndustrySubCatChartRequest extends Request {

    private String id;//行业类目id

    private String startTime;

    private String endTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private TraceOrOrder traceOrOrder;//追踪类型

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

    public TraceOrOrder getTraceOrOrder() {
        return traceOrOrder;
    }

    public void setTraceOrOrder(TraceOrOrder traceOrOrder) {
        this.traceOrOrder = traceOrOrder;
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
