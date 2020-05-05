package com.edge.product.controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.entity.ERP_Products_QueryVo;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
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

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private Pro_StockRecordService recordService;

	@Resource
	private ERP_Sales_ContractService saleService;

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private KC_StatusService statusService;

	// 跳转至成品列表页面
	@RequestMapping(value = "/initProductList.do")
	public String initProductList() {
		return "product/productList";
	}

	// 分页查询成品列表
	@RequestMapping(value = "/productList.do")
	@ResponseBody
	public String productList(Integer page, Integer limit, String productName, String specificationType, String dw,
			Integer sl, Double ccj1, Double ccj2, Double qdj1, Double qdj2, Double scj1, Double scj2) {
		// new出ERP_Products_QueryVo查询对象
		ERP_Products_QueryVo vo = new ERP_Products_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setProductName(productName);
		vo.setSpecificationType(specificationType);
		vo.setDw(dw);
		vo.setSl(sl);
		vo.setChannel_Price1(ccj1);
		vo.setChannel_Price2(ccj2);
		vo.setChannel_Price1(qdj1);
		vo.setChannel_Price2(qdj2);
		vo.setMarket_Value1(scj1);
		vo.setMarket_Value2(scj2);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", productService.productCount(vo));
		List<ERP_Products> productList = productService.productList(vo);
		for (ERP_Products p : productList) {
			// 销售订单
			if (p.getSales_Contract_Id() != null) {
				ERP_Sales_Contract contract = saleService.queryContractById(p.getSales_Contract_Id());
				p.setSales_Contract_Name(contract.getSales_Contract_Name());
			}
		}
		map.put("data", productList);
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
		products.setIs_allrk(false);
		products.setIs_allck(false);
		productService.saveProduct(products);
		// 新增库存状态为待入库
		this.saveKcStatus(productService.queryMaxProductId());
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
		// 根据成品销售合同主键获得销售合同对象
		if (products.getSales_Contract_Id() != null) {
			ERP_Sales_Contract contract = contractService.queryContractById(products.getSales_Contract_Id());
			model.addAttribute("contractName", contract.getSales_Contract_Name());
		}
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

	// 加载所有的销售订单
	@RequestMapping(value = "/allSales.do")
	@ResponseBody
	public String allSales() {
		JSONArray jsonArray = new JSONArray();
		List<ERP_Sales_Contract> salesList = productService.salesList();
		for (ERP_Sales_Contract s : salesList) {
			jsonArray.add(s);
		}
		return jsonArray.toString();
	}

	// 点击入库跳转至库存穿梭框页面
	@RequestMapping(value = "/rkProductStock.do")
	public String rkProductStock(@RequestParam Integer product_Id, Model model) {
		// 根据成品Id获得成品对象
		ERP_Products product = productService.queryProductById(product_Id);
		// 加载当前成品的入库数量
		Integer rkNumber = recordService.queryProRkNumber(product.getProduct_Id());
		if (rkNumber == null) {
			model.addAttribute("rkNumber", 0);
		} else {
			model.addAttribute("rkNumber", rkNumber);
		}
		model.addAttribute("product", product);
		return "product/rkProductStock";
	}

	// 新增库存状态记录
	private void saveKcStatus(Integer product_Id) {
		ERP_Stock_Status status = new ERP_Stock_Status();
		status.setProduct_Id(product_Id);
		status.setStock_Type(false);
		status.setStatus("待入库");
		statusService.saveStockStatus(status);
	}
}
