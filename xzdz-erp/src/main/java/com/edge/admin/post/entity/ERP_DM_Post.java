package com.edge.admin.post.entity;

/**
 * 岗位实体类
 * 
 * @author NingCG
 *
 */
public class ERP_DM_Post {
	private Integer post_Id;// 主键
	private String post_Name;// 岗位名称
	private String post_Code;// 岗位代号
	private Integer post_Department;// 所属部门
	private Boolean post_Flag;// 是否删除

	// 辅助属性
	private String dep_Name;// 部门名称

	public Integer getPost_Id() {
		return post_Id;
	}

	public void setPost_Id(Integer post_Id) {
		this.post_Id = post_Id;
	}

	public String getPost_Name() {
		return post_Name;
	}

	public void setPost_Name(String post_Name) {
		this.post_Name = post_Name;
	}

	public String getPost_Code() {
		return post_Code;
	}

	public void setPost_Code(String post_Code) {
		this.post_Code = post_Code;
	}

	public Integer getPost_Department() {
		return post_Department;
	}

	public void setPost_Department(Integer post_Department) {
		this.post_Department = post_Department;
	}

	public Boolean getPost_Flag() {
		return post_Flag;
	}

	public void setPost_Flag(Boolean post_Flag) {
		this.post_Flag = post_Flag;
	}

	public String getDep_Name() {
		return dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		this.dep_Name = dep_Name;
	}

	@Override
	public String toString() {
		return "ERP_DM_Post [post_Id=" + post_Id + ", post_Name=" + post_Name + ", post_Code=" + post_Code
				+ ", post_Department=" + post_Department + ", post_Flag=" + post_Flag + ", dep_Name=" + dep_Name + "]";
	}

}
