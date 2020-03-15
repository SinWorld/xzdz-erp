package com.edge.checkDelivery.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 送货单核对控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "shdsh")
public class ShdhdController {

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	// 跳转至送货单核对页面
	@RequestMapping(value = "/initShdsh.do")
	public String initShdsh(@RequestParam String objId, String taskId, Model model) {
		String rowId = objId.substring(objId.indexOf(".") + 1);
		model.addAttribute("rowId", rowId);
		model.addAttribute("taskId", taskId);
		return "checkDelivery/saveshdhd";
	}

	// 提交表单推动流程
	@RequestMapping(value = "/saveshdsh.do")
	public String saveshdsh(@RequestParam String task_Id, String out_come, String advice_, HttpServletRequest request,
			Model model) {
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		if (out_come != null) {
			variables.put("outcome", out_come);
		}
		this.savelcsp(task, user, out_come, advice_);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "checkDelivery/saveshdhd";
	}

	// 新增流程审批
	private void savelcsp(Task task, ERP_User user, String outcome, String advice) {
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(task.getProcessInstanceId());
		r.setTASK_ID_(task.getId());
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		r.setTASK_NAME_(task.getName());
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(outcome);
		r.setMESSAGE_INFOR_(advice);
		r.setTITLE_("已办理");
		pingShenYjService.savePingShenYJ(r);
	}

	// 新增已办数据集
	private void saveAlreadyTask(Task task, ERP_User user, String objId) {
		AlreadyTask alreadyTask = new AlreadyTask();
		alreadyTask.setTASK_ID_(task.getId());
		alreadyTask.setREV_(null);
		alreadyTask.setEXECUTION_ID_(task.getExecutionId());
		alreadyTask.setPROC_INST_ID_(task.getProcessInstanceId());
		alreadyTask.setPROC_DEF_ID_(task.getProcessDefinitionId());
		alreadyTask.setNAME_(task.getName());
		alreadyTask.setPARENT_TASK_ID_(task.getParentTaskId());
		alreadyTask.setDESCRIPTION_(task.getDescription());
		alreadyTask.setTASK_DEF_KEY_(task.getTaskDefinitionKey());
		alreadyTask.setOWNER_(task.getOwner());
		alreadyTask.setASSIGNEE_(String.valueOf(user.getUserId()));
		alreadyTask.setDELEGATION_(null);
		alreadyTask.setPRIORITY_(task.getPriority());
		alreadyTask.setSTART_TIME_(task.getCreateTime());
		alreadyTask.setEND_TIME_(new Date());
		alreadyTask.setFORM_KEY_(task.getFormKey());
		alreadyTask.setBUSINESS_KEY_(objId);
		alreadyTask.setCOMPLETION_STATUS_("审批中");
		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
	}
}
