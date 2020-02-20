package com.edge.stocks.product.rk.entity;

/**
 * 成品库存实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Product_Stock {

	private Integer stock_Id;// 主键

	private String stock;// 库位

	private String remarks;// 备注

	private Integer cksl;// 出库数量

	public Integer getStock_Id() {
		return stock_Id;
	}

	public void setStock_Id(Integer stock_Id) {
		this.stock_Id = stock_Id;
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

	public Integer getCksl() {
		return cksl;
	}

	public void setCksl(Integer cksl) {
		this.cksl = cksl;
	}

	@Override
	public String toString() {
		return "ERP_Product_Stock [stock_Id=" + stock_Id + ", stock=" + stock + ", remarks=" + remarks + ", cksl="
				+ cksl + "]";
	}

}
