package com.raycloud.overseas.erp.data.vo;



import java.io.Serializable;

/**
 * Created by zhanxf on 16/7/14.
 */
public class MerchantEvent implements Serializable{

    private static final long serialVersionUID = -6483464669633046598L;

    private String merchantId;//店铺id

    private String time;//时间

    private Integer onSale;//上架
    private Integer distributeCat;//划分类目
    private Integer wishRecommend;//wish推荐
    private Integer firstWishSave;//第一次收藏
    private Integer firstSale;//第一次销售
    private Integer firstGoodRate;//第一次好评
    private Integer firstBadRate;//第一次差评
    private Integer remove;//下架
    private Integer changeWishPrice ;//产品页面价格
    private Integer changeSellerPrice;// 产品卖家后台价格
    private Integer changeWishTag;// 产品Wish标签
    private Integer changeCustomerTag;// 产品自定义标签
    private Integer changeOriginalPrice;//吊牌价格改动
    private Integer deleteItem;//删除产品
    private Integer audit;//被审核
    private Integer auditPass;//被审核
    private Integer authCancle;//取消认证
    private Integer authGet;//获得认证

    private boolean filter = true;
    public MerchantEvent() {
    }

//    public MerchantEvent(Shop merchantMark) {=============
//        if(merchantMark.getInsertDate()!=null){
//            time = DateUtil.getDate(merchantMark.getInsertDate(),"yyyy-MM-dd");
//        }
//        if(merchantMark.getNewItemCount()!=null &&merchantMark.getNewItemCount()!=0 ){
//            onSale = merchantMark.getNewItemCount();
//            filter = false;
//        }
//        if(merchantMark.getRemovedCount()!=null&&merchantMark.getRemovedCount()!=0){
//            remove = merchantMark.getRemovedCount();
//            filter = false;
//        }
//        if(merchantMark.getChangeCatCount()!=null&&merchantMark.getChangeCatCount()!=0 ){
//            distributeCat = merchantMark.getChangeCatCount();
//            filter = false;
//        }
//        if( merchantMark.getChangeRefer1T0Count()!=null&& merchantMark.getChangeRefer1T0Count()!=0 ){
//            authCancle = merchantMark.getChangeRefer1T0Count();
//            filter = false;
//        }
//
//        if( merchantMark.getChangeRefer0T1Count()!=null&& merchantMark.getChangeRefer0T1Count()!=0 ){
//            authGet = merchantMark.getChangeRefer0T1Count();
//            filter = false;
//        }
//
//        if(merchantMark.getChangePriceCount()!=null&&merchantMark.getChangePriceCount()!=0 ){
//            changeWishPrice = merchantMark.getChangePriceCount();
//            filter = false;
//        }
//        if(merchantMark.getChangePrice1Count()!=null&&merchantMark.getChangePrice1Count()!=0 ){
//            changeSellerPrice = merchantMark.getChangePrice1Count();
//            filter = false;
//        }
//        if(merchantMark.getChangeMsrpCount()!=null&&merchantMark.getChangeMsrpCount()!=0 ){
//            changeOriginalPrice = merchantMark.getChangeMsrpCount();
//            filter = false;
//        }
//        if(merchantMark.getAmountCount()!=null&&merchantMark.getAmountCount()!=0 ){
//            firstSale = merchantMark.getAmountCount();
//            filter = false;
//        }
//        if(merchantMark.getSaveCount()!=null&&merchantMark.getSaveCount()!=0 ){
//            firstWishSave = merchantMark.getSaveCount();
//            filter = false;
//        }
//        if(merchantMark.getChangeTagCount()!=null&&merchantMark.getChangeTagCount()!=0 ){
//            changeWishTag = merchantMark.getChangeTagCount();
//            filter = false;
//        }if(merchantMark.getChangeTag1Count()!=null&&merchantMark.getChangeTag1Count()!=0 ){
//            changeCustomerTag = merchantMark.getChangeTag1Count();
//            filter = false;
//        }
//        if(merchantMark.getBadRateCount()!=null&&merchantMark.getBadRateCount()!=0 ){
//            firstBadRate = merchantMark.getBadRateCount();
//            filter = false;
//        }
//        if(merchantMark.getGoodRateCount()!=null&&merchantMark.getGoodRateCount()!=0 ){
//           firstGoodRate = merchantMark.getGoodRateCount();
//            filter = false;
//        }
//        if(merchantMark.getDeletedCount()!=null&&merchantMark.getDeletedCount()!=0 ){
//            deleteItem = merchantMark.getDeletedCount();
//            filter = false;
//        }
//        if(merchantMark.getChangeAudit0T1Count()!=null&&merchantMark.getChangeAudit0T1Count()!=0 ){
//            audit = merchantMark.getChangeAudit0T1Count();
//            filter = false;
//        }
//        if(merchantMark.getChangeAudit1T0Count()!=null&&merchantMark.getChangeAudit1T0Count()!=0 ){
//            auditPass = merchantMark.getChangeAudit1T0Count();
//            filter = false;
//        }
//
//    }

