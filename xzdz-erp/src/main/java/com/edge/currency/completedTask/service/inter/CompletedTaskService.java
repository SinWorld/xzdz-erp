package com.edge.currency.completedTask.service.inter;

import java.util.List;

import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;

public interface CompletedTaskService {
	// 加载所有已完成数据集
	public List<AlreadyTask> CompletedTask(AlreadyTask_QueryVo vo);

	// 加载所有已完成数据集数量
	public Integer CompletedTaskCount();

	// 根据流程实例和状态查询开始数据
	public AlreadyTask queryBeginCompletedTask(String PROC_INST_ID_);
}
