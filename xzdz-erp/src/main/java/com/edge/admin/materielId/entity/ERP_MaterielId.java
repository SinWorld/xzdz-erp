package com.edge.admin.materielId.entity;

/**
 * 物料/成品Id实体类
 * 
 * @author NingCG
 *
 */
public class ERP_MaterielId {
	private Integer row_Id;// 主键
	private String materiel_Id;// 物料/成品Id
	private String specification_Type;// 规格型号
	private String remarks;// 备注
	private String bzq;// 保质期
	private Double ckdj;// 参考单价
	private Integer materielType;// 物料id类型
	private Integer materielNumber;// 物料Id类型号
	private String fjsx;// 附件

	// 辅助属性
	private String typeName;

	private String materielTypeName;
	private String materielNumberName;

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public String getMateriel_Id() {
		return materiel_Id;
	}

	public void setMateriel_Id(String materiel_Id) {
		this.materiel_Id = materiel_Id;
	}

	public String getSpecification_Type() {
		return specification_Type;
	}

	public void setSpecification_Type(String specification_Type) {
		this.specification_Type = specification_Type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBzq() {
		return bzq;
	}

	public void setBzq(String bzq) {
		this.bzq = bzq;
	}

	public Double getCkdj() {
		return ckdj;
	}

	public void setCkdj(Double ckdj) {
		this.ckdj = ckdj;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getMaterielType() {
		return materielType;
	}

	public void setMaterielType(Integer materielType) {
		this.materielType = materielType;
	}

	public Integer getMaterielNumber() {
		return materielNumber;
	}

	public void setMaterielNumber(Integer materielNumber) {
		this.materielNumber = materielNumber;
	}

	public String getFjsx() {
		return fjsx;
	}

	public void setFjsx(String fjsx) {
		this.fjsx = fjsx;
	}

	public String getMaterielTypeName() {
		return materielTypeName;
	}

	public void setMaterielTypeName(String materielTypeName) {
		this.materielTypeName = materielTypeName;
	}

	public String getMaterielNumberName() {
		return materielNumberName;
	}

	public void setMaterielNumberName(String materielNumberName) {
		this.materielNumberName = materielNumberName;
	}

	@Override
	public String toString() {
		return "ERP_MaterielId [row_Id=" + row_Id + ", materiel_Id=" + materiel_Id + ", specification_Type="
				+ specification_Type + ", remarks=" + remarks + ", bzq=" + bzq + ", ckdj=" + ckdj + ", materielType="
				+ materielType + ", materielNumber=" + materielNumber + ", fjsx=" + fjsx + ", typeName=" + typeName
				+ ", materielTypeName=" + materielTypeName + ", materielNumberName=" + materielNumberName + "]";
	}

}
