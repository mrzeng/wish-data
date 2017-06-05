package com.raycloud.overseas.erp.data.search.query;



import com.raycloud.overseas.erp.data.search.common.SolrConfig;
import org.apache.solr.client.solrj.SolrRequest;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WishMerchantSolrQuery extends AbstractSolrQuery implements Serializable {

    private static final long serialVersionUID = 2192234940823830198L;
    /**
     * basic_search
     */
    private String searchKey;

    /**
     * 店铺id
     */
    private String merchantId;

    /**
     * 店铺id集合
     */
    private Collection<String> merchantIdCollection;

    /**
     * 行业id
     */
    private String catId;

    /**
     * 7日销量
     */
    private Integer amount7Start;

    private Integer amount7End;

    /**
     * 7日收藏
     */
    private Integer wishSave7Start;

    private Integer wishSave7End;

    enum SortField{
        price,//7日销售额
        wish_save,//7日收藏量
        item_count,//7日宝贝数量
        amount//7日销量
        ;

        @Override
        public String toString() {
            if(this == null){
                return "";
            }
            return super.toString();
        }
    }

    public Collection<String> getMerchantIdCollection() {
        return merchantIdCollection;
    }

    public void setMerchantIdCollection(Collection<String> merchantIdCollection) {
        this.merchantIdCollection = merchantIdCollection;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

        if(!StringUtils.isEmpty(merchantId)){
            query.append(MessageFormat.format("{0} merchant_id:{1}",query.length()==0?"":" AND ", merchantId));
        }

        if(!StringUtils.isEmpty(catId)){
            query.append(MessageFormat.format("{0} cat_ids:{1}",query.length()==0?"":" AND ",catId));
        }

        if (wishSave7Start != null || wishSave7End != null) {
            query.append(MessageFormat.format("{0} wish_save:[{1} TO {2}]",query.length()==0?"":" AND",
                    wishSave7Start!=null?wishSave7Start.toString():"*",wishSave7End!=null?wishSave7End.toString():"*"));

        }

        if (amount7Start != null || amount7End != null) {

            query.append(MessageFormat.format("{0} amount:[{1} TO {2}]",query.length()==0?"":" AND",
                    amount7Start!=null?amount7Start.toString():"*",amount7End!=null?amount7End.toString():"*"));

        }

        if (merchantIdCollection!=null && merchantIdCollection.size()>0) {
            query.append(MessageFormat.format("{0} merchant_id:{1}",query.length()==0?"":" AND ", joinCollection(merchantIdCollection)));
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
        return SolrRequest.METHOD.GET;
    }

    @Override
    public boolean isHighLight() {
        return searchKey!=null && searchKey.length()!=24;
    }

    @Override
    public String getCoreType() {
        return SolrConfig.MERCHANT_CORE;
    }
}
