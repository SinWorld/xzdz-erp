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

	private String product_Name;// 产品名称
	private String specification_Type;// 规格型号
	private String unit;// 单位
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

	public String getProduct_Name() {
		return product_Name;
	}

	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}

	public String getSpecification_Type() {
		return specification_Type;
	}

	public void setSpecification_Type(String specification_Type) {
		this.specification_Type = specification_Type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
		return "ERP_Products_QueryVo [page=" + page + ", rows=" + rows + ", product_Name=" + product_Name
				+ ", specification_Type=" + specification_Type + ", unit=" + unit + ", factory_Price1=" + factory_Price1
				+ ", factory_Price2=" + factory_Price2 + ", channel_Price1=" + channel_Price1 + ", channel_Price2="
				+ channel_Price2 + ", market_Value1=" + market_Value1 + ", market_Value2=" + market_Value2 + "]";
	}

}
