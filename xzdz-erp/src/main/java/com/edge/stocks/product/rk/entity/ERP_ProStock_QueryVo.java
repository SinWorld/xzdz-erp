package com.edge.stocks.product.rk.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_ProStock_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

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

	@Override
	public String toString() {
		return "ERP_ProStock_QueryVo [page=" + page + ", rows=" + rows + "]";
	}

}
