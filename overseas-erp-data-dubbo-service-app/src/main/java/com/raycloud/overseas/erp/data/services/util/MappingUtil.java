package com.raycloud.overseas.erp.data.services.util;

import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.commom.domain.wish.domain.ItemMilestone;
import com.raycloud.overseas.data.commom.domain.wish.domain.Shop;
import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.common.util.DateUtil;

import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.domain.GuessItem;
import com.raycloud.overseas.erp.data.domain.GuessMerchant;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.ItemEvent;
import com.raycloud.overseas.erp.data.request.SearchListRequest;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.vo.MerchantEvent;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 对象转换映射
 * Created by zhanxf on 16/12/28.
 */
public class MappingUtil {


    /**
     * SearchListRequest TO SolrItem
     * @param request
     * @return
     */
    public static WishMerchantSolrQuery mappingWishMerchantSQ(SearchListRequest request){

        WishMerchantSolrQuery wishMerchantSolrQuery = new WishMerchantSolrQuery();

        wishMerchantSolrQuery.setOrder(request.getSortType());

        wishMerchantSolrQuery.setMerchantId(request.getMerchantId());

        wishMerchantSolrQuery.setSearchKey(request.getMerchantName());

        wishMerchantSolrQuery.setSearchKey(request.getSearchKey());

        wishMerchantSolrQuery.setMerchantIdCollection(request.getMerchantIdList());

        wishMerchantSolrQuery.setCatId(request.getCatId());

        if(request.getTraceOrOrder()!=null){
            if(request.getTraceOrOrder()== TraceOrOrder.amount_7){
                wishMerchantSolrQuery.setSortField("amount");
            }else if(request.getTraceOrOrder() == TraceOrOrder.price_7){
                wishMerchantSolrQuery.setSortField("price");
            }else if(request.getTraceOrOrder() == TraceOrOrder.wish_save_7){
                wishMerchantSolrQuery.setSortField("wish_save");
            }else if(request.getTraceOrOrder() == TraceOrOrder.item_count){
                wishMerchantSolrQuery.setSortField("item_count");
            }else if(request.getTraceOrOrder() == TraceOrOrder.new_item_count){
                wishMerchantSolrQuery.setSortField("new_item_count");
            }

        }

        wishMerchantSolrQuery.setStart((request.getPageNo()-1)*request.getPageSize());

        wishMerchantSolrQuery.setRows(request.getPageSize());

        if(request.getMinAmount7()!=null){
            wishMerchantSolrQuery.setAmount7Start((int)((request.getMinAmount7()-0.5)*7)+1);
        }
        if(request.getMaxAmount7()!=null){
            wishMerchantSolrQuery.setAmount7End((int) ((request.getMaxAmount7()+0.5)*7));
        }
        if(request.getMinSave()!=null){
            wishMerchantSolrQuery.setWishSave7Start((int)((request.getMinSave()-0.5)*7)+1);
        }
        if(request.getMaxSave()!=null){
            wishMerchantSolrQuery.setWishSave7End((int) ((request.getMaxSave()+0.5)*7));
        }

        return wishMerchantSolrQuery;
    }

