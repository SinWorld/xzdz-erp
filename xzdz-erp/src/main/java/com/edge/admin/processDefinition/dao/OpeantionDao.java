package com.edge.admin.processDefinition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;

public interface OpeantionDao {
	// 新增流程操作数据
	public void saveWorkOperation(SYS_WorkFlow_Operation operation);

	// 根据流程部署主键查询流程操作集合
	public List<SYS_WorkFlow_Operation> queryOperationByProId(@Param("procdef_Id") String procdef_Id);
	
	//编辑流程操作
	public void editWorkOperation(SYS_WorkFlow_Operation operation);
}
