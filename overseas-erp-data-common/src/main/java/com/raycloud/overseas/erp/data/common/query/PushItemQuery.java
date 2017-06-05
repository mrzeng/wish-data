package com.raycloud.overseas.erp.data.common.query;

import com.raycloud.overseas.erp.data.common.util.TB;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import org.springframework.util.StringUtils;

import java.util.*;
/**
 *
 * @author zhanxiaofeng@raycloud.com
 */
public class PushItemQuery extends BaseQuery {

	public PushItemQuery() {
	}

	public PushItemQuery(String pushTimeEqual,String pushTimeStart, String pushTimeEnd, Integer userId, Integer founderId) {
		this.pushTimeEqual = pushTimeEqual;
		this.pushTimeStart = pushTimeStart;
		this.pushTimeEnd = pushTimeEnd;
//		this.deleteFlag = deleteFlag;
//		this.localCollected = localCollected;
		this.founderId = founderId;
		this.userId = userId;
		this.tableId = TB.ID(TB.getStoreConfig(founderId),TB.PI_FIELD);
	}

	/**==============================批量查询、更新、删除时的Where条件设置==================================**/

	private List<String> itemIdList;

	public List<String> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<String> itemIdList) {
		this.itemIdList = itemIdList;
	}

	private String merchantIds;

	public String getMerchantIds() {
		return merchantIds;
	}

	public void setMerchantIds(String merchantIds) {
		this.merchantIds = merchantIds;
	}

	private List<String> merchantIdList;

	public List<String> getMerchantIdList() {
		return merchantIdList;
	}

	public void setMerchantIdList(List<String> merchantIdList) {
		this.merchantIdList = merchantIdList;
	}

	private String catId;

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	/*******************************************************************/
	/** id **/
	private Integer id;
	public Integer getId () {
		return id;
	}
	public PushItemQuery setId(Integer id) {
		this.id = id;
		return this;
	}
	/** 宝贝id **/
	private String itemId;
	public String getItemId () {
		return itemId;
	}
	public PushItemQuery setItemId(String itemId) {
		this.itemId = itemId;
		return this;
	}
	/** 宝贝昵称 **/
	private String itemName;
	public String getItemName () {
		return itemName;
	}
	public PushItemQuery setItemName(String itemName) {
		this.itemName = itemName;
		return this;
	}
	/** 用户id **/
	private Integer userId;
	public Integer getUserId () {
		return userId;
	}
	public PushItemQuery setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
//	/** 是否采集到本地 **/
//	private Boolean localCollected;
//	public Boolean getLocalCollected () {
//		return localCollected;
//	}
//	public PushItemQuery setLocalCollected(Boolean localCollected) {
//		this.localCollected = localCollected;
//		return this;
//	}
	/** 是否关注 **/
	private Boolean localFocus;
	public Boolean getLocalFocus () {
		return localFocus;
	}
	public PushItemQuery setLocalFocus(Boolean localFocus) {
		this.localFocus = localFocus;
		return this;
	}
	/** 推送时间 **/
	private String pushTimeStart;
	private String pushTimeEnd;
	private String pushTimeEqual;

	public String getPushTimeEqual() {
		return pushTimeEqual;
	}

	public void setPushTimeEqual(String pushTimeEqual) {
		this.pushTimeEqual = pushTimeEqual;
	}

	public String getPushTimeStart() {
		return pushTimeStart;
	}

	public void setPushTimeStart(String pushTimeStart) {
		this.pushTimeStart = pushTimeStart;
	}

	public String getPushTimeEnd() {
		return pushTimeEnd;
	}

	public void setPushTimeEnd(String pushTimeEnd) {
		this.pushTimeEnd = pushTimeEnd;
	}

	/** 店铺id **/
	private String merchantId;
	public String getMerchantId () {
		return merchantId;
	}
	public PushItemQuery setMerchantId(String merchantId) {
		this.merchantId = merchantId;
		return this;
	}
//	/** 0:未删除，1已删除 **/
//	private Boolean deleteFlag;
//	public Boolean getDeleteFlag () {
//		return deleteFlag;
//	}
//	public PushItemQuery setDeleteFlag(Boolean deleteFlag) {
//		this.deleteFlag = deleteFlag;
//		return this;
//	}
	/** merchant_name **/
	private String merchantName;
	public String getMerchantName () {
		return merchantName;
	}
	public PushItemQuery setMerchantName(String merchantName) {
		this.merchantName = merchantName;
		return this;
	}
	/** 行业 **/
	private String catNames;
	public String getCatNames () {
		return catNames;
	}
	public PushItemQuery setCatNames(String catNames) {
		this.catNames = catNames;
		return this;
	}
	/** 上架时间 **/
