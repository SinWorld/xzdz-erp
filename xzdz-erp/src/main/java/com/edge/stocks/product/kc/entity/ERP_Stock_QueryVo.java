package com.edge.stocks.product.kc.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Stock_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private Integer cp;// 成品
	private Integer kw;// 库位
	private String wlId;// 物料Id
	private Integer kcl1;// 库存量1
	private Integer kcl2;// 库存量2

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

	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public Integer getKw() {
		return kw;
	}

	public void setKw(Integer kw) {
		this.kw = kw;
	}

	public String getWlId() {
		return wlId;
	}

	public void setWlId(String wlId) {
		this.wlId = wlId;
	}

	public Integer getKcl1() {
		return kcl1;
	}

	public void setKcl1(Integer kcl1) {
		this.kcl1 = kcl1;
	}

	public Integer getKcl2() {
		return kcl2;
	}

	public void setKcl2(Integer kcl2) {
		this.kcl2 = kcl2;
	}

	@Override
	public String toString() {
		return "ERP_Stock_QueryVo [page=" + page + ", rows=" + rows + ", cp=" + cp + ", kw=" + kw + ", wlId=" + wlId
				+ ", kcl1=" + kcl1 + ", kcl2=" + kcl2 + "]";
	}

}
