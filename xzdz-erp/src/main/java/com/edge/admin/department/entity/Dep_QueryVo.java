package com.edge.admin.department.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Dep_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String bmmc;
	private Integer sjbmdm;
	private String bmms;

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

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public Integer getSjbmdm() {
		return sjbmdm;
	}

	public void setSjbmdm(Integer sjbmdm) {
		this.sjbmdm = sjbmdm;
	}

	public String getBmms() {
		return bmms;
	}

	public void setBmms(String bmms) {
		this.bmms = bmms;
	}

	@Override
	public String toString() {
		return "Dep_QueryVo [page=" + page + ", rows=" + rows + ", bmmc=" + bmmc + ", sjbmdm=" + sjbmdm + ", bmms="
				+ bmms + "]";
	}

}
