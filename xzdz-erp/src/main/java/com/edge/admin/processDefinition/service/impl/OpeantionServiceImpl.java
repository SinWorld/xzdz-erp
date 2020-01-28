package com.edge.admin.processDefinition.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.processDefinition.dao.OpeantionDao;
import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;
import com.edge.admin.processDefinition.service.inter.OpeantionService;

/**
 * 流程权限操作业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class OpeantionServiceImpl implements OpeantionService {
	@Resource
	private OpeantionDao opeantionDao;

	// 新增流程操作数据
	public void saveWorkOperation(SYS_WorkFlow_Operation operation) {
		opeantionDao.saveWorkOperation(operation);
	}

	// 根据流程部署主键查询流程操作集合
	public List<SYS_WorkFlow_Operation> queryOperationByProId(String procdef_Id) {
		return opeantionDao.queryOperationByProId(procdef_Id);
	}

	// 编辑流程操作
	public void editWorkOperation(SYS_WorkFlow_Operation operation) {
		opeantionDao.editWorkOperation(operation);
	}

}
