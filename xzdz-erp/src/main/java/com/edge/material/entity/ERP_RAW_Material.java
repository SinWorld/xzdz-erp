package com.edge.material.entity;

/**
 * 原材料信息实体类
 * 
 * @author NingCG
 *
 */
public class ERP_RAW_Material {
	private Integer raw_Material_Id;// 主键
	private String material_Code;// 材料编码
	private String material_Name;// 材料名称
	private String specification_Type;// 规格单位
	private String unit;// 单位
	private String remarks;// 备注

	public Integer getRaw_Material_Id() {
		return raw_Material_Id;
	}

	public void setRaw_Material_Id(Integer raw_Material_Id) {
		this.raw_Material_Id = raw_Material_Id;
	}

	public String getMaterial_Code() {
		return material_Code;
	}

	public void setMaterial_Code(String material_Code) {
		this.material_Code = material_Code;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ERP_RAW_Material [raw_Material_Id=" + raw_Material_Id + ", material_Code=" + material_Code
				+ ", material_Name=" + material_Name + ", specification_Type=" + specification_Type + ", unit=" + unit
				+ ", remarks=" + remarks + "]";
	}

}
