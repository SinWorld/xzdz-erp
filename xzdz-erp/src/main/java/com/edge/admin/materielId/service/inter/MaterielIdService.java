package com.edge.admin.materielId.service.inter;

import java.util.List;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;

public interface MaterielIdService {
	// 分页查询物料Id
	public List<ERP_MaterielId> materielIdList(ERP_MaterielId_QueryVo vo);

	// 查询物料Id数量
	public Integer materielIdCount(ERP_MaterielId_QueryVo vo);

	// 物料Id不重复
	public ERP_MaterielId queryERP_MaterielIdByWLID(String materiel_Id, Integer materielNumber);

	// 新增物料Id
	public void saveMaterielId(ERP_MaterielId materielId);

	// 根据Id获得物料Id对象
	public ERP_MaterielId queryMaterielIdById(Integer row_Id);

	// 编辑操作
	public void editMaterielId(ERP_MaterielId materielId);

	// 删除操作
	public void deleteMaterelId(Integer row_Id);

	// 获取新增后的物料Id
	public Integer queryMaxMaterielId();
}
