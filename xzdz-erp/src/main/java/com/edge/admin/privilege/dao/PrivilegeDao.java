package com.edge.admin.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.privilege.entity.Role_Privilege;

public interface PrivilegeDao {

	// 查询某个角色是否存在功能点
	public List<Role_Privilege> rolePrivilegeLists(@Param("roleId") Integer roleId);

	// 删除某个角色的所有功能权限
	public void deleteRolePrivilege(@Param("roleId") Integer roleId);

	// 新增功能权限
	public void saveRolePrivilege(Role_Privilege role_Privilege);

	// 加载顶级功能点
	public List<ERP_FunctionPoint> topFunctionPointList();

	// 加载当前功能点的所有子功能点
	public List<ERP_FunctionPoint> childrenFList(@Param("fp_Id") Integer fp_Id);
}
