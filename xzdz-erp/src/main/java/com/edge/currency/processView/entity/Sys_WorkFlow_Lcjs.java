package com.edge.currency.processView.entity;

/**
 * 流程检视实体类
 * 
 * @author NingCG
 *
 */
public class Sys_WorkFlow_Lcjs {

	private Integer row_Id;// 主键
	private String nodeName;// 节点名称
	private String processingUsers;// 处理用户
	private String nodeInfo;// 办理信息
	private String objDm;// obj代码
	private String beginTime;// 开始时间
	private String endTime;// 结束时间

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getProcessingUsers() {
		return processingUsers;
	}

	public void setProcessingUsers(String processingUsers) {
		this.processingUsers = processingUsers;
	}

	public String getNodeInfo() {
		return nodeInfo;
	}

	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}

	public String getObjDm() {
		return objDm;
	}

	public void setObjDm(String objDm) {
		this.objDm = objDm;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Sys_WorkFlow_Lcjs [row_Id=" + row_Id + ", nodeName=" + nodeName + ", processingUsers=" + processingUsers
				+ ", nodeInfo=" + nodeInfo + ", objDm=" + objDm + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ "]";
	}

}
