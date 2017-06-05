package com.raycloud.overseas.erp.data.common.query;

                                    import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class SearchConditionQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public SearchConditionQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 搜索名称 **/
    private String name;
	public String getName () {
    	return name;
   	}
    public SearchConditionQuery setName(String name) {
    	this.name = name;
    	return this;
    }
	/** 搜索描述 **/
    private String description;
	public String getDescription () {
    	return description;
   	}
    public SearchConditionQuery setDescription(String description) {
    	this.description = description;
    	return this;
    }
	/** 搜索内容 **/
    private String filter;
	public String getFilter () {
    	return filter;
   	}
    public SearchConditionQuery setFilter(String filter) {
    	this.filter = filter;
    	return this;
    }
	/** 用户id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public SearchConditionQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** 团队创始人id **/
    private Integer teamFounderId;
	public Integer getTeamFounderId () {
    	return teamFounderId;
   	}
    public SearchConditionQuery setTeamFounderId(Integer teamFounderId) {
    	this.teamFounderId = teamFounderId;
    	return this;
    }
	/** 模块名 **/
    private String status;
	public String getStatus () {
    	return status;
   	}
    public SearchConditionQuery setStatus(String status) {
    	this.status = status;
    	return this;
    }
     /** 创建时间 **/
    private Date createdAtStart;
    public Date getCreatedAtStart () {
        return createdAtStart;
    }
    public SearchConditionQuery setCreatedAtStart(Date createdAt) {
        this.createdAtStart = createdAt;
        return this;
    }

    private Date createdAtEnd;
    public Date getCreatedAtEnd () {
        return createdAtEnd;
    }
    public SearchConditionQuery setCreatedAtEnd(Date createdAt) {
        this.createdAtEnd = createdAt;
        return this;
    }

    private Date createdAtEqual;
    public Date getCreatedAtEqual () {
        return createdAtEqual;
    }
    public SearchConditionQuery setCreatedAtEqual(Date createdAt) {
        this.createdAtEqual = createdAt;
        return this;
    }
     /** 更新时间 **/
    private Date updatedAtStart;
    public Date getUpdatedAtStart () {
        return updatedAtStart;
    }
    public SearchConditionQuery setUpdatedAtStart(Date updatedAt) {
        this.updatedAtStart = updatedAt;
        return this;
    }

    private Date updatedAtEnd;
    public Date getUpdatedAtEnd () {
        return updatedAtEnd;
    }
    public SearchConditionQuery setUpdatedAtEnd(Date updatedAt) {
        this.updatedAtEnd = updatedAt;
        return this;
    }

    private Date updatedAtEqual;
    public Date getUpdatedAtEqual () {
        return updatedAtEqual;
    }
    public SearchConditionQuery setUpdatedAtEqual(Date updatedAt) {
        this.updatedAtEqual = updatedAt;
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
	public SearchConditionQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：搜索名称
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyName(boolean isAsc){
		orderFields.add(new OrderField("name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：搜索描述
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyDescription(boolean isAsc){
		orderFields.add(new OrderField("description",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：搜索内容
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyFilter(boolean isAsc){
		orderFields.add(new OrderField("filter",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：用户id
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：团队创始人id
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyTeamFounderId(boolean isAsc){
		orderFields.add(new OrderField("team_founder_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：模块名
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyStatus(boolean isAsc){
		orderFields.add(new OrderField("status",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：创建时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyCreatedAt(boolean isAsc){
		orderFields.add(new OrderField("created_at",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：更新时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public SearchConditionQuery orderbyUpdatedAt(boolean isAsc){
		orderFields.add(new OrderField("updated_at",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public SearchConditionQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("name", "name");
                    fieldMap.put("description", "description");
                    fieldMap.put("filter", "filter");
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("team_founder_id", "teamFounderId");
                    fieldMap.put("status", "status");
                    fieldMap.put("created_at", "createdAt");
                    fieldMap.put("updated_at", "updatedAt");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public SearchConditionQuery  setFields(String fields){
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
