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
	private Integer numbers;// 生产数量
	private Boolean is_rk;// 是否入库
	private Boolean is_ck;// 是否出库
	private Boolean is_allrk;// 是否全部入库
	private Boolean is_allck;// 是否全部出库
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

	public Boolean getIs_allrk() {
		return is_allrk;
	}

	public void setIs_allrk(Boolean is_allrk) {
		this.is_allrk = is_allrk;
	}

	public Boolean getIs_allck() {
		return is_allck;
	}

	public void setIs_allck(Boolean is_allck) {
		this.is_allck = is_allck;
	}

	@Override
	public String toString() {
		return "ERP_RAW_Material [raw_Material_Id=" + raw_Material_Id + ", material_Code=" + material_Code
				+ ", material_Name=" + material_Name + ", specification_Type=" + specification_Type + ", unit=" + unit
				+ ", numbers=" + numbers + ", is_rk=" + is_rk + ", is_ck=" + is_ck + ", is_allrk=" + is_allrk
				+ ", is_allck=" + is_allck + ", remarks=" + remarks + "]";
	}

}
