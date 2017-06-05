package com.raycloud.overseas.erp.data.common.pojo;

import java.io.Serializable;

/**
 *
 * @author  zhanxf
 */
public class BasePojo implements Serializable{

    private static final long serialVersionUID = 1L;
    private String splitTableName;
    private String splitDBName = "1";
    protected Long fkId = 1L;
    protected Integer tableId;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }

    public String getSplitTableName() {
        return splitTableName;
    }

    public void setSplitTableName(String splitTableName) {
        this.splitTableName = splitTableName;
    }

    public String getSplitDBName() {
        return splitDBName;
    }

    public void setSplitDBName(String splitDBName) {
        this.splitDBName = splitDBName;
    }
}