package com.edge.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.login.service.inter.LoginService;

/**
 * 登录控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "login")
public class LoginController {

	@Resource
	private LoginService loginService;

	// 初始化登录界面
	@RequestMapping(value = "/initLogin.do")
	public String initLogin() {
		return "login/login";
	}

	// 用户登陆
	@RequestMapping(value = "/doLogin.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String doLogin(String loginName, String password, HttpServletRequest request) {
		// new出JSONObject对象
		JSONObject jsonObject = new JSONObject();
		// 得到session
		HttpSession session = request.getSession();
		if (loginName != null && password != null) {
			ERP_User user = loginService.checkUser(loginName.trim(), password.trim());
			// 查询到了该用户，用户名密码正确
			if (user != null) {
				// 将用户对象存入session
				session.setAttribute("user", user);
				/**
				 * 1.若登录用户为admin 则跳转至系统后台,否则跳转至系统前台
				 */
				if ("admin".equals(user.getLoginName().trim())) {
					jsonObject.put("flag", "ht");
				} else {
					// 用于用户在不退出登录的情况下控制加载权限菜单
					session.setAttribute("kg", true);
					jsonObject.put("flag", "qt");
				}
				return jsonObject.toString();
			} else {
				// 说明无该用户，用户名或密码错误回转至登录页，并给出提示
				jsonObject.put("flag", "fail");
				return jsonObject.toString();
			}
		} else {
			return null;
		}
	}

	// 退出系统
	@RequestMapping(value = "/exit.do")
	public ModelAndView exit(HttpServletRequest request, ModelAndView mv) {
		HttpSession session = request.getSession();
		// 清除session
		session.invalidate();
		mv.setViewName("redirect:initLogin.do");
		return mv;
	}
}
