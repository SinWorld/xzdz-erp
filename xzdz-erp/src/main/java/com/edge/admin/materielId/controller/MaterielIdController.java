package com.edge.admin.materielId.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.post.entity.ERP_DM_Post;
import com.google.gson.Gson;

/**
 * 物料Id控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materielId")
public class MaterielIdController {
	@Resource
	private MaterielIdService materielIdService;

	// 跳转至物料Id列表页面
	@RequestMapping(value = "/initMaterielIdList.do")
	public String initMaterielIdList() {
		return "admin/materielId/materielIdList";
	}

	// easyUI 列表查询
	@RequestMapping(value = "/materielIdList.do")
	@ResponseBody
	public String materielIdList(@RequestParam Integer page, Integer rows, String wlId, String ggxh, String bzq,
			String type, String ckj1, String ckj2) {
		// new出查询对象
		ERP_MaterielId_QueryVo vo = new ERP_MaterielId_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		vo.setWlId(wlId);
		vo.setGgxh(ggxh);
		vo.setBzq(bzq);
		vo.setType(type);
		if (ckj1 != null && ckj1 != "") {
			vo.setCkj1(Double.valueOf(ckj1.trim()));
		}
		if (ckj2 != null && ckj2 != "") {
			vo.setCkj2(Double.valueOf(ckj2.trim()));
		}
		List<ERP_MaterielId> materielIdList = materielIdService.materielIdList(vo);
		for (ERP_MaterielId m : materielIdList) {
			if (m.getType()) {
				m.setTypeName("材料");
			} else {
				m.setTypeName("成品");
			}
		}
		map.put("total", materielIdService.materielIdCount(vo));
		map.put("rows", materielIdList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至新增页面
	@RequestMapping(value = "/initSaveMaterielId.do")
	public String initSaveMaterielId() {
		return "admin/materielId/saveMaterielId";
	}

	// ajax物料Id不重复
	@RequestMapping(value = "/wlIdbcf.do")
	@ResponseBody
	public String wlIdbcf(String materiel_Id, boolean type) {
		JSONObject jsonObject = new JSONObject();
		ERP_MaterielId erp_MaterielId = materielIdService.queryERP_MaterielIdByWLID(materiel_Id.trim(), type);
		if (erp_MaterielId != null) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 新增物料Id
	@RequestMapping(value = "/saveMaterielId.do")
	public String saveMaterielId(ERP_MaterielId materielId, Model model) {
		materielIdService.saveMaterielId(materielId);
		model.addAttribute("flag", true);
		return "admin/materielId/saveMaterielId";
	}

	// 跳转至物料编辑页面
	@RequestMapping(value = "/initEditMaterielId.do")
	public String initEditMaterielId(@RequestParam Integer row_Id, Model model) {
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		model.addAttribute("materielId", materielId);
		return "admin/materielId/editMaterielId";
	}

	// 编辑操作
	@RequestMapping(value = "/editMaterielId.do")
	public String editMaterielId(ERP_MaterielId materielId, Model model) {
		materielIdService.editMaterielId(materielId);
		model.addAttribute("flag", true);
		return "admin/materielId/editMaterielId";
	}

	// 删除操作
	@RequestMapping(value = "/deleteMaterielId.do")
	@ResponseBody
	public String deleteMaterielId(Integer row_Id) {
		JSONObject jsonObject = new JSONObject();
		materielIdService.deleteMaterelId(row_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 批量删除
	@RequestMapping(value = "/batchDeletePost.do")
	@ResponseBody
	public String batchDeletePost(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			materielIdService.deleteMaterelId(Integer.parseInt(id.trim()));
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 查看操作
	@RequestMapping(value = "/showMaterielId.do")
	public String showMaterielId(@RequestParam Integer row_Id, Model model) {
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		if (materielId.getType()) {
			materielId.setTypeName("材料");
		} else {
			materielId.setTypeName("成品");
		}
		model.addAttribute("materielId", materielId);
		return "admin/materielId/showMaterielId";
	}

}
