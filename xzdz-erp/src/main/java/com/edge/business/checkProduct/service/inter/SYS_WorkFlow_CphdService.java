package com.edge.business.checkProduct.service.inter;

import java.util.List;

import com.edge.business.checkProduct.entity.SYS_WorkFlow_Cphd;

public interface SYS_WorkFlow_CphdService {

	// 新增成品核对记录
	public void saveCphd(SYS_WorkFlow_Cphd cphd);

	// 根据评审意见外键获得核对记录数据集合
	public List<SYS_WorkFlow_Cphd> cphds(Integer cphd_ObjId);

	// 根据评审意见外键删除成品核对记录
	public void deleteCphdsByPsyj(Integer cphd_ObjId);
}
