package com.edge.stocks.product.rk.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;
import com.google.gson.Gson;

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
		return "stocks/rkproduct/productList";
	}

	// 分页查询库存列表
	@RequestMapping(value = "/proStockList.do")
	@ResponseBody
	public String proStockList(Integer page, Integer limit) {
		// new出ERP_ProStock_QueryVo查询对象
		ERP_ProStock_QueryVo vo = new ERP_ProStock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit+1);
		vo.setRows(page*limit);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", stockService.pro_StockCount(vo));
		List<ERP_Product_Stock> list = stockService.pro_StockList(vo);
		for (ERP_Product_Stock l : list) {
			ERP_Products products = productService.queryProductById(l.getProduct());
			//设置该成品的生产总数量
			l.setTotalNumber(products.getNumbers());
			l.setProductName(products.getProduct_Name());
			if (products.getIs_rk()) {
				l.setIs_rk(true);
			} else {
				l.setIs_rk(false);
			}
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至成品入库页面
	@RequestMapping(value = "/initRkProduct.do")
	public String initRkProduct() {
		return "stocks/rkproduct/saveProduct";
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
				user.getUserId(), proStock.getRemarks());

		// 得到该成品的入库记录
		List<ERP_stocks_Record> recordList = stockRecordService.recordList(proStock.getProduct());
		// 获得该产品入库的总数量
		int rkzsl = recordList.size();
		// 获得成品信息对象
		ERP_Products product = productService.queryProductById(proStock.getProduct());
		if (rkzsl == product.getNumbers()) {
			// 更新该成品的入库标志位
			product.setIs_ck(false);
			product.setIs_rk(true);
			productService.editProduct(product);
		}
		// stockService.queryMaxStock_Id();
		product.setIs_ck(false);
		productService.editProduct(product);
		model.addAttribute("flag", true);
		return "stocks/rkproduct/saveProduct";
	}

	// 新增入库记录
	private void saveProStockRecord(Integer numbers, Integer stock_Id, Integer product, Integer userId,
			String remarks) {
		for (int i = 0; i < numbers; i++) {
			ERP_stocks_Record record = new ERP_stocks_Record();
			record.setProduct(product);
			record.setStock(stock_Id);
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(false);// false为入库 true 为出库
			record.setJbr(userId);
			record.setRemarks(remarks);
			stockRecordService.saveStockRecord(record);
		}

	}

	// 跳转至剩余成品入库页面
	@RequestMapping(value = "/initSycprk.do")
	public String initSycprk(@RequestParam Integer stock_Id, Model model) {
		// 获得成品入库对象
		ERP_Product_Stock product_Stock = stockService.queryPro_StockById(stock_Id);
		// 获得成品信息对象
		ERP_Products product = productService.queryProductById(product_Stock.getProduct());
		model.addAttribute("product_Stock", product_Stock);
		model.addAttribute("product", product);
		return "stocks/rkproduct/syrkProduct";
	}

	// 剩余成品入库操作
	@RequestMapping(value = "/sycprk.do")
	public String sycprk(ERP_Product_Stock stock, HttpServletRequest request, Model model) {
		/**
		 * 1.获得成品入库数量,根据该数量去新增入库记录 2.若入库数量+已入库数量==生产数量 则更新产品的入库标记
		 */
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		for (int i = 0; i < stock.getRknumber(); i++) {
			// 新增入库记录
			ERP_stocks_Record record = new ERP_stocks_Record();
			record.setProduct(stock.getProduct());
			record.setStock(stock.getStock_Id());
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(false);
			record.setJbr(user.getUserId());
			stockRecordService.saveStockRecord(record);
		}
		// 获得成品对象
		ERP_Products product = productService.queryProductById(stock.getProduct());
		// 得到该成品的入库记录
		List<ERP_stocks_Record> recordList = stockRecordService.recordList(stock.getProduct());
		// 获得该产品入库的总数量
		int rkzsl = recordList.size();
		if (rkzsl == product.getNumbers()) {
			// 更新该成品的入库标志位
			product.setIs_ck(false);
			product.setIs_rk(true);
			productService.editProduct(product);
		}
		stock.setRknumber(rkzsl);
		product.setIs_ck(false);
		productService.editProduct(product);
		stockService.syrkProduct(stock);
		model.addAttribute("flag", true);
		return "stocks/rkproduct/syrkProduct";
	}

}
