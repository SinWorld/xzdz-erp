package com.edge.currency.myTask.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.myTask.dao.MyTaskDao;
import com.edge.currency.myTask.entity.MyTask;
import com.edge.currency.myTask.entity.MyTask_QueryVo;
import com.edge.currency.myTask.service.inter.MyTaskService;

/**
 * 我的待办 业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MyTaskServiceImpl implements MyTaskService {

	@Resource
	private MyTaskDao myTaskDao;

	// 加载我的代办列表
	public List<MyTask> myTaskList(MyTask_QueryVo vo) {
		return myTaskDao.myTaskList(vo);
	}

	// 加载我的待办列表数量
	public Integer myTaskCount(MyTask_QueryVo vo) {
		return myTaskDao.myTaskCount(vo);
	}

}
