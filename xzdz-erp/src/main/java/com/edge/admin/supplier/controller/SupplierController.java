package com.edge.admin.supplier.controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.entity.Supplier_QueryVo;
import com.edge.admin.supplier.service.inter.SupplierService;
import com.google.gson.Gson;

/**
 * 供应商控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "supplier")
public class SupplierController {

	@Resource
	private SupplierService supplierService;

	// 跳转至供应商列表页面
	@RequestMapping(value = "/initSupplierList.do")
	public String initSupplierList() {
		return "admin/supplier/supplierList";
	}

	// easyUI 列表查询
	@RequestMapping(value = "/supplierList.do")
	@ResponseBody
	public String supplierList(@RequestParam Integer page, Integer rows, String gysmc, String zcdz, String bgdz,
			String shtyxydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Supplier_QueryVo vo = new Supplier_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		vo.setGysmc(gysmc);
		vo.setZcdz(zcdz);
		vo.setBgdz(bgdz);
		vo.setShtyxydm(shtyxydm);
		vo.setFddbr(fddbr);
		vo.setKhh(khh);
		vo.setDh(dh);
		map.put("total", supplierService.supplierCount(vo));
		map.put("rows", supplierService.supplierList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至供应商新增页面
	@RequestMapping(value = "/initSaveSupplier.do")
	public String initSaveSupplier() {
		return "admin/supplier/saveSupplier";
	}

	// 新增供应商
	@RequestMapping(value = "/saveSupplier.do")
	public String saveSupplier(ERP_Supplier supplier, Model model) {
		// 设置编号
		supplier.setSupplier_Code(this.dwbh());
		supplierService.saveSupplier(supplier);
		model.addAttribute("flag", true);
		return "admin/supplier/saveSupplier";
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
		String bh = "GYS" + "-" + time + x;
		return bh;
	}

	// 跳转至供应商编辑页面
	@RequestMapping(value = "/initEditSupplier.do")
	public String initEditSupplier(@RequestParam Integer row_Id, Model model) {
		// 根据Id获得供应商对象
		ERP_Supplier supplier = supplierService.querySupplierById(row_Id);
		model.addAttribute("supplier", supplier);
		return "admin/supplier/editSupplier";
	}

	// 编辑供应商
	@RequestMapping(value = "/editSupplier.do")
	public String editSupplier(ERP_Supplier supplier, Model model) {
		supplierService.editSupplier(supplier);
		model.addAttribute("flag", true);
		return "admin/supplier/editSupplier";
	}

	// 查看供应商
	@RequestMapping(value = "/showSupplier.do")
	public String showSupplier(@RequestParam Integer row_Id, Model model) {
		// 根据Id获得供应商对象
		ERP_Supplier supplier = supplierService.querySupplierById(row_Id);
		model.addAttribute("supplier", supplier);
		return "admin/supplier/showSupplier";
	}

	// 删除供应商
	@RequestMapping(value = "/deleteSupplier.do")
	@ResponseBody
	public String deleteSupplier(Integer row_Id) {
		JSONObject jsonObject = new JSONObject();
		supplierService.deleteSupplier(row_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 批量删除
	@RequestMapping(value = "/batchDeletePost.do")
	@ResponseBody
	public String batchDeletePost(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			supplierService.deleteSupplier(Integer.parseInt(id.trim()));
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
