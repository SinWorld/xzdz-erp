package com.edge.admin.function.service.inter;

import java.util.List;

import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.function.entity.Function_QueryVo;

public interface FunctionService {
	// 功能点列表
	public List<ERP_FunctionPoint> functionList(Function_QueryVo vo);

	// 功能点列表数量
	public Integer functionCount(Function_QueryVo vo);

	// 新增功能点
	public void saveFunction(ERP_FunctionPoint functionPoint);

	// 根据Id获得功能点对象
	public ERP_FunctionPoint queryFunById(Integer fp_Id);

	// 编辑功能点
	public void editFunction(ERP_FunctionPoint functionPoint);

	// 加载所有功能点
	public List<ERP_FunctionPoint> allFunction();
}
