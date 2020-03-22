package com.edge.stocks.material.kc.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.kc.service.inter.KC_MaterialStockService;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_QueryVo;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.entity.ERP_WarnStock;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.google.gson.Gson;

/**
 * 材料库存控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "kc_materialStock")
public class KC_MaterialStockController {

	@Resource
	private KC_MaterialStockService materialStockService;

	@Resource
	private Mat_StockService stockService;

	@Resource
	private MaterialService materialService;

	@Resource
	private KC_StatusService statusService;

	// 跳转至材料库存页面
	@RequestMapping(value = "/initMaterialStockList.do")
	public String initMaterialStockList() {
		return "stocks/KcMaterial/KcMaterialList";
	}

	// 分页查询材料库存列表
	@RequestMapping(value = "/kcList.do")
	@ResponseBody
	public String kcList(Integer page, Integer limit, Integer cp, Integer kw, String wlId, Integer kcl1, Integer kcl2) {
		// new出ERP_Stock_QueryVo查询对象
		ERP_Stock_QueryVo vo = new ERP_Stock_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setCp(cp);
		vo.setKw(kw);
		vo.setWlId(wlId);
		vo.setKcl1(kcl1);
		vo.setKcl2(kcl2);
		Gson gson = new Gson();
		List<ERP_Stock> stockList = materialStockService.stockList(vo);
		for (ERP_Stock s : stockList) {
			ERP_Material_Stock stock = stockService.queryMatStockById(s.getStock_Id());
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			ERP_Stock_Status status = statusService.queryStastusByClId(material.getRaw_Material_Id());
			s.setProductName(material.getMaterial_Name());
			s.setStockName(stock.getStock());
			s.setStatus(status.getStatus());
			s.setOddNumbers(status.getOddNumbers());
			// 总库存量
			s.setZkcl(materialStockService.totalKcNumber(s.getMaterielId()));
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", materialStockService.stockListCount(vo));
		map.put("data", stockList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 库存报警(查询库存量小于200)
	@RequestMapping(value = "/warnMaterialStockList.do")
	@ResponseBody
	public String warnMaterialStockList() {
		JSONArray jsonArray = new JSONArray();
		List<ERP_WarnStock> list = materialStockService.warnStockList();
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
