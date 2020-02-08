package com.edge.stocks.product.rk.entity;

/**
 * 入库工具对象,用于接收json数据
 * 
 * @author NingCG
 *
 */
public class ERP_RkObj {
	private Integer stock_Id;// 库存主键
	private Integer productId;// 成品主键
	private Integer rknumber;// 入库数量
	private String remarks;// 备注

	public Integer getStock_Id() {
		return stock_Id;
	}

	public void setStock_Id(Integer stock_Id) {
		this.stock_Id = stock_Id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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
		return "ERP_RkObj [stock_Id=" + stock_Id + ", productId=" + productId + ", rknumber=" + rknumber + ", remarks="
				+ remarks + "]";
	}

}
