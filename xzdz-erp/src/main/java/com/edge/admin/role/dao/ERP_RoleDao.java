package com.edge.admin.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.role.entity.Role_QueryVo;

public interface ERP_RoleDao {
	// 分页显示角色列表
	public List<ERP_Roles> roleList(Role_QueryVo vo);

	// 显示角色列表数量
	public Integer roleCount(Role_QueryVo vo);

	// 新增角色
	public void saveRole(ERP_Roles role);
	
	//根据Id返回角色对象
	public ERP_Roles queryRoleById(@Param("role_Id")Integer role_Id);
	
	//修改角色
	public void editRole(ERP_Roles role);
	
	//删除角色(逻辑删除)
	public void deleteRole(ERP_Roles role);
}
