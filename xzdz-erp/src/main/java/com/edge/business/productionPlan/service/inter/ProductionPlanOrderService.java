package com.edge.business.productionPlan.service.inter;

import java.util.List;

import com.edge.business.productionPlan.entity.ProductionPlanOrder;

public interface ProductionPlanOrderService {
	// 新增生产计划成品项
	public void saveProductionPlanOrder(ProductionPlanOrder productionPlanOrder);

	// 根据生产计划获得生产计划成品项集合
	public List<ProductionPlanOrder> queryPlanOrderByPlanId(Integer productionPlanId);
}
