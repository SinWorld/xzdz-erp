package com.edge.admin.processDefinition.entity;

/**
 * 流程操作实体类
 * 
 * @author NingCG
 *
 */
public class SYS_WorkFlow_Operation implements Comparable<SYS_WorkFlow_Operation> {
	private Integer operation_Id;// 主键
	private String s_Id;// 节点主键
	private String activiti_Name;// 节点名称
	private Integer post_Id;// 岗位主键
	private String procdef_Id;// 流程部署主键

	public Integer getOperation_Id() {
		return operation_Id;
	}

	public void setOperation_Id(Integer operation_Id) {
		this.operation_Id = operation_Id;
	}

	public String getS_Id() {
		return s_Id;
	}

	public void setS_Id(String s_Id) {
		this.s_Id = s_Id;
	}

	public String getActiviti_Name() {
		return activiti_Name;
	}

	public void setActiviti_Name(String activiti_Name) {
		this.activiti_Name = activiti_Name;
	}

	public Integer getPost_Id() {
		return post_Id;
	}

	public void setPost_Id(Integer post_Id) {
		this.post_Id = post_Id;
	}

	public String getProcdef_Id() {
		return procdef_Id;
	}

	public void setProcdef_Id(String procdef_Id) {
		this.procdef_Id = procdef_Id;
	}

	@Override
	public String toString() {
		return "SYS_WorkFlow_Operation [operation_Id=" + operation_Id + ", s_Id=" + s_Id + ", activiti_Name="
				+ activiti_Name + ", post_Id=" + post_Id + ", procdef_Id=" + procdef_Id + "]";
	}

	public int compareTo(SYS_WorkFlow_Operation o) {
		if (this.operation_Id > o.operation_Id) {
			return 1;
		} else {
			return -1;
		}
	}

}
