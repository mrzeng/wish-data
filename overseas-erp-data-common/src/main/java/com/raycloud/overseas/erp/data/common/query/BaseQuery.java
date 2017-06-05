package com.raycloud.overseas.erp.data.common.query;

/**
 * @author Ou zhouyou (ouzhouyou@gmail.com)
 */
public class BaseQuery {

	protected Long fkId = 1L;
	public Long getFkId() {
		return fkId;
	}
	public void setFkId(Long fkId) {
		this.fkId = fkId;
	}

	protected int tableId;
	public final static int DEFAULT_PAGE_SIZE = 10;
    public final static int DEFAULT_PAGE_NUM = 1;
	protected Integer pageSize = DEFAULT_PAGE_SIZE;
	protected Integer startRow;//起始行
	protected Integer endRow;//结束行(闭合)
	protected Integer pageNo = DEFAULT_PAGE_NUM;

	public Integer getStartRow() {
		return startRow;
	}
	public BaseQuery setStartRow(Integer startRow) {
		this.startRow = startRow;
		return this;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public BaseQuery setEndRow(Integer endRow) {
		this.endRow = endRow;
		return this;
	}
	public BaseQuery setPageNo(Integer pageNo) {
        if(pageNo < 0) pageNo = DEFAULT_PAGE_NUM;
		this.pageNo = pageNo;
		this.startRow = (pageNo-1) * this.pageSize;
		this.endRow = this.startRow + this.pageSize - 1;
		return this;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public BaseQuery setPageSize(Integer pageSize) {
        if( pageSize < 1) pageSize = 1;
        this.pageSize = pageSize;
        this.startRow = (pageNo-1)*this.pageSize;
        this.endRow= this.startRow + this.pageSize - 1;
        return this;
	}
	public Integer getPageNo() {
		return pageNo;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
}
