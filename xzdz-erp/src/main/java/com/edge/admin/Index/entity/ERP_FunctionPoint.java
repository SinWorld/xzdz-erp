package com.edge.admin.Index.entity;

import java.util.TreeSet;

/**
 * 系统功能点实体类
 * 
 * @author NingCG
 *
 */
public class ERP_FunctionPoint implements Comparable<ERP_FunctionPoint> {
	private Integer fp_Id;// 主键
	private String fp_Name;// 功能点名称
	private String fp_Url;// 功能点URL
	private Integer fp_parentId;// 上级功能点
	private String fp_Icon;// 字体图标
	private Boolean Is_Yc;// 是否隐藏

	private TreeSet<ERP_FunctionPoint> children = new TreeSet<ERP_FunctionPoint>();// 辅助属性 下级权限

	public Integer getFp_Id() {
		return fp_Id;
	}

	public void setFp_Id(Integer fp_Id) {
		this.fp_Id = fp_Id;
	}

	public String getFp_Name() {
		return fp_Name;
	}

	public void setFp_Name(String fp_Name) {
		this.fp_Name = fp_Name;
	}

	public String getFp_Url() {
		return fp_Url;
	}

	public void setFp_Url(String fp_Url) {
		this.fp_Url = fp_Url;
	}

	public Integer getFp_parentId() {
		return fp_parentId;
	}

	public void setFp_parentId(Integer fp_parentId) {
		this.fp_parentId = fp_parentId;
	}

	public String getFp_Icon() {
		return fp_Icon;
	}

	public void setFp_Icon(String fp_Icon) {
		this.fp_Icon = fp_Icon;
	}

	public Boolean getIs_Yc() {
		return Is_Yc;
	}

	public void setIs_Yc(Boolean is_Yc) {
		Is_Yc = is_Yc;
	}

	public TreeSet<ERP_FunctionPoint> getChildren() {
		return children;
	}

	public void setChildren(ERP_FunctionPoint children) {
		this.children.add(children);
	}

	@Override
	public String toString() {
		return "ERP_FunctionPoint [fp_Id=" + fp_Id + ", fp_Name=" + fp_Name + ", fp_Url=" + fp_Url + ", fp_parentId="
				+ fp_parentId + ", fp_Icon=" + fp_Icon + ", Is_Yc=" + Is_Yc + ", children=" + children + "]";
	}

	public int compareTo(ERP_FunctionPoint f) {
		if (this.fp_Id > f.fp_Id) {
			return 1;
		} else {
			return -1;
		}
	}

}
