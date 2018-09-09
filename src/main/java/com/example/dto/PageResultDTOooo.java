package com.example.dto;

import java.util.List;

/**
 * 分页结果DTO
 * 
 * @author 刘冬 博客出处：http://www.cnblogs.com/GoodHelper/
 *
 */
public class PageResultDTOooo {

	private long total;

	private List<?> rows;

	public PageResultDTOooo() {
	}

	public PageResultDTOooo(long total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
