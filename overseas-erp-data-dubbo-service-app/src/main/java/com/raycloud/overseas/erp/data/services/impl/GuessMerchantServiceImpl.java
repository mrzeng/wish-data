package com.raycloud.overseas.erp.data.services.impl;

import java.util.*;


import com.raycloud.overseas.data.commom.domain.wish.domain.Shop;

import com.raycloud.overseas.data.api.dubbo.service.IIndustryService;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.pojo.MerchantDomain;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.domain.GuessMerchant;
import com.raycloud.overseas.erp.data.search.core.WishMerchantSolrService;
import com.raycloud.overseas.erp.data.search.query.WishMerchantSolrQuery;
import com.raycloud.overseas.erp.data.services.api.GuessMerchantService;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;
import com.raycloud.overseas.erp.data.services.util.MappingUtil;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.raycloud.overseas.erp.data.common.dao.GuessMerchantDAO;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.GuessMerchantQuery;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-27
 */
@Service("GuessMerchantService")
public class GuessMerchantServiceImpl  implements GuessMerchantService {

	private static Logger logger= Logger.getLogger(LocalIndustryServiceImpl.class);

	/**
	 * 保存用户猜你喜欢店铺索引
	 */
	private String USER_GUESS_MERCHANT = "USER_GUESS_MERCHANT_";

	@Autowired
	private OcsSession ocsSession;

	@Resource
	GuessMerchantDAO guessMerchantDAO;

	@Resource
	private WishCategoryService wishCategoryService;

	@Resource
	private WishMerchantSolrService wishMerchantSolrService;

	@Autowired
	private IIndustryService mjIndustryService;

    /**
     * 插入数据库
     * @return
     */
	public Integer addGuessMerchant(GuessMerchant guessMerchant){
			return guessMerchantDAO.addGuessMerchant(guessMerchant);
	}
    /**
     * 根据主键查找
     */
	public GuessMerchant getGuessMerchantByKey(     String merchantId       ){
		return guessMerchantDAO.getGuessMerchantByKey(    merchantId       );
	}
    public List<GuessMerchant> getGuessMerchantByKeys(    List<String> merchantIdList    ){

		return guessMerchantDAO.getGuessMerchantByKeys(    merchantIdList    );
    }
    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(     String merchantId       ){
		return guessMerchantDAO.deleteByKey(    merchantId       );
	}
    public Integer deleteByKeys(    List<String> merchantIdList    ){
        return guessMerchantDAO.deleteByKeys(    merchantIdList    );
    }

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateGuessMerchantByKey(GuessMerchant guessMerchant){
	    return guessMerchantDAO.updateGuessMerchantByKey(guessMerchant);
	}

	public Result<GuessMerchant> getGuessMerchantListWithPage(GuessMerchantQuery guessMerchantQuery){
		Result<GuessMerchant> rs = guessMerchantDAO.getGuessMerchantListWithPage(guessMerchantQuery);
		if(!rs.isSuccess()){
			logger.error("get GuessMerchant error."+rs.getErrorMsg());
		}
		return rs;
	}	
    
    public List<GuessMerchant> getGuessMerchantList(GuessMerchantQuery guessMerchantQuery){
		return guessMerchantDAO.getGuessMerchantList(guessMerchantQuery);
	}

	@Override
	public List<GuessMerchant> getGuessMerchantList(Long userId){

		Integer total = guessMerchantDAO.getGuessMerchantCount(new GuessMerchantQuery());
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int pageSize = 10;//10个一组

		String str = (String) ocsSession.getAppObj(USER_GUESS_MERCHANT+userId);
		Integer firstResult = null;
		Long lastTime = null;
		if(str!=null){
			firstResult = Integer.parseInt(str.split(":")[0]);
			lastTime = Long.parseLong(str.split(":")[1]);
		}
		if(firstResult == null || firstResult>=total){
			//第一次生成,确保每次都能拿到30个数据
			firstResult = new Random().nextInt(total-10);
			ocsSession.setAppObj(USER_GUESS_MERCHANT+userId,firstResult+":"+System.currentTimeMillis());
		}else{
			if((hour>7&&hour<23)&&(System.currentTimeMillis()-lastTime)>30*60000){//7-21点更替,并且两次请求时间超过半小时
				if(firstResult+30>total){//转了一圈,从新回到起点
					firstResult = 30;
				}else{
					firstResult += 30;
				}
				ocsSession.setAppObj(USER_GUESS_MERCHANT+userId,firstResult+":"+System.currentTimeMillis());
			}
		}
		GuessMerchantQuery guessMerchantQuery = new GuessMerchantQuery();
		guessMerchantQuery.setStartRow(firstResult);
		guessMerchantQuery.setEndRow(firstResult+pageSize);
		return guessMerchantDAO.getGuessMerchantList(guessMerchantQuery);
	}




