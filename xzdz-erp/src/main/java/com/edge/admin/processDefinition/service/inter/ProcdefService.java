package com.edge.admin.processDefinition.service.inter;

import java.util.List;

import com.edge.admin.processDefinition.entity.ACT_RE_Procdef;
import com.edge.admin.processDefinition.entity.Procdef_QueryVo;
import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;

public interface ProcdefService {

	// 分页显示流程部署列表
	public List<ACT_RE_Procdef> procdefList(Procdef_QueryVo vo);

	// 显示流程部署总数量
	public Integer procdefCount(Procdef_QueryVo vo);

}
