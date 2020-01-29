package com.edge.currency.allTask.service.inter;

import java.util.List;

import com.edge.currency.allTask.entity.AllTask;
import com.edge.currency.allTask.entity.AllTask_QueryVo;

public interface AllTaskService {
	// 加载已办列表
	public List<AllTask> allTaskList(AllTask_QueryVo vo);

	// 加载已办列表数量
	public Integer allTaskCount(AllTask_QueryVo vo);
}
