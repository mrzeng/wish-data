
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.SearchItemQuery;
import com.raycloud.overseas.erp.data.common.pojo.SearchItem;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-01
 */

@Repository
public class SearchItemDAO  extends BaseDAO{


	public Integer addSearchItem(SearchItem searchItem){
		return (Integer) getSqlMapClientTemplate().insert("SearchItem.insertSearchItem", searchItem);
	}

    public void batchAddSearchItem(List<SearchItem> searchItemList){
        batchInsert("SearchItem.insertSearchItem", searchItemList);
    }

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public SearchItem getSearchItemByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        SearchItem result = (SearchItem)  getSqlMapClientTemplate().queryForObject(
                "SearchItem.getSearchItemByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<SearchItem> getSearchItemByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<SearchItem>) getSqlMapClientTemplate().queryForList("SearchItem.getSearchItemsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     Integer id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("SearchItem.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("SearchItem.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateSearchItemByKey(SearchItem searchItem){
		return  getSqlMapClientTemplate().update("SearchItem.updateSearchItemByKey", searchItem);
	}
    @SuppressWarnings("unchecked")
    public Result<SearchItem> getSearchItemListWithPage(SearchItemQuery searchItemQuery){
	    Result<SearchItem> rs = new Result<SearchItem>(searchItemQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("SearchItem.getSearchItemListCount",searchItemQuery)));
            if(searchItemQuery.getFields()!=null && searchItemQuery.getFields()!=""){
                rs.setItems((List<SearchItem>) getSqlMapClientTemplate().queryForList("SearchItem.getSearchItemListWithPageFields", searchItemQuery));
            }else{
                rs.setItems((List<SearchItem>) getSqlMapClientTemplate().queryForList("SearchItem.getSearchItemListWithPage", searchItemQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<SearchItem> getSearchItemList(SearchItemQuery searchItemQuery){
    if(searchItemQuery.getFields()!=null&&searchItemQuery.getFields()!=""){
        return (List<SearchItem>) getSqlMapClientTemplate().queryForList("SearchItem.getSearchItemListFields", searchItemQuery);
    }
		return (List<SearchItem>) getSqlMapClientTemplate().queryForList("SearchItem.getSearchItemList", searchItemQuery);
	}


}