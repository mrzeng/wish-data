package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 商品详情查询请求
 */
public class StarRequest extends ListRequest {

    private String userId;

    private String id;//行业id

    private int type;//1明星产品,2:明星店铺

    private String startTime;

    private String endTime;

    private TraceOrOrder traceOrOrder;

    private String sortType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
    public boolean validate(Response response) {
        if(id == null){
            response.setResult(400);
            response.setMessage("行业类目id不能为空");
            return false;
        }
        if(userId == null){
            response.setResult(400);
            response.setMessage("会话失效");
            return false;
        }
        return true;
    }

}
