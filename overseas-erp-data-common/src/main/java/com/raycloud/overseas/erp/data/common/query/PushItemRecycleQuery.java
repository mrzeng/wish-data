package com.raycloud.overseas.erp.data.common.query;

            import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class PushItemRecycleQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
    public PushItemRecycleQuery() {
    }

    public PushItemRecycleQuery(Integer userId, Integer founderId) {
        this.userId = userId;
        this.founderId = founderId;
    }

    /** 用户id+创始人id+宝贝id **/
    private String id;
	public String getId () {
    	return id;
   	}
    public PushItemRecycleQuery setId(String id) {
    	this.id = id;
    	return this;
    }
	/** user_id **/
    private Integer userId;
	public Integer getUserId () {
    	return userId;
   	}
    public PushItemRecycleQuery setUserId(Integer userId) {
    	this.userId = userId;
    	return this;
    }
	/** founder_id **/
    private Integer founderId;
	public Integer getFounderId () {
    	return founderId;
   	}
    public PushItemRecycleQuery setFounderId(Integer founderId) {
    	this.founderId = founderId;
    	return this;
    }

    private String sortField;

    private String sortOrder;

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        if(searchKey!=null&&!searchKey.trim().equals("")){
            this.searchKey = searchKey.trim();
        }
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
	 * 设置排序按属性：用户id+创始人id+宝贝id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemRecycleQuery orderbyId(boolean isAsc){
        this.sortField = "id";
        this.sortOrder = isAsc?"ASC":"DESC";
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemRecycleQuery orderbyUserId(boolean isAsc){
        this.sortField = "user_id";
        this.sortOrder = isAsc?"ASC":"DESC";
		return this;
	}
	/**
	 * 设置排序按属性：founder_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemRecycleQuery orderbyFounderId(boolean isAsc){
        this.sortField = "founder_id";
        this.sortOrder = isAsc?"ASC":"DESC";
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public PushItemRecycleQuery setKeys(List<Integer> keys) {
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
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushItemRecycleQuery  setFields(String fields){
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
