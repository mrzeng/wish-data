package com.raycloud.overseas.erp.data.search.core;

import com.raycloud.bizlogger.Logger;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.search.common.SolrConnFactory;
import com.raycloud.overseas.erp.data.search.query.AbstractSolrQuery;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;


import java.io.IOException;
/**
 *  @Description 搜索服务类
 *  数据流形式
 *  (SearchListRequest=>
 *  (解析成solr查询参数)SolrMerchantQueryCondition=>
 *  获取solr Response=>获取分页对象ListReponse=>返回
 *  @Author zhanxf
 *  @Date 16/12/28 14:29
 */
public abstract class AbstractSolrService implements ISolrService{

    private static Logger logger= Logger.getLogger(AbstractSolrService.class);

    protected Integer MAX_BLOCAK_SIZE = 500;

    public AbstractSolrService() {
    }

    public abstract SolrConnFactory getSolrConnFactory();

    @Override
    public Result queryDocsWishQuery(AbstractSolrQuery abstractSolrQuery) {
        Result result = new Result();
        try {

            SolrQuery solrQuery = new SolrQuery();

            SolrClient solrClient = getSolrConnFactory().getConnection(abstractSolrQuery.getCoreType());

            abstractSolrQuery.initSolrQuery(solrQuery);

            Long startTime = System.currentTimeMillis();

            QueryResponse response = solrClient.query(solrQuery, abstractSolrQuery.getRequestType());

            logger.biz("solr查询,耗时:{}毫秒", (System.currentTimeMillis() - startTime));

            SolrDocumentList solrDocumentList = response.getResults();

            result.setTotal(solrDocumentList.getNumFound());
            if(abstractSolrQuery.getRows()!=0){
                result.setPageNo((abstractSolrQuery.getStart()/abstractSolrQuery.getRows())+1);
            }else{
                result.setPageNo(1);
            }
            result.setPageSize(abstractSolrQuery.getRows());
            result.setItems(parseSolrDocToObj(response,abstractSolrQuery));

        } catch (SolrServerException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }


        return result;
    }

    public Result blockQueryDocs2(AbstractSolrQuery abstractSolrQuery){
        return null;
    }

    /**
     * 将solr文档解析成对象
     * @param queryResponse
     * @param abstractSolrQuery
     * @return
     */
    public abstract List parseSolrDocToObj(QueryResponse queryResponse,AbstractSolrQuery abstractSolrQuery);

//    /**
//     * 计算块的大小
//     * @param b
//     * @return
//     */
//    public int caculateBlockSize(int b){
//        if(b<=MAX_BLOCAK_SIZE){
//            return b;
//        }
//        for(int i = 1;i<20;i++){
//            if(b/i<=MAX_BLOCAK_SIZE){
//                return b/i+1;
//            }
//        }
//        return 0;
//    }

    /**
     * @param abstractSolrQuery
     * @return
     */
    @Override
    public Result blockQueryDocs(AbstractSolrQuery abstractSolrQuery) {

        return null;
    }
}
