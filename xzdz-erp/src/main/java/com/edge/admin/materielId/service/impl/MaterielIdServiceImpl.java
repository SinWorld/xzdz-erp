package com.edge.admin.materielId.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.materielId.dao.MaterielIdDao;
import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.service.inter.MaterielIdService;

/**
 * 物料Id业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterielIdServiceImpl implements MaterielIdService {

	@Resource
	private MaterielIdDao materielIdDao;

	// 分页查询物料Id
	public List<ERP_MaterielId> materielIdList(ERP_MaterielId_QueryVo vo) {
		return materielIdDao.materielIdList(vo);
	}

	// 查询物料Id数量
	public Integer materielIdCount(ERP_MaterielId_QueryVo vo) {
		return materielIdDao.materielIdCount(vo);
	}

	// 物料Id不重复
	public ERP_MaterielId queryERP_MaterielIdByWLID(String materiel_Id, boolean type) {
		return materielIdDao.queryERP_MaterielIdByWLID(materiel_Id, type);
	}

	// 新增物料Id
	public void saveMaterielId(ERP_MaterielId materielId) {
		materielIdDao.saveMaterielId(materielId);
	}

	// 根据Id获得物料Id对象
	public ERP_MaterielId queryMaterielIdById(Integer row_Id) {
		return materielIdDao.queryMaterielIdById(row_Id);
	}

	// 编辑操作
	public void editMaterielId(ERP_MaterielId materielId) {
		materielIdDao.editMaterielId(materielId);
	}

	// 删除操作
	public void deleteMaterelId(Integer row_Id) {
		materielIdDao.deleteMaterelId(row_Id);
	}

	// 加载成品对应的物料Id
	public String product_MaterielId(String specification_Type) {
		return materielIdDao.product_MaterielId(specification_Type);
	}

	// 加载材料对应的物料Id
	public String materiel_MaterielId(String specification_Type) {
		return materielIdDao.materiel_MaterielId(specification_Type);
	}

}
