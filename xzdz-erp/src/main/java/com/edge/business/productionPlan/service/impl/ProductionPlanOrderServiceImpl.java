package com.edge.business.productionPlan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.productionPlan.dao.ProductionPlanOrderDao;
import com.edge.business.productionPlan.entity.ProductionPlanOrder;
import com.edge.business.productionPlan.service.inter.ProductionPlanOrderService;

/**
 * 生产计划货物项业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ProductionPlanOrderServiceImpl implements ProductionPlanOrderService {

	@Resource
	private ProductionPlanOrderDao productionPlanOrderDao;

	// 新增生产计划货物项
	public void saveProductionPlanOrder(ProductionPlanOrder productionPlanOrder) {
		productionPlanOrderDao.saveProductionPlanOrder(productionPlanOrder);
	}

	// 根据生产计划获得生产计划成品项集合
	public List<ProductionPlanOrder> queryPlanOrderByPlanId(Integer productionPlanId) {
		return productionPlanOrderDao.queryPlanOrderByPlanId(productionPlanId);
	}

	// 编辑生产计划成品项
	public void editProductionPlanOrder(ProductionPlanOrder productionPlanOrder) {
		productionPlanOrderDao.editProductionPlanOrder(productionPlanOrder);
	}

	// 根据Id删除生产计划成品项
	public void deleteProductionPlanOrderById(Integer row_Id) {
		productionPlanOrderDao.deleteProductionPlanOrderById(row_Id);
	}

}
