package com.edge.admin.role.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.role.entity.ERP_Roles;
import com.edge.admin.role.entity.Role_QueryVo;
import com.edge.admin.role.service.inter.ERP_RoleService;
import com.google.gson.Gson;

/**
 * 角色控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "role")
public class ERP_RoleController {
	@Resource
	private ERP_RoleService erp_RoleService;

	// 跳转至角色列表页面
	@RequestMapping(value = "/initRoleList.do")
	public String initRoleList() {
		return "admin/role/roleList";
	}

	// easyUI 角色列表查询
	@RequestMapping(value = "/roleList.do")
	@ResponseBody
	public String roleList(@RequestParam Integer page, Integer rows, String roleName, String roleInfor) {
		// new出查询对象
		Role_QueryVo vo = new Role_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows+1);
		vo.setRows(page*rows);
		if (roleName != null && roleName != "") {
			vo.setRoleName(roleName.trim());
		}
		if (roleInfor != null && roleInfor != "") {
			vo.setRoleInfor(roleInfor.trim());
		}
		List<ERP_Roles> roleList = erp_RoleService.roleList(vo);
		map.put("total", erp_RoleService.roleCount(vo));
		map.put("rows", roleList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至角色新增页面
	@RequestMapping(value = "/initSaveRole.do")
	public String initSaveRole() {
		return "admin/role/saveRole";
	}

	// 新增角色
	@RequestMapping(value = "/saveRoles.do")
	public String saveRoles(ERP_Roles roles, Model model) {
		roles.setFlag(false);
		erp_RoleService.saveRole(roles);
		model.addAttribute("flag", true);
		return "admin/role/saveRole";
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditRole.do")
	public String initEditRole(@RequestParam Integer role_Id, Model model) {
		ERP_Roles roles = erp_RoleService.queryRoleById(role_Id);
		model.addAttribute("role", roles);
		return "admin/role/editRole";
	}

	// 编辑角色
	@RequestMapping(value = "/editRole.do")
	public String editRole(ERP_Roles roles, Model model) {
		erp_RoleService.editRole(roles);
		model.addAttribute("flag", true);
		return "admin/role/editRole";
	}

	// 删除角色(逻辑删除)
	@RequestMapping(value = "/deletRole.do")
	@ResponseBody
	public String deletRole(Integer role_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Roles roles = erp_RoleService.queryRoleById(role_Id);
		roles.setFlag(true);
		erp_RoleService.deleteRole(roles);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 批量删除(逻辑删除)
	@RequestMapping(value = "/batchDeleteRole.do")
	@ResponseBody
	public String batchDeleteRole(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] userIds = ids.split(",");
		// 遍历该数组
		for (String id : userIds) {
			// 根据Id获得角色对象
			ERP_Roles role = erp_RoleService.queryRoleById(Integer.parseInt(id.trim()));
			role.setFlag(true);
			// 进行删除
			erp_RoleService.deleteRole(role);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}
}
