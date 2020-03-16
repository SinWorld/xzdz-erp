package com.edge.business.sale.saleOrder.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.department.service.inter.ERP_DepartmentService;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.checkProduct.entity.SYS_WorkFlow_Cphd;
import com.edge.business.checkProduct.service.inter.SYS_WorkFlow_CphdService;
import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.entity.ERP_DeliveryOrder;
import com.edge.business.ckfh.service.inter.DeliveryOrderService;
import com.edge.business.ckfh.service.inter.DeliveryService;
import com.edge.business.materialPlan.entity.ERP_MaterialPlan;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;
import com.edge.business.materialPlan.service.inter.MaterialPlanService;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.entity.ProductionPlanOrder;
import com.edge.business.productionPlan.service.inter.ProductionPlanOrderService;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.purchase.entity.ERP_Purchase_List;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseListService;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.entity.ERP_Sales_Contract_Order;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.xshtsk.service.inter.XshtskService;

/**
 * 销售订单控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "saleOrder")
public class SaleOrderController {

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private ApprovalService approvalService;

	@Resource
	private SYS_WorkFlow_CphdService cphdService;

	@Resource
	private DeliveryService deliveryService;

	@Resource
	private DeliveryOrderService deliveryOrderService;

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private ProductionPlanOrderService productionPlanOrderService;

	@Resource
	private ERP_DepartmentService departmentService;

	@Resource
	private ProductService productService;

	@Resource
	private MaterialPlanService materialPlanService;

	@Resource
	private MaterialPlanOrderService materialPlanOrderService;

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private PurchaseListService purchaseListService;

	@Resource
	private CustomerService customerService;

	@Resource
	private XshtskService xshtskService;

	// ajax跳转至销售订到页面且进行验证
	@RequestMapping(value = "/checkSaleOrder.do")
	@ResponseBody
	public String checkSaleOrder(Integer xsddId) {
		JSONObject jsonObject = new JSONObject();
		// 根据销售订单Id获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(xsddId);
		/**
		 * 1.若销售订单审核未通过，且已存在销售订单货物项
		 */
		if (contract.getApprovalDm() != 1) {
			jsonObject.put("flag", false);
			jsonObject.put("infor", "当前销售合同未审批完成!!!");
			return jsonObject.toString();
		}
		if (contract.getIs_xsddprcedf()) {
			jsonObject.put("flag", false);
			jsonObject.put("infor", "当前销售合同已经行销售订单流程!!!");
			return jsonObject.toString();
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 跳转至销售订单页面
	@RequestMapping(value = "/initSaleOrder.do")
	public String initSaleOrder(@RequestParam Integer xsddId, Model model) {
		// 根据销售订单获得销售订单货物项
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(xsddId);
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(xsddId);
		// 获得付款方信息
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
		model.addAttribute("contract", contract);
		model.addAttribute("customer", customer);
		model.addAttribute("ljkpje", xshtskService.querySumLjkpje(xsddId));
		model.addAttribute("ljkpjebl", (xshtskService.querySumLjkpje(xsddId) / contract.getHtje()) * 100 + "%");
		model.addAttribute("sykpje", contract.getHtje() - xshtskService.querySumLjkpje(xsddId));
		model.addAttribute("ljskje", xshtskService.querySumSjskje(xsddId));
		model.addAttribute("ljskjebl", (xshtskService.querySumSjskje(xsddId) / contract.getHtje()) * 100 + "%");
		model.addAttribute("syskje", contract.getHtje() - xshtskService.querySumSjskje(xsddId));
		model.addAttribute("orderList", orderList);
		model.addAttribute("xsddId", xsddId);
		return "business/sale/saleOrder/saveSaleOrder";
	}

	// 跳转至销售订单查看页
	@RequestMapping(value = "/showSaleOrder.do")
	public String showSaleOrder(@RequestParam Integer sales_Contract_Id, Model model) {
		// 格式化计划合同签订日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		// 根据id获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(sales_Contract_Id);
		// 获得销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(contract.getSales_Contract_Id());
		String businessKey = contract.getClass().getSimpleName() + "." + String.valueOf(sales_Contract_Id);
		// 根据businessKey获取历史流程实例对象
		HistoricProcessInstance hisp = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();
		// 若流程实例不为空,则获取流程实例主键
		String processInstanceId = null;
		List<SYS_WorkFlow_PingShenYJ> psyjList = null;
		List<SYS_WorkFlow_Cphd> cphds = null;// 成品核对流程变量数据
		List<ERP_DeliveryOrder> deliveryOrder = null;// 送货单货物项
		ERP_ProductionPlan productionPlan = null;// 生产计划对象
		List<ProductionPlanOrder> productionPlanOrders = null;// 生产计划货物项
		ERP_MaterialPlan materialPlan = null;// 材料计划对象
		List<MaterialPlanOrder> materialPlanOrder = null;// 材料计划货物项
		List<MaterialPlanOrder> ingredients = null;// 加工配料
		List<ERP_Purchase_List> purchaseList = null;// 发起采购
		if (hisp != null) {
			processInstanceId = hisp.getId();
			psyjList = pingShenYjService.psyjList(processInstanceId);
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
				// 判断任务节点为成品核对则取出该数据的主键
				if ("成品核对".equals(p.getTASK_NAME_())) {
					// 得到成品核对的流程数据
					cphds = cphdService.cphds(p.getID_());
				} else if ("生产计划".equals(p.getTASK_NAME_())) {
					// 得到生产计划的流程数据
					productionPlan = productionPlanService.queryPlanByXsht(sales_Contract_Id);
					if (productionPlan != null) {
						// 设置生产部门名称
						ERP_Department department = departmentService.queryDepById(productionPlan.getPlan_Department());
						productionPlan.setPlan_DepartmentName(department.getDep_Name());
						productionPlan.setXddrq(sdf.format(productionPlan.getPlan_Date()));
						productionPlan.setJhkgrq(sdf.format(productionPlan.getPlan_BeginDate()));
						productionPlan.setJhwgrq(sdf.format(productionPlan.getPlan_EndDate()));
					}
					// 获得生产计划货物项数据
					productionPlanOrders = productionPlanOrderService
							.queryPlanOrderByPlanId(productionPlan.getRow_Id());
					// 遍历该集合
					for (ProductionPlanOrder polder : productionPlanOrders) {
						// 根据成品获得成品对象
						ERP_Products product = productService.queryProductById(polder.getProduct());
						polder.setErp_product(product);
					}
				} else if ("材料计划".equals(p.getTASK_NAME_())) {
					// 根据销售合同主键获得材料计划对象
					materialPlan = materialPlanService.queryMaterialPlanByXsht(sales_Contract_Id);
					if (materialPlan != null) {
						materialPlan.setXddrq(sdf.format(materialPlan.getPlan_Date()));
						materialPlan.setJhkgrq(sdf.format(materialPlan.getPlan_BeginDate()));
						materialPlan.setJhwgrq(sdf.format(materialPlan.getPlan_EndDate()));
						// 该生产计划的生产计划货物项
						materialPlanOrder = materialPlanOrderService.queryOrderByMaplanId(materialPlan.getRow_Id());
					}
				} else if ("加工配料".contentEquals(p.getTASK_NAME_())) {
					ingredients = this.processingIngredients(businessKey);
				} else if ("发起采购".equals(p.getTASK_NAME_())) {
					purchaseList = this.purchaseList(sales_Contract_Id);
				}
			}
		}
		// 根据销售合同对象获得送货单对象
		ERP_Delivery delivery = deliveryService.queryDeliveryByXsht(contract.getSales_Contract_Id());
		// 根据送货单对象加载送货单货物项
		if (delivery != null) {
			deliveryOrder = deliveryOrderService.orderList(delivery.getDelivery_Id());
		}
		model.addAttribute("contract", contract);
		model.addAttribute("orderList", orderList);
		model.addAttribute("qdrq", sdf.format(contract.getQd_Date()));
		model.addAttribute("OBJDM", contract.getClass().getSimpleName() + "." + String.valueOf(sales_Contract_Id));
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("cphds", cphds);
		model.addAttribute("deliveryOrder", deliveryOrder);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("productionPlan", productionPlan);
		model.addAttribute("productionPlanOrders", productionPlanOrders);
		model.addAttribute("materialPlan", materialPlan);
		model.addAttribute("materialPlanOrder", materialPlanOrder);
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("purchaseList", purchaseList);
		return "business/sale/saleOrder/showSaleOrder";
	}

	// ajax查看销售订单验证
	@RequestMapping(value = "/checkXsdd.do")
	@ResponseBody
	public String checkXsdd(Integer xsht) {
		JSONObject jsonObject = new JSONObject();
		// 根据id获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(xsht);
		if (contract.getIs_xsddprcedf()) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 提交表单 启动流程
	@RequestMapping(value = "/submitForm.do")
	public String submitForm(@RequestParam Integer xsddId, Model model, HttpServletRequest request) {
		ERP_Sales_Contract contract = contractService.queryContractById(xsddId);
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 设置待办任务描述
		contract.setTask_des("【任务名称：销售订单】");
		contract.setIs_xsddprcedf(true);
		// 编辑销售合同
		contractService.editSalesContract(contract);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String key = contract.getClass().getSimpleName();
		String objId = key + "." + String.valueOf(contractService.maxSalesContract());
		map.put("inputUser", user.getUserId());
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OperationFlow", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "business/sale/saleOrder/saveSaleOrder";
	}

	// 新增流程审批
	private void savelcsp(Task task, ERP_User user) {
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
		alreadyTask.setCOMPLETION_STATUS_("开始");
		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
	}

	// 评审意见加载加工配料项
	private List<MaterialPlanOrder> processingIngredients(String businesskey) {
		// 获得历史流程实例对象
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businesskey).singleResult();
		// 获得历史流程实例Id
		List<MaterialPlanOrder> list = new ArrayList<MaterialPlanOrder>();
		if (historicProcessInstance != null) {
			// 根据历史流程实例Id获得历史流程变量
			HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery()
					.processInstanceId(historicProcessInstance.getId()).variableName("jgpl").singleResult();
			String jgpl = (String) historicVariableInstance.getValue();
			if (jgpl != null && jgpl != "") {
				String[] jgpls = jgpl.split(",");
				for (String j : jgpls) {
					Integer row_Id = null;// 材料计划货物项主键
					Integer cgsl = null;// 采购数量
					String[] datas = j.split(":");
					row_Id = Integer.parseInt(datas[0].trim());
					cgsl = Integer.parseInt(datas[1].trim());
					// 根据row_Id获得材料计划货物项对象
					MaterialPlanOrder order = materialPlanOrderService.queryOrderById(row_Id);
					order.setCgsl(cgsl);
					list.add(order);
				}
			}

		}
		return list;

	}

	// 评审意见加载发起采购项
	private List<ERP_Purchase_List> purchaseList(Integer xshtdm) {
		// 根据销售合同获得采购合同
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderByXsht(xshtdm);
		List<ERP_Purchase_List> list = null;
		// 根据采购合同获得采购合同货物项
		if (purchaseOrder != null) {
			list = purchaseListService.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
		}
		return list;

	}

	// 跳转至销售订单编辑页面
	@RequestMapping(value = "/initEditSaleOrder.do")
	public String initEditSaleOrder(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(contract.getSales_Contract_Id());
		model.addAttribute("orderList", orderList);
		model.addAttribute("xsddId", id);
		model.addAttribute("taskId", taskId);
		return "business/sale/saleOrder/editSaleOrder";
	}

	// 编辑合同
	@RequestMapping(value = "/editSaleOrder.do")
	public String editSaleOrder(@RequestParam Integer xsddId, String taskId, Model model, HttpServletRequest request) {
		ERP_Sales_Contract contract = contractService.queryContractById(xsddId);
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		String key = contract.getClass().getSimpleName();
		String objId = key + "." + String.valueOf(contractService.maxSalesContract());
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTasks(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "business/sale/saleOrder/editSaleOrder";
	}

	// 新增已办数据集
	private void saveAlreadyTasks(Task task, ERP_User user, String objId) {
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
