package com.edge.business.purchase.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;
import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.service.inter.SupplierService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;
import com.edge.business.purchase.entity.ERP_Purchase_List;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseListService;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 采购模块控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "purchase")
public class PurchaseController {

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private PurchaseListService purchaseListService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private TaskService taskService;

	@Resource
	private MaterialPlanOrderService materialPlanOrderService;

	@Resource
	private CompanyService companyService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private RuntimeService runtimeService;

	// 跳转至采购订单页面
	@RequestMapping(value = "/initPurchase.do")
	public String initPurchase(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 获得加工配料流程变量
		String variable = (String) taskService.getVariable(taskId, "jgpl");
		List<MaterialPlanOrder> orders = new ArrayList<MaterialPlanOrder>();
		if (variable != "" && variable != null) {
			String[] variables = variable.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer row_Id = null;
				Integer cgsl = null;
				String[] datas = v.split(":");
				row_Id = Integer.parseInt(datas[0].trim().trim());
				cgsl = Integer.parseInt(datas[1].trim().trim());
				// 获得row_Id获得材料计划货物项对象
				MaterialPlanOrder order = materialPlanOrderService.queryOrderById(row_Id);
				order.setCgsl(cgsl);
				orders.add(order);
			}
		}
		model.addAttribute("orders", orders);
		model.addAttribute("sales_Contract_Id", id);
		model.addAttribute("taskId", taskId);
		return "business/purchase/savePurchase";
	}

	// ajax加载所有的供货单位
	@RequestMapping(value = "/allSupplier.do")
	@ResponseBody
	public String allSupplier() {
		JSONArray jsonArray = purchaseOrderService.allSupplier();
		return jsonArray.toString();
	}

	// 合同编号生成
	@RequestMapping(value = "/htbh.do")
	@ResponseBody
	public String htbh() {
		JSONObject jsonObject = new JSONObject();
		// 编号规则 CG-年度-月份-日期-3位流水 例如 XZ-2020-01-17-001
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String time = year + "-" + month + "-" + day;
		String bh = "CG" + "-" + time;
		// 根据该编号去模糊检索查询出最大流水号的编号
		String maxHtbh = purchaseOrderService.htbh(bh);
		// 如果最大合同编号为空则流水号为001否则需要切割字符串进行新增
		String newHtbh = null;
		if (maxHtbh == null) {
			newHtbh = bh + "-001";
		} else {
			String maxLiuShuiH = maxHtbh.substring(bh.length() + 1);
			int nextLiuShuiH = Integer.parseInt(maxLiuShuiH) + 1;
			newHtbh = bh + "-" + String.format("%03d", nextLiuShuiH);
		}
		jsonObject.put("htbh", newHtbh);
		return jsonObject.toString();
	}

	// ajax查询供应商设置其属性
	@RequestMapping(value = "/supplier.do")
	@ResponseBody
	public String supplier(Integer supplier_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Supplier supplier = supplierService.querySupplierById(supplier_Id);
		jsonObject.put("supplier", supplier);
		return jsonObject.toString();
	}

	// 提价表单并推动流程
	@RequestMapping(value = "/savePurchaseOrder.do")
	@ResponseBody
	public String savePurchaseOrder(@RequestBody ERP_Purchase_Order purchaseOrder, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(purchaseOrder.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		// 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(purchaseOrder.getSales_Contract_Id());
		ERP_Our_Unit unit = companyService.queryUnitById(contract.getSupplier());
		if (unit != null) {
			purchaseOrder.setOur_uint(unit.getUnit_Id());
		}
		purchaseOrder.setStatus("已下单");
		purchaseOrder.setIs_Availability(false);
		purchaseOrder.setQd_Date(new Date());
		purchaseOrder.setGfqd_Date(new Date());
		purchaseOrder.setSub_Date(new Date());
		purchaseOrder.setUserId(user.getUserId());
		purchaseOrderService.savePurchaseOrder(purchaseOrder);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "领导审核");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增生产计划货物项
	@RequestMapping(value = "/savePurchaseList.do")
	@ResponseBody
	public String savePurchaseList(@RequestBody ERP_Purchase_List[] purchaseList) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Purchase_List p : purchaseList) {
			p.setPur_Order_Id(purchaseOrderService.queryMaxOrderId());
			purchaseListService.savePurchaseList(p);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
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
