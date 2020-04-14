package com.edge.admin.permissionVerification.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.Index.entity.ERP_FunctionPoint;

public interface PermissionVerificationDao {

	// 根据功能的url获取功能对象
	public ERP_FunctionPoint queryFunctionByUrl(@Param("fp_Url") String fp_Url);

	// 根据当前登录用户获取所属角色集合
	public List<Integer> userRoleList(@Param("user_Id") Integer user_Id);
	
	//查询当前功能的所属角色集合
	public List<Integer> fpRoleList(@Param("fp_id")Integer fp_id);
}
