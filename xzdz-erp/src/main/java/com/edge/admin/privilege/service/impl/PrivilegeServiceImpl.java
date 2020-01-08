package com.edge.admin.privilege.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.privilege.dao.PrivilegeDao;
import com.edge.admin.privilege.entity.Role_Privilege;
import com.edge.admin.privilege.service.inter.PrivilegeService;

/**
 * 角色功能点业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Resource
	private PrivilegeDao privilegeDao;

	// 查询某个角色是否存在功能点
	public List<Role_Privilege> rolePrivilegeLists(Integer roleId) {
		return privilegeDao.rolePrivilegeLists(roleId);
	}

	// 删除某个角色的所有功能权限
	public void deleteRolePrivilege(Integer roleId) {
		privilegeDao.deleteRolePrivilege(roleId);
	}

	// 新增功能权限
	public void saveRolePrivilege(Role_Privilege role_Privilege) {
		privilegeDao.saveRolePrivilege(role_Privilege);
	}

}
