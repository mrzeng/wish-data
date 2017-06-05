package com.raycloud.overseas.erp.data.search.core;

import com.alibaba.asyncload.AsyncLoadConfig;
import com.alibaba.asyncload.AsyncLoadExecutor;
import com.alibaba.asyncload.impl.AsyncLoadEnhanceProxy;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.search.common.SolrConnFactory;
import com.raycloud.overseas.erp.data.search.common.SolrWishItemField;
import com.raycloud.overseas.erp.data.search.query.AbstractSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.task.WishItemTask;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 *  @Description 搜索服务类
 *  数据流形式
 *  (SearchListRequest=>
 *  (解析成solr查询参数)SolrMerchantQueryCondition=>
 *  获取solr Response=>获取分页对象ListReponse=>返回
 *  @Author zhanxf
 *  @Date 16/12/28 14:29
 */
@Service
public class WishItemSolrService extends AbstractSolrService{

    private static Logger logger= Logger.getLogger(WishItemSolrService.class);


    @Resource
    private AsyncLoadExecutor asyncLoadExecutor;

    @Resource
    private AsyncLoadConfig asyncLoadConfig;

    @Autowired
    private SolrConnFactory solrConnFactory;

    public WishItemSolrService() {
    }

    @Override
    public SolrConnFactory getSolrConnFactory() {
        return solrConnFactory;
    }

    /**
     * 将solr文档解析成对象
     *
     * @param queryResponse
     * @param abstractSolrQuery
     * @return
     */
    @Override
    public List parseSolrDocToObj(QueryResponse queryResponse, AbstractSolrQuery abstractSolrQuery) {
        String[] filterField = abstractSolrQuery.getFilterField();
        Iterator<SolrDocument> documentIterator = queryResponse.getResults().iterator();
        List list = new ArrayList();
        if(filterField == null || filterField.length != 1){
            while (documentIterator.hasNext()){
                SolrDocument solrDocument = documentIterator.next();
                ItemDomain itemDomain = new ItemDomain();
                itemDomain.setItemId((String) solrDocument.get(SolrWishItemField.itemid.toString()));
                itemDomain.setItemLogoUrl(DataConstant.WISH_IMAGE_SERVER_DOMAIN + solrDocument.get(SolrWishItemField.itemid.toString())+"-small.jpg");
                itemDomain.setSellerPrice( (Double) solrDocument.get(SolrWishItemField.seller_price.toString()));
                itemDomain.setWishPrice ( (Double) solrDocument.get(SolrWishItemField.wish_price.toString()));
                itemDomain.setSellerFeightPrice((Double) solrDocument.get(SolrWishItemField.seller_freight_price.toString()));
                itemDomain.setWishFeightPrice((Double) solrDocument.get(SolrWishItemField.wish_freight_price.toString()));
                itemDomain.setRateScore((Double) solrDocument.get(SolrWishItemField.rate_score.toString()));
                itemDomain.setOriginalPrice((Double) solrDocument.get(SolrWishItemField.original_price.toString()));
                itemDomain.setAmount((Integer) solrDocument.get(SolrWishItemField.offer.toString()));
                itemDomain.setNewSave((Integer) solrDocument.get(SolrWishItemField.wish_num.toString()));
                itemDomain.setRemoveFlag((Boolean) solrDocument.get(SolrWishItemField.removed_flag.toString()));
                itemDomain.setRateNum((Integer) solrDocument.get(SolrWishItemField.rate_num.toString()));
                itemDomain.setMerchantId((String) solrDocument.get(SolrWishItemField.merchant_id.toString()));
                itemDomain.setAlreadyRecommended ((Boolean)solrDocument.get(SolrWishItemField.already_recommended_flag.toString()) == true?1:0);
                itemDomain.setCatIds((String) solrDocument.get(SolrWishItemField.cat_ids.toString()));
//                itemDomain.setIsHwc((String) solrDocument.get(SolrWishItemField.is_hwc.toString()));
                String itemName = (String) solrDocument.get(SolrWishItemField.itemname.toString());
                if(itemName!=null){
                    itemDomain.setItemName(itemName.replaceAll("%2C",","));
                }

                Integer amount7 = (Integer) solrDocument.get(SolrWishItemField.amount_7.toString());
                if(amount7!=null){
                    itemDomain.setAmount7(new BigDecimal(amount7*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
                }

                Integer wishSave7 = (Integer) solrDocument.get(SolrWishItemField.wish_save_7.toString());
                if(wishSave7!=null){
                    itemDomain.setNewSave7(new BigDecimal(wishSave7*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
                }

                Integer rateNum7 = (Integer) solrDocument.get(SolrWishItemField.rate_num_7.toString());
                if(rateNum7!=null){
                    itemDomain.setRateNum7(new BigDecimal(rateNum7*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
                }

                Date genTime = (Date) solrDocument.get(SolrWishItemField.gen_time.toString());
                if(genTime!=null){
//                    calendar.setTime(genTime);
//                    calendar.add(Calendar.HOUR,-8);
                    itemDomain.setGenTime(DateUtil.getDate(genTime,"yyyy-MM-dd HH:mm:ss"));
                }

                Double amountRate = (Double) solrDocument.get(SolrWishItemField.amount_rate.toString());
                if(amountRate!=null){
                    itemDomain.setGrowth(new BigDecimal(amountRate*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                }

                list.add(itemDomain);
            }

            return list;
        }else{
            while (documentIterator.hasNext()){
                SolrDocument solrDocument = documentIterator.next();
                list.add(solrDocument.get(filterField[0]));
            }
            return list;
        }
    }

    /**
     * @param abstractSolrQuery
     * @return
     */
    @Override
    public Result blockQueryDocs(AbstractSolrQuery abstractSolrQuery) {
        Long startL = System.currentTimeMillis();
        WishItemSolrQuery wishItemSolrQuery = (WishItemSolrQuery) abstractSolrQuery;
        Collection<String> idCollection = wishItemSolrQuery.getItemIdCollection();
        if(idCollection==null || (idCollection!=null && idCollection.size()<=MAX_BLOCAK_SIZE)){
            return queryDocsWishQuery(abstractSolrQuery);
        }

        List<List> idBlocks = DataUtil.blockLargeCollection(idCollection,MAX_BLOCAK_SIZE);
        int start = wishItemSolrQuery.getStart();
        int rows = wishItemSolrQuery.getRows();
        long total = 0;
        List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>(rows*idBlocks.size());
        List<Result> resultList = new ArrayList<Result>();

        for (List<String> block_i:idBlocks) {
            WishItemSolrQuery blockQuery = new WishItemSolrQuery();
            BeanUtils.copyProperties(wishItemSolrQuery,blockQuery);
            blockQuery.setStart(0);
            blockQuery.setRows(rows+start);
            blockQuery.setItemIdCollection(block_i);
            WishItemTask wishItemTask = new WishItemTask(this,blockQuery);

            AsyncLoadEnhanceProxy<WishItemTask> asyncLoadEnhanceProxy = new
                    AsyncLoadEnhanceProxy<WishItemTask>();
            asyncLoadEnhanceProxy.setExecutor(asyncLoadExecutor);
            asyncLoadEnhanceProxy.setConfig(asyncLoadConfig);
            asyncLoadEnhanceProxy.setService(wishItemTask);
            WishItemTask proxy = asyncLoadEnhanceProxy.getProxy();

            Result result = proxy.queryDocs();
            resultList.add(result);
        }

        for(Result result : resultList){
            total += result.getTotal();
            itemDomainList.addAll(result.getItems());
        }
        logger.biz("分片搜索,片块大小:{},块数:{},耗时{}ms",idBlocks.get(0).size(),idBlocks.size(),System.currentTimeMillis()-startL);
        //合并各块查询结果并进行排序
        DataUtil.sortItemDomain(itemDomainList, TraceOrOrder.getEnumFromString(wishItemSolrQuery.getSortField()),wishItemSolrQuery.getOrder());

        List<ItemDomain> list = itemDomainList.subList(start,Math.min(rows+start,itemDomainList.size()));

        Result result = new Result();
        result.setItems(list);
        result.setTotal(total);
        result.setPageNo(start/rows+1);
        result.setPageSize(rows);
        return result;
    }

    /**
     * @param abstractSolrQuery
     * @return
     */
    @Override
    public Result blockQueryDocs2(AbstractSolrQuery abstractSolrQuery) {
        Long startL = System.currentTimeMillis();
        WishItemSolrQuery wishItemSolrQuery = (WishItemSolrQuery) abstractSolrQuery;
        Collection<String> idCollection = wishItemSolrQuery.getMerchantIdCollection();
        if(idCollection==null || (idCollection!=null && idCollection.size()<=MAX_BLOCAK_SIZE)){
//            logger.biz("单片搜索,块size:{},耗时{}ms",idCollection.size(),System.currentTimeMillis()-startL);
            return queryDocsWishQuery(abstractSolrQuery);
        }

//        List<List<String>> idBlocks = new ArrayList<List<String>>();
//        int idx = 0;
//        List<String> block = null;
//        Iterator<String> it = idCollection.iterator();
//        while (it.hasNext()){
//            if(idx% caculateBlockSize(idCollection.size()) == 0){
//
//                block = new ArrayList<String>(Math.min(MAX_BLOCAK_SIZE,idCollection.size()));
//                idBlocks.add(block);
//            }
//            block.add(it.next());
//            idx++;
//        }
        List<List> idBlocks = DataUtil.blockLargeCollection(idCollection,MAX_BLOCAK_SIZE);

        int start = wishItemSolrQuery.getStart();
        int rows = wishItemSolrQuery.getRows();
        long total = 0;
        List<ItemDomain> itemDomainList = new ArrayList<ItemDomain>(rows*idBlocks.size());
        List<Result> resultList = new ArrayList<Result>();

        for (List<String> block_i:idBlocks) {
            WishItemSolrQuery blockQuery = new WishItemSolrQuery();
            BeanUtils.copyProperties(wishItemSolrQuery,blockQuery);
            blockQuery.setStart(0);
            blockQuery.setRows(rows+start);
            blockQuery.setMerchantIdCollection(block_i);
            WishItemTask wishItemTask = new WishItemTask(this,blockQuery);

            AsyncLoadEnhanceProxy<WishItemTask> asyncLoadEnhanceProxy = new
                    AsyncLoadEnhanceProxy<WishItemTask>();
            asyncLoadEnhanceProxy.setExecutor(asyncLoadExecutor);
            asyncLoadEnhanceProxy.setConfig(asyncLoadConfig);
            asyncLoadEnhanceProxy.setService(wishItemTask);
            WishItemTask proxy = asyncLoadEnhanceProxy.getProxy();

            Result result = proxy.queryDocs();
            resultList.add(result);
        }

        for(Result result : resultList){
            total += result.getTotal();
            itemDomainList.addAll(result.getItems());
        }
        logger.biz("分片搜索,片块大小:{},块数:{},耗时{}ms",idBlocks.get(0).size(),idBlocks.size(),System.currentTimeMillis()-startL);
        //合并各块查询结果并进行排序
        DataUtil.sortItemDomain(itemDomainList, TraceOrOrder.getEnumFromString(wishItemSolrQuery.getSortField()),wishItemSolrQuery.getOrder());

        List<ItemDomain> list = itemDomainList.subList(start,Math.min(rows+start,itemDomainList.size()));

        Result result = new Result();
        result.setItems(list);
        result.setTotal(total);
        result.setPageNo(start/rows+1);
        result.setPageSize(rows);
        return result;
    }
}
