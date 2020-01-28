package com.edge.stocks.product.rk.entity;

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
	private Integer cknumber;// 出库数量
	private String remarks;// 备注

	// 辅助属性
	private String productName;// 成品名称
	private Boolean is_rk;// 是否全部入库
	private Boolean is_ck;// 是否全部出库
	private Integer totalNumber;// 生产总数量
	private Integer kcnumber;// 库存量
	private String ggxh;// 规格型号

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Integer getCknumber() {
		return cknumber;
	}

	public void setCknumber(Integer cknumber) {
		this.cknumber = cknumber;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getKcnumber() {
		return kcnumber;
	}

	public void setKcnumber(Integer kcnumber) {
		this.kcnumber = kcnumber;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	@Override
	public String toString() {
		return "ERP_Product_Stock [stock_Id=" + stock_Id + ", product=" + product + ", stock=" + stock + ", rknumber="
				+ rknumber + ", cknumber=" + cknumber + ", remarks=" + remarks + ", productName=" + productName
				+ ", is_rk=" + is_rk + ", is_ck=" + is_ck + ", totalNumber=" + totalNumber + ", kcnumber=" + kcnumber
				+ ", ggxh=" + ggxh + "]";
	}

}
