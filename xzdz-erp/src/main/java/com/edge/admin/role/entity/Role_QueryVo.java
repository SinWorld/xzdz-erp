package com.edge.admin.role.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Role_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String roleName;// 角色名称
	private String roleInfor;// 角色说明

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleInfor() {
		return roleInfor;
	}

	public void setRoleInfor(String roleInfor) {
		this.roleInfor = roleInfor;
	}

	@Override
	public String toString() {
		return "Role_QueryVo [page=" + page + ", rows=" + rows + ", roleName=" + roleName + ", roleInfor=" + roleInfor
				+ "]";
	}

}
