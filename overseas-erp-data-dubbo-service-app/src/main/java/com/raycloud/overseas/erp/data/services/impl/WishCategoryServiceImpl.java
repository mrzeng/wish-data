package com.raycloud.overseas.erp.data.services.impl;

import java.util.*;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.services.api.WishCategoryService;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.dao.WishCategoryDAO;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;

/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-27
 */
@Service("WishCategoryService")
public class WishCategoryServiceImpl implements WishCategoryService {

	private static Logger logger= Logger.getLogger(WishCategoryServiceImpl.class);

	@Resource
	WishCategoryDAO wishCategoryDAO;

	@Autowired
	private OcsSession ocsSession;

	public static Map<String,String> wishCatNameMap = null;

	@Resource
	private String env;

	public static String OVERSEA_WISH_CATEGORY = "OVERSEA_WISH_CATEGORY";

	public static String OVERSEA_WISH_CATEGORY_CAT_NAME = "OVERSEA_WISH_CATEGORY_CAT_NAME";


	/**
     * 插入数据库
     * @return
     */
	public Integer addWishCategory(WishCategory wishCategory){
		return wishCategoryDAO.addWishCategory(wishCategory);
	}
    /**
     * 根据主键查找
     */
	public WishCategory getWishCategoryByKey(     String id       ){
		return wishCategoryDAO.getWishCategoryByKey(id);
	}
    public List<WishCategory> getWishCategoryByKeys(    List<String> idList    ){
		return wishCategoryDAO.getWishCategoryByKeys(    idList    );
    }
    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(     String id       ){

		return wishCategoryDAO.deleteByKey(    id       );
	}

	/**
	 * 根据主键列表删除行业
	 * @param idList
	 * @return
     */
    public Integer deleteByKeys(    List<String> idList    ){
		return wishCategoryDAO.deleteByKeys(    idList    );
    }


    /**
     * 根据主键更新
     * @return
     */
	public Integer updateWishCategoryByKey(WishCategory wishCategory){
		    return wishCategoryDAO.updateWishCategoryByKey(wishCategory);
	}

	public Result<WishCategory> getWishCategoryListWithPage(WishCategoryQuery wishCategoryQuery){
		return wishCategoryDAO.getWishCategoryListWithPage(wishCategoryQuery);
	}
    
    public List<WishCategory> getWishCategoryList(WishCategoryQuery wishCategoryQuery){
		return wishCategoryDAO.getWishCategoryList(wishCategoryQuery);
	}

	@Override
	public WishCategory getLevel2Cat(String id) {
		WishCategory wishCategory = getWishCategoryByKey(id);
		if(wishCategory.getLevel()<2){
			return null;
		}else if(wishCategory.getLevel() == 2){
			return wishCategory;
		}else{
			return getLevel2Cat(wishCategory.getParentId());
		}
	}

	@Override
	public void initWishCateTree() {

		Map<String,WishCategory> categoryMap = (Map<String,WishCategory>) ocsSession.getAppObj(OVERSEA_WISH_CATEGORY+env);
		if(categoryMap == null){

			List<WishCategory> categoryList = wishCategoryDAO.getWishCategoryList(new WishCategoryQuery());
			categoryMap = new HashMap<String, WishCategory>();

			for(int i = 0;i<categoryList.size();i++){
				WishCategory wishCategory = categoryList.get(i);
				categoryMap.put(wishCategory.getId(),wishCategory);
			}

			for(int i = 0;i<categoryList.size();i++){
				WishCategory wishCategory = categoryList.get(i);
				String pId = wishCategory.getParentId();
				if(categoryMap.containsKey(pId)){
					WishCategory pCategory = categoryMap.get(pId);
					if(pCategory.getChildCats() == null){
						pCategory.setChildCats(new HashMap<String, WishCategory>());
					}
					pCategory.getChildCats().put(wishCategory.getId(),wishCategory);
				}
			}
			ocsSession.setAppObj(OVERSEA_WISH_CATEGORY+env,categoryMap);
		}
	}

	/**
	 * 获取子行业信息
	 * @param id
	 * @return
	 */
	@Override
	public List<String> getSubCatIdList(String id) {

		Map<String,WishCategory> categoryMap = getWishCategorMap();

		WishCategory wishCategory = categoryMap.get(id);
		List<String> catIdList = null;
		if(wishCategory != null){
			catIdList = getAllSubCatId(wishCategory);
		}
		return catIdList;
	}

