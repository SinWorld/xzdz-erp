package com.edge.business.ckfh.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Delivery_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String ddbh;// 订单编号
	private Integer shdw;// 收货单位
	private Integer xsht;// 销售合同
	private Integer jbr;// 经办人
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

	public String getDdbh() {
		return ddbh;
	}

	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}

	public Integer getShdw() {
		return shdw;
	}

	public void setShdw(Integer shdw) {
		this.shdw = shdw;
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
		return "ERP_Delivery_QueryVo [page=" + page + ", rows=" + rows + ", ddbh=" + ddbh + ", shdw=" + shdw + ", xsht="
				+ xsht + ", jbr=" + jbr + ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}

}
