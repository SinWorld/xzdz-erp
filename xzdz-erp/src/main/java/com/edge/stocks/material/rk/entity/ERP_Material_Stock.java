package com.edge.stocks.material.rk.entity;

/**
 * 原材料库存表
 * 
 * @author NingCG
 *
 */
public class ERP_Material_Stock {
	private Integer material_Id;// 主键
	private String stock;// 库位
	private String remarks;// 备注

	private Integer kcl;// 库存量
	private Integer clId;// 材料Id

	public Integer getMaterial_Id() {
		return material_Id;
	}

	public void setMaterial_Id(Integer material_Id) {
		this.material_Id = material_Id;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getKcl() {
		return kcl;
	}

	public void setKcl(Integer kcl) {
		this.kcl = kcl;
	}

	public Integer getClId() {
		return clId;
	}

	public void setClId(Integer clId) {
		this.clId = clId;
	}

	@Override
	public String toString() {
		return "ERP_Material_Stock [material_Id=" + material_Id + ", stock=" + stock + ", remarks=" + remarks + ", kcl="
				+ kcl + ", clId=" + clId + "]";
	}

}
