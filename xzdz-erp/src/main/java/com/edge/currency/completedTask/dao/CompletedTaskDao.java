package com.edge.currency.completedTask.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;

public interface CompletedTaskDao {
	// 加载所有已完成数据集
	public List<AlreadyTask> CompletedTask(AlreadyTask_QueryVo vo);

	// 加载所有已完成数据集数量
	public Integer CompletedTaskCount();

	// 根据流程实例和状态查询开始数据
	public AlreadyTask queryBeginCompletedTask(@Param("PROC_INST_ID_") String PROC_INST_ID_);
}
