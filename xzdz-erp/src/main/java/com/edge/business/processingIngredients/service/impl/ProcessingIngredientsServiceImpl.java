package com.edge.business.processingIngredients.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.processingIngredients.dao.ProcessingIngredientsDao;
import com.edge.business.processingIngredients.service.inter.ProcessingIngredientsService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

/**
 * 加工配料业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ProcessingIngredientsServiceImpl implements ProcessingIngredientsService {

	@Resource
	private ProcessingIngredientsDao processingIngredientsDao;

	// 根据物料id获得材料集合
	public List<ERP_RAW_Material> queryMaterialByMaterielId(String materielId) {
		return processingIngredientsDao.queryMaterialByMaterielId(materielId);
	}

	// 根据材料集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(List<Integer> productIds) {
		return processingIngredientsDao.statsusList(productIds);
	}

}
