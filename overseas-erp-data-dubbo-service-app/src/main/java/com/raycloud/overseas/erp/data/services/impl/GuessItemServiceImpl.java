package com.raycloud.overseas.erp.data.services.impl;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.dao.GuessItemDAO;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.query.GuessItemQuery;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.domain.GuessItem;
import com.raycloud.overseas.erp.data.search.core.WishItemSolrService;
import com.raycloud.overseas.erp.data.search.query.WishItemSolrQuery;
import com.raycloud.overseas.erp.data.services.api.GuessItemService;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;
import com.raycloud.overseas.erp.data.services.util.MappingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-27
 */
@Service("GuessItemService")
public class GuessItemServiceImpl  implements GuessItemService {

	private static Logger logger= Logger.getLogger(LocalIndustryServiceImpl.class);

	/**
	 * 保存用户猜你喜欢产品索引
	 */
	private String USER_GUESS_ITEM = "USER_GUESS_ITEM_";

	@Resource
	GuessItemDAO guessItemDAO;

	@Autowired
	private OcsSession ocsSession;

	@Resource
	private WishCategoryService wishCategoryService;

	@Resource
	private WishItemSolrService wishItemSolrService;

    /**
     * 插入数据库
     * @return
     */
	public Integer addGuessItem(GuessItem guessItem){
			return guessItemDAO.addGuessItem(guessItem);
	}
    /**
     * 根据主键查找
     */
	public GuessItem getGuessItemByKey(     String itemId       ){
			return guessItemDAO.getGuessItemByKey(    itemId       );
	}
    public List<GuessItem> getGuessItemByKeys(    List<String> itemIdList    ){
            return guessItemDAO.getGuessItemByKeys(    itemIdList    );
    }
    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(     String itemId       ){
			return guessItemDAO.deleteByKey(    itemId       );
	}
    public Integer deleteByKeys(    List<String> itemIdList    ){
            return guessItemDAO.deleteByKeys(    itemIdList    );
    }

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateGuessItemByKey(GuessItem guessItem){
		    return guessItemDAO.updateGuessItemByKey(guessItem);
	}

	public Result<GuessItem> getGuessItemListWithPage(GuessItemQuery guessItemQuery){
		Result<GuessItem> rs = guessItemDAO.getGuessItemListWithPage(guessItemQuery);
		return rs;
	}
    
    public List<GuessItem> getGuessItemList(GuessItemQuery guessItemQuery){
		return guessItemDAO.getGuessItemList(guessItemQuery);
	}



	@Override
	public List<GuessItem> getGuessItemList(Long userId) {

		int total = guessItemDAO.getGuessItemCount(new GuessItemQuery());
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int pageSize = 30;//30个一组
		String str = (String) ocsSession.getAppObj(USER_GUESS_ITEM+userId);
		Integer firstResult = null;
		Long lastTime = null;
		//缓存中保存该用户的分页信息,然后循环遍历
		if(str!=null){
			firstResult = Integer.parseInt(str.split(":")[0]);
			lastTime = Long.parseLong(str.split(":")[1]);
		}
		if(firstResult == null || firstResult>=total){
			firstResult = new Random().nextInt(total-30);
			ocsSession.setAppObj(USER_GUESS_ITEM+userId,firstResult+":"+System.currentTimeMillis());
		}else{
			if((hour>7&&hour<23)&&(System.currentTimeMillis()-lastTime)>30*60000){//7-21点更替,并且两次请求时间超过半小时
				if(firstResult+30>total){//转了一圈,从新回到起点
					firstResult = 30;
				}else{
					firstResult += 30;
				}
				ocsSession.setAppObj(USER_GUESS_ITEM+userId,firstResult+":"+System.currentTimeMillis());
			}
		}
		GuessItemQuery guessItemQuery = new GuessItemQuery();
		guessItemQuery.setStartRow(firstResult);
		guessItemQuery.setEndRow(firstResult+pageSize);
		Result result = getGuessItemListWithPage(guessItemQuery);
		return  result.getItems();
	}

