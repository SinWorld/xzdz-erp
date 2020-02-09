package com.edge.admin.company.controller;

import java.util.Calendar;
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
import com.edge.admin.company.entity.Company_QueryVo;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;
import com.google.gson.Gson;

/**
 * 单位控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "company")
public class CompanyController {
	@Resource
	private CompanyService companyService;

	// 跳转至单位列表页面
	@RequestMapping(value = "/initCompanyList.do")
	public String initCompanyList() {
		return "admin/company/companyList";
	}

	// 单位列表查询
	@RequestMapping(value = "/companyList.do")
	@ResponseBody
	public String customerList(@RequestParam Integer page, Integer rows, String dwmc, String zcdz, String bgdz,
			String xydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Company_QueryVo vo = new Company_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		if (dwmc != null && dwmc != "") {
			vo.setDwmc(dwmc.trim());
		}
		if (zcdz != null && zcdz != "") {
			vo.setZcdz(zcdz.trim());
		}
		if (bgdz != null && bgdz != "") {
			vo.setBgdz(bgdz.trim());
		}
		if (xydm != null && xydm != "") {
			vo.setXydm(xydm.trim());
		}
		if (fddbr != null && fddbr != "") {
			vo.setFddbr(fddbr.trim());
		}
		if (khh != null && khh != "") {
			vo.setKhh(khh.trim());
		}
		if (dh != null && dh != "") {
			vo.setDh(dh.trim());
		}
		List<ERP_Our_Unit> unitList = companyService.unitList(vo);
		map.put("total", companyService.unitCount(vo));
		map.put("rows", unitList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至单位新增页面
	@RequestMapping(value = "/initSaveCompany.do")
	public String initSaveCompany() {
		return "admin/company/saveCompany";
	}

	// 新增单位
	@RequestMapping(value = "/saveCompany.do")
	public String saveCompany(ERP_Our_Unit unit, Model model) {
		unit.setUnit_Code(this.dwbh());
		companyService.saveUnit(unit);
		model.addAttribute("flag", true);
		return "admin/company/saveCompany";
	}

	// 生成单位编号
	private String dwbh() {
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
		String bh = "DW" + "-" + time + x;
		return bh;
	}

	// 跳转至单位编辑页面
	@RequestMapping(value = "/initEditCompany.do")
	public String initEditCompany(@RequestParam Integer unit_Id, Model model) {
		ERP_Our_Unit unit = companyService.queryUnitById(unit_Id);
		model.addAttribute("unit", unit);
		return "admin/company/editCompany";
	}

	// 编辑操作
	@RequestMapping(value = "/editCompany.do")
	public String editCompany(ERP_Our_Unit unit, Model model) {
		companyService.editUnit(unit);
		model.addAttribute("flag", true);
		return "admin/company/editCompany";
	}

	// 查看操作
	@RequestMapping(value = "/showCompany.do")
	public String showCompany(@RequestParam Integer unit_Id, Model model) {
		ERP_Our_Unit unit = companyService.queryUnitById(unit_Id);
		model.addAttribute("unit", unit);
		return "admin/company/showCompany";
	}

	// 删除操作
	@RequestMapping(value = "/deleteCompany.do")
	@ResponseBody
	public String deleteCompany(Integer unit_Id) {
		JSONObject jsonObject = new JSONObject();
		companyService.deleteUnit(unit_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 批量删除
	@RequestMapping(value = "/batchDeleteCompany.do")
	@ResponseBody
	public String batchDeleteCompany(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (int i = 0; i < depIds.length; i++) {
			companyService.deleteUnit(Integer.parseInt(depIds[i]));
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}
}
