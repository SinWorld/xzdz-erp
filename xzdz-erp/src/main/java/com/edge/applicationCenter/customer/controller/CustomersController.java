package com.edge.applicationCenter.customer.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.admin.customer.entity.Customer_QueryVo;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.google.gson.Gson;

/**
 * 客户管理控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "kh")
public class CustomersController {

	@Resource
	private CustomerService customerService;

	// 跳转至客户管理列表页面
	@RequestMapping(value = "/initCustomerList.do")
	public String initCustomerList() {
		return "applicationCenter/customer/customerList";
	}

	// 客户列表查询
	@RequestMapping(value = "/customerList.do")
	@ResponseBody
	public String customerList(@RequestParam Integer page, Integer limit, String dwmc, String zcdz, String bgdz,
			String xydm, String fddbr, String khh, String dh) {
		// new出查询对象
		Customer_QueryVo vo = new Customer_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
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
		List<ERP_Customer> cusList = customerService.customerList(vo);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", customerService.customerCount(vo));
		map.put("data", cusList);
		String json = gson.toJson(map);
		return json.toString();
	}

}
