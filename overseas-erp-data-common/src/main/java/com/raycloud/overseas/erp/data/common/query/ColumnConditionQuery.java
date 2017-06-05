package com.raycloud.overseas.erp.data.common.query;

                        import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class ColumnConditionQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public ColumnConditionQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 用户id **/
    private String userId;
	public String getUserId () {
    	return userId;
   	}
    public ColumnConditionQuery setUserId(String userId) {
    	this.userId = userId;
    	return this;
    }
	/** 团队创始人id **/
    private String founderId;
	public String getFounderId () {
    	return founderId;
   	}
    public ColumnConditionQuery setFounderId(String founderId) {
    	this.founderId = founderId;
    	return this;
    }
	/** 模块名 **/
    private String status;
	public String getStatus () {
    	return status;
   	}
    public ColumnConditionQuery setStatus(String status) {
    	this.status = status;
    	return this;
    }
	/** 自定义列 **/
    private String param;
	public String getParam () {
    	return param;
   	}
    public ColumnConditionQuery setParam(String param) {
    	this.param = param;
    	return this;
    }
     /** 创建时间 **/
    private Date createTimeStart;
    public Date getCreateTimeStart () {
        return createTimeStart;
    }
    public ColumnConditionQuery setCreateTimeStart(Date createTime) {
        this.createTimeStart = createTime;
        return this;
    }

    private Date createTimeEnd;
    public Date getCreateTimeEnd () {
        return createTimeEnd;
    }
    public ColumnConditionQuery setCreateTimeEnd(Date createTime) {
        this.createTimeEnd = createTime;
        return this;
    }

    private Date createTimeEqual;
    public Date getCreateTimeEqual () {
        return createTimeEqual;
    }
    public ColumnConditionQuery setCreateTimeEqual(Date createTime) {
        this.createTimeEqual = createTime;
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
	public ColumnConditionQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：用户id
	 * @param isAsc 是否升序，否则为降序
	 */
	public ColumnConditionQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：团队创始人id
	 * @param isAsc 是否升序，否则为降序
	 */
	public ColumnConditionQuery orderbyFounderId(boolean isAsc){
		orderFields.add(new OrderField("founder_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：模块名
	 * @param isAsc 是否升序，否则为降序
	 */
	public ColumnConditionQuery orderbyStatus(boolean isAsc){
		orderFields.add(new OrderField("status",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：自定义列
	 * @param isAsc 是否升序，否则为降序
	 */
	public ColumnConditionQuery orderbyParam(boolean isAsc){
		orderFields.add(new OrderField("param",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：创建时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public ColumnConditionQuery orderbyCreateTime(boolean isAsc){
		orderFields.add(new OrderField("create_time",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public ColumnConditionQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("founder_id", "founderId");
                    fieldMap.put("status", "status");
                    fieldMap.put("param", "param");
                    fieldMap.put("create_time", "createTime");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public ColumnConditionQuery  setFields(String fields){
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
