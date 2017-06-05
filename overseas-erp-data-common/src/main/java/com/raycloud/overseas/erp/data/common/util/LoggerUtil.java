package com.raycloud.overseas.erp.data.common.util;

import com.raycloud.bizlogger.Logger;

/**
 * Created by zhanxf on 17/1/18.
 */
public class LoggerUtil {



    public static void formatBizLog(Logger logger, String func, String param, String desc){
        StringBuffer buffer = new StringBuffer();
        buffer.append("func:");
        buffer.append(func);
        buffer.append(",param:");
        buffer.append(param);
        buffer.append(",desc");
        buffer.append(desc);
        logger.biz(buffer.toString());
    }

}
