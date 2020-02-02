package com.edge.currency.completedTask.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;
import com.edge.currency.completedTask.dao.CompletedTaskDao;
import com.edge.currency.completedTask.service.inter.CompletedTaskService;

/**
 * 已完成业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class CompletedTaskServiceImpl implements CompletedTaskService {

	@Resource
	private CompletedTaskDao completedTaskDao;

	// 加载所有已完成数据集
	public List<AlreadyTask> CompletedTask(AlreadyTask_QueryVo vo) {
		return completedTaskDao.CompletedTask(vo);
	}

	// 加载所有已完成数据集数量
	public Integer CompletedTaskCount() {
		return completedTaskDao.CompletedTaskCount();
	}

	// 根据流程实例和状态查询开始数据
	public AlreadyTask queryBeginCompletedTask(String PROC_INST_ID_) {
		return completedTaskDao.queryBeginCompletedTask(PROC_INST_ID_);
	}

}
