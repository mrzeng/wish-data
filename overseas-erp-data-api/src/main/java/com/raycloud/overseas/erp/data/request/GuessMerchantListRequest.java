package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.vo.Response;

import java.io.Serializable;

public class GuessMerchantListRequest extends ListRequest implements Serializable{

    private static final long serialVersionUID = -2187173981072961596L;

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
