package com.raycloud.overseas.erp.data.constant;

/**
 * 追踪或排序用
 * Created by zhanxf on 16/7/18.
 */
public enum TraceOrOrder {
    //商品,店铺,默认安装总销量降序排列
     itemname,// 宝贝名称
     seller_price ,//卖家标价
     wish_price ,//wish售价
     offer ,//总销量
     wish_num ,//收藏数
     rate_num ,//评论数
     amount_1 ,//当天销量
     price_1 ,//当天销售额
     wish_save_1 ,//当天收藏数
     rate_num_1 ,//当天评论数
     amount_7 ,//近7天销量
     price_7 ,//近7天销售额
     wish_save_7 ,//近7天收藏数
     rate_num_7 ,//近7天评论数
    new_item_count,//上新数量
    //行业明星产品,明星店铺排序
    amount ,//销售额
    saleCount ,//销量
    wish_save ,//收藏数
    rate_score,//产品评分
    cat_names,//所属行业排序
    already_recommended,//是否wish认证
    new_count,//新增产品
    growth,//增长率
    amount_rate,//数据库中增长率字段排序
    gen_time,//上架时间
    cat_amount_rank,//行业销量排名
    push_time,
    seller_freight_price,
    wish_freight_price,
    is_hwc,
    /***************************搜索排序说明************************************/

    /***************************追踪说明************************************/

    item_count,//按产品总数追踪
    avg_price,//按平均价格追踪
    save//按收藏量追踪




    ;
    public static TraceOrOrder getEnumFromString(String string){
        if(string!=null){
            try{
                 return Enum.valueOf(TraceOrOrder.class, string.trim());
                 }
             catch(IllegalArgumentException ex){

             }
         }
         return null;
     }

    @Override
    public String toString() {
        if(this == null){
            return "";
        }
        return super.toString();
    }
}
