package com.edge.product.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Products_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String productName;// 产品名称
	private String specificationType;// 规格型号
	private String dw;// 单位
	private Integer sl;// 数量
	private Double factory_Price1;// 出厂价
	private Double factory_Price2;// 出厂价
	private Double channel_Price1;// 渠道价
	private Double channel_Price2;// 渠道价
	private Double market_Value1;// 市场价
	private Double market_Value2;// 市场价

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpecificationType() {
		return specificationType;
	}

	public void setSpecificationType(String specificationType) {
		this.specificationType = specificationType;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public Double getFactory_Price1() {
		return factory_Price1;
	}

	public void setFactory_Price1(Double factory_Price1) {
		this.factory_Price1 = factory_Price1;
	}

	public Double getFactory_Price2() {
		return factory_Price2;
	}

	public void setFactory_Price2(Double factory_Price2) {
		this.factory_Price2 = factory_Price2;
	}

	public Double getChannel_Price1() {
		return channel_Price1;
	}

	public void setChannel_Price1(Double channel_Price1) {
		this.channel_Price1 = channel_Price1;
	}

	public Double getChannel_Price2() {
		return channel_Price2;
	}

	public void setChannel_Price2(Double channel_Price2) {
		this.channel_Price2 = channel_Price2;
	}

	public Double getMarket_Value1() {
		return market_Value1;
	}

	public void setMarket_Value1(Double market_Value1) {
		this.market_Value1 = market_Value1;
	}

	public Double getMarket_Value2() {
		return market_Value2;
	}

	public void setMarket_Value2(Double market_Value2) {
		this.market_Value2 = market_Value2;
	}

	@Override
	public String toString() {
		return "ERP_Products_QueryVo [page=" + page + ", rows=" + rows + ", productName=" + productName
				+ ", specificationType=" + specificationType + ", dw=" + dw + ", sl=" + sl + ", factory_Price1="
				+ factory_Price1 + ", factory_Price2=" + factory_Price2 + ", channel_Price1=" + channel_Price1
				+ ", channel_Price2=" + channel_Price2 + ", market_Value1=" + market_Value1 + ", market_Value2="
				+ market_Value2 + "]";
	}

}
