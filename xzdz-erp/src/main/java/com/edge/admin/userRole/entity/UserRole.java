package com.edge.admin.userRole.entity;

/**
 * 用户角色映射实体类
 * 
 * @author NingCG
 *
 */
public class UserRole {
	private Integer user_role_Id;// 主键
	private Integer user_Id;// 用户主键
	private Integer role_Id;// 角色主键

	public Integer getUser_role_Id() {
		return user_role_Id;
	}

	public void setUser_role_Id(Integer user_role_Id) {
		this.user_role_Id = user_role_Id;
	}

	public Integer getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}

	public Integer getRole_Id() {
		return role_Id;
	}

	public void setRole_Id(Integer role_Id) {
		this.role_Id = role_Id;
	}

	@Override
	public String toString() {
		return "UserRole [user_role_Id=" + user_role_Id + ", user_Id=" + user_Id + ", role_Id=" + role_Id + "]";
	}

}
