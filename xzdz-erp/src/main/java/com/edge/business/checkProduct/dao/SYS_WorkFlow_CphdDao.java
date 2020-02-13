package com.edge.business.checkProduct.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.checkProduct.entity.SYS_WorkFlow_Cphd;

public interface SYS_WorkFlow_CphdDao {

	// 新增成品核对记录
	public void saveCphd(SYS_WorkFlow_Cphd cphd);

	// 根据评审意见外键获得核对记录数据集合
	public List<SYS_WorkFlow_Cphd> cphds(@Param("cphd_ObjId") Integer cphd_ObjId);

}
