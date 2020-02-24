package com.edge.business.leadershipAudit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 领导审核控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "leadershipAudit")
public class LeadershipAuditController {

	// 跳转至领导审核页面
	@RequestMapping(value = "/initLeadershipAudit.do")
	public String initLeadershipAudit(@RequestParam String objId, String taskId, Model model) {
		model.addAttribute("objId", objId);
		model.addAttribute("taskId", taskId);
		return "business/leadershipAudit/leadershipAudit";
	}
}
