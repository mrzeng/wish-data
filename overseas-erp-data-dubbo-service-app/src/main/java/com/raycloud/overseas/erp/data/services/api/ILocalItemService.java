package com.raycloud.overseas.erp.data.services.api;





import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.commom.domain.wish.domain.ItemMilestone;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.FocusItem;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.ItemEvent;
import com.raycloud.overseas.erp.data.common.query.FocusItemQuery;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.FocusItemListRequest;
import com.raycloud.overseas.erp.data.request.ItemChartDataRequest;
import com.raycloud.overseas.erp.data.request.ItemDetailRequest;
import com.raycloud.overseas.erp.data.services.context.CollectContext;
import com.raycloud.overseas.erp.data.vo.ItemChartVo;
import com.raycloud.overseas.erp.data.vo.Response;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanxf on 16/7/16.
 */
public interface ILocalItemService {

    /**
     * 根据主键查找
     * @throws SQLException
     */
    public FocusItem getFocusItemByKey(UserData userData,String itemId);

    /**
     * 获取关注产品列表
     * @param request
     * @return
     */
    public Result getFocusItemList(FocusItemListRequest request) throws BizException;

    /**
     * 获取关注列表
     * @param focusItemQuery
     * @return
     */
    public List<FocusItem> getFocusItemList(FocusItemQuery focusItemQuery);

    /**
     * 获取产品详情
     * @param request
     * @return
     */
    public ItemDomain getItemDetail(ItemDetailRequest request) throws BizException;

    /**
     * 获取产品echart图
     * @param request
     * @return
     * @throws ParseException
     */
    public ItemChartVo getItemChart(ItemChartDataRequest request);

    /**
     * 获取宝贝们的里程碑列表
     * @param itemIds
     * @return
     */
    public Result getItemsEventList(List<String> itemIds,List<String> merchantIdList);

//    /**
//     * 查询产品相关热销产品
//     * @return
//     */
//    public Result queryRelatedHotItems(String itemId, Integer days, String sortField, Integer pageNo, Integer pageSize);
//
//
//    /**
//     * 查询某产品相关的飙升产品
//     * @param itemId 产品id
//     * @param days 7:按近7天销量查询，30:按近30天销量查询
//     * @param sortField 排序字段 amount(销量)/price(销售额)/amountDiff(近7天与上一个7天销量差额)，默认倒序排序，不传则无排序规则
//     * @param pageNo 页码
//     * @param pageSize 每页数据条数
//     * @return
//     */
//    public Result queryItemRelatedIndustrySurgeItemList(String itemId, Integer days, String sortField, Integer pageNo, Integer pageSize);
//
//
//    /**
//     * 查询某产品相关的标签，按标签热度倒序排序
//     * 这里的业务逻辑是产品定的：查询产品对应level最高行业的热销产品的所有标签
//     * @param itemId 产品id
//     * @param tagType 标签类型：0-wish推荐标签，1-卖家自定义标签，注意：目前只有卖家自定义标签数据！
//     * @param pageNo 页码
//     * @param pageSize 每页数据条数
//     * @return
//     */
//    public Result queryItemRelatedIndustryHotItemTagList(String itemId, Integer tagType, Integer pageNo, Integer pageSize);

    /**
     * 获取里程碑描述
     * @param mark
     * @return
     */
    public String getItemEventDesc(ItemMilestone mark);

    /**
     * 将卖家网里程碑类型转换为本地里程碑类型格式进行显示
     * @param markList
     * @return
     */
    public List<ItemEvent> getItemEventList(List<ItemMilestone> markList);

    /**
     * 获取标签里程碑事件
     * @param tag
     * @param date
     * @param divDesc
     * @return
     */
    public List<ItemEvent> getTagItemEvent(String tag,Date date,String divDesc);

    /**
     * 获取行业里程碑事件
     * @param date
     * @return
     */
    public List<ItemEvent> getCatItemEvent(String cat,Date date);

    /**
     * 获取价格调整里程碑事件
     * @return
     */
    public List<ItemEvent> getPriceItemEvent(ItemMilestone milestone);

    /**
     * 获取店铺某种里程碑类型的宝贝id列表
     * @return
     */
    public List<String> getItemIdListByEventType(String merchantId, String eventType, String eventTime, Integer pageNo, Integer pageSize);

    /**
     * 批量更新关注宝贝
     * @param expireFocusItemList
     */
    public void batchUpdateFocusItem(List<FocusItem> expireFocusItemList);

    public void setHistoryImageIdList(ItemDomain itemDomain,String skuImages);

    /**
     * 通过关注宝贝详情界面采集产品到本地
     * @param itemId
     * @param userData
     */
    public void collectToLocalByFocus(String itemId,String merchantId, UserData userData) throws BizException;

    /**
     * 通过搜索或推送列表采集产品到本地
     * @param itemIdList
     * @param userData
     */
    public CollectContext collectToLocalByEntry(List<String> itemIdList,List<String> merchantIdList,boolean needBean, UserData userData);

    /**
     * 采集产品然后关注
     * @param collectContext
     */
    public void collectThenFocus(CollectContext collectContext);

    /**
     * 设置店长id,前往产品查看用
     * @param itemDomin
     * @param userData
     */
    public void setSuperBossNo(ItemDomain itemDomin, UserData userData);

    /**
     * 关注宝贝列表
     * @param itemIdList
     * @param focus
     * @param expireTime
     * @param userData
     * @throws BizException
     */
    public void focusItemList(List<String> itemIdList,List<String> merchantIdList,Integer focus,Date expireTime,UserData userData) throws BizException;

    /**
     * 标记团队内所有成员的此次采集的宝贝为已采集
     * @param itemIdList
     * @param expireTime
     * @param userData
     */
    public void markTeamItemAsCollected(List<String> itemIdList,Date expireTime,UserData userData);

    /**
     * 处理宝贝标签
     * @param itemDomain
     */
    public void dealItemTags(ItemDomain itemDomain,Item item);

    public void dealItemDetail(ItemDomain itemDomain,Item item);

    public String getMerchantIdByItem(String itemId);

    public ItemDomain getItemForCollect(String itemId,String merchantId, UserData userData) throws BizException;

    public List<ItemDomain> getItemListForCollect(List<String> itemIdList,List<String> merchantIdList, UserData userData) throws BizException;
}
