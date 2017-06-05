package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 新增产品请求
 */
public class NewItemRequest extends ListRequest {

    private TraceOrOrder traceOrOrder;

    private String sortType;

    private String id;//行业id映射

    private String maxInsertDate;//最新插入数据库时间

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
        if(id == null){
            response.setResult(400);
            response.setMessage("行业类目id不能为空");
            return false;
        }
        return true;
    }
}
