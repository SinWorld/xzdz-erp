package com.edge.stocks.material.rk.entity;

public class ERP_MatObj {
	private Integer stock_Id;// 库存主键
	private Integer materialId;// 材料主键
	private Integer rknumber;// 入库数量
	private String remarks;// 备注

	public Integer getStock_Id() {
		return stock_Id;
	}

	public void setStock_Id(Integer stock_Id) {
		this.stock_Id = stock_Id;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getRknumber() {
		return rknumber;
	}

	public void setRknumber(Integer rknumber) {
		this.rknumber = rknumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ERP_MatObj [stock_Id=" + stock_Id + ", materialId=" + materialId + ", rknumber=" + rknumber
				+ ", remarks=" + remarks + "]";
	}

}
