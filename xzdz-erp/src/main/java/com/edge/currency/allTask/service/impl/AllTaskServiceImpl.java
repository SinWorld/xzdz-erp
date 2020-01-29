package com.edge.currency.allTask.service.impl;

/**
 * 我的已办业务逻辑层
 * @author NingCG
 *
 */

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.allTask.dao.AllTaskDao;
import com.edge.currency.allTask.entity.AllTask;
import com.edge.currency.allTask.entity.AllTask_QueryVo;
import com.edge.currency.allTask.service.inter.AllTaskService;

@Service
public class AllTaskServiceImpl implements AllTaskService {

	@Resource
	private AllTaskDao allTaskDao;

	// 加载已办列表
	public List<AllTask> allTaskList(AllTask_QueryVo vo) {
		return allTaskDao.allTaskList(vo);
	}

	// 加载已办列表数量
	public Integer allTaskCount(AllTask_QueryVo vo) {
		return allTaskDao.allTaskCount(vo);
	}

}
