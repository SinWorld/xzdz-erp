package com.edge.applicationCenter.materielId.dao;

import java.util.List;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;

public interface SalesMaterielIdDao {
	// 分页查询物料Id
	public List<ERP_MaterielId> materielIdList(ERP_MaterielId_QueryVo vo);

	// 查询物料Id数量
	public Integer materielIdCount(ERP_MaterielId_QueryVo vo);

}
