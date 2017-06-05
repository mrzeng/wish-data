package com.raycloud.overseas.erp.data.common.query;

                            import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class UserCommonCatQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private String id;
	public String getId () {
    	return id;
   	}
    public UserCommonCatQuery setId(String id) {
    	this.id = id;
    	return this;
    }
	/** 行业id **/
    private String catId;
	public String getCatId () {
    	return catId;
   	}
    public UserCommonCatQuery setCatId(String catId) {
    	this.catId = catId;
    	return this;
    }
	/** 行业名称 **/
    private String catName;
	public String getCatName () {
    	return catName;
   	}
    public UserCommonCatQuery setCatName(String catName) {
    	this.catName = catName;
    	return this;
    }
	/** 用户id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public UserCommonCatQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** 平台类型 **/
    private Integer type;
	public Integer getType () {
    	return type;
   	}
    public UserCommonCatQuery setType(Integer type) {
    	this.type = type;
    	return this;
    }
     /** created **/
    private Date createdStart;
    public Date getCreatedStart () {
        return createdStart;
    }
    public UserCommonCatQuery setCreatedStart(Date created) {
        this.createdStart = created;
        return this;
    }

    private Date createdEnd;
    public Date getCreatedEnd () {
        return createdEnd;
    }
    public UserCommonCatQuery setCreatedEnd(Date created) {
        this.createdEnd = created;
        return this;
    }

    private Date createdEqual;
    public Date getCreatedEqual () {
        return createdEqual;
    }
    public UserCommonCatQuery setCreatedEqual(Date created) {
        this.createdEqual = created;
        return this;
    }
	/** cat_index **/
    private Integer catIndex;
	public Integer getCatIndex () {
    	return catIndex;
   	}
    public UserCommonCatQuery setCatIndex(Integer catIndex) {
    	this.catIndex = catIndex;
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
	public UserCommonCatQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：行业id
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserCommonCatQuery orderbyCatId(boolean isAsc){
		orderFields.add(new OrderField("cat_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：行业名称
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserCommonCatQuery orderbyCatName(boolean isAsc){
		orderFields.add(new OrderField("cat_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：用户id
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserCommonCatQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：平台类型
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserCommonCatQuery orderbyType(boolean isAsc){
		orderFields.add(new OrderField("type",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：created
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserCommonCatQuery orderbyCreated(boolean isAsc){
		orderFields.add(new OrderField("created",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：cat_index
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserCommonCatQuery orderbyCatIndex(boolean isAsc){
		orderFields.add(new OrderField("cat_index",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public UserCommonCatQuery setKeys(List<String> keys) {
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
                    fieldMap.put("cat_id", "catId");
                    fieldMap.put("cat_name", "catName");
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("type", "type");
                    fieldMap.put("created", "created");
                    fieldMap.put("cat_index", "catIndex");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public UserCommonCatQuery  setFields(String fields){
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
