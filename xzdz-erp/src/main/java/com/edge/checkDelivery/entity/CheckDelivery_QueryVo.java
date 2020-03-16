package com.edge.checkDelivery.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class CheckDelivery_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private Integer xsht;// 销售合同
	private Integer spzt;// 规格型号
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

	public Integer getXsht() {
		return xsht;
	}

	public void setXsht(Integer xsht) {
		this.xsht = xsht;
	}

	public Integer getSpzt() {
		return spzt;
	}

	public void setSpzt(Integer spzt) {
		this.spzt = spzt;
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
		return "CheckDelivery_QueryVo [page=" + page + ", rows=" + rows + ", xsht=" + xsht + ", spzt=" + spzt
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}

}
