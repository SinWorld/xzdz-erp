package com.edge.cghtfk.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 采购合同付款实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Cghtfk {

	private Integer cghtfk_Id;// 主键
	private Integer cght;// 采购合同
	private String fklx;// 付款类型
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sqrq;// 申请日期
	private String ysqk;// 预算情况
	private Double sqfkje;// 申请付款金额
	private String remarks;// 备注
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fkrq;// 付款日期
	private Integer approvalDm;// 审批状态
	private String fjsx;// 附件
	private String task_describe;// 待办任务描述
	private String fksm;// 付款说明

	// 辅助属性
	private String cghtmc;
	private String shenqrq;
	private String fukrq;
	private String approvalMc;

	public Integer getCghtfk_Id() {
		return cghtfk_Id;
	}

	public void setCghtfk_Id(Integer cghtfk_Id) {
		this.cghtfk_Id = cghtfk_Id;
	}

	public Integer getCght() {
		return cght;
	}

	public void setCght(Integer cght) {
		this.cght = cght;
	}

	public String getFklx() {
		return fklx;
	}

	public void setFklx(String fklx) {
		this.fklx = fklx;
	}

	public Date getSqrq() {
		return sqrq;
	}

	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}

	public String getYsqk() {
		return ysqk;
	}

	public void setYsqk(String ysqk) {
		this.ysqk = ysqk;
	}

	public Double getSqfkje() {
		return sqfkje;
	}

	public void setSqfkje(Double sqfkje) {
		this.sqfkje = sqfkje;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getFkrq() {
		return fkrq;
	}

	public void setFkrq(Date fkrq) {
		this.fkrq = fkrq;
	}

	public Integer getApprovalDm() {
		return approvalDm;
	}

	public void setApprovalDm(Integer approvalDm) {
		this.approvalDm = approvalDm;
	}

	public String getCghtmc() {
		return cghtmc;
	}

	public void setCghtmc(String cghtmc) {
		this.cghtmc = cghtmc;
	}

	public String getShenqrq() {
		return shenqrq;
	}

	public void setShenqrq(String shenqrq) {
		this.shenqrq = shenqrq;
	}

	public String getFukrq() {
		return fukrq;
	}

	public void setFukrq(String fukrq) {
		this.fukrq = fukrq;
	}

	public String getApprovalMc() {
		return approvalMc;
	}

	public void setApprovalMc(String approvalMc) {
		this.approvalMc = approvalMc;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	public String getTask_describe() {
		return task_describe;
	}

	public void setTask_describe(String task_describe) {
		this.task_describe = task_describe;
	}

	public String getFksm() {
		return fksm;
	}

	public void setFksm(String fksm) {
		this.fksm = fksm;
	}

	@Override
	public String toString() {
		return "ERP_Cghtfk [cghtfk_Id=" + cghtfk_Id + ", cght=" + cght + ", fklx=" + fklx + ", sqrq=" + sqrq + ", ysqk="
				+ ysqk + ", sqfkje=" + sqfkje + ", remarks=" + remarks + ", fkrq=" + fkrq + ", approvalDm=" + approvalDm
				+ ", fjsx=" + fjsx + ", task_describe=" + task_describe + ", fksm=" + fksm + ", cghtmc=" + cghtmc
				+ ", shenqrq=" + shenqrq + ", fukrq=" + fukrq + ", approvalMc=" + approvalMc + "]";
	}

}
