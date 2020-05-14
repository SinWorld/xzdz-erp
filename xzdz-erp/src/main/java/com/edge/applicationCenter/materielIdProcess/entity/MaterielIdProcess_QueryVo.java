package com.edge.applicationCenter.materielIdProcess.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class MaterielIdProcess_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String wlId;// 物料Id
	private String ggxh;// 规格型号
	private String bzq;// 保质期
	private Integer materielType;// 物料ID类型
	private Integer materielNumber;// 物料ID号
	private String wlIdms;// 物料ID描述

	private Double ckj1;// 参考价1
	private Double ckj2;// 参考价2
	private String beginTime;// 开始日期
	private String endTime;// 结束日期

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

	public Integer getMaterielType() {
		return materielType;
	}

	public void setMaterielType(Integer materielType) {
		this.materielType = materielType;
	}

	public Integer getMaterielNumber() {
		return materielNumber;
	}

	public void setMaterielNumber(Integer materielNumber) {
		this.materielNumber = materielNumber;
	}

	public String getWlIdms() {
		return wlIdms;
	}

	public void setWlIdms(String wlIdms) {
		this.wlIdms = wlIdms;
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
		return "MaterielIdProcess_QueryVo [page=" + page + ", rows=" + rows + ", wlId=" + wlId + ", ggxh=" + ggxh
				+ ", bzq=" + bzq + ", materielType=" + materielType + ", materielNumber=" + materielNumber + ", wlIdms="
				+ wlIdms + ", ckj1=" + ckj1 + ", ckj2=" + ckj2 + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ "]";
	}

}
