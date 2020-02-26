package com.edge.admin.supplier.entity;

/**
 * 供应商实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Supplier {
	private Integer supplier_Id;// 主键
	private String supplier_Code;// 供应商编码
	private String supplier_Name;// 供应商名称
	private String registered_Address;// 注册地址
	private String office_Address;// 办公地址
	private String unified_Code;// 社会统一信用代码
	private String legal_person;// 法定代表人
	private String opening_Bank;// 开户行
	private String account_Number;// 账号
	private String duty_Paragraph;// 税号
	private String phone;// 电话
	private String fax;// 传真
	private String contacts;// 联系人
	private String productInfor;// 主营产品
	private String remarks;// 备注

	public Integer getSupplier_Id() {
		return supplier_Id;
	}

	public void setSupplier_Id(Integer supplier_Id) {
		this.supplier_Id = supplier_Id;
	}

	public String getSupplier_Code() {
		return supplier_Code;
	}

	public void setSupplier_Code(String supplier_Code) {
		this.supplier_Code = supplier_Code;
	}

	public String getSupplier_Name() {
		return supplier_Name;
	}

	public void setSupplier_Name(String supplier_Name) {
		this.supplier_Name = supplier_Name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getProductInfor() {
		return productInfor;
	}

	public void setProductInfor(String productInfor) {
		this.productInfor = productInfor;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ERP_Supplier [supplier_Id=" + supplier_Id + ", supplier_Code=" + supplier_Code + ", supplier_Name="
				+ supplier_Name + ", registered_Address=" + registered_Address + ", office_Address=" + office_Address
				+ ", unified_Code=" + unified_Code + ", legal_person=" + legal_person + ", opening_Bank=" + opening_Bank
				+ ", account_Number=" + account_Number + ", duty_Paragraph=" + duty_Paragraph + ", phone=" + phone
				+ ", fax=" + fax + ", contacts=" + contacts + ", productInfor=" + productInfor + ", remarks=" + remarks
				+ "]";
	}

}
