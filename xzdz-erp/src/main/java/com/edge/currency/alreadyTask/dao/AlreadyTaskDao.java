package com.edge.currency.alreadyTask.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;

public interface AlreadyTaskDao {

	// 新增已办数据
	public void saveAlreadyTask(AlreadyTask alreayTask);

	// 加载当前用户的已办数据集
	public List<AlreadyTask> userAlreadyTask(AlreadyTask_QueryVo vo);

	// 加载当前用户的已办数据集数量
	public Integer userAlreadyTaskCount(AlreadyTask_QueryVo vo);

	// 根据Id获得已办对象
	public AlreadyTask queryAlreadyTaskById(@Param("ID_") Integer ID_);

}
