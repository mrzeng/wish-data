package com.raycloud.overseas.erp.data.services.api;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.UserCommonCat;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.*;
import com.raycloud.overseas.erp.data.vo.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanxf on 16/7/16.
 */
public interface ILocalIndustryService {

    /**
     * 获取数据刷新时间
     * @param type
     * @return
     */
    public String getDataRefreshTime(int type);

    /**
     * 获取行业详情数据
     * @return
     */
    public IndustryDetailVo getIndustryDetail(String id,Integer level);

    /**
     * 获取行业趋势
     * @return
     */
    public IndustryTrendVo getIndustryTrend(String id,String startTime,String endTime);

    /**
     * 获取明星产品或明星店铺
     * @param request
     * @return
     * @throws ParseException
     */
    public Result star(StarRequest request);

    /**
     * 获取明星产品
     * @param id
     * @param sortField
     * @param order
     * @return
     */
    public Result getStarItemList(String id,TraceOrOrder sortField,String order,int pageNo,int pageSize);

    /**
     * 获取明星店铺
     * @param id
     * @param sortField
     * @param order
     * @return
     */
    public Result getStarMerchantList(String id,TraceOrOrder sortField,String order,int pageNo,int pageSize);

    /**
     * 获取子行业产品销量走势图
     * @return
     */
    public Map<String,Object> getSubCatChart(String id,String startTime,String endTime,TraceOrOrder traceOrOrder);

    /**
     * 获取行业销量暴增产品列表
     * @param request
     * @return
     */
    public Result getAmountGrowthItemList(AmountGrowthItemRequest request);

    /**
     * 获取行业上新产品列表
     * @param request
     * @return
     */
    public Result getNewItemList(NewItemRequest request) throws BizException;

    /**
     * 获取全行业类目数据
     * @return
     */
    public List<CategoryVo> getAllCat();

    /**
     * 获取默认行业类目
     * @return
     */
    public CategoryVo getDefaultCategory();



    /**
     * 查询常用行业
     * @param userId
     * @param type
     * @return
     */
    public List<UserCommonCat> queryCommonCat(Integer userId, Integer type);

    public boolean insertCommonCat(UserCommonCat userCommonCat);

}

