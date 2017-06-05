package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 销量飙升请求
 */
public class AmountGrowthItemRequest extends ListRequest {


    private String id;

    private TraceOrOrder traceOrOrder;

    private String sortType;

    private String maxInsertDate;

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


    public String getMaxInsertDate() {
        return maxInsertDate;
    }

    public void setMaxInsertDate(String maxInsertDate) {
        this.maxInsertDate = maxInsertDate;
    }


    @Override
    public boolean validate(Response response) {
        if(id == null){
            response.setResult(400);
            response.setMessage("行业类目id不能为空");
            return false;
        }
        return true;
    }
}
