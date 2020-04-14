package com.edge.admin.permissionVerification.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.permissionVerification.dao.PermissionVerificationDao;
import com.edge.admin.permissionVerification.services.inter.PermissionVerificationService;

/**
 * 权限验证业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class PermissionVerificationServiceImpl implements PermissionVerificationService {

	@Resource
	private PermissionVerificationDao permissionVerificationDao;

	// 根据功能的url获取功能对象
	public ERP_FunctionPoint queryFunctionByUrl(String fp_Url) {
		return permissionVerificationDao.queryFunctionByUrl(fp_Url);
	}

	// 根据当前登录用户获取所属角色集合
	public List<Integer> userRoleList(Integer user_Id) {
		return permissionVerificationDao.userRoleList(user_Id);
	}

	// 查询当前功能的所属角色集合
	public List<Integer> fpRoleList(Integer fp_id) {
		return permissionVerificationDao.fpRoleList(fp_id);
	}

}