	//@Scheduled(cron = "0 0 1 2,7,14,21,29 * ?")
	@Override
	public void timerGuessMerchantTask(){

			logger.biz("开始更新猜你喜欢的店铺");


			long start = System.currentTimeMillis();

			Map<String,GuessMerchant> map = new HashMap<String, GuessMerchant>();

			WishMerchantSolrQuery wishMerchantSolrQuery = new WishMerchantSolrQuery();
			wishMerchantSolrQuery.setSortField("new_item_count");
			wishMerchantSolrQuery.setOrder("DESC");
			wishMerchantSolrQuery.setStart(0);
			wishMerchantSolrQuery.setRows(50);
			List<MerchantDomain> newItemMerchantList = wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery).getItems();
			logger.biz("solr_query_上新店铺查询个数:{}", ListUtil.isBlank(newItemMerchantList)?0:newItemMerchantList.size());

			if(!ListUtil.isBlank(newItemMerchantList)){
				for(MerchantDomain merchant:newItemMerchantList){
					if(map.containsKey(merchant.getMerchantId())){
						GuessMerchant guessMerchant = map.get(merchant.getMerchantId());
						if(guessMerchant.getRecommand().indexOf("快速上新") == -1){
							guessMerchant.setStar(5);
							guessMerchant.setRecommand(guessMerchant.getRecommand()+",快速上新");
						}
					}else{
						GuessMerchant guessMerchant = MappingUtil.mappingGuessMerchant(merchant);
						guessMerchant.setStar(4);
						guessMerchant.setRecommand("快速上新");
						map.put(merchant.getMerchantId(),guessMerchant);
					}
				}
			}
			logger.biz("快速上新店铺筛选结束....");

			WishMerchantSolrQuery wishMerchantSolrQuery2 = new WishMerchantSolrQuery();
			wishMerchantSolrQuery2.setSortField("item_cat_count");
			wishMerchantSolrQuery2.setOrder("DESC");
			wishMerchantSolrQuery2.setStart(0);
			wishMerchantSolrQuery2.setRows(50);
			List<MerchantDomain> itemCatList = wishMerchantSolrService.queryDocsWishQuery(wishMerchantSolrQuery2).getItems();
			logger.biz("solr_query_划分行业店铺查询个数:{}",ListUtil.isBlank(itemCatList)?0:itemCatList.size());

			if(!ListUtil.isBlank(itemCatList)){
				for(MerchantDomain merchant:itemCatList){
					if(map.containsKey(merchant.getMerchantId())){
						GuessMerchant guessMerchant = map.get(merchant.getMerchantId());
						if(guessMerchant.getRecommand().indexOf("多产品归属行业") == -1){
							guessMerchant.setStar(5);
							guessMerchant.setRecommand(guessMerchant.getRecommand()+",多产品归属行业");
						}
					}else{
						GuessMerchant guessMerchant = MappingUtil.mappingGuessMerchant(merchant);
						guessMerchant.setStar(4);
						guessMerchant.setRecommand("多产品归属行业");
						map.put(merchant.getMerchantId(),guessMerchant);
					}
				}
			}

			logger.biz("多行业归属店铺筛选结束....");
			List<WishCategory> allCatList = wishCategoryService.getWishCategoryList(new WishCategoryQuery());
			for(int i = 0;i<allCatList.size();i++){
				WishCategory industryCat = allCatList.get(i);
				if(i%100==0){
					logger.debug("行业TOP销量店铺数据已经更新"+i+"条");
				}
				//top销量店铺
				List<Shop> hotSaleMerchantList = mjIndustryService.queryIndustryTopShopsByCatId(industryCat.getCatId(),1,10);

				if(!ListUtil.isBlank(hotSaleMerchantList)){
					for(Shop shop:hotSaleMerchantList){
						if(map.containsKey(shop.getMerchantId())){
							GuessMerchant guessMerchant = map.get(shop.getMerchantId());
							if(guessMerchant.getRecommand().indexOf("TOP销量") == -1){
								guessMerchant.setStar(5);
								guessMerchant.setRecommand(guessMerchant.getRecommand()+",TOP销量");
							}
						}else{
							GuessMerchant guessMerchant = MappingUtil.mappingGuessMerchant(shop);
							guessMerchant.setStar(4);
							guessMerchant.setRecommand("TOP销量");
							map.put(shop.getMerchantId(),guessMerchant);
						}
					}
				}
			}
			logger.biz("top销量店铺筛选结束....");
			List<GuessMerchant> guessMerchantList = new ArrayList<GuessMerchant>();
			for(String merchantId:map.keySet()){
				guessMerchantList.add(map.get(merchantId));
			}
			guessMerchantDAO.deleteGuessMerchantList(new GuessMerchantQuery());
			guessMerchantDAO.batchAddGuessMerchant(guessMerchantList);
			logger.biz("更新猜你喜欢的店铺结束,共{}个店铺,耗时{}ms",guessMerchantList.size(),(System.currentTimeMillis()-start));


	}



}
