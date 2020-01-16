package com.edge.admin.function.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.function.dao.FunctionDao;
import com.edge.admin.function.entity.Function_QueryVo;
import com.edge.admin.function.service.inter.FunctionService;

/**
 * 功能点业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class FunctionServiceImpl implements FunctionService {

	@Resource
	private FunctionDao functionDao;

	// 功能点列表
	public List<ERP_FunctionPoint> functionList(Function_QueryVo vo) {
		return functionDao.functionList(vo);
	}

	// 功能点列表数量
	public Integer functionCount(Function_QueryVo vo) {
		return functionDao.functionCount(vo);
	}

	// 新增功能点
	public void saveFunction(ERP_FunctionPoint functionPoint) {
		functionDao.saveFunction(functionPoint);
	}

	// 根据Id获得功能点对象
	public ERP_FunctionPoint queryFunById(Integer fp_Id) {
		return functionDao.queryFunById(fp_Id);
	}

	// 编辑功能点
	public void editFunction(ERP_FunctionPoint functionPoint) {
		functionDao.editFunction(functionPoint);
	}

	// 加载所有的功能点
	public List<ERP_FunctionPoint> allFunction() {
		return functionDao.allFunction();
	}

}
