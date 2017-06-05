package com.raycloud.overseas.erp.data.constant;

/**
 * Created by zhanxf on 17/2/13.
 */
public class DataConstant {

    /********************************************关注采集**********************************************/

    /**
     * 关注
     */
    public static int FOCUS = 1;

    /**
     * 取消关注
     */
    public static int UN_FOCUS = 0;

    /**
     * 采集
     */
    public static int COLLECT = 1;

    /**
     * 未采集
     */
    public static int UN_COLLECT = 0;

    /**
     * 标准版
     */
    public static int VIP_0 = 0;

    /**
     * 专业版
     */
    public static int VIP_8 = 8;

    /********************************************推送范围**********************************************/
    /**
     * 关注店铺范围
     */
    public static int PUSH_RANGE_FOCUS_MERCHANT = 1;

    /**
     * 打标签店铺范围
     */
    public static int PUSH_RANGE_MARK_MERCHANT = 2;

    /**
     * 关注宝贝范围
     */
    public static int PUSH_RANGE_FOCUS_ITEM = 3;

    /**
     * 打标签宝贝范围
     */
    public static int PUSH_RANGE_MARK_ITEM = 4;

    /**
     * 按行业选择范围
     */
    public static int PUSH_RANGE_CAT = 5;

    /********************************************异步类型**********************************************/
    /**
     * 异步推送规则校验
     */
    public static String ASYN_PUSH_RULE_CHECK = "ASYN_PUSH_RULE_CHECK";

    /**
     * 异步推送规则保存启用
     */
    public static String ASYN_PUSH_RULE_SAVE = "ASYN_PUSH_RULE_SAVE";

    /**
     * 异步采集
     */
    public static String ASYN_ITEM_COLLECT = "ASYN_ITEM_COLLECT";



    /********************************************其他**********************************************/

    /**
     * wish宝贝图片地址
     */
    public static String WISH_IMAGE_SERVER_DOMAIN = "http://contestimg.wish.com/api/webimage/";

    /**
     * 消息推送
     */
    public static String COLLECT_FINISH_NOTICE = "COLLECT_FINISH_NOTICE";

    /**
     * 5分钟
     */
    public static int FIVE_MINUTE = 300;

    /**
     * 标记宝贝
     */
    public static int MARK_ITEM = 2;

    /**
     * 标记店铺
     */
    public static int MARK_MERCHANT = 3;
}
