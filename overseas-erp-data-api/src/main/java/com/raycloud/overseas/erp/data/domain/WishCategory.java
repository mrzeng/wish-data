package com.raycloud.overseas.erp.data.domain;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author  zhanxiaofeng@raycloud.com
 * @date    2017-02-27
 */
public class WishCategory  extends BasePojo {

    /**
	 *序列化ID
	 */
    private static final long serialVersionUID = 4505027104118947673L;

	/**
     * 主键id
     */
    private String id;
	/**
     * 行业类目id
     */
    private String catId;
	/**
     * 中文名
     */
    private String chineseName;
	/**
     * english_name
     */
    private String englishName;
	/**
     * 父类目id
     */
    private String parentId;
	/**
     * 层级
     */
    private Integer level;
	/**
     * updated
     */
    private Date updated;
	/**
     * is_valid
     */
    private Integer isValid;
    /**
     * wish子类目
     */
    private Map<String,WishCategory> childCats;

    public Map<String, WishCategory> getChildCats() {
        return childCats;
    }

    public void setChildCats(Map<String, WishCategory> childCats) {
        this.childCats = childCats;
    }

    /**
    * @return id 主键id
    */
    public String getId() {
       return id;
    }
   /**
    * @param id 主键id
    */
    public void setId(String id) {
       this.id = id;
    }

   /**
    * @return catId 行业类目id
    */
    public String getCatId() {
       return catId;
    }
   /**
    * @param catId 行业类目id
    */
    public void setCatId(String catId) {
       this.catId = catId;
    }

   /**
    * @return chineseName 中文名
    */
    public String getChineseName() {
       return chineseName;
    }
   /**
    * @param chineseName 中文名
    */
    public void setChineseName(String chineseName) {
       this.chineseName = chineseName;
    }

   /**
    * @return englishName english_name
    */
    public String getEnglishName() {
       return englishName;
    }
   /**
    * @param englishName english_name
    */
    public void setEnglishName(String englishName) {
       this.englishName = englishName;
    }

   /**
    * @return parentId 父类目id
    */
    public String getParentId() {
       return parentId;
    }
   /**
    * @param parentId 父类目id
    */
    public void setParentId(String parentId) {
       this.parentId = parentId;
    }

   /**
    * @return level 层级
    */
    public Integer getLevel() {
       return level;
    }
   /**
    * @param level 层级
    */
    public void setLevel(Integer level) {
       this.level = level;
    }

   /**
    * @return updated updated
    */
    public Date getUpdated() {
       return updated;
    }
   /**
    * @param updated updated
    */
    public void setUpdated(Date updated) {
       this.updated = updated;
    }

   /**
    * @return isValid is_valid
    */
    public Integer getIsValid() {
       return isValid;
    }
   /**
    * @param isValid is_valid
    */
    public void setIsValid(Integer isValid) {
       this.isValid = isValid;
    }

}