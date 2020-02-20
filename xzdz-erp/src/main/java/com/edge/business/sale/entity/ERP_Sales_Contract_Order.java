package com.edge.business.sale.entity;

/**
 * 销售合同货物清单
 * 
 * @author NingCG
 *
 */
public class ERP_Sales_Contract_Order {
	private Integer sales_Contract_Id;// 主键
	private String material_Name;// 货物名称
	private String specification_Type;// 规格型号
	private Integer sl;// 数量
	private String unit;// 单位
	private Double price;// 单价
	private Double total_price;// 金额
	private String bz;// 备注
	private Integer sales_Contract;// 销售合同
	private String materielId;// 物料Id

	public Integer getSales_Contract_Id() {
		return sales_Contract_Id;
	}

	public void setSales_Contract_Id(Integer sales_Contract_Id) {
		this.sales_Contract_Id = sales_Contract_Id;
	}

	public String getMaterial_Name() {
		return material_Name;
	}

	public void setMaterial_Name(String material_Name) {
		this.material_Name = material_Name;
	}

	public String getSpecification_Type() {
		return specification_Type;
	}

	public void setSpecification_Type(String specification_Type) {
		this.specification_Type = specification_Type;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getSales_Contract() {
		return sales_Contract;
	}

	public void setSales_Contract(Integer sales_Contract) {
		this.sales_Contract = sales_Contract;
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	@Override
	public String toString() {
		return "ERP_Sales_Contract_Order [sales_Contract_Id=" + sales_Contract_Id + ", material_Name=" + material_Name
				+ ", specification_Type=" + specification_Type + ", sl=" + sl + ", unit=" + unit + ", price=" + price
				+ ", total_price=" + total_price + ", bz=" + bz + ", sales_Contract=" + sales_Contract + ", materielId="
				+ materielId + "]";
	}

}
