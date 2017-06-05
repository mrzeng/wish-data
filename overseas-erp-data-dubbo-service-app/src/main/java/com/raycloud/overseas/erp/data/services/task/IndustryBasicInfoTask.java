package com.raycloud.overseas.erp.data.services.task;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.raycloud.overseas.data.commom.domain.wish.domain.Industry;
import com.raycloud.overseas.data.api.dubbo.service.IIndustryService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.spring.SpringUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 获取行业基本信息任务
 */
public class IndustryBasicInfoTask {

    private static Logger logger = Logger.getLogger(IndustryBasicInfoTask.class);

    private IIndustryService mjIndustryService;

    public IndustryBasicInfoTask() {
        this.mjIndustryService = (IIndustryService) SpringUtil.getBean("mjIndustryService");
    }


    public Map<String,Object> queryIndustryBasicInfo(WishCategory wishCategory,String traceOrOrder, String timeStart, String timeEnd, List<Long> dateList){
        List<Industry> industryList = mjIndustryService.queryIndustryBasicInfoTrendByCatId(wishCategory.getCatId(),timeStart,timeEnd);

        Map<Long,Industry> dayIndustryMap = Maps.newHashMap();
        if(!ListUtil.isBlank(industryList)){
            for(Industry industry : industryList){
                dayIndustryMap.put(industry.getInsertDate().getTime(),industry);
            }
        }

        List<Object> traceList = new ArrayList<Object>();

        for(int i = 0;i<dateList.size();i++){

            //时间选的跨度超过可采集的数据时
            if(!dayIndustryMap.containsKey(dateList.get(i))){
                traceList.add(0);
            }else{
                Industry industry = dayIndustryMap.get(dateList.get(i));
                if(traceOrOrder.equals("amount")){
                    traceList.add(industry.getPrice());
                }else if(traceOrOrder.equals("save")){
                    traceList.add(industry.getWishSave());
                }else if(traceOrOrder.equals("saleCount")){
                    traceList.add(industry.getAmount());
                }
            }

        }
        Map<String,Object> catDataMap = new HashMap<String, Object>();
        catDataMap.put("name",wishCategory.getChineseName());
        catDataMap.put("englishName",wishCategory.getEnglishName());
        catDataMap.put("data",traceList);
        return catDataMap;
    }
}
