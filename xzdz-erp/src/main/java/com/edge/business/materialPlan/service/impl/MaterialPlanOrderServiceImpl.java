package com.edge.business.materialPlan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.materialPlan.dao.MaterialPlanOrderDao;
import com.edge.business.materialPlan.entity.CheckCgslUtil;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;

/**
 * 材料计划材料项业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterialPlanOrderServiceImpl implements MaterialPlanOrderService {

	@Resource
	private MaterialPlanOrderDao materialPlanOrderDao;

	// 新增材料计划材料项
	public void saveMaterialPlanOrder(MaterialPlanOrder materialPlanOrder) {
		materialPlanOrderDao.saveMaterialPlanOrder(materialPlanOrder);
	}

	// 根据材料计划Id获得材料计划材料项集合
	public List<MaterialPlanOrder> queryOrderByMaplanId(Integer materialPlanId) {
		return materialPlanOrderDao.queryOrderByMaplanId(materialPlanId);
	}

	// 根据Id获得材料计划材料项对象
	public MaterialPlanOrder queryOrderById(Integer row_Id) {
		return materialPlanOrderDao.queryOrderById(row_Id);
	}

	// 编辑材料计划项对象
	public void editMaterialPlanOrder(MaterialPlanOrder materialPlanOrder) {
		materialPlanOrderDao.editMaterialPlanOrder(materialPlanOrder);
	}

	// 根据Id删除材料计划项对象
	public void deleteMaterialPlanOrder(Integer row_Id) {
		materialPlanOrderDao.deleteMaterialPlanOrder(row_Id);
	}

	// 根据材料计划及物料Id获得该材料的计划总数量
	public Integer xsddMaterialCount(String materielId) {
		return materialPlanOrderDao.xsddMaterialCount(materielId);
	}

}
