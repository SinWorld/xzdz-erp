package com.edge.admin.Index.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.Index.service.inter.IndexService;

/**
 * 系统后台控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "/adminIndex")
public class AdminIndexController {

	@Resource
	private IndexService indexService;

	// 跳转至系统后端首页
	@RequestMapping(value = "/initIndex.do")
	public String initIndex() {
		return "admin/index/index";
	}

	// 初始化功能点数据
	@RequestMapping(value = "/privilegeList.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String privilegeList() {
		// new出JSONArray数组存储顶级权限
		JSONArray jsonArray = new JSONArray();
		// 得到所有的顶级权限
		List<ERP_FunctionPoint> trees = indexService.topFunctionPointList();
		Collections.sort(trees);
		// 遍历所有顶级功能点集合
		for (ERP_FunctionPoint tree : trees) {
			// new出map集合
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			// new出JSONArray数组存储二级功能点
			JSONArray jsonArrays = new JSONArray();
			// 向map中添加元素
			map.put("id", tree.getFp_Id());
			map.put("text", tree.getFp_Name());
			map.put("state", "close");
			map.put("url", tree.getFp_Url());
			// 查询当前功能点的二级功能点
			List<ERP_FunctionPoint> childrenTrees = indexService.childrenFList(tree.getFp_Id());
			// 遍历当前二级功能点
			for (ERP_FunctionPoint treePrivilege : childrenTrees) {
				// new出map集合
				Map<String, Object> childrenMap = new LinkedHashMap<String, Object>();
				// new出JSONArray数组存储三级功能点
				JSONArray sunJsonArray = new JSONArray();
				// 向map中添加元素
				childrenMap.put("id", treePrivilege.getFp_Id());
				childrenMap.put("text", treePrivilege.getFp_Name());
				childrenMap.put("state", "close");
				childrenMap.put("url", treePrivilege.getFp_Url());
				jsonArrays.add(childrenMap);
				// 查询当前权限的三级功能点
				List<ERP_FunctionPoint> sanjiQX = indexService.childrenFList(treePrivilege.getFp_Id());
				// 遍历三级权限
				for (ERP_FunctionPoint sjqx : sanjiQX) {
					// new出map集合
					Map<String, Object> sjMap = new LinkedHashMap<String, Object>();
					// 向map中添加元素
					sjMap.put("id", sjqx.getFp_Id());
					sjMap.put("text", sjqx.getFp_Name());
					sjMap.put("state", "close");
					sjMap.put("url", sjqx.getFp_Url());
					sunJsonArray.add(sjMap);
				}
				if (sunJsonArray.size() > 0 && sunJsonArray != null) {
					childrenMap.put("children", sunJsonArray);
				}
				if (jsonArrays.size() > 0 && jsonArrays != null) {
					map.put("children", jsonArrays);
				}
			}
			jsonArray.add(map);
		}
		return jsonArray.toJSONString();
	}

	// 密码修改
	@RequestMapping(value = "/initSecuritySetting.do")
	public String initSecuritySetting() {
		return "admin/user/securitySetting";
	}

}
