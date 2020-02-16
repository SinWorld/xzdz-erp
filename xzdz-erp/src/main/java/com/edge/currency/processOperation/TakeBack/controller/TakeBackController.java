package com.edge.currency.processOperation.TakeBack.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 流程退回的控制跳转层
 *
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "takeBack")
public class TakeBackController {

	@Resource
	private HistoryService historyService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	// 跳转至流程退回页面
	@RequestMapping(value = "/initTakeBack.do")
	public String initTakeBack(@RequestParam String taskId, Model model) {
		model.addAttribute("taskId", taskId);
		return "currency/processOperation/returnProcess";
	}

	// 提交表单执行退回操作
	@RequestMapping(value = "/takeBack.do")
	public String takeBack(@RequestParam String taskId, String advice, HttpServletRequest request, Model model) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 当前节点流转的上一节点信息
		HistoricTaskInstance taskInstance = this.processRecord(task.getProcessInstanceId());
		// 取得该节点的节点名称
		String nodeName = taskInstance.getName();
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		// 根据不同的流程节点名称设置流程变量条件
		if ("成品核对".equals(task.getName())) {
			variables.put("outcome", "退回");
			variables.put("node", nodeName);
		}
		if ("生产计划".equals(task.getName())) {
			variables.put("outcome", "退回");
			variables.put("node", nodeName);
		}
		// 当某个节点有多个退回时根据流转的上个节点名称动态设置流程变量
		if ("成品核对".equals(nodeName)) {
			variables.put("outcome", "退回");
			variables.put("node", nodeName);
		}
		if ("成品入库".equals(nodeName)) {
			variables.put("outcome", "退回");
			variables.put("node", nodeName);
		}
		this.savelcsp(task, user, "退回", advice, nodeName);
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables);
		model.addAttribute("flag", true);
		return "currency/processOperation/returnProcess";
	}

	// 新增流程审批
	private void savelcsp(Task task, ERP_User user, String outcome, String advice, String nodeName) {
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(task.getProcessInstanceId());
		r.setTASK_ID_(task.getId());
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		if ("成品核对".equals(task.getName())) {
			r.setTASK_NAME_("【" + task.getName() + "】");
		} else {
			r.setTASK_NAME_(task.getName());
		}
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(outcome);
		r.setMESSAGE_INFOR_(advice);
		r.setTITLE_("已退回至" + nodeName);
		pingShenYjService.savePingShenYJ(r);
	}

	private HistoricTaskInstance processRecord(String processInstanceId) {
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().desc().list();
		// 出去当前节点本身
		return list.get(1);
	}

}
