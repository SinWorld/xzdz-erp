package com.edge.admin.jobTask.service.impl;

/**
 * 定时任务业务逻辑层
 * @author NingCG
 *
 */

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.jobTask.dao.SYS_JobTaskDao;
import com.edge.admin.jobTask.entity.SYS_JobTask;
import com.edge.admin.jobTask.service.inter.SYS_JobTaskService;

@Service
public class SYS_JobTaskServiceImpl implements SYS_JobTaskService {

	@Resource
	private SYS_JobTaskDao jobTaskDao;

	// 查询定时任务列表
	public List<SYS_JobTask> jobTaskList(Integer page, Integer rows) {
		return jobTaskDao.jobTaskList(page, rows);
	}

	// 查询定时任务列表数量
	public Integer jobTaskListCount() {
		return jobTaskDao.jobTaskListCount();
	}

	// 新增定时任务
	public void saveJobTask(SYS_JobTask jobTask) {
		jobTaskDao.saveJobTask(jobTask);
	}

	// 根据Id查询定时任务对象
	public SYS_JobTask queryJobTaskById(Integer job_Task_Id_) {
		return jobTaskDao.queryJobTaskById(job_Task_Id_);
	}

	// 编辑操作
	public void editJobTask(SYS_JobTask jobTask) {
		jobTaskDao.editJobTask(jobTask);
	}

	// 删除操作
	public void deleteJobTask(Integer job_Task_Id_) {
		jobTaskDao.deleteJobTask(job_Task_Id_);
	}

	// 根据定时任务文件名称获取定时任务对象
	public SYS_JobTask queryJobByClassName(String job_Task_Class_Name_) {
		return jobTaskDao.queryJobByClassName(job_Task_Class_Name_);
	}

}
