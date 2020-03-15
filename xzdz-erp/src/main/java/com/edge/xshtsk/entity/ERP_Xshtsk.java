package com.edge.xshtsk.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 销售合同收款实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Xshtsk {

	private Integer xshtskdm;// 主键
	private Integer xsht;// 销售合同
	private Boolean is_fpkj;// 是否发票开具
	private Double fpje;// 发票金额
	private Double ysk;// 应收款
	private Double sjsk;// 实际收款
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date kprq;// 开票日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sqkprq;// 申请开票日期
	private String remarks;// 备注
	private String fjsx;// 附件
	private Double kpje;// 开票金额
	private String task_describe;// 待办任务描述
	private Boolean is_fplb;// 发票类别(增值税专用发票、增值税普通发票)
	private String sl;// 税率
	private Integer approvaldm;// 审批状态(1.完成、2.审批中、3.终止)

	// 辅助属性
	private String xshtName;// 销售合同名称
	private String shenqkprq;
	private String kaiprq;
	private String sfkpkj;
	private String fplb;
	private String approvalmc;

	public Integer getXshtskdm() {
		return xshtskdm;
	}

	public void setXshtskdm(Integer xshtskdm) {
		this.xshtskdm = xshtskdm;
	}

	public Integer getXsht() {
		return xsht;
	}

	public void setXsht(Integer xsht) {
		this.xsht = xsht;
	}

	public Boolean getIs_fpkj() {
		return is_fpkj;
	}

	public void setIs_fpkj(Boolean is_fpkj) {
		this.is_fpkj = is_fpkj;
	}

	public Double getFpje() {
		return fpje;
	}

	public void setFpje(Double fpje) {
		this.fpje = fpje;
	}

	public Double getYsk() {
		return ysk;
	}

	public void setYsk(Double ysk) {
		this.ysk = ysk;
	}

	public Double getSjsk() {
		return sjsk;
	}

	public void setSjsk(Double sjsk) {
		this.sjsk = sjsk;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getSqkprq() {
		return sqkprq;
	}

	public void setSqkprq(Date sqkprq) {
		this.sqkprq = sqkprq;
	}

	public Date getKprq() {
		return kprq;
	}

	public void setKprq(Date kprq) {
		this.kprq = kprq;
	}

	public String getXshtName() {
		return xshtName;
	}

	public void setXshtName(String xshtName) {
		this.xshtName = xshtName;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	public Double getKpje() {
		return kpje;
	}

	public void setKpje(Double kpje) {
		this.kpje = kpje;
	}

	public String getTask_describe() {
		return task_describe;
	}

	public void setTask_describe(String task_describe) {
		this.task_describe = task_describe;
	}

	public String getShenqkprq() {
		return shenqkprq;
	}

	public void setShenqkprq(String shenqkprq) {
		this.shenqkprq = shenqkprq;
	}

	public String getKaiprq() {
		return kaiprq;
	}

	public void setKaiprq(String kaiprq) {
		this.kaiprq = kaiprq;
	}

	public Boolean getIs_fplb() {
		return is_fplb;
	}

	public void setIs_fplb(Boolean is_fplb) {
		this.is_fplb = is_fplb;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public Integer getApprovaldm() {
		return approvaldm;
	}

	public void setApprovaldm(Integer approvaldm) {
		this.approvaldm = approvaldm;
	}

	public String getSfkpkj() {
		return sfkpkj;
	}

	public void setSfkpkj(String sfkpkj) {
		this.sfkpkj = sfkpkj;
	}

	public String getFplb() {
		return fplb;
	}

	public void setFplb(String fplb) {
		this.fplb = fplb;
	}

	public String getApprovalmc() {
		return approvalmc;
	}

	public void setApprovalmc(String approvalmc) {
		this.approvalmc = approvalmc;
	}

	@Override
	public String toString() {
		return "ERP_Xshtsk [xshtskdm=" + xshtskdm + ", xsht=" + xsht + ", is_fpkj=" + is_fpkj + ", fpje=" + fpje
				+ ", ysk=" + ysk + ", sjsk=" + sjsk + ", kprq=" + kprq + ", sqkprq=" + sqkprq + ", remarks=" + remarks
				+ ", fjsx=" + fjsx + ", kpje=" + kpje + ", task_describe=" + task_describe + ", is_fplb=" + is_fplb
				+ ", sl=" + sl + ", approvaldm=" + approvaldm + ", xshtName=" + xshtName + ", shenqkprq=" + shenqkprq
				+ ", kaiprq=" + kaiprq + ", sfkpkj=" + sfkpkj + ", fplb=" + fplb + ", approvalmc=" + approvalmc + "]";
	}

}
