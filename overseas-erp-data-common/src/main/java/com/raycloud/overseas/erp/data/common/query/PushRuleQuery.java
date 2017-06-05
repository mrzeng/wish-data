package com.raycloud.overseas.erp.data.common.query;

import org.springframework.util.StringUtils;

import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class PushRuleQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** 推送规则id **/
    private String id;
	public String getId () {
    	return id;
   	}
    public PushRuleQuery setId(String id) {
    	this.id = id;
    	return this;
    }

	/** rule_name **/
    private String ruleName;
	public String getRuleName () {
    	return ruleName;
   	}
    public PushRuleQuery setRuleName(String ruleName) {
    	this.ruleName = ruleName;
    	return this;
    }
	/** rule_desc **/
    private String ruleDesc;
	public String getRuleDesc () {
    	return ruleDesc;
   	}
    public PushRuleQuery setRuleDesc(String ruleDesc) {
    	this.ruleDesc = ruleDesc;
    	return this;
    }
	/** 7 **/
    private Integer pushTotal7;
	public Integer getPushTotal7 () {
    	return pushTotal7;
   	}
    public PushRuleQuery setPushTotal7(Integer pushTotal7) {
    	this.pushTotal7 = pushTotal7;
    	return this;
    }
	private Integer pushTotal7Start;
	private Integer pushTotal7End;

	public Integer getPushTotal7Start() {
		return pushTotal7Start;
	}

	public void setPushTotal7Start(Integer pushTotal7Start) {
		this.pushTotal7Start = pushTotal7Start;
	}

	public Integer getPushTotal7End() {
		return pushTotal7End;
	}

	public void setPushTotal7End(Integer pushTotal7End) {
		this.pushTotal7End = pushTotal7End;
	}

	/** 添加时间 **/
    private Date createdStart;
    public Date getCreatedStart () {
        return createdStart;
    }
    public PushRuleQuery setCreatedStart(Date created) {
        this.createdStart = created;
        return this;
    }

    private Date createdEnd;
    public Date getCreatedEnd () {
        return createdEnd;
    }
    public PushRuleQuery setCreatedEnd(Date created) {
        this.createdEnd = created;
        return this;
    }

    private Date createdEqual;
    public Date getCreatedEqual () {
        return createdEqual;
    }
    public PushRuleQuery setCreatedEqual(Date created) {
        this.createdEqual = created;
        return this;
    }
     /** 更新时间 **/
    private Date updatedStart;
    public Date getUpdatedStart () {
        return updatedStart;
    }
    public PushRuleQuery setUpdatedStart(Date updated) {
        this.updatedStart = updated;
        return this;
    }

    private Date updatedEnd;
    public Date getUpdatedEnd () {
        return updatedEnd;
    }
    public PushRuleQuery setUpdatedEnd(Date updated) {
        this.updatedEnd = updated;
        return this;
    }

    private Date updatedEqual;
    public Date getUpdatedEqual () {
        return updatedEqual;
    }
    public PushRuleQuery setUpdatedEqual(Date updated) {
        this.updatedEqual = updated;
        return this;
    }
	/** 0 禁用，1启用 **/
    private Integer ruleStatus;

	public Integer getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(Integer ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	/** rule_delete **/
    private Boolean ruleDelete;

	public Boolean getRuleDelete() {
		return ruleDelete;
	}

	public PushRuleQuery setRuleDelete(Boolean ruleDelete) {
		this.ruleDelete = ruleDelete;
		return this;
	}

	/** 推送策略（1关注店铺-全部,2关注店铺-标签，3关注产品-全部，4关注店铺标签） **/
    private Integer strategyType;
	public Integer getStrategyType () {
    	return strategyType;
   	}
    public PushRuleQuery setStrategyType(Integer strategyType) {
    	this.strategyType = strategyType;
    	return this;
    }
	/** 策略参数 **/
    private String strategyParam;
	public String getStrategyParam () {
    	return strategyParam;
   	}
    public PushRuleQuery setStrategyParam(String strategyParam) {
    	this.strategyParam = strategyParam;
    	return this;
    }
	/** 用户id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public PushRuleQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** 创始人id **/
    private Integer founderId;
	public Integer getFounderId () {
    	return founderId;
   	}
    public PushRuleQuery setFounderId(Integer founderId) {
    	this.founderId = founderId;
    	return this;
    }

	private String searchKey;

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		if(searchKey == null || searchKey.trim().equals("")){
			this.searchKey = null;
		}else{
			this.searchKey = searchKey;
		}

	}

	private Integer running;

	public Integer getRunning() {
		return running;
	}

	public void setRunning(Integer running) {
		this.running = running;
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
	 * 设置排序按属性：推送规则id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：rule_name
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyRuleName(boolean isAsc){
		orderFields.add(new OrderField("rule_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：rule_desc
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyRuleDesc(boolean isAsc){
		orderFields.add(new OrderField("rule_desc",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：7
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyPushTotal7(boolean isAsc){
		orderFields.add(new OrderField("push_total_7",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：添加时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyCreated(boolean isAsc){
		orderFields.add(new OrderField("created",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：更新时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyUpdated(boolean isAsc){
		orderFields.add(new OrderField("updated",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：0 禁用，1启用
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyRuleStatus(boolean isAsc){
		orderFields.add(new OrderField("rule_status",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：rule_delete
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyRuleDelete(boolean isAsc){
		orderFields.add(new OrderField("rule_delete",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送策略（1关注店铺-全部,2关注店铺-标签，3关注产品-全部，4关注店铺标签）
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyStrategyType(boolean isAsc){
		orderFields.add(new OrderField("strategy_type",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：策略参数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyStrategyParam(boolean isAsc){
		orderFields.add(new OrderField("strategy_param",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：用户id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：创始人id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushRuleQuery orderbyFounderId(boolean isAsc){
		orderFields.add(new OrderField("founder_id",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public PushRuleQuery setKeys(List<String> keys) {
        this.keys = keys;
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

	public void sortBySingleCol(){
		if(sortField!=null&&!sortField.equals("")){
			orderFields.add(new OrderField(sortField,sortOrder));
		}
	}

    private String fields;
    /**
     * 提供自定义字段使用
     */
    private static Map<String,String> fieldMap;

    private static Map<String,String> getFieldSet() {
        if (fieldMap == null){
            fieldMap =new HashMap<String,String>();
                    fieldMap.put("id", "id");
                    fieldMap.put("rule_name", "ruleName");
                    fieldMap.put("rule_desc", "ruleDesc");
                    fieldMap.put("push_total_7", "pushTotal7");
                    fieldMap.put("created", "created");
                    fieldMap.put("updated", "updated");
                    fieldMap.put("rule_status", "ruleStatus");
                    fieldMap.put("rule_delete", "ruleDelete");
                    fieldMap.put("strategy_type", "strategyType");
                    fieldMap.put("strategy_param", "strategyParam");
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("founder_id", "founderId");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushRuleQuery  setFields(String fields){
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
