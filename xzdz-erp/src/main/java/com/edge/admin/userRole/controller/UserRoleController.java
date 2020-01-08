package com.edge.admin.userRole.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.admin.userRole.entity.UserRole;
import com.edge.admin.userRole.service.inter.UserRoleService;

/**
 * 用户角色控制层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "userRole")
public class UserRoleController {
	@Resource
	private UserRoleService userRoleService;

	@Resource
	private ERP_UserService userService;

	// 跳转至角色设置页面
	@RequestMapping(value = "/initRoleList.do")
	public String initRoleList(@RequestParam Integer userId, Model model) {
		// 根据用户主键查询用户
		ERP_User user = userService.queryUserById(userId);
		model.addAttribute("user_id", userId);
		model.addAttribute("userName", user.getUserName());
		return "admin/user/setRoleList";
	}

	// 初始化角色数据
	@RequestMapping(value = "/allRoleList.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String allRoleList() {
		// new出JSONArray数组存储顶级权限
		JSONArray jsonArray = new JSONArray();
		// 取到所有的角色集合
		List<ERP_Roles> trees = userRoleService.roleList();
		// 遍历所有顶级权限集合
		for (ERP_Roles tree : trees) {
			// new出map集合
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			// 向map中添加元素
			map.put("id", tree.getRole_Id());
			map.put("text", tree.getRole_Name());
			map.put("state", "open");
			jsonArray.add(map);
		}
		return jsonArray.toJSONString();
	}

	// 当某个用户已勾选角色时页面加载时自动勾选所选中的功能数据
	@RequestMapping(value = "/defaultRole.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String defaultPrivilege(@RequestParam Integer userId) {
		// new出JSONArray数组对象
		JSONArray jsonArray = new JSONArray();
		// 根据用户主键去查询该用户所有的角色集合
		List<UserRole> userRoleList = userRoleService.userRoleList(userId);
		// 遍历该集合将对象添加到JSONArray数组中
		for (UserRole user_Role : userRoleList) {
			jsonArray.add(user_Role);
		}
		return jsonArray.toString();

	}

	// 为用户授予角色
	@RequestMapping(value = "/setUserRole.do")
	public String setUserRole(@RequestParam String selectId, Integer userId, Model model) {
		// 将角色字符串转为数组
		String roleIds[] = selectId.split(",");
		// 当前是授予了角色
		int hashCode = selectId.hashCode();
		// 1.查询该用户有无该角色的
		List<UserRole> userRoleList = userRoleService.userRoleList(userId);
		// 2.若有则将该用户的所有的角色清除，重新添加
		if (userRoleList.size() > 0 && null != userRoleList) {
			userRoleService.deleteUserRole(userId);
			if (hashCode != 0) {
				// 遍历该数组
				for (String id : roleIds) {
					// new出UserRole对象
					UserRole userRole = new UserRole();
					userRole.setRole_Id(Integer.parseInt(id.trim()));
					userRole.setUser_Id(userId);
					userRoleService.saveUserRole(userRole);
				}
			}
		} else {
			if (hashCode != 0) {
				// 3.若无则直接添加
				// 遍历该数组
				for (String id : roleIds) {
					// new出UserRole对象
					UserRole userRole = new UserRole();
					userRole.setRole_Id(Integer.parseInt(id.trim()));
					userRole.setUser_Id(userId);
					userRoleService.saveUserRole(userRole);
				}
			}
		}
		model.addAttribute("flag", true);
		return "admin/user/setRoleList";
	}

}
