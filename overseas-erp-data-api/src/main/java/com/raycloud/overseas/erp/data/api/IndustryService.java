package com.raycloud.overseas.erp.data.api;

import com.raycloud.overseas.erp.data.domain.GuessItem;
import com.raycloud.overseas.erp.data.domain.GuessMerchant;
import com.raycloud.overseas.erp.data.request.*;

import java.util.List;

/**
 * Created by zhanxf on 16/7/16.
 */
public interface IndustryService {



    /**********************************猜你喜欢****************************************/

    /**
     * 获取用户显示的猜你喜欢的产品列表
     * @param request
     * @return
     */
    public List<GuessItem> getGuessItemList(GuessItemListRequest request);

    /**
     * 获取用户显示的猜你喜欢的店铺列表
     * @param request
     * @return
     */
    public List<GuessMerchant> getGuessMerchantList(GuessMerchantListRequest request);



}

