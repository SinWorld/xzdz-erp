package com.edge.admin.jobTask.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.jobTask.entity.SYS_JobTask;

public interface SYS_JobTaskDao {
	// 查询定时任务列表
	public List<SYS_JobTask> jobTaskList(@Param("page") Integer page, @Param("rows") Integer rows);

	// 查询定时任务列表数量
	public Integer jobTaskListCount();

	// 新增定时任务
	public void saveJobTask(SYS_JobTask jobTask);

	// 根据Id查询定时任务对象
	public SYS_JobTask queryJobTaskById(@Param("job_Task_Id_") Integer job_Task_Id_);

	// 编辑操作
	public void editJobTask(SYS_JobTask jobTask);

	// 删除操作
	public void deleteJobTask(@Param("job_Task_Id_") Integer job_Task_Id_);

	// 根据定时任务文件名称获取定时任务对象
	public SYS_JobTask queryJobByClassName(@Param("job_Task_Class_Name_") String job_Task_Class_Name_);
}
