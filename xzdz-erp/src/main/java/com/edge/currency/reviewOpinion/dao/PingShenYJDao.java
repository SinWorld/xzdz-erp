package com.edge.currency.reviewOpinion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;

public interface PingShenYJDao {
	// 新增评审意见
	public void savePingShenYJ(SYS_WorkFlow_PingShenYJ pingShenYJ);

	// 根据流程实例加载评审意见
	public List<SYS_WorkFlow_PingShenYJ> psyjList(@Param("PROC_INST_ID_") String PROC_INST_ID_);

	// 根据流程实例Id删除评审意见
	public void deletePsyjByProcinstId(@Param("PROC_INST_ID_") String PROC_INST_ID_);

	// 获得刚新增的评审记录主键
	public Integer psyjId();
}
