package com.raycloud.overseas.erp.data.search.common;



public enum SolrWishItemField{

    
    itemid,//宝贝id

    
    itemname,//产品标题

    
    merchant_id,//店铺id

    
    insert_date,

    
    rate_score,//产品评分

    
    cat_ids,//类目id

    
    seller_price,//刊登价格

    
    wish_price,//wish售价

    
    already_recommended_flag,//是否wish认证

    
    amount_7,//7日销量

    
    wish_save_7,//7日收藏量

    
    main_image,//主图

    hilghtTitle,

    
    gen_time,//上架时间

    
    rate_num,//评论数量

    
    seller_freight_price,//卖家运费

    
    wish_freight_price,//wish运费

    
    original_price,//吊牌价

    
    offer,//累计销量

    
    rate_num_7,//7日评论

    
    wish_num,//累计收藏

    
     removed_flag,//是否下架/售罄

    
    amount_rate,//销量增长率

    is_hwc;//是否是海外仓(TRUE:是,FALSE:不是,NULL,未知)
}
