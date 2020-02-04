package com.edge.admin.department.entity;

import java.util.List;

public class TreeNode {
	private Integer id;// 文件夹主键
	private String text;// 文件夹名称
	private Integer parent_Id;// 上级文件夹主键

	private String parent_Name;// 上级文件夹名称
	private List<TreeNode> children;// 子菜单集合
	private String state = "close";// 默认展开

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getParent_Id() {
		return parent_Id;
	}

	public void setParent_Id(Integer parent_Id) {
		this.parent_Id = parent_Id;
	}

	public String getParent_Name() {
		return parent_Name;
	}

	public void setParent_Name(String parent_Name) {
		this.parent_Name = parent_Name;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", text=" + text + ", parent_Id=" + parent_Id + ", parent_Name=" + parent_Name
				+ ", children=" + children + ", state=" + state + "]";
	}

}
