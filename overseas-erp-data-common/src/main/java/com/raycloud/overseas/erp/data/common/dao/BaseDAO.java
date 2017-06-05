package com.raycloud.overseas.erp.data.common.dao;

import com.alibaba.cobar.client.CobarSqlMapClientDaoSupport;
import com.alibaba.cobar.client.CobarSqlMapClientTemplate;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @version: 1.0
 * @version: Nov 26, 2010 1:53:06 PM
 * @author: Administrator
 */
public abstract class BaseDAO extends CobarSqlMapClientDaoSupport {
	/**
	 * The Batch size.
	 */
	int BATCH_SIZE = 1000;

	@Resource(name = "sqlMapClientTemplate")
	private CobarSqlMapClientTemplate cobarSqlMapClientTemplate;

	/**
	 * Init sql map client.
	 */
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClientTemplate(cobarSqlMapClientTemplate);
	}

	/**
	 * 方法描述：批量插入数据 list批量插入的对象，statement SqlMap.xml一条语句的id
	 *
	 * @param list      the list
	 * @param statement the statement
	 * @throws java.sql.SQLException
	 * @param: {@link List @link java.lang.String}
	 * @return:
	 * @version: 1.0
	 * @version: Nov 26, 2010 2:05:02 PM
	 * @author: Administrator
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertBatch(final List list, final String statement) {
		if (!CollectionUtils.isEmpty(list)) {
			List subList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				subList.add(list.get(i));
				if (i != 0 && i % BATCH_SIZE == 0) {
					super.batchInsert(statement, subList);
					subList.clear();
				}
			}
            super.batchInsert(statement, subList);
			subList.clear();
		}
	}

	/**
	 * 方法描述：批量更新数据 list批量更新的对象，statement SqlMap.xml一条语句的id
	 *
	 * @param list      the list
	 * @param statement the statement
	 * @param: {@link List @link java.lang.String}
	 * @return:
	 * @version: 1.0
	 * @version: Nov 26, 2010 2:05:02 PM
	 * @author: Administrator
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateBatch(final List list, final String statement) {
		if (!CollectionUtils.isEmpty(list)) {
			List subList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				subList.add(list.get(i));
				if (i != 0 && i % BATCH_SIZE == 0) {
					super.batchUpdate(statement, subList);
					subList.clear();
				}
			}
			super.batchUpdate(statement, subList);
			subList.clear();
		}
	}

	/**
	 * 方法描述：批量删除数据 list批量删除对象，statement SqlMap.xml一条语句的nid
	 *
	 * @param list      the list
	 * @param statement the statement
	 * @param: {@link List @link java.lang.String}
	 * @return:
	 * @version: 1.0
	 * @version: Nov 26, 2010 2:05:02 PM
	 * @author: Administrator
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteBatch(final List list, final String statement) {
		if (!CollectionUtils.isEmpty(list)) {
			List subList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				subList.add(list.get(i));
				if (i != 0 && i % BATCH_SIZE == 0) {
					super.batchDelete(statement, subList);
					subList.clear();
				}
			}
			super.batchDelete(statement, subList);
			subList.clear();
		}
	}

}