    /**
     *
     * @param request
     * @return
     */
    public static WishItemSolrQuery mappingWishItemSQ(SearchListRequest request){
        WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
        String order = request.getTraceOrOrder()!=null?request.getTraceOrOrder().toString():null;
        wishItemSolrQuery.setOrder(request.getSortType());
        wishItemSolrQuery.setSortField(order);
        wishItemSolrQuery.setSearchKey(request.getItemName());
        wishItemSolrQuery.setItemId(request.getItemId());
        wishItemSolrQuery.setStart((request.getPageNo()-1)*request.getPageSize());
        wishItemSolrQuery.setRows(request.getPageSize());
        wishItemSolrQuery.setMerchantId(request.getMerchantId());

        wishItemSolrQuery.setRateNumStart(request.getMinRateNum());
        wishItemSolrQuery.setRateNumEnd(request.getMaxRateNum());
        wishItemSolrQuery.setSellerPriceStart(request.getMinWishPrice());
        wishItemSolrQuery.setSellerPriceEnd(request.getMaxWishPrice());

        wishItemSolrQuery.setItemIdCollection(request.getItemIdList());
        wishItemSolrQuery.setMerchantIdCollection(request.getMerchantIdList());
        wishItemSolrQuery.setSearchKey(request.getSearchKey());
        wishItemSolrQuery.setCatId(request.getCatId());
        if(request.getMinAmount7()!=null){
            wishItemSolrQuery.setAmount7Start((int)((request.getMinAmount7()-0.5)*7)+1);
        }
        if(request.getMaxAmount7()!=null){
            wishItemSolrQuery.setAmount7End((int) ((request.getMaxAmount7()+0.5)*7));
        }
        if(request.getMinSave()!=null){
            wishItemSolrQuery.setWishSave7Start((int)((request.getMinSave()-0.5)*7)+1);
        }
        if(request.getMaxSave()!=null){
            wishItemSolrQuery.setWishSave7End((int) ((request.getMaxSave()+0.5)*7));
        }

        Calendar calendar = Calendar.getInstance();
        if(!StringUtils.isEmpty(request.getStartSellTime())){
            Date genTimeStart = DateUtil.getDate(request.getStartSellTime());
            calendar.setTime(genTimeStart);
            calendar.add(Calendar.HOUR,-8);
            wishItemSolrQuery.setGenTimeStart(calendar.getTime());
        }

        if(!StringUtils.isEmpty(request.getEndSellTime())){
            Date genTimeEnd = DateUtil.getDate(request.getEndSellTime());
            calendar.setTime(genTimeEnd);
            calendar.add(Calendar.HOUR,-8);
            wishItemSolrQuery.setGenTimeEnd(calendar.getTime());
        }


        if(request.getEventType()!=null && request.getEventType().equals("allDistributeCat")){
            wishItemSolrQuery.setMerchantId(request.getEventMerchantId());
        }

        return wishItemSolrQuery;
    }

