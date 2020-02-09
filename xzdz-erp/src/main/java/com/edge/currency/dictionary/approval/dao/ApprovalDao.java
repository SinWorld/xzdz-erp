package com.edge.currency.dictionary.approval.dao;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;

public interface ApprovalDao {

	// 根据Id获得ERP_DM_Approval对象
	public ERP_DM_Approval queryApprovalById(@Param("approvaldm") Integer approvaldm);

	// ajax加载所有的审批状态
	public JSONArray allApproval();
}
