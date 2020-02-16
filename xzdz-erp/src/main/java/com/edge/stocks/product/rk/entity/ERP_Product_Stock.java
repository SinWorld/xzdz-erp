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

	private String productName;

	private Integer kcNumber;

	private String Ggxh;

	private Integer product_Id;// 成品主键

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getKcNumber() {
		return kcNumber;
	}

	public void setKcNumber(Integer kcNumber) {
		this.kcNumber = kcNumber;
	}

	public String getGgxh() {
		return Ggxh;
	}

	public void setGgxh(String ggxh) {
		Ggxh = ggxh;
	}

	public Integer getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(Integer product_Id) {
		this.product_Id = product_Id;
	}

	public Integer getCksl() {
		return cksl;
	}

	public void setCksl(Integer cksl) {
		this.cksl = cksl;
	}

	@Override
	public String toString() {
		return "ERP_Product_Stock [stock_Id=" + stock_Id + ", stock=" + stock + ", remarks=" + remarks
				+ ", productName=" + productName + ", kcNumber=" + kcNumber + ", Ggxh=" + Ggxh + ", product_Id="
				+ product_Id + ", cksl=" + cksl + "]";
	}

}
