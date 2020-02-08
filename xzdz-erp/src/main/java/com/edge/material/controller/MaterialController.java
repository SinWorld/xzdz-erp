package com.edge.material.controller;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.entity.ERP_RAW_Material_QueryVo;
import com.edge.material.service.inter.MaterialService;
import com.edge.product.entity.ERP_Products;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.google.gson.Gson;

/**
 * 材料信息控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "material")
public class MaterialController {
	@Resource
	private MaterialService materialService;

	@Resource
	private Mat_StockRecordService recordService;

	// 跳转至材料列表页面
	@RequestMapping(value = "/initMaterialList.do")
	public String initMaterialList() {
		return "material/materialList";
	}

	// 分页查询材料列表
	@RequestMapping(value = "/materialList.do")
	@ResponseBody
	public String materialList(Integer page, Integer limit, String material_Name, String specification_Type,
			String unit) {
		// new出ERP_RAW_Material_QueryVo查询对象
		ERP_RAW_Material_QueryVo vo = new ERP_RAW_Material_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		if (material_Name != null && material_Name != "") {
			vo.setMaterial_Name(material_Name.trim());
		}
		if (specification_Type != null && specification_Type != "") {
			vo.setSpecification_Type(specification_Type.trim());
		}
		if (unit != null && unit != "") {
			vo.setUnit(unit.trim());
		}
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", materialService.materialCount(vo));
		map.put("data", materialService.materialList(vo));
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至材料新增页面
	@RequestMapping(value = "/initSaveMaterial.do")
	public String initSaveMaterial() {
		return "material/saveMaterial";
	}

	// 新增操作
	@RequestMapping(value = "/saveMaterial.do")
	public String saveMaterial(ERP_RAW_Material material, Model model) {
		material.setMaterial_Code(this.clbh());
		material.setIs_ck(false);
		material.setIs_rk(false);
		material.setIs_allrk(false);
		material.setIs_allck(false);
		materialService.saveMaterial(material);
		model.addAttribute("flag", true);
		return "material/saveMaterial";
	}

	// 生成材料编号
	private String clbh() {
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
		String bh = "CL" + "-" + time + x;
		return bh;
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditMaterial.do")
	public String initEditMaterial(@RequestParam Integer raw_Material_Id, Model model) {
		// 根据Id获得材料信息对象
		ERP_RAW_Material material = materialService.queryMaterialById(raw_Material_Id);
		model.addAttribute("material", material);
		return "material/editMaterial";
	}

	// 编辑操作
	@RequestMapping(value = "/editMaterial.do")
	public String editMaterial(ERP_RAW_Material material, Model model) {
		materialService.editMaterial(material);
		model.addAttribute("flag", true);
		return "material/editMaterial";
	}

	// 查看操作
	@RequestMapping(value = "/showMaterial.do")
	public String showMaterial(@RequestParam Integer raw_Material_Id, Model model) {
		// 根据Id获得材料信息对象
		ERP_RAW_Material material = materialService.queryMaterialById(raw_Material_Id);
		model.addAttribute("material", material);
		return "material/showMaterial";
	}

	// 删除操作
	@RequestMapping(value = "/deleteMaterial.do")
	@ResponseBody
	public String deleteMaterial(Integer raw_Material_Id) {
		JSONObject jsonObject = new JSONObject();
		materialService.deleteMaterial(raw_Material_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 点击入库跳转至库存穿梭框页面
	@RequestMapping(value = "/rkMaterialStock.do")
	public String rkMaterialStock(@RequestParam Integer raw_Material_Id, Model model) {
		// 根据成品Id获得材料对象
		ERP_RAW_Material material = materialService.queryMaterialById(raw_Material_Id);
		// 加载当前成品的入库数量
		Integer rkNumber = recordService.queryMatRkNumber(material.getRaw_Material_Id());
		model.addAttribute("material", material);
		model.addAttribute("rkNumber", rkNumber);
		return "material/rkMaterialStock";
	}
}
