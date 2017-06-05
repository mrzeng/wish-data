package com.raycloud.overseas.erp.data.common.pojo;



import java.io.Serializable;
import java.util.List;


/**
 * Created by zhanxf on 16/7/14.
 */
public class MerchantDomain implements Serializable{

    private static final long serialVersionUID = 7576278339902462315L;

    private String merchantId;//店铺id

    private String merchantNick;//店铺名称

    private String merchantName;

    private Integer rank;//店铺排名

    private Integer itemCount;//宝贝数量

    private String outRate = "0";//动销率

    private Double hotAvgPrice;//有销量产品均价

    private Double avgPrice;//总体产品均价

    private Integer authItemCount;//被认证产品数量

    private Integer hasCatItemCount;//有行业产品数量

    private Integer focus;//是否关注

    private String merchantLogoUrl;//店铺图片链接

    private String openStoreDate;//开店时间

    private Integer shopRate;//店铺评论数量

    private Double rateScore;//评价分数

    private Integer rankComp;//昨日排名

    private Double shopRateComp;//前日评分

    private String itemCountComp = "0";//昨日产品数对比

    private String outRateComp = "0";//行业水平对比

    private String hotAvgPriceComp = "0";//有销量产品均价对比

    private String avgPriceComp = "0";//全部产品均价对比

    private Integer hasCatItemCountComp;//行业水平

    private Integer authItemCountComp;//被认证产品数量

    private Integer avgAmount7;//7日日均销量

    private Double avgPrice7;//7日日均销售额

    private Integer newSave7;//7日日均收藏量

    private Integer newCount7;//7日日均上新量

    private Integer catAmountRank;//行业销量排名

    private List<Integer> markIds;

    public List<Integer> getMarkIds() {
        return markIds;
    }

    public void setMarkIds(List<Integer> markIds) {
        this.markIds = markIds;
    }

    public MerchantDomain(){}



//    public MerchantDomain(com.raycloud.overseas.data.commom.domain.wish.domain.Shop shop){
//
//        this.setAvgPrice(shop.getAvgPrice());
//        this.setHotAvgPrice(shop.getHotAvgPrice());
//        this.setAuthItemCount(shop.getWishRecommendedCount());
//        this.merchantName = shop.getMerchantName().replaceAll("%2C",",");
//        this.setHasCatItemCount(shop.getItemCatCount());
//        this.setItemCount(shop.getItemCount());
//        this.setMerchantId(shop.getMerchantId());
//        this.setMerchantNick(shop.getMerchantNick());
//        this.setRank(shop.getAmountRank());//排名
//        this.merchantLogoUrl = shop.getMerchantLogo();
//        this.rateScore = shop.getShopScore();
//        this.setShopRate(shop.getShopRate());
//        if(shop.getInsertDate() != null){
//            this.setOpenStoreDate(DateUtil.getDate(shop.getInsertDate(),"yyyy-MM-dd"));
//        }
//
//        this.setRankComp(shop.getOldAmountRank());
//
//        if(shop.getAvgPriceChainGrowth()!=null){
//            this.setAvgPriceComp(shop.getAvgPriceChainGrowth()+"");
//        }
//
//        if(shop.getHotAvgPriceChainGrowth()!=null){
//            this.setHotAvgPriceComp(shop.getHotAvgPriceChainGrowth()+"");
//        }
//
//        if(shop.getItemCountChainGrowth()!=null){
//            this.setItemCountComp(shop.getItemCountChainGrowth()+"");
//        }
//
//        if(shop.getIndustryRatio()!=null){
//            this.outRateComp = shop.getIndustryRatio()+"";
//        }
//
//        if(shop.getRatio()!=null){
//            this.outRate = shop.getRatio()+"";
//        }
//
//        /*if(shop.getHotItemCount()!=null&&shop.getItemCount()!=null&&shop.getItemCount()!=0){
//            this.setOutRate(DataUtil.sfloor(shop.getHotItemCount()*100.0/shop.getItemCount()));//动销率
//        }*/
//
//        if(shop.getAmount7()!=null){
//            this.avgAmount7 = new BigDecimal(shop.getAmount7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//        }
//
//        if(shop.getWishSave7()!=null){
//            this.newSave7 = new BigDecimal(shop.getWishSave7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
//        }
//
//        if(shop.getPrice7()!=null){
//            this.avgPrice7 = new BigDecimal(shop.getPrice7()/7).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//        }
//        this.newCount7 = shop.getNewItemCount();
//    }

