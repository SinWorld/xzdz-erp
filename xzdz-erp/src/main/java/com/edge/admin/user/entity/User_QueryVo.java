package com.edge.admin.user.entity;

import java.util.Date;

/**
 * 查询对象
 * 
 * @author NingCG
 *
 */
public class User_QueryVo {
	private Integer page;// 当前页
	private Integer rows;// 每页显示数量

	// 查询属性
	private String userName;// 姓名
	private String gender;// 性别
	private Integer department;// 所属部门
	private String zw;// 职务
	private String sjh;// 手机号
	private String yx;// 邮箱
	private String xl;// 学历
	private Date Date;// 开始时间
	private Date Date2;// 结束时间

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public String getSjh() {
		return sjh;
	}

	public void setSjh(String sjh) {
		this.sjh = sjh;
	}

	public String getYx() {
		return yx;
	}

	public void setYx(String yx) {
		this.yx = yx;
	}

	public String getXl() {
		return xl;
	}

	public void setXl(String xl) {
		this.xl = xl;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public Date getDate2() {
		return Date2;
	}

	public void setDate2(Date date2) {
		Date2 = date2;
	}

	@Override
	public String toString() {
		return "User_QueryVo [page=" + page + ", rows=" + rows + ", userName=" + userName + ", gender=" + gender
				+ ", department=" + department + ", zw=" + zw + ", sjh=" + sjh + ", yx=" + yx + ", xl=" + xl + ", Date="
				+ Date + ", Date2=" + Date2 + "]";
	}

}
