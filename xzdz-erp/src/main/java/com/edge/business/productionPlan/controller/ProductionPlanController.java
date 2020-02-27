package com.edge.business.productionPlan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.edge.admin.user.entity.ERP_User;
import com.edge.business.checkProduct.entity.XZ_Product;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.entity.ProductionPlanOrder;
import com.edge.business.productionPlan.service.inter.ProductionPlanOrderService;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.entity.ERP_Sales_Contract_Order;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;

/**
 * 生产计划控制跳转成
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "prouctPlan")
public class ProductionPlanController {

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private ProductionPlanOrderService productionPlanOrderService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private TaskService taskService;

	@Resource
	private ProductService productService;

	@Resource
	private KC_StockService kcStockService;

	@Resource
	private Pro_StockService productStoService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	// 跳转至生产计划页面
	@RequestMapping(value = "/initProuctPlan.do")
	public String initProuctPlan(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据id得到销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(Integer.parseInt(id));
		// 格式化订单日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 获取流程变量中的闲置成品
		String variable = (String) taskService.getVariable(taskId, "cphd");
		List<XZ_Product> list = new ArrayList<XZ_Product>();
		List<ProductionPlanOrder> planOrders = new ArrayList<ProductionPlanOrder>();
		if (variable != "" && variable != null) {
			String[] variables = variable.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer stockId = null;
				Integer productId = null;
				Integer cksl = null;
				String[] datas = v.split(":");
				for (int i = 0; i < datas.length; i++) {
					stockId = Integer.parseInt(datas[0].trim().trim());
					productId = Integer.parseInt(datas[1].trim().trim());
					cksl = Integer.parseInt(datas[2].trim().trim());
					break;
				}
				// 获得成品对象
				ERP_Products product = productService.queryProductById(productId);
				// new 出闲置成品对象
				XZ_Product ps = new XZ_Product();
				// 库存数量
				ps.setStock_Id(stockId);
				// 获得改成品的库存对像
				ERP_Stock erp_stock = kcStockService.queryStockByCPId(productId, stockId);
				ps.setKcNumber(erp_stock.getSl());
				ps.setProductName(product.getProduct_Name());
				ps.setGgxh(product.getSpecification_Type());
				ps.setProduct_Id(product.getProduct_Id());
				// 获得成品库存对象
				ERP_Product_Stock product_stock = productStoService.queryPro_StockById(erp_stock.getStock_Id());
				ps.setStock(product_stock.getStock());
				ps.setCksl(cksl);
				ps.setMaterielId(erp_stock.getMaterielId());
				list.add(ps);
			}
		}
		// 加载生产计划项
		for (ERP_Sales_Contract_Order o : orderList) {
			// 根据销售订单物料Id得到所有的库存不为0的成品主键
			List<Integer> productIds = productionPlanService.planProductId(o.getMaterielId());
			// 遍历该集合
			for (Integer pid : productIds) {
				ProductionPlanOrder planOrder = new ProductionPlanOrder();
				// 获得成品对象
				ERP_Products product = productService.queryProductById(pid);
				if (product != null) {
					planOrder.setProduct(product.getProduct_Id());
					planOrder.setMaterielId(product.getMaterielid());
					planOrder.setErp_product(product);
					planOrders.add(planOrder);
				}
			}
		}
		// 生成生产计划号
		String contract_Code = contract.getContract_Code();
		String plan_Code = contract_Code.replace("XZ", "SC");
		model.addAttribute("plan_Code", plan_Code);
		model.addAttribute("plan_Date", sdf.format(contract.getQd_Date()));
		model.addAttribute("contract", contract);
		model.addAttribute("orderList", orderList);
		model.addAttribute("planOrders", planOrders);
		model.addAttribute("list", list);
		model.addAttribute("taskId", taskId);
		return "business/productionPlan/saveProductionPlan";
	}

	// ajxa加载所有的部门
	@RequestMapping(value = "/allDepartment.do")
	@ResponseBody
	public String allDepartment() {
		JSONArray departments = productionPlanService.allDepartment();
		return departments.toString();
	}

	// 提交表单新增生产计划及推动流程
	@RequestMapping(value = "/saveProductionPlan.do")
	@ResponseBody
	public String saveProductionPlan(@RequestBody ERP_ProductionPlan productionPlan, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(productionPlan.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		productionPlanService.saveProductionPlan(productionPlan);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "材料计划");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增生产计划货物项
	@RequestMapping(value = "/saveProductionPlanOrder.do")
	@ResponseBody
	public String saveProductionPlanOrder(@RequestBody ProductionPlanOrder[] productionPlanOrders) {
		JSONObject jsonObject = new JSONObject();
		for (ProductionPlanOrder p : productionPlanOrders) {
			p.setProductionPlanId(productionPlanService.queryMaxProductionPlanId());
			productionPlanOrderService.saveProductionPlanOrder(p);
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

	// 跳转至生产计划编辑页
	@RequestMapping(value = "/initEditProuctPlan.do")
	public String initEditProuctPlan(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据id得到销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(Integer.parseInt(id));
		// 格式化订单日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 根据销售合同Id获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 格式化生产计划日期
		if (productionPlan != null) {
			productionPlan.setXddrq(sdf.format(productionPlan.getPlan_Date()));
			productionPlan.setJhkgrq(sdf.format(productionPlan.getPlan_BeginDate()));
			productionPlan.setJhwgrq(sdf.format(productionPlan.getPlan_EndDate()));
		}
		// 根据该生产计划获得对应的生产计划货物项集合
		List<ProductionPlanOrder> planOrders = productionPlanOrderService
				.queryPlanOrderByPlanId(productionPlan.getRow_Id());
		// 遍历该集合设置成品属性
		for (ProductionPlanOrder p : planOrders) {
			// 根据成品Id获得成品对象
			ERP_Products product = productService.queryProductById(p.getProduct());
			p.setErp_product(product);
		}
		// 获取流程变量中的闲置成品
		String variable = (String) taskService.getVariable(taskId, "cphd");
		List<XZ_Product> list = new ArrayList<XZ_Product>();
		if (variable != "" && variable != null) {
			String[] variables = variable.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer stockId = null;
				Integer productId = null;
				Integer cksl = null;
				String[] datas = v.split(":");
				stockId = Integer.parseInt(datas[0].trim().trim());
				productId = Integer.parseInt(datas[1].trim().trim());
				cksl = Integer.parseInt(datas[2].trim().trim());
				// 获得成品对象
				ERP_Products product = productService.queryProductById(productId);
				// new 出闲置成品对象
				XZ_Product ps = new XZ_Product();
				// 库存数量
				ps.setStock_Id(stockId);
				// 获得改成品的库存对像
				ERP_Stock erp_stock = kcStockService.queryStockByCPId(productId, stockId);
				ps.setKcNumber(erp_stock.getSl());
				ps.setProductName(product.getProduct_Name());
				ps.setGgxh(product.getSpecification_Type());
				ps.setProduct_Id(product.getProduct_Id());
				// 获得成品库存对象
				ERP_Product_Stock product_stock = productStoService.queryPro_StockById(erp_stock.getStock_Id());
				ps.setStock(product_stock.getStock());
				ps.setCksl(cksl);
				ps.setMaterielId(erp_stock.getMaterielId());
				list.add(ps);
			}
		}
		model.addAttribute("productionPlan", productionPlan);
		model.addAttribute("contract", contract);
		model.addAttribute("orderList", orderList);
		model.addAttribute("planOrders", planOrders);
		model.addAttribute("list", list);
		model.addAttribute("taskId", taskId);
		return "business/productionPlan/editProductionPlan";
	}

	// 编辑生产计划并启动业务流程
	@RequestMapping(value = "/editProductionPlan")
	@ResponseBody
	public String editProductionPlan(@RequestBody ERP_ProductionPlan productionPlan, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(productionPlan.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		productionPlanService.editProductionPlan(productionPlan);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "材料计划");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 编辑生产计划货物项
	@RequestMapping(value = "/editProductionPlanOrder.do")
	@ResponseBody
	public String editProductionPlanOrder(@RequestBody ProductionPlanOrder[] productionPlanOrders) {
		JSONObject jsonObject = new JSONObject();
		for (ProductionPlanOrder p : productionPlanOrders) {
			ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(p.getSales_Contract_Id());
			p.setProductionPlanId(productionPlan.getRow_Id());
			if (p.getRow_Id() != null) {
				productionPlanOrderService.editProductionPlanOrder(p);
			} else {
				productionPlanOrderService.saveProductionPlanOrder(p);
			}

		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 根据Id删除生产计划货物项
	@RequestMapping(value = "/deleteProductionPlanOrderById.do")
	@ResponseBody
	public String deleteProductionPlanOrderById(Integer row_Id) {
		JSONObject jsonObject = new JSONObject();
		productionPlanOrderService.deleteProductionPlanOrderById(row_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// ajax填写生产计划检验生产数量
	@RequestMapping(value = "/checkScsl.do")
	@ResponseBody
	public String checkScsl(Integer xsddId, String taskId) {
		// 获取流程变量中的闲置成品
		String variable = (String) taskService.getVariable(taskId, "cphd");
		List<XZ_Product> list = new ArrayList<XZ_Product>();
		JSONArray jsonArray = new JSONArray();
		if (variable != "" && variable != null) {
			String[] variables = variable.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer stockId = null;
				Integer productId = null;
				Integer cksl = null;
				String[] datas = v.split(":");
				stockId = Integer.parseInt(datas[0].trim().trim());
				productId = Integer.parseInt(datas[1].trim().trim());
				cksl = Integer.parseInt(datas[2].trim().trim());
				// 获得成品对象
				ERP_Products product = productService.queryProductById(productId);
				// new 出闲置成品对象
				XZ_Product ps = new XZ_Product();
				// 库存数量
				ps.setStock_Id(stockId);
				// 获得改成品的库存对像
				ERP_Stock erp_stock = kcStockService.queryStockByCPId(productId, stockId);
				ps.setKcNumber(erp_stock.getSl());
				ps.setProductName(product.getProduct_Name());
				ps.setGgxh(product.getSpecification_Type());
				ps.setProduct_Id(product.getProduct_Id());
				// 获得成品库存对象
				ERP_Product_Stock product_stock = productStoService.queryPro_StockById(erp_stock.getStock_Id());
				ps.setStock(product_stock.getStock());
				ps.setCksl(cksl);
				ps.setMaterielId(erp_stock.getMaterielId());
				list.add(ps);
			}
		}

		// 根据id得到销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(xsddId);
		Set<String> set = new HashSet<String>();
		for (ERP_Sales_Contract_Order o : orderList) {
			// 取出集合中不重复的物料id
			set.add(o.getMaterielId());
		}
		for (String s : set) {
			Integer xsddProductCount = productionPlanService.queryContractOrderCount(xsddId, s);
			int totalck = 0;
			// 遍历list集合
			for (XZ_Product l : list) {
				// 获得所有物料一致的闲置成品对象
				if (s.equals(l.getMaterielId())) {
					// 计算同种类型的所有库存量
					totalck += l.getCksl();
				}
			}
			int result = xsddProductCount - totalck;
			JSONObject jsonObject = new JSONObject();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("materialId", s);
			map.put("result", result);
			jsonObject.put("data", map);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

}
