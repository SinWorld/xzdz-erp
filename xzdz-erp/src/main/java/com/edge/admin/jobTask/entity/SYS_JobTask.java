package com.edge.admin.jobTask.entity;

/**
 * 定时任务实体类
 * 
 * @author NingCG
 *
 */
public class SYS_JobTask {
	private Integer job_Task_Id_;// 主键
	private String job_Task_Name_;// 定时任务名称
	private String job_Task_Remark_;// 定时任务描述
	private String job_Task_ObjId;// 功能数据主键
	private String job_Task_Class_Name_;// 定时任务文件名称
	private Integer job_post;// 所属岗位

	public Integer getJob_Task_Id_() {
		return job_Task_Id_;
	}

	public void setJob_Task_Id_(Integer job_Task_Id_) {
		this.job_Task_Id_ = job_Task_Id_;
	}

	public String getJob_Task_Name_() {
		return job_Task_Name_;
	}

	public void setJob_Task_Name_(String job_Task_Name_) {
		this.job_Task_Name_ = job_Task_Name_;
	}

	public String getJob_Task_Remark_() {
		return job_Task_Remark_;
	}

	public void setJob_Task_Remark_(String job_Task_Remark_) {
		this.job_Task_Remark_ = job_Task_Remark_;
	}

	public String getJob_Task_ObjId() {
		return job_Task_ObjId;
	}

	public void setJob_Task_ObjId(String job_Task_ObjId) {
		this.job_Task_ObjId = job_Task_ObjId;
	}

	public String getJob_Task_Class_Name_() {
		return job_Task_Class_Name_;
	}

	public void setJob_Task_Class_Name_(String job_Task_Class_Name_) {
		this.job_Task_Class_Name_ = job_Task_Class_Name_;
	}

	public Integer getJob_post() {
		return job_post;
	}

	public void setJob_post(Integer job_post) {
		this.job_post = job_post;
	}

	@Override
	public String toString() {
		return "SYS_JobTask [job_Task_Id_=" + job_Task_Id_ + ", job_Task_Name_=" + job_Task_Name_
				+ ", job_Task_Remark_=" + job_Task_Remark_ + ", job_Task_ObjId=" + job_Task_ObjId
				+ ", job_Task_Class_Name_=" + job_Task_Class_Name_ + ", job_post=" + job_post + "]";
	}

}
