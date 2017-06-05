package com.raycloud.overseas.erp.data.common.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *推送条件设置
 * @author zhanxiaofeng@raycloud.com
 */
public class PushConditionQuery extends BaseQuery {
	public PushConditionQuery() {
	}

	public PushConditionQuery(Long userId) {
		this.userId = userId;
	}

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public PushConditionQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 最近上新天数 **/
    private Integer latestGenDays;
	public Integer getLatestGenDays () {
    	return latestGenDays;
   	}
    public PushConditionQuery setLatestGenDays(Integer latestGenDays) {
    	this.latestGenDays = latestGenDays;
    	return this;
    }
	/** 热销宝贝个数 **/
    private Integer saleHotCount;
	public Integer getSaleHotCount () {
    	return saleHotCount;
   	}
    public PushConditionQuery setSaleHotCount(Integer saleHotCount) {
    	this.saleHotCount = saleHotCount;
    	return this;
    }
	/** 销量飙升宝贝个数 **/
    private Integer amountSurgeCount;
	public Integer getAmountSurgeCount () {
    	return amountSurgeCount;
   	}
    public PushConditionQuery setAmountSurgeCount(Integer amountSurgeCount) {
    	this.amountSurgeCount = amountSurgeCount;
    	return this;
    }
	/** user_id **/
    private Long userId;
	public Long getUserId () {
    	return userId;
   	}
    public PushConditionQuery setUserId(Long userId) {
    	this.userId = userId;
    	return this;
    }

	private String created;

	public PushConditionQuery(String created) {
		this.created = created;
	}
	private Integer founderId;

	public Integer getFounderId() {
		return founderId;
	}

	public void setFounderId(Integer founderId) {
		this.founderId = founderId;
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
	public PushConditionQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：最近上新天数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushConditionQuery orderbyLatestGenDays(boolean isAsc){
		orderFields.add(new OrderField("latest_gen_days",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：热销宝贝个数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushConditionQuery orderbySaleHotCount(boolean isAsc){
		orderFields.add(new OrderField("sale_hot_count",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：销量飙升宝贝个数
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushConditionQuery orderbyAmountSurgeCount(boolean isAsc){
		orderFields.add(new OrderField("amount_surge_count",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushConditionQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}

	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushConditionQuery orderbyCreated(boolean isAsc){
		orderFields.add(new OrderField("created",isAsc?"ASC":"DESC"));
		return this;
	}

      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public PushConditionQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("latest_gen_days", "latestGenDays");
                    fieldMap.put("sale_hot_count", "saleHotCount");
                    fieldMap.put("amount_surge_count", "amountSurgeCount");
                    fieldMap.put("user_id", "userId");
					fieldMap.put("created", "created");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushConditionQuery  setFields(String fields){
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
