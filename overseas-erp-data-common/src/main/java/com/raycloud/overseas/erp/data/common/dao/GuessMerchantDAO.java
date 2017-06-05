
package com.raycloud.overseas.erp.data.common.dao;

import com.raycloud.overseas.erp.data.domain.GuessMerchant;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.GuessMerchantQuery;

/**
 * author  zhanxiaofengraycloud.com
 * date    2017-02-27
 */

@Repository
public class GuessMerchantDAO  extends BaseDAO{

    /**
     * 新增猜你喜欢店铺
     * @param guessMerchant
     * @return
     */
	public Integer addGuessMerchant(GuessMerchant guessMerchant){
		return (Integer) getSqlMapClientTemplate().insert("GuessMerchant.insertGuessMerchant", guessMerchant);
	}

    /**
     * 批量新增猜你喜欢店铺
     * @param guessMerchantList
     */
    public void batchAddGuessMerchant(List<GuessMerchant> guessMerchantList) {
        batchInsert("GuessMerchant.insertGuessMerchant",guessMerchantList);
    }

    /**
    * 根据主键查找
    * 
    */
    public GuessMerchant getGuessMerchantByKey(     String merchantId       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("merchantId", merchantId);
        GuessMerchant result = (GuessMerchant)  getSqlMapClientTemplate().queryForObject(
                "GuessMerchant.getGuessMerchantByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     */
    public  List<GuessMerchant> getGuessMerchantByKeys(    List<String> merchantIdList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", merchantIdList);
        return (List<GuessMerchant>) getSqlMapClientTemplate().queryForList("GuessMerchant.getGuessMerchantsByKeys", params);
    }
    /**
     * 根据主键删除
     * return
     * 
     */
    public Integer deleteByKey(     String merchantId       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("merchantId", merchantId);
        return getSqlMapClientTemplate().delete("GuessMerchant.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * return
     * 
     */
    public Integer deleteByKeys(    List<String> merchantIdList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keys", merchantIdList);

        return getSqlMapClientTemplate().delete("GuessMerchant.deleteByKeys", params);
    }

    /**
     * 根据查询条件删除店铺
     * @return
     */
    public void deleteGuessMerchantList(GuessMerchantQuery guessMerchantQuery)  {
        getSqlMapClientTemplate().delete("GuessMerchant.deleteGuessMerchantList", guessMerchantQuery);
    }

    /**
     * 根据主键更新
     * return
     * 
     */
    public Integer updateGuessMerchantByKey(GuessMerchant guessMerchant) {
		return  getSqlMapClientTemplate().update("GuessMerchant.updateGuessMerchantByKey", guessMerchant);
	}


    public Result<GuessMerchant> getGuessMerchantListWithPage(GuessMerchantQuery guessMerchantQuery){
	    Result<GuessMerchant> rs = new Result<GuessMerchant>(guessMerchantQuery);
		            rs.setTotal(Long.parseLong(""+  getSqlMapClientTemplate().queryForObject("GuessMerchant.getGuessMerchantListCount",guessMerchantQuery)));
            if(guessMerchantQuery.getFields()!=null && guessMerchantQuery.getFields()!=""){
                rs.setItems((List<GuessMerchant>) getSqlMapClientTemplate().queryForList("GuessMerchant.getGuessMerchantListWithPageFields", guessMerchantQuery));
            }else{
                rs.setItems((List<GuessMerchant>) getSqlMapClientTemplate().queryForList("GuessMerchant.getGuessMerchantListWithPage", guessMerchantQuery));
            }
		return rs;
	}


    public List<GuessMerchant> getGuessMerchantList(GuessMerchantQuery guessMerchantQuery) {
    if(guessMerchantQuery.getFields()!=null&&guessMerchantQuery.getFields()!=""){
        return (List<GuessMerchant>) getSqlMapClientTemplate().queryForList("GuessMerchant.getGuessMerchantListFields", guessMerchantQuery);
    }
		return (List<GuessMerchant>) getSqlMapClientTemplate().queryForList("GuessMerchant.getGuessMerchantList", guessMerchantQuery);
	}

    /**
     * 获取猜你喜欢店铺数量
     * @param guessMerchantQuery
     * @return
     */
    public Integer getGuessMerchantCount(GuessMerchantQuery guessMerchantQuery){
        return (Integer)  getSqlMapClientTemplate().queryForObject("GuessMerchant.getGuessMerchantListCount",guessMerchantQuery);
    }
}