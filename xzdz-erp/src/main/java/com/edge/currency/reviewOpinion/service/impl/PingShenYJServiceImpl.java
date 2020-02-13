package com.edge.currency.reviewOpinion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.currency.reviewOpinion.dao.PingShenYJDao;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 评审意见业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class PingShenYJServiceImpl implements PingShenYJService {
	@Resource
	private PingShenYJDao pingShenYJDao;

	// 新增评审意见
	public void savePingShenYJ(SYS_WorkFlow_PingShenYJ pingShenYJ) {
		pingShenYJDao.savePingShenYJ(pingShenYJ);
	}

	// 根据流程实例加载评审意见
	public List<SYS_WorkFlow_PingShenYJ> psyjList(String PROC_INST_ID_) {
		return pingShenYJDao.psyjList(PROC_INST_ID_);
	}

	// 根据流程实例Id删除评审意见
	public void deletePsyjByProcinstId(String PROC_INST_ID_) {
		pingShenYJDao.deletePsyjByProcinstId(PROC_INST_ID_);
	}

	// 获得刚新增的评审记录主键
	public Integer psyjId() {
		return pingShenYJDao.psyjId();
	}

}