	//@Scheduled(cron = "0 0 1 1/7 * ?")
	@Override
	public void timerGuessItemTask(){

		logger.biz("开始更新猜你喜欢的产品");


			long start = System.currentTimeMillis();

			Map<String,GuessItem> map = new HashMap<String, GuessItem>();
			List<WishCategory> industryCatList = wishCategoryService.getWishCategoryList(new WishCategoryQuery());
			if(industryCatList!=null&&industryCatList.size()>0){
				int i = 0;
				for(WishCategory industryCat:industryCatList){
					logger.biz("扫描行业:{},行业id:{}",industryCat.getChineseName(),industryCat.getCatId());
					i++;
					if(i%100==0){
						logger.biz("扫描第"+i+"个行业猜你喜欢产品数据...");
					}

					WishItemSolrQuery wishItemSolrQuery = new WishItemSolrQuery();
					wishItemSolrQuery.setSortField(TraceOrOrder.amount_rate.toString());
					wishItemSolrQuery.setOrder("DESC");
					wishItemSolrQuery.setCatId(industryCat.getCatId());
					wishItemSolrQuery.setStart(0);
					wishItemSolrQuery.setRows(10);
					List<ItemDomain> amountSurgeList = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery).getItems();

					WishItemSolrQuery wishItemSolrQuery2 = new WishItemSolrQuery();
					wishItemSolrQuery2.setSortField(TraceOrOrder.amount_7.toString());
					wishItemSolrQuery2.setOrder("DESC");
					wishItemSolrQuery2.setCatId(industryCat.getCatId());
					wishItemSolrQuery2.setStart(0);
					wishItemSolrQuery2.setRows(10);
					List<ItemDomain> amount7List = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery2).getItems();

					WishItemSolrQuery wishItemSolrQuery3 = new WishItemSolrQuery();
					wishItemSolrQuery3.setSortField(TraceOrOrder.wish_save_7.toString());
					wishItemSolrQuery3.setOrder("DESC");
					wishItemSolrQuery3.setCatId(industryCat.getCatId());
					wishItemSolrQuery3.setStart(0);
					wishItemSolrQuery3.setRows(10);
					List<ItemDomain> wishSave7List = wishItemSolrService.queryDocsWishQuery(wishItemSolrQuery3).getItems();

					for(ItemDomain item: amountSurgeList){
						if(map.containsKey(item.getItemId())){
							GuessItem guessItem = map.get(item.getItemId());
							if(guessItem.getRecommand().indexOf("销量暴增") == -1){
								guessItem.setRecommand(guessItem.getRecommand()+",销量暴增");
								guessItem.setStar(5);
							}

						}else{
							GuessItem guessItem = MappingUtil.mappingGuessItem(item);
							guessItem.setStar(4);
							guessItem.setRecommand("销量暴增");
							guessItem.setCatNames(wishCategoryService.getCategoryNames(item.getCatIds()));
							map.put(item.getItemId(),guessItem);
						}
					}

					for(ItemDomain item:amount7List){
						if(map.containsKey(item.getItemId())){
							GuessItem guessItem = map.get(item.getItemId());
							if(guessItem.getRecommand().indexOf("TOP销量") == -1){
								guessItem.setRecommand(guessItem.getRecommand()+",TOP销量");
								guessItem.setStar(5);
							}

						}else{
							GuessItem guessItem = MappingUtil.mappingGuessItem(item);
							guessItem.setStar(4);
							guessItem.setRecommand("TOP销量");
							guessItem.setCatNames(wishCategoryService.getCategoryNames(item.getCatIds()));
							map.put(item.getItemId(),guessItem);
						}
					}

					for(ItemDomain item:wishSave7List){
						if(map.containsKey(item.getItemId())){
							GuessItem guessItem = map.get(item.getItemId());
							if(guessItem.getRecommand().indexOf("收藏暴增") == -1){
								guessItem.setRecommand(guessItem.getRecommand()+",收藏暴增");
								guessItem.setStar(5);
							}
						}else{
							GuessItem guessItem = MappingUtil.mappingGuessItem(item);
							guessItem.setStar(4);
							guessItem.setRecommand("收藏暴增");
							guessItem.setCatNames(wishCategoryService.getCategoryNames(item.getCatIds()));
							map.put(item.getItemId(),guessItem);
						}
					}
				}
			}
			List<GuessItem> guessItemList = new ArrayList<GuessItem>();
			for(String itemId:map.keySet()){
				guessItemList.add(map.get(itemId));
			}
			guessItemDAO.deleteGuessItemList(new GuessItemQuery());
			guessItemDAO.batchAddGuessItem(guessItemList);
			logger.biz("更新猜你喜欢的产品结束,共更新{}个猜你喜欢产品,耗时{}ms",guessItemList.size(),(System.currentTimeMillis()-start));



	}


}
