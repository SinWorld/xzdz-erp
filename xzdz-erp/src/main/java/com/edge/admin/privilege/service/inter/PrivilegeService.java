package com.edge.admin.privilege.service.inter;

import java.util.List;

import com.edge.admin.privilege.entity.Role_Privilege;

/**
 */
public interface PrivilegeService {

	// 查询某个角色是否存在功能点
	public List<Role_Privilege> rolePrivilegeLists(Integer roleId);

	// 删除某个角色的所有功能权限
	public void deleteRolePrivilege(Integer roleId);

	// 新增功能权限
	public void saveRolePrivilege(Role_Privilege role_Privilege);
}
