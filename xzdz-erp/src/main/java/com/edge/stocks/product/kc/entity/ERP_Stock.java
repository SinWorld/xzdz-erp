package com.edge.stocks.product.kc.entity;

/**
 * 成品库存表
 * 
 * @author NingCG
 *
 */
public class ERP_Stock {
	private Integer row_Id;// 主键

	private Integer stock_Id;// 库位
	private Integer sl;// 数量
	private Boolean stock_Type;// 类别（0:成品 1：材料）
	private String materielId;// 物料Id
	private Integer product_Id;// 成品/材料Id

	// 辅助属性
	private String productName;// 成品名称
	private String stockName;// 库位名称
	private Integer zkcl;// 总库存量

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public Integer getStock_Id() {
		return stock_Id;
	}

	public void setStock_Id(Integer stock_Id) {
		this.stock_Id = stock_Id;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public Boolean getStock_Type() {
		return stock_Type;
	}

	public void setStock_Type(Boolean stock_Type) {
		this.stock_Type = stock_Type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Integer getZkcl() {
		return zkcl;
	}

	public void setZkcl(Integer zkcl) {
		this.zkcl = zkcl;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public Integer getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(Integer product_Id) {
		this.product_Id = product_Id;
	}

	@Override
	public String toString() {
		return "ERP_Stock [row_Id=" + row_Id + ", stock_Id=" + stock_Id + ", sl=" + sl + ", stock_Type=" + stock_Type
				+ ", materielId=" + materielId + ", product_Id=" + product_Id + ", productName=" + productName
				+ ", stockName=" + stockName + ", zkcl=" + zkcl + "]";
	}

}
