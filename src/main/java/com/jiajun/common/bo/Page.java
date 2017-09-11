package com.jiajun.common.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jiajun.common.util.JsonUtils;

/**
 *  分页对象
 */
public class Page<T> implements java.io.Serializable{
	
	private static final long serialVersionUID = -2355709752059511551L;

	private int currentPage;
	
	private int pageSize;
	
	private Map<String,Object> conditions = new HashMap<>(); //分页条件

	private int count; //总共的条数

	private int totalPage; //总页数

	private List<T> list; //对象集合
	
	
	private void init() {
		currentPage = 1;
		pageSize = 25; //default page size
	}
	
	public Page() {
		init();
	}
	
	public Page (HttpServletRequest request) {
		init();
		Map<String, String[]> paramMap = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = paramMap.entrySet();
		if(entrySet != null) {
			String key;
			String[] value;
			for (java.util.Map.Entry<String, String[]> e : entrySet) {
				key = e.getKey();
				value = e.getValue();
				if(value != null && value.length > 1) {
					conditions.put(key, value);
				} else {
					if("currentPage".equals(key) && Integer.valueOf(value[0])>0) {
						currentPage = Integer.valueOf(value[0]);
					} else if("pageSize".equals(key) && Integer.valueOf(value[0]) > 0) {
						pageSize = Integer.valueOf(value[0]);
					} else {
						conditions.put(key, value[0]);
					}
				}
			}
		}
	}
	
	public void addCondition(String name, Object value) {
		this.conditions.put(name, value);
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		return JsonUtils.toString(this);
	}
	
}
