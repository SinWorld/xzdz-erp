package com.edge.login.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.user.entity.ERP_User;
import com.edge.login.dao.LoginDao;
import com.edge.login.service.inter.LoginService;

/**
 * 登录业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private LoginDao loginDao;

	public ERP_User checkUser(String loginName, String password) {
		return loginDao.checkUser(loginName, password);
	}

}
