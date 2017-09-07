package com.jiajun.imagehosting.common.base.dao;

import java.util.List;

import com.jiajun.imagehosting.common.bo.Page;

/**
 * @描述： baseDao接口
 * @author tvu
 * @date 2017年5月1日下午3:58:47
 */
@SuppressWarnings("rawtypes")
public interface BaseDao {
	
	/**
	 * insert
	 * @param statement 对应mapper的方法
	 * @param obj
	 */
	int insert(String statement, Object obj) throws Exception;
	
	/**
	 * delete
	 * @param statement
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	int delete(String statement, Object obj) throws Exception;
	
	/**
	 * update
	 * @param statement
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	int update(String statement, Object obj) throws Exception;

	/**
	 * 返回一个对象
	 * @param statement
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	Object selectObject(String statement, Object obj) throws Exception;
	
	/**
	 * 返回一个集合
	 * @param statement
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	List selectList(String statement, Object obj) throws Exception;
	
	
	/**
	 * 分页查询
	 * @param page
	 * @param countStatement 查询总个数sql
	 * @param listStatement list sql
	 * @throws Exception
	 */
	void page(Page<?> page, String countStatement,String listStatement) throws Exception;
	
	/**
	 * 批量插入
	 * @param strtrment
	 * @param list
	 * @return
	 * @throws Exception
	 */
	void batchInsert(String statement, List list) throws Exception;
	
	/**
	 * 批量修改
	 * @param statement
	 * @param list
	 * @return
	 * @throws Exception
	 */
	void batchUpdate(String statement, List list) throws Exception;
	
	/**
	 * 批量删除
	 * @param statement
	 * @param lisst
	 * @return
	 * @throws Exception
	 */
	void batchDelete(String statement, List list) throws Exception;
}
