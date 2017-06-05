package com.raycloud.overseas.erp.data.common.query;

import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class PushItemRuleTraceQuery extends BaseQuery {

    public PushItemRuleTraceQuery() {
    }

    public PushItemRuleTraceQuery(String insertDateEqual, String insertDateStart, String insertDateEnd, Integer userId, Integer founderId) {
        this.insertDateEqual = insertDateEqual;
        this.insertDateStart = insertDateStart;
        this.insertDateEnd = insertDateEnd;
        this.userId = userId;
        this.founderId = founderId;
    }

    /**==============================批量查询、更新、删除时的Where条件设置==================================**/
    /** 规则id **/
    private String ruleId;
    public String getRuleId () {
        return ruleId;
    }
    public PushItemRuleTraceQuery setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }
    /** 插入时间 **/
    private String insertDateEqual;

    public String getInsertDateEqual() {
        return insertDateEqual;
    }

    public void setInsertDateEqual(String insertDateEqual) {
        this.insertDateEqual = insertDateEqual;
    }

    private String insertDateStart;
    private String insertDateEnd;

    public String getInsertDateStart() {
        return insertDateStart;
    }

    public void setInsertDateStart(String insertDateStart) {
        this.insertDateStart = insertDateStart;
    }

    public String getInsertDateEnd() {
        return insertDateEnd;
    }

    public void setInsertDateEnd(String insertDateEnd) {
        this.insertDateEnd = insertDateEnd;
    }

    /** 宝贝id **/
    private String itemId;
    public String getItemId () {
        return itemId;
    }
    public PushItemRuleTraceQuery setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }
    /** user_id **/
    private Integer userId;
    public Integer getUserId () {
        return userId;
    }
    public PushItemRuleTraceQuery setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
    /** founder_id **/
    private Integer founderId;
    public Integer getFounderId () {
        return founderId;
    }
    public PushItemRuleTraceQuery setFounderId(Integer founderId) {
        this.founderId = founderId;
        return this;
    }

    private String sortField;

    private String sortOrder;

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        if(searchKey!=null&&!searchKey.trim().equals("")){
            this.searchKey = searchKey.trim();
        }
    }
    /**==============================批量查询时的Order条件顺序设置==================================**/
    public class OrderField{
        public OrderField(String fieldName, String order) {
            super();
            this.fieldName = fieldName;
            this.order = order;
        }
        private String fieldName;
        private String order;
        public String getFieldName() {
            return fieldName;
        }
        public OrderField setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }
        public String getOrder() {
            return order;
        }
        public OrderField setOrder(String order) {
            this.order = order;
            return this;
        }
    }

    /**==============================批量查询时的Order条件顺序设置==================================**/
    /**排序列表字段**/
    private List<OrderField> orderFields = new ArrayList<OrderField>();
    /**
     * 设置排序按属性：规则id
     * @param isAsc 是否升序，否则为降序
     */
    public PushItemRuleTraceQuery orderbyRuleId(boolean isAsc){
        this.sortField = "rule_id";
        this.sortOrder = isAsc?"ASC":"DESC";
        return this;
    }
    /**
     * 设置排序按属性：插入时间
     * @param isAsc 是否升序，否则为降序
     */
    public PushItemRuleTraceQuery orderbyInsertDate(boolean isAsc){
        this.sortField = "insert_date";
        this.sortOrder = isAsc?"ASC":"DESC";
        return this;
    }
    /**
     * 设置排序按属性：宝贝id
     * @param isAsc 是否升序，否则为降序
     */
    public PushItemRuleTraceQuery orderbyItemId(boolean isAsc){
        this.sortField = "item_id";
        this.sortOrder = isAsc?"ASC":"DESC";
        return this;
    }
    /**
     * 设置排序按属性：user_id
     * @param isAsc 是否升序，否则为降序
     */
    public PushItemRuleTraceQuery orderbyUserId(boolean isAsc){
        this.sortField = "user_id";
        this.sortOrder = isAsc?"ASC":"DESC";
        return this;
    }
    /**
     * 设置排序按属性：founder_id
     * @param isAsc 是否升序，否则为降序
     */
    public PushItemRuleTraceQuery orderbyFounderId(boolean isAsc){
        this.sortField = "founder_id";
        this.sortOrder = isAsc?"ASC":"DESC";
        return this;
    }
    private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public PushItemRuleTraceQuery setKeys(List<String> keys) {
        this.keys = keys;
        return this;
    }


    private String fields;
    /**
     * 提供自定义字段使用
     */
    private static Map<String,String> fieldMap;

    private static Map<String,String> getFieldSet() {
        if (fieldMap == null){
            fieldMap =new HashMap<String,String>();
            fieldMap.put("rule_id", "ruleId");
            fieldMap.put("insert_date", "insertDate");
            fieldMap.put("item_id", "itemId");
            fieldMap.put("user_id", "userId");
            fieldMap.put("founder_id", "founderId");
        }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushItemRuleTraceQuery  setFields(String fields){
        String[] array = fields.split(",");
        StringBuffer buffer = new StringBuffer();
        for (String field : array){
            if(getFieldSet().containsKey(field)){
                buffer.append(field).append(" as ").append(getFieldSet().get(field)).append(" ,");
            }
            if(getFieldSet().containsKey("`"+field+"`")){
                buffer.append("`"+field+"`").append(" as ").append(getFieldSet().get(field)).append(" ,");
            }
        }
        if (buffer.length() != 0){
            this.fields = buffer.substring(0, buffer.length() - 1);
        }else{
            this.fields = " 1 ";//没有一个参数可能会报错
        }
        return this;
    }
}
