package com.edge.index.controller;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.notice.entity.Notice_QueryVo;
import com.edge.currency.notice.service.inter.NoticeService;
import com.edge.index.service.inter.QTIndexService;

/**
 * 系统前台首页控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "index")
public class IndexController {

	@Resource
	private QTIndexService qtIndexService;

	@Resource
	private NoticeService noticeService;

	// 跳转至登录首页
	@RequestMapping(value = "/initIndex.do")
	public String initIndex(HttpServletRequest request, Model model) {
		// 从request作用域中得到session
		HttpSession session = request.getSession();
		// 从session中得到当前登录用户的主键
		ERP_User user = (ERP_User) session.getAttribute("user");
		Boolean flag = (Boolean) session.getAttribute("kg");
		this.userAllPrivilege(user.getUserId(), model, session, flag);
		Notice_QueryVo vo = new Notice_QueryVo();
		vo.setMbyhs(String.valueOf(user.getUserId()));
		model.addAttribute("wdCount", noticeService.noticeWdCount(vo));
		return "index/index";

	}

	// 用户登录系统查询当前用户所拥有的权限
	public void userAllPrivilege(Integer userId, Model model, HttpSession session, Boolean flag) {
		// 查询当前登录用户的所有顶级权限权限
		List<ERP_FunctionPoint> userPrivilegeList = qtIndexService.userPrivilegeList(userId);
		TreeSet<ERP_FunctionPoint> topList = new TreeSet<ERP_FunctionPoint>();
		// new出JSONArry对象用于存储所有的三级权限
		JSONArray jsonArray = new JSONArray();
		// 遍历顶级权限集合
		// 查询当前登录用户的所有顶级权限下的二级子权限
		for (ERP_FunctionPoint privilege : userPrivilegeList) {
			topList.add(privilege);
			List<ERP_FunctionPoint> ejChildrenList = qtIndexService.ejChildrenList(userId, privilege.getFp_Id());
			// 遍历二级子权限集合 将二级子权限添加到权限对象的set集合中
			for (ERP_FunctionPoint ej : ejChildrenList) {
				if (ej.getIs_Yc() == false) {
					privilege.setChildren(ej);
				}
				// 得到用户二级下所有的三级权限
				List<ERP_FunctionPoint> sjChildrenList = qtIndexService.ejChildrenList(userId, ej.getFp_Id());
				// 遍历所有的三级权限添加到JSONArray数组中
				for (ERP_FunctionPoint sjqx : sjChildrenList) {
					jsonArray.add(sjqx);
				}
			}
		}
		// 将三级权限存入session中
		if (flag) {
			session.setAttribute("sjqxs", jsonArray.toString().trim());
			session.setAttribute("kg", false);
		}
		// 用于用户在不退出登录的情况下控制加载权限菜单
		model.addAttribute("privilegeTopList", topList);
	}
}
