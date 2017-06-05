package com.raycloud.overseas.erp.data.exception;

/**
 * Description:
 *
 * @author: 钟 亮
 * Date: 14-4-18
 * Time: 下午3:51
 */
public interface ExceptionCode {

    int SUCCESS = 100;
    int PARAMETER_ERROE = 200;//参数异常
    int PARAMETER_ERROE1 = 201;//该宝贝未生成手机详情
    int PARAMETER_ERROE2 = 202;//上次全店生成未完成,不得进行二次投放
    int PARAMETER_ERROE3 = 203;//宝贝数量超过规定限制
    int PARAMETER_ERROE4 = 204;//当日已经操作了1次
    int PARAMETER_ERROE5 = 205;//该宝贝正在处理中,稍后进行保存投放... 个性化
    int PARAMETER_ERROE7 = 207;//非当前登录用户

    int NULL_ERROR = 300;//没有捕获的异常，如空指针异常等
    int SYS_ERROR = 400;//数据库异常
    int SYS_ERROR_MQ=401;//metaQ异常
    int SYS_ERROR_MQ4=404;//生成CSV异常
    int SYS_ERROR_MQ5=405;//Biz异常
    int SYS_ERROR_MQ6=406;//IO异常
    int SYS_ERROR_MQ8=408;//系统异常
    int SYS_ERROR_MQ9 = 409;//顺丰线下接口异常
    int SYS_ERROR_MAIJIA = 410;//卖家网接口调用异常

    int SERVICE_ERROR = 600;//淘宝接口调用失败，淘宝的Session失效
    int SERVICE_ERROR1 = 601;//淘宝接口异常
    int SERVICE_ERROR2 = 602;//淘宝Session失效
    int SERVICE_ERROR3 = 603;//远程数据服务接口调用失败
    int SERVICE_ERROR4 = 604;//调用wish接口失败
    int SERVICE_NO_DATA_ROLE = 650;//调用wish接口失败
    int AUTH_ERROR = 700;//权限错误 例如OSC失效
    int AUTH_ERROR1 = 701;//OSC失效
    int AUTH_ERROR2 = 702;//OSC失效

    int PRO_ERROR = 800;//程序挂了,或者备用


    int SYS_TIME_OUT = 407;//订单更新超时

    int INTERRUPTED_EXCEPTION = 408;//线程打断异常

    int EXECUTION_EXCEPTION = 409;//线程打断异常




}
