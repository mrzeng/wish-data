
package com.raycloud.overseas.erp.data.common.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Collections;

import com.raycloud.overseas.erp.data.common.common.Result;
import com.raycloud.overseas.erp.data.common.query.FocusMerchantQuery;
import com.raycloud.overseas.erp.data.common.pojo.FocusMerchant;

/**
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */

@Repository
public class FocusMerchantDAO  extends BaseDAO{


	public Integer addFocusMerchant(FocusMerchant focusMerchant) {
		return (Integer) getSqlMapClientTemplate().insert("FocusMerchant.insertFocusMerchant", focusMerchant);
	}

    /**
    * 根据主键查找
    * @
    */
    public FocusMerchant getFocusMerchantByKey(Integer founderId,     String userId  ,   String merchantId       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fkId",1L);
        params.put("userId", userId);
        params.put("founderId", founderId);
        params.put("merchantId", merchantId);
        FocusMerchant result = (FocusMerchant)  getSqlMapClientTemplate().queryForObject(
                "FocusMerchant.getFocusMerchantByKey", params);
        return result;
    }
    /**
     * 根据主键批量查找
     * @
     */
    public  List<FocusMerchant> getFocusMerchantByKeys(Integer founderId,String userId,List<String> merchantIdList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fkId",1L);
        params.put("founderId", founderId);
        params.put("userId", userId);
        params.put("keys", merchantIdList);
        return (List<FocusMerchant>) getSqlMapClientTemplate().queryForList("FocusMerchant.getFocusMerchantsByKeys", params);
    }
    /**
     * 根据主键删除
     * @return
     * @
     */
    public Integer deleteByKey(     String userId  ,   String merchantId       )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fkId",1L);
        params.put("userId", userId);
        params.put("merchantId", merchantId);
        return getSqlMapClientTemplate().delete("FocusMerchant.deleteByKey", params);
    }
    /**
     * 根据主键批量删除
     * @return
     * @
     */
    public Integer deleteByKeys(    String userId,List<String> merchantIdList    )  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fkId",1L);
        params.put("userId", userId);
        params.put("keys", merchantIdList);

        return getSqlMapClientTemplate().delete("FocusMerchant.deleteByKeys", params);
    }

    /**
     * 根据主键更新
     * @return
     * @
     */
    public Integer updateFocusMerchantByKey(FocusMerchant focusMerchant) {
		return  getSqlMapClientTemplate().update("FocusMerchant.updateFocusMerchantByKey", focusMerchant);
	}
    @SuppressWarnings("unchecked")
    public Result<FocusMerchant> getFocusMerchantListWithPage(FocusMerchantQuery focusMerchantQuery){
	    Result<FocusMerchant> rs = new Result<FocusMerchant>(focusMerchantQuery);
		            rs.setTotal(Long.parseLong(""+getSqlMapClientTemplate().queryForObject("FocusMerchant.getFocusMerchantListCount",focusMerchantQuery)));
            if(focusMerchantQuery.getFields()!=null && focusMerchantQuery.getFields()!=""){
                rs.setItems((List<FocusMerchant>) getSqlMapClientTemplate().queryForList("FocusMerchant.getFocusMerchantListWithPageFields", focusMerchantQuery));
            }else{
                rs.setItems((List<FocusMerchant>) getSqlMapClientTemplate().queryForList("FocusMerchant.getFocusMerchantListWithPage", focusMerchantQuery));
            }
		return rs;
	}

    @SuppressWarnings("unchecked")
    public List<FocusMerchant> getFocusMerchantList(FocusMerchantQuery focusMerchantQuery) {
    if(focusMerchantQuery.getFields()!=null&&focusMerchantQuery.getFields()!=""){
        return (List<FocusMerchant>) getSqlMapClientTemplate().queryForList("FocusMerchant.getFocusMerchantListFields", focusMerchantQuery);
    }
		return (List<FocusMerchant>) getSqlMapClientTemplate().queryForList("FocusMerchant.getFocusMerchantList", focusMerchantQuery);
	}

    public Long getFocusMerchantListCount(FocusMerchantQuery focusMerchantQuery){
        return (Long)  getSqlMapClientTemplate().queryForObject("FocusMerchant.getFocusMerchantListCount",focusMerchantQuery);
    }

}