package com.edge.applicationCenter.materielIdProcess.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 物料ID评审实体类
 * 
 * @author NingCG
 *
 */
public class MaterielIdProcess {

	private Integer row_Id;// 主键
	private String materiel_Id;// 物料Id
	private String specification_Type;// 规格型号
	private String remarks;// 备注
	private String bzq;// 保质期
	private Double ckdj;// 参考单价
	private Integer materielType;// 物料id类型
	private Integer materielNumber;// 物料Id类型号
	private String task_describe;// 待办任务描述
	private Integer approvaldm;// 审批状态(1.完成、2.审批中、3.终止)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;// 创建时间
	private String fjsx;// 附件

	private String typeName;

	private String materielTypeName;
	private String materielNumberName;
	private String approvalmc;

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public String getMateriel_Id() {
		return materiel_Id;
	}

	public void setMateriel_Id(String materiel_Id) {
		this.materiel_Id = materiel_Id;
	}

	public String getSpecification_Type() {
		return specification_Type;
	}

	public void setSpecification_Type(String specification_Type) {
		this.specification_Type = specification_Type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBzq() {
		return bzq;
	}

	public void setBzq(String bzq) {
		this.bzq = bzq;
	}

	public Double getCkdj() {
		return ckdj;
	}

	public void setCkdj(Double ckdj) {
		this.ckdj = ckdj;
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

	public String getTask_describe() {
		return task_describe;
	}

	public void setTask_describe(String task_describe) {
		this.task_describe = task_describe;
	}

	public Integer getApprovaldm() {
		return approvaldm;
	}

	public void setApprovaldm(Integer approvaldm) {
		this.approvaldm = approvaldm;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMaterielTypeName() {
		return materielTypeName;
	}

	public void setMaterielTypeName(String materielTypeName) {
		this.materielTypeName = materielTypeName;
	}

	public String getMaterielNumberName() {
		return materielNumberName;
	}

	public void setMaterielNumberName(String materielNumberName) {
		this.materielNumberName = materielNumberName;
	}

	public String getApprovalmc() {
		return approvalmc;
	}

	public void setApprovalmc(String approvalmc) {
		this.approvalmc = approvalmc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "MaterielIdProcess [row_Id=" + row_Id + ", materiel_Id=" + materiel_Id + ", specification_Type="
				+ specification_Type + ", remarks=" + remarks + ", bzq=" + bzq + ", ckdj=" + ckdj + ", materielType="
				+ materielType + ", materielNumber=" + materielNumber + ", task_describe=" + task_describe
				+ ", approvaldm=" + approvaldm + ", createTime=" + createTime + ", fjsx=" + fjsx + ", typeName="
				+ typeName + ", materielTypeName=" + materielTypeName + ", materielNumberName=" + materielNumberName
				+ ", approvalmc=" + approvalmc + "]";
	}

}
