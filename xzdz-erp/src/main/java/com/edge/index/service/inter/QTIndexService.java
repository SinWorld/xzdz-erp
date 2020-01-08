package com.edge.index.service.inter;

import java.util.List;

import com.edge.admin.Index.entity.ERP_FunctionPoint;

public interface QTIndexService {
	// 用户登录到首页时根据用户主键查询当前用户的顶级权限
	public List<ERP_FunctionPoint> userPrivilegeList(Integer userId);

	// 查询当前用户所有顶级权限下的二级子权限或三级权限
	public List<ERP_FunctionPoint> ejChildrenList(Integer userId, Integer fp_parentId);
}
