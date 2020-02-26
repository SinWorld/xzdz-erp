package com.edge.business.purchase.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class PurchaseOrder_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String htmc;// 合同名称
	private String htbh;// 合同编号
	private Integer ghdw;// 供货单位
	private Integer wfdw;// 我方单位
	private Integer xsht;// 销售合同
	private Integer jbr;// 经办人
	private String htzt;// 合同状态
	private String beginTime;// 开始时间
	private String endTime;// 结束时间

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

	public String getHtmc() {
		return htmc;
	}

	public void setHtmc(String htmc) {
		this.htmc = htmc;
	}

	public String getHtbh() {
		return htbh;
	}

	public void setHtbh(String htbh) {
		this.htbh = htbh;
	}

	public Integer getGhdw() {
		return ghdw;
	}

	public void setGhdw(Integer ghdw) {
		this.ghdw = ghdw;
	}

	public Integer getWfdw() {
		return wfdw;
	}

	public void setWfdw(Integer wfdw) {
		this.wfdw = wfdw;
	}

	public Integer getXsht() {
		return xsht;
	}

	public void setXsht(Integer xsht) {
		this.xsht = xsht;
	}

	public Integer getJbr() {
		return jbr;
	}

	public void setJbr(Integer jbr) {
		this.jbr = jbr;
	}

	public String getHtzt() {
		return htzt;
	}

	public void setHtzt(String htzt) {
		this.htzt = htzt;
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
		return "PurchaseOrder_QueryVo [page=" + page + ", rows=" + rows + ", htmc=" + htmc + ", htbh=" + htbh
				+ ", ghdw=" + ghdw + ", wfdw=" + wfdw + ", xsht=" + xsht + ", jbr=" + jbr + ", htzt=" + htzt
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}

}
