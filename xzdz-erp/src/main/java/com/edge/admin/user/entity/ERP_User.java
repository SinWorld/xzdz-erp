package com.edge.admin.user.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户实体类
 * 
 * @author NingCG
 *
 */
public class ERP_User {
	private Integer userId;// 用户主键
	private Integer dep_Id;// 部门
	private Integer post_Id;// 岗位
	private String loginName;// 登录名
	private String password;// 密码
	private String userName;// 用户名
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;// 出生日期
	private String gender;// 性别(男/女)
	private String nation;// 民族
	private String duties;// 职务
	private String phoneNumber;// 手机号
	private String telPhone;// 电话号码
	private String email;// 邮箱
	private String QQNumber;// qq号
	private String weChat;// 微信号
	private Boolean flag;// 是否禁用
	private String education;// 学历
	private String remarks;// 备注
	private String photoName;// 头像

	// 辅助属性
	private String dep_Name;// 部门名称
	private String post_Name;// 岗位名称
	private String User_birthday;// 生日

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDep_Id() {
		return dep_Id;
	}

	public void setDep_Id(Integer dep_Id) {
		this.dep_Id = dep_Id;
	}

	public Integer getPost_Id() {
		return post_Id;
	}

	public void setPost_Id(Integer post_Id) {
		this.post_Id = post_Id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQQNumber() {
		return QQNumber;
	}

	public void setQQNumber(String qQNumber) {
		QQNumber = qQNumber;
	}

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDep_Name() {
		return dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		this.dep_Name = dep_Name;
	}

	public String getPost_Name() {
		return post_Name;
	}

	public void setPost_Name(String post_Name) {
		this.post_Name = post_Name;
	}

	public String getUser_birthday() {
		return User_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		User_birthday = user_birthday;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	@Override
	public String toString() {
		return "ERP_User [userId=" + userId + ", dep_Id=" + dep_Id + ", post_Id=" + post_Id + ", loginName=" + loginName
				+ ", password=" + password + ", userName=" + userName + ", birthday=" + birthday + ", gender=" + gender
				+ ", nation=" + nation + ", duties=" + duties + ", phoneNumber=" + phoneNumber + ", telPhone="
				+ telPhone + ", email=" + email + ", QQNumber=" + QQNumber + ", weChat=" + weChat + ", flag=" + flag
				+ ", education=" + education + ", remarks=" + remarks + ", photoName=" + photoName + ", dep_Name="
				+ dep_Name + ", post_Name=" + post_Name + ", User_birthday=" + User_birthday + "]";
	}

}
