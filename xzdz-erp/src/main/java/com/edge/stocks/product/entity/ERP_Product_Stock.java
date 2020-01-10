package com.edge.stocks.product.entity;

/**
 * 成品库存实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Product_Stock {
	private Integer stock_Id;// 主键
	private Integer product;// 成品信息
	private String stock;// 库位
	private Integer rknumber;// 入库数量
	private String remarks;// 备注

	public Integer getStock_Id() {
		return stock_Id;
	}

	public void setStock_Id(Integer stock_Id) {
		this.stock_Id = stock_Id;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
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
		return "ERP_Product_Stock [stock_Id=" + stock_Id + ", product=" + product + ", stock=" + stock + ", rknumber="
				+ rknumber + ", remarks=" + remarks + "]";
	}

}