    public Integer getCatAmountRank() {
        return catAmountRank;
    }

    public void setCatAmountRank(Integer catAmountRank) {
        this.catAmountRank = catAmountRank;
    }

    public Integer getAvgAmount7() {
        return avgAmount7;
    }

    public void setAvgAmount7(Integer avgAmount7) {
        this.avgAmount7 = avgAmount7;
    }

    public Double getAvgPrice7() {
        return avgPrice7;
    }

    public void setAvgPrice7(Double avgPrice7) {
        this.avgPrice7 = avgPrice7;
    }

    public Integer getNewSave7() {
        return newSave7;
    }

    public void setNewSave7(Integer newSave7) {
        this.newSave7 = newSave7;
    }

    public Integer getNewCount7() {
        return newCount7;
    }

    public void setNewCount7(Integer newCount7) {
        this.newCount7 = newCount7;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantNick() {
        return merchantNick;
    }

    public void setMerchantNick(String merchantNick) {
        this.merchantNick = merchantNick.replaceAll("%2C",",");
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getOutRate() {
        return outRate;
    }

    public void setOutRate(String outRate) {
        this.outRate = outRate;
    }

    public Double getHotAvgPrice() {
        return hotAvgPrice;
    }

    public void setHotAvgPrice(Double hotAvgPrice) {
        this.hotAvgPrice = hotAvgPrice;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Integer getAuthItemCount() {
        return authItemCount;
    }

    public void setAuthItemCount(Integer authItemCount) {
        this.authItemCount = authItemCount;
    }

    public Integer getHasCatItemCount() {
        return hasCatItemCount;
    }

    public void setHasCatItemCount(Integer hasCatItemCount) {
        this.hasCatItemCount = hasCatItemCount;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public String getMerchantLogoUrl() {
        return merchantLogoUrl;
    }

    public void setMerchantLogoUrl(String merchantLogoUrl) {
        this.merchantLogoUrl = merchantLogoUrl;
    }

    public String getOpenStoreDate() {
        return openStoreDate;
    }

    public void setOpenStoreDate(String openStoreDate) {
        this.openStoreDate = openStoreDate;
    }

    public Integer getShopRate() {
        return shopRate;
    }

    public void setShopRate(Integer shopRate) {
        this.shopRate = shopRate;
    }

    public Double getRateScore() {
        return rateScore;
    }

    public void setRateScore(Double rateScore) {
        this.rateScore = rateScore;
    }

    public Integer getRankComp() {
        return rankComp;
    }

    public void setRankComp(Integer rankComp) {
        this.rankComp = rankComp;
    }

    public Double getShopRateComp() {
        return shopRateComp;
    }

    public void setShopRateComp(Double shopRateComp) {
        this.shopRateComp = shopRateComp;
    }

    public String getItemCountComp() {
        return itemCountComp;
    }

    public void setItemCountComp(String itemCountComp) {
        this.itemCountComp = itemCountComp;
    }

    public String getOutRateComp() {
        return outRateComp;
    }

    public void setOutRateComp(String outRateComp) {
        this.outRateComp = outRateComp;
    }

    public String getHotAvgPriceComp() {
        return hotAvgPriceComp;
    }

    public void setHotAvgPriceComp(String hotAvgPriceComp) {
        this.hotAvgPriceComp = hotAvgPriceComp;
    }

    public String getAvgPriceComp() {
        return avgPriceComp;
    }

    public void setAvgPriceComp(String avgPriceComp) {
        this.avgPriceComp = avgPriceComp;
    }

    public Integer getHasCatItemCountComp() {
        return hasCatItemCountComp;
    }

    public void setHasCatItemCountComp(Integer hasCatItemCountComp) {
        this.hasCatItemCountComp = hasCatItemCountComp;
    }

    public Integer getAuthItemCountComp() {
        return authItemCountComp;
    }

    public void setAuthItemCountComp(Integer authItemCountComp) {
        this.authItemCountComp = authItemCountComp;
    }
}
