package com.edge.admin.permissionVerification.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.permissionVerification.services.inter.PermissionVerificationService;
import com.edge.admin.user.entity.ERP_User;

/**
 * 权限验证控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "PermissionVerification")
public class PermissionVerificationController {

	@Resource
	private PermissionVerificationService permissionVerificationService;

	// ajax进行权限验证
	@ResponseBody
	@RequestMapping(value = "/checkPermission.do")
	public String checkPermission(String fp_Url, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 获取当前登录系统的用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 根据url获得功能对象
		ERP_FunctionPoint functionPoint = permissionVerificationService.queryFunctionByUrl(fp_Url);
		// 用户所属的角色集合
		List<Integer> roleList = permissionVerificationService.userRoleList(user.getUserId());
		// 查询当前功能的所属角色集合
		List<Integer> fpRoleList = permissionVerificationService.fpRoleList(functionPoint.getFp_Id());
		boolean flag = false;
		for (Integer f : fpRoleList) {
			for (Integer r : roleList) {
				if (r.equals(f)) {
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

}
