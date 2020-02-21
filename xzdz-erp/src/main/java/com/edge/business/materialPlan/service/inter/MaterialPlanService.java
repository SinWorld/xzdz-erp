package com.edge.business.materialPlan.service.inter;

import com.edge.business.materialPlan.entity.ERP_MaterialPlan;

public interface MaterialPlanService {

	// 新增材料计划
	public void saveMaterialPlan(ERP_MaterialPlan materialPlan);

	// 查询新增后的材料计划主键
	public Integer materialPlanMaxId();

	// 根据销售合同Id获得材料计划对象
	public ERP_MaterialPlan queryMaterialPlanByXsht(Integer sales_Contract_Id);
}
