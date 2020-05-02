package com.edge.admin.materielId.service.inter;

import java.util.List;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.MaterielType;

public interface MaterielTypeService {

	// 查询顶级节点
	public List<MaterielType> topMaterielTypeTree();

	// 查询所有子节点
	public List<MaterielType> childrenMaterielTypeTree(Integer parent_id);

	// 新增物料ID类型
	public void saveMaterielType(MaterielType materielType);

	// 编辑物料Id类型
	public void editMaterielType(MaterielType materielType);

	// 根据Id获得物料Id类型
	public MaterielType queryMaterielTypeById(Integer id);

	// 查询所有物料Id类型
	public List<MaterielType> allMaterielType();

	// 删除物料ID类型
	public void deleteMaterielTypeById(Integer id);

	// ajax加载物料Id类型(加载根节点)
	public List<MaterielType> queryMaterielType();

	// 新增检测物料Id类型名称不重复
	public MaterielType checkMaterielType(Integer parent_id, String title);

	// 点击物料ID类型展现对应的物料ID数据
	public List<ERP_MaterielId> queryWLIDByWLIDLX(Integer id);
}
