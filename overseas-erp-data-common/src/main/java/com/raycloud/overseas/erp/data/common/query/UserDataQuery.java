package com.raycloud.overseas.erp.data.common.query;

                                import java.util.*;

/**
 * 数据用户
 * @author zhanxiaofeng@raycloud.com
 */
public class UserDataQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** user_id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public UserDataQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public UserDataQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** uname **/
    private String uname;
	public String getUname () {
    	return uname;
   	}
    public UserDataQuery setUname(String uname) {
    	this.uname = uname;
    	return this;
    }
	/** mobile **/
    private String mobile;
	public String getMobile () {
    	return mobile;
   	}
    public UserDataQuery setMobile(String mobile) {
    	this.mobile = mobile;
    	return this;
    }
	/** email **/
    private String email;
	public String getEmail () {
    	return email;
   	}
    public UserDataQuery setEmail(String email) {
    	this.email = email;
    	return this;
    }
	/** db_table_config **/
    private String dbTableConfig;
	public String getDbTableConfig () {
    	return dbTableConfig;
   	}
    public UserDataQuery setDbTableConfig(String dbTableConfig) {
    	this.dbTableConfig = dbTableConfig;
    	return this;
    }
     /** created **/
    private Date createdStart;
    public Date getCreatedStart () {
        return createdStart;
    }
    public UserDataQuery setCreatedStart(Date created) {
        this.createdStart = created;
        return this;
    }

    private Date createdEnd;
    public Date getCreatedEnd () {
        return createdEnd;
    }
    public UserDataQuery setCreatedEnd(Date created) {
        this.createdEnd = created;
        return this;
    }

    private Date createdEqual;
    public Date getCreatedEqual () {
        return createdEqual;
    }
    public UserDataQuery setCreatedEqual(Date created) {
        this.createdEqual = created;
        return this;
    }
     /** update_time **/
    private Date updateTimeStart;
    public Date getUpdateTimeStart () {
        return updateTimeStart;
    }
    public UserDataQuery setUpdateTimeStart(Date updateTime) {
        this.updateTimeStart = updateTime;
        return this;
    }

    private Date updateTimeEnd;
    public Date getUpdateTimeEnd () {
        return updateTimeEnd;
    }
    public UserDataQuery setUpdateTimeEnd(Date updateTime) {
        this.updateTimeEnd = updateTime;
        return this;
    }

    private Date updateTimeEqual;
    public Date getUpdateTimeEqual () {
        return updateTimeEqual;
    }
    public UserDataQuery setUpdateTimeEqual(Date updateTime) {
        this.updateTimeEqual = updateTime;
        return this;
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
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：id
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：uname
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyUname(boolean isAsc){
		orderFields.add(new OrderField("uname",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：mobile
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyMobile(boolean isAsc){
		orderFields.add(new OrderField("mobile",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：email
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyEmail(boolean isAsc){
		orderFields.add(new OrderField("email",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：db_table_config
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyDbTableConfig(boolean isAsc){
		orderFields.add(new OrderField("db_table_config",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：created
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyCreated(boolean isAsc){
		orderFields.add(new OrderField("created",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：update_time
	 * @param isAsc 是否升序，否则为降序
	 */
	public UserDataQuery orderbyUpdateTime(boolean isAsc){
		orderFields.add(new OrderField("update_time",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public UserDataQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("id", "id");
                    fieldMap.put("uname", "uname");
                    fieldMap.put("mobile", "mobile");
                    fieldMap.put("email", "email");
                    fieldMap.put("db_table_config", "dbTableConfig");
                    fieldMap.put("created", "created");
                    fieldMap.put("update_time", "updateTime");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public UserDataQuery  setFields(String fields){
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
