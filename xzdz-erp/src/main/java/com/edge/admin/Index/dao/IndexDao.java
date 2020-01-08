package com.edge.admin.Index.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.Index.entity.ERP_FunctionPoint;

public interface IndexDao {
	// 加载顶级菜单权限
	public List<ERP_FunctionPoint> topFunctionPointList();

	// 加载当前功能点的所有子功能点
	public List<ERP_FunctionPoint> childrenFList(@Param("fp_Id") Integer fp_Id);
}
