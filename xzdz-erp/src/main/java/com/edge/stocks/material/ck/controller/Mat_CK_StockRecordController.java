package com.edge.stocks.material.ck.controller;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.ck.service.inter.Mat_CK_StockService;
import com.edge.stocks.material.rk.entity.ERP_MatStockRecord_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.google.gson.Gson;

/**
 * 成品出库控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "ckMatRecord")
public class Mat_CK_StockRecordController {

	@Resource
	private Mat_CK_StockService ckStockService;

	@Resource
	private Mat_StockService stockService;

	@Resource
	private MaterialService materialService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private Mat_StockRecordService recordService;

	// 跳转至成品出库记录列表页面
	@RequestMapping(value = "/initStockRecodList.do")
	public String initStockRecodList() {
		return "stocks/ckmatStoRecord/stockRecordList";
	}

	// 分页查询库存列表
	@RequestMapping(value = "/stockRecodList.do")
	@ResponseBody
	public String stockRecodList(Integer page, Integer limit, Integer cl, Integer kw, Integer jbr, Integer rksl,
			String time1, String time2) {
		// new出ERP_MatStockRecord_QueryVo查询对象
		ERP_MatStockRecord_QueryVo vo = new ERP_MatStockRecord_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setCl(cl);
		vo.setKw(kw);
		vo.setJbr(jbr);
		vo.setRksl(rksl);
		vo.setBeginTime(time1);
		vo.setEndTime(time2);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", ckStockService.recordCount(vo));
		List<ERP_Material_Stocks_Record> recordList = ckStockService.recordList(vo);
		for (ERP_Material_Stocks_Record r : recordList) {
			// 查询对应的库位及成品名称及经办人
			ERP_Material_Stock stock = stockService.queryMatStockById(r.getStock());
			if (stock != null) {
				r.setStockName(stock.getStock());
			}
			ERP_RAW_Material material = materialService.queryMaterialById(r.getMaterial());
			if (material != null) {
				r.setMaterialName(material.getMaterial_Name());
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
		ERP_Material_Stocks_Record record = recordService.queryMateStockRecordById(record_Id);
		ERP_Material_Stock stock = stockService.queryMatStockById(record.getStock());
		if (stock != null) {
			record.setStockName(stock.getStock());
		}
		ERP_RAW_Material material = materialService.queryMaterialById(record.getMaterial());
		if (material != null) {
			record.setMaterialName(material.getMaterial_Name());
		}
		ERP_User user = userService.queryUserById(record.getJbr());
		if (user != null) {
			record.setUserName(user.getUserName());
		}
		model.addAttribute("record", record);
		model.addAttribute("sj", sdf.format(record.getSj()));
		return "stocks/ckmatStoRecord/showStockRecord";
	}
}
