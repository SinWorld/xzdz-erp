package com.edge.admin.processDefinition.entity;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class Procdef_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	private String postName;// 岗位名称
	private Integer department;// 所属岗位
	private String postCode;// 岗位说明

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

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "Post_QueryVo [page=" + page + ", rows=" + rows + ", postName=" + postName + ", department=" + department
				+ ", postCode=" + postCode + "]";
	}

}
