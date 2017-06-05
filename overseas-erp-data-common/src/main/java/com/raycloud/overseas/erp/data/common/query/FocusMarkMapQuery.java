package com.raycloud.overseas.erp.data.common.query;

                    import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class FocusMarkMapQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public FocusMarkMapQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** 宝贝id或店铺id **/
    private String focusId;
	public String getFocusId () {
    	return focusId;
   	}
    public FocusMarkMapQuery setFocusId(String focusId) {
    	this.focusId = focusId;
    	return this;
    }
	/** user_id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public FocusMarkMapQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** mark_id **/
    private Integer markId;
	public Integer getMarkId () {
    	return markId;
   	}
    public FocusMarkMapQuery setMarkId(Integer markId) {
    	this.markId = markId;
    	return this;
    }
	/** 0：关注店铺，1关注产品 **/
    private Integer focusType;
	public Integer getFocusType () {
    	return focusType;
   	}
    public FocusMarkMapQuery setFocusType(Integer focusType) {
    	this.focusType = focusType;
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
	 * 关注店铺或宝贝列表
	 */
	private List<String> focusIdList;
	public List<String> getFocusIdList() {
		return focusIdList;
	}
	public void setFocusIdList(List<String> focusIdList) {
		this.focusIdList = focusIdList;
	}

	/**
	 * 标签列表
	 */
	private List<Integer> markIdList;

	public List<Integer> getMarkIdList() {
		return markIdList;
	}

	public void setMarkIdList(List<Integer> markIdList) {
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
	 * 设置排序按属性：id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMarkMapQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：宝贝id或店铺id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMarkMapQuery orderbyFocusId(boolean isAsc){
		orderFields.add(new OrderField("focus_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMarkMapQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：mark_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMarkMapQuery orderbyMarkId(boolean isAsc){
		orderFields.add(new OrderField("mark_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：0：关注店铺，1关注产品
	 * @param isAsc 是否升序，否则为降序
	 */
	public FocusMarkMapQuery orderbyFocusType(boolean isAsc){
		orderFields.add(new OrderField("focus_type",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public FocusMarkMapQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("focus_id", "focusId");
                    fieldMap.put("user_id", "userId");
                    fieldMap.put("mark_id", "markId");
                    fieldMap.put("focus_type", "focusType");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public FocusMarkMapQuery  setFields(String fields){
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
