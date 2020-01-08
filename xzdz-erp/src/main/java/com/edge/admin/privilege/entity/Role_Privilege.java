package com.edge.admin.privilege.entity;

/**
 * 角色功能映射实体类
 * 
 * @author NingCG
 *
 */
public class Role_Privilege {
	private Integer role_fp_Id;// 主键
	private Integer role_Id;// 角色主键
	private Integer fp_Id;// 功能点主键

	public Integer getRole_fp_Id() {
		return role_fp_Id;
	}

	public void setRole_fp_Id(Integer role_fp_Id) {
		this.role_fp_Id = role_fp_Id;
	}

	public Integer getRole_Id() {
		return role_Id;
	}

	public void setRole_Id(Integer role_Id) {
		this.role_Id = role_Id;
	}

	public Integer getFp_Id() {
		return fp_Id;
	}

	public void setFp_Id(Integer fp_Id) {
		this.fp_Id = fp_Id;
	}

	@Override
	public String toString() {
		return "Role_Privilege [role_fp_Id=" + role_fp_Id + ", role_Id=" + role_Id + ", fp_Id=" + fp_Id + "]";
	}

}
