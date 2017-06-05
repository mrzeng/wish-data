package com.raycloud.overseas.erp.data.common.query;

                                import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class SearchItemQuery extends BaseQuery {

	private Long fkId = 4L;
	public Long getFkId() {
		return fkId;
	}
	public void setFkId(Long fkId) {
		this.fkId = fkId;
	}

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public SearchItemQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 产品id **/
    private String itemId;
	public String getItemId () {
    	return itemId;
   	}
    public SearchItemQuery setItemId(String itemId) {
    	this.itemId = itemId;
    	return this;
    }
	/** 店铺名称 **/
    private String merchantName;
	public String getMerchantName () {
    	return merchantName;
   	}
    public SearchItemQuery setMerchantName(String merchantName) {
    	this.merchantName = merchantName;
    	return this;
    }
     /** 创建时间 **/
    private Date createdStart;
    public Date getCreatedStart () {
        return createdStart;
    }
    public SearchItemQuery setCreatedStart(Date created) {
        this.createdStart = created;
        return this;
    }

    private Date createdEnd;
    public Date getCreatedEnd () {
        return createdEnd;
    }
    public SearchItemQuery setCreatedEnd(Date created) {
        this.createdEnd = created;
        return this;
    }

    private Date createdEqual;
    public Date getCreatedEqual () {
        return createdEqual;
    }
    public SearchItemQuery setCreatedEqual(Date created) {
        this.createdEqual = created;
        return this;
    }
     /** 更新时间 **/
    private Date updatedStart;
    public Date getUpdatedStart () {
        return updatedStart;
    }
    public SearchItemQuery setUpdatedStart(Date updated) {
        this.updatedStart = updated;
        return this;
    }

    private Date updatedEnd;
    public Date getUpdatedEnd () {
        return updatedEnd;
    }
    public SearchItemQuery setUpdatedEnd(Date updated) {
        this.updatedEnd = updated;
        return this;
    }

    private Date updatedEqual;
    public Date getUpdatedEqual () {
        return updatedEqual;
    }
    public SearchItemQuery setUpdatedEqual(Date updated) {
        this.updatedEqual = updated;
        return this;
    }
	/** 0等待收录,1:收录成功,2:wish中暂未收到该产品 **/
    private Integer status;
	public Integer getStatus () {
    	return status;
   	}
    public SearchItemQuery setStatus(Integer status) {
    	this.status = status;
    	return this;
    }
	/** 用户id **/
    private String userId;
	public String getUserId () {
    	return userId;
   	}
    public SearchItemQuery setUserId(String userId) {
    	this.userId = userId;
    	return this;
    }
	/** 1:收录店铺，2收录产品 **/
    private Integer type;
	public Integer getType () {
    	return type;
   	}
    public SearchItemQuery setType(Integer type) {
    	this.type = type;
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
	public SearchItemQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：产品id
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyItemId(boolean isAsc){
		orderFields.add(new OrderField("item_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：店铺名称
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyMerchantName(boolean isAsc){
		orderFields.add(new OrderField("merchant_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：创建时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyCreated(boolean isAsc){
		orderFields.add(new OrderField("created",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：更新时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyUpdated(boolean isAsc){
		orderFields.add(new OrderField("updated",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：0等待收录,1:收录成功,2:wish中暂未收到该产品
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyStatus(boolean isAsc){
		orderFields.add(new OrderField("status",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：用户id
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：1:收录店铺，2收录产品
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchItemQuery orderbyType(boolean isAsc){
		orderFields.add(new OrderField("type",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public SearchItemQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("item_id", "itemId");
                    fieldMap.put("merchant_name", "merchantName");
                    fieldMap.put("created", "created");
                    fieldMap.put("updated", "updated");
                    fieldMap.put("status", "status");
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("type", "type");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public SearchItemQuery  setFields(String fields){
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
