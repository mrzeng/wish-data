
package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.PushItemRecycleQuery;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.common.query.FocusItemQuery;
import com.raycloud.overseas.erp.data.common.pojo.FocusItem;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */

@Repository
public class FocusItemDAO  extends BaseDAO{

    private static Logger logger= Logger.getLogger(FocusItemDAO.class);

    public Integer addFocusItem(FocusItem focusItem){
		return (Integer) getSqlMapClientTemplate().insert("FocusItem.insertFocusItem", focusItem);
	}


    public void batchAddFocusItem(List<FocusItem> focusItemList){
        batchInsert("FocusItem.insertFocusItem", focusItemList);
    }

    /**
    * 根据主键查找
    * @throws SQLException
    */
    public FocusItem getFocusItemByKey(Integer founderId,     String userId  ,   String itemId       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("founderId", founderId);
        params.put("itemId", itemId);
        params.put("fkId",1L);
        FocusItem result = (FocusItem)  getSqlMapClientTemplate().queryForObject(
                "FocusItem.getFocusItemByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @throws SQLException
     */
    public  List<FocusItem> getFocusItemByKeys(    String userId,List<String> itemIdList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("keys", itemIdList);
        params.put("fkId",1L);
        return (List<FocusItem>) getSqlMapClientTemplate().queryForList("FocusItem.getFocusItemsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKey(     String userId  ,   String itemId       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("itemId", itemId);
        params.put("fkId",1L);
        return getSqlMapClientTemplate().delete("FocusItem.deleteByKey", params);
    }
    /**
     * 根据主键删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByItemId(String itemId       ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        params.put("fkId",1L);
        return getSqlMapClientTemplate().delete("FocusItem.deleteByItemId", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @throws SQLException
     */
    public Integer deleteByKeys(    String userId,List<String> itemIdList    ) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("keys", itemIdList);
        params.put("fkId",1L);
        return getSqlMapClientTemplate().delete("FocusItem.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public Integer updateFocusItemByKey(FocusItem focusItem){
		return  getSqlMapClientTemplate().update("FocusItem.updateFocusItemByKey", focusItem);
	}

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public void updateFocusItemByQuery(FocusItemQuery focusItemQuery){
            getSqlMapClientTemplate().update("FocusItem.updateFocusItemByQuery", focusItemQuery);
    }

    /**
     * 根据主键更新
     * @return
     * @throws SQLException
     */
    public void batchUpdateFocusItemByKey(List<FocusItem> focusItemList){
        batchUpdate("FocusItem.updateFocusItemByKey", focusItemList);
    }

    @SuppressWarnings("unchecked")
    public Result<FocusItem> getFocusItemListWithPage(FocusItemQuery focusItemQuery){
	    Result<FocusItem> rs = new Result<FocusItem>(focusItemQuery);
		            rs.setTotal(Long.parseLong(getSqlMapClientTemplate().queryForObject("FocusItem.getFocusItemListCount",focusItemQuery)+""));
            if(focusItemQuery.getFields()!=null && focusItemQuery.getFields()!=""){
                rs.setItems((List<FocusItem>) getSqlMapClientTemplate().queryForList("FocusItem.getFocusItemListWithPageFields", focusItemQuery));
            }else{
                rs.setItems((List<FocusItem>) getSqlMapClientTemplate().queryForList("FocusItem.getFocusItemListWithPage", focusItemQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<FocusItem> getFocusItemList(FocusItemQuery focusItemQuery){
    if(focusItemQuery.getFields()!=null&&focusItemQuery.getFields()!=""){
        return (List<FocusItem>) getSqlMapClientTemplate().queryForList("FocusItem.getFocusItemListFields", focusItemQuery);
    }
		return (List<FocusItem>) getSqlMapClientTemplate().queryForList("FocusItem.getFocusItemList", focusItemQuery);
	}

    /**
     * 根据查询条件获取关注列表的个数
     * @param focusItemQuery
     * @return
     */
    public Long getFocusItemListCount(FocusItemQuery focusItemQuery){
        return Long.parseLong(""+getSqlMapClientTemplate().queryForObject("FocusItem.getFocusItemListCount",focusItemQuery));
    }

    public Map<String,String> getItemIdMap(FocusItemQuery focusItemQuery){
        return (Map<String,String>) getSqlMapClientTemplate().queryForMap("FocusItem.getItemIdMap", focusItemQuery,"itemId","itemId");
    }
}