package com.edge.applicationCenter.materielId.service.inter;

import java.util.List;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;

public interface SalesMaterielIdService {
	// 分页查询物料Id
	public List<ERP_MaterielId> materielIdList(ERP_MaterielId_QueryVo vo);

	// 查询物料Id数量
	public Integer materielIdCount(ERP_MaterielId_QueryVo vo);
}
