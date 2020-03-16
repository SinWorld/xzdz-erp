package com.edge.currency.processOperation.fangqilc.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;
import com.edge.cghtfk.entity.ERP_Cghtfk;
import com.edge.cghtfk.service.inter.CghtfkService;
import com.edge.checkDelivery.entity.CheckDelivery;
import com.edge.checkDelivery.service.inter.CheckDeliveryService;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.service.inter.XshtskService;

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

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private PingShenYJService psyjService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private XshtskService xshtskService;

	@Resource
	private CheckDeliveryService checkDeliveryService;

	@Resource
	private CghtfkService cghtfkService;

	// 跳转至放弃流程页面
	@RequestMapping(value = "/initFqlc.do")
	public String initFqlc(@RequestParam String taskId, Integer sales_Contract_Id, Model model) {
		model.addAttribute("taskId", taskId);
		model.addAttribute("sales_Contract_Id", sales_Contract_Id);
		return "currency/processOperation/giveUpProcess";
	}

	// 放弃流程操作
	@RequestMapping(value = "/fqlc.do")
	public String fqlc(@RequestParam String taskId, Integer objId, String advice, Model model,
			HttpServletRequest request) {
		// 根据taskId找到对应的流程实例Id
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = rumtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		// 获取路程部署的Key
		String key = processDefinition.getKey();
		// 放弃流程
		rumtimeService.deleteProcessInstance(processInstanceId, advice);
		this.savelcsp(task, user, null, null);
		if ("OperationFlow".equals(key) || "SalesContract".equals(key)) {// 表示销售订单、销售合同
			ERP_Sales_Contract contract = contractService.queryContractById(objId);
			contract.setApprovalDm(3);
			contractService.editSalesContract(contract);
		} else if ("PurchaseOrder".equals(key)) {// 表示采购合同
			ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(objId);
			purchaseOrder.setApprovalDm(3);
			purchaseOrderService.editPurchaseOrder(purchaseOrder);
		} else if ("Xshtsk".equals(key)) {// 表示销售合同收款
			ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(objId);
			xshtsk.setApprovaldm(3);
			xshtskService.editXshtsk(xshtsk);
		} else if ("CheckDelivery".equals(key)) {// 表示送货单上传
			CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(objId);
			checkDelivery.setApprovalDm(3);
			checkDeliveryService.editCheckDelivery(checkDelivery);
		} else if ("Cghtfk".equals(key)) {// 表示采购合同付款
			ERP_Cghtfk cghtfk = cghtfkService.queryCghtfkById(objId);
			cghtfk.setApprovalDm(3);
			cghtfkService.editCghtsk(cghtfk);
		}
		// this.saveAlreadyTask(task, user, processInstance.getBusinessKey());
		model.addAttribute("flag", true);
		return "currency/processOperation/giveUpProcess";
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
		r.setTITLE_("已终止");
		pingShenYjService.savePingShenYJ(r);
	}

	// 新增已办数据集
//	private void saveAlreadyTask(Task task, ERP_User user, String objId) {
//		AlreadyTask alreadyTask = new AlreadyTask();
//		alreadyTask.setTASK_ID_(task.getId());
//		alreadyTask.setREV_(null);
//		alreadyTask.setEXECUTION_ID_(task.getExecutionId());
//		alreadyTask.setPROC_INST_ID_(task.getProcessInstanceId());
//		alreadyTask.setPROC_DEF_ID_(task.getProcessDefinitionId());
//		alreadyTask.setNAME_(task.getName());
//		alreadyTask.setPARENT_TASK_ID_(task.getParentTaskId());
//		alreadyTask.setDESCRIPTION_(task.getDescription());
//		alreadyTask.setTASK_DEF_KEY_(task.getTaskDefinitionKey());
//		alreadyTask.setOWNER_(task.getOwner());
//		alreadyTask.setASSIGNEE_(String.valueOf(user.getUserId()));
//		alreadyTask.setDELEGATION_(null);
//		alreadyTask.setPRIORITY_(task.getPriority());
//		alreadyTask.setSTART_TIME_(task.getCreateTime());
//		alreadyTask.setEND_TIME_(new Date());
//		alreadyTask.setFORM_KEY_(task.getFormKey());
//		alreadyTask.setBUSINESS_KEY_(objId);
//		alreadyTask.setCOMPLETION_STATUS_("已终止");
//		// 设置任务发起人
//		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
//		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
//		alreadyTaskService.saveAlreadyTask(alreadyTask);
//	}
}
