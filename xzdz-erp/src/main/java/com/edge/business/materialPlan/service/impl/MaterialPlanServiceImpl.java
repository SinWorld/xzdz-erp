package com.edge.business.materialPlan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.materialPlan.dao.MaterialPlanDao;
import com.edge.business.materialPlan.entity.ERP_MaterialPlan;
import com.edge.business.materialPlan.service.inter.MaterialPlanService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

/**
 * 材料计划业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterialPlanServiceImpl implements MaterialPlanService {

	@Resource
	private MaterialPlanDao materialPlanDao;

	// 新增材料计划
	public void saveMaterialPlan(ERP_MaterialPlan materialPlan) {
		materialPlanDao.saveMaterialPlan(materialPlan);
	}

	// 查询新增后的材料计划主键
	public Integer materialPlanMaxId() {
		return materialPlanDao.materialPlanMaxId();
	}

	// 根据销售合同Id获得材料计划对象
	public ERP_MaterialPlan queryMaterialPlanByXsht(Integer sales_Contract_Id) {
		return materialPlanDao.queryMaterialPlanByXsht(sales_Contract_Id);
	}

	// 编辑材料计划
	public void editMaterialPlan(ERP_MaterialPlan materialPlan) {
		materialPlanDao.editMaterialPlan(materialPlan);
	}

	// 根据材料集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(List<Integer> productIds) {
		return materialPlanDao.statsusList(productIds);
	}

}
