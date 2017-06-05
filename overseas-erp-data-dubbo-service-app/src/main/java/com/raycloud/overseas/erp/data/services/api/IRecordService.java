package com.raycloud.overseas.erp.data.services.api;


import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.RecordRequest;
import com.raycloud.overseas.erp.data.vo.RecordItemVo;
import com.raycloud.overseas.usercenter.web.api.pojo.Shop;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zhanxf on 16/7/16.
 */
public interface IRecordService {

    /**
     * 收录所有在db中不存在的宝贝
     * @param since 刷新时间
     */
    public void recordUnInDBProduct(String since);

    /**
     * 获取店铺的所有宝贝
     * @param shop
     * @return
     */
    public List<String> getNormalShopItemListByAccessToken(Shop shop, String since) throws BizException;

    /**
     * 获取大店铺的所有宝贝
     * @param shop
     * @return
     */
    public List<String> getLargeShopItemListByAccessToken(Shop shop, String since);

    /**
     * 分页获取wish店铺宝贝列表
     * @param shop
     * @param start
     * @return
     */
    public LinkedHashMap<String, Object> getWishProductByPage(Shop shop, int start, String since) throws BizException;

    /**
     * 下载超过25000的wish宝贝
     * @param url
     * @param fileName
     * @return
     */
    public List<String> downWishItemIdList(String url, String fileName);

    /**
     * 获取未收录宝贝列表
     * @return
     */
    public List<String> getUnRecordItemIdList(Shop shop, String since);

    /**
     * 获取所有店铺
     * @param platType
     * @return
     */
    public List<Shop> getAllShops(int platType);

    /**
     * 定时收录宝贝任务
     */
    public void timerRecordItemTask();

    /**
     * 收录店铺和该店铺的宝贝信息
     * @throws Exception
     */
    public void recordMerchant(String shopId, String accessToken, String userId) throws BizException;

    /**
     * 校验店铺宝贝是否存在于卖家网数据库,如果没有就收录
     * @param shopId
     * @param accessToken
     * @param userId
     */
    public void recordUnExistMerchant(String shopId, String accessToken, String userId) throws BizException;

    /**
     * 获取收录产品或店铺
     * @return
     */
    public RecordItemVo getRecordResponse(RecordRequest request) throws BizException;

    /**
     * 检查wish是否有该产品id对应的产品
     * @param itemId
     * @return
     */
    public RecordItemVo checkWishItemExist(String itemId) throws BizException;

    /**
     * 检查wish是否有该店铺
     * @param merchantName
     * @return
     */
    public RecordItemVo checkWishMerchantExist(String merchantName) throws BizException;

//   /* *//**
//     * 收录店铺和该店铺的宝贝信息
//     * @throws Exception
//     */
//    public void recordMerchant(String shopId, String accessToken, String userId) throws BizException;
//
//    /**
//     * 校验店铺宝贝是否存在于卖家网数据库,如果没有就收录
//     * @param shopId
//     * @param userId
//     */
//    public void recordUnExistMerchant(String shopId, String accessToken, String userId) throws BizException;*/

    public List<JSONObject> getAllProduct(String shopId, String userId) throws BizException;
}
