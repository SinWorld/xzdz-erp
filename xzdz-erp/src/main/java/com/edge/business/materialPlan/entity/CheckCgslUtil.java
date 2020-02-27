package com.edge.business.materialPlan.entity;

/**
 * 采购数量检测工具类
 * 
 * @author NingCG
 *
 */
public class CheckCgslUtil {
	private Integer materialPlanId;// 材料计划Id
	private String materielId;// 物料Id

	public Integer getMaterialPlanId() {
		return materialPlanId;
	}

	public void setMaterialPlanId(Integer materialPlanId) {
		this.materialPlanId = materialPlanId;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	@Override
	public String toString() {
		return "CheckCgslUtil [materialPlanId=" + materialPlanId + ", materielId=" + materielId + "]";
	}

}
