package com.edge.stocks.product.ck.controller;

import java.util.Date;
import java.util.List;

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
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_RkObj;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;

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

	@Resource
	private KC_StockService kc_stockService;

	// 跳转至出库库存列表页面
	@RequestMapping(value = "/initckProStockList.do")
	public String initckProStockList(@RequestParam Integer stock_Id, Model model) {
		// 查询库存对象
		ERP_Product_Stock stock = stockService.queryPro_StockById(stock_Id);
		model.addAttribute("stock", stock);
		return "stocks/ckproduct/ckProductStock";
	}

	// 加载当前库位下所有已入库的成品
	@RequestMapping(value = "/queryYrkProduct.do")
	@ResponseBody
	public String queryYrkProduct(Integer stock_Id) {
		// new 出JSONArray数组
		JSONArray jsonArray = new JSONArray();
		List<ERP_Products> products = ckStockService.queryStockWckProduct(stock_Id);
		// 遍历该集合
		for (ERP_Products p : products) {
			// new 出JSONObject对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", p.getProduct_Id());
			jsonObject.put("title", p.getProduct_Name());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 根据选择成品查询成品对象
	@RequestMapping(value = "/queryChoseProduct.do")
	@ResponseBody
	public String queryChoseProduct(String strs, Integer stockId) {
		JSONArray jsonArray = new JSONArray();
		// 将str进行字符截取
		String str = strs.substring(1, strs.length());
		// 将其转换为数组
		String[] productdm = str.split(",");
		for (String p : productdm) {
			// 根据Id获得入库记录对象
			ERP_Products products = productService.queryProductById(Integer.parseInt(p.trim()));
			// 根据成品Id获得库存对象 设置库存量
			ERP_Stock kc = kc_stockService.queryStockByCPId(products.getProduct_Id(), stockId);
			products.setRkNumber(kc.getSl());
			jsonArray.add(products);
		}
		return jsonArray.toString();
	}

	// 成品出库
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
				record.setRecord_Type(true);// false为入库 true 为出库
				record.setJbr(user.getUserId());
				record.setRemarks(r.getRemarks());
				stockRecordService.saveStockRecord(record);
				/**
				 * 库存出库
				 */
				// 更新成品的入库标志位
				ERP_Products product = productService.queryProductById(r.getProductId());
				ERP_Stock kc = kc_stockService.queryStockByCPId(product.getProduct_Id(), r.getStock_Id());
				if (kc != null) {
					kc.setSl(kc.getSl() - r.getRknumber());
					kc_stockService.editStock(kc);
				}
				product.setIs_ck(true);
				if (kg) {
					productService.editProduct(product);
					kg = false;
				}
				// 该成品已全部出库
				if (ckStockService.totalrkKc(product.getProduct_Id()).equals(0)) {
					// 更新该成品的入库标志位
					product.setIs_allck(true);
					productService.editProduct(product);
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();

	}

}
