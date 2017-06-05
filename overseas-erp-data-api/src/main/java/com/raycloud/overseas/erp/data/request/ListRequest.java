package com.raycloud.overseas.erp.data.request;



/**
 * Created by forest on 14-9-10.
 */
public class ListRequest extends Request {

    private int pageNo = 1; // [必需] 查询的页码

    private int pageSize = 10; // [可选] 每页的数目

    public static int DEFAULT_PAGESIZE = 50;

    private boolean force;

    private int firstResult;

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize<=500?pageSize:500;
    }

    public void setPageSizeForce(int pageSize,boolean force){
        if(force){
            this.pageSize = pageSize;
        }
    }

}


