package com.raycloud.overseas.erp.data.domain;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanxf on 16/8/15.
 */
public class ItemDomain implements Serializable{

    private static final long serialVersionUID = 19698751325268995L;

    protected Date insertDate;

    protected String itemId;//产品id

    protected String itemName;//产品名称

    protected String itemLogoUrl;//产品url

    protected String merchantId;//店铺id

    protected String merchantName;//店铺名称

    protected String catIds;//行业id列表

    protected String catNames;//行业名称


    /********************************************基础数据****************************************************/

    protected Boolean removeFlag;

    protected String openStoreDate;//产品上架日期

    protected String genTime;//上架时间

    protected Double rateScore;//产品评分

    protected Integer alreadyRecommended;//true:被认证,false:未被认证

    protected Integer amount;//累计销量

    protected Integer rateNum;//累计评论数

    protected Double sellerPrice;//卖家设置价格

    protected Double sellerFeightPrice;//卖家运费

    protected Double wishFeightPrice;//wish运费

    protected Double wishPrice;//wish售价

    protected Double originalPrice;//吊牌价格

//    private String wishTag;//wish推荐标签

//    private String customTag;//用户自定义标签

    protected List<String> wishTagList;

    protected List<String> customTagList;

    protected String hasCatTime;//行业分配时间

    protected String itemDesc;//产品描述

    protected String extraImages;//小图列表，数字存储，折号分隔^

    protected List<ItemSKU> skuList;//宝贝sku信息

    protected List<ItemEvent> itemEventList;//里程碑记录列表

    protected List<WishCategory> wishCategoryList;
    /********************************************统计数据****************************************************/

    protected Integer newSave1;//今日新增收藏量

    protected Integer newSave7;//近7日新增wish收藏量

    protected Integer newSave;//累计收藏量

    protected Integer amount7;//近7日销量

    protected Integer amount1;//今日销量

    protected Double price1;//今日销售额

    protected Double price7;//7日销售额

    protected Double price;//销售额

    protected Integer rateNum7;//近7日评论数

    protected Integer rateNum1;//今日评论数

    protected Integer amountDiff;//销售差额

    protected Double growth;//销售增长率

    protected Boolean isIsHwc;//是否是海外仓

    /********************************************其他****************************************************/

    protected Integer focus;

    protected boolean collection;

    protected String superbossNo;

    protected int collected;

    private List<Integer> historyImageIdList;

    private List<Integer> imageIdList;

    public List<Integer> getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(List<Integer> imageIdList) {
        this.imageIdList = imageIdList;
    }

    public List<Integer> getHistoryImageIdList() {
        return historyImageIdList;
    }

    public void setHistoryImageIdList(List<Integer> historyImageIdList) {
        this.historyImageIdList = historyImageIdList;
    }

    protected List<Integer> markIds;

    public Boolean getIsHwc() {
        return isIsHwc;
    }

    public void setIsHwc(Boolean isHwc) {
        isIsHwc = isHwc;
    }

    public Boolean getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(Boolean removeFlag) {
        this.removeFlag = removeFlag;
    }

    public List<WishCategory> getWishCategoryList() {
        return wishCategoryList;
    }