//	private String genTime;
//	public String getGenTime () {
//		return genTime;
//	}
//	public PushItemQuery setGenTime(String genTime) {
//		this.genTime = genTime;
//		return this;
//	}
	private String genTimeStart;
	private String genTimeEnd;
	private String genTimeEqual;

	public String getGenTimeStart() {
		return genTimeStart;
	}

	public void setGenTimeStart(String genTimeStart) {
		this.genTimeStart = genTimeStart;
	}

	public String getGenTimeEnd() {
		return genTimeEnd;
	}

	public void setGenTimeEnd(String genTimeEnd) {
		this.genTimeEnd = genTimeEnd;
	}

	public String getGenTimeEqual() {
		return genTimeEqual;
	}

	public void setGenTimeEqual(String genTimeEqual) {
		this.genTimeEqual = genTimeEqual;
	}


	/** 销量增长率 **/
	private Double amountRate;
	public Double getAmountRate () {
		return amountRate;
	}
	public PushItemQuery setAmountRate(Double amountRate) {
		this.amountRate = amountRate;
		return this;
	}
	/** 产品评分 **/
	private Double rateScore;
	public Double getRateScore () {
		return rateScore;
	}
	public PushItemQuery setRateScore(Double rateScore) {
		this.rateScore = rateScore;
		return this;
	}
	/** 吊牌价格 **/
	private Double originalPrice;
	public Double getOriginalPrice () {
		return originalPrice;
	}
	public PushItemQuery setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
		return this;
	}
	/** 刊登价格 **/
	private Double sellerPrice;
	public Double getSellerPrice () {
		return sellerPrice;
	}
	public PushItemQuery setSellerPrice(Double sellerPrice) {
		this.sellerPrice = sellerPrice;
		return this;
	}
	private Double sellerPriceStart;
	private Double sellerPriceEnd;

	public Double getSellerPriceStart() {
		return sellerPriceStart;
	}

	public void setSellerPriceStart(Double sellerPriceStart) {
		this.sellerPriceStart = sellerPriceStart;
	}

	public Double getSellerPriceEnd() {
		return sellerPriceEnd;
	}

	public void setSellerPriceEnd(Double sellerPriceEnd) {
		this.sellerPriceEnd = sellerPriceEnd;
	}

	/** 刊登运费 **/
	private Double sellerFreightPrice;
	public Double getSellerFreightPrice () {
		return sellerFreightPrice;
	}
	public PushItemQuery setSellerFreightPrice(Double sellerFreightPrice) {
		this.sellerFreightPrice = sellerFreightPrice;
		return this;
	}
	/** 刊登总价 **/
	private Double sellerTotalPrice;
	public Double getSellerTotalPrice () {
		return sellerTotalPrice;
	}
	public PushItemQuery setSellerTotalPrice(Double sellerTotalPrice) {
		this.sellerTotalPrice = sellerTotalPrice;
		return this;
	}
	/** wish售价 **/
	private Double wishPrice;
	public Double getWishPrice () {
		return wishPrice;
	}
	public PushItemQuery setWishPrice(Double wishPrice) {
		this.wishPrice = wishPrice;
		return this;
	}
	private Double wishPriceStart;
	private Double wishPriceEnd;

	public Double getWishPriceStart() {
		return wishPriceStart;
	}

	public void setWishPriceStart(Double wishPriceStart) {
		this.sellerPriceStart = wishPriceStart;

//		this.wishPriceStart = wishPriceStart;
	}

	public Double getWishPriceEnd() {
		return wishPriceEnd;
	}

	public void setWishPriceEnd(Double wishPriceEnd) {
		this.sellerPriceEnd = wishPriceEnd;

//		this.wishPriceEnd = wishPriceEnd;
	}

	/** wish运费 **/
	private Double wishFreightPrice;
	public Double getWishFreightPrice () {
		return wishFreightPrice;
	}
	public PushItemQuery setWishFreightPrice(Double wishFreightPrice) {
		this.wishFreightPrice = wishFreightPrice;
		return this;
	}
	/** wish总价 **/
	private Double wishTotalPrice;
	public Double getWishTotalPrice () {
		return wishTotalPrice;
	}
	public PushItemQuery setWishTotalPrice(Double wishTotalPrice) {
		this.wishTotalPrice = wishTotalPrice;
		return this;
	}
	/** 7日日均销量 **/
	private Integer amount7;
	public Integer getAmount7 () {
		return amount7;
	}
	public PushItemQuery setAmount7(Integer amount7) {
		this.amount7 = amount7;
		return this;
	}
	private Integer amount7Start;
	private Integer amount7End;

	public Integer getAmount7Start() {
		return amount7Start;
	}

	public void setAmount7Start(Integer amount7Start) {
		this.amount7Start = amount7Start;
	}

	public Integer getAmount7End() {
		return amount7End;
	}

	public void setAmount7End(Integer amount7End) {
		this.amount7End = amount7End;
	}

	/** 累计销量 **/
	private Integer offer;
	public Integer getOffer () {
		return offer;
	}
	public PushItemQuery setOffer(Integer offer) {
		this.offer = offer;
		return this;
	}
	/** 7日日均收藏量 **/
	private Integer wishSave7;
	public Integer getWishSave7 () {
		return wishSave7;
	}
	public PushItemQuery setWishSave7(Integer wishSave7) {
		this.wishSave7 = wishSave7;
		return this;
	}
	private Integer wishSave7Start;
	private Integer wishSave7End;

	public Integer getWishSave7End() {
		return wishSave7End;
	}

	public void setWishSave7End(Integer wishSave7End) {
		this.wishSave7End = wishSave7End;
	}

	public Integer getWishSave7Start() {
		return wishSave7Start;
	}

	public void setWishSave7Start(Integer wishSave7Start) {
		this.wishSave7Start = wishSave7Start;
	}

	/** 累计收藏量 **/
	private Integer wishNum;
	public Integer getWishNum () {
		return wishNum;
	}
	public PushItemQuery setWishNum(Integer wishNum) {
		this.wishNum = wishNum;
		return this;
	}
	/** 7日日均评论 **/
	private Integer rateNum7;
	public Integer getRateNum7 () {
		return rateNum7;
	}
	public PushItemQuery setRateNum7(Integer rateNum7) {
		this.rateNum7 = rateNum7;
		return this;
	}
	/** 累计评论 **/
	private Integer rateNum;
	public Integer getRateNum () {
		return rateNum;
	}
	public PushItemQuery setRateNum(Integer rateNum) {
		this.rateNum = rateNum;
		return this;
	}

	private Integer rateNumStart;
	private Integer rateNumEnd;

	public Integer getRateNumStart() {
		return rateNumStart;
	}

	public void setRateNumStart(Integer rateNumStart) {
		this.rateNumStart = rateNumStart;
	}

	public Integer getRateNumEnd() {
		return rateNumEnd;
	}

	public void setRateNumEnd(Integer rateNumEnd) {
		this.rateNumEnd = rateNumEnd;
	}

	/** 行业id **/
	private String catIds;
	public String getCatIds () {
		return catIds;
	}
	public PushItemQuery setCatIds(String catIds) {
		this.catIds = catIds;
		return this;
	}

	private Integer founderId;

	public Integer getFounderId() {
		return founderId;
	}

	public void setFounderId(Integer founderId) {
		this.founderId = founderId;
	}

	private String rules;

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		if(!StringUtils.isEmpty(rules)){
			StringBuffer sb = new StringBuffer();
			String[] idArr = rules.split(",");
			for(int i = 0;i<idArr.length;i++){
				if(i==0){
					sb.append(" rules like '%"+"&"+idArr[i]+"*"+"%'");
				}else{
					sb.append(" OR rules like '%"+"&"+idArr[i]+"*"+"%'");
				}
			}
			this.rules = sb.toString();
		}

