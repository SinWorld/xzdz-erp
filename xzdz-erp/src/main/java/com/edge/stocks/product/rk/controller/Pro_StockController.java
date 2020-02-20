package com.edge.stocks.product.rk.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_RkObj;
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

	@Resource
	private KC_StockService kc_stockService;

	@Resource
	private KC_StatusService statusService;

	// 跳转至库存列表页面
	@RequestMapping(value = "/initProStockList.do")
	public String initProStockList() {
		return "stocks/productStock/productStockList";
	}

	// 分页查询库存列表
	@RequestMapping(value = "/proStockList.do")
	@ResponseBody
	public String proStockList(Integer page, Integer limit, String kw, String bz) {
		// new出ERP_ProStock_QueryVo查询对象
		ERP_ProStock_QueryVo vo = new ERP_ProStock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		if (kw != null && kw != "") {
			vo.setKw(kw.trim());
		}
		if (bz != null && bz != "") {
			vo.setBz(bz.trim());
		}
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
		return "stocks/productStock/saveProductStock";
	}

	// 库存名重复验证
	@RequestMapping(value = "/checkStock.do")
	@ResponseBody
	public String checkStock(String kcName) {
		JSONObject jsonObject = new JSONObject();
		ERP_Product_Stock stock = stockService.queryStockByStock(kcName.trim());
		if (stock == null) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 新增库存
	@RequestMapping(value = "/saveStock.do")
	public String saveStock(ERP_Product_Stock proStock, Model model) {
		stockService.saveProStock(proStock);
		model.addAttribute("flag", true);
		return "stocks/productStock/saveProductStock";
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditStock.do")
	public String initEditStock(@RequestParam Integer stock_Id, Model model) {
		ERP_Product_Stock stock = stockService.queryPro_StockById(stock_Id);
		model.addAttribute("stock", stock);
		return "stocks/productStock/editProductStock";
	}

	// 编辑库存
	@RequestMapping(value = "/editStock.do")
	public String editStock(ERP_Product_Stock proStock, Model model) {
		stockService.editProStock(proStock);
		model.addAttribute("flag", true);
		return "stocks/productStock/editProductStock";
	}

	// 删除库存
	@RequestMapping(value = "/deleteStock.do")
	@ResponseBody
	public String deleteStock(Integer stock_Id) {
		JSONObject jsonObject = new JSONObject();
		stockService.deleteProStock(stock_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 查询所有的库位
	@RequestMapping(value = "/queryAllStock.do")
	@ResponseBody
	public String queryAllStock() {
		// new 出JSONArray数组
		JSONArray jsonArray = new JSONArray();
		List<ERP_Product_Stock> stocks = stockService.queryAllStock();
		// 遍历该集合
		for (ERP_Product_Stock s : stocks) {
			// new 出JSONObject对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", s.getStock_Id());
			jsonObject.put("title", s.getStock());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 根据选择库位查询库存对象
	@RequestMapping(value = "/queryChoseStock.do")
	@ResponseBody
	public String queryChoseStock(String strs) {
		JSONArray jsonArray = new JSONArray();
		// 将str进行字符截取
		String str = strs.substring(1, strs.length());
		// 将其转换为数组
		String[] stockdms = str.split(",");
		for (String s : stockdms) {
			// 根据Id获得成品库存对象
			ERP_Product_Stock stock = stockService.queryPro_StockById(Integer.parseInt(s.trim()));
			jsonArray.add(stock);
		}
		return jsonArray.toString();
	}

	// 成品入库
	@RequestMapping(value = "/saveProStock.do")
	@ResponseBody
	public String saveProStock(@RequestBody ERP_RkObj[] rkObj, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 从session中获取登录用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		boolean kg = true;
		// 遍历该集合
		for (ERP_RkObj r : rkObj) {
			if (r.getRknumber() != 0) {
				// 新增入库记录
				ERP_stocks_Record record = new ERP_stocks_Record();
				record.setProduct(r.getProductId());
				record.setStock(r.getStock_Id());
				record.setSl(r.getRknumber());
				record.setSj(new Date());
				record.setRecord_Type(false);// false为入库 true 为出库
				record.setJbr(user.getUserId());
				record.setRemarks(r.getRemarks());
				stockRecordService.saveStockRecord(record);
				/**
				 * 1.根据入库的物料Id查询若存在则更新库存反之则新增
				 */
				ERP_Products product = productService.queryProductById(r.getProductId());
				ERP_Stock kc = kc_stockService.queryStockByCPId(product.getProduct_Id(), r.getStock_Id());
				if (kc != null) {
					kc.setSl(kc.getSl() + r.getRknumber());
					kc_stockService.editStock(kc);
				} else {
					this.saveKCStock(r.getStock_Id(), r.getRknumber(), product.getMaterielid(),
							product.getProduct_Id());
				}
				//根据成品Id查询库存状态
				ERP_Stock_Status status = statusService.queryStastusByCpId(product.getProduct_Id());
				/**
				 * 入库存状态存在则更新状态反之则新增
				 */
				if(status!=null) {
					status.setStatus("待出库");
					statusService.editStockStatus(status);
				}else {
					this.saveKcStatus(product.getProduct_Id());
				}
				product.setIs_rk(true);
				if (kg) {
					productService.editProduct(product);
					kg = false;
				}

				// 得到该成品的入库总数量
				Integer totalRkNumber = stockRecordService.queryProRkNumber(product.getProduct_Id());

				// 若入库总数量等于生产数量更新入库标志
				if (totalRkNumber == product.getNumbers()) {
					// 更新该成品的入库标志位
					product.setIs_allrk(true);
					// 更新该成品的出库标志位
					product.setIs_allck(false);
					productService.editProduct(product);
				} else {
					// 更新该成品的出库标志位
					product.setIs_allck(false);
					productService.editProduct(product);
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();

	}

	// 新增库存记录
	private void saveKCStock(Integer stock_Id, Integer sl, String materielId, Integer product_Id) {
		ERP_Stock stock = new ERP_Stock();
		stock.setProduct_Id(product_Id);
		stock.setStock_Id(stock_Id);
		stock.setSl(sl);
		stock.setMaterielId(materielId);
		stock.setStock_Type(false);
		kc_stockService.saveStock(stock);
	}

	// 新增库存状态记录
	private void saveKcStatus(Integer product_Id) {
		ERP_Stock_Status status = new ERP_Stock_Status();
		status.setProduct_Id(product_Id);
		status.setStock_Type(false);
		status.setStatus("待出库");
		statusService.saveStockStatus(status);
	}
}
