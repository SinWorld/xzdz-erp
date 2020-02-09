package com.edge.stocks.material.rk.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_MatStockRecord_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private Integer cl;
	private Integer kw;
	private Integer jbr;
	private Integer rksl;
	private String beginTime;
	private String endTime;

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

	public Integer getCl() {
		return cl;
	}

	public void setCl(Integer cl) {
		this.cl = cl;
	}

	public Integer getKw() {
		return kw;
	}

	public void setKw(Integer kw) {
		this.kw = kw;
	}

	public Integer getJbr() {
		return jbr;
	}

	public void setJbr(Integer jbr) {
		this.jbr = jbr;
	}

	public Integer getRksl() {
		return rksl;
	}

	public void setRksl(Integer rksl) {
		this.rksl = rksl;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "ERP_MatStockRecord_QueryVo [page=" + page + ", rows=" + rows + ", cl=" + cl + ", kw=" + kw + ", jbr="
				+ jbr + ", rksl=" + rksl + ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}

}
