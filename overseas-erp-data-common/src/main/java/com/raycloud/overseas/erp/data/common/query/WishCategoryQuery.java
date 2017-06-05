package com.raycloud.overseas.erp.data.common.query;

                                import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class WishCategoryQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** 主键id **/
    private String id;
	public String getId () {
    	return id;
   	}
    public WishCategoryQuery setId(String id) {
    	this.id = id;
    	return this;
    }
	/** 行业类目id **/
    private String catId;
	public String getCatId () {
    	return catId;
   	}
    public WishCategoryQuery setCatId(String catId) {
    	this.catId = catId;
    	return this;
    }
	/** 中文名 **/
    private String chineseName;
	public String getChineseName () {
    	return chineseName;
   	}
    public WishCategoryQuery setChineseName(String chineseName) {
    	this.chineseName = chineseName;
    	return this;
    }
	/** english_name **/
    private String englishName;
	public String getEnglishName () {
    	return englishName;
   	}
    public WishCategoryQuery setEnglishName(String englishName) {
    	this.englishName = englishName;
    	return this;
    }
	/** 父类目id **/
    private String parentId;
	public String getParentId () {
    	return parentId;
   	}
    public WishCategoryQuery setParentId(String parentId) {
    	this.parentId = parentId;
    	return this;
    }
	/** 层级 **/
    private Integer level;
	public Integer getLevel () {
    	return level;
   	}
    public WishCategoryQuery setLevel(Integer level) {
    	this.level = level;
    	return this;
    }
     /** updated **/
    private Date updatedStart;
    public Date getUpdatedStart () {
        return updatedStart;
    }
    public WishCategoryQuery setUpdatedStart(Date updated) {
        this.updatedStart = updated;
        return this;
    }

    private Date updatedEnd;
    public Date getUpdatedEnd () {
        return updatedEnd;
    }
    public WishCategoryQuery setUpdatedEnd(Date updated) {
        this.updatedEnd = updated;
        return this;
    }

    private Date updatedEqual;
    public Date getUpdatedEqual () {
        return updatedEqual;
    }
    public WishCategoryQuery setUpdatedEqual(Date updated) {
        this.updatedEqual = updated;
        return this;
    }
	/** is_valid **/
    private Integer isValid;
	public Integer getIsValid () {
    	return isValid;
   	}
    public WishCategoryQuery setIsValid(Integer isValid) {
    	this.isValid = isValid;
    	return this;
    }
	private List<String> catIdList;

	public List<String> getCatIdList() {
		return catIdList;
	}

	public void setCatIdList(List<String> catIdList) {
		this.catIdList = catIdList;
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
	 * 设置排序按属性：主键id
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：行业类目id
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyCatId(boolean isAsc){
		orderFields.add(new OrderField("cat_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：中文名
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyChineseName(boolean isAsc){
		orderFields.add(new OrderField("chinese_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：english_name
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyEnglishName(boolean isAsc){
		orderFields.add(new OrderField("english_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：父类目id
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyParentId(boolean isAsc){
		orderFields.add(new OrderField("parent_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：层级
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyLevel(boolean isAsc){
		orderFields.add(new OrderField("level",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：updated
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyUpdated(boolean isAsc){
		orderFields.add(new OrderField("updated",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：is_valid
	 * @param isAsc 是否升序，否则为降序
	 */
	public WishCategoryQuery orderbyIsValid(boolean isAsc){
		orderFields.add(new OrderField("is_valid",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public WishCategoryQuery setKeys(List<String> keys) {
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
                    fieldMap.put("chinese_name", "chineseName");
                    fieldMap.put("english_name", "englishName");
                    fieldMap.put("parent_id", "parentId");
                    fieldMap.put("level", "level");
                    fieldMap.put("updated", "updated");
                    fieldMap.put("is_valid", "isValid");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public WishCategoryQuery  setFields(String fields){
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
