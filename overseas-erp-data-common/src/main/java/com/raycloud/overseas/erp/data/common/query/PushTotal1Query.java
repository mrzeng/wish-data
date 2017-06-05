package com.raycloud.overseas.erp.data.common.query;

                        import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class PushTotal1Query extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public PushTotal1Query setId(Integer id) {
    	this.id = id;
    	return this;
    }

	private String pushTimeEqual;

	public String getPushTimeEqual() {
		return pushTimeEqual;
	}

	public void setPushTimeEqual(String pushTimeEqual) {
		this.pushTimeEqual = pushTimeEqual;
	}

	/** 推送时间 **/
    private String pushTimeStart;

	public String getPushTimeStart() {
		return pushTimeStart;
	}

	public void setPushTimeStart(String pushTimeStart) {
		this.pushTimeStart = pushTimeStart;
	}

	private String pushTimeEnd;

	public String getPushTimeEnd() {
		return pushTimeEnd;
	}

	public void setPushTimeEnd(String pushTimeEnd) {
		this.pushTimeEnd = pushTimeEnd;
	}

	/** 推送总数 **/
    private Integer pushTotal1;
	public Integer getPushTotal1 () {
    	return pushTotal1;
   	}
    public PushTotal1Query setPushTotal1(Integer pushTotal1) {
    	this.pushTotal1 = pushTotal1;
    	return this;
    }
	/** 推送明细 **/
    private String ruleResultDetail;
	public String getRuleResultDetail () {
    	return ruleResultDetail;
   	}
    public PushTotal1Query setRuleResultDetail(String ruleResultDetail) {
    	this.ruleResultDetail = ruleResultDetail;
    	return this;
    }
	/** 推送7日宝贝总数 **/
    private Integer pushTotal7;
	public Integer getPushTotal7 () {
    	return pushTotal7;
   	}
    public PushTotal1Query setPushTotal7(Integer pushTotal7) {
    	this.pushTotal7 = pushTotal7;
    	return this;
    }
	/** 创始人id **/
    private Integer founderId;
	public Integer getFounderId () {
    	return founderId;
   	}
    public PushTotal1Query setFounderId(Integer founderId) {
    	this.founderId = founderId;
    	return this;
    }

	private String sortField;

	private String sortOrder;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
	 * 设置排序按属性：id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal1Query orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal1Query orderbyPushTime(boolean isAsc){
		this.sortField = "push_time";
		this.sortOrder = isAsc?"ASC":"DESC";
		return this;
	}
	/**
	 * 设置排序按属性：推送总数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal1Query orderbyPushTotal1(boolean isAsc){
		orderFields.add(new OrderField("push_total_1",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送明细
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal1Query orderbyRuleResultDetail(boolean isAsc){
		orderFields.add(new OrderField("rule_result_detail",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送7日宝贝总数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal1Query orderbyPushTotal7(boolean isAsc){
		orderFields.add(new OrderField("push_total_7",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：创始人id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal1Query orderbyFounderId(boolean isAsc){
		orderFields.add(new OrderField("founder_id",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public PushTotal1Query setKeys(List<Integer> keys) {
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
                    fieldMap.put("id", "id");
                    fieldMap.put("push_item", "pushItem");
                    fieldMap.put("push_total_1", "pushTotal1");
                    fieldMap.put("rule_result_detail", "ruleResultDetail");
                    fieldMap.put("push_total_7", "pushTotal7");
                    fieldMap.put("founder_id", "founderId");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushTotal1Query  setFields(String fields){
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
