package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

import java.util.Date;

/**
 * 操作关注请求
 */
public class OperFocusRequest extends Request {

    private int userId;//用户id

    private String ids;//店铺id,或产品id数组

    private Integer focus;// 1:关注,0:取消关注,2:暂停关注

    private String id;

    private Integer type;//1:店铺,2:商品

    private Date expireTime;//过期时间

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean validate(Response response){
        if(ids == null || ids.length()==0){
            response.setResult(400);
            response.setMessage("店铺id或产品id不能为空");
            return false;
        }
        if(focus == null){
            response.setResult(400);
            response.setMessage("是否进行关注不能为空");
            return false;
        }
        if(type == null){
            response.setResult(400);
            response.setMessage("类型不能为空");
            return false;
        }
        return true;
    }
}
