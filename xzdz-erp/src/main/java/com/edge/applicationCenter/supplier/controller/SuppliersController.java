package com.edge.applicationCenter.supplier.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = "gys")
public class SuppliersController {

	@Resource
	private SupplierService supplierService;

	// 跳转至供应商列表页面
	@RequestMapping(value = "/initSupplierList.do")
	public String initSupplierList() {
		return "applicationCenter/supplier/supplierList";
	}

	// 列表查询
	@RequestMapping(value = "/supplierList.do")
	@ResponseBody
	public String supplierList(@RequestParam Integer page, Integer limit, String gysmc, String zcdz, String bgdz,
			String shtyxydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Supplier_QueryVo vo = new Supplier_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setGysmc(gysmc);
		vo.setZcdz(zcdz);
		vo.setBgdz(bgdz);
		vo.setShtyxydm(shtyxydm);
		vo.setFddbr(fddbr);
		vo.setKhh(khh);
		vo.setDh(dh);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", supplierService.supplierCount(vo));
		map.put("data", supplierService.supplierList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}
}
