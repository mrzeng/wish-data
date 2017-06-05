package com.raycloud.overseas.erp.data.search.query;



import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.search.common.SolrConfig;
import org.apache.solr.client.solrj.SolrRequest;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WishItemSolrQuery extends AbstractSolrQuery implements Serializable {

    private static final long serialVersionUID = 518639536948423887L;

    /**
     * basic_search
     */
    private String searchKey;

    /**
     * 店铺id
     */
    private String merchantId;

    /**
     * 宝贝id
     */
    private String itemId;

    /**
     * 宝贝名称
     */
    private String itemName;

    /**
     * 行业id
     */
    private String catId;

    /**
     * 刊登价格
     */
    private Double sellerPriceStart;

    private Double sellerPriceEnd;

    /**
     * wish价格
     */
    private Double wishPriceStart;

    private Double wishPriceEnd;

    /**
     * 上架时间
     */
    private Date genTimeStart;

    private Date genTimeEnd;

    /**
     * 增长率
     */
    private Double amountRateStart;

    private Double amountRateEnd;

    /**
     *累计销量
     */
    private Integer offerStart;

    private Integer offerEnd;

    /**
     * 7日销量
     */
    private Integer amount7Start;

    private Integer amount7End;

    /**
     * 累计收藏
     */
    private Integer wishNumStart;

    private Integer wishNumEnd;

    /**
     * 7日收藏
     */
    private Integer wishSave7Start;

    private Integer wishSave7End;

    /**
     * 累计评论
     */
    private Integer rateNumStart;

    private Integer rateNumEnd;

    /**
     * 7日评论
     */
    private Integer rateNum7Start;

    private Integer rateNum7End;




    public static enum SolrField{
        seller_price ,//卖家标价
        wish_price ,//wish售价
        seller_freight_price,//刊登运费
        wish_freight_price,//wish运费
        offer ,//累计销量
        wish_num ,//累计收藏数
        rate_num ,//累计评论数
        amount_1 ,//当天销量
        price_1 ,//当天销售额
        wish_save_1 ,//当天收藏数
        rate_num_1 ,//当天评论数
        amount_7 ,//近7天销量
        price_7 ,//近7天销售额
        wish_save_7 ,//近7天收藏数
        rate_num_7 ,//近7天评论数
        new_item_count,//上新数量
        rate_score,//产品评分
        already_recommended,//是否wish认证
        amount_rate,//按销量增长率
        gen_time//上架时间
        ;
        @Override
        public String toString() {
            if(this == null){
                return "";
            }
            return super.toString();
        }
    }

    public WishItemSolrQuery() {
    }

    public WishItemSolrQuery(String sortField,String order) {
        this.sortField = sortField;
        this.order = order;
    }

    /**
     * 店铺id迭代器
     */
    private Collection<String> itemIdCollection;

    public Double getAmountRateStart() {
        return amountRateStart;
    }

    public void setAmountRateStart(Double amountRateStart) {
        this.amountRateStart = amountRateStart;
    }

    public Double getAmountRateEnd() {
        return amountRateEnd;
    }

    public void setAmountRateEnd(Double amountRateEnd) {
        this.amountRateEnd = amountRateEnd;
    }

    /**
     * 店铺id迭代器
     */
    private Collection<String> merchantIdCollection;

    public Double getWishPriceStart() {
        return wishPriceStart;
    }

    public void setWishPriceStart(Double wishPriceStart) {
        this.wishPriceStart = wishPriceStart;
    }

    public Double getWishPriceEnd() {
        return wishPriceEnd;
    }

    public void setWishPriceEnd(Double wishPriceEnd) {
        this.wishPriceEnd = wishPriceEnd;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public Double getSellerPriceStart() {
        return sellerPriceStart;
    }

    public void setSellerPriceStart(Double sellerPriceStart) {
        this.sellerPriceStart = sellerPriceStart;
    }

    public Double getSellerPriceEnd() {
        return sellerPriceEnd;
    }

    public void setSellerPriceEnd(Double sellerPriceEnd) {
        this.sellerPriceEnd = sellerPriceEnd;
    }

    public Date getGenTimeStart() {
        return genTimeStart;
    }

    public void setGenTimeStart(Date genTimeStart) {
        this.genTimeStart = genTimeStart;
    }

    public Date getGenTimeEnd() {
        return genTimeEnd;
    }

    public void setGenTimeEnd(Date genTimeEnd) {
        this.genTimeEnd = genTimeEnd;
    }

    public Integer getOfferStart() {
        return offerStart;
    }

    public void setOfferStart(Integer offerStart) {
        this.offerStart = offerStart;
    }

    public Integer getOfferEnd() {
        return offerEnd;
    }

    public void setOfferEnd(Integer offerEnd) {
        this.offerEnd = offerEnd;
    }

    public Integer getAmount7Start() {
        return amount7Start;
    }

    public void setAmount7Start(Integer amount7Start) {
        this.amount7Start = amount7Start;
    }

    public Integer getAmount7End() {
        return amount7End;
    }

    public void setAmount7End(Integer amount7End) {
        this.amount7End = amount7End;
    }

    public Integer getWishNumStart() {
        return wishNumStart;
    }

    public void setWishNumStart(Integer wishNumStart) {
        this.wishNumStart = wishNumStart;
    }

    public Integer getWishNumEnd() {
        return wishNumEnd;
    }

    public void setWishNumEnd(Integer wishNumEnd) {
        this.wishNumEnd = wishNumEnd;
    }

    public Integer getWishSave7Start() {
        return wishSave7Start;
    }

    public void setWishSave7Start(Integer wishSave7Start) {
        this.wishSave7Start = wishSave7Start;
    }

    public Integer getWishSave7End() {
        return wishSave7End;
    }

    public void setWishSave7End(Integer wishSave7End) {
        this.wishSave7End = wishSave7End;
    }

    public Integer getRateNumStart() {
        return rateNumStart;
    }

    public void setRateNumStart(Integer rateNumStart) {
        this.rateNumStart = rateNumStart;
    }

    public Integer getRateNumEnd() {
        return rateNumEnd;
    }

    public void setRateNumEnd(Integer rateNumEnd) {
        this.rateNumEnd = rateNumEnd;
    }

    public Integer getRateNum7Start() {
        return rateNum7Start;
    }

    public void setRateNum7Start(Integer rateNum7Start) {
        this.rateNum7Start = rateNum7Start;
    }

    public Integer getRateNum7End() {
        return rateNum7End;
    }

    public void setRateNum7End(Integer rateNum7End) {
        this.rateNum7End = rateNum7End;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Collection<String> getMerchantIdCollection() {
        return merchantIdCollection;
    }

    public void setMerchantIdCollection(Collection<String> merchantIdCollection) {
        this.merchantIdCollection = merchantIdCollection;
    }

    public Collection<String> getItemIdCollection() {
        return itemIdCollection;
    }

    public void setItemIdCollection(Collection<String> itemIdCollection) {
        this.itemIdCollection = itemIdCollection;
    }

    public String joinQueryParam(){
        StringBuilder query = new StringBuilder();

        if(!StringUtils.isEmpty(searchKey)){
            String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(searchKey);
            searchKey = m.replaceAll(" ").trim();
            query.append(MessageFormat.format("{0} basic_search:{1}",query.length()==0?"":" AND ", joinUrlEncode(searchKey)));
        }

        if(!StringUtils.isEmpty(itemId)){
            query.append(MessageFormat.format("{0} itemid:{1}",query.length()==0?"":" AND ", itemId));
        }

        if(!StringUtils.isEmpty(merchantId)){
            query.append(MessageFormat.format("{0} merchant_id:{1}",query.length()==0?"":" AND ", merchantId));
        }

        if(!StringUtils.isEmpty(catId)){
            query.append(MessageFormat.format("{0} cat_ids:{1}",query.length()==0?"":" AND ",catId));
        }

        if ((itemIdCollection!=null&&itemIdCollection.size()>0) && (merchantIdCollection!=null&&merchantIdCollection.size()>0)) {
            query.append(MessageFormat.format("{0} itemid:{1}",query.length()==0?"(":" AND (", joinCollection(itemIdCollection)));
            query.append(MessageFormat.format("{0} merchant_id:{1}{2}",query.length()==0?"":" OR ", joinCollection(merchantIdCollection),")"));

        }else{
            if (itemIdCollection!=null&&itemIdCollection.size()>0) {
                query.append(MessageFormat.format("{0} itemid:{1}",query.length()==0?"":" AND ", joinCollection(itemIdCollection)));
            }

            if (merchantIdCollection!=null&&merchantIdCollection.size()>0) {
                query.append(MessageFormat.format("{0} merchant_id:{1}",query.length()==0?"":" AND ", joinCollection(merchantIdCollection)));
            }
        }

        if (wishSave7Start != null || wishSave7End != null) {

            query.append(MessageFormat.format("{0} wish_save_7:[{1} TO {2}]",query.length()==0?"":" AND",
                    wishSave7Start!=null?wishSave7Start.toString():"*",wishSave7End!=null?wishSave7End.toString():"*"));
        }

        if (amount7Start != null || amount7End != null) {
            query.append(MessageFormat.format("{0} amount_7:[{1} TO {2}]",query.length()==0?"":" AND",
                    amount7Start!=null?amount7Start.toString():"*",amount7End!=null?amount7End.toString():"*"));
        }

        if (rateNumStart != null || rateNumEnd != null) {

            query.append(MessageFormat.format("{0} rate_num:[{1} TO {2}]",query.length()==0?"":" AND",
                    rateNumStart!=null?rateNumStart.toString():"*",rateNumEnd!=null?rateNumEnd.toString():"*"));
        }
        if (amountRateStart != null || amountRateEnd != null) {

            query.append(MessageFormat.format("{0} amount_rate:[{1} TO {2}]",query.length()==0?"":" AND",
                    amountRateStart!=null?amountRateStart.toString():"*",amountRateEnd!=null?amountRateEnd.toString():"*"));
        }

        if (sellerPriceStart != null || sellerPriceEnd != null) {

            query.append(MessageFormat.format("{0} seller_price:[{1} TO {2}]",query.length()==0?"":" AND",
                    sellerPriceStart!=null?sellerPriceStart:"*",sellerPriceEnd!=null?sellerPriceEnd:"*"));
        }

        if (genTimeStart != null || genTimeEnd != null) {

            query.append(MessageFormat.format("{0} gen_time:[{1} TO {2}]",query.length()==0?"":" AND",
                    joinDate(getGenTimeStart()),joinDate(genTimeEnd)));

        }

        if(query.length()==0){
            query.append("*:*");
        }
        return query.toString();
    }

    @Override
    public String toString() {
        return joinQueryParam();
    }

    @Override
    public SolrRequest.METHOD getRequestType() {
        return (itemIdCollection!=null||merchantIdCollection!=null)?SolrRequest.METHOD.POST:SolrRequest.METHOD.GET;
    }

    @Override
    public boolean isHighLight() {
        return searchKey!=null && searchKey.length()!=24;
    }

    @Override
    public String getCoreType() {
        return SolrConfig.ITEM_CORE;
    }

    public void setSortAndRowInfo(String sortField,String order,int rows){
        this.sortField=sortField;
        this.order=order;
        this.rows=rows;
    }

    public static void main(String[] args){
        System.out.println(4/100);
    }
}
