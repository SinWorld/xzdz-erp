package com.edge.currency.processView.service.inter;

import java.util.List;

import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.ibatis.annotations.Param;

import com.edge.currency.processView.entity.Sys_WorkFlow_Lcjs;

public interface WorkFlowLcjsService {

	// 新增流程检视
	public void saveLcjs(Sys_WorkFlow_Lcjs lcjs);

	// 编辑流程检视
	public void editLcjs(Sys_WorkFlow_Lcjs lcjs);

	// 根据ObjId展现流程检视数据
	public List<Sys_WorkFlow_Lcjs> lcjsList(String objId);

	// 获取下一任务节点办理人
	public TaskDefinition getNextTaskInfo(String processInstanceId);

	// 根据ObjId、节点名称、办理详情、结束时间获得流程检视对象
	public Sys_WorkFlow_Lcjs queryLcjsByInfor(String objId, String nodeName);
}
