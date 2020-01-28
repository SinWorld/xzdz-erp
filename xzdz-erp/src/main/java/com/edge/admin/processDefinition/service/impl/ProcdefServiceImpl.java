package com.edge.admin.processDefinition.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.processDefinition.dao.ProcdefDao;
import com.edge.admin.processDefinition.entity.ACT_RE_Procdef;
import com.edge.admin.processDefinition.entity.Procdef_QueryVo;
import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;
import com.edge.admin.processDefinition.service.inter.ProcdefService;

/**
 * 流程部署业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ProcdefServiceImpl implements ProcdefService {

	@Resource
	private ProcdefDao procdefDao;

	// 分页显示流程部署对象
	public List<ACT_RE_Procdef> procdefList(Procdef_QueryVo vo) {
		return procdefDao.procdefList(vo);
	}

	// 显示流程部署总数量
	public Integer procdefCount(Procdef_QueryVo vo) {
		return procdefDao.procdefCount(vo);
	}

}
