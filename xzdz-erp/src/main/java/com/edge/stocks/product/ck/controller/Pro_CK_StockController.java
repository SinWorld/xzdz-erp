package com.edge.stocks.product.ck.controller;

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
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;
import com.google.gson.Gson;

/**
 * 成品出库控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "ckStock")
public class Pro_CK_StockController {
	@Resource
	private Pro_CK_StockService ckStockService;

	@Resource
	private ProductService productService;

	@Resource
	private Pro_StockService stockService;

	@Resource
	private Pro_StockRecordService stockRecordService;

	// 跳转至出库库存列表页面
	@RequestMapping(value = "/initckProStockList.do")
	public String initckProStockList() {
		return "stocks/ckproduct/productList";
	}

	// 分页查询出库库存列表
	@RequestMapping(value = "/prockStockList.do")
	@ResponseBody
	public String prockStockList(Integer page, Integer limit) {
		// new出ERP_ProStock_QueryVo查询对象
		ERP_ProStock_QueryVo vo = new ERP_ProStock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit+1);
		vo.setRows(page*limit);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", ckStockService.pro_CK_StockCount(vo));
		List<ERP_Product_Stock> list = ckStockService.pro_CK_StockList(vo);
		for (ERP_Product_Stock l : list) {
			ERP_Products products = productService.queryProductById(l.getProduct());
			l.setProductName(products.getProduct_Name());
			if (products.getIs_allck()) {
				l.setIs_ck(true);
			} else {
				l.setIs_ck(false);
			}
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至成品出库页面
	@RequestMapping(value = "/initCkProduct.do")
	public String initCkProduct() {
		return "stocks/ckproduct/saveProduct";
	}

	// 加载所有未出库的成品
	@RequestMapping(value = "/allWckProductStock.do")
	@ResponseBody
	public String allWckProductStock() {
		JSONArray array = new JSONArray();
		List<ERP_Product_Stock> wckProduct = ckStockService.allWckProductStock();
		for (ERP_Product_Stock w : wckProduct) {
			ERP_Products products = productService.queryProductById(w.getProduct());
			w.setProductName(products.getProduct_Name());
			array.add(w);
		}
		return array.toString();
	}

	// 下拉成品对象设置属性
	@RequestMapping(value = "/queryStockById.do")
	@ResponseBody
	public String queryStockById(Integer stock_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Product_Stock stock = stockService.queryPro_StockById(stock_Id);
		// 获得成品对象
		ERP_Products products = productService.queryProductById(stock.getProduct());
		// 加载某一成品已出库的数量
		Integer yckCount = ckStockService.yckCount(stock_Id);
		jsonObject.put("stock", stock);
		jsonObject.put("products", products);
		jsonObject.put("yckCount", yckCount);
		return jsonObject.toString();
	}

	// 出库操作
	@RequestMapping(value = "/ckStock.do")
	public String ckStock(@RequestParam Integer stock_Id, Integer cknumber, String remarks, Model model,
			HttpServletRequest request) {
		// 从session中获取登录用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		ERP_Product_Stock stock = stockService.queryPro_StockById(stock_Id);
		ERP_Products product = productService.queryProductById(stock.getProduct());
		//已经出库
		product.setIs_ck(true);
		productService.editProduct(product);
		for (int i = 0; i < cknumber; i++) {
			ERP_stocks_Record record = new ERP_stocks_Record();
			record.setProduct(product.getProduct_Id());
			record.setStock(stock_Id);
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(true);// false为入库 true 为出库
			record.setJbr(user.getUserId());
			record.setRemarks(remarks);
			stockRecordService.saveStockRecord(record);
		}
		// 加载某一成品已出库的数量
		Integer yckCount = ckStockService.totalYckCount(product.getProduct_Id());
		// 获得该成品已入库的总数量
		List<ERP_stocks_Record> yrkzsl = stockRecordService.recordList(stock.getProduct());
		if (yckCount == yrkzsl.size()) {
			/**
			 * 若该成品全部出库则更新标志
			 */
			product.setIs_allck(true);
			productService.editProduct(product);
		}
		// 设置出库数量
		stock.setCknumber(ckStockService.yckCount(stock_Id));
		stockService.syrkProduct(stock);
		model.addAttribute("flag", true);
		return "stocks/ckproduct/saveProduct";
	}

}
