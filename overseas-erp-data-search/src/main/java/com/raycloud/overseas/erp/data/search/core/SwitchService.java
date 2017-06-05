package com.raycloud.overseas.erp.data.search.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.HttpClientUtil;
import com.raycloud.overseas.erp.data.search.common.SolrConfig;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhanxf on 17/5/22.
 */
@Service
public class SwitchService {

    private static Logger logger= Logger.getLogger(SwitchService.class);

    @Autowired
    private OcsSession ocsSession;

    public static ExecutorService fixThreadPool = Executors.newFixedThreadPool(2);

    /**
     * 切换solr服务
     * @param node 0切换到另外节点,1切换到89服务器,2切换到155服务器,,并重新导入当前节点
     * @param command
     * @return
     */
    public Map<String,Object> switchNode(int node,String command){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("result","success");
        if(node==SolrConfig.ALL_NODE){
            String curl = (String) ocsSession.getAppObj(SolrConfig.OCS_SOLR_URL_KEY);
            logger.biz("启用全部节点,当前节点{}",curl);
            ocsSession.removeAppObj(SolrConfig.OCS_SOLR_URL_KEY);
            return map;
        }
        String url = null;
        String oldUrl = null;
        switch (node){
            case SolrConfig.OTHER_NODE:
                oldUrl = (String) ocsSession.getAppObj(SolrConfig.OCS_SOLR_URL_KEY);
                if(oldUrl==null){
                    oldUrl = SolrConfig.SOLR_URL_2;
                    logger.biz("当前为正常运行状态,将{}作为旧节点",oldUrl);
                }
                if(oldUrl.equals(SolrConfig.SOLR_URL_2)) {
                    url = SolrConfig.SOLR_URL_1;
                }else{
                    url = SolrConfig.SOLR_URL_2;
                    oldUrl = SolrConfig.SOLR_URL_1;
                }
                break;
            case SolrConfig.NODE1:
                url =  SolrConfig.SOLR_URL_1;
                oldUrl = SolrConfig.SOLR_URL_2;
                break;
            case SolrConfig.NODE2:
                url =  SolrConfig.SOLR_URL_2;
                oldUrl = SolrConfig.SOLR_URL_1;
                break;
            default:
                logger.error("SOLR无效节点:{}",node);
                break;
        }
        logger.biz("SOLR切换操作启动,准备切换节点,旧节点:{},新节点:{}",oldUrl,url);
        ocsSession.setAppObj(SolrConfig.OCS_SOLR_URL_KEY,url);
        if(!StringUtils.isEmpty(command)){
            if(command.equalsIgnoreCase("reload")){
                boolean success = reload(oldUrl+SolrConfig.MERCHANT_CORE+SolrConfig.IMPORT);
                success = reload(oldUrl+SolrConfig.ITEM_CORE+SolrConfig.IMPORT);
                if(success){
                    monitor(oldUrl);
                }
            }else{
                logger.error("SOLR指令无效:{}",command);
            }
        }
//        solrConnFactory.close();
        logger.biz("SOLR服务器节点切换成功,当前节点:{}",url);
        map.put("url",url);
        return map;
    }

    public boolean reload(String url){
        Map<String,String> param = new HashMap<String, String>();
        param.put("command","full-import");
        param.put("clean","true");
        param.put("commit","true");
        param.put("wt","json");
        param.put("indent","true");
        param.put("verbose","false");
        param.put("optimize","false");
        param.put("debug","false");
        int count = 0;
        boolean success = false;
        while (true){
            try {
                HttpClientUtil.sendPostRequest(url,param,null,null);
                success = true;
                logger.biz("SOLR指令发送成功,url:{},param:{}",url, JSON.toJSONString(param));
                break;
            } catch (Exception e) {
                count++;
                if(count<3){
                    try {
                        Thread.sleep(30*60*1000);//30分钟发一次
                        HttpClientUtil.sendPostRequest(url,param,null,null);
                        success = true;
                        logger.biz("SOLR指令发送成功,url:{},param:{}",url, JSON.toJSONString(param));
                        break;
                    } catch (InterruptedException e1) {
                        logger.error("指令发送失败",e);
                    } catch (Exception e1) {
                        logger.error("指令发送失败",e);
                    }
                }else{
                    logger.error("重试达到最大次数");
                    break;
                }
            }
        }
        return success;
    }

