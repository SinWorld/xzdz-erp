package com.edge.currency.processOperation.zhuanjiao.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 转交控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "zj")
public class ZhuanJiaoController {

	@Resource
	private ERP_UserService userService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	// 跳转至转交页面
	@RequestMapping(value = "/initZhuanJiao.do")
	public String initZhuanJiao(@RequestParam String taskId,HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 查询当前系统中国所有未删除的用户
		List<ERP_User> list = userService.erp_userList(user.getUserId());
		model.addAttribute("list", list);
		model.addAttribute("taskId", taskId);
		return "currency/processOperation/zhuanJiao";
	}

	// 转交操作
	@RequestMapping(value = "/zhuanJiao.do")
	public String zhuanJiao(@RequestParam String taskId, String userIds, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		String ids = userIds.substring(1, userIds.length());
		String[] userids = ids.split(",");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.deleteCandidateUser(taskId, String.valueOf(user.getUserId()));
		for (String id : userids) {
			taskService.addCandidateUser(taskId, id);
			// new出SYS_WorkFlow_PingShenYJ对象
			SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
			r.setPROC_INST_ID_(task.getProcessInstanceId());
			r.setTASK_ID_(task.getId());
			r.setTIME_(new Date());
			r.setUSER_ID_(user.getUserId());
			r.setTASK_NAME_(task.getName());
			r.setUserName(user.getUserName());
			r.setMESSAGE_RESULT_(null);
			r.setMESSAGE_INFOR_(null);
			r.setTITLE_("转交至" + userService.queryUserById(Integer.parseInt(id)).getUserName() + "办理");
			pingShenYjService.savePingShenYJ(r);
		}
		model.addAttribute("flag", true);
		return "currency/processOperation/zhuanJiao";
	}

}
