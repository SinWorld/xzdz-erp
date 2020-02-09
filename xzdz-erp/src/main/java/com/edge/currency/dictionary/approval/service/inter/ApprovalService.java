package com.edge.currency.dictionary.approval.service.inter;

import com.alibaba.fastjson.JSONArray;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;

public interface ApprovalService {

	// 根据Id获得ERP_DM_Approval对象
	public ERP_DM_Approval queryApprovalById(Integer approvaldm);

	// ajax加载所有的审批状态
	public JSONArray allApproval();
}
