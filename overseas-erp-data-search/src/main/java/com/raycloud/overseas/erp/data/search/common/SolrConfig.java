package com.raycloud.overseas.erp.data.search.common;

/**
 * Created by zhanxf on 17/2/24.
 */
public class SolrConfig {

    /**
     * 宝贝
     */
    public static String ITEM_CORE = "erp_wish_item1";

    /**
     * 店铺
     */
    public static String MERCHANT_CORE = "erp_wish_merchant1";

    /**
     * 切换key
     */
    public static String OCS_SOLR_URL_KEY = "overseas:solr";

    public static String OCS_SOLR_IMPORT_COUNT = "overseas:solr";

    public static String IMPORT = "/dataimport";

    public static final String SOLR_URL_1 = "http://121.41.42.89:30005/solr/";
//    public static final String SOLR_URL_1 = "http://localhost:6088/solr/";

    public static final String SOLR_URL_2 = "http://121.41.43.155:30005/solr/";

    /**
     * SOLR节点
     */
    public static final int ALL_NODE = -1;

    public static final int OTHER_NODE = 0;
    
    public static final int NODE1 = 1;

    public static final int NODE2 = 2;

}