    public void monitor(final String url){
        logger.biz("SOLR监控import状态,监控节点{}",url);
        fixThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while(true){
                    try {
                        Thread.sleep(10*60*1000);//10分钟监控一次
                        String res = HttpClientUtil.sendGetRequest(url+SolrConfig.ITEM_CORE+"/dataimport?command=status&indent=true&wt=json");
                        JSONObject json = JSON.parseObject(res);
                        logger.biz("节点url:{},状态:{}",url,json.get("status"));
                        if(json.get("status").equals("idle")){
                            JSONObject msg = json.getJSONObject("statusMessages");
                            if(msg.containsKey("Full Dump Started")){
                                String start = msg.getString("Full Dump Started");
                                Date date = DateUtil.getDate(start,"yyyy-MM-dd HH:mm:ss");
                                logger.biz("SOLR节点:{}导入完成",url);
                                if(checkOtherNodeImportNecessary(url,date)){
                                    logger.biz("切换到节点{},准备更新节点{}",url,getOtherNode(url));
                                    switchNode(getUrlNode(url),"reload");
                                }
                            }
                            logger.biz("SOLR节点{}监控结束",url);
                            break;
                        }
                    } catch (Exception e){
                        count++;
                        logger.error("SOLR监控失败",e);
                        if(count>10){
                            logger.error("SOLR监控失败次数达到上限");
                            break;
                        }
                    }
                }
            }
        });
    }

    private String getOtherNode(String url){
        if(url.equals(SolrConfig.SOLR_URL_1)){
            return SolrConfig.SOLR_URL_2;
        }else{
            return SolrConfig.SOLR_URL_1;
        }
    }

    private int getUrlNode(String url){
        if(url.equals(SolrConfig.SOLR_URL_1)){
            return SolrConfig.NODE1;
        }else{
            return SolrConfig.NODE2;
        }
    }

    private boolean checkOtherNodeImportNecessary(String url,Date date){
        String otherUrl = getOtherNode(url);
        String res = HttpClientUtil.sendGetRequest(otherUrl+SolrConfig.ITEM_CORE+"/dataimport?command=status&indent=true&wt=json");
        JSONObject json = JSON.parseObject(res);
        if(!json.get("status").equals("idle")){
            logger.biz("SOLR正在导入,节点:{}",otherUrl);
            return false;
        }
        JSONObject msg = json.getJSONObject("statusMessages");
        if(!msg.containsKey("Full Dump Started")){
            return true;
        }
        String start = msg.getString("Full Dump Started");
        Date otherDate = DateUtil.getDate(start,"yyyy-MM-dd HH:mm:ss");
        long seconds = Math.abs(otherDate.getTime()-date.getTime())/1000;
        logger.biz("节点间隔:{}秒,节点:{}上次更新开始时间:{},节点2:{}本次更新开始时间{}",seconds,otherUrl,start,url,DateUtil.getDate(date,"yyyy-MM-dd HH:mm:ss"));
        if(seconds>60*60*12){
            logger.biz("节点{}更新时间过早,进行重新更新...",otherUrl);
            return true;
        }else{
            logger.biz("SOLR节点全部启用...");
            ocsSession.removeAppObj(SolrConfig.OCS_SOLR_URL_KEY);
            return false;
        }
    }

    public static void main(String[] args){
        String st1 = "2017-05-23 23:03:30";
        String st2 = "2017-05-24 05:06:03";
        Date st1d = DateUtil.getDate(st1,"yyyy-MM-dd HH:mm:ss");
        Date st2d = DateUtil.getDate(st2,"yyyy-MM-dd HH:mm:ss");
        System.out.println(Math.abs(st1d.getTime()-st2d.getTime()));
        System.out.println(Math.abs(st1d.getTime()-st2d.getTime())>1000*60*60*12);
    }
}
