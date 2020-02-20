package com.edge.stocks.material.rk.controller;

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
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.rk.entity.ERP_MatObj;
import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "matStock")
public class Mat_StockController {
	@Resource
	private Mat_StockService stockService;

	@Resource
	private MaterialService materialService;

	@Resource
	private Mat_StockRecordService stockRecordService;

	@Resource
	private KC_StockService kc_stockService;

	// 跳转至材料库存列表页面
	@RequestMapping(value = "/initMatStockList.do")
	public String initMatStockList() {
		return "stocks/materialStock/materialList";
	}

	// 分页查询材料库存列表
	@RequestMapping(value = "/matStockList.do")
	@ResponseBody
	public String matStockList(Integer page, Integer limit, String kw, String bz) {
		// new出ERP_MatStock_QueryVo查询对象
		ERP_MatStock_QueryVo vo = new ERP_MatStock_QueryVo();
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
		map.put("count", stockService.mat_StockCount(vo));
		map.put("data", stockService.mat_StockList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至材料入库页面
	@RequestMapping(value = "/initRkMaterial.do")
	public String initRkMaterial() {
		return "stocks/materialStock/saveMaterial";
	}

	// 新增时库存名检测
	@RequestMapping(value = "/checkKc.do")
	@ResponseBody
	public String checkKc(String kcName) {
		JSONObject jsonObject = new JSONObject();
		ERP_Material_Stock stock = stockService.checkKw(kcName.trim());
		if (stock != null) {
			jsonObject.put("flag", true);
		}
		return jsonObject.toString();
	}

	// 新增库存
	@RequestMapping(value = "/saveMatStock.do")
	public String saveMatStock(ERP_Material_Stock matStock, Model model) {
		stockService.saveMatStock(matStock);
		model.addAttribute("flag", true);
		return "stocks/materialStock/saveMaterial";
	}

	// 跳转至库存编辑页面
	@RequestMapping(value = "/initEditMatStock.do")
	public String initEditMatStock(@RequestParam Integer material_Id, Model model) {
		ERP_Material_Stock material_Stock = stockService.queryMatStockById(material_Id);
		model.addAttribute("material_Stock", material_Stock);
		return "stocks/materialStock/editMaterial";
	}

	// 编辑库存
	@RequestMapping(value = "/editMatStock.do")
	public String editMatStock(ERP_Material_Stock matStock, Model model) {
		stockService.editMatStock(matStock);
		model.addAttribute("flag", true);
		return "stocks/materialStock/editMaterial";
	}

	// 删除库存
	@RequestMapping(value = "/deleteMatStock.do")
	@ResponseBody
	public String deleteMatStock(Integer material_Id) {
		JSONObject jsonObject = new JSONObject();
		stockService.deleteMatStock(material_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 查询所有的库位
	@RequestMapping(value = "/queryAllStock.do")
	@ResponseBody
	public String queryAllStock() {
		// new 出JSONArray数组
		JSONArray jsonArray = new JSONArray();
		List<ERP_Material_Stock> stocks = stockService.queryAllStock();
		// 遍历该集合
		for (ERP_Material_Stock s : stocks) {
			// new 出JSONObject对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", s.getMaterial_Id());
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
			ERP_Material_Stock stock = stockService.queryMatStockById(Integer.parseInt(s.trim()));
			jsonArray.add(stock);
		}
		return jsonArray.toString();
	}

	// 材料入库
	@RequestMapping(value = "/saveMaterialStock.do")
	@ResponseBody
	public String saveMaterialStock(@RequestBody ERP_MatObj[] rkObj, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 从session中获取登录用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		boolean kg = true;
		// 遍历该集合
		for (ERP_MatObj r : rkObj) {
			if (r.getRknumber() != 0) {
				// 新增入库记录
				ERP_Material_Stocks_Record record = new ERP_Material_Stocks_Record();
				record.setMaterial(r.getMaterialId());
				record.setStock(r.getStock_Id());
				record.setSl(r.getRknumber());
				record.setSj(new Date());
				record.setRecord_Type(false);// false为入库 true 为出库
				record.setJbr(user.getUserId());
				record.setRemarks(r.getRemarks());
				stockRecordService.saveStockRecord(record);
				/**
				 * 1.根据入库的成品及库位去库存查询若存在则更新库存反之则新增
				 */
				// 更新材料的入库标志位
				ERP_RAW_Material material = materialService.queryMaterialById(r.getMaterialId());
				ERP_Stock kc = kc_stockService.queryStockByCLId(material.getRaw_Material_Id(),r.getStock_Id());
				if (kc != null) {
					kc.setSl(kc.getSl() + r.getRknumber());
					kc_stockService.editStock(kc);
				} else {
					this.saveKCStock(r.getStock_Id(), r.getRknumber(), kc.getMaterielId());
				}
				material.setIs_rk(true);
				if (kg) {
					materialService.editMaterial(material);
					kg = false;
				}
				// 得到该材料的入库总数量
				Integer totalRkNumber = stockRecordService.queryMatRkNumber(material.getRaw_Material_Id());
				// 若入库总数量等于生产数量更新入库标志
				if (totalRkNumber == material.getNumbers()) {
					// 更新该材料的入库标志位
					material.setIs_allrk(true);
					materialService.editMaterial(material);
					// 更新该材料的出库标志位
					material.setIs_allck(false);
					materialService.editMaterial(material);
				} else {
					// 更新该材料的出库标志位
					material.setIs_allck(false);
					materialService.editMaterial(material);
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增库存记录
	private void saveKCStock(Integer stock_Id, Integer sl, String materielId) {
		ERP_Stock stock = new ERP_Stock();
		stock.setStock_Id(stock_Id);
		stock.setSl(sl);
		stock.setStock_Type(true);
		stock.setMaterielId(materielId);
		kc_stockService.saveStock(stock);
	}
}
