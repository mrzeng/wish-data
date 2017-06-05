package com.raycloud.overseas.erp.data.request;


import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import java.util.List;
import java.util.Set;

/**
 * 团队关注店铺请求
 */
public class SearchListRequest extends ListRequest {

    private Integer userId;//用户id

    private String fkId = "3";

    private Integer type;//1店铺,2:产品

    private TraceOrOrder traceOrOrder;//追踪或排序用

    private String sortType;

    //////筛选字段/////////////

    private String searchKey;//搜索内容

    private String itemId;

    private String itemName;

    private String merchantId;

    private String merchantName;

    private Double minWishPrice;//最小wish价格

    private Double maxWishPrice;//最大wish价格

    private Double sellerPriceStart;//最小wish价格

    private Double sellerPriceEnd;//最大wish价格

    private String startSellTime;//产品上架开始时间结束时间

    private String endSellTime;

    private Integer minAmount7;

    private Integer maxAmount7;//最小销量-最大销量

    private Integer minSave;

    private Integer maxSave;//最小收藏量-最大收藏量

    private Integer minRateNum;//最少评论数

    private Integer maxRateNum;//最多评论数

    private String catId;//行业id（调用行业列表接口，展示1 2级行业，单选就行）

    private String maxInsertDate;//查询最新的一条店铺记录

    private String eventMerchantId;//里程碑店铺id

    private String eventType;//里程碑类型

    private String eventTime;//里程碑时间

    private List<String> merchantIdList;

    private List<String> itemIdList;

    private Boolean searchInShop = false;

    public Boolean getSearchInShop() {
        return searchInShop;
    }

    public void setSearchInShop(Boolean searchInShop) {
        this.searchInShop = searchInShop;
    }

    private Integer total;

    public SearchListRequest() {
    }

    public SearchListRequest(String itemId) {
        this.itemId = itemId;
    }

    public SearchListRequest(List<String> merchantIdList) {
        this.merchantIdList = merchantIdList;
    }

    public SearchListRequest(TraceOrOrder traceOrOrder, String sortType,List<String> merchantIdList, List<String> itemIdList,String startSellTime, int pageNo, int pageSize) {
        this.traceOrOrder = traceOrOrder;
        this.sortType = sortType;
        this.itemIdList = itemIdList;
        this.merchantIdList = merchantIdList;
        this.startSellTime = startSellTime;
        super.setPageNo(pageNo);
        super.setPageSize(pageSize);
    }

    public SearchListRequest(TraceOrOrder traceOrOrder, String sortType, List<String> merchantIdList, List<String> itemIdList,int pageNo,int pageSize) {
        this.traceOrOrder = traceOrOrder;
        this.sortType = sortType;
        this.merchantIdList = merchantIdList;
        this.itemIdList = itemIdList;
        super.setPageNo(pageNo);
        super.setPageSize(pageSize);
    }

    public String getSearchKey() {
        if(searchKey!=null){
            return searchKey.trim();
        }
        return searchKey;
    }


    public Double getSellerPriceEnd() {
        return sellerPriceEnd;
    }

    public void setSellerPriceEnd(Double sellerPriceEnd) {
        this.sellerPriceEnd = sellerPriceEnd;
    }

    public Double getSellerPriceStart() {
        return sellerPriceStart;
    }

    public void setSellerPriceStart(Double sellerPriceStart) {
        this.sellerPriceStart = sellerPriceStart;
    }

    public List<String> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<String> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public List<String> getMerchantIdList() {
        return merchantIdList;
    }

    public void setMerchantIdList(List<String> merchantIdList) {
        this.merchantIdList = merchantIdList;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getMaxRateNum() {
        return maxRateNum;
    }

    public void setMaxRateNum(Integer maxRateNum) {
        this.maxRateNum = maxRateNum;
    }

    public Integer getMinRateNum() {
        return minRateNum;
    }

    public void setMinRateNum(Integer minRateNum) {
        this.minRateNum = minRateNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEventMerchantId() {
        return eventMerchantId;
    }

    public void setEventMerchantId(String eventMerchantId) {
        this.eventMerchantId = eventMerchantId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId;
    }

    public String getMaxInsertDate() {
        return maxInsertDate;
    }

    public void setMaxInsertDate(String maxInsertDate) {
        this.maxInsertDate = maxInsertDate;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public TraceOrOrder getTraceOrOrder() {
        return traceOrOrder;
    }

    public void setTraceOrOrder(TraceOrOrder traceOrOrder) {
        this.traceOrOrder = traceOrOrder;
    }

    public Double getMinWishPrice() {
        return minWishPrice;
    }

    public void setMinWishPrice(Double minWishPrice) {
        this.minWishPrice = minWishPrice;
    }

    public Double getMaxWishPrice() {
        return maxWishPrice;
    }

    public void setMaxWishPrice(Double maxWishPrice) {
        this.maxWishPrice = maxWishPrice;
    }

    public String getStartSellTime() {
        return startSellTime;
    }

    public void setStartSellTime(String startSellTime) {
        this.startSellTime = startSellTime;
    }

    public String getEndSellTime() {
        return endSellTime;
    }

    public void setEndSellTime(String endSellTime) {
        this.endSellTime = endSellTime;
    }

    public Integer getMinAmount7() {
        return minAmount7;
    }

    public void setMinAmount7(Integer minAmount7) {
        this.minAmount7 = minAmount7;
    }

    public Integer getMaxAmount7() {
        return maxAmount7;
    }

    public void setMaxAmount7(Integer maxAmount7) {
        this.maxAmount7 = maxAmount7;
    }

    public Integer getMinSave() {
        return minSave;
    }

    public void setMinSave(Integer minSave) {
        this.minSave = minSave;
    }

    public Integer getMaxSave() {
        return maxSave;
    }

    public void setMaxSave(Integer maxSave) {
        this.maxSave = maxSave;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

}
