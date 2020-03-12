package com.edge.currency.notice.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 通知实体类
 * 
 * @author NingCG
 *
 */
public class Notice {

	private Integer row_Id;// 主键
	private String content;// 通知内容
	private Boolean ready;// 是否已读
	private String objId;// 功能OBJId
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;// 创建时间

	public Integer getRow_Id() {
		return row_Id;
	}

	public void setRow_Id(Integer row_Id) {
		this.row_Id = row_Id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getReady() {
		return ready;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Notice [row_Id=" + row_Id + ", content=" + content + ", ready=" + ready + ", objId=" + objId
				+ ", createTime=" + createTime + "]";
	}

}
