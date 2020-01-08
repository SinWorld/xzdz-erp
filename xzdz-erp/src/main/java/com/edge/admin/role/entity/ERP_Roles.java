package com.edge.admin.role.entity;

/**
 * 角色实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Roles {
	private Integer role_Id;// 角色
	private String role_Name;// 角色名称
	private String role_Infor;// 角色说明
	private Boolean flag;// 是否删除

	public Integer getRole_Id() {
		return role_Id;
	}

	public void setRole_Id(Integer role_Id) {
		this.role_Id = role_Id;
	}

	public String getRole_Name() {
		return role_Name;
	}

	public void setRole_Name(String role_Name) {
		this.role_Name = role_Name;
	}

	public String getRole_Infor() {
		return role_Infor;
	}

	public void setRole_Infor(String role_Infor) {
		this.role_Infor = role_Infor;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "ERP_Roles [role_Id=" + role_Id + ", role_Name=" + role_Name + ", role_Infor=" + role_Infor + ", flag="
				+ flag + "]";
	}

}
