package com.raycloud.overseas.erp.data.search.query;

import com.raycloud.overseas.erp.data.common.util.DateUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.common.params.SolrParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhanxf on 17/2/26.
 */
public abstract class AbstractSolrQuery {

    public int start;

    public int rows;

    public String sortField;

    public String order;

    private String[] filterField;//过滤域

    /**
     * 获取请求类型
     * @return
     */
    public abstract SolrRequest.METHOD getRequestType();

    /**
     * 拼接q查询参数
     * @return
     */
    public abstract String joinQueryParam();

    public abstract boolean isHighLight();

    public abstract String getCoreType();

    public void initSolrQuery(SolrQuery solrQuery){
        solrQuery.setQuery(joinQueryParam());
//        solrQuery.setFilterQueries(joinQueryParam());
        solrQuery.addField(joinFilterField());
        if(sortField!=null)
        solrQuery.setSort(sortField, order.toUpperCase().equals("DESC")? SolrQuery.ORDER.desc: SolrQuery.ORDER.asc);
        if (rows != 0) {
            solrQuery.setStart(start);
            solrQuery.setRows(rows);
        } else {
            solrQuery.setStart(0);
            solrQuery.setRows(10);
        }
    }

    public String[] getFilterField() {
        return filterField;
    }

    public void setFilterField(String[] filterField) {
        this.filterField = filterField;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 添加过滤域
     * @return
     */
    private String joinFilterField(){
        if(filterField!=null){
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<filterField.length;i++){
                 if(i<filterField.length-1){
                     sb.append(filterField[i]+",");
                 }else{
                     sb.append(filterField[i]);
                 }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 获取编码后的值
     * @param s
     * @return
     */
    public String joinUrlEncode(String s){
        try {
            return URLEncoder.encode(s,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "*";
    }

    /**
     * 拼接迭代器列表
     * @return
     */
    public String joinCollection(Collection<String> collection){
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = collection.iterator();
        String s = it.next();
        if(s==null){
            return "*";
        }else{
            sb.append("("+s+" OR ");
        }
        while (it.hasNext()){
            sb.append(it.next()+" OR ");
        }
        String tmp = sb.substring(0,sb.length()-3);
        return tmp+")";
    }

    /**
     * 获取某天开始的时间
     * @param date
     * @return
     */
    public String joinDate(Date date){
        if(date == null){
            return "*";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(date);
    }

    public static Integer getTotal7Start(Integer total1Start){
        if(total1Start!=null){
            return (int)((total1Start-0.5)*7)+1;
        }else{
            return null;
        }
    }

    public static Integer getTotal7End(Integer total1End){
        if(total1End!=null){
            return (int) ((total1End+0.5)*7);
        }else{
            return null;
        }
    }
}
