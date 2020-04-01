package com.edge.currency.enclosure.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.google.gson.Gson;

/**
 * 附件控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "enclosure")
public class EnclosureController {

	@Resource
	private EnclosureService enclosureService;

	// 查询合同附件列表
	@RequestMapping(value = "/enclosureList.do")
	@ResponseBody
	public String enclosureList(Integer page, Integer limit, String OBJDM) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", enclosureService.enclosureList(OBJDM).size());
		map.put("data", enclosureService.enclosureList(OBJDM));
		String json = gson.toJson(map);
		return json.toString();
	}

}
