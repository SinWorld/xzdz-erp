package com.edge.admin.userRole.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.userRole.dao.UserRoleDao;
import com.edge.admin.userRole.entity.UserRole;
import com.edge.admin.userRole.service.inter.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Resource
	private UserRoleDao userRoleDao;

	// 查询当前用户的角色
	public List<UserRole> userRoleList(Integer userId) {
		return userRoleDao.userRoleList(userId);
	}

	// 删除该用户的所有角色(物理删除)
	public void deleteUserRole(Integer userId) {
		userRoleDao.deleteUserRole(userId);
	}

	// 新增用户角色
	public void saveUserRole(UserRole userRole) {
		userRoleDao.saveUserRole(userRole);
	}

	// 查询所有的角色
	public List<ERP_Roles> roleList() {
		return userRoleDao.roleList();
	}

}
