package com.edge.currency.reviewOpinion.service.inter;

import java.util.List;

import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;

public interface PingShenYJService {
	// 新增评审意见
	public void savePingShenYJ(SYS_WorkFlow_PingShenYJ pingShenYJ);

	// 根据流程实例加载评审意见
	public List<SYS_WorkFlow_PingShenYJ> psyjList(String PROC_INST_ID_);

	// 根据流程实例Id删除评审意见
	public void deletePsyjByProcinstId(String PROC_INST_ID_);

	// 获得刚新增的评审记录主键
	public Integer psyjId();
}
