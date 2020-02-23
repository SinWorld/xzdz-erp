package com.edge.business.materialPlan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.materialPlan.entity.MaterialPlanOrder;

public interface MaterialPlanOrderDao {

	// 新增材料计划材料项
	public void saveMaterialPlanOrder(MaterialPlanOrder materialPlanOrder);

	// 根据材料计划Id获得材料计划材料项集合
	public List<MaterialPlanOrder> queryOrderByMaplanId(@Param("materialPlanId") Integer materialPlanId);

	// 根据Id获得材料计划材料项对象
	public MaterialPlanOrder queryOrderById(@Param("row_Id") Integer row_Id);

	// 编辑材料计划项对象
	public void editMaterialPlanOrder(MaterialPlanOrder materialPlanOrder);

	// 根据Id删除材料计划项对象
	public void deleteMaterialPlanOrder(@Param("row_Id") Integer row_Id);
}
