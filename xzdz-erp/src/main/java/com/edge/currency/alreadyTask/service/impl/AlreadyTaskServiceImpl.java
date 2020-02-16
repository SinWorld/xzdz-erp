package com.edge.currency.alreadyTask.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.alreadyTask.dao.AlreadyTaskDao;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;

/**
 * 已办业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class AlreadyTaskServiceImpl implements AlreadyTaskService {

	@Resource
	private AlreadyTaskDao alreadyTaskDao;

	public void saveAlreadyTask(AlreadyTask alreayTask) {
		alreadyTaskDao.saveAlreadyTask(alreayTask);
	}

	// 加载当前用户的已办数据集
	public List<AlreadyTask> userAlreadyTask(AlreadyTask_QueryVo vo) {
		return alreadyTaskDao.userAlreadyTask(vo);
	}

	// 加载当前用户的已办数据集数量
	public Integer userAlreadyTaskCount(AlreadyTask_QueryVo vo) {
		return alreadyTaskDao.userAlreadyTaskCount(vo);
	}

	// 根据Id获得已办对象
	public AlreadyTask queryAlreadyTaskById(Integer ID_) {
		return alreadyTaskDao.queryAlreadyTaskById(ID_);
	}

	// 放弃流程删除已办数据集
	public void deleteAlreadyTaskByProcessId(String PROC_INST_ID_) {
		alreadyTaskDao.deleteAlreadyTaskByProcessId(PROC_INST_ID_);
	}

}
