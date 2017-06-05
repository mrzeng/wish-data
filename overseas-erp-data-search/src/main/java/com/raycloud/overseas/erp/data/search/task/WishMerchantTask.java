package com.raycloud.overseas.erp.data.search.task;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.core.WishMerchantSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;

/**
 * Created by zhanxf on 17/3/4.
 */
public class WishMerchantTask {
    private static Logger logger = Logger.getLogger(WishMerchantTask.class);

    private WishMerchantSolrService wishMerchantSolrService;

    private WishMerchantSolrQuery wishMerchantSolrQuery;

    public WishMerchantTask() {
    }

    public WishMerchantTask(WishMerchantSolrService wishMerchantSolrService, WishMerchantSolrQuery wishMerchantSolrQuery) {
        this.wishMerchantSolrService = wishMerchantSolrService;
        this.wishMerchantSolrQuery = wishMerchantSolrQuery;
    }

    public Result queryDocs(){
        return wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery);
    }
}
