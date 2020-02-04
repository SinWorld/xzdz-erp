package com.edge.admin.department.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.department.entity.Dep_QueryVo;
import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.department.entity.TreeNode;
import com.edge.admin.department.service.inter.ERP_DepartmentService;
import com.google.gson.Gson;

/**
 * 部门控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "department")
public class ERP_DepartmentController {
	@Resource
	private ERP_DepartmentService erp_DepartmentService;

	// 跳转至部门列表页面
	@RequestMapping(value = "/initDepList.do")
	public String initDepList() {
		return "admin/department/departmentList";
	}

	// easyUI 部门列表查询
	@RequestMapping(value = "/departmentList.do")
	@ResponseBody
	public String departmentList(@RequestParam Integer page, Integer rows, String id, boolean flag, String bmmc,
			Integer sjbmdm, String bmms) {
		// new出查询对象
		Dep_QueryVo vo = new Dep_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		if (bmmc != null && bmmc != "") {
			vo.setBmmc(bmmc.trim());
		}
		if (sjbmdm != null) {
			vo.setSjbmdm(sjbmdm);
		}
		if (bmms != null && bmms != "") {
			vo.setBmms(bmms.trim());
		}
		if (id == null || id == "") {
			List<ERP_Department> departmentList = erp_DepartmentService.departmentList(vo);
			// 遍历该集合
			for (ERP_Department erp_Department : departmentList) {
				if (erp_Department.getDep_parentId() != null) {
					ERP_Department department = erp_DepartmentService.queryDepById(erp_Department.getDep_parentId());
					erp_Department.setParentDepName(department.getDep_Name());
				}

			}
			Collections.sort(departmentList);
			map.put("total", erp_DepartmentService.queryDepCount(vo));
			map.put("rows", departmentList);
			String json = gson.toJson(map);
			return json.toString();
		} else {
			if (flag) {
				List<ERP_Department> departments = new ArrayList<ERP_Department>();
				ERP_Department department = erp_DepartmentService.queryDepById(Integer.parseInt(id.trim()));
				if (department.getDep_parentId() != null) {
					ERP_Department parentDep = erp_DepartmentService.queryDepById(department.getDep_parentId());
					department.setParentDepName(parentDep.getDep_Name());
				}
				departments.add(department);
				Collections.sort(departments);
				map.put("total", departments.size());
				map.put("rows", departments);
				String json = gson.toJson(map);
				return json.toString();
			} else {
				ERP_Department department = erp_DepartmentService.queryDepById(Integer.parseInt(id.trim()));
				List<ERP_Department> childrenDeps = erp_DepartmentService.childrenDeps(department.getDep_Id());
				for (ERP_Department erp_Department : childrenDeps) {
					erp_Department.setParentDepName(department.getDep_Name());
				}
				childrenDeps.add(department);
				Collections.sort(childrenDeps);
				map.put("total", childrenDeps.size());
				map.put("rows", childrenDeps);
				String json = gson.toJson(map);
				return json.toString();
			}
		}
	}

	// 初始化部门Tree
	@RequestMapping(value = "/initDepTree.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String initDepTree() {
		List<TreeNode> topWenJianJ = topWenJianJ();
		Gson gson = new Gson();
		String str = gson.toJson(topWenJianJ);
		return str.toString();
	}

	private List<TreeNode> topWenJianJ() {
		// 查询所有未删除的部门
		List<ERP_Department> topList = erp_DepartmentService.queryAllDepartment();
		List<TreeNode> list = new ArrayList<TreeNode>();
		List<TreeNode> tList = new ArrayList<TreeNode>();
		for (ERP_Department top : topList) {
			TreeNode t = new TreeNode();
			t.setId(top.getDep_Id());
			t.setText(top.getDep_Name());
			t.setParent_Id(top.getDep_parentId());
			tList.add(t);
		}
		// 遍历该集合
		for (TreeNode t : tList) {
			// 根据集合主键查询子集和
			String id = String.valueOf(t.getParent_Id());
			if (id == "null") {
				String pid = null;
				if (StringUtils.isBlank(pid)) {
					list.add(findChildren(t, tList));
				}
			} else {
				if (StringUtils.isBlank(id)) {
					list.add(findChildren(t, tList));

				}
			}

		}
		return list;
	}

	// 递归查询子节点
	private TreeNode findChildren(TreeNode treeNode, List<TreeNode> childrenList) {
		for (TreeNode t : childrenList) {
			if (t.getParent_Id() == treeNode.getId()) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<TreeNode>());
				}
				treeNode.getChildren().add(findChildren(t, childrenList));
			}
		}
		return treeNode;
	}

	// 新增、编辑时初始化上级部门的部门机构树
	@RequestMapping(value = "/orgDepartment.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String orgDepartment() {
		// new出JSONArray数组对象
		JSONArray jsonArray = new JSONArray();
		// 准备部门列表数据，用于select框显示
		List<ERP_Department> topList = erp_DepartmentService.topDepList();
		List<ERP_Department> treeList = this.getTreeList(topList, null);
		jsonArray.add(treeList);
		return jsonArray.toString();
	}

	private List<ERP_Department> getTreeList(List<ERP_Department> topList, Long removeId) {
		List<ERP_Department> treeList = new ArrayList<ERP_Department>();
		this.walkTree(topList, treeList, "┣", removeId, erp_DepartmentService);
		return treeList;
	}

	/**
	 * 组织树形部门数据
	 */
	private void walkTree(Collection<ERP_Department> topList, List<ERP_Department> treeList, String prefix,
			Long removeId, ERP_DepartmentService erp_DepartmentService) {
		for (ERP_Department d : topList) {
			if (removeId != null && d.getDep_Id().equals(removeId)) {
				continue;
			}
			ERP_Department copy = new ERP_Department();
			copy.setDep_Id(d.getDep_Id());
			copy.setDep_Name(prefix + d.getDep_Name());
			// 顶点
			treeList.add(copy);
			// 子树
			Integer dep_id = d.getDep_Id();
			List<ERP_Department> children = erp_DepartmentService.childrenDeps(dep_id);
			walkTree(children, treeList, "　" + prefix, removeId, erp_DepartmentService);
		}
	}

	// 跳转至新增部门页面
	@RequestMapping(value = "/initSaveDepartment.do")
	public String initSaveDepartment() {
		return "admin/department/saveDepartment";
	}

	// 新增部门
	@RequestMapping(value = "/saveDepartment.do")
	public String saveDepartment(ERP_Department department, Model model) {
		department.setDep_Code(this.bianHao());
		department.setDep_flag(false);
		erp_DepartmentService.saveDepartment(department);
		model.addAttribute("flag", true);
		return "admin/department/saveDepartment";
	}

	// 生产部门编码
	// 生成编号
	private String bianHao() {
		// 1.设置项目编号 编号规则为’P’+年、月、日、时、分、秒+六位随机数 如：P20190604172610123456
		// 获取当前系统时间 并获取年月日时分秒
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String Hourse = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		if (now.get(Calendar.HOUR_OF_DAY) <= 9) {
			Hourse = 0 + Hourse;
		}
		String minute = String.valueOf(now.get(Calendar.MINUTE));
		if (now.get(Calendar.MINUTE) <= 9) {
			minute = 0 + minute;
		}
		String second = String.valueOf(now.get(Calendar.SECOND));
		if (now.get(Calendar.SECOND) <= 9) {
			second = 0 + second;
		}
		// 产生六位随机数
		int a = (int) ((Math.random() * 9 + 1) * 100000);
		String x = String.valueOf(a);
		String time = year + month + day + Hourse + minute + second;
		String bh = "DEP" + "-" + time + x;
		return bh;
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditDepartment.do")
	public String initEditDepartment(@RequestParam Integer dep_Id, Model model) {
		ERP_Department department = erp_DepartmentService.queryDepById(dep_Id);
		model.addAttribute("department", department);
		return "admin/department/editDepartment";
	}

	// 编辑部门
	@RequestMapping(value = "/editDepartment.do")
	public String editDepartment(ERP_Department department, Model model) {
		erp_DepartmentService.editDepartment(department);
		model.addAttribute("flag", true);
		return "admin/department/editDepartment";
	}

	// 删除部门(逻辑删除)
	@RequestMapping(value = "/deleteDepartment.do")
	@ResponseBody
	public String deleteDepartment(Integer dep_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Department department = erp_DepartmentService.queryDepById(dep_Id);
		/**
		 * 1.查询当前部门下是否存在子部门,若存在则不允许删除给出提示,若不存在则直接删除
		 * 
		 */
		// 根据当前部门对象查询子部门集合
		List<ERP_Department> childrenDeps = erp_DepartmentService.childrenDeps(department.getDep_Id());
		if (childrenDeps.size() > 0) {
			jsonObject.put("flag", false);
			jsonObject.put("infor", "当前部门下存在子部门,不允许删除!!!");
		} else {
			department.setDep_flag(true);
			erp_DepartmentService.deleteDepartment(department);
			jsonObject.put("flag", true);
		}
		return jsonObject.toString();
	}

	// 批量删除(逻辑删除)
	@RequestMapping(value = "/batchDeleteDepartment.do")
	@ResponseBody
	public String batchDeleteDepartment(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			// 进行删除
			ERP_Department department = erp_DepartmentService.queryDepById(Integer.parseInt(id.trim()));
			department.setDep_flag(true);
			erp_DepartmentService.deleteDepartment(department);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
