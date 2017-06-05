package com.raycloud.overseas.erp.data.common.query;

import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class DataMarkInfoQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public DataMarkInfoQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 标签名称 **/
    private String markName;
	public String getMarkName () {
    	return markName;
   	}
    public DataMarkInfoQuery setMarkName(String markName) {
    	this.markName = markName;
    	return this;
    }
	/** 标签颜色 **/
    private String color;
	public String getColor () {
    	return color;
   	}
    public DataMarkInfoQuery setColor(String color) {
    	this.color = color;
    	return this;
    }
	/** mark_desc **/
    private String markDesc;
	public String getMarkDesc () {
    	return markDesc;
   	}
    public DataMarkInfoQuery setMarkDesc(String markDesc) {
    	this.markDesc = markDesc;
    	return this;
    }
	/** user_id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public DataMarkInfoQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** type **/
    private String type;
	public String getType () {
    	return type;
   	}
    public DataMarkInfoQuery setType(String type) {
    	this.type = type;
    	return this;
    }
	/** trade_uuid **/
    private String tradeUuid;
	public String getTradeUuid () {
    	return tradeUuid;
   	}
    public DataMarkInfoQuery setTradeUuid(String tradeUuid) {
    	this.tradeUuid = tradeUuid;
    	return this;
    }
	/** founder_id **/
    private Integer founderId;
	public Integer getFounderId () {
    	return founderId;
   	}
    public DataMarkInfoQuery setFounderId(Integer founderId) {
    	this.founderId = founderId;
    	return this;
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
	public DataMarkInfoQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：标签名称
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyMarkName(boolean isAsc){
		orderFields.add(new OrderField("mark_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：标签颜色
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyColor(boolean isAsc){
		orderFields.add(new OrderField("color",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：mark_desc
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyMarkDesc(boolean isAsc){
		orderFields.add(new OrderField("mark_desc",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：type
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyType(boolean isAsc){
		orderFields.add(new OrderField("type",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：trade_uuid
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyTradeUuid(boolean isAsc){
		orderFields.add(new OrderField("trade_uuid",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：founder_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public DataMarkInfoQuery orderbyFounderId(boolean isAsc){
		orderFields.add(new OrderField("founder_id",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public DataMarkInfoQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("mark_name", "markName");
                    fieldMap.put("color", "color");
                    fieldMap.put("mark_desc", "markDesc");
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("type", "type");
                    fieldMap.put("trade_uuid", "tradeUuid");
                    fieldMap.put("founder_id", "founderId");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public DataMarkInfoQuery  setFields(String fields){
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
