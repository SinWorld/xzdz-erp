package com.edge.currency.allTask.dao;

import java.util.List;

import com.edge.currency.allTask.entity.AllTask;
import com.edge.currency.allTask.entity.AllTask_QueryVo;

public interface AllTaskDao {
	// 加载已办列表
	public List<AllTask> allTaskList(AllTask_QueryVo vo);

	// 加载已办列表数量
	public Integer allTaskCount(AllTask_QueryVo vo);
}
