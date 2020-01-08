package com.edge.admin.userRole.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.userRole.entity.UserRole;

public interface UserRoleDao {
	
	// 根据用户主键查询该用户有无角色
	public List<UserRole> userRoleList(@Param("userId") Integer userId);

	// 删除该用户的所有角色(物理删除)
	public void deleteUserRole(@Param("userId") Integer userId);

	// 新增用户的角色
	public void saveUserRole(UserRole userRole);

	// 查询所有角色
	public List<ERP_Roles> roleList();
}