    public Integer getAuthCancle() {
        return authCancle;
    }

    public void setAuthCancle(Integer authCancle) {
        this.authCancle = authCancle;
    }

    public Integer getAuthGet() {
        return authGet;
    }

    public void setAuthGet(Integer authGet) {
        this.authGet = authGet;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getAuditPass() {
        return auditPass;
    }

    public void setAuditPass(Integer auditPass) {
        this.auditPass = auditPass;
    }

    public Integer getDeleteItem() {
        return deleteItem;
    }

    public void setDeleteItem(Integer deleteItem) {
        this.deleteItem = deleteItem;
    }

    public Integer getChangeOriginalPrice() {
        return changeOriginalPrice;
    }

    public void setChangeOriginalPrice(Integer changeOriginalPrice) {
        this.changeOriginalPrice = changeOriginalPrice;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Integer getDistributeCat() {
        return distributeCat;
    }

    public void setDistributeCat(Integer distributeCat) {
        this.distributeCat = distributeCat;
    }

    public Integer getWishRecommend() {
        return wishRecommend;
    }

    public void setWishRecommend(Integer wishRecommend) {
        this.wishRecommend = wishRecommend;
    }

    public Integer getFirstWishSave() {
        return firstWishSave;
    }

    public void setFirstWishSave(Integer firstWishSave) {
        this.firstWishSave = firstWishSave;
    }

    public Integer getFirstSale() {
        return firstSale;
    }

    public void setFirstSale(Integer firstSale) {
        this.firstSale = firstSale;
    }

    public Integer getFirstGoodRate() {
        return firstGoodRate;
    }

    public void setFirstGoodRate(Integer firstGoodRate) {
        this.firstGoodRate = firstGoodRate;
    }

    public Integer getFirstBadRate() {
        return firstBadRate;
    }

    public void setFirstBadRate(Integer firstBadRate) {
        this.firstBadRate = firstBadRate;
    }

    public Integer getRemove() {
        return remove;
    }

    public void setRemove(Integer remove) {
        this.remove = remove;
    }

    public Integer getChangeWishPrice() {
        return changeWishPrice;
    }

    public void setChangeWishPrice(Integer changeWishPrice) {
        this.changeWishPrice = changeWishPrice;
    }

    public Integer getChangeSellerPrice() {
        return changeSellerPrice;
    }

    public void setChangeSellerPrice(Integer changeSellerPrice) {
        this.changeSellerPrice = changeSellerPrice;
    }

    public Integer getChangeWishTag() {
        return changeWishTag;
    }

    public void setChangeWishTag(Integer changeWishTag) {
        this.changeWishTag = changeWishTag;
    }

    public Integer getChangeCustomerTag() {
        return changeCustomerTag;
    }

    public void setChangeCustomerTag(Integer changeCustomerTag) {
        this.changeCustomerTag = changeCustomerTag;
    }
}
