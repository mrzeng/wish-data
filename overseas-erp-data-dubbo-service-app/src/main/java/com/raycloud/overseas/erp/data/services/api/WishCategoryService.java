package com.raycloud.overseas.erp.data.services.api;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.WishCategory;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;
/**
 * @author zhanxiaofeng@raycloud.com
 * @since 2017-02-27
 */
public interface WishCategoryService{
    /**
     * 基本插入
     * @return
     */
	public Integer addWishCategory(WishCategory wishCategory);
	
    /**
     * 根据主键查询
     */
	public WishCategory getWishCategoryByKey(String id);
    /**
     * 根据主键批量查询
     */
    public List<WishCategory> getWishCategoryByKeys(List<String> idList);

    /**
     * 根据主键删除
     * @return
     */
	public Integer deleteByKey(String id);
    /**
     * 根据主键批量删除
     * @return
     */
    public Integer deleteByKeys(List<String> idList);

    /**
     * 根据主键更新
     * @return
     */
	public Integer updateWishCategoryByKey(WishCategory wishCategory);
    /**
     * 根据条件查询分页查询
     * @param wishCategoryQuery 查询条件
     * @return
     */
    public Result<WishCategory> getWishCategoryListWithPage(WishCategoryQuery wishCategoryQuery);
    /**
     * 根据条件查询
     * @param wishCategoryQuery 查询条件
     * @return
     */
    public List<WishCategory> getWishCategoryList(WishCategoryQuery wishCategoryQuery);

    /**
     * 获取子行业相对应的二级行业
     * @param id
     * @return
     */
    public WishCategory getLevel2Cat(String id);



    /**
     * 获取wish类目缓存
     * @return
     */
    public Map<String,WishCategory> getWishCategorMap();

    /**
     * 行业名称缓存
     * @return
     */
    public Map<String, String> getCatNameMap();

    /**
     * 初始化目录树
     */
    public void initWishCateTree();

    /**
     * 获取子行业id列表
     * @param id
     * @return
     */
    public List<String> getSubCatIdList(String id);

    /**
     * 获取行业名称列表
     * @param catIds
     * @return
     */
    public String getCategoryNames(String catIds);

    /**
     * 获取行业名称列表
     * @param catIds
     * @return
     */
    public List<String> getCategoryNames2(String catIds);

    /**
     * 从缓存获取wish类目信息
     * @param catIds
     * @return
     */
    public List<WishCategory> getWishCategoryByCatIds(String catIds);

    public void addCatNameForItemList(List<ItemDomain> itemDomainList);
}
