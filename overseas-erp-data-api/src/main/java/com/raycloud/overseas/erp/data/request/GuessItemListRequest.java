package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

import java.io.Serializable;

/**
 * 查询关注产品列表请求
 */
public class GuessItemListRequest extends Request implements Serializable{

    private static final long serialVersionUID = -4694443991930921235L;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean validate(Response response){
        return true;
    }
}
