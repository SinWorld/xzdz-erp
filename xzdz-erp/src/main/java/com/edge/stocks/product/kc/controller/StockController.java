package com.edge.stocks.product.kc.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_QueryVo;
import com.edge.stocks.product.kc.entity.ERP_WarnStock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;
import com.google.gson.Gson;

/**
 * 成品库存控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "/stock")
public class StockController {

	@Resource
	private KC_StockService kcStockService;

	@Resource
	private ProductService productService;

	@Resource
	private Pro_StockService stockService;

	// 跳转至成品库存页面
	@RequestMapping(value = "/initKcList.do")
	public String initKcList() {
		return "stocks/kcProduct/kcProductList";
	}

	// 分页查询成品库存列表
	@RequestMapping(value = "/kcList.do")
	@ResponseBody
	public String kcList(Integer page, Integer limit) {
		// new出ERP_Stock_QueryVo查询对象
		ERP_Stock_QueryVo vo = new ERP_Stock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		Gson gson = new Gson();
		List<ERP_Stock> stockList = kcStockService.stockList(vo);
		for (ERP_Stock s : stockList) {
			ERP_Product_Stock stock = stockService.queryPro_StockById(s.getStock_Id());
			ERP_Products product = productService.queryProductById(s.getProduct_Id());
			s.setProductName(product.getProduct_Name());
			s.setStockName(stock.getStock());
			// 总库存量
			s.setZkcl(kcStockService.totalKcNumber(s.getMaterielId()));
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", kcStockService.stockListCount(vo));
		map.put("data", stockList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 库存报警(查询库存量小于200)
	@RequestMapping(value = "/warnStockList.do")
	@ResponseBody
	public String warnStockList() {
		JSONArray jsonArray = new JSONArray();
		List<ERP_WarnStock> list = kcStockService.warnStockList();
		List<ERP_WarnStock> warnStockList = new ArrayList<ERP_WarnStock>();
		for (ERP_WarnStock l : list) {
			if (l.getKcl() < 200) {
				warnStockList.add(l);
			}
		}
		for (ERP_WarnStock w : warnStockList) {
			jsonArray.add(w);
		}
		return jsonArray.toString();
	}
}
