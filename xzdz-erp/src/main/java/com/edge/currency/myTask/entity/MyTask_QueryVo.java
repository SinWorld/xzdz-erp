package com.edge.currency.myTask.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class MyTask_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量
	private Integer userId;// 用户主键

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "MyTask_QueryVo [page=" + page + ", rows=" + rows + ", userId=" + userId + "]";
	}

}