    public void setWishCategoryList(List<WishCategory> wishCategoryList) {
        this.wishCategoryList = wishCategoryList;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public List<String> getWishTagList() {
        return wishTagList;
    }

    public void setWishTagList(List<String> wishTagList) {
        this.wishTagList = wishTagList;
    }

    public List<String> getCustomTagList() {
        return customTagList;
    }

    public void setCustomTagList(List<String> customTagList) {
        this.customTagList = customTagList;
    }

    public List<Integer> getMarkIds() {
        return markIds;
    }

    public void setMarkIds(List<Integer> markIds) {
        this.markIds = markIds;
    }

    public int getCollected() {
        return collected;
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ItemDomain() {}

//    public ItemDomain(Item item){
//        this.itemId = item.getItemId();
//        this.itemLogoUrl = "https://contestimg.wish.com/api/webimage/"+item.getItemId()+"-small.jpg";
//        if(item.getGenTime()!=null){
//            this.genTime = DateUtil.getDate(item.getGenTime(),"yyyy-MM-dd");
//        }
//
//        this.sellerPrice = item.getSellerPrice();
//        this.sellerFeightPrice = item.getSellerFreightPrice();
//        this.wishPrice = item.getWishPrice();
//        this.wishFeightPrice = item.getWishFreightPrice();
//
//        this.amount = item.getAmount_1();//每日销量
//        this.itemName = item.getItemName();
//
//        this.customTag = item.getCustomTag();
//        this.wishTag = item.getWishTag();
//        this.alreadyRecommended = item.getAlreadyRecommendedFlag();
//        this.merchantId = item.getMerchantId();
//        this.rateScore = item.getRateScore();
//        this.wishNum = item.getWishNum();
//
//        if(item.getAmount_7()!=null){
//            this.amount7 = new BigDecimal(item.getAmount_7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//
//        }
//        if(item.getWishSave_7()!=null){
//            this.newSave7 = new BigDecimal(item.getWishSave_7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//        }
//
//    }

//    public ItemDomain(DataItemVo dataItemVo){
//        this.itemId = dataItemVo.getItemid();
//        this.itemLogoUrl = "https://contestimg.wish.com/api/webimage/"+dataItemVo.getItemid()+"-small.jpg";
//        this.itemName = dataItemVo.getItemname().replaceAll("%2C",",");;
//        this.rateScore = dataItemVo.getRate_score();
//        if(dataItemVo.getAmount_7()!=null){
//            this.amount7 = new BigDecimal(dataItemVo.getAmount_7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//        }
//        if(dataItemVo.getWish_save_7()!=null){
//            this.newSave7 = new BigDecimal(dataItemVo.getWish_save_7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//        }
//
//        this.sellerPrice = dataItemVo.getSeller_price();
//        this.wishPrice = dataItemVo.getWish_price();
//        this.sellerFeightPrice = dataItemVo.getSeller_freight_price();
//        this.wishFeightPrice = dataItemVo.getWish_freight_price();
//        this.alreadyRecommended = dataItemVo.getAlready_recommended_flag()==true?1:0;
//        if(dataItemVo.getGen_time()!=null){
//            this.genTime = DateUtil.getDate(dataItemVo.getGen_time(),"yyyy-MM-dd");
//        }
//        if(dataItemVo.getAmount_rate()!=null){
//            this.growth = new BigDecimal(dataItemVo.getAmount_rate()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//        }
//        this.rateNum = dataItemVo.getRate_num();
//        this.merchantId = dataItemVo.getMerchant_id();
//    }
//
//    public ItemDomain(Item item){
//        this.itemId = item.getItemId();
//        this.itemLogoUrl = "https://contestimg.wish.com/api/webimage/"+item.getItemId()+"-small.jpg";
//        if(item.getGenTime()!=null){
//            this.genTime = DateUtil.getDate(item.getGenTime(),"yyyy-MM-dd");
//        }
//        this.rateNum = item.getRateNum();//累计评论数(new)
//        this.sellerPrice = item.getSellerPrice();
//        this.sellerFeightPrice = item.getSellerFreightPrice();
//        this.wishPrice = item.getWishPrice();
//        this.wishFeightPrice = item.getWishFreightPrice();
//
//        this.amount = item.getAmount1();//每日销量
//        this.itemName = item.getItemName().replaceAll("%2C",",");
//
//        this.customTag = item.getCustomTag();
//        this.wishTag = item.getWishTag();
//        this.alreadyRecommended = item.getAlreadyRecommendedFlag();
//        this.merchantId = item.getMerchantId();
//        this.rateScore = item.getRateScore();
//        this.wishNum = item.getWishNum();
//        this.extraImages = item.getExtraImageList();
//        if(item.getAmount7()!=null){
//            this.amount7 = new BigDecimal(item.getAmount7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//
//        }
//        if(item.getWishSave7()!=null){
//            this.newSave7 = new BigDecimal(item.getWishSave7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//        }
//
//    }

    public Integer getRateNum() {
        return rateNum;
    }

    public void setRateNum(Integer rateNum) {
        this.rateNum = rateNum;
    }

    public String getSuperbossNo() {
        return superbossNo;
    }

    public void setSuperbossNo(String superbossNo) {
        this.superbossNo = superbossNo;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
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

    public String getItemLogoUrl() {
        return itemLogoUrl;
    }

    public void setItemLogoUrl(String itemLogoUrl) {
        this.itemLogoUrl = itemLogoUrl;
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
        this.merchantName = merchantName.replaceAll("%2C",",");
    }

    public String getCatIds() {
        return catIds;
    }

    public void setCatIds(String catIds) {
        this.catIds = catIds;
    }

    public String getCatNames() {
        return catNames;
    }

    public void setCatNames(String catNames) {
        this.catNames = catNames;
    }

    public String getOpenStoreDate() {
        return openStoreDate;
    }

    public void setOpenStoreDate(String openStoreDate) {
        this.openStoreDate = openStoreDate;
    }

    public Double getRateScore() {
        return rateScore;
    }

    public void setRateScore(Double rateScore) {
        this.rateScore = rateScore;
    }

    public Integer getAlreadyRecommended() {
        return alreadyRecommended;
    }

    public void setAlreadyRecommended(Integer alreadyRecommended) {
        this.alreadyRecommended = alreadyRecommended;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNewSave() {
        return newSave;
    }

    public void setNewSave(Integer newSave) {
        this.newSave = newSave;
    }

    public Double getSellerPrice() {
        return sellerPrice;
    }

    public void setSellerPrice(Double sellerPrice) {
        this.sellerPrice = sellerPrice;
    }

    public Double getSellerFeightPrice() {
        return sellerFeightPrice;
    }

    public void setSellerFeightPrice(Double sellerFeightPrice) {
        this.sellerFeightPrice = sellerFeightPrice;
    }

    public Double getWishFeightPrice() {
        return wishFeightPrice;
    }

    public void setWishFeightPrice(Double wishFeightPrice) {
        this.wishFeightPrice = wishFeightPrice;
    }

    public Double getWishPrice() {
        return wishPrice;
    }

    public void setWishPrice(Double wishPrice) {
        this.wishPrice = wishPrice;
    }

//    public String getWishTag() {
//        return wishTag;
//    }
//
//    public void setWishTag(String wishTag) {
//        this.wishTag = wishTag;
//    }
//
//    public String getCustomTag() {
//        return customTag;
//    }
//
//    public void setCustomTag(String customTag) {
//        this.customTag = customTag;
//    }



    public String getHasCatTime() {
        return hasCatTime;
    }

    public void setHasCatTime(String hasCatTime) {
        this.hasCatTime = hasCatTime;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public List<ItemSKU> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ItemSKU> skuList) {
        this.skuList = skuList;
    }

    public List<ItemEvent> getItemEventList() {
        return itemEventList;
    }

    public void setItemEventList(List<ItemEvent> itemEventList) {
        this.itemEventList = itemEventList;
    }

    public Integer getNewSave1() {
        return newSave1;
    }

    public void setNewSave1(Integer newSave1) {
        this.newSave1 = newSave1;
    }

    public Integer getNewSave7() {
        return newSave7;
    }

    public void setNewSave7(Integer newSave7) {
        this.newSave7 = newSave7;
    }

    public Integer getAmount7() {
        return amount7;
    }

    public void setAmount7(Integer amount7) {
        this.amount7 = amount7;
    }

    public Integer getAmount1() {
        return amount1;
    }

    public void setAmount1(Integer amount1) {
        this.amount1 = amount1;
    }

    public Double getPrice1() {
        return price1;
    }

    public void setPrice1(Double price1) {
        this.price1 = price1;
    }

    public Double getPrice7() {
        return price7;
    }

    public void setPrice7(Double price7) {
        this.price7 = price7;
    }

    public Integer getRateNum7() {
        return rateNum7;
    }

    public void setRateNum7(Integer rateNum7) {
        this.rateNum7 = rateNum7;
    }

    public Integer getRateNum1() {
        return rateNum1;
    }

    public void setRateNum1(Integer rateNum1) {
        this.rateNum1 = rateNum1;
    }

    public Integer getAmountDiff() {
        return amountDiff;
    }

    public void setAmountDiff(Integer amountDiff) {
        this.amountDiff = amountDiff;
    }

    public Double getGrowth() {
        return growth;
    }

    public void setGrowth(Double growth) {
        this.growth = growth;
    }

    public String getGenTime() {
        return genTime;
    }

    public void setGenTime(String genTime) {
        this.genTime = genTime;
    }

    public String getExtraImages() {
        return extraImages;
    }

    public void setExtraImages(String extraImages) {
        this.extraImages = extraImages;
    }
}
