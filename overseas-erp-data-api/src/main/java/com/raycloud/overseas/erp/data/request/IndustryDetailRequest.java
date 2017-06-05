package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 行业详情
 */
public class IndustryDetailRequest extends Request {

    private String id;//行业类目主键id

    private int level;//行业层级

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean validate(Response response){
        if(id == null){
            response.setResult(400);
            response.setMessage("查询参数异常,行业类目id不能为空");
            return false;
        }
        return true;
    }
}
