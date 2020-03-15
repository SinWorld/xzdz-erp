package com.edge.checkDelivery.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 送货单核对实体类
 * 
 * @author NingCG
 *
 */
public class CheckDelivery {

	private Integer row_Id;// 主键
	private Integer delivery_Id;// 送货单主键
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;// 创建时间
	private String remarks;// 备注
	private Integer createUser;// 发起用户
	private String fksm;// 反馈说明
	private String task_Describe;// 待办任务描述
	private String fjsx;// 附件
	private Integer approvalDm;// 流程状态

	// 辅助属性
	private String xshtmc;// 销售合同名称

	private String approvalMc;
	private String startTime;

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public Integer getDelivery_Id() {
		return delivery_Id;
	}

	public void setDelivery_Id(Integer delivery_Id) {
		this.delivery_Id = delivery_Id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getFksm() {
		return fksm;
	}

	public void setFksm(String fksm) {
		this.fksm = fksm;
	}

	public String getTask_Describe() {
		return task_Describe;
	}

	public void setTask_Describe(String task_Describe) {
		this.task_Describe = task_Describe;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	public Integer getApprovalDm() {
		return approvalDm;
	}

	public void setApprovalDm(Integer approvalDm) {
		this.approvalDm = approvalDm;
	}

	public String getXshtmc() {
		return xshtmc;
	}

	public void setXshtmc(String xshtmc) {
		this.xshtmc = xshtmc;
	}

	public String getApprovalMc() {
		return approvalMc;
	}

	public void setApprovalMc(String approvalMc) {
		this.approvalMc = approvalMc;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "CheckDelivery [row_Id=" + row_Id + ", delivery_Id=" + delivery_Id + ", createTime=" + createTime
				+ ", remarks=" + remarks + ", createUser=" + createUser + ", fksm=" + fksm + ", task_Describe="
				+ task_Describe + ", fjsx=" + fjsx + ", approvalDm=" + approvalDm + ", xshtmc=" + xshtmc
				+ ", approvalMc=" + approvalMc + ", startTime=" + startTime + "]";
	}

}
