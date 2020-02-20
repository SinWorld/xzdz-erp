package com.edge.business.productionPlan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.business.productionPlan.dao.ProductionPlanDao;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;

/**
 * 生产计划业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ProductionPlanServiceImpl implements ProductionPlanService {

	@Resource
	private ProductionPlanDao productionPlanDao;

	public JSONArray allDepartment() {
		return productionPlanDao.allDepartment();
	}

	// 根据物料Id查询当前货物订单库存不为0的成品主键
	public List<Integer> planProductId(String materielId) {
		return productionPlanDao.planProductId(materielId);
	}

	// 新增生产计划
	public void saveProductionPlan(ERP_ProductionPlan productionPlan) {
		productionPlanDao.saveProductionPlan(productionPlan);
	}

	// 查询新增后的生产计划主键
	public Integer queryMaxProductionPlanId() {
		return productionPlanDao.queryMaxProductionPlanId();
	}

	// 根据销售订单主键获得生产计划对象
	public ERP_ProductionPlan queryPlanByXsht(Integer sales_Contract_Id) {
		return productionPlanDao.queryPlanByXsht(sales_Contract_Id);
	}

}
