package com.jiajun.common.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiajun.common.base.dao.BaseDao;
import com.jiajun.common.bo.Page;

@Repository
@SuppressWarnings(value= {"unchecked","rawtypes"})
public class BaseDaoImpl extends  SqlSessionDaoSupport implements BaseDao {
	
	private static final String DEFAULT_PAGE_COUNT_STATEMENT = "selectPageCount";
	private static final String DEFAILT_PAGE_LIST_STATEMENT = "selectPageList";
	
	
	@Override
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int insert(String statement, Object obj) throws Exception {
		 return this.getSqlSession().insert(statement, obj);
	}

	@Override
	public int delete(String statement, Object obj) throws Exception {
		return this.getSqlSession().delete(statement, obj);
	}
	
	@Override
	public int update(String statement, Object obj) throws Exception {
		return this.getSqlSession().update(statement, obj);
	}

	@Override
	public List selectList(String statement, Object obj) throws Exception {
		return this.getSqlSession().selectList(statement, obj);
		
	}

	@Override
	public Object selectObject(String statement, Object obj) throws Exception {
		return this.getSqlSession().selectOne(statement, obj);
	}

	@Override
	public void batchInsert(String statement, List list) throws Exception {
		for (Object object : list) {
			this.getSqlSession().insert(statement, object);
		}
	}

	@Override
	public void batchUpdate(String statement, List list) throws Exception {
		for (Object object : list) {
			this.getSqlSession().update(statement, object);
		}
	}

	@Override
	public void batchDelete(String statement,  List list) throws Exception {
		for (Object object : list) {
			this.getSqlSession().delete(statement, object);
		}
	}


	@Override
	public void page(Page<?> page, String countStatement, String listStatement) throws Exception {
		Map<String, Object> conditions = page.getConditions();
		int count = (int) selectObject(countStatement, conditions);
		page.setCount(count);
		if(count > 0) {
			int pageSize = page.getPageSize();
			int begin  = (page.getCurrentPage()-1)*pageSize;
			conditions.put("begin", begin);
			conditions.put("pageSize", pageSize);
			
			List list =  selectList(listStatement, conditions);
			int totalPage = (count-1)/page.getPageSize() + 1;
			page.setTotalPage(totalPage);
			page.setList(list);
			conditions.remove("begin");
			conditions.remove("pageSize");
		}
	}

	@Override
	public void page(Page<?> page) throws Exception {
		this.page(page, DEFAULT_PAGE_COUNT_STATEMENT, DEFAILT_PAGE_LIST_STATEMENT);
	}
}
