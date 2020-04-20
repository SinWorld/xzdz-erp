package com.edge.applicationCenter.wjg.entity;

import java.util.Date;

/**
 * 高级搜索查询实体类
 * 
 * @author NingCG
 *
 */
public class Wjg_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	// 文件名
	private String wenJianM;
	// 所属文件
	private Integer wenJianJDM;
	// 上传用户
	private Integer userDM;
	// 提交时间 时间段
	private Date date;
	private Date date2;

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

	public String getWenJianM() {
		return wenJianM;
	}

	public void setWenJianM(String wenJianM) {
		this.wenJianM = wenJianM;
	}

	public Integer getWenJianJDM() {
		return wenJianJDM;
	}

	public void setWenJianJDM(Integer wenJianJDM) {
		this.wenJianJDM = wenJianJDM;
	}

	public Integer getUserDM() {
		return userDM;
	}

	public void setUserDM(Integer userDM) {
		this.userDM = userDM;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	@Override
	public String toString() {
		return "Wjg_QueryVo [page=" + page + ", rows=" + rows + ", wenJianM=" + wenJianM + ", wenJianJDM=" + wenJianJDM
				+ ", userDM=" + userDM + ", date=" + date + ", date2=" + date2 + "]";
	}

}
