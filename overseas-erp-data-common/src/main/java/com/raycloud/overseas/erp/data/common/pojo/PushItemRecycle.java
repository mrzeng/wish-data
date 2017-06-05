package com.raycloud.overseas.erp.data.common.pojo;

import java.util.*;
import java.io.Serializable;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-04-21
 */
public class PushItemRecycle  extends BasePojo {

    public PushItemRecycle() {
    }

    public PushItemRecycle(String id, Integer userId, Integer founderId) {
        this.id = id;
        this.userId = userId;
        this.founderId = founderId;
    }

    /**
	 *序列化ID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 用户id+创始人id+宝贝id
     */
    private String id;
	/**
     * user_id
     */
    private Integer userId;
	/**
     * founder_id
     */
    private Integer founderId;


   /**
    * @return id 用户id+创始人id+宝贝id
    */
    public String getId() {
       return id;
    }
   /**
    * @param id 用户id+创始人id+宝贝id
    */
    public void setId(String id) {
       this.id = id;
    }

   /**
    * @return userId user_id
    */
    public Integer getUserId() {
       return userId;
    }
   /**
    * @param userId user_id
    */
    public void setUserId(Integer userId) {
       this.userId = userId;
    }

   /**
    * @return founderId founder_id
    */
    public Integer getFounderId() {
       return founderId;
    }
   /**
    * @param founderId founder_id
    */
    public void setFounderId(Integer founderId) {
       this.founderId = founderId;
    }

}