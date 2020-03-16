package com.edge.stocks.product.rk.controller;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;
import com.google.gson.Gson;

/**
 * 成品入库记录控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "stockRecod")
public class Pro_StockRecordController {

	@Resource
	private Pro_StockRecordService recordService;

	@Resource
	private Pro_StockService stockService;

	@Resource
	private ProductService productService;

	@Resource
	private ERP_UserService userService;

	// 跳转至成品入库记录列表页面
	@RequestMapping(value = "/initStockRecodList.do")
	public String initStockRecodList() {
		return "stocks/rkproStoRecord/stockRecordList";
	}

	// 分页查询库存列表
	@RequestMapping(value = "/stockRecodList.do")
	@ResponseBody
	public String stockRecodList(Integer page, Integer limit, Integer cp, Integer kw, Integer jbr, Integer rksl,
			String time1, String time2) {
		// new出ERP_StockRecord_QueryVo查询对象
		ERP_StockRecord_QueryVo vo = new ERP_StockRecord_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setCp(cp);
		vo.setKw(kw);
		vo.setJbr(jbr);
		vo.setRksl(rksl);
		vo.setBeginTime(time1);
		vo.setEndTime(time2);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", recordService.recordCount(vo));
		List<ERP_stocks_Record> recordList = recordService.stockRecordList(vo);
		for (ERP_stocks_Record r : recordList) {
			// 查询对应的库位及成品名称及经办人
			ERP_Product_Stock stock = stockService.queryPro_StockById(r.getStock());
			if (stock != null) {
				r.setStockName(stock.getStock());
			}
			ERP_Products products = productService.queryProductById(r.getProduct());
			if (products != null) {
				r.setProductName(products.getProduct_Name());
			}
			ERP_User user = userService.queryUserById(r.getJbr());
			if (user != null) {
				r.setUserName(user.getUserName());
			}
		}
		map.put("data", recordList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 查看操作
	@RequestMapping(value = "/ShowStockRecod.do")
	public String ShowStockRecod(Integer record_Id, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		ERP_stocks_Record record = recordService.queryRecordById(record_Id);
		ERP_Product_Stock stock = stockService.queryPro_StockById(record.getStock());
		if (stock != null) {
			record.setStockName(stock.getStock());
		}
		ERP_Products products = productService.queryProductById(record.getProduct());
		if (products != null) {
			record.setProductName(products.getProduct_Name());
		}
		ERP_User user = userService.queryUserById(record.getJbr());
		if (user != null) {
			record.setUserName(user.getUserName());
		}
		model.addAttribute("record", record);
		model.addAttribute("sj", sdf.format(record.getSj()));
		return "stocks/rkproStoRecord/showStockRecord";
	}

	// ajax 查询所有的成品
	@RequestMapping(value = "/allCpList.do")
	@ResponseBody
	public String allCpList() {
		JSONArray allCpList = recordService.allCpList();
		return allCpList.toString();
	}

	// ajax 查询所有的成品库存
	@RequestMapping(value = "/allKwList.do")
	@ResponseBody
	public String allKwList() {
		JSONArray allKwList = recordService.allKwList();
		return allKwList.toString();
	}

	// ajax 查询所有的经办人
	@RequestMapping(value = "/allJbrList.do")
	@ResponseBody
	public String allJbrList() {
		JSONArray allJbrList = recordService.allJbrList();
		return allJbrList.toString();
	}

	// ajax 查询所有的材料
	@RequestMapping(value = "/allCLList.do")
	@ResponseBody
	public String allCLList() {
		JSONArray allClList = recordService.allClList();
		return allClList.toString();
	}

	// ajax 查询所有的成品库存
	@RequestMapping(value = "/allClKwList.do")
	@ResponseBody
	public String allClKwList() {
		JSONArray allClKwList = recordService.allClKwList();
		return allClKwList.toString();
	}

}
