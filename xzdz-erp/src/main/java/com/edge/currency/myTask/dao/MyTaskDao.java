package com.edge.currency.myTask.dao;

import java.util.List;

import com.edge.currency.myTask.entity.MyTask;
import com.edge.currency.myTask.entity.MyTask_QueryVo;

public interface MyTaskDao {
	// 加载我的代办列表
	public List<MyTask> myTaskList(MyTask_QueryVo vo);

	// 加载我的待办列表数量
	public Integer myTaskCount(MyTask_QueryVo vo);
}
