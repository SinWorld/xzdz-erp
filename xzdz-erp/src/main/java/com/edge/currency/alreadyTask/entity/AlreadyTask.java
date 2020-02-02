package com.edge.currency.alreadyTask.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 已办实体类
 * 
 * @author NingCG
 *
 */
public class AlreadyTask {
	private Integer ID_;// 主键
	private String TASK_ID_;// 任务Id
	private Integer REV_;// 版本
	private String EXECUTION_ID_;
	private String PROC_INST_ID_;// 历史流程实例
	private String PROC_DEF_ID_;// 流程部署Id
	private String NAME_;// 节点名称
	private String PARENT_TASK_ID_;
	private String DESCRIPTION_;
	private String TASK_DEF_KEY_;// 历史任务Id
	private String OWNER_;
	private String ASSIGNEE_;
	private String DELEGATION_;
	private Integer PRIORITY_;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
	private Date START_TIME_;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
	private Date END_TIME_;
	private String FORM_KEY_;
	private String BUSINESS_KEY_;
	private String COMPLETION_STATUS_;// 状态表
	// 任务发起人
	private String CREATE_USER_;

	// 辅助属性
	private String userName;
	// 辅助字段代办任务描述
	private String taskDecription;
	private String createUser;

	public Integer getID_() {
		return ID_;
	}

	public void setID_(Integer iD_) {
		ID_ = iD_;
	}

	public String getTASK_ID_() {
		return TASK_ID_;
	}

	public void setTASK_ID_(String tASK_ID_) {
		TASK_ID_ = tASK_ID_;
	}

	public Integer getREV_() {
		return REV_;
	}

	public void setREV_(Integer rEV_) {
		REV_ = rEV_;
	}

	public String getEXECUTION_ID_() {
		return EXECUTION_ID_;
	}

	public void setEXECUTION_ID_(String eXECUTION_ID_) {
		EXECUTION_ID_ = eXECUTION_ID_;
	}

	public String getPROC_INST_ID_() {
		return PROC_INST_ID_;
	}

	public void setPROC_INST_ID_(String pROC_INST_ID_) {
		PROC_INST_ID_ = pROC_INST_ID_;
	}

	public String getPROC_DEF_ID_() {
		return PROC_DEF_ID_;
	}

	public void setPROC_DEF_ID_(String pROC_DEF_ID_) {
		PROC_DEF_ID_ = pROC_DEF_ID_;
	}

	public String getNAME_() {
		return NAME_;
	}

	public void setNAME_(String nAME_) {
		NAME_ = nAME_;
	}

	public String getPARENT_TASK_ID_() {
		return PARENT_TASK_ID_;
	}

	public void setPARENT_TASK_ID_(String pARENT_TASK_ID_) {
		PARENT_TASK_ID_ = pARENT_TASK_ID_;
	}

	public String getDESCRIPTION_() {
		return DESCRIPTION_;
	}

	public void setDESCRIPTION_(String dESCRIPTION_) {
		DESCRIPTION_ = dESCRIPTION_;
	}

	public String getTASK_DEF_KEY_() {
		return TASK_DEF_KEY_;
	}

	public void setTASK_DEF_KEY_(String tASK_DEF_KEY_) {
		TASK_DEF_KEY_ = tASK_DEF_KEY_;
	}

	public String getOWNER_() {
		return OWNER_;
	}

	public void setOWNER_(String oWNER_) {
		OWNER_ = oWNER_;
	}

	public String getASSIGNEE_() {
		return ASSIGNEE_;
	}

	public void setASSIGNEE_(String aSSIGNEE_) {
		ASSIGNEE_ = aSSIGNEE_;
	}

	public String getDELEGATION_() {
		return DELEGATION_;
	}

	public void setDELEGATION_(String dELEGATION_) {
		DELEGATION_ = dELEGATION_;
	}

	public Integer getPRIORITY_() {
		return PRIORITY_;
	}

	public void setPRIORITY_(Integer pRIORITY_) {
		PRIORITY_ = pRIORITY_;
	}

	public Date getSTART_TIME_() {
		return START_TIME_;
	}

	public void setSTART_TIME_(Date sTART_TIME_) {
		START_TIME_ = sTART_TIME_;
	}

	public Date getEND_TIME_() {
		return END_TIME_;
	}

	public void setEND_TIME_(Date eND_TIME_) {
		END_TIME_ = eND_TIME_;
	}

	public String getFORM_KEY_() {
		return FORM_KEY_;
	}

	public void setFORM_KEY_(String fORM_KEY_) {
		FORM_KEY_ = fORM_KEY_;
	}

	public String getBUSINESS_KEY_() {
		return BUSINESS_KEY_;
	}

	public void setBUSINESS_KEY_(String bUSINESS_KEY_) {
		BUSINESS_KEY_ = bUSINESS_KEY_;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskDecription() {
		return taskDecription;
	}

	public void setTaskDecription(String taskDecription) {
		this.taskDecription = taskDecription;
	}

	public String getCOMPLETION_STATUS_() {
		return COMPLETION_STATUS_;
	}

	public void setCOMPLETION_STATUS_(String cOMPLETION_STATUS_) {
		COMPLETION_STATUS_ = cOMPLETION_STATUS_;
	}

	public String getCREATE_USER_() {
		return CREATE_USER_;
	}

	public void setCREATE_USER_(String cREATE_USER_) {
		CREATE_USER_ = cREATE_USER_;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "AlreadyTask [ID_=" + ID_ + ", TASK_ID_=" + TASK_ID_ + ", REV_=" + REV_ + ", EXECUTION_ID_="
				+ EXECUTION_ID_ + ", PROC_INST_ID_=" + PROC_INST_ID_ + ", PROC_DEF_ID_=" + PROC_DEF_ID_ + ", NAME_="
				+ NAME_ + ", PARENT_TASK_ID_=" + PARENT_TASK_ID_ + ", DESCRIPTION_=" + DESCRIPTION_ + ", TASK_DEF_KEY_="
				+ TASK_DEF_KEY_ + ", OWNER_=" + OWNER_ + ", ASSIGNEE_=" + ASSIGNEE_ + ", DELEGATION_=" + DELEGATION_
				+ ", PRIORITY_=" + PRIORITY_ + ", START_TIME_=" + START_TIME_ + ", END_TIME_=" + END_TIME_
				+ ", FORM_KEY_=" + FORM_KEY_ + ", BUSINESS_KEY_=" + BUSINESS_KEY_ + ", COMPLETION_STATUS_="
				+ COMPLETION_STATUS_ + ", CREATE_USER_=" + CREATE_USER_ + ", userName=" + userName + ", taskDecription="
				+ taskDecription + ", createUser=" + createUser + "]";
	}

}
