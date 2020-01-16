package com.edge.admin.function.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.function.entity.Function_QueryVo;

public interface FunctionDao {
	// 功能点列表
	public List<ERP_FunctionPoint> functionList(Function_QueryVo vo);

	// 功能点列表数量
	public Integer functionCount(Function_QueryVo vo);
	
	//新增功能点
	public void saveFunction(ERP_FunctionPoint functionPoint);
	
	//根据Id获得功能点对象
	public ERP_FunctionPoint queryFunById(@Param("fp_Id")Integer fp_Id);
	
	//编辑功能点
	public void editFunction(ERP_FunctionPoint functionPoint);
	
	//加载所有功能点
	public List<ERP_FunctionPoint> allFunction();
}
