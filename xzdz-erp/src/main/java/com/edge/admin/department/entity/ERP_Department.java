package com.edge.admin.department.entity;

/**
 * 部门实体类
 * 
 * @author NingCG
 *
 */
public class ERP_Department implements Comparable<ERP_Department> {
	private Integer dep_Id;// 主键
	private String dep_Code;// 部门编码
	private String dep_Name;// 部门名称
	private Integer dep_parentId;// 上级部门
	private Boolean dep_flag;// 禁用/启用
	private String remarks;// 备注

	// 辅助属性
	private String parentDepName;// 上级部门名称

	public Integer getDep_Id() {
		return dep_Id;
	}

	public void setDep_Id(Integer dep_Id) {
		this.dep_Id = dep_Id;
	}

	public String getDep_Code() {
		return dep_Code;
	}

	public void setDep_Code(String dep_Code) {
		this.dep_Code = dep_Code;
	}

	public String getDep_Name() {
		return dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		this.dep_Name = dep_Name;
	}

	public Integer getDep_parentId() {
		return dep_parentId;
	}

	public void setDep_parentId(Integer dep_parentId) {
		this.dep_parentId = dep_parentId;
	}

	public Boolean getDep_flag() {
		return dep_flag;
	}

	public void setDep_flag(Boolean dep_flag) {
		this.dep_flag = dep_flag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getParentDepName() {
		return parentDepName;
	}

	public void setParentDepName(String parentDepName) {
		this.parentDepName = parentDepName;
	}

	@Override
	public String toString() {
		return "ERP_Department [dep_Id=" + dep_Id + ", dep_Code=" + dep_Code + ", dep_Name=" + dep_Name
				+ ", dep_parentId=" + dep_parentId + ", dep_flag=" + dep_flag + ", remarks=" + remarks
				+ ", parentDepName=" + parentDepName + "]";
	}

	public int compareTo(ERP_Department d) {
		if (this.dep_Id > d.dep_Id) {
			return 1;
		} else {
			return -1;
		}
	}

}
