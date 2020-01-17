package com.edge.business.sale.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class ERP_Sales_Contract_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String material_Name;// 产品名称
	private String specification_Type;// 规格型号
	private String unit;// 单位

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "ERP_RAW_Material_QueryVo [page=" + page + ", rows=" + rows + ", material_Name=" + material_Name
				+ ", specification_Type=" + specification_Type + ", unit=" + unit + "]";
	}

}
