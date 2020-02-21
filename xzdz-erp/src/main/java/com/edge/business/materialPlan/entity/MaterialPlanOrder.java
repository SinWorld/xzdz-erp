package com.edge.business.materialPlan.entity;

/**
 * 生产计划 购物项
 * 
 * @author NingCG
 *
 */
public class MaterialPlanOrder {
	private Integer row_Id;// 主键
	private String materielId;// 物料id
	private String materialName;// 材料名称
	private String specification_Type;// 规格型号
	private Integer materialPlanId;// 材料计划Id
	private Integer planNumber;// 计划数量

	// 辅助属性
	private Integer cgsl;// 采购数量

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getMaterialPlanId() {
		return materialPlanId;
	}

	public void setMaterialPlanId(Integer materialPlanId) {
		this.materialPlanId = materialPlanId;
	}

	public String getSpecification_Type() {
		return specification_Type;
	}

	public void setSpecification_Type(String specification_Type) {
		this.specification_Type = specification_Type;
	}

	public Integer getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(Integer planNumber) {
		this.planNumber = planNumber;
	}

	public Integer getCgsl() {
		return cgsl;
	}

	public void setCgsl(Integer cgsl) {
		this.cgsl = cgsl;
	}

	@Override
	public String toString() {
		return "MaterialPlanOrder [row_Id=" + row_Id + ", materielId=" + materielId + ", materialName=" + materialName
				+ ", specification_Type=" + specification_Type + ", materialPlanId=" + materialPlanId + ", planNumber="
				+ planNumber + ", cgsl=" + cgsl + "]";
	}

}
