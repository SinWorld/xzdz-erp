package com.edge.admin.Index.service.inter;

import java.util.List;

import com.edge.admin.Index.entity.ERP_FunctionPoint;

public interface IndexService {
	// 加载顶级菜单权限
	public List<ERP_FunctionPoint> topFunctionPointList();

	// 加载当前功能点的所有子功能点
	public List<ERP_FunctionPoint> childrenFList(Integer fp_Id);
}
