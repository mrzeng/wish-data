package com.raycloud.overseas.erp.data.common.query;

                                import java.math.BigDecimal;
                import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class GuessItemQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** 产品id **/
    private String itemId;
	public String getItemId () {
    	return itemId;
   	}
    public GuessItemQuery setItemId(String itemId) {
    	this.itemId = itemId;
    	return this;
    }
	/** 产品图片url **/
    private String itemUrl;
	public String getItemUrl () {
    	return itemUrl;
   	}
    public GuessItemQuery setItemUrl(String itemUrl) {
    	this.itemUrl = itemUrl;
    	return this;
    }
	/** 产品推荐指数 **/
    private Integer star;
	public Integer getStar () {
    	return star;
   	}
    public GuessItemQuery setStar(Integer star) {
    	this.star = star;
    	return this;
    }
	/** 推荐理由 **/
    private String recommand;
	public String getRecommand () {
    	return recommand;
   	}
    public GuessItemQuery setRecommand(String recommand) {
    	this.recommand = recommand;
    	return this;
    }
	/** cat_names **/
    private String catNames;
	public String getCatNames () {
    	return catNames;
   	}
    public GuessItemQuery setCatNames(String catNames) {
    	this.catNames = catNames;
    	return this;
    }
	/** amount_7 **/
    private Integer amount7;
	public Integer getAmount7 () {
    	return amount7;
   	}
    public GuessItemQuery setAmount7(Integer amount7) {
    	this.amount7 = amount7;
    	return this;
    }
	/** price **/
    private BigDecimal price;
	public BigDecimal getPrice () {
    	return price;
   	}
    public GuessItemQuery setPrice(BigDecimal price) {
    	this.price = price;
    	return this;
    }
	/** wish_feight_price **/
    private BigDecimal wishFeightPrice;
	public BigDecimal getWishFeightPrice () {
    	return wishFeightPrice;
   	}
    public GuessItemQuery setWishFeightPrice(BigDecimal wishFeightPrice) {
    	this.wishFeightPrice = wishFeightPrice;
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
	 * 设置排序按属性：产品id
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyItemId(boolean isAsc){
		orderFields.add(new OrderField("item_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：产品图片url
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyItemUrl(boolean isAsc){
		orderFields.add(new OrderField("item_url",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：产品推荐指数
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyStar(boolean isAsc){
		orderFields.add(new OrderField("star",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推荐理由
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyRecommand(boolean isAsc){
		orderFields.add(new OrderField("recommand",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：cat_names
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyCatNames(boolean isAsc){
		orderFields.add(new OrderField("cat_names",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：amount_7
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyAmount7(boolean isAsc){
		orderFields.add(new OrderField("amount_7",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：price
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyPrice(boolean isAsc){
		orderFields.add(new OrderField("price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：wish_feight_price
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessItemQuery orderbyWishFeightPrice(boolean isAsc){
		orderFields.add(new OrderField("wish_feight_price",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public GuessItemQuery setKeys(List<String> keys) {
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
                    fieldMap.put("item_id", "itemId");
                    fieldMap.put("item_url", "itemUrl");
                    fieldMap.put("star", "star");
                    fieldMap.put("recommand", "recommand");
                    fieldMap.put("cat_names", "catNames");
                    fieldMap.put("amount_7", "amount7");
                    fieldMap.put("price", "price");
                    fieldMap.put("wish_feight_price", "wishFeightPrice");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public GuessItemQuery  setFields(String fields){
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
