package com.edge.admin.materielId.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_MaterielId_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String wlId;// 物料Id
	private String ggxh;// 规格型号
	private String bzq;// 保质期
	private String type;// 类型
	private Double ckj1;// 参考价1
	private Double ckj2;// 参考价2

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

	public String getWlId() {
		return wlId;
	}

	public void setWlId(String wlId) {
		this.wlId = wlId;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getBzq() {
		return bzq;
	}

	public void setBzq(String bzq) {
		this.bzq = bzq;
	}

	public String isType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getCkj1() {
		return ckj1;
	}

	public void setCkj1(Double ckj1) {
		this.ckj1 = ckj1;
	}

	public Double getCkj2() {
		return ckj2;
	}

	public void setCkj2(Double ckj2) {
		this.ckj2 = ckj2;
	}

	@Override
	public String toString() {
		return "ERP_MaterielId_QueryVo [page=" + page + ", rows=" + rows + ", wlId=" + wlId + ", ggxh=" + ggxh
				+ ", bzq=" + bzq + ", type=" + type + ", ckj1=" + ckj1 + ", ckj2=" + ckj2 + "]";
	}

}
