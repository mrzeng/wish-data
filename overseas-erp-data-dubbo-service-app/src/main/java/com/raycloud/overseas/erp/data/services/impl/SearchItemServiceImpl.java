package com.raycloud.overseas.erp.data.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.raycloud.overseas.data.commom.domain.wish.domain.Item;
import com.raycloud.overseas.data.api.dubbo.service.IItemService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.dao.QuickQueryBizDataDAO;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.util.DateUtil;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.request.SearchListRequest;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.core.WishMerchantSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.services.api.SearchItemService;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;
import com.raycloud.overseas.erp.data.services.util.MappingUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raycloud.overseas.erp.data.common.pojo.SearchItem;
import com.raycloud.overseas.erp.data.common.dao.SearchItemDAO;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.SearchItemQuery;
/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-03-01
 */
@Service("SearchItemService")
public class SearchItemServiceImpl implements SearchItemService {

	private static Logger logger= Logger.getLogger(SearchItemServiceImpl.class);

	@Resource
	SearchItemDAO searchItemDAO;

	@Autowired
	private OcsSession ocsSession;

	@Resource
	private WishItemSolrService wishItemSolrService;

	@Resource
	private WishMerchantSolrService wishMerchantSolrService;

	@Autowired
	private WishCategoryService wishCategoryService;

	@Autowired
	private IItemService mjItemService;

	/**
	 * 行业内明星产品缓存
	 */
	private String OVERSEA_USER_SEARCH_ = "OVERSEA_USER_SEARCH_";

	public static Integer MAX_SEARCH_COUNT = 2000;

    /**
     * 插入数据库
     * @return
     */
	public void batchAddSearchItem(List<SearchItem> searchItemList){

		searchItemDAO.batchAddSearchItem(searchItemList);
	}

	/**
	 * 插入数据库
	 * @return
	 */
	public Integer addSearchItem(SearchItem searchItem){
		return searchItemDAO.addSearchItem(searchItem);
	}

	/**
     * 根据主键查找
     */
	public SearchItem getSearchItemByKey(     Integer id       ){
		return searchItemDAO.getSearchItemByKey(    id       );
	}
    public List<SearchItem> getSearchItemByKeys(    List<Integer> idList    ){
        return searchItemDAO.getSearchItemByKeys(    idList    );
    }
    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(     Integer id       ){
        return searchItemDAO.deleteByKey(    id       );
	}
    public Integer deleteByKeys(    List<Integer> idList    ){

		return searchItemDAO.deleteByKeys(    idList    );
    }

//    /**
//     * 根据加入时间批量删除
//     */
//    public Integer deleteByAddTime(SearchItemQuery searchItemQuery){
//        return searchItemDAO.deleteByAddTime(searchItemQuery);
//    }
    /**
     * 根据主键更新
     * @return
     */
	public Integer updateSearchItemByKey(SearchItem searchItem){
	   return searchItemDAO.updateSearchItemByKey(searchItem);
	}

	public Result<SearchItem> getSearchItemListWithPage(SearchItemQuery searchItemQuery){
		return searchItemDAO.getSearchItemListWithPage(searchItemQuery);
	}
    
    public List<SearchItem> getSearchItemList(SearchItemQuery searchItemQuery){

        return searchItemDAO.getSearchItemList(searchItemQuery);
	}

