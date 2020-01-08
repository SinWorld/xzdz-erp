package com.edge.login.service.inter;

import com.edge.admin.user.entity.ERP_User;

public interface LoginService {

	// 登录时检测用户名密码
	public ERP_User checkUser(String loginName, String password);
}
