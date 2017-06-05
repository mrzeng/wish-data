
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.WishCategoryQuery;
import com.raycloud.overseas.erp.data.domain.WishCategory;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */

@Repository
public class WishCategoryDAO  extends BaseDAO{


	public Integer addWishCategory(WishCategory wishCategory){
		return (Integer) getSqlMapClientTemplate().insert("WishCategory.insertWishCategory", wishCategory);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public WishCategory getWishCategoryByKey(     String id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        WishCategory result = (WishCategory)  getSqlMapClientTemplate().queryForObject(
                "WishCategory.getWishCategoryByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<WishCategory> getWishCategoryByKeys(    List<String> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<WishCategory>) getSqlMapClientTemplate().queryForList("WishCategory.getWishCategorysByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     String id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("WishCategory.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<String> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("WishCategory.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateWishCategoryByKey(WishCategory wishCategory){
		return  getSqlMapClientTemplate().update("WishCategory.updateWishCategoryByKey", wishCategory);
	}
    @SuppressWarnings("unchecked")
    public Result<WishCategory> getWishCategoryListWithPage(WishCategoryQuery wishCategoryQuery){
	    Result<WishCategory> rs = new Result<WishCategory>(wishCategoryQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("WishCategory.getWishCategoryListCount",wishCategoryQuery)));
            if(wishCategoryQuery.getFields()!=null && wishCategoryQuery.getFields()!=""){
                rs.setItems((List<WishCategory>) getSqlMapClientTemplate().queryForList("WishCategory.getWishCategoryListWithPageFields", wishCategoryQuery));
            }else{
                rs.setItems((List<WishCategory>) getSqlMapClientTemplate().queryForList("WishCategory.getWishCategoryListWithPage", wishCategoryQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<WishCategory> getWishCategoryList(WishCategoryQuery wishCategoryQuery){
    if(wishCategoryQuery.getFields()!=null&&wishCategoryQuery.getFields()!=""){
        return (List<WishCategory>) getSqlMapClientTemplate().queryForList("WishCategory.getWishCategoryListFields", wishCategoryQuery);
    }
		return (List<WishCategory>) getSqlMapClientTemplate().queryForList("WishCategory.getWishCategoryList", wishCategoryQuery);
	}


}