package com.edge.business.processingIngredients.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

public interface ProcessingIngredientsDao {

	// 根据物料id获得材料集合
	public List<ERP_RAW_Material> queryMaterialByMaterielId(@Param("materielId") String materielId);

	// 根据材料集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(@Param("productIds") List<Integer> productIds);
}
