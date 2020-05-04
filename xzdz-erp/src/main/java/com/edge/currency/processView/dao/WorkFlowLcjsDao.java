package com.edge.currency.processView.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.currency.processView.entity.Sys_WorkFlow_Lcjs;

public interface WorkFlowLcjsDao {

	// 新增流程检视
	public void saveLcjs(Sys_WorkFlow_Lcjs lcjs);

	// 编辑流程检视
	public void editLcjs(Sys_WorkFlow_Lcjs lcjs);

	// 根据ObjId展现流程检视数据
	public List<Sys_WorkFlow_Lcjs> lcjsList(String objId);

	// 根据ObjId、节点名称、办理详情、结束时间获得流程检视对象
	public Sys_WorkFlow_Lcjs queryLcjsByInfor(@Param("objId") String objId, @Param("nodeName") String nodeName);
}
