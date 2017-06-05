package com.raycloud.overseas.erp.data.common.query;

                        import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class PushTotal7Query extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public PushTotal7Query setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 1:今日概况，2：昨日概况，3近3日概况，4 近7日概况 **/
    private Integer type;
	public Integer getType () {
    	return type;
   	}
    public PushTotal7Query setType(Integer type) {
    	this.type = type;
    	return this;
    }
	/** 推送总数 **/
    private Integer pushTotal;
	public Integer getPushTotal () {
    	return pushTotal;
   	}
    public PushTotal7Query setPushTotal(Integer pushTotal) {
    	this.pushTotal = pushTotal;
    	return this;
    }
	/** 推送规则结果明细 **/
    private String pushResultDetail;
	public String getPushResultDetail () {
    	return pushResultDetail;
   	}
    public PushTotal7Query setPushResultDetail(String pushResultDetail) {
    	this.pushResultDetail = pushResultDetail;
    	return this;
    }
	/** 待采集产品数量 **/
    private Integer uncollectTotal;
	public Integer getUncollectTotal () {
    	return uncollectTotal;
   	}
    public PushTotal7Query setUncollectTotal(Integer uncollectTotal) {
    	this.uncollectTotal = uncollectTotal;
    	return this;
    }
	/** 创始人id **/
    private Integer founderId;
	public Integer getFounderId () {
    	return founderId;
   	}
    public PushTotal7Query setFounderId(Integer founderId) {
    	this.founderId = founderId;
    	return this;
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
	public PushTotal7Query orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：1:今日概况，2：昨日概况，3近3日概况，4 近7日概况
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal7Query orderbyType(boolean isAsc){
		orderFields.add(new OrderField("type",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送总数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal7Query orderbyPushTotal(boolean isAsc){
		orderFields.add(new OrderField("push_total",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送规则结果明细
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal7Query orderbyPushResultDetail(boolean isAsc){
		orderFields.add(new OrderField("push_result_detail",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：待采集产品数量
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal7Query orderbyUncollectTotal(boolean isAsc){
		orderFields.add(new OrderField("uncollect_total",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：创始人id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushTotal7Query orderbyFounderId(boolean isAsc){
		orderFields.add(new OrderField("founder_id",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public PushTotal7Query setKeys(List<Integer> keys) {
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
                    fieldMap.put("type", "type");
                    fieldMap.put("push_total", "pushTotal");
                    fieldMap.put("push_result_detail", "pushResultDetail");
                    fieldMap.put("uncollect_total", "uncollectTotal");
                    fieldMap.put("founder_id", "founderId");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushTotal7Query  setFields(String fields){
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
