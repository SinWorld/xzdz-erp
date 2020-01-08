package com.edge.login.dao;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.user.entity.ERP_User;

public interface LoginDao {
	// 登录时检测用户名密码
	public ERP_User checkUser(@Param("loginName") String loginName, @Param("password") String password);
}
