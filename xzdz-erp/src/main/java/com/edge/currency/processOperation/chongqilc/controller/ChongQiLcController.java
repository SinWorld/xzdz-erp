package com.edge.currency.processOperation.chongqilc.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 重启流程控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "cqlc")
public class ChongQiLcController {

	@Resource
	private RuntimeService runTimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	// 跳转至重启流程页面
	@RequestMapping(value = "/initCqlc.do")
	public String initCqlc(@RequestParam String taskId, Model model) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 查询当前流程状态
		Execution exe = runTimeService.createExecutionQuery().activityId(task.getTaskDefinitionKey()).singleResult();
		boolean flag = exe.isSuspended();
		model.addAttribute("taskId", taskId);
		model.addAttribute("zt", flag);
		return "currency/processOperation/resertProcess";
	}

	// 中断流程
	@RequestMapping(value = "/zdlc.do")
	public String zdlc(String taskId, Model model, HttpServletRequest request) {
		ERP_User user = (ERP_User) request.getSession().getAttribute("user");
		// 获得流程实例Id
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runTimeService.suspendProcessInstanceById(processInstanceId);
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(processInstanceId);
		r.setTASK_ID_(taskId);
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		r.setTASK_NAME_("【"+task.getName()+"】");
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(null);
		r.setMESSAGE_INFOR_(null);
		r.setTITLE_("流程已中断");
		pingShenYjService.savePingShenYJ(r);
		model.addAttribute("flag", true);
		return "currency/processOperation/resertProcess";
	}

	// 恢复流程
	@RequestMapping(value = "/hflc.do")
	public String hflc(String taskId, Model model, HttpServletRequest request) {
		ERP_User user = (ERP_User) request.getSession().getAttribute("user");
		// 获得流程实例Id
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runTimeService.activateProcessInstanceById(processInstanceId);
		model.addAttribute("flag", true);
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(processInstanceId);
		r.setTASK_ID_(taskId);
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		r.setTASK_NAME_("【"+task.getName()+"】");
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(null);
		r.setMESSAGE_INFOR_(null);
		r.setTITLE_("流程已恢复");
		pingShenYjService.savePingShenYJ(r);
		return "currency/processOperation/resertProcess";
	}
}
