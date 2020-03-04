package com.edge.admin.customer.entity;

/**
 * 客户联系人实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Customer_Contacts {
	private Integer cus_Con_Id;// 主键
	private String cus_Con_Name;// 客户联系人名称
	private String cus_Con_Code;// 客户联系人编码
	private String cun_Con_Posstation;// 客户联系人岗位
	private String cun_Con_Post;// 客户联系人职务
	private String cun_Con_Dep;// 客户联系人部门
	private String cun_Con_Phone;// 客户联系人手机号
	private String cun_Con_Tel;// 客户联系人办公电话
	private String cun_Con_Email;// 客户联系人邮箱
	private String cun_Con_QQ;// 客户联系人QQ
	private String cun_Con_WeChat;// 客户联系人微信
	private String cun_Con_Remarks;// 备注
	private Integer customer;// 客户

	public Integer getCus_Con_Id() {
		return cus_Con_Id;
	}

	public void setCus_Con_Id(Integer cus_Con_Id) {
		this.cus_Con_Id = cus_Con_Id;
	}

	public String getCus_Con_Name() {
		return cus_Con_Name;
	}

	public void setCus_Con_Name(String cus_Con_Name) {
		this.cus_Con_Name = cus_Con_Name;
	}

	public String getCus_Con_Code() {
		return cus_Con_Code;
	}

	public void setCus_Con_Code(String cus_Con_Code) {
		this.cus_Con_Code = cus_Con_Code;
	}

	public String getCun_Con_Posstation() {
		return cun_Con_Posstation;
	}

	public void setCun_Con_Posstation(String cun_Con_Posstation) {
		this.cun_Con_Posstation = cun_Con_Posstation;
	}

	public String getCun_Con_Post() {
		return cun_Con_Post;
	}

	public void setCun_Con_Post(String cun_Con_Post) {
		this.cun_Con_Post = cun_Con_Post;
	}

	public String getCun_Con_Dep() {
		return cun_Con_Dep;
	}

	public void setCun_Con_Dep(String cun_Con_Dep) {
		this.cun_Con_Dep = cun_Con_Dep;
	}

	public String getCun_Con_Phone() {
		return cun_Con_Phone;
	}

	public void setCun_Con_Phone(String cun_Con_Phone) {
		this.cun_Con_Phone = cun_Con_Phone;
	}

	public String getCun_Con_Tel() {
		return cun_Con_Tel;
	}

	public void setCun_Con_Tel(String cun_Con_Tel) {
		this.cun_Con_Tel = cun_Con_Tel;
	}

	public String getCun_Con_Email() {
		return cun_Con_Email;
	}

	public void setCun_Con_Email(String cun_Con_Email) {
		this.cun_Con_Email = cun_Con_Email;
	}

	public String getCun_Con_QQ() {
		return cun_Con_QQ;
	}

	public void setCun_Con_QQ(String cun_Con_QQ) {
		this.cun_Con_QQ = cun_Con_QQ;
	}

	public String getCun_Con_WeChat() {
		return cun_Con_WeChat;
	}

	public void setCun_Con_WeChat(String cun_Con_WeChat) {
		this.cun_Con_WeChat = cun_Con_WeChat;
	}

	public String getCun_Con_Remarks() {
		return cun_Con_Remarks;
	}

	public void setCun_Con_Remarks(String cun_Con_Remarks) {
		this.cun_Con_Remarks = cun_Con_Remarks;
	}

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "ERP_Customer_Contacts [cus_Con_Id=" + cus_Con_Id + ", cus_Con_Name=" + cus_Con_Name + ", cus_Con_Code="
				+ cus_Con_Code + ", cun_Con_Posstation=" + cun_Con_Posstation + ", cun_Con_Post=" + cun_Con_Post
				+ ", cun_Con_Dep=" + cun_Con_Dep + ", cun_Con_Phone=" + cun_Con_Phone + ", cun_Con_Tel=" + cun_Con_Tel
				+ ", cun_Con_Email=" + cun_Con_Email + ", cun_Con_QQ=" + cun_Con_QQ + ", cun_Con_WeChat="
				+ cun_Con_WeChat + ", cun_Con_Remarks=" + cun_Con_Remarks + ", customer=" + customer + "]";
	}
}
