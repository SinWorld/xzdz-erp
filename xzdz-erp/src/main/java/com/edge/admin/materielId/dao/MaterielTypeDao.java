package com.edge.admin.materielId.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.MaterielType;

public interface MaterielTypeDao {

	// 查询顶级节点
	public List<MaterielType> topMaterielTypeTree();

	// 查询所有子节点
	public List<MaterielType> childrenMaterielTypeTree(@Param("parent_id") Integer parent_id);

	// 新增物料ID类型
	public void saveMaterielType(MaterielType materielType);

	// 编辑物料Id类型
	public void editMaterielType(MaterielType materielType);

	// 根据Id获得物料Id类型
	public MaterielType queryMaterielTypeById(@Param("id") Integer id);

	// 查询所有物料Id类型
	public List<MaterielType> allMaterielType();

	// 删除物料ID类型
	public void deleteMaterielTypeById(@Param("id") Integer id);

	// ajax加载物料Id类型(加载根节点)
	public List<MaterielType> queryMaterielType();

	// 新增检测物料Id类型名称不重复
	public MaterielType checkMaterielType(@Param("parent_id") Integer parent_id, @Param("title") String title);

	// 点击物料ID类型展现对应的物料ID数据
	public List<ERP_MaterielId> queryWLIDByWLIDLX(@Param("id") Integer id);

}
