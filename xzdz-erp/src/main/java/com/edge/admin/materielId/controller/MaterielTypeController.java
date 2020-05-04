package com.edge.admin.materielId.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.entity.MaterielType;
import com.edge.admin.materielId.service.inter.MaterielTypeService;
import com.google.gson.Gson;

/**
 * 物料Id类型控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materielType")
public class MaterielTypeController {

	@Resource
	private MaterielTypeService materielTypeService;

	// 初始化物料Id类型
	@RequestMapping(value = "/initMaterielType.do")
	@ResponseBody
	private String initMaterielType() {
		List<MaterielType> topWenJianJ = topWenJianJ();
		Gson gson = new Gson();
		String str = gson.toJson(topWenJianJ);
		return str.toString();
	}

	private List<MaterielType> topWenJianJ() {
		// 获得顶级节点
		List<MaterielType> topList = materielTypeService.allMaterielType();
		List<MaterielType> list = new ArrayList<MaterielType>();
		// 遍历该集合
		for (MaterielType t : topList) {
			// 根据集合主键查询子集和
			String id = String.valueOf(t.getParent_Id());
			if (id == "null") {
				String pid = null;
				if (StringUtils.isBlank(pid)) {
					list.add(findChildren(t, topList));
				}
			} else {
				if (StringUtils.isBlank(id)) {
					list.add(findChildren(t, topList));

				}
			}

		}
		return list;
	}

	// 递归查询子节点
	private MaterielType findChildren(MaterielType wjj, List<MaterielType> childrenList) {
		for (MaterielType c : childrenList) {
			if (c.getParent_Id() == wjj.getId()) {
				if (wjj.getChildren() == null) {
					wjj.setChildren(new ArrayList<MaterielType>());
				}
				wjj.getChildren().add(findChildren(c, childrenList));
			}
		}
		return wjj;
	}

	// 跳转至物料Id类型新增页面
	@RequestMapping(value = "/initSaveMaterielType.do")
	public String initSaveMaterielType(@RequestParam Integer id, Model model) {
		MaterielType materielType = materielTypeService.queryMaterielTypeById(id);
		model.addAttribute("materielType", materielType);
		return "applicationCenter/materielId/saveMaterielType";
	}

	// 新增物料Id类型
	@RequestMapping(value = "/saveMaterielType.do")
	public String saveWjj(MaterielType wjj, @RequestParam Integer flwjj, Model model) {
		// 新增文件夹
		wjj.setParent_Id(flwjj);
		materielTypeService.saveMaterielType(wjj);
		model.addAttribute("flag", true);
		return "applicationCenter/materielId/saveMaterielType";
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditMaterielType.do")
	public String initEditWJg(@RequestParam Integer id, Model model) {
		// 根据Id查询当前节点名称
		MaterielType wjj = materielTypeService.queryMaterielTypeById(id);
		// 得到上级文件夹的主键 根据该主键去查询上级文件夹名称
		// 如果该文件夹为顶级文件夹则上级文件夹主键为空
		if (wjj.getParent_Id() == null) {
			model.addAttribute("wjj", wjj);
			model.addAttribute("title", "请选择文件夹");
		} else {
			MaterielType sys_wjj = (MaterielType) materielTypeService.queryMaterielTypeById(wjj.getParent_Id());
			model.addAttribute("wjj", sys_wjj);
			model.addAttribute("title", sys_wjj.getTitle());
		}
		model.addAttribute("wj", wjj);
		return "applicationCenter/materielId/editMaterielType";
	}

	// 编辑页面初始化文件机构树
	@RequestMapping(value = "/orgWJJTree.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String initEditWjj() {
		// new出JSONArray数组对象
		JSONArray jsonArray = new JSONArray();
		// 准备文件夹列表数据，用于select框显示
		List<MaterielType> topList = materielTypeService.topMaterielTypeTree();
		List<MaterielType> treeList = this.getTreeList(topList, null);
		jsonArray.add(treeList);
		return jsonArray.toString();

	}

	private List<MaterielType> getTreeList(List<MaterielType> topList, Long removeId) {
		List<MaterielType> treeList = new ArrayList<MaterielType>();
		this.walkTree(topList, treeList, "┣", removeId, materielTypeService);
		return treeList;
	}

	/**
	 * 组织树形部门数据
	 */
	private void walkTree(Collection<MaterielType> topList, List<MaterielType> treeList, String prefix, Long removeId,
			MaterielTypeService materielTypeService) {
		for (MaterielType d : topList) {
			if (removeId != null && d.getId().equals(removeId)) {
				continue;
			}
			MaterielType copy = new MaterielType();
			copy.setId(d.getId());
			copy.setTitle((prefix + d.getTitle()));
			// 顶点
			treeList.add(copy);
			// 子树
			Integer dep_id = d.getId();
			List<MaterielType> children = materielTypeService.childrenMaterielTypeTree(dep_id);
			walkTree(children, treeList, "　" + prefix, removeId, materielTypeService);
		}
	}

	// 编辑物料Id类型
	@RequestMapping(value = "/editMaterielType.do")
	public String editMaterielType(MaterielType wjj, Model model) {
		materielTypeService.editMaterielType(wjj);
		model.addAttribute("flag", true);
		return "applicationCenter/wjg/editWjg";
	}

	// 删除物料Id类型
	@RequestMapping(value = "/deleteMaterielType.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteMaterielType(@RequestParam Integer id) {
		// new出JSONObject对象
		JSONObject jsonObject = new JSONObject();
		// 根据id查询当前节点
		MaterielType wjj = materielTypeService.queryMaterielTypeById(id);
		// 查询当前节点的子节点
		List<MaterielType> queryChildrenWJJTree = materielTypeService.childrenMaterielTypeTree(wjj.getId());
		if (queryChildrenWJJTree.size() == 0) {
			// 表示不存在子节点可直接删除
			materielTypeService.deleteMaterielTypeById(wjj.getId());
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
			jsonObject.put("infor", "当前节点存在子节点请先删除子节点");
		}
		return jsonObject.toString();
	}

	// 新增/编辑检测物料Id类型不重复
	@RequestMapping(value = "/checkMaterielType.do")
	@ResponseBody
	public String checkMaterielType(Integer parent_id, String title) {
		JSONObject jsonObject = new JSONObject();
		if (title != null && title != "") {
			MaterielType materielType = materielTypeService.checkMaterielType(parent_id, title.trim());
			if (materielType != null) {
				jsonObject.put("flag", false);
			} else {
				jsonObject.put("flag", true);
			}
			return jsonObject.toString();
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
