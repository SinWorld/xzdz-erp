package com.edge.product.controller;

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
import com.edge.product.entity.ERP_Products;
import com.edge.product.entity.ERP_Products_QueryVo;
import com.edge.product.service.inter.ProductService;
import com.google.gson.Gson;

/**
 * 成品信息控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "product")
public class ProductController {

	@Resource
	private ProductService productService;

	// 跳转至成品列表页面
	@RequestMapping(value = "/initProductList.do")
	public String initProductList() {
		return "product/productList";
	}

	// 分页查询成品列表
	@RequestMapping(value = "/productList.do")
	@ResponseBody
	public String productList(Integer page, Integer limit, String product_Name, String specification_Type,
			String factory_Price1, String factory_Price2, String channel_Price1, String channel_Price2,
			String market_Value1, String market_Value2) {
		// new出ERP_Products_QueryVo查询对象
		ERP_Products_QueryVo vo = new ERP_Products_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit+1);
		vo.setRows(page*limit);
		if (product_Name != null && product_Name != "") {
			vo.setProduct_Name(product_Name.trim());
		}
		if (specification_Type != null && specification_Type != "") {
			vo.setSpecification_Type(specification_Type.trim());
		}
		if (factory_Price1 != null && factory_Price1 != "") {
			vo.setChannel_Price1(Double.parseDouble(factory_Price1.trim()));
		}
		if (factory_Price2 != null && factory_Price2 != "") {
			vo.setChannel_Price2(Double.parseDouble(factory_Price2.trim()));
		}
		if (channel_Price1 != null && channel_Price1 != "") {
			vo.setChannel_Price1(Double.parseDouble(channel_Price1.trim()));
		}
		if (channel_Price2 != null && channel_Price2 != "") {
			vo.setChannel_Price2(Double.parseDouble(channel_Price2.trim()));
		}
		if (market_Value1 != null && market_Value1 != "") {
			vo.setMarket_Value1(Double.parseDouble(market_Value1.trim()));
		}
		if (market_Value2 != null && market_Value2 != "") {
			vo.setMarket_Value2(Double.parseDouble(market_Value2.trim()));
		}
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", productService.productCount(vo));
		map.put("data", productService.productList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至成品信息新增页面
	@RequestMapping(value = "/initSaveProduct.do")
	public String initSaveProduct() {
		return "product/saveProduct";
	}

	// 新增产品
	@RequestMapping(value = "/saveProduct.do")
	public String saveProduct(ERP_Products products, Model model) {
		products.setProduct_Code(this.cpbh());
		products.setIs_rk(false);
		products.setIs_ck(false);
		productService.saveProduct(products);
		model.addAttribute("flag", true);
		return "product/saveProduct";
	}

	// 生成产品编号
	private String cpbh() {
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
		String bh = "CP" + "-" + time + x;
		return bh;
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditProduct.do")
	public String initEditProduct(@RequestParam Integer product_Id, Model model) {
		ERP_Products products = productService.queryProductById(product_Id);
		model.addAttribute("product", products);
		return "product/editProduct";
	}

	// 编辑操作
	@RequestMapping(value = "/editProduct.do")
	public String editProduct(ERP_Products products, Model model) {
		productService.editProduct(products);
		model.addAttribute("flag", true);
		return "product/editProduct";
	}

	// 查看操作
	@RequestMapping(value = "/showProduct.do")
	public String showProduct(@RequestParam Integer product_Id, Model model) {
		ERP_Products products = productService.queryProductById(product_Id);
		model.addAttribute("product", products);
		return "product/ShowProduct";
	}

	// 删除成品
	@RequestMapping(value = "/deleteProduct.do")
	@ResponseBody
	public String deleteProduct(Integer product_Id) {
		JSONObject jsonObject = new JSONObject();
		productService.deleteProductById(product_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
