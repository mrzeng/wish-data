package com.raycloud.overseas.erp.data.common.query;

                            import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class GuessMerchantQuery extends BaseQuery {

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** 店铺id **/
    private String merchantId;
	public String getMerchantId () {
    	return merchantId;
   	}
    public GuessMerchantQuery setMerchantId(String merchantId) {
    	this.merchantId = merchantId;
    	return this;
    }
	/** 店铺图片url **/
    private String merchantUrl;
	public String getMerchantUrl () {
    	return merchantUrl;
   	}
    public GuessMerchantQuery setMerchantUrl(String merchantUrl) {
    	this.merchantUrl = merchantUrl;
    	return this;
    }
	/** 推荐指数 **/
    private Integer star;
	public Integer getStar () {
    	return star;
   	}
    public GuessMerchantQuery setStar(Integer star) {
    	this.star = star;
    	return this;
    }
	/** 推荐理由 **/
    private String recommand;
	public String getRecommand () {
    	return recommand;
   	}
    public GuessMerchantQuery setRecommand(String recommand) {
    	this.recommand = recommand;
    	return this;
    }
	/** merchant_name **/
    private String merchantName;
	public String getMerchantName () {
    	return merchantName;
   	}
    public GuessMerchantQuery setMerchantName(String merchantName) {
    	this.merchantName = merchantName;
    	return this;
    }
	/** item_count **/
    private Integer itemCount;
	public Integer getItemCount () {
    	return itemCount;
   	}
    public GuessMerchantQuery setItemCount(Integer itemCount) {
    	this.itemCount = itemCount;
    	return this;
    }
	/** amount_7 **/
    private Integer amount7;
	public Integer getAmount7 () {
    	return amount7;
   	}
    public GuessMerchantQuery setAmount7(Integer amount7) {
    	this.amount7 = amount7;
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
	 * 设置排序按属性：店铺id
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyMerchantId(boolean isAsc){
		orderFields.add(new OrderField("merchant_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：店铺图片url
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyMerchantUrl(boolean isAsc){
		orderFields.add(new OrderField("merchant_url",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推荐指数
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyStar(boolean isAsc){
		orderFields.add(new OrderField("star",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推荐理由
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyRecommand(boolean isAsc){
		orderFields.add(new OrderField("recommand",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：merchant_name
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyMerchantName(boolean isAsc){
		orderFields.add(new OrderField("merchant_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：item_count
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyItemCount(boolean isAsc){
		orderFields.add(new OrderField("item_count",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：amount_7
	 * @param isAsc 是否升序，否则为降序
	 */
	public GuessMerchantQuery orderbyAmount7(boolean isAsc){
		orderFields.add(new OrderField("amount_7",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<String> keys;

    public List<String> getKeys() {
        return keys;
    }

    public GuessMerchantQuery setKeys(List<String> keys) {
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
                    fieldMap.put("merchant_id", "merchantId");
                    fieldMap.put("merchant_url", "merchantUrl");
                    fieldMap.put("star", "star");
                    fieldMap.put("recommand", "recommand");
                    fieldMap.put("merchant_name", "merchantName");
                    fieldMap.put("item_count", "itemCount");
                    fieldMap.put("amount_7", "amount7");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public GuessMerchantQuery  setFields(String fields){
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
