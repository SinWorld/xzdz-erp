package com.edge.business.purchase.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 采购订单实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Purchase_Order {
	private Integer pur_Order_Id;// 主键
	private Integer userId;// 操作员
	private String pur_Code;// 订单编号
	private Integer supplier;// 供应商
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pur_Date;// 订购日期
	private String remarks;// 备注
	private String order_One;// 订单条款一
	private String order_Two;// 订单条款二
	private String order_Three;// 订单条款三
	private Integer our_uint;// 我方单位
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date qd_Date;// 签订日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date gfqd_Date;// 供方签订日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sub_Date;// 提交日期
	private String gf_user;// 供方签订人
	private String status;// 订单状态
	private Integer sales_Contract_Id;// 销售订单
	private String purchaseOrderName;// 采购订单名称
	private String sjr;// 收件人
	private String taskId;// 任务id
	private Boolean is_Availability;// 是否可用（领导审核通过后为可用）
	private Integer approvalDm;// 审批状态
	private String task_Describe;// 待办任务描述
	private Double totalPrice;// 合同总金额

	// 辅助属性
	private String supplierName;// 供应商名称
	private String uintName;// 我方单位名称
	private String sales_Contract_Name;// 销售订单名称
	private String userName;// 操作员名称
	private String dgrq;// 订购日期
	private String telPhone;// 供应商电话
	private String fjsx;// 附件属性

	public Integer getPur_Order_Id() {
		return pur_Order_Id;
	}

	public void setPur_Order_Id(Integer pur_Order_Id) {
		this.pur_Order_Id = pur_Order_Id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPur_Code() {
		return pur_Code;
	}

	public void setPur_Code(String pur_Code) {
		this.pur_Code = pur_Code;
	}

	public Integer getSupplier() {
		return supplier;
	}

	public void setSupplier(Integer supplier) {
		this.supplier = supplier;
	}

	public Date getPur_Date() {
		return pur_Date;
	}

	public void setPur_Date(Date pur_Date) {
		this.pur_Date = pur_Date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrder_One() {
		return order_One;
	}

	public void setOrder_One(String order_One) {
		this.order_One = order_One;
	}

	public String getOrder_Two() {
		return order_Two;
	}

	public void setOrder_Two(String order_Two) {
		this.order_Two = order_Two;
	}

	public String getOrder_Three() {
		return order_Three;
	}

	public void setOrder_Three(String order_Three) {
		this.order_Three = order_Three;
	}

	public Integer getOur_uint() {
		return our_uint;
	}

	public void setOur_uint(Integer our_uint) {
		this.our_uint = our_uint;
	}

	public Date getQd_Date() {
		return qd_Date;
	}

	public void setQd_Date(Date qd_Date) {
		this.qd_Date = qd_Date;
	}

	public Date getGfqd_Date() {
		return gfqd_Date;
	}

	public void setGfqd_Date(Date gfqd_Date) {
		this.gfqd_Date = gfqd_Date;
	}

	public Date getSub_Date() {
		return sub_Date;
	}

	public void setSub_Date(Date sub_Date) {
		this.sub_Date = sub_Date;
	}

	public String getGf_user() {
		return gf_user;
	}

	public void setGf_user(String gf_user) {
		this.gf_user = gf_user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSales_Contract_Id() {
		return sales_Contract_Id;
	}

	public void setSales_Contract_Id(Integer sales_Contract_Id) {
		this.sales_Contract_Id = sales_Contract_Id;
	}

	public String getPurchaseOrderName() {
		return purchaseOrderName;
	}

	public void setPurchaseOrderName(String purchaseOrderName) {
		this.purchaseOrderName = purchaseOrderName;
	}

	public String getSjr() {
		return sjr;
	}

	public void setSjr(String sjr) {
		this.sjr = sjr;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Boolean getIs_Availability() {
		return is_Availability;
	}

	public void setIs_Availability(Boolean is_Availability) {
		this.is_Availability = is_Availability;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getUintName() {
		return uintName;
	}

	public void setUintName(String uintName) {
		this.uintName = uintName;
	}

	public String getSales_Contract_Name() {
		return sales_Contract_Name;
	}

	public void setSales_Contract_Name(String sales_Contract_Name) {
		this.sales_Contract_Name = sales_Contract_Name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDgrq() {
		return dgrq;
	}

	public void setDgrq(String dgrq) {
		this.dgrq = dgrq;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Integer getApprovalDm() {
		return approvalDm;
	}

	public void setApprovalDm(Integer approvalDm) {
		this.approvalDm = approvalDm;
	}

	public String getTask_Describe() {
		return task_Describe;
	}

	public void setTask_Describe(String task_Describe) {
		this.task_Describe = task_Describe;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	@Override
	public String toString() {
		return "ERP_Purchase_Order [pur_Order_Id=" + pur_Order_Id + ", userId=" + userId + ", pur_Code=" + pur_Code
				+ ", supplier=" + supplier + ", pur_Date=" + pur_Date + ", remarks=" + remarks + ", order_One="
				+ order_One + ", order_Two=" + order_Two + ", order_Three=" + order_Three + ", our_uint=" + our_uint
				+ ", qd_Date=" + qd_Date + ", gfqd_Date=" + gfqd_Date + ", sub_Date=" + sub_Date + ", gf_user="
				+ gf_user + ", status=" + status + ", sales_Contract_Id=" + sales_Contract_Id + ", purchaseOrderName="
				+ purchaseOrderName + ", sjr=" + sjr + ", taskId=" + taskId + ", is_Availability=" + is_Availability
				+ ", approvalDm=" + approvalDm + ", task_Describe=" + task_Describe + ", totalPrice=" + totalPrice
				+ ", supplierName=" + supplierName + ", uintName=" + uintName + ", sales_Contract_Name="
				+ sales_Contract_Name + ", userName=" + userName + ", dgrq=" + dgrq + ", telPhone=" + telPhone
				+ ", fjsx=" + fjsx + "]";
	}

}
