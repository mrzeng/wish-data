package com.raycloud.overseas.erp.data.services.api;




import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.FocusMerchant;
import com.raycloud.overseas.erp.data.common.pojo.MerchantDomain;
import com.raycloud.overseas.erp.data.common.query.FocusMerchantQuery;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.vo.MerchantChartVo;
import com.raycloud.overseas.erp.data.vo.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanxf on 16/7/16.
 */
public interface ILocalMerchantService {

    /**
     * 根据主键获取获取关注店铺
     * @param userData
     * @param merchantId
     * @return
     */
    public FocusMerchant getFocusMerchantByKey(UserData userData, String merchantId);

    /**
     * 关注宝贝列表
     * @param userData
     * @param ids
     * @return
     */
    public Response focusMerchants(UserData userData, List<String> ids,Integer focus) throws BizException;

    /**
     * 获取关注店铺列表
     * @param focusMerchantQuery
     * @return
     */
    public List<FocusMerchant> getFocusMerchantList(FocusMerchantQuery focusMerchantQuery);

    /**
     * 分页获取关注店铺列表
     * @param request
     * @return
     */
    public Result getFocusMerchantList(FocusMerchantListRequest request) throws BizException;


    /**
     * 关注店铺
     * @param userId
     * @param merchantId
     * @param focus
     * @throws BizException
     */
    public void focusMerchant(UserData userData,String merchantId,Integer focus) throws BizException;

    /**
     * 获取店铺详情
     * @param request
     * @return
     */
    public MerchantDomain getMerchantDetailVo(MerchantDetailRequest request);

    /**
     * 获取店铺经营数据echart图表
     * @param request
     * @return
     */
    public MerchantChartVo getMerchantChartVo(MerchantChartRequest request);

    /**
     * 获取产品行业分析数据图表
     * @param request
     * @return
     */
    public Map<String,Object> getCatChart(MerchantCatChartRequest request);

    /**
     * 获取店铺热销产品列表
     * @param request
     * @return
     */
    public Result  getHotItemList(MerchantHotItemListRequest request) throws BizException;

    /**
    * 获取店铺上新产品列表
    * @param request
    * @return
            */
    public Result getNewItemList(MerchantNewItemRequest request) throws BizException;

    /**
     * 获取店铺里程碑列表列表
     * @param request
     * @return
     */
    public Result getMerchantEventList(MerchantItemEventRequest request);

//    public FocusMerchant getFocusMerchant(Integer userId, String merchantId);
//
//    public FocusItem getFocusItem(Integer userId, String itemId);

//    public void initFcousData();

    /**
     * 查询店铺滞销宝贝（近30天销量为零）
     *
     * @param merchantId 店铺id
     * @param pageNo 页码
     * @param pageSize 每页数据条数
     * @return
     */
    public Result queryUnsalableItemsbyMerchantId(String merchantId, Integer pageNo, Integer pageSize);
}