	/**
	 * 深度递归获取所有子行业
	 * @param wishCategory
	 * @return
	 */
	private List<String> getAllSubCatId(WishCategory wishCategory){
		List<String> catIdList = new ArrayList<String>();
		catIdList.add(wishCategory.getCatId());
		Map<String,WishCategory> subWishCategory = wishCategory.getChildCats();
		if(subWishCategory != null){
			for(String id:subWishCategory.keySet()){
				catIdList.addAll(getAllSubCatId(subWishCategory.get(id)));
			}
		}
		return catIdList;
	}

	/**
	 * 获取行业名称列表
	 *
	 * @param catIds
	 * @return
	 */
	@Override
	public String getCategoryNames(String catIds) {

		if(catIds == null || catIds.equals("")){
			return "";
		}
		Map<String,String> catNameMap = getCatNameMap();

		Set<String> set = new HashSet<String>();
		List<String> catIdList = ListUtil.strToList(catIds,";");

		for(String catId:catIdList){
			if(catNameMap.containsKey(catId)){
				String catName = catNameMap.get(catId);
				set.add(catName.substring(0,catName.indexOf("@")));
			}
		}

		return set.toString().replace("[","").replace("]","");
	}

	/**
	 * 获取行业名称列表
	 *
	 * @param catIds
	 * @return
	 */
	@Override
	public List<String> getCategoryNames2(String catIds) {
		if(catIds == null || catIds.equals("")){
			return null;
		}
		Map<String,String> catNameMap = getCatNameMap();

		List<String> list = new ArrayList<String>();
		List<String> catIdList = ListUtil.strToList(catIds,";");

		for(String catId:catIdList){
			if(catNameMap.containsKey(catId)){
				list.add(catNameMap.get(catId));
			}
		}

		return list;
	}

	@Override
	public Map<String, String> getCatNameMap() {

		if(wishCatNameMap == null){
			Map<String,WishCategory> categoryMap = getWishCategorMap();
			if(categoryMap == null || categoryMap.size() == 0){
				logger.error("数据库异常,无行业类目信息");
			}
			wishCatNameMap = new HashMap<String, String>();
			for(String id : categoryMap.keySet()){
				WishCategory category = categoryMap.get(id);
				wishCatNameMap.put(category.getCatId(),category.getEnglishName()+"("+category.getChineseName()+")"+"@"+category.getId()+"@"+category.getLevel());
			}
		}

		return wishCatNameMap;
	}

	@Override
	public Map<String,WishCategory> getWishCategorMap() {
		Map<String, WishCategory> categoryMap = null;
		try{
			categoryMap = (Map<String,WishCategory>) ocsSession.getAppObj(OVERSEA_WISH_CATEGORY+env);
			if(categoryMap == null){
				List<WishCategory> categoryList = wishCategoryDAO.getWishCategoryList(new WishCategoryQuery());
				categoryMap = new HashMap<String,WishCategory>();

				for(int i = 0;i<categoryList.size();i++){
					WishCategory wishCategory = categoryList.get(i);
					categoryMap.put(wishCategory.getId(),wishCategory);
				}

				for(int i = 0;i<categoryList.size();i++){
					WishCategory wishCategory = categoryList.get(i);
					String pId = wishCategory.getParentId();
					if(categoryMap.containsKey(pId)){
						WishCategory pCategory = categoryMap.get(pId);
						if(pCategory.getChildCats() == null){
							pCategory.setChildCats(new HashMap<String, WishCategory>());
						}
						pCategory.getChildCats().put(wishCategory.getId(),wishCategory);
					}
				}
				ocsSession.setAppObj(OVERSEA_WISH_CATEGORY+env,categoryMap);
			}
		}catch (Exception e){
			logger.error("GET_WISH_CATEGORY_EXCEPTION",e);
		}

		return categoryMap;
	}

	@Override
	public List<WishCategory> getWishCategoryByCatIds(String catIds) {
		List<WishCategory> list = null;
		Map<String,WishCategory> map = getWishCategorMap();
		List<String> catIdList = ListUtil.strToList(catIds,";");
		if(!ListUtil.isBlank(catIdList)){
			list = new ArrayList<WishCategory>();
			for(String catId : catIdList){
				for(String id : map.keySet()){
					WishCategory wishCategory = map.get(id);
					if(wishCategory.getCatId().equals(catId)){
						list.add(wishCategory);
						break;
					}
				}
			}
		}
		return list;
	}

	@Override
	public void addCatNameForItemList(List<ItemDomain> itemDomainList) {
		if(!ListUtil.isBlank(itemDomainList)){
			for(ItemDomain itemDomain : itemDomainList){
				itemDomain.setCatNames(getCategoryNames(itemDomain.getCatIds()));
				itemDomain.setCatIds(null);
			}
		}
	}
}