    public static ItemDomain mappingItemDomain(Item item){
        ItemDomain itemDomain = new ItemDomain();
        itemDomain.setItemId(item.getItemId());
        itemDomain.setItemLogoUrl("http://contestimg.wish.com/api/webimage/"+item.getItemId()+"-small.jpg");
        if(item.getGenTime()!=null){
            itemDomain.setGenTime(DateUtil.getDate(item.getGenTime(),"yyyy-MM-dd"));
        }
        itemDomain.setRateNum(item.getRateNum());//累计评论数(new)
        itemDomain.setSellerPrice(item.getSellerPrice());
        itemDomain.setSellerFeightPrice(item.getSellerFreightPrice());
        itemDomain.setWishPrice(item.getWishPrice());
        itemDomain.setWishFeightPrice(item.getWishFreightPrice());

        itemDomain.setAmount(item.getAmount1());//每日销量
        itemDomain.setItemName(item.getItemName().replaceAll("%2C",","));
        itemDomain.setAmount(item.getOffer());
        itemDomain.setAlreadyRecommended(item.getAlreadyRecommendedFlag());
        itemDomain.setMerchantId(item.getMerchantId());
        itemDomain.setRateScore(item.getRateScore());
        itemDomain.setNewSave(item.getWishNum());
        itemDomain.setExtraImages(item.getExtraImageList());
        itemDomain.setCatIds(item.getCatIds());
        if(item.getAmount7()!=null){
            itemDomain.setAmount7(new BigDecimal(item.getAmount7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());

        }
        if(item.getWishSave7()!=null){
            itemDomain.setNewSave7(new BigDecimal(item.getWishSave7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
        }

        if(item.getRateNum7()!=null){
            itemDomain.setRateNum7(new BigDecimal(item.getRateNum7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
        }

        itemDomain.setOriginalPrice(item.getOriginalPrice());
        if(item.getIsHwc()!=null && item.getIsHwc().intValue()==1){
            itemDomain.setIsHwc(true);
        }else{
            itemDomain.setIsHwc(false);
        }
        itemDomain.setInsertDate(item.getInsertDate());
        return itemDomain;
    }

    public static ItemEvent mappingItemEvent(ItemMilestone mark) {
        ItemEvent itemEvent = new ItemEvent();
        if(mark.getInsertDate()!=null){
            itemEvent.setTime(new SimpleDateFormat("yyyy-MM-dd").format(mark.getInsertDate()));
        }
        if(mark.getType()!=null){
            if(mark.getType().equals("wishRecommendPass")){
                itemEvent.setType("authGet");
            }else if(mark.getType().equals("wishRecommendCancel")){
                itemEvent.setType("authCancle");
            }else{
                itemEvent.setType(mark.getType());
            }
        }
        return itemEvent;
    }

    public static MerchantDomain mappingMerchantDomain(com.raycloud.overseas.data.commom.domain.wish.domain.Shop shop){
        MerchantDomain merchantDomain = new MerchantDomain();
        merchantDomain.setAvgPrice(shop.getAvgPrice());
        merchantDomain.setHotAvgPrice(shop.getHotAvgPrice());
        merchantDomain.setAuthItemCount(shop.getWishRecommendedCount());
        merchantDomain.setMerchantName(shop.getMerchantName().replaceAll("%2C",","));
        merchantDomain.setHasCatItemCount(shop.getItemCatCount());
        merchantDomain.setItemCount(shop.getItemCount());
        merchantDomain.setMerchantId(shop.getMerchantId());
        merchantDomain.setMerchantNick(shop.getMerchantNick());
        merchantDomain.setRank(shop.getAmountRank());//排名
        merchantDomain.setMerchantLogoUrl(shop.getMerchantLogo());
        merchantDomain.setRateScore(shop.getShopScore());
        merchantDomain.setShopRate(shop.getShopRate());
        if(shop.getInsertDate() != null){
            merchantDomain.setOpenStoreDate(DateUtil.getDate(shop.getInsertDate(),"yyyy-MM-dd"));
        }

        merchantDomain.setRankComp(shop.getOldAmountRank());

        if(shop.getAvgPriceChainGrowth()!=null){
            merchantDomain.setAvgPriceComp(shop.getAvgPriceChainGrowth()+"");
        }

        if(shop.getHotAvgPriceChainGrowth()!=null){
            merchantDomain.setHotAvgPriceComp(shop.getHotAvgPriceChainGrowth()+"");
        }

        if(shop.getItemCountChainGrowth()!=null){
            merchantDomain.setItemCountComp(shop.getItemCountChainGrowth()+"");
        }

        if(shop.getIndustryRatio()!=null){
            merchantDomain.setOutRateComp(shop.getIndustryRatio()+"");
        }

        if(shop.getRatio()!=null){
            merchantDomain.setOutRate(shop.getRatio()+"");
        }

        /*if(shop.getHotItemCount()!=null&&shop.getItemCount()!=null&&shop.getItemCount()!=0){
            this.setOutRate(DataUtil.sfloor(shop.getHotItemCount()*100.0/shop.getItemCount()));//动销率
        }*/

        if(shop.getAmount7()!=null){
            merchantDomain.setAvgAmount7(new BigDecimal(shop.getAmount7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
        }

        if(shop.getWishSave7()!=null){
            merchantDomain.setNewSave7(new BigDecimal(shop.getWishSave7()*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
        }

        if(shop.getPrice7()!=null){
            merchantDomain.setAvgPrice7(new BigDecimal(shop.getPrice7()/7).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        merchantDomain.setNewCount7(shop.getNewItemCount());
        return merchantDomain;
    }

    public static GuessItem mappingGuessItem(Item item){
        GuessItem guessItem = new GuessItem();
        guessItem.setItemId(item.getItemId());
        guessItem.setItemUrl("http://contestimg.wish.com/api/webimage/"+item.getItemId()+"-middle.jpg");
        guessItem.setAmount7(item.getAmount7());
        guessItem.setPrice(item.getWishPrice()+item.getWishFreightPrice());
        return guessItem;
    }

    public static GuessItem mappingGuessItem(ItemDomain item){
        GuessItem guessItem = new GuessItem();
        guessItem.setItemId(item.getItemId());
        guessItem.setItemUrl("http://contestimg.wish.com/api/webimage/"+item.getItemId()+"-middle.jpg");
        guessItem.setAmount7(item.getAmount7());
        guessItem.setPrice(item.getWishPrice()+item.getWishFeightPrice());
        guessItem.setCatNames(item.getCatNames());
        guessItem.setWishFeightPrice(item.getWishFeightPrice());
        return guessItem;
    }

    public static GuessMerchant mappingGuessMerchant(Shop shop){
        GuessMerchant guessMerchant = new GuessMerchant();
        guessMerchant.setMerchantId(shop.getMerchantId());
        guessMerchant.setMerchantUrl(shop.getMerchantLogo());
        guessMerchant.setAmount7(shop.getAmount());
        guessMerchant.setItemCount(shop.getItemCount());
        guessMerchant.setMerchantName(shop.getMerchantNick());
        return guessMerchant;
    }

    public static GuessMerchant mappingGuessMerchant(MerchantDomain shop){
        GuessMerchant guessMerchant = new GuessMerchant();
        guessMerchant.setMerchantId(shop.getMerchantId());
        guessMerchant.setMerchantUrl(shop.getMerchantLogoUrl());
        guessMerchant.setAmount7(new BigDecimal(shop.getAvgAmount7()).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
        guessMerchant.setItemCount(shop.getItemCount());
        guessMerchant.setMerchantName(shop.getMerchantNick());
        return guessMerchant;
    }

    public static MerchantEvent mappingMerchantEvent(Shop merchantMark) {
        MerchantEvent merchantEvent = new MerchantEvent();
        if(merchantMark.getInsertDate()!=null){
            merchantEvent.setTime(DateUtil.getDate(merchantMark.getInsertDate(),"yyyy-MM-dd"));
        }
        if(merchantMark.getNewItemCount()!=null &&merchantMark.getNewItemCount()!=0 ){
            merchantEvent.setOnSale(merchantMark.getNewItemCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getRemovedCount()!=null&&merchantMark.getRemovedCount()!=0){
            merchantEvent.setRemove(merchantMark.getRemovedCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getChangeCatCount()!=null&&merchantMark.getChangeCatCount()!=0 ){
            merchantEvent.setDistributeCat(merchantMark.getChangeCatCount());
            merchantEvent.setFilter(false);
        }
        if( merchantMark.getChangeRefer1T0Count()!=null&& merchantMark.getChangeRefer1T0Count()!=0 ){
            merchantEvent.setAuthCancle(merchantMark.getChangeRefer1T0Count());
            merchantEvent.setFilter(false);
        }

        if( merchantMark.getChangeRefer0T1Count()!=null&& merchantMark.getChangeRefer0T1Count()!=0 ){
            merchantEvent.setAuthGet(merchantMark.getChangeRefer0T1Count());
            merchantEvent.setFilter(false);
        }

        if(merchantMark.getChangePriceCount()!=null&&merchantMark.getChangePriceCount()!=0 ){
            merchantEvent.setChangeWishPrice(merchantMark.getChangePriceCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getChangePrice1Count()!=null&&merchantMark.getChangePrice1Count()!=0 ){
            merchantEvent.setChangeSellerPrice(merchantMark.getChangePrice1Count());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getChangeMsrpCount()!=null&&merchantMark.getChangeMsrpCount()!=0 ){
            merchantEvent.setChangeOriginalPrice(merchantMark.getChangeMsrpCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getAmountCount()!=null&&merchantMark.getAmountCount()!=0 ){
            merchantEvent.setFirstSale(merchantMark.getAmountCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getSaveCount()!=null&&merchantMark.getSaveCount()!=0 ){
            merchantEvent.setFirstWishSave(merchantMark.getSaveCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getChangeTagCount()!=null&&merchantMark.getChangeTagCount()!=0 ){
            merchantEvent.setChangeWishTag(merchantMark.getChangeTagCount());
            merchantEvent.setFilter(false);
        }if(merchantMark.getChangeTag1Count()!=null&&merchantMark.getChangeTag1Count()!=0 ){
            merchantEvent.setChangeCustomerTag(merchantMark.getChangeTag1Count());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getBadRateCount()!=null&&merchantMark.getBadRateCount()!=0 ){
            merchantEvent.setFirstBadRate(merchantMark.getBadRateCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getGoodRateCount()!=null&&merchantMark.getGoodRateCount()!=0 ){
            merchantEvent.setFirstGoodRate(merchantMark.getGoodRateCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getDeletedCount()!=null&&merchantMark.getDeletedCount()!=0 ){
            merchantEvent.setDeleteItem(merchantMark.getDeletedCount());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getChangeAudit0T1Count()!=null&&merchantMark.getChangeAudit0T1Count()!=0 ){
            merchantEvent.setAudit(merchantMark.getChangeAudit0T1Count());
            merchantEvent.setFilter(false);
        }
        if(merchantMark.getChangeAudit1T0Count()!=null&&merchantMark.getChangeAudit1T0Count()!=0 ){
            merchantEvent.setAuditPass( merchantMark.getChangeAudit1T0Count());
            merchantEvent.setFilter(false);
        }
        return merchantEvent;
    }


}
