package com.edge.admin.materielId.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.materielId.dao.MaterielTypeDao;
import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.MaterielType;
import com.edge.admin.materielId.service.inter.MaterielTypeService;

/**
 * 物料Id类型业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterielTypeServiceImpl implements MaterielTypeService {

	@Resource
	private MaterielTypeDao materielTypeDao;

	// 查询顶级节点
	public List<MaterielType> topMaterielTypeTree() {
		return materielTypeDao.topMaterielTypeTree();
	}

	// 查询所有子节点
	public List<MaterielType> childrenMaterielTypeTree(Integer parent_id) {
		return materielTypeDao.childrenMaterielTypeTree(parent_id);
	}

	// 新增物料ID类型
	public void saveMaterielType(MaterielType materielType) {
		materielTypeDao.saveMaterielType(materielType);
	}

	// 编辑物料Id类型
	public void editMaterielType(MaterielType materielType) {
		materielTypeDao.editMaterielType(materielType);
	}

	// 根据Id获得物料Id类型
	public MaterielType queryMaterielTypeById(Integer id) {
		return materielTypeDao.queryMaterielTypeById(id);
	}

	// 查询所有物料Id类型
	public List<MaterielType> allMaterielType() {
		return materielTypeDao.allMaterielType();
	}

	// 删除物料ID类型
	public void deleteMaterielTypeById(Integer id) {
		materielTypeDao.deleteMaterielTypeById(id);
	}

	// ajax加载物料Id类型(加载根节点)
	public List<MaterielType> queryMaterielType() {
		return materielTypeDao.queryMaterielType();
	}

	// 新增检测物料Id类型名称不重复
	public MaterielType checkMaterielType(Integer parent_id, String title) {
		return materielTypeDao.checkMaterielType(parent_id, title);
	}

	// 点击物料ID类型展现对应的物料ID数据
	public List<ERP_MaterielId> queryWLIDByWLIDLX(Integer id) {
		return materielTypeDao.queryWLIDByWLIDLX(id);
	}

}
