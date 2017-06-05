
package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.domain.GuessItem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.common.query.GuessItemQuery;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */

@Repository
public class GuessItemDAO  extends BaseDAO{


	public Integer addGuessItem(GuessItem guessItem) {
		return (Integer) getSqlMapClientTemplate().insert("GuessItem.insertGuessItem", guessItem);
	}

    public void batchAddGuessItem(List<GuessItem> guessItemList) {
        batchInsert("GuessItem.insertGuessItem",guessItemList);
    }

    /**
    * 根据主键查找
    * @
    */
    public GuessItem getGuessItemByKey(     String itemId       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        GuessItem result = (GuessItem)  getSqlMapClientTemplate().queryForObject(
                "GuessItem.getGuessItemByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @
     */
    public  List<GuessItem> getGuessItemByKeys(    List<String> itemIdList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", itemIdList);
        return (List<GuessItem>) getSqlMapClientTemplate().queryForList("GuessItem.getGuessItemsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @
     */
    public Integer deleteByKey(     String itemId       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        return getSqlMapClientTemplate().delete("GuessItem.deleteByKey", params);
    }

    /**
     * 根据查询条件删除宝贝
     * @return
     */
    public void deleteGuessItemList(GuessItemQuery guessItemQuery)  {
        getSqlMapClientTemplate().delete("GuessItem.deleteGuessItemList", guessItemQuery);
    }

    /**
     * 根据主键批量删除
     * @return
     * @
     */
    public Integer deleteByKeys(    List<String> itemIdList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", itemIdList);

        return getSqlMapClientTemplate().delete("GuessItem.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @
     */
    public Integer updateGuessItemByKey(GuessItem guessItem) {
		return  getSqlMapClientTemplate().update("GuessItem.updateGuessItemByKey", guessItem);
	}

    @SuppressWarnings("unchecked")
    public Result<GuessItem> getGuessItemListWithPage(GuessItemQuery guessItemQuery){
	    Result<GuessItem> rs = new Result<GuessItem>(guessItemQuery);
		            rs.setTotal(Long.parseLong(""+  getSqlMapClientTemplate().queryForObject("GuessItem.getGuessItemListCount",guessItemQuery)));
            if(guessItemQuery.getFields()!=null && guessItemQuery.getFields()!=""){
                rs.setItems((List<GuessItem>) getSqlMapClientTemplate().queryForList("GuessItem.getGuessItemListWithPageFields", guessItemQuery));
            }else{
                rs.setItems((List<GuessItem>) getSqlMapClientTemplate().queryForList("GuessItem.getGuessItemListWithPage", guessItemQuery));
            }
		return rs;
	}

    /**
     * 获取猜你喜欢宝贝数量
     * @param guessItemQuery
     * @return
     */
    public Integer getGuessItemCount(GuessItemQuery guessItemQuery){
        return (Integer)  getSqlMapClientTemplate().queryForObject("GuessItem.getGuessItemListCount",guessItemQuery);
    }

    @SuppressWarnings("unchecked")
    public List<GuessItem> getGuessItemList(GuessItemQuery guessItemQuery) {
    if(guessItemQuery.getFields()!=null&&guessItemQuery.getFields()!=""){
        return (List<GuessItem>) getSqlMapClientTemplate().queryForList("GuessItem.getGuessItemListFields", guessItemQuery);
    }
		return (List<GuessItem>) getSqlMapClientTemplate().queryForList("GuessItem.getGuessItemList", guessItemQuery);
	}


}