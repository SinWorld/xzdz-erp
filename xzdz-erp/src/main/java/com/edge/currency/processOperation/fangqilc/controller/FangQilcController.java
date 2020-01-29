package com.edge.currency.processOperation.fangqilc.controller;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;

/**
 * 放弃流程控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "fqlc")
public class FangQilcController {

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService rumtimeService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	// 跳转至放弃流程页面
	@RequestMapping(value = "/initFqlc.do")
	public String initFqlc(@RequestParam String taskId, Integer sales_Contract_Id, Model model) {
		model.addAttribute("taskId", taskId);
		model.addAttribute("sales_Contract_Id", sales_Contract_Id);
		return "currency/processOperation/giveUpProcess";
	}

	// 放弃流程操作
	@RequestMapping(value = "/fqlc.do")
	public String fqlc(@RequestParam String taskId, Integer sales_Contract_Id, String advice, Model model) {
		// 根据taskId找到对应的流程实例Id
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		// 删除销售合同数据
		contractService.deleteConractById(sales_Contract_Id);
		orderService.deleteOrderByContract(sales_Contract_Id);
		// 放弃流程
		rumtimeService.deleteProcessInstance(processInstanceId, advice);
		model.addAttribute("flag", true);
		return "currency/processOperation/giveUpProcess";
	}
}
