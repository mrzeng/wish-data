
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.util.*;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.FocusMarkMapQuery;
import com.raycloud.overseas.erp.data.common.pojo.FocusMarkMap;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-03-01
 */
@Repository
public class FocusMarkMapDAO  extends BaseDAO{


	public Integer addFocusMarkMap(FocusMarkMap focusMarkMap){
		return (Integer) getSqlMapClientTemplate().insert("FocusMarkMap.insertFocusMarkMap", focusMarkMap);
	}

    public void batchAddFocusMarkMap(List<FocusMarkMap> focusMarkMapList){
        batchInsert("FocusMarkMap.insertFocusMarkMap",focusMarkMapList);
    }

    /**
    * 根据主键查找
    * @
    */
    public FocusMarkMap getFocusMarkMapByKey(     Integer id       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        FocusMarkMap result = (FocusMarkMap)  getSqlMapClientTemplate().queryForObject(
                "FocusMarkMap.getFocusMarkMapByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @
     */
    public  List<FocusMarkMap> getFocusMarkMapByKeys(    List<Integer> idList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);
        return (List<FocusMarkMap>) getSqlMapClientTemplate().queryForList("FocusMarkMap.getFocusMarkMapsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @
     */
    public Integer deleteByKey(     Integer id       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return getSqlMapClientTemplate().delete("FocusMarkMap.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @
     */
    public Integer deleteByKeys(    List<Integer> idList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", idList);

        return getSqlMapClientTemplate().delete("FocusMarkMap.deleteByKeys", params);
    }

    /**
     * 根据条件删除关注映射
     * @return
     * @
     */
    public void deleteFocusMarkMapList(FocusMarkMapQuery focusMarkMapQuery)  {
        getSqlMapClientTemplate().delete("FocusMarkMap.deleteFocusMarkMapList", focusMarkMapQuery);
    }

    /**
     * 根据主键更新
     * @return
     * @
     */
    public Integer updateFocusMarkMapByKey(FocusMarkMap focusMarkMap){
		return  getSqlMapClientTemplate().update("FocusMarkMap.updateFocusMarkMapByKey", focusMarkMap);
	}
    @SuppressWarnings("unchecked")
    public Result<FocusMarkMap> getFocusMarkMapListWithPage(FocusMarkMapQuery focusMarkMapQuery){
	    Result<FocusMarkMap> rs = new Result<FocusMarkMap>(focusMarkMapQuery);
		            rs.setTotal(Long.parseLong(""+ getSqlMapClientTemplate().queryForObject("FocusMarkMap.getFocusMarkMapListCount",focusMarkMapQuery)));
            if(focusMarkMapQuery.getFields()!=null && focusMarkMapQuery.getFields()!=""){
                rs.setItems((List<FocusMarkMap>) getSqlMapClientTemplate().queryForList("FocusMarkMap.getFocusMarkMapListWithPageFields", focusMarkMapQuery));
            }else{
                rs.setItems((List<FocusMarkMap>) getSqlMapClientTemplate().queryForList("FocusMarkMap.getFocusMarkMapListWithPage", focusMarkMapQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<FocusMarkMap> getFocusMarkMapList(FocusMarkMapQuery focusMarkMapQuery){
    if(focusMarkMapQuery.getFields()!=null&&focusMarkMapQuery.getFields()!=""){
        return (List<FocusMarkMap>) getSqlMapClientTemplate().queryForList("FocusMarkMap.getFocusMarkMapListFields", focusMarkMapQuery);
    }
		return (List<FocusMarkMap>) getSqlMapClientTemplate().queryForList("FocusMarkMap.getFocusMarkMapList", focusMarkMapQuery);
	}

    public Map<String,List<Integer>> getMarkIdMap(Integer userId,Integer founderId,Integer focusType){
        FocusMarkMapQuery focusMarkMapQuery = new FocusMarkMapQuery();
        focusMarkMapQuery.setUserId(userId);
        focusMarkMapQuery.setFounderId(founderId);
        focusMarkMapQuery.setFocusType(focusType);
        focusMarkMapQuery.setFields("mark_id,focus_id");
        List<FocusMarkMap> focusMarkMapList = getFocusMarkMapList(focusMarkMapQuery);
        Map<String,List<Integer>> map = new HashMap<String, List<Integer>>();
        if(focusMarkMapList!=null){
            for(FocusMarkMap focusMarkMap : focusMarkMapList){
                if(map.containsKey(focusMarkMap.getFocusId())){
                    List<Integer> markIdList = map.get(focusMarkMap.getFocusId());
                    markIdList.add(focusMarkMap.getMarkId());
                }else{
                    List<Integer> markIdList = new ArrayList<Integer>();
                    markIdList.add(focusMarkMap.getMarkId());
                    map.put(focusMarkMap.getFocusId(),markIdList);
                }
            }
        }
        return map;
    }

}