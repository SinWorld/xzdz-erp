package com.edge.stocks.material.ck.controller;

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
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.ck.service.inter.Mat_CK_StockService;
import com.edge.stocks.material.rk.entity.ERP_MatObj;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

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

	@Resource
	private KC_StockService kc_stockService;

	@Resource
	private KC_StatusService kc_statusService;

	// 跳转至出库库存列表页面
	@RequestMapping(value = "/initckMatStockList.do")
	public String initckProStockList(@RequestParam Integer material_Id, Model model) {
		// 查询库存对象
		ERP_Material_Stock material_Stock = stockService.queryMatStockById(material_Id);
		model.addAttribute("material_Stock", material_Stock);
		return "stocks/ckmaterial/ckMaterialStock";
	}

	// 加载当前库位下所有已入库的成品
	@RequestMapping(value = "/queryYrkMaterial.do")
	@ResponseBody
	public String queryYrkMaterial(Integer material_Id) {
		// new 出JSONArray数组
		JSONArray jsonArray = new JSONArray();
		List<ERP_RAW_Material> material = ckStockService.queryStockWckMaterial(material_Id);
		// 遍历该集合
		for (ERP_RAW_Material m : material) {
			// new 出JSONObject对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", m.getRaw_Material_Id());
			jsonObject.put("title", m.getMaterial_Name());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 根据选择成品查询成品对象
	@RequestMapping(value = "/queryChoseProduct.do")
	@ResponseBody
	public String queryChoseProduct(String strs, Integer material_Id) {
		JSONArray jsonArray = new JSONArray();
		// 将str进行字符截取
		String str = strs.substring(1, strs.length());
		// 将其转换为数组
		String[] productdm = str.split(",");
		for (String p : productdm) {
			// 根据Id获得入库记录对象
			ERP_RAW_Material material = materialService.queryMaterialById(Integer.parseInt(p.trim()));
			Integer totalKc = ckStockService.totalKc(material.getRaw_Material_Id(), material_Id);// 该成品的总库存量
			material.setRkNumber(totalKc);
			jsonArray.add(material);
		}
		return jsonArray.toString();
	}

	// 材料出库
	@RequestMapping(value = "/saveMatStock.do")
	@ResponseBody
	public String saveMatStock(@RequestBody ERP_MatObj[] rkObj, HttpServletRequest request) {
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
				record.setRecord_Type(true);// false为入库 true 为出库
				record.setJbr(user.getUserId());
				record.setRemarks(r.getRemarks());
				stockRecordService.saveStockRecord(record);
				/**
				 * 库存出库
				 */
				// 更新材料的入库标志位
				ERP_RAW_Material material = materialService.queryMaterialById(r.getMaterialId());
				ERP_Stock kc = kc_stockService.queryStockByCLId(material.getRaw_Material_Id(), r.getStock_Id());
				if (kc != null) {
					kc.setSl(kc.getSl() - r.getRknumber());
					kc_stockService.editStock(kc);
				}
				material.setIs_ck(true);
				if (kg) {
					materialService.editMaterial(material);
					kg = false;
				}
				// 查询该成品的库存状态并设置为已出库
				ERP_Stock_Status status = kc_statusService.queryStastusByClId(material.getRaw_Material_Id());
				if (status != null) {
					status.setStatus("已出库");
					kc_statusService.editStockStatus(status);
				}
				// 该材料已全部出库
				if (ckStockService.totalrkKc(material.getRaw_Material_Id()).equals(0)) {
					// 更新该成品的入库标志位
					material.setIs_allck(true);
					materialService.editMaterial(material);
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();

	}

}
