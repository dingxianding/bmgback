package com.example.dto;

/**
 * 分页查询参数DTO
 * 
 * @author 刘冬 博客出处：http://www.cnblogs.com/GoodHelper/
 *
 */
public class PageQueryParamDTO {

	/**
	 * 页码
	 */
	private int current;

	/**
	 * 每页数量
	 */
	private int pageSize;

	/**
	 * 查询条件
	 */
	private Object query;

	public PageQueryParamDTO() {
	}

	public PageQueryParamDTO(int page, int size, Object query) {
		this.current = page;
		this.pageSize = size;
		this.query = query;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Object getQuery() {
		return query;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

}
