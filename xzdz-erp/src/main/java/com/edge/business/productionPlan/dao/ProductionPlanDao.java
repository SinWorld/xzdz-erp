package com.edge.business.productionPlan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;

public interface ProductionPlanDao {
	// 加载所有的部门
	public JSONArray allDepartment();

	// 根据物料Id查询当前货物订单库存不为0的成品主键
	public List<Integer> planProductId(@Param("materielId") String materielId);

	// 新增生产计划
	public void saveProductionPlan(ERP_ProductionPlan productionPlan);

	// 查询新增后的生产计划主键
	public Integer queryMaxProductionPlanId();

	// 根据销售订单主键获得生产计划对象
	public ERP_ProductionPlan queryPlanByXsht(@Param("sales_Contract_Id") Integer sales_Contract_Id);

	// 编辑生产计划
	public void editProductionPlan(ERP_ProductionPlan productionPlan);

	// 加载生产计划中销售订单货物相同物料Id的成品总数量
	public Integer queryContractOrderCount(@Param("sales_Contract") Integer sales_Contract,
			@Param("materielId") String materielId);

}
