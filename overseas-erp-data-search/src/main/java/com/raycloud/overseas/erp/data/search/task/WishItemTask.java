package com.raycloud.overseas.erp.data.search.task;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.pojo.PushConditionDomain;
import com.raycloud.overseas.erp.data.common.query.PushConditionQuery;
import com.raycloud.overseas.erp.data.common.spring.SpringUtil;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.search.core.AbstractSolrService;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;

import java.util.List;

/**
 * Created by zhanxf on 17/3/4.
 */
public class WishItemTask {
    private static Logger logger = Logger.getLogger(WishItemTask.class);

    private WishItemSolrService wishItemSolrService;

    private WishItemSolrQuery wishItemSolrQuery;

    public WishItemTask() {
    }

    public WishItemTask(WishItemSolrService wishItemSolrService, WishItemSolrQuery wishItemSolrQuery) {
        this.wishItemSolrService = wishItemSolrService;
        this.wishItemSolrQuery = wishItemSolrQuery;
    }

    public Result queryDocs(){
        return wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery);
    }
}
