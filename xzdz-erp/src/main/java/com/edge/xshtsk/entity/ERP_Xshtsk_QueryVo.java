package com.edge.xshtsk.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Xshtsk_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private Integer xsht;
	private Integer fpkj;
	private Integer fplb;
	private Double fpje1;
	private Double fpje2;
	private Double ysk1;
	private Double ysk2;
	private Double sjsk1;
	private Double sjsk2;
	private String beginTime;
	private String endTime;
	private Integer spzt;

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

	public Integer getFpkj() {
		return fpkj;
	}

	public void setFpkj(Integer fpkj) {
		this.fpkj = fpkj;
	}

	public Integer getFplb() {
		return fplb;
	}

	public void setFplb(Integer fplb) {
		this.fplb = fplb;
	}

	public Double getFpje1() {
		return fpje1;
	}

	public void setFpje1(Double fpje1) {
		this.fpje1 = fpje1;
	}

	public Double getFpje2() {
		return fpje2;
	}

	public void setFpje2(Double fpje2) {
		this.fpje2 = fpje2;
	}

	public Double getYsk1() {
		return ysk1;
	}

	public void setYsk1(Double ysk1) {
		this.ysk1 = ysk1;
	}

	public Double getYsk2() {
		return ysk2;
	}

	public void setYsk2(Double ysk2) {
		this.ysk2 = ysk2;
	}

	public Double getSjsk1() {
		return sjsk1;
	}

	public void setSjsk1(Double sjsk1) {
		this.sjsk1 = sjsk1;
	}

	public Double getSjsk2() {
		return sjsk2;
	}

	public void setSjsk2(Double sjsk2) {
		this.sjsk2 = sjsk2;
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

	public Integer getSpzt() {
		return spzt;
	}

	public void setSpzt(Integer spzt) {
		this.spzt = spzt;
	}

	@Override
	public String toString() {
		return "ERP_Xshtsk_QueryVo [page=" + page + ", rows=" + rows + ", xsht=" + xsht + ", fpkj=" + fpkj + ", fplb="
				+ fplb + ", fpje1=" + fpje1 + ", fpje2=" + fpje2 + ", ysk1=" + ysk1 + ", ysk2=" + ysk2 + ", sjsk1="
				+ sjsk1 + ", sjsk2=" + sjsk2 + ", beginTime=" + beginTime + ", endTime=" + endTime + ", spzt=" + spzt
				+ "]";
	}

}
