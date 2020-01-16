package com.edge.admin.privilege.controller;

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
import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.privilege.entity.Role_Privilege;
import com.edge.admin.privilege.service.inter.PrivilegeService;
import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.role.service.inter.ERP_RoleService;

/**
 * 权限加载控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "privilege")
public class PrivilegeController {
	@Resource
	private ERP_RoleService erp_RoleService;

	@Resource
	private PrivilegeService privilegeService;

	// 跳转至功能点列表页面
	@RequestMapping(value = "/initPrivilegeList.do")
	public String initPrivilege(@RequestParam Integer roleId, Model model) {
		// 根据Id查询该角色
		ERP_Roles role = erp_RoleService.queryRoleById(roleId);
		model.addAttribute("role", role);
		return "admin/privilege/privilegeList";
	}

	// 初始化功能点数据
	@RequestMapping(value = "/privilegeList.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String privilege() {
		// new出JSONArray数组存储顶级功能点
		JSONArray jsonArray = new JSONArray();
		// 得到所有的顶级功能点
		List<ERP_FunctionPoint> trees = privilegeService.topFunctionPointList();
		// 遍历所有顶级功能点集合
		for (ERP_FunctionPoint tree : trees) {
			// new出map集合
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			// new出JSONArray数组存储二级功能点
			JSONArray jsonArrays = new JSONArray();
			// 向map中添加元素
			map.put("id", tree.getFp_Id());
			map.put("text", tree.getFp_Name());
			map.put("state", "open");
			// 查询当前功能点的二级功能点
			List<ERP_FunctionPoint> childrenTrees = privilegeService.childrenFList(tree.getFp_Id());
			// 遍历当前二级功能点
			for (ERP_FunctionPoint treePrivilege : childrenTrees) {
				// new出map集合
				Map<String, Object> childrenMap = new LinkedHashMap<String, Object>();
				// new出JSONArray数组存储三级功能点
				JSONArray sunJsonArray = new JSONArray();
				// 向map中添加元素
				childrenMap.put("id", treePrivilege.getFp_Id());
				childrenMap.put("text", treePrivilege.getFp_Name());
				childrenMap.put("state", "open");
				jsonArrays.add(childrenMap);
				// 查询当前功能点的三级功能点
				List<ERP_FunctionPoint> sanjiQX = privilegeService.childrenFList(treePrivilege.getFp_Id());
				// 遍历三级功能点
				for (ERP_FunctionPoint sjqx : sanjiQX) {
					// new出map集合
					Map<String, Object> sjMap = new LinkedHashMap<String, Object>();
					// 向map中添加元素
					sjMap.put("id", sjqx.getFp_Id());
					sjMap.put("text", sjqx.getFp_Name());
					sjMap.put("state", "open");
					sunJsonArray.add(sjMap);
				}
				childrenMap.put("children", sunJsonArray);
				map.put("children", jsonArrays);
			}
			jsonArray.add(map);
		}
		return jsonArray.toJSONString();
	}

	// 当某个角色已勾选功能时页面加载时自动勾选所选中的功能数据
	@RequestMapping(value = "/defaultPrivilege.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String defaultPrivilege(@RequestParam Integer roleId) {
		// new出JSONArray数组对象
		JSONArray jsonArray = new JSONArray();
		// 根据角色主键去查询该角色所有的功能点集合
		List<Role_Privilege> rolePrivilegeList = privilegeService.rolePrivilegeLists(roleId);
		// 遍历该集合将对象添加到JSONArray数组中
		for (Role_Privilege role_Privilege : rolePrivilegeList) {
			jsonArray.add(role_Privilege);
		}
		return jsonArray.toString();

	}

	// 设置功能点
	@RequestMapping(value = "/setPrivilegeUI.do")
	public String setPrivilege(@RequestParam String selectId, Integer roleId, Model model) {
		// 得到功能数组
		String selectIds[] = selectId.split(",");
		// 当前是否配置了功能点
		int hashCode = selectId.hashCode();
		// 1.查询该角色有无该功能的功能点
		List<Role_Privilege> rolePrivilegeList = privilegeService.rolePrivilegeLists(roleId);
		// 2.若有则将该角色的所有的功能点清楚，重新添加
		if (rolePrivilegeList.size() > 0 && null != rolePrivilegeList) {
			privilegeService.deleteRolePrivilege(roleId);
			if (hashCode != 0) {
				// 遍历该数组
				for (String id : selectIds) {
					// new出Role_Privilege对象
					Role_Privilege role_Privilege = new Role_Privilege();
					// 设置其属性
					role_Privilege.setFp_Id(Integer.parseInt(id.trim()));
					role_Privilege.setRole_Id(roleId);
					privilegeService.saveRolePrivilege(role_Privilege);
				}
			}
		} else {
			if (hashCode != 0) {
				// 3.若无则直接添加
				for (String id : selectIds) {
					// new出Role_Privilege对象
					Role_Privilege role_Privilege = new Role_Privilege();
					// 设置其属性
					role_Privilege.setFp_Id(Integer.parseInt(id.trim()));
					role_Privilege.setRole_Id(roleId);
					privilegeService.saveRolePrivilege(role_Privilege);
				}
			}
		}
		model.addAttribute("flag", true);
		return "admin/privilege/privilegeList";
	}
}
