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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.google.gson.Gson;

/**
 * 库存控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "matStock")
public class Mat_StockController {
	@Resource
	private Mat_StockService stockService;

	@Resource
	private MaterialService materialService;

	@Resource
	private Mat_StockRecordService stockRecordService;

	// 跳转至材料库存列表页面
	@RequestMapping(value = "/initMatStockList.do")
	public String initMatStockList() {
		return "stocks/rkmaterial/materialList";
	}

	// 分页查询材料库存列表
	@RequestMapping(value = "/matStockList.do")
	@ResponseBody
	public String matStockList(Integer page, Integer limit) {
		// new出ERP_MatStock_QueryVo查询对象
		ERP_MatStock_QueryVo vo = new ERP_MatStock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit);
		vo.setRows(limit);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", stockService.mat_StockCount(vo));
		List<ERP_Material_Stock> list = stockService.mat_StockList(vo);
		for (ERP_Material_Stock l : list) {
			ERP_RAW_Material material = materialService.queryMaterialById(l.getMaterial());
			// 设置该成品的生产总数量
			l.setTotalNumber(material.getNumbers());
			l.setMaterialName(material.getMaterial_Name());
			if (material.getIs_rk()) {
				l.setIs_rk(true);
			} else {
				l.setIs_rk(false);
			}
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至材料入库页面
	@RequestMapping(value = "/initRkMaterial.do")
	public String initRkMaterial() {
		return "stocks/rkmaterial/saveMaterial";
	}

	// 加载所有未入库的材料
	@RequestMapping(value = "/allWrkMaterial.do")
	@ResponseBody
	public String allWrkMaterial() {
		JSONArray array = new JSONArray();
		List<ERP_RAW_Material> wrkMaterial = materialService.allWrkMaterial();
		for (ERP_RAW_Material w : wrkMaterial) {
			array.add(w);
		}
		return array.toString();
	}

	// 加载入库材料
	@RequestMapping(value = "/queryMaterialById.do")
	@ResponseBody
	public String queryMaterialById(Integer raw_Material_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_RAW_Material material = materialService.queryMaterialById(raw_Material_Id);
		// 加载该原材料的入库记录
		List<ERP_Material_Stocks_Record> recordList = stockRecordService.recordList(material.getRaw_Material_Id());
		jsonObject.put("material", material);
		jsonObject.put("yrksl", recordList.size());
		return jsonObject.toString();
	}

	// 新增库存
	@RequestMapping(value = "/saveMatStock.do")
	public String saveMatStock(ERP_Material_Stock matStock, Model model, HttpServletRequest request) {
		// 从session中获取登录用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		stockService.saveMatStock(matStock);
		// 新增出/入库记录
		this.saveMatStockRecord(matStock.getRknumber(), stockService.queryMaxStock_Id(), matStock.getMaterial(),
				user.getUserId(), matStock.getRemarks());

		// 得到该材料的入库记录
		List<ERP_Material_Stocks_Record> recordList = stockRecordService.recordList(matStock.getMaterial());
		// 获得该产品入库的总数量
		int rkzsl = recordList.size();
		// 获得材料信息对象
		ERP_RAW_Material material = materialService.queryMaterialById(matStock.getMaterial());
		if (rkzsl == material.getNumbers()) {
			// 更新该成品的入库标志位
			material.setIs_ck(false);
			material.setIs_rk(true);
			materialService.editMaterial(material);
		}
		material.setIs_ck(false);
		materialService.editMaterial(material);
		model.addAttribute("flag", true);
		return "stocks/rkmaterial/saveMaterial";
	}

	// 新增入库记录
	private void saveMatStockRecord(Integer numbers, Integer stock_Id, Integer material, Integer userId,
			String remarks) {
		for (int i = 0; i < numbers; i++) {
			ERP_Material_Stocks_Record record = new ERP_Material_Stocks_Record();
			record.setMaterial(material);
			record.setStock(stock_Id);
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(false);// false为入库 true 为出库
			record.setJbr(userId);
			record.setRemarks(remarks);
			stockRecordService.saveStockRecord(record);
		}

	}

	// 跳转至剩余材料入库页面
	@RequestMapping(value = "/initSyclrk.do")
	public String initSyclrk(@RequestParam Integer material_Id, Model model) {
		// 获得材料入库对象
		ERP_Material_Stock material_Stock = stockService.queryMatStockById(material_Id);
		// 获得材料信息对象
		ERP_RAW_Material material = materialService.queryMaterialById(material_Stock.getMaterial());
		model.addAttribute("material_Stock", material_Stock);
		model.addAttribute("material", material);
		return "stocks/rkmaterial/syrkMaterial";
	}

	// 剩余材料入库操作
	@RequestMapping(value = "/syclrk.do")
	public String syclrk(ERP_Material_Stock stock, HttpServletRequest request, Model model) {
		/**
		 * 1.获得材料入库数量,根据该数量去新增入库记录 2.若入库数量+已入库数量==生产数量 则更新材料的入库标记
		 */
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		for (int i = 0; i < stock.getRknumber(); i++) {
			// 新增入库记录
			ERP_Material_Stocks_Record record = new ERP_Material_Stocks_Record();
			record.setMaterial(stock.getMaterial());
			record.setStock(stock.getMaterial_Id());
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(false);
			record.setJbr(user.getUserId());
			stockRecordService.saveStockRecord(record);
		}
		// 获得材料对象
		ERP_RAW_Material material = materialService.queryMaterialById(stock.getMaterial());
		// 得到该材料的入库记录
		List<ERP_Material_Stocks_Record> recordList = stockRecordService.recordList(stock.getMaterial());
		// 获得该材料入库的总数量
		int rkzsl = recordList.size();
		if (rkzsl == material.getNumbers()) {
			// 更新该材料的入库标志位
			material.setIs_ck(false);
			material.setIs_rk(true);
			materialService.editMaterial(material);
		}
		stock.setRknumber(rkzsl);
		material.setIs_ck(false);
		materialService.editMaterial(material);
		stockService.syrkMaterial(stock);
		model.addAttribute("flag", true);
		return "stocks/rkmaterial/syrkMaterial";
	}

}
