package com.edge.currency.alreadyTask.service.inter;

import java.util.List;

import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;

public interface AlreadyTaskService {
	// 新增已办数据
	public void saveAlreadyTask(AlreadyTask alreayTask);

	// 加载当前用户的已办数据集
	public List<AlreadyTask> userAlreadyTask(AlreadyTask_QueryVo vo);

	// 加载当前用户的已办数据集数量
	public Integer userAlreadyTaskCount(AlreadyTask_QueryVo vo);

	// 根据Id获得已办对象
	public AlreadyTask queryAlreadyTaskById(Integer ID_);

	// 放弃流程删除已办数据集
	public void deleteAlreadyTaskByProcessId(String PROC_INST_ID_);
}
