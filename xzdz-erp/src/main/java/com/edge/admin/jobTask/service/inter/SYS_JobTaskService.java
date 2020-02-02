package com.edge.admin.jobTask.service.inter;

import java.util.List;

import com.edge.admin.jobTask.entity.SYS_JobTask;

public interface SYS_JobTaskService {
	// 查询定时任务列表
	public List<SYS_JobTask> jobTaskList(Integer page, Integer rows);

	// 查询定时任务列表数量
	public Integer jobTaskListCount();

	// 新增定时任务
	public void saveJobTask(SYS_JobTask jobTask);

	// 根据Id查询定时任务对象
	public SYS_JobTask queryJobTaskById(Integer job_Task_Id_);

	// 编辑操作
	public void editJobTask(SYS_JobTask jobTask);

	// 删除操作
	public void deleteJobTask(Integer job_Task_Id_);
}
