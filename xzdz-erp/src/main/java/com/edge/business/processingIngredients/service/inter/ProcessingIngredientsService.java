package com.edge.business.processingIngredients.service.inter;

import java.util.List;

import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

public interface ProcessingIngredientsService {

	// 根据物料id获得材料集合
	public List<ERP_RAW_Material> queryMaterialByMaterielId(String materielId);

	// 根据材料集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(List<Integer> productIds);
}