	@Override
	public Result search(SearchListRequest request) throws BizException {

		if(!checkUserSearchIegal(request.getUserId())){
			return new Result();
		}
		if(!StringUtils.isEmpty(request.getCatId())){
			WishCategory wishCategory = wishCategoryService.getWishCategoryByKey(request.getCatId());
			if(wishCategory!=null && wishCategory.getLevel()!= 0){
				request.setCatId(wishCategory.getCatId());
			}else{
				request.setCatId(null);
			}
		}
		if(request.getType() == 1){//店铺
			WishMerchantSolrQuery wishMerchantSolrQuery = MappingUtil.mappingWishMerchantSQ(request);
			return wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery);
		}else{
			dealMerchantEventRequest(request);
			if(request.getSearchInShop() && ListUtil.isBlank(request.getItemIdList())){
				Result result = new Result();
				result.setPageNo(1);
				result.setPageSize(100);
				result.setTotal(0L);
				return result;
			}
			WishItemSolrQuery wishItemSolrQuery = MappingUtil.mappingWishItemSQ(request);
			Result result = wishItemSolrService.blockQueryDocs(wishItemSolrQuery);
			List<ItemDomain> itemDomainList = result.getItems();
			if(!ListUtil.isBlank(itemDomainList)){
				for(ItemDomain itemDomain : itemDomainList){
					itemDomain.setCatNames(wishCategoryService.getCategoryNames(itemDomain.getCatIds()));
					itemDomain.setGenTime(itemDomain.getGenTime().substring(0,10));
				}
			}
			return result;
		}
	}



	/**
	 * 校验用户是否符合搜索条件
	 *
	 * @return
	 */
	@Override
	public boolean checkUserSearchIegal(Integer userId) {
		String date = DateUtil.getDate(new Date(),"yyyy-MM-dd");

		Integer count = (Integer) ocsSession.getAppObj(OVERSEA_USER_SEARCH_+date+userId);

		if(count == null){
			count = 0;
		}

		if(count<MAX_SEARCH_COUNT){
			ocsSession.setAppObj(OVERSEA_USER_SEARCH_+date+userId,++count);
			return true;
		}else{
			logger.biz("搜索失败,该用户本日搜索次数已达上限,用户id:{}",userId);
			return false;
		}
	}

	@Override
	public void dealMerchantEventRequest(SearchListRequest request) {
		if(request.getEventType()!=null && request.getEventMerchantId()!=null && request.getTotal()!=null){
			request.setMerchantId(request.getEventMerchantId());
			int _pageSize = 100;
			if(request.getEventType().equals("changeWishPrice")||request.getEventType().equals("changeSellerPrice")||request.getEventType().equals("changeOriginalPrice")
					||request.getEventType().equals("allDistributeCat")){
				_pageSize = 4000;
			}
			List<String> itemIdList = new ArrayList<String>(_pageSize);
			int _pageTotal = request.getTotal()%_pageSize == 0?request.getTotal()/_pageSize:request.getTotal()/_pageSize+1;
			for(int i = 1;i<=_pageTotal;i++){
				List<String> _itemIdList = getItemIdListByEventType(request.getEventMerchantId(),request.getEventType(),request.getEventTime(),i,_pageSize);
				if(!ListUtil.isBlank(_itemIdList)){
					itemIdList.addAll(_itemIdList);
				}
			}
			request.setItemIdList(itemIdList);
			request.setSearchInShop(true);
		}

	}

	@Override
	public List<String> getItemIdListByEventType(String merchantId,String eventType,String eventTime,Integer pageNo,Integer pageSize){
		long startL = System.currentTimeMillis();
		List<Item> itemList = null;

		if(eventType.equals("allDistributeCat")){//所有划分行业的宝贝id
			itemList = mjItemService.queryDividedCatItemListbyMerchantId(merchantId,pageNo,pageSize);

		}else if(eventType.equals("allRefer")){//店铺内所有认证的宝贝id
			itemList = mjItemService.queryWishRecommendItemListbyMerchantId(merchantId,pageNo,pageSize);

		}else{
			itemList = mjItemService.queryItemListFromSomeTableByMerchantId(merchantId,eventType,eventTime,pageNo,pageSize);
		}


		if(!ListUtil.isBlank(itemList)){
			List<String> itemIdList = new ArrayList<String>();
			for(Item item : itemList){
				itemIdList.add(item.getItemId());
			}
			return itemIdList;
		}
		logger.biz("里程碑类型:{},店铺id:{},宝贝个数:{},耗时{}ms",eventType,merchantId,itemList.size(),(System.currentTimeMillis()-startL));
		return null;
	}
}
