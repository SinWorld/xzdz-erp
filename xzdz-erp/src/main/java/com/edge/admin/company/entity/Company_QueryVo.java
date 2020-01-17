package com.edge.admin.company.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Company_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String dwmc;// 单位名称
	private String zcdz;// 注册地址
	private String bgdz;// 办公地址
	private String xydm;// 信用代码
	private String fddbr;// 法定代表人
	private String khh;// 开户行
	private String dh;// 电话

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

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getZcdz() {
		return zcdz;
	}

	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	public String getBgdz() {
		return bgdz;
	}

	public void setBgdz(String bgdz) {
		this.bgdz = bgdz;
	}

	public String getXydm() {
		return xydm;
	}

	public void setXydm(String xydm) {
		this.xydm = xydm;
	}

	public String getFddbr() {
		return fddbr;
	}

	public void setFddbr(String fddbr) {
		this.fddbr = fddbr;
	}

	public String getKhh() {
		return khh;
	}

	public void setKhh(String khh) {
		this.khh = khh;
	}

	public String getDh() {
		return dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	@Override
	public String toString() {
		return "Customer_QueryVo [page=" + page + ", rows=" + rows + ", dwmc=" + dwmc + ", zcdz=" + zcdz + ", bgdz="
				+ bgdz + ", xydm=" + xydm + ", fddbr=" + fddbr + ", khh=" + khh + ", dh=" + dh + "]";
	}

}
