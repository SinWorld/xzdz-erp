package com.edge.stocks.product.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.entity.ERP_Product_Stock;
import com.edge.stocks.product.entity.ERP_stocks_Record;
import com.edge.stocks.product.service.inter.Pro_StockRecordService;
import com.edge.stocks.product.service.inter.Pro_StockService;
import com.google.gson.Gson;
import com.sun.star.i18n.reservedWords;

/**
 * 库存控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "proStock")
public class Pro_StockController {
	@Resource
	private Pro_StockService stockService;

	@Resource
	private ProductService productService;

	@Resource
	private Pro_StockRecordService stockRecordService;

	// 跳转至库存列表页面
	@RequestMapping(value = "/initProStockList.do")
	public String initProStockList() {
		return "stocks/product/productList";
	}

	// 分页查询库存列表
	@RequestMapping(value = "/proStockList.do")
	@ResponseBody
	public String proStockList(Integer page, Integer limit) {
		// new出ERP_ProStock_QueryVo查询对象
		ERP_ProStock_QueryVo vo = new ERP_ProStock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit);
		vo.setRows(limit);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", stockService.pro_StockCount(vo));
		map.put("data", stockService.pro_StockList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至成品入库页面
	@RequestMapping(value = "/initRkProduct.do")
	public String initRkProduct() {
		return "stocks/product/saveProduct";
	}

	// 加载所有未入库的成品
	@RequestMapping(value = "/allWrkProduct.do")
	@ResponseBody
	public String allWrkProduct() {
		JSONArray array = new JSONArray();
		List<ERP_Products> wrkProduct = stockService.allWrkProduct();
		for (ERP_Products w : wrkProduct) {
			array.add(w);
		}
		return array.toString();
	}

	// 根据成品主键加载该成品对象,并得到已入库数量
	@RequestMapping(value = "/queryProductById.do")
	@ResponseBody
	public String queryProductById(Integer product_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Products products = productService.queryProductById(product_Id);
		// 得到该成品的入库记录
		List<ERP_stocks_Record> recordList = stockRecordService.recordList(product_Id);
		jsonObject.put("product", products);
		jsonObject.put("yrksl", recordList.size());
		return jsonObject.toString();
	}

	// 新增库存
	@RequestMapping(value = "/saveProStock.do")
	public String saveProStock(ERP_Product_Stock proStock, Model model, HttpServletRequest request) {
		// 从session中获取登录用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		stockService.saveProStock(proStock);
		// 新增出/入库记录
		this.saveProStockRecord(proStock.getRknumber(), stockService.queryMaxStock_Id(), proStock.getProduct(),
				user.getUserId());
		stockService.queryMaxStock_Id();
		model.addAttribute("flag", true);
		return "stocks/product/saveProduct";
	}

	// 新增入库记录
	private void saveProStockRecord(Integer numbers, Integer stock_Id, Integer product, Integer userId) {
		for (int i = 0; i < numbers; i++) {
			ERP_stocks_Record record = new ERP_stocks_Record();
			record.setProduct(product);
			record.setStock(stock_Id);
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(false);
			record.setJbr(userId);
			stockRecordService.saveStockRecord(record);
		}

	}

	// 跳转至产品入库信息页面
	@RequestMapping("/rkProductShow.do")
	public String rkProductShow(@RequestParam String hwdms, Model model) {
		// new 出list集合用于存储所有的成品入库下的成品详情数据
		List<ERP_Products> products = new ArrayList<ERP_Products>();
		// 将hwdms进行字符截取
		String dms = hwdms.substring(1, hwdms.length());
		// 将其转换为数组
		String[] hwdm = dms.split(",");
		// 遍历该集合
		for (String id : hwdm) {
			ERP_Products product = productService.queryProductById(Integer.parseInt(id.trim()));
			products.add(product);
		}
		model.addAttribute("list", products);
		return "stocks/product/productRkList";
	}
}
