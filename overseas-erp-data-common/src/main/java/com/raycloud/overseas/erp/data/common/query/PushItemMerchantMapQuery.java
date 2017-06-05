package com.raycloud.overseas.erp.data.common.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *推送宝贝店铺映射表
 * @author zhanxiaofeng@raycloud.com
 */
public class PushItemMerchantMapQuery extends BaseQuery {
	public PushItemMerchantMapQuery() {
	}

	public PushItemMerchantMapQuery(Long userId) {
		this.userId = userId;
	}

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** id **/
    private Integer id;
	public Integer getId () {
    	return id;
   	}
    public PushItemMerchantMapQuery setId(Integer id) {
    	this.id = id;
    	return this;
    }
	/** user_id **/
    private Long userId;
	public Long getUserId () {
    	return userId;
   	}
    public PushItemMerchantMapQuery setUserId(Long userId) {
    	this.userId = userId;
    	return this;
    }
	/** merchant_id **/
    private String merchantId;
	public String getMerchantId () {
    	return merchantId;
   	}
    public PushItemMerchantMapQuery setMerchantId(String merchantId) {
    	this.merchantId = merchantId;
    	return this;
    }
	/** merchant_name **/
    private String merchantName;
	public String getMerchantName () {
    	return merchantName;
   	}
    public PushItemMerchantMapQuery setMerchantName(String merchantName) {
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
	public PushItemMerchantMapQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemMerchantMapQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：merchant_id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemMerchantMapQuery orderbyMerchantId(boolean isAsc){
		orderFields.add(new OrderField("merchant_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：merchant_name
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemMerchantMapQuery orderbyMerchantName(boolean isAsc){
		orderFields.add(new OrderField("merchant_name",isAsc?"ASC":"DESC"));
		return this;
	}
      private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public PushItemMerchantMapQuery setKeys(List<Integer> keys) {
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
                    fieldMap.put("merchant_id", "merchantId");
                    fieldMap.put("merchant_name", "merchantName");
                }
        return fieldMap;
    }

    public String getFields(){
        return this.fields;
    }
    public PushItemMerchantMapQuery  setFields(String fields){
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
