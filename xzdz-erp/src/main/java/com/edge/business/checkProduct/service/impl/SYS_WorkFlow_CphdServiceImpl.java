package com.edge.business.checkProduct.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.checkProduct.dao.SYS_WorkFlow_CphdDao;
import com.edge.business.checkProduct.entity.SYS_WorkFlow_Cphd;
import com.edge.business.checkProduct.service.inter.SYS_WorkFlow_CphdService;

/**
 * 成品核对评审意见业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class SYS_WorkFlow_CphdServiceImpl implements SYS_WorkFlow_CphdService {

	@Resource
	private SYS_WorkFlow_CphdDao cphdDao;

	// 新增成品核对
	public void saveCphd(SYS_WorkFlow_Cphd cphd) {
		cphdDao.saveCphd(cphd);
	}

	// 根据评审意见外键获得核对记录数据集合
	public List<SYS_WorkFlow_Cphd> cphds(Integer cphd_ObjId) {
		return cphdDao.cphds(cphd_ObjId);
	}

	// 根据评审意见外键删除成品核对记录
	public void deleteCphdsByPsyj(Integer cphd_ObjId) {
		cphdDao.deleteCphdsByPsyj(cphd_ObjId);
	}

}
