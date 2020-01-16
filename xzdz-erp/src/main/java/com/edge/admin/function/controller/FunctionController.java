package com.edge.admin.function.controller;

import java.util.ArrayList;
import java.util.Collection;
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
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.Index.service.inter.IndexService;
import com.edge.admin.function.entity.Function_QueryVo;
import com.edge.admin.function.service.inter.FunctionService;
import com.google.gson.Gson;

/**
 * 功能点控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "function")
public class FunctionController {

	@Resource
	private FunctionService functionService;

	@Resource
	private IndexService indexService;

	// 跳转至功能点列表页面
	@RequestMapping(value = "/initFunList.do")
	public String initFunList() {
		return "admin/function/functionList";
	}

	// easyUI 功能点列表查询
	@RequestMapping(value = "/funList.do")
	@ResponseBody
	public String funList(@RequestParam Integer page, Integer rows, String gnmc, String gndz, Integer sjgn) {
		// new出查询对象
		Function_QueryVo vo = new Function_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		if (gnmc != null && gnmc != "") {
			vo.setGnmc(gnmc.trim());
		}
		if (gndz != null && gndz != "") {
			vo.setGndz(gndz.trim());
		}
		if (sjgn != null) {
			vo.setSjgn(sjgn);
		}
		List<ERP_FunctionPoint> funList = functionService.functionList(vo);
		for (ERP_FunctionPoint f : funList) {
			if (f.getFp_parentId() != null) {
				ERP_FunctionPoint functionPoint = functionService.queryFunById(f.getFp_parentId());
				f.setParentName(functionPoint.getFp_Name());
			}
		}
		map.put("total", functionService.functionCount(vo));
		map.put("rows", funList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至功能点新增页面
	@RequestMapping(value = "/initSaveFunction.do")
	public String initSaveFunction() {
		return "admin/function/saveFunction";
	}

	// 新增、编辑时初始化上级功能的功能机构树
	@RequestMapping(value = "/orgFunction.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String orgFunction() {
		// new出JSONArray数组对象
		JSONArray jsonArray = new JSONArray();
		// 准备部门列表数据，用于select框显示
		List<ERP_FunctionPoint> topList = indexService.topFunctionPointList();
		List<ERP_FunctionPoint> treeList = this.getTreeList(topList, null);
		jsonArray.add(treeList);
		return jsonArray.toString();
	}

	private List<ERP_FunctionPoint> getTreeList(List<ERP_FunctionPoint> topList, Long removeId) {
		List<ERP_FunctionPoint> treeList = new ArrayList<ERP_FunctionPoint>();
		this.walkTree(topList, treeList, "┣", removeId, indexService);
		return treeList;
	}

	/**
	 * 组织树形部门数据
	 */
	private void walkTree(Collection<ERP_FunctionPoint> topList, List<ERP_FunctionPoint> treeList, String prefix,
			Long removeId, IndexService indexService) {
		for (ERP_FunctionPoint f : topList) {
			if (removeId != null && f.getFp_Id().equals(removeId)) {
				continue;
			}
			ERP_FunctionPoint copy = new ERP_FunctionPoint();
			copy.setFp_Id(f.getFp_Id());
			copy.setFp_Name(prefix + f.getFp_Name());
			// 顶点
			treeList.add(copy);
			// 子树
			Integer fp_Id = f.getFp_Id();
			List<ERP_FunctionPoint> children = indexService.childrenFList(fp_Id);
			walkTree(children, treeList, "　" + prefix, removeId, indexService);
		}
	}

	// 新增功能点
	@RequestMapping(value = "/saveFunction.do")
	public String saveFunction(ERP_FunctionPoint functionPoint, Model model) {
		functionPoint.setIs_Yc(false);
		functionPoint.setIs_Sc(false);
		functionService.saveFunction(functionPoint);
		model.addAttribute("flag", true);
		return "admin/function/saveFunction";
	}

	// 跳转至功能点编辑页面
	@RequestMapping(value = "/initEditFunction.do")
	public String initEditFunction(@RequestParam Integer fp_Id, Model model) {
		ERP_FunctionPoint functionPoint = functionService.queryFunById(fp_Id);
		model.addAttribute("functionPoint", functionPoint);
		return "admin/function/editFunction";
	}

	// 编辑功能点
	@RequestMapping(value = "/editFunction.do")
	public String editFunction(ERP_FunctionPoint functionPoint, Model model) {
		functionService.editFunction(functionPoint);
		model.addAttribute("flag", true);
		return "admin/function/editFunction";
	}

	// 删除功能点(逻辑删除)
	@RequestMapping(value = "/deleteFunction.do")
	@ResponseBody
	public String deleteFunction(Integer fp_Id) {
		JSONObject jsonObject = new JSONObject();
		// 查询当前功能点下是否存在子功能集合,若存在不允许直接删除,给出提示,否则可以删除
		List<ERP_FunctionPoint> list = indexService.childrenFList(fp_Id);
		ERP_FunctionPoint functionPoint = functionService.queryFunById(fp_Id);
		if (list.size() == 0 || list == null) {
			functionPoint.setIs_Sc(true);
			functionService.editFunction(functionPoint);
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 加载所有的功能点
	@RequestMapping(value = "/allFunction.do")
	@ResponseBody
	public String allFunction() {
		JSONArray jsonArray = new JSONArray();
		List<ERP_FunctionPoint> list = functionService.allFunction();
		for (ERP_FunctionPoint l : list) {
			jsonArray.add(l);
		}
		return jsonArray.toString();
	}
}
