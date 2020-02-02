package com.edge.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.edge.admin.user.entity.ERP_User;

/**
 * 登录权限认证-拦截器
 * 
 * @author NingCG
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		// 获取请求的URL
		StringBuffer url = request.getRequestURL();
		// URL:除了login.jsp是可以公开访问的，其余的URL都要进行拦截控制
		if (url.indexOf("/login/initLogin.do") > 0 || url.indexOf("/login/doLogin.do") > 0
				|| url.indexOf("/login/exit.do") > 0) {
			return true;
		}
		// 获取session
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 判断session里是不是有登录信息
		if (user != null) {
			return true;
		}
		request.setAttribute("msg", "您还没有登录，请先登录！");
		request.getRequestDispatcher("/WEB-INF/pages/login/login.jsp").forward(request, response);
		return false;

	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

}
