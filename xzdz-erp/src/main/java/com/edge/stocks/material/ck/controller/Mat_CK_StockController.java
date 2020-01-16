package com.edge.stocks.material.ck.controller;

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
import com.edge.stocks.material.ck.service.inter.Mat_CK_StockService;
import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.google.gson.Gson;

/**
 * 材料出库控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "clckStock")
public class Mat_CK_StockController {
	@Resource
	private Mat_CK_StockService ckStockService;

	@Resource
	private MaterialService materialService;

	@Resource
	private Mat_StockService stockService;

	@Resource
	private Mat_StockRecordService stockRecordService;

	// 跳转至出库库存列表页面
	@RequestMapping(value = "/initckMatStockList.do")
	public String initckProStockList() {
		return "stocks/ckmaterial/materialList";
	}

	// 分页查询出库库存列表
	@RequestMapping(value = "/matckStockList.do")
	@ResponseBody
	public String matckStockList(Integer page, Integer limit) {
		// new出ERP_Material_Stocks_Record查询对象
		ERP_MatStock_QueryVo vo = new ERP_MatStock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit+1);
		vo.setRows(page*limit);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", ckStockService.matCKStockCount(vo));
		List<ERP_Material_Stock> list = ckStockService.mat_CK_StockList(vo);
		for (ERP_Material_Stock l : list) {
			ERP_RAW_Material material = materialService.queryMaterialById(l.getMaterial());
			l.setMaterialName(material.getMaterial_Name());
			if (material.getIs_ck()) {
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
	@RequestMapping(value = "/initCkMaterial.do")
	public String initCkMaterial() {
		return "stocks/ckmaterial/saveMaterial";
	}

	// 加载所有未出库的材料
	@RequestMapping(value = "/allWckMaterialStock.do")
	@ResponseBody
	public String allWckMaterialStock() {
		JSONArray array = new JSONArray();
		List<ERP_Material_Stock> wckMaterial = ckStockService.allWckMaterialStock();
		for (ERP_Material_Stock w : wckMaterial) {
			ERP_RAW_Material material = materialService.queryMaterialById(w.getMaterial());
			w.setMaterialName(material.getMaterial_Name());
			array.add(w);
		}
		return array.toString();
	}

	// 下拉材料对象设置属性
	@RequestMapping(value = "/queryStockById.do")
	@ResponseBody
	public String queryStockById(Integer material_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Material_Stock stock = stockService.queryMatStockById(material_Id);
		// 获得材料对象
		ERP_RAW_Material material = materialService.queryMaterialById(stock.getMaterial());
		// 加载某一成品已出库的数量
		Integer yckCount = ckStockService.yckCount(material_Id);
		jsonObject.put("stock", stock);
		jsonObject.put("material", material);
		jsonObject.put("yckCount", yckCount);
		return jsonObject.toString();
	}

	// 出库操作
	@RequestMapping(value = "/ckStock.do")
	public String ckStock(@RequestParam Integer material_Id, Integer cknumber, String remarks, Model model,
			HttpServletRequest request) {
		// 从session中获取登录用户
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		ERP_Material_Stock stock = stockService.queryMatStockById(material_Id);
		ERP_RAW_Material material = materialService.queryMaterialById(stock.getMaterial());
		for (int i = 0; i < cknumber; i++) {
			ERP_Material_Stocks_Record record = new ERP_Material_Stocks_Record();
			record.setMaterial(material.getRaw_Material_Id());
			record.setStock(material_Id);
			record.setSl(1);
			record.setSj(new Date());
			record.setRecord_Type(false);// false为入库 true 为出库
			record.setJbr(user.getUserId());
			record.setRemarks(remarks);
			stockRecordService.saveStockRecord(record);
		}
		// 加载某一成品已出库的数量
		Integer yckCount = ckStockService.totalYckCount(material.getRaw_Material_Id());
		// 获得该成品已入库的总数量
		List<ERP_Material_Stocks_Record> yrkzsl = stockRecordService.recordList(stock.getMaterial());
		if (yckCount == yrkzsl.size()) {
			/**
			 * 若该成品全部出库则更新标志
			 */
			material.setIs_ck(true);
			materialService.editMaterial(material);
		}
		// 设置出库数量
		stock.setCknumber(ckStockService.yckCount(material_Id));
		stockService.syrkMaterial(stock);
		model.addAttribute("flag", true);
		return "stocks/ckmaterial/saveMaterial";
	}

}
