package com.edge.currency.notice.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Notice_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量
	private Integer userId;// 用户主键

	private String mbyhs;

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

	public String getMbyhs() {
		return mbyhs;
	}

	public void setMbyhs(String mbyhs) {
		this.mbyhs = mbyhs;
	}

	@Override
	public String toString() {
		return "Notice_QueryVo [page=" + page + ", rows=" + rows + ", userId=" + userId + ", mbyhs=" + mbyhs + "]";
	}

}
