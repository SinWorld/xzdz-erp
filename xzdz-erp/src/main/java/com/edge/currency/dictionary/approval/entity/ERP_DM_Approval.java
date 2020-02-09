package com.edge.currency.dictionary.approval.entity;

/**
 * 流程审批状态实体类
 * 
 * @author NingCG
 *
 */
public class ERP_DM_Approval {
	private Integer approvaldm;// 主键
	private String approvalmc;// 名称
	private String approvaldh;// 代号

	public Integer getApprovaldm() {
		return approvaldm;
	}

	public void setApprovaldm(Integer approvaldm) {
		this.approvaldm = approvaldm;
	}

	public String getApprovalmc() {
		return approvalmc;
	}

	public void setApprovalmc(String approvalmc) {
		this.approvalmc = approvalmc;
	}

	public String getApprovaldh() {
		return approvaldh;
	}

	public void setApprovaldh(String approvaldh) {
		this.approvaldh = approvaldh;
	}

	@Override
	public String toString() {
		return "ERP_DM_Approval [approvaldm=" + approvaldm + ", approvalmc=" + approvalmc + ", approvaldh=" + approvaldh
				+ "]";
	}

}
