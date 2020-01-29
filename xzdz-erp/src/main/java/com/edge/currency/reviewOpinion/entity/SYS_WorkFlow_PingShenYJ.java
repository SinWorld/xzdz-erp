package com.edge.currency.reviewOpinion.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 评审意见实体类
 * 
 * @author NingCG
 *
 */
public class SYS_WorkFlow_PingShenYJ {
	private Integer ID_;// 主键
	private String TYPE_;// 类型
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
	private Date TIME_;// 审批时间
	private Integer USER_ID_;// 审批用户主键
	private String TASK_ID_;// 任务Id
	private String TASK_NAME_;// 节点名称
	private String PROC_INST_ID_;// 流程实例Id
	private String ACTION_;// 流程信息
	private String MESSAGE_RESULT_;// 审批结果
	private String MESSAGE_INFOR_;// 审批信息
	private String TITLE_;

	// 辅助属性
	private String userName;// 审批人名称
	private String time;// 审批时间
	private String procinstById;// 流程部署Id

	public Integer getID_() {
		return ID_;
	}

	public void setID_(Integer iD_) {
		ID_ = iD_;
	}

	public String getTYPE_() {
		return TYPE_;
	}

	public void setTYPE_(String tYPE_) {
		TYPE_ = tYPE_;
	}

	public Date getTIME_() {
		return TIME_;
	}

	public void setTIME_(Date tIME_) {
		TIME_ = tIME_;
	}

	public Integer getUSER_ID_() {
		return USER_ID_;
	}

	public void setUSER_ID_(Integer uSER_ID_) {
		USER_ID_ = uSER_ID_;
	}

	public String getTASK_ID_() {
		return TASK_ID_;
	}

	public void setTASK_ID_(String tASK_ID_) {
		TASK_ID_ = tASK_ID_;
	}

	public String getTASK_NAME_() {
		return TASK_NAME_;
	}

	public void setTASK_NAME_(String tASK_NAME_) {
		TASK_NAME_ = tASK_NAME_;
	}

	public String getPROC_INST_ID_() {
		return PROC_INST_ID_;
	}

	public void setPROC_INST_ID_(String pROC_INST_ID_) {
		PROC_INST_ID_ = pROC_INST_ID_;
	}

	public String getACTION_() {
		return ACTION_;
	}

	public void setACTION_(String aCTION_) {
		ACTION_ = aCTION_;
	}

	public String getMESSAGE_RESULT_() {
		return MESSAGE_RESULT_;
	}

	public void setMESSAGE_RESULT_(String mESSAGE_RESULT_) {
		MESSAGE_RESULT_ = mESSAGE_RESULT_;
	}

	public String getMESSAGE_INFOR_() {
		return MESSAGE_INFOR_;
	}

	public void setMESSAGE_INFOR_(String mESSAGE_INFOR_) {
		MESSAGE_INFOR_ = mESSAGE_INFOR_;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProcinstById() {
		return procinstById;
	}

	public void setProcinstById(String procinstById) {
		this.procinstById = procinstById;
	}

	public String getTITLE_() {
		return TITLE_;
	}

	public void setTITLE_(String tITLE_) {
		TITLE_ = tITLE_;
	}

	@Override
	public String toString() {
		return "SYS_WorkFlow_PingShenYJ [ID_=" + ID_ + ", TYPE_=" + TYPE_ + ", TIME_=" + TIME_ + ", USER_ID_="
				+ USER_ID_ + ", TASK_ID_=" + TASK_ID_ + ", TASK_NAME_=" + TASK_NAME_ + ", PROC_INST_ID_="
				+ PROC_INST_ID_ + ", ACTION_=" + ACTION_ + ", MESSAGE_RESULT_=" + MESSAGE_RESULT_ + ", MESSAGE_INFOR_="
				+ MESSAGE_INFOR_ + ", TITLE_=" + TITLE_ + ", userName=" + userName + ", time=" + time
				+ ", procinstById=" + procinstById + "]";
	}

}