//		if(!StringUtils.isEmpty(rules)){
//			StringBuffer sb = new StringBuffer();
//			String[] idArr = rules.split(",");
//			for(int i = 0;i<idArr.length;i++){
//				if(i==0){
//					sb.append("&"+idArr[i]+"*");
//				}
//			}
//			this.rules = sb.toString();
//		}
	}

	private String sortField;

	private String sortOrder;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**************************工作台查询条件*********************************/
	//1:工作台采集，2工作台概览规则，3工作台每日推送总和点击，4工作台每日推送规则数量名称按钮点击，5推送规则7日总和跳转
	private Integer queryType;

	private String ruleId;

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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
	public PushItemQuery orderbyId(boolean isAsc){
		orderFields.add(new OrderField("id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：宝贝id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyItemId(boolean isAsc){
		orderFields.add(new OrderField("item_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：宝贝昵称
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyItemName(boolean isAsc){
		orderFields.add(new OrderField("item_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：用户id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyUserId(boolean isAsc){
		orderFields.add(new OrderField("user_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：是否热销
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbySaleHot(boolean isAsc){
		orderFields.add(new OrderField("sale_hot",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：是否销量暴增
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyAmountSurge(boolean isAsc){
		orderFields.add(new OrderField("amount_surge",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：是否最近上新
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyLatestGen(boolean isAsc){
		orderFields.add(new OrderField("latest_gen",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：是否采集到本地
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyLocalCollected(boolean isAsc){
		orderFields.add(new OrderField("local_collected",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：是否关注
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyLocalFocus(boolean isAsc){
		orderFields.add(new OrderField("local_focus",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：推送时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyPushTime(boolean isAsc){
		orderFields.add(new OrderField("push_time",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：店铺id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyMerchantId(boolean isAsc){
		orderFields.add(new OrderField("merchant_id",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：0:未删除，1已删除
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyDeleteFlag(boolean isAsc){
		orderFields.add(new OrderField("delete_flag",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：merchant_name
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyMerchantName(boolean isAsc){
		orderFields.add(new OrderField("merchant_name",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：行业
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyCatNames(boolean isAsc){
		orderFields.add(new OrderField("cat_names",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：上架时间
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyGenTime(boolean isAsc){
		orderFields.add(new OrderField("gen_time",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：销量增长率
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyAmountRate(boolean isAsc){
		orderFields.add(new OrderField("amount_rate",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：产品评分
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyRateScore(boolean isAsc){
		orderFields.add(new OrderField("rate_score",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：吊牌价格
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyOriginalPrice(boolean isAsc){
		orderFields.add(new OrderField("original_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：刊登价格
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbySellerPrice(boolean isAsc){
		orderFields.add(new OrderField("seller_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：刊登运费
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbySellerFreightPrice(boolean isAsc){
		orderFields.add(new OrderField("seller_freight_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：刊登总价
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbySellerTotalPrice(boolean isAsc){
		orderFields.add(new OrderField("seller_total_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：wish售价
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyWishPrice(boolean isAsc){
		orderFields.add(new OrderField("wish_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：wish运费
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyWishFreightPrice(boolean isAsc){
		orderFields.add(new OrderField("wish_freight_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：wish总价
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyWishTotalPrice(boolean isAsc){
		orderFields.add(new OrderField("wish_total_price",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：7日日均销量
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyAmount7(boolean isAsc){
		orderFields.add(new OrderField("amount_7",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：累计销量
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyOffer(boolean isAsc){
		orderFields.add(new OrderField("offer",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：7日日均收藏量
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyWishSave7(boolean isAsc){
		orderFields.add(new OrderField("wish_save_7",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：累计收藏量
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyWishNum(boolean isAsc){
		orderFields.add(new OrderField("wish_num",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：7日日均评论
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyRateNum7(boolean isAsc){
		orderFields.add(new OrderField("rate_num_7",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：累计评论
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyRateNum(boolean isAsc){
		orderFields.add(new OrderField("rate_num",isAsc?"ASC":"DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：行业id
	 * @param isAsc 是否升序，否则为降序
	 */
	public PushItemQuery orderbyCatIds(boolean isAsc){
		orderFields.add(new OrderField("cat_ids",isAsc?"ASC":"DESC"));
		return this;
	}
	private List<Integer> keys;

	public List<Integer> getKeys() {
		return keys;
	}

	public PushItemQuery setKeys(List<Integer> keys) {
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
			fieldMap.put("item_id", "itemId");
			fieldMap.put("item_name", "itemName");
			fieldMap.put("user_id", "userId");
			fieldMap.put("sale_hot", "saleHot");
			fieldMap.put("amount_surge", "amountSurge");
			fieldMap.put("latest_gen", "latestGen");
			fieldMap.put("local_collected", "localCollected");
			fieldMap.put("local_focus", "localFocus");
			fieldMap.put("push_time", "pushTime");
			fieldMap.put("merchant_id", "merchantId");
			fieldMap.put("delete_flag", "deleteFlag");
			fieldMap.put("merchant_name", "merchantName");
			fieldMap.put("cat_names", "catNames");
			fieldMap.put("gen_time", "genTime");
			fieldMap.put("amount_rate", "amountRate");
			fieldMap.put("rate_score", "rateScore");
			fieldMap.put("original_price", "originalPrice");
			fieldMap.put("seller_price", "sellerPrice");
			fieldMap.put("seller_freight_price", "sellerFreightPrice");
			fieldMap.put("seller_total_price", "sellerTotalPrice");
			fieldMap.put("wish_price", "wishPrice");
			fieldMap.put("wish_freight_price", "wishFreightPrice");
			fieldMap.put("wish_total_price", "wishTotalPrice");
			fieldMap.put("amount_7", "amount7");
			fieldMap.put("offer", "offer");
			fieldMap.put("wish_save_7", "wishSave7");
			fieldMap.put("wish_num", "wishNum");
			fieldMap.put("rate_num_7", "rateNum7");
			fieldMap.put("rate_num", "rateNum");
			fieldMap.put("cat_ids", "catIds");
		}
		return fieldMap;
	}

	public String getFields(){
		return this.fields;
	}
	public PushItemQuery  setFields(String fields){
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
