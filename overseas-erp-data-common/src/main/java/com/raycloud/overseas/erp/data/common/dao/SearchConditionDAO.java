
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.SearchConditionQuery;
import com.raycloud.overseas.erp.data.common.pojo.SearchCondition;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-02
 */

@Repository
public class SearchConditionDAO  extends BaseDAO{


	public Integer addSearchCondition(SearchCondition searchCondition){
		return (Integer) getSqlMapClientTemplate().insert("SearchCondition.insertSearchCondition", searchCondition);
	}

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public SearchCondition getSearchConditionByKey(Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        SearchCondition result = (SearchCondition)  getSqlMapClientTemplate().queryForObject(
                "SearchCondition.getSearchConditionByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<SearchCondition> getSearchConditionByKeys(List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<SearchCondition>) getSqlMapClientTemplate().queryForList("SearchCondition.getSearchConditionsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     Integer id       ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("SearchCondition.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    List<Integer> idList    ){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("SearchCondition.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateSearchConditionByKey(SearchCondition searchCondition){
		return  getSqlMapClientTemplate().update("SearchCondition.updateSearchConditionByKey", searchCondition);
	}
    @SuppressWarnings("unchecked")
    public Result<SearchCondition> getSearchConditionListWithPage(SearchConditionQuery searchConditionQuery){
	    Result<SearchCondition> rs = new Result<SearchCondition>(searchConditionQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("SearchCondition.getSearchConditionListCount",searchConditionQuery)));
            if(searchConditionQuery.getFields()!=null && searchConditionQuery.getFields()!=""){
                rs.setItems((List<SearchCondition>) getSqlMapClientTemplate().queryForList("SearchCondition.getSearchConditionListWithPageFields", searchConditionQuery));
            }else{
                rs.setItems((List<SearchCondition>) getSqlMapClientTemplate().queryForList("SearchCondition.getSearchConditionListWithPage", searchConditionQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<SearchCondition> getSearchConditionList(SearchConditionQuery searchConditionQuery){
    if(searchConditionQuery.getFields()!=null&&searchConditionQuery.getFields()!=""){
        return (List<SearchCondition>) getSqlMapClientTemplate().queryForList("SearchCondition.getSearchConditionListFields", searchConditionQuery);
    }
		return (List<SearchCondition>) getSqlMapClientTemplate().queryForList("SearchCondition.getSearchConditionList", searchConditionQuery);
	}


}