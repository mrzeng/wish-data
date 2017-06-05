package com.raycloud.overseas.erp.data.search.core;

import com.alibaba.asyncload.AsyncLoadConfig;
import com.alibaba.asyncload.AsyncLoadExecutor;
import com.alibaba.asyncload.impl.AsyncLoadEnhanceProxy;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.common.pojo.MerchantDomain;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.search.common.SolrConnFactory;
import com.raycloud.overseas.erp.data.search.common.SolrWishMerchantField;
import com.raycloud.overseas.erp.data.search.query.AbstractSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.search.task.WishItemTask;
import com.raycloud.overseas.erp.data.search.task.WishMerchantTask;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
public class WishMerchantSolrService extends AbstractSolrService{

    private static Logger logger= Logger.getLogger(WishMerchantSolrService.class);

    @Resource
    private AsyncLoadExecutor asyncLoadExecutor;

    @Resource
    private AsyncLoadConfig asyncLoadConfig;

    @Autowired
    private SolrConnFactory solrConnFactory;

    public WishMerchantSolrService() {
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
                MerchantDomain merchantDomain = new MerchantDomain();
                merchantDomain.setMerchantId((String) solrDocument.get(SolrWishMerchantField.merchant_id.toString()));
                merchantDomain.setMerchantLogoUrl((String) solrDocument.get(SolrWishMerchantField.merchant_logo.toString()));
                merchantDomain.setItemCount( (Integer) solrDocument.get(SolrWishMerchantField.item_count.toString()));

                String merchantNick = (String) solrDocument.get(SolrWishMerchantField.merchant_nick.toString());
                if(merchantNick!=null){
                    merchantDomain.setMerchantNick(merchantNick.replaceAll("%2C",","));
                }

                Integer amount7 = (Integer) solrDocument.get(SolrWishMerchantField.amount.toString());
                if(amount7!=null){
                    merchantDomain.setAvgAmount7(new BigDecimal(amount7*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
                }

                Integer newItemCount7 = (Integer) solrDocument.get(SolrWishMerchantField.new_item_count.toString());
                if(newItemCount7!=null){
                    merchantDomain.setNewCount7(new BigDecimal(newItemCount7*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
                }

                Integer wishSave7 = (Integer) solrDocument.get(SolrWishMerchantField.wish_save.toString());
                if(wishSave7!=null){
                    merchantDomain.setNewSave7(new BigDecimal(wishSave7*1.0/7).setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
                }

                Double price7 = (Double) solrDocument.get(SolrWishMerchantField.price.toString());
                if(price7!=null){
                    merchantDomain.setAvgPrice7(new BigDecimal(price7*1.0/7).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                }

                list.add(merchantDomain);
            }

            return list;
        }else {
            while (documentIterator.hasNext()){
                SolrDocument solrDocument = documentIterator.next();
                list.add(solrDocument.get(filterField[0]));
            }
        }
        return list;
    }

    /**
     * @param abstractSolrQuery
     * @return
     */
    @Override
    public Result blockQueryDocs(AbstractSolrQuery abstractSolrQuery) {
        Long startL = System.currentTimeMillis();
        WishMerchantSolrQuery wishMerchantSolrQuery = (WishMerchantSolrQuery) abstractSolrQuery;
        Collection<String> idCollection = wishMerchantSolrQuery.getMerchantIdCollection();
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
        int start = wishMerchantSolrQuery.getStart();
        int rows = wishMerchantSolrQuery.getRows();
        long total = 0;
        List<MerchantDomain> domainList = new ArrayList<MerchantDomain>(rows*idBlocks.size());
        List<Result> resultList = new ArrayList<Result>();

        for (List<String> block_i:idBlocks) {
            WishMerchantSolrQuery blockQuery = new WishMerchantSolrQuery();
            BeanUtils.copyProperties(wishMerchantSolrQuery,blockQuery);
            blockQuery.setStart(0);
            blockQuery.setRows(rows+start);
            blockQuery.setMerchantIdCollection(block_i);
            WishMerchantTask wishMerchantTask = new WishMerchantTask(this,blockQuery);

            AsyncLoadEnhanceProxy<WishMerchantTask> asyncLoadEnhanceProxy = new
                    AsyncLoadEnhanceProxy<WishMerchantTask>();
            asyncLoadEnhanceProxy.setExecutor(asyncLoadExecutor);
            asyncLoadEnhanceProxy.setConfig(asyncLoadConfig);
            asyncLoadEnhanceProxy.setService(wishMerchantTask);
            WishMerchantTask proxy = asyncLoadEnhanceProxy.getProxy();

            Result result = proxy.queryDocs();
            resultList.add(result);
        }

        for(Result result : resultList){
            total += result.getTotal();
            domainList.addAll(result.getItems());
        }
        logger.biz("分片搜索,片块大小:{},块数:{},耗时{}ms",idBlocks.get(0).size(),idBlocks.size(),System.currentTimeMillis()-startL);

        List<MerchantDomain> list = domainList.subList(start,Math.min(rows+start,domainList.size()));

        Result result = new Result();
        result.setItems(list);
        result.setTotal(total);
        result.setPageNo(start/rows+1);
        result.setPageSize(rows);
        return result;
    }
}
