package com.edge.business.materialPlan.service.inter;

import java.util.List;

import com.edge.business.materialPlan.entity.ERP_MaterialPlan;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

public interface MaterialPlanService {

	// 新增材料计划
	public void saveMaterialPlan(ERP_MaterialPlan materialPlan);

	// 查询新增后的材料计划主键
	public Integer materialPlanMaxId();

	// 根据销售合同Id获得材料计划对象
	public ERP_MaterialPlan queryMaterialPlanByXsht(Integer sales_Contract_Id);

	// 编辑材料计划
	public void editMaterialPlan(ERP_MaterialPlan materialPlan);

	// 根据材料集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(List<Integer> productIds);
}
