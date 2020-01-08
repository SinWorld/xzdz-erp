package com.edge.admin.role.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.role.dao.ERP_RoleDao;
import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.role.entity.Role_QueryVo;
import com.edge.admin.role.service.inter.ERP_RoleService;

/**
 * 角色业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ERP_RoleServiceImpl implements ERP_RoleService {

	@Resource
	private ERP_RoleDao erp_RoleDao;

	// 分页显示角色列表
	public List<ERP_Roles> roleList(Role_QueryVo vo) {
		return erp_RoleDao.roleList(vo);
	}

	// 显示角色列表数量
	public Integer roleCount(Role_QueryVo vo) {
		return erp_RoleDao.roleCount(vo);
	}

	// 新增角色
	public void saveRole(ERP_Roles roles) {
		erp_RoleDao.saveRole(roles);
	}

	// 根据Id返回角色对象
	public ERP_Roles queryRoleById(Integer role_Id) {
		return erp_RoleDao.queryRoleById(role_Id);
	}

	// 修改角色
	public void editRole(ERP_Roles role) {
		erp_RoleDao.editRole(role);
	}

	// 删除角色
	public void deleteRole(ERP_Roles role) {
		erp_RoleDao.deleteRole(role);
	}

}
