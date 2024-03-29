package com.edge.admin.customer.entity;

/**
 * 客户实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Customer {
	private Integer customer_Id;// 主键
	private String unit_Code;// 单位编码
	private String unit_Name;// 单位名称
	private String registered_Address;// 单位注册地址
	private String office_Address;// 办公地址
	private String unified_Code;// 社会统一信用代码
	private String legal_person;// 法定代表人
	private String opening_Bank;// 开户行
	private String account_Number;// 账号
	private String duty_Paragraph;// 税号
	private String telPhone;// 电话
	private String fax;// 传真
	private String remarks;// 备注
	private String wtdlr;// 委托代理人
	private String objDm;// 功能Obj代码
	private String fjsx;// 附件

	public Integer getCustomer_Id() {
		return customer_Id;
	}

	public void setCustomer_Id(Integer customer_Id) {
		this.customer_Id = customer_Id;
	}

	public String getUnit_Code() {
		return unit_Code;
	}

	public void setUnit_Code(String unit_Code) {
		this.unit_Code = unit_Code;
	}

	public String getUnit_Name() {
		return unit_Name;
	}

	public void setUnit_Name(String unit_Name) {
		this.unit_Name = unit_Name;
	}

	public String getRegistered_Address() {
		return registered_Address;
	}

	public void setRegistered_Address(String registered_Address) {
		this.registered_Address = registered_Address;
	}

	public String getOffice_Address() {
		return office_Address;
	}

	public void setOffice_Address(String office_Address) {
		this.office_Address = office_Address;
	}

	public String getUnified_Code() {
		return unified_Code;
	}

	public void setUnified_Code(String unified_Code) {
		this.unified_Code = unified_Code;
	}

	public String getLegal_person() {
		return legal_person;
	}

	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}

	public String getOpening_Bank() {
		return opening_Bank;
	}

	public void setOpening_Bank(String opening_Bank) {
		this.opening_Bank = opening_Bank;
	}

	public String getAccount_Number() {
		return account_Number;
	}

	public void setAccount_Number(String account_Number) {
		this.account_Number = account_Number;
	}

	public String getDuty_Paragraph() {
		return duty_Paragraph;
	}

	public void setDuty_Paragraph(String duty_Paragraph) {
		this.duty_Paragraph = duty_Paragraph;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getWtdlr() {
		return wtdlr;
	}

	public void setWtdlr(String wtdlr) {
		this.wtdlr = wtdlr;
	}

	public String getObjDm() {
		return objDm;
	}

	public void setObjDm(String objDm) {
		this.objDm = objDm;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	@Override
	public String toString() {
		return "ERP_Customer [customer_Id=" + customer_Id + ", unit_Code=" + unit_Code + ", unit_Name=" + unit_Name
				+ ", registered_Address=" + registered_Address + ", office_Address=" + office_Address
				+ ", unified_Code=" + unified_Code + ", legal_person=" + legal_person + ", opening_Bank=" + opening_Bank
				+ ", account_Number=" + account_Number + ", duty_Paragraph=" + duty_Paragraph + ", telPhone=" + telPhone
				+ ", fax=" + fax + ", remarks=" + remarks + ", wtdlr=" + wtdlr + ", objDm=" + objDm + ", fjsx=" + fjsx
				+ "]";
	}

}
