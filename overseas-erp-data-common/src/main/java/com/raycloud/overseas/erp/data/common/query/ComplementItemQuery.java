package com.raycloud.overseas.erp.data.common.query;

                import java.util.*;

/**
 * 补全宝贝信息表
 */
public class ComplementItemQuery extends BaseQuery {

    private Long fkId = 4L;
    public Long getFkId() {
        return fkId;
    }
    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }
    /**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** itemid **/
    private String itemid;
	public String getItemid () {
    	return itemid;
   	}
    public ComplementItemQuery setItemid(String itemid) {
    	this.itemid = itemid;
    	return this;
    }
     /** 插入时间 **/
    private Date createAtStart;
    public Date getCreateAtStart () {
        return createAtStart;
    }
    public ComplementItemQuery setCreateAtStart(Date createAt) {
        this.createAtStart = createAt;
        return this;
    }

    private Date createAtEnd;
    public Date getCreateAtEnd () {
        return createAtEnd;
    }
    public ComplementItemQuery setCreateAtEnd(Date createAt) {
        this.createAtEnd = createAt;
        return this;
    }

    private Date createAtEqual;
    public Date getCreateAtEqual () {
        return createAtEqual;
    }
    public ComplementItemQuery setCreateAtEqual(Date createAt) {
        this.createAtEqual = createAt;
        return this;
    }
	/** 0:采集中，1：采集成功，2：采集失败 **/
    private Integer status;
	public Integer getStatus () {
    	return status;
   	}
    public ComplementItemQuery setStatus(Integer status) {
    	this.status = status;
    	return this;
    }
	/** user_id **/
    private String userId;
	public String getUserId () {
    	return userId;
   	}
    public ComplementItemQuery setUserId(String userId) {
    	this.userId = userId;
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
	 * 设置排序按属性：itemid
	 * @param isAsc 是否升序，否则为降序
	 */
	public ComplementItemQuery orderbyItemid(boolean isAsc){
		orderFields.add(new OrderField("itemid",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：插入时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public ComplementItemQuery orderbyCreateAt(boolean isAsc){
		orderFields.add(new OrderField("create_at",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：0:采集中，1：采集成功，2：采集失败
	 * @param isAsc 是否升序，否则为降序
	 */
	public ComplementItemQuery orderbyStatus(boolean isAsc){
		orderFields.add(new OrderField("status",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public ComplementItemQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public ComplementItemQuery setKeys(List<String> keys) {
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
                    fieldMap.put("itemid", "itemid");
                    fieldMap.put("create_at", "createAt");
                    fieldMap.put("status", "status");
                    fieldMap.put("user_id", "userId");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public ComplementItemQuery  setFields(String fields){
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
