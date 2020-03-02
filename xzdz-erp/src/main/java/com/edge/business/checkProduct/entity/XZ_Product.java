package com.edge.business.checkProduct.entity;

public class XZ_Product {

	private String productName;

	private Integer kcNumber;

	private String Ggxh;

	private Integer product_Id;

	private Integer cksl;

	private Integer rksl;

	private Integer stock_Id;

	private String stock;

	private String materielId;

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

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public Integer getRksl() {
		return rksl;
	}

	public void setRksl(Integer rksl) {
		this.rksl = rksl;
	}

	@Override
	public String toString() {
		return "XZ_Product [productName=" + productName + ", kcNumber=" + kcNumber + ", Ggxh=" + Ggxh + ", product_Id="
				+ product_Id + ", cksl=" + cksl + ", rksl=" + rksl + ", stock_Id=" + stock_Id + ", stock=" + stock
				+ ", materielId=" + materielId + "]";
	}

}
