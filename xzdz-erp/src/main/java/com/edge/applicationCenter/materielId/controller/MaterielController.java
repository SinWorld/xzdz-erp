package com.edge.applicationCenter.materielId.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.google.gson.Gson;

/**
 * 物料Id控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "wlId")
public class MaterielController {

	@Resource
	private MaterielIdService materielIdService;

	// 跳转至物料Id列表页面
	@RequestMapping(value = "/initWlId.do")
	public String initWlId() {
		return "applicationCenter/materielId/materielIdList";
	}

	// 列表查询
	@RequestMapping(value = "/materielIdList.do")
	@ResponseBody
	public String materielIdList(@RequestParam Integer page, Integer limit, String wlId, String ggxh, String bzq,
			String type, String ckj1, String ckj2) {
		// new出查询对象
		ERP_MaterielId_QueryVo vo = new ERP_MaterielId_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
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
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", materielIdService.materielIdCount(vo));
		map.put("data", materielIdList);
		String json = gson.toJson(map);
		return json.toString();
	}
}
