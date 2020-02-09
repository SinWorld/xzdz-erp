package com.edge.business.sale.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Sales_Contract_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String htmc;// 合同名称
	private String htbh;// 合同编号
	private Integer spzt;// 审批状态
	private Integer gf;// 供方
	private Integer xf;// 需方
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

	public Integer getSpzt() {
		return spzt;
	}

	public void setSpzt(Integer spzt) {
		this.spzt = spzt;
	}

	public Integer getGf() {
		return gf;
	}

	public void setGf(Integer gf) {
		this.gf = gf;
	}

	public Integer getXf() {
		return xf;
	}

	public void setXf(Integer xf) {
		this.xf = xf;
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
		return "ERP_Sales_Contract_QueryVo [page=" + page + ", rows=" + rows + ", htmc=" + htmc + ", htbh=" + htbh
				+ ", spzt=" + spzt + ", gf=" + gf + ", xf=" + xf + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ "]";
	}

}
