package com.raycloud.overseas.erp.data.common.common;


import com.raycloud.overseas.erp.data.common.query.BaseQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Result<T> implements Serializable {

	public Result() {
	}

	public Result(BaseQuery baseQuery){
		this.pageNo = baseQuery.getPageNo();
		this.pageSize = baseQuery.getPageSize();
		this.total = 0L;
	}

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -859829257318035808L;
	
	private boolean isSuccess = true;
	private List<T> items;
	private Long total;
	private String errorMsg;

	private int pageNo;

	private int pageSize;

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
		this.pageSize = pageSize;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<T> getItems() {
		if(items == null){
			items = new ArrayList<T>();
		}
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
