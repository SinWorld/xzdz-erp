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
	private String content;// 标题
	private Boolean ready;// 是否已读
	private String objId;// 功能OBJId
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;// 创建时间
	private String mbyhs;// 目标用户
	private Integer fsyh;// 发送用户
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ydsj;// 已读时间

	private String ydshij;

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

	public String getMbyhs() {
		return mbyhs;
	}

	public void setMbyhs(String mbyhs) {
		this.mbyhs = mbyhs;
	}

	public Integer getFsyh() {
		return fsyh;
	}

	public void setFsyh(Integer fsyh) {
		this.fsyh = fsyh;
	}

	public Date getYdsj() {
		return ydsj;
	}

	public void setYdsj(Date ydsj) {
		this.ydsj = ydsj;
	}

	public String getYdshij() {
		return ydshij;
	}

	public void setYdshij(String ydshij) {
		this.ydshij = ydshij;
	}

	@Override
	public String toString() {
		return "Notice [row_Id=" + row_Id + ", content=" + content + ", ready=" + ready + ", objId=" + objId
				+ ", createTime=" + createTime + ", mbyhs=" + mbyhs + ", fsyh=" + fsyh + ", ydsj=" + ydsj + ", ydshij="
				+ ydshij + "]";
	}

}
