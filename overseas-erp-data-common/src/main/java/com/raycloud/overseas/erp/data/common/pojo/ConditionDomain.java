package com.raycloud.overseas.erp.data.common.pojo;

import java.io.Serializable;

/**
 * Created by zhanxf on 17/2/10.
 */
public class ConditionDomain extends BasePojo {

    private static final long serialVersionUID = 4067196696776774760L;

    private Integer userId;//用户名

    private Integer id;//筛选项id

    private Integer modelId;

    private String modelName;//筛选条件模块名(同一个用户不可重复)

    private String conditions;//属性值(自己组装成以后能解析的格式类似于新手引导)

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
