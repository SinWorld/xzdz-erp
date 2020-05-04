package com.edge.currency.processView.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.currency.processView.service.inter.WorkFlowLcjsService;
import com.google.gson.Gson;

/**
 * 流程检视控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "processView")
public class ProcessViewController {

	@Resource
	private WorkFlowLcjsService workFlowLcjsService;

	// 查询流程检视列表
	@RequestMapping(value = "/processViewList.do")
	@ResponseBody
	public String processViewList(String OBJDM) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", workFlowLcjsService.lcjsList(OBJDM).size());
		map.put("data", workFlowLcjsService.lcjsList(OBJDM));
		String json = gson.toJson(map);
		return json.toString();
	}

}
