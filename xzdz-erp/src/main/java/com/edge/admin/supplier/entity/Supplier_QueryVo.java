package com.edge.admin.supplier.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Supplier_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String gysmc;// 供应商名称
	private String zcdz;// 注册地址
	private String bgdz;// 办公地址
	private String shtyxydm;// 社会统一信用代码
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

	public String getGysmc() {
		return gysmc;
	}

	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
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

	public String getShtyxydm() {
		return shtyxydm;
	}

	public void setShtyxydm(String shtyxydm) {
		this.shtyxydm = shtyxydm;
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
		return "Supplier_QueryVo [page=" + page + ", rows=" + rows + ", gysmc=" + gysmc + ", zcdz=" + zcdz + ", bgdz="
				+ bgdz + ", shtyxydm=" + shtyxydm + ", fddbr=" + fddbr + ", khh=" + khh + ", dh=" + dh + "]";
	}

}
