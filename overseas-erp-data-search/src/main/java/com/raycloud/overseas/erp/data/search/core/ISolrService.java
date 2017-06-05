package com.raycloud.overseas.erp.data.search.core;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.search.query.AbstractSolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 *  @Description 搜索接口
 *  @Author zhanxf
 *  @Date 16/12/28 14:36
 */
public interface ISolrService {

    /**
     * 查询solr文档
     * @param abstractSolrQuery
     * @return
     */
    public Result queryDocsWishQuery(AbstractSolrQuery abstractSolrQuery);

    /**
     * 分片搜索
     * @param abstractSolrQuery
     * @return
     */
    public Result blockQueryDocs(AbstractSolrQuery abstractSolrQuery);
}
