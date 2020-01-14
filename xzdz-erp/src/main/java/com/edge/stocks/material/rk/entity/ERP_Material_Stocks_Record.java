package com.edge.stocks.material.rk.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 原材料出/入库记录实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Material_Stocks_Record {
	private Integer record_Id;// 主键
	private Integer material;// 材料信息
	private Integer stock;// 库存
	private Boolean record_Type;// 出入库类别(0入库[false] 1 出库[true])
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sj;// 出/入库时间
	private Integer jbr;// 经办人
	private Integer sl;// 数量
	private String remarks;// 备注

	public Integer getRecord_Id() {
		return record_Id;
	}

	public void setRecord_Id(Integer record_Id) {
		this.record_Id = record_Id;
	}

	public Integer getMaterial() {
		return material;
	}

	public void setMaterial(Integer material) {
		this.material = material;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getRecord_Type() {
		return record_Type;
	}

	public void setRecord_Type(Boolean record_Type) {
		this.record_Type = record_Type;
	}

	public Date getSj() {
		return sj;
	}

	public void setSj(Date sj) {
		this.sj = sj;
	}

	public Integer getJbr() {
		return jbr;
	}

	public void setJbr(Integer jbr) {
		this.jbr = jbr;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ERP_Material_Stocks_Record [record_Id=" + record_Id + ", material=" + material + ", stock=" + stock
				+ ", record_Type=" + record_Type + ", sj=" + sj + ", jbr=" + jbr + ", sl=" + sl + ", remarks=" + remarks
				+ "]";
	}

}
