package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 店铺详情查询请求
 */
public class MerchantDetailRequest extends Request {

    private UserData userData;

    private String merchantId;//店铺id

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @Override
    public boolean validate(Response response){
        if(merchantId == null){
            response.setResult(400);
            response.setMessage("店铺id不能为空");
            return false;
        }
        return true;
    }
}
