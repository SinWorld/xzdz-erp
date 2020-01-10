package com.edge.product.entity;

public class ERP_Products {
	private Integer product_Id;// 主键
	private String product_Code;// 产品编码
	private String product_Name;// 产品名称
	private String specification_Type;// 规格型号
	private String unit;// 单位
	private Double factory_Price;// 出厂价
	private Double channel_Price;// 渠道价
	private Double market_Value;// 市场价
	private Integer numbers;// 生产数量
	private Boolean is_rk;// 是否全部入库
	private Boolean is_ck;// 是否全部出库
	private String remarks;// 备注

	public Integer getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(Integer product_Id) {
		this.product_Id = product_Id;
	}

	public String getProduct_Code() {
		return product_Code;
	}

	public void setProduct_Code(String product_Code) {
		this.product_Code = product_Code;
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

	public Double getFactory_Price() {
		return factory_Price;
	}

	public void setFactory_Price(Double factory_Price) {
		this.factory_Price = factory_Price;
	}

	public Double getChannel_Price() {
		return channel_Price;
	}

	public void setChannel_Price(Double channel_Price) {
		this.channel_Price = channel_Price;
	}

	public Double getMarket_Value() {
		return market_Value;
	}

	public void setMarket_Value(Double market_Value) {
		this.market_Value = market_Value;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
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

	@Override
	public String toString() {
		return "ERP_Products [product_Id=" + product_Id + ", product_Code=" + product_Code + ", product_Name="
				+ product_Name + ", specification_Type=" + specification_Type + ", unit=" + unit + ", factory_Price="
				+ factory_Price + ", channel_Price=" + channel_Price + ", market_Value=" + market_Value + ", numbers="
				+ numbers + ", is_rk=" + is_rk + ", is_ck=" + is_ck + ", remarks=" + remarks + "]";
	}

}
