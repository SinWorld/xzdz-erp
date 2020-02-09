package com.edge.currency.dictionary.approval.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.currency.dictionary.approval.dao.ApprovalDao;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;

/**
 * 流程审批状态业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Resource
	private ApprovalDao approvalDao;

	// 根据Id获得ERP_DM_Approval对象
	public ERP_DM_Approval queryApprovalById(Integer approvaldm) {
		return approvalDao.queryApprovalById(approvaldm);
	}

	// ajax加载所有的审批状态
	public JSONArray allApproval() {
		return approvalDao.allApproval();
	}

}
