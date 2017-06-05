package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.vo.Response;

/**
 * 商品详情查询请求
 */
public class ItemDetailRequest extends Request {

    private String itemId;//商品id

    private String merchantId;

    private UserData userData;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public boolean validate(Response response){

        if(itemId == null){
            response.setResult(400);
            response.setMessage("产品id不能为空");
            return false;
        }
        return true;
    }
}
