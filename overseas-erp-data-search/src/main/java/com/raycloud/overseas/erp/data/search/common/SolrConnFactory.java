package com.raycloud.overseas.erp.data.search.common;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.io.SolrClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class SolrConnFactory {

    private static Logger logger= Logger.getLogger(SolrConnFactory.class);

    private SolrClientCache solrClientCache = new SolrClientCache();

    @Autowired
    private OcsSession ocsSession;

    /**
     * 获取solr连接
     * @param core
     * @return
     */
    public SolrClient getConnection(String core){

        String url = (String) ocsSession.getAppObj(SolrConfig.OCS_SOLR_URL_KEY);
        if(url==null){
            url = randomRoute();
        }
        SolrClient client = null;
        try{
            client = solrClientCache.getHttpSolrClient(url+core);
        }catch (Exception e){
            logger.error("solr连接失败",e);
        }
        return client;
    }

    public void close(){
        solrClientCache.close();
    }


    private String randomRoute(){
        Long time = System.currentTimeMillis();
        String timeStr = time.toString();
        int sed = timeStr.charAt(timeStr.length()-1)%2;
        if(sed==0){
            return SolrConfig.SOLR_URL_1;
        }else{
            return SolrConfig.SOLR_URL_2;
        }
    }

}
