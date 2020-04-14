package com.edge.admin.permissionVerification.services.inter;

import java.util.List;

import com.edge.admin.Index.entity.ERP_FunctionPoint;

public interface PermissionVerificationService {

	// 根据功能的url获取功能对象
	public ERP_FunctionPoint queryFunctionByUrl(String fp_Url);

	// 根据当前登录用户获取所属角色集合
	public List<Integer> userRoleList(Integer user_Id);

	// 查询当前功能的所属角色集合
	public List<Integer> fpRoleList(Integer fp_id);
}
