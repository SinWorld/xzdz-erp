package com.edge.currency.alreadyTask.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.department.service.inter.ERP_DepartmentService;
import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.service.inter.SupplierService;
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
import com.edge.checkDelivery.entity.CheckDelivery;
import com.edge.checkDelivery.service.inter.CheckDeliveryService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.service.inter.XshtskService;
import com.google.gson.Gson;

/**
 * 已办控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "alreadyTask")
public class AlreadyTaskController {

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private CompanyService companyService;

	@Resource
	private CustomerService customerService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private PingShenYJService pingShenYJService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private HistoryService historyService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private SYS_WorkFlow_CphdService cphdService;

	@Resource
	private DeliveryService deliveryService;

	@Resource
	private DeliveryOrderService deliveryOrderService;

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
	private SupplierService supplierService;

	@Resource
	private XshtskService xshtskService;

	@Resource
	private CheckDeliveryService checkDeliveryService;

	@RequestMapping(value = "/userAlreadyTask.do")
	@ResponseBody
	public String allTask(Integer page, Integer limit, HttpServletRequest request) {
		// 获取session
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		// new出AlreadyTask_QueryVo查询对象
		AlreadyTask_QueryVo vo = new AlreadyTask_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setUserId(user.getUserId());
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", alreadyTaskService.userAlreadyTaskCount(vo));
		List<AlreadyTask> allTaskList = alreadyTaskService.userAlreadyTask(vo);
		// 遍历该集合 设置已办集合
		for (AlreadyTask a : allTaskList) {
			// 取得businesskey
			String businesskey = a.getBUSINESS_KEY_();
			String id = businesskey.substring(businesskey.indexOf(".") + 1);
			// 任务办理人
			a.setUserName(user.getUserName());
			// 任务发起人
			a.setCreateUser(userService.queryUserById(Integer.parseInt(a.getCREATE_USER_())).getUserName());
			// 得到业务数据类型
			String object = businesskey.substring(0, businesskey.indexOf("."));
			if ("ERP_Sales_Contract".equals(object)) {// 表示业务流程（销售订单）
				// 获得ERP_Sales_Contract对象
				ERP_Sales_Contract sales = contractService.queryContractById(Integer.parseInt(id));
				if (sales != null) {
					// 获得任务描述 设置待办任务描述
					String taskDecription = "【" + a.getNAME_() + "】" + "  " + sales.getTask_des();
					a.setTaskDecription(taskDecription);
				}
			} else if ("SalesContract".equals(object)) {
				// 获得ERP_Sales_Contract对象
				ERP_Sales_Contract sales = contractService.queryContractById(Integer.parseInt(id));
				if (sales != null) {
					// 获得任务描述 设置待办任务描述
					String taskDecription = "【" + a.getNAME_() + "】" + "  " + sales.getTask_Describe();
					a.setTaskDecription(taskDecription);
				}
			} else if ("ERP_Purchase_Order".equals(object)) {// 表示采购合同
				// 获得采购合同对象
				ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(Integer.parseInt(id));
				// 获得任务描述 设置待办任务描述
				String taskDecription = "【" + a.getNAME_() + "】" + "  " + purchaseOrder.getTask_Describe();
				a.setTaskDecription(taskDecription);
			} else if ("ERP_Xshtsk".equals(object)) {// 表示销售合同收款
				// 获得销售合同收款对象
				ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(Integer.parseInt(id));
				// 获得任务描述 设置待办任务描述
				String taskDecription = "【" + a.getNAME_() + "】" + "  " + xshtsk.getTask_describe();
				a.setTaskDecription(taskDecription);
			} else if ("CheckDelivery".equals(object)) {// 表示送货单上传
				// 获得送货单上传对象
				CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(Integer.parseInt(id));
				// 获得任务描述 设置待办任务描述
				String taskDecription = "【" + a.getNAME_() + "】" + "  " + checkDelivery.getTask_Describe();
				a.setTaskDecription(taskDecription);
			}
		}
		map.put("data", allTaskList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 点击已办列表进入相应的查看页进行查看
	@RequestMapping(value = "/alreadyTaskInfor.do")
	public String allTaskInfor(@RequestParam Integer id, Model model) {
		AlreadyTask alreadyTask = alreadyTaskService.queryAlreadyTaskById(id);
		String businessKey = alreadyTask.getBUSINESS_KEY_();
		String obj = businessKey.substring(0, businessKey.indexOf("."));
		String objId = businessKey.substring(businessKey.indexOf(".") + 1, businessKey.length());
		// 加载批注信息
		List<SYS_WorkFlow_PingShenYJ> psyjList = pingShenYJService.psyjList(alreadyTask.getPROC_INST_ID_());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		List<SYS_WorkFlow_Cphd> cphds = null;// 成品核对流程变量数据
		List<ERP_DeliveryOrder> deliveryOrder = null;// 送货单货物项
		ERP_ProductionPlan productionPlan = null;// 生产计划对象
		List<ProductionPlanOrder> productionPlanOrders = null;// 生产计划货物项
		ERP_MaterialPlan materialPlan = null;// 材料计划对象
		List<MaterialPlanOrder> materialPlanOrder = null;// 材料计划货物项
		List<MaterialPlanOrder> ingredients = null;// 加工配料
		List<ERP_Purchase_List> purchaseList = null;// 发起采购
		for (SYS_WorkFlow_PingShenYJ p : psyjList) {
			p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
			p.setTime(sdf1.format(p.getTIME_()));
			// 判断任务节点为成品核对则取出该数据的主键
			if ("成品核对".equals(p.getTASK_NAME_())) {
				// 得到成品核对的流程数据
				cphds = cphdService.cphds(p.getID_());
			} else if ("生产计划".equals(p.getTASK_NAME_())) {
				// 得到生产计划的流程数据
				productionPlan = productionPlanService.queryPlanByXsht(Integer.parseInt(objId.trim()));
				if (productionPlan != null) {
					// 设置生产部门名称
					ERP_Department department = departmentService.queryDepById(productionPlan.getPlan_Department());
					productionPlan.setPlan_DepartmentName(department.getDep_Name());
					productionPlan.setXddrq(sdf.format(productionPlan.getPlan_Date()));
					productionPlan.setJhkgrq(sdf.format(productionPlan.getPlan_BeginDate()));
					productionPlan.setJhwgrq(sdf.format(productionPlan.getPlan_EndDate()));
				}
				// 获得生产计划货物项数据
				productionPlanOrders = productionPlanOrderService.queryPlanOrderByPlanId(productionPlan.getRow_Id());
				// 遍历该集合
				for (ProductionPlanOrder polder : productionPlanOrders) {
					// 根据成品获得成品对象
					ERP_Products product = productService.queryProductById(polder.getProduct());
					polder.setErp_product(product);
				}
			} else if ("材料计划".equals(p.getTASK_NAME_())) {
				// 根据销售合同主键获得材料计划对象
				materialPlan = materialPlanService.queryMaterialPlanByXsht(Integer.parseInt(objId.trim()));
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
				purchaseList = this.purchaseList(Integer.parseInt(objId.trim()));
			}
		}
		if ("ERP_Sales_Contract".equals(obj)) {
			ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(objId));
			// 获得供方对象
			ERP_Our_Unit our_Unit = companyService.queryUnitById(contract.getSupplier());
			// 获得需求方对象
			ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
			// 获得销售合同货物清单对象
			List<ERP_Sales_Contract_Order> orderList = orderService.orderList(contract.getSales_Contract_Id());
			// 根据销售合同对象获得送货单对象
			ERP_Delivery delivery = deliveryService.queryDeliveryByXsht(contract.getSales_Contract_Id());
			// 根据送货单对象加载送货单货物项
			if (delivery != null) {
				deliveryOrder = deliveryOrderService.orderList(delivery.getDelivery_Id());
			}
			model.addAttribute("contract", contract);
			model.addAttribute("our_Unit", our_Unit);
			model.addAttribute("customer", customer);
			model.addAttribute("orderList", orderList);
			model.addAttribute("qdrq", sdf.format(contract.getQd_Date()));
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("reviewOpinions", psyjList);
			model.addAttribute("cphds", cphds);
			model.addAttribute("deliveryOrder", deliveryOrder);
			model.addAttribute("processInstanceId", alreadyTask.getPROC_INST_ID_());
			model.addAttribute("productionPlan", productionPlan);
			model.addAttribute("productionPlanOrders", productionPlanOrders);
			model.addAttribute("materialPlan", materialPlan);
			model.addAttribute("materialPlanOrder", materialPlanOrder);
			model.addAttribute("ingredients", ingredients);
			model.addAttribute("purchaseList", purchaseList);
			return "business/sale/saleOrder/showSaleOrder";
		} else if ("SalesContract".equals(obj)) {// 表示销售合同
			ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(objId));
			// 获得供方对象
			ERP_Our_Unit our_Unit = companyService.queryUnitById(contract.getSupplier());
			// 获得需求方对象
			ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
			// 获得销售合同货物清单对象
			List<ERP_Sales_Contract_Order> orderList = orderService.orderList(contract.getSales_Contract_Id());
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
			model.addAttribute("contract", contract);
			model.addAttribute("our_Unit", our_Unit);
			model.addAttribute("customer", customer);
			model.addAttribute("orderList", orderList);
			model.addAttribute("qdrq", sdf.format(contract.getQd_Date()));
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("reviewOpinions", psyjList);
			model.addAttribute("processInstanceId", alreadyTask.getPROC_INST_ID_());
			return "business/sale/saleShow";
		} else if ("ERP_Purchase_Order".equals(obj)) {// 表示采购合同
			ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(Integer.parseInt(objId));
			List<ERP_Purchase_List> purchaseLists = null;
			if (purchaseOrder != null) {
				// 设置属性
				ERP_Supplier supplier = supplierService.querySupplierById(purchaseOrder.getSupplier());
				purchaseOrder.setSupplierName(supplier.getSupplier_Name());
				purchaseOrder.setTelPhone(supplier.getPhone());
				purchaseOrder.setDgrq(sdf.format(purchaseOrder.getPur_Date()));
				// 获得采购清单集合
				purchaseLists = purchaseListService.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
			}
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
			model.addAttribute("purchaseOrder", purchaseOrder);
			model.addAttribute("purchaseList", purchaseLists);
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("reviewOpinions", psyjList);
			model.addAttribute("processInstanceId", alreadyTask.getPROC_INST_ID_());
			return "business/purchase/purchaseOrder/purchaseOrderShow";
		} else if ("ERP_Xshtsk".equals(obj)) {// 表示销售合同收款
			ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(Integer.parseInt(objId));
			if (xshtsk.getSqkprq() != null) {
				xshtsk.setShenqkprq(sdf.format(xshtsk.getSqkprq()));
			}
			if (xshtsk.getKprq() != null) {
				xshtsk.setKaiprq(sdf.format(xshtsk.getKprq()));
			}
			// 获取销售合同
			ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
			// 获得付款方信息 sdf
			ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
			if (xshtsk.getIs_fpkj()) {
				model.addAttribute("fpkj", "是");
			} else {
				model.addAttribute("fpkj", "否");
			}
			if (xshtsk.getIs_fplb()) {
				model.addAttribute("fplb", "增值税专用发票");
			} else {
				model.addAttribute("fplb", "增值税普通发票");
			}
			model.addAttribute("xshtsk", xshtsk);
			model.addAttribute("contract", contract);
			model.addAttribute("customer", customer);
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("reviewOpinions", psyjList);
			model.addAttribute("processInstanceId", alreadyTask.getPROC_INST_ID_());
			model.addAttribute("ljkpje", xshtskService.querySumLjkpje(xshtsk.getXsht()));
			model.addAttribute("ljkpjebl",
					(xshtskService.querySumLjkpje(xshtsk.getXsht()) / contract.getHtje()) * 100 + "%");
			model.addAttribute("sykpje", contract.getHtje() - xshtskService.querySumLjkpje(xshtsk.getXsht()));
			model.addAttribute("ljskje", xshtskService.querySumSjskje(xshtsk.getXsht()));
			model.addAttribute("ljskjebl",
					(xshtskService.querySumSjskje(xshtsk.getXsht()) / contract.getHtje()) * 100 + "%");
			model.addAttribute("syskje", contract.getHtje() - xshtskService.querySumSjskje(xshtsk.getXsht()));
			return "xshtsk/xshtskShow";
		} else if ("CheckDelivery".equals(obj)) {// 表示送货单上传
			CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(Integer.parseInt(objId));
			if (checkDelivery.getCreateTime() != null) {
				checkDelivery.setStartTime(sdf.format(checkDelivery.getCreateTime()));
			}
			// 送货单对象
			ERP_Delivery delivery = deliveryService.queryDeliveryById(checkDelivery.getDelivery_Id());
			// 获取销售合同
			ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
			model.addAttribute("checkDelivery", checkDelivery);
			model.addAttribute("contract", contract);
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("reviewOpinions", psyjList);
			model.addAttribute("processInstanceId", alreadyTask.getPROC_INST_ID_());
			model.addAttribute("ljkpje", xshtskService.querySumLjkpje(contract.getSales_Contract_Id()));
			model.addAttribute("ljkpjebl",
					(xshtskService.querySumLjkpje(contract.getSales_Contract_Id()) / contract.getHtje()) * 100 + "%");
			model.addAttribute("sykpje",
					contract.getHtje() - xshtskService.querySumLjkpje(contract.getSales_Contract_Id()));
			model.addAttribute("ljskje", xshtskService.querySumSjskje(contract.getSales_Contract_Id()));
			model.addAttribute("ljskjebl",
					(xshtskService.querySumSjskje(contract.getSales_Contract_Id()) / contract.getHtje()) * 100 + "%");
			model.addAttribute("syskje",
					contract.getHtje() - xshtskService.querySumSjskje(contract.getSales_Contract_Id()));
			return "checkDelivery/checkDeliveryShow";
		} else {
			return null;
		}
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

}
