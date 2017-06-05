package com.raycloud.overseas.erp.data.common.query;

                                    import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class FocusMerchantQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** user_id **/
    private String userId;
	public String getUserId () {
    	return userId;
   	}
    public FocusMerchantQuery setUserId(String userId) {
    	this.userId = userId;
    	return this;
    }
	/** merchant_id **/
    private String merchantId;
	public String getMerchantId () {
    	return merchantId;
   	}
    public FocusMerchantQuery setMerchantId(String merchantId) {
    	this.merchantId = merchantId;
    	return this;
    }
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public FocusMerchantQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** focus **/
    private Integer focus;
	public Integer getFocus () {
    	return focus;
   	}
    public FocusMerchantQuery setFocus(Integer focus) {
    	this.focus = focus;
    	return this;
    }
     /** updated **/
    private Date updatedStart;
    public Date getUpdatedStart () {
        return updatedStart;
    }
    public FocusMerchantQuery setUpdatedStart(Date updated) {
        this.updatedStart = updated;
        return this;
    }

    private Date updatedEnd;
    public Date getUpdatedEnd () {
        return updatedEnd;
    }
    public FocusMerchantQuery setUpdatedEnd(Date updated) {
        this.updatedEnd = updated;
        return this;
    }

    private Date updatedEqual;
    public Date getUpdatedEqual () {
        return updatedEqual;
    }
    public FocusMerchantQuery setUpdatedEqual(Date updated) {
        this.updatedEqual = updated;
        return this;
    }
     /** created **/
    private Date createdStart;
    public Date getCreatedStart () {
        return createdStart;
    }
    public FocusMerchantQuery setCreatedStart(Date created) {
        this.createdStart = created;
        return this;
    }

    private Date createdEnd;
    public Date getCreatedEnd () {
        return createdEnd;
    }
    public FocusMerchantQuery setCreatedEnd(Date created) {
        this.createdEnd = created;
        return this;
    }

    private Date createdEqual;
    public Date getCreatedEqual () {
        return createdEqual;
    }
    public FocusMerchantQuery setCreatedEqual(Date created) {
        this.createdEqual = created;
        return this;
    }
	/** 标签id(对应mark_info表) **/
    private Integer markId;
	public Integer getMarkId () {
    	return markId;
   	}
    public FocusMerchantQuery setMarkId(Integer markId) {
    	this.markId = markId;
    	return this;
    }
	/** 是否采集，0：未采集，1：采集 **/
    private Long collected;
	public Long getCollected () {
    	return collected;
   	}
    public FocusMerchantQuery setCollected(Long collected) {
    	this.collected = collected;
    	return this;
    }
	/** merchant_name **/
    private String merchantName;
	public String getMerchantName () {
    	return merchantName;
   	}
    public FocusMerchantQuery setMerchantName(String merchantName) {
    	this.merchantName = merchantName;
    	return this;
    }

	private Integer founderId;

	public Integer getFounderId() {
		return founderId;
	}

	public void setFounderId(Integer founderId) {
		this.founderId = founderId;
	}

	/**
	 * 标签列表
	 */
	private List<String> markIdList;
	public List<String> getMarkIdList() {
		return markIdList;
	}
	public void setMarkIdList(List<String> markIdList) {
		this.markIdList = markIdList;
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
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：merchant_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyMerchantId(boolean isAsc){
		orderFields.add(new OrderField("merchant_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：focus
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyFocus(boolean isAsc){
		orderFields.add(new OrderField("focus",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：updated
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyUpdated(boolean isAsc){
		orderFields.add(new OrderField("updated",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：created
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyCreated(boolean isAsc){
		orderFields.add(new OrderField("created",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：标签id(对应mark_info表)
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyMarkId(boolean isAsc){
		orderFields.add(new OrderField("mark_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：是否采集，0：未采集，1：采集
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyCollected(boolean isAsc){
		orderFields.add(new OrderField("collected",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：merchant_name
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMerchantQuery orderbyMerchantName(boolean isAsc){
		orderFields.add(new OrderField("merchant_name",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public FocusMerchantQuery setKeys(List<String> keys) {
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
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("merchant_id", "merchantId");
                    fieldMap.put("id", "id");
                    fieldMap.put("focus", "focus");
                    fieldMap.put("updated", "updated");
                    fieldMap.put("created", "created");
                    fieldMap.put("mark_id", "markId");
                    fieldMap.put("collected", "collected");
                    fieldMap.put("merchant_name", "merchantName");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public FocusMerchantQuery  setFields(String fields){
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
