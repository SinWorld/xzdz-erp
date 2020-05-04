package com.edge.applicationCenter.materielId.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.applicationCenter.materielId.dao.SalesMaterielIdDao;
import com.edge.applicationCenter.materielId.service.inter.SalesMaterielIdService;

/**
 * 物料Id业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class SalesMaterielIdServiceImpl implements SalesMaterielIdService {

	@Resource
	private SalesMaterielIdDao salesMaterielIdDao;

	public List<ERP_MaterielId> materielIdList(ERP_MaterielId_QueryVo vo) {
		return salesMaterielIdDao.materielIdList(vo);
	}

	public Integer materielIdCount(ERP_MaterielId_QueryVo vo) {
		return salesMaterielIdDao.materielIdCount(vo);
	}

}
