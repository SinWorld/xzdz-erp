package com.edge.business.sale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 销售合同物料ID控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "salesMaterielId")
public class SalesMaterielIdController {

	// 跳转至物料ID列表页面
	@RequestMapping(value = "/initSalesMaterielIdList.do")
	public String initSalesMaterielIdList(String index, Model model) {
		model.addAttribute("index", index);
		return "business/sale/materielIdList";
	}
}
