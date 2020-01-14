package com.edge.stocks.material.rk.entity;

/**
 * 原材料库存表
 * 
 * @author NingCG
 *
 */
public class ERP_Material_Stock {
	private Integer material_Id;// 主键
	private Integer material;// 原材料
	private String stock;// 库位
	private Integer rknumber;// 入库数量
	private Integer cknumber;// 出库数量
	private String remarks;// 备注

	// 辅助属性
	private String materialName;// 材料名称
	private Boolean is_rk;// 是否全部入库
	private Boolean is_ck;// 是否全部出库
	private Integer totalNumber;// 生产总数量

	public Integer getMaterial_Id() {
		return material_Id;
	}

	public void setMaterial_Id(Integer material_Id) {
		this.material_Id = material_Id;
	}

	public Integer getMaterial() {
		return material;
	}

	public void setMaterial(Integer material) {
		this.material = material;
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

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Boolean getIs_rk() {
		return is_rk;
	}

	public void setIs_rk(Boolean is_rk) {
		this.is_rk = is_rk;
	}

	public Boolean getIs_ck() {
		return is_ck;
	}

	public void setIs_ck(Boolean is_ck) {
		this.is_ck = is_ck;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getRknumber() {
		return rknumber;
	}

	public void setRknumber(Integer rknumber) {
		this.rknumber = rknumber;
	}

	public Integer getCknumber() {
		return cknumber;
	}

	public void setCknumber(Integer cknumber) {
		this.cknumber = cknumber;
	}

	@Override
	public String toString() {
		return "ERP_Material_Stock [material_Id=" + material_Id + ", material=" + material + ", stock=" + stock
				+ ", rknumber=" + rknumber + ", cknumber=" + cknumber + ", remarks=" + remarks + ", materialName="
				+ materialName + ", is_rk=" + is_rk + ", is_ck=" + is_ck + ", totalNumber=" + totalNumber + "]";
	}

}
