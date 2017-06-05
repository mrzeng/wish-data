package com.raycloud.overseas.erp.data.services.context;

import com.github.ltsopensource.core.json.JSON;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.UserData;

import java.util.*;

/**
 * 产品采集到关注产品过程容器
 * Created by zhanxf on 17/4/26.
 */
public class CollectContext {

    private static Logger logger= Logger.getLogger(CollectContext.class);

    public CollectContext() {
    }

    public CollectContext(Date expireTime, List<ItemDomain> collectItemList, UserData userData,List<String> sucIdList,int userLevel,boolean needBean) {
        this.expireTime = expireTime;
        this.collectItemList = collectItemList;
        this.userData = userData;
        this.sucIdList = sucIdList;
        this.userLevel = userLevel;
        this.needBean = needBean;
    }

    public Date expireTime;

    public List<ItemDomain> collectItemList;

    public UserData userData;

    public List<String> sucIdList;

    public int userLevel = 0;

    public int consume = 0;

    public boolean needBean;

    public Map<String,Object> createExecInfo(){
        int dealCount = ListUtil.isBlank(sucIdList)?0:sucIdList.size();
        Map<String,Object> param = new HashMap<String, Object>();
        countConsumeBeans();
        param.put("consume",consume);
        param.put("planCount",ListUtil.isBlank(collectItemList)?0:collectItemList.size());
        param.put("dealCount", dealCount);
        return param;
    }

    /**
     * 统计消费的豆数
     */
    public void countConsumeBeans(){
        int dealCount = ListUtil.isBlank(sucIdList)?0:sucIdList.size();
        if(userLevel== DataConstant.VIP_0){
            if(!needBean){
                consume=0;
            }else{
                consume=dealCount;
            }
        }else{
            consume = 0;
        }
    }

    public void showFailLog(){
        if(ListUtil.isBlank(collectItemList)){
            collectItemList = Collections.EMPTY_LIST;
        }
        if(ListUtil.isBlank(sucIdList)){
            sucIdList = Collections.EMPTY_LIST;
        }
        if(collectItemList.size()!=sucIdList.size()){
            Set<String> srcIdSet = new HashSet<String>();
            for(ItemDomain itemDomain : collectItemList){
                srcIdSet.add(itemDomain.getItemId());
            }
            srcIdSet.removeAll(sucIdList);
            logger.biz("用户id:{},采集失败宝贝列表:{}",userData.getUserId(), JSON.toJSONString(srcIdSet));
        }
    }
}
