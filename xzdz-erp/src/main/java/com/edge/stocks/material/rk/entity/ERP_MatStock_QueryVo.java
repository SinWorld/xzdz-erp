package com.edge.stocks.material.rk.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_MatStock_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量
	private String kw;// 库位
	private String bz;// 备注

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

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
	public String toString() {
		return "ERP_MatStock_QueryVo [page=" + page + ", rows=" + rows + ", kw=" + kw + ", bz=" + bz + "]";
	}

}
