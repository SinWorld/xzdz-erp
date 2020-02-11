package com.edge.business.ckfh.entity;

/**
 * 送货货物项实体类实体类
 * 
 * @author NingCG
 *
 */
public class ERP_DeliveryOrder {
	private Integer deliveryOrder_Id;// 主键
	private String material_Name;// 物料名称
	private String specification_Type;// 规格型号
	private String company;// 单位
	private Integer delivery_Number;// 送货数量
	private String remarks;// 备注
	private Integer delivery_Id;// 送货表外键

	private Integer stock;// 库存
	private Integer product;// 成品

	public Integer getDeliveryOrder_Id() {
		return deliveryOrder_Id;
	}

	public void setDeliveryOrder_Id(Integer deliveryOrder_Id) {
		this.deliveryOrder_Id = deliveryOrder_Id;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getDelivery_Number() {
		return delivery_Number;
	}

	public void setDelivery_Number(Integer delivery_Number) {
		this.delivery_Number = delivery_Number;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getDelivery_Id() {
		return delivery_Id;
	}

	public void setDelivery_Id(Integer delivery_Id) {
		this.delivery_Id = delivery_Id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ERP_DeliveryOrder [deliveryOrder_Id=" + deliveryOrder_Id + ", material_Name=" + material_Name
				+ ", specification_Type=" + specification_Type + ", company=" + company + ", delivery_Number="
				+ delivery_Number + ", remarks=" + remarks + ", delivery_Id=" + delivery_Id + ", stock=" + stock
				+ ", product=" + product + "]";
	}

}
