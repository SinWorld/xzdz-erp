package com.edge.index.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.Index.entity.ERP_FunctionPoint;

public interface QTIndexDao {
	// 用户登录到首页时根据用户主键查询当前用户的顶级权限
	public List<ERP_FunctionPoint> userPrivilegeList(@Param("userId") Integer userId);

	// 查询当前用户所有顶级权限下的二级子权限或三级权限
	public List<ERP_FunctionPoint> ejChildrenList(@Param("userId") Integer userId, @Param("fp_parentId") Integer fp_parentId);
}
