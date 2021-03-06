package com.edge.business.checkProduct.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.checkProduct.entity.SYS_WorkFlow_Cphd;
import com.edge.business.checkProduct.entity.XZ_Product;
import com.edge.business.checkProduct.service.inter.CheckProductService;
import com.edge.business.checkProduct.service.inter.SYS_WorkFlow_CphdService;
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
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;

/**
 * 成品核对控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "checkProduct")
public class CheckProductController {

	@Resource
	private CheckProductService checkProductService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private ProductService productService;

	@Resource
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private Pro_CK_StockService stockService;

	@Resource
	private Pro_StockRecordService stockRecordService;

	@Resource
	private Pro_StockService productStoService;

	@Resource
	private SYS_WorkFlow_CphdService cphdService;

	@Resource
	private KC_StockService kcStockService;

	// 跳转至成品核对页面
	@RequestMapping(value = "/initCheckProduct.do")
	public String initCheckProduct(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据id得到销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(Integer.parseInt(id));
		/**
		 * 1.查询成品表 sales_Contract_Id=null(闲置状态的成品)且is_rk=1且is_ck=0 得到所需成品主键
		 * 2.根据该成品主键去查询成品库存表得到该成品的入库信息
		 */
		List<XZ_Product> list = new ArrayList<XZ_Product>();
		List<Integer> productIds = new ArrayList<Integer>();
		List<ERP_Stock_Status> statsusList = new ArrayList<ERP_Stock_Status>();
		/**
		 * 根据合同货物清单中的成品主键去检索库存状态成品Id一致 且状态为闲置的对象
		 */
		// 查询当前合同货物清单中的闲置成品集合
		for (ERP_Sales_Contract_Order o : orderList) {
			List<ERP_Products> productList = checkProductService.queryProductByMaterielId(o.getMaterielId());
			for (ERP_Products p : productList) {
				productIds.add(p.getProduct_Id());
			}

		}
		if (productIds.size() > 0) {
			statsusList = checkProductService.statsusList(productIds);
			for (ERP_Stock_Status s : statsusList) {
				// 根据成品id获得该成品的库存集合
				List<ERP_Stock> stocks = kcStockService.queryStockByProductId(s.getProduct_Id());
				// 遍历该集合
				for (ERP_Stock erp_stock : stocks) {
					// 根据成品Id获得闲置成品对象
					ERP_Products product = productService.queryProductById(erp_stock.getProduct_Id());
					XZ_Product ps = new XZ_Product();
					// 库存数量
					ps.setStock_Id(erp_stock.getStock_Id());
					ps.setKcNumber(erp_stock.getSl());
					ps.setProductName(product.getProduct_Name());
					ps.setGgxh(product.getSpecification_Type());
					ps.setProduct_Id(product.getProduct_Id());
					// 获得成品库存对象
					ERP_Product_Stock product_stock = productStoService.queryPro_StockById(erp_stock.getStock_Id());
					ps.setStock(product_stock.getStock());
					list.add(ps);
				}
			}
		}
		model.addAttribute("contract", contract);
		model.addAttribute("orderList", orderList);
		model.addAttribute("list", list);
		model.addAttribute("taskId", taskId);
		return "business/checkProduct/checkProResult";

	}

	// 提交表单(成品核对审批)
	@RequestMapping(value = "/CheckProduct.do")
	public String CheckProduct(@RequestParam String task_Id, String out_come, String advice_, String cphd,
			HttpServletRequest request, Model model) {
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		if (out_come != null && cphd != null) {
			variables.put("outcome", out_come);
			variables.put("cphd", cphd);
		}
		this.savelcsp(task, user, out_come, advice_);
		// 获得流程变量数组 新增评审意见成品核对数据
		if (cphd != "" && cphd != null) {
			String[] cphds = cphd.split(",");
			for (String c : cphds) {
				Integer stockId = null;
				Integer productId = null;
				Integer cksl = null;
				// 将c按 :分割为数组
				String[] datas = c.split(":");
				stockId = Integer.parseInt(datas[0].trim());
				productId = Integer.parseInt(datas[1].trim());
				cksl = Integer.parseInt(datas[2].trim());
				// 根据库位主键和成品主键获得库存量
				ERP_Products product = productService.queryProductById(productId);
				ERP_Stock kc = kcStockService.queryStockByCPId(product.getProduct_Id(), stockId);
				ERP_Product_Stock stock = productStoService.queryPro_StockById(stockId);
				this.saveWorkFlowcphd(product.getProduct_Name(), product.getSpecification_Type(), stock.getStock(),
						kc.getSl(), cksl, pingShenYjService.psyjId());
			}
		}
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "business/checkProduct/checkProResult";
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

	// 新增评审意见成品核对数据
	private void saveWorkFlowcphd(String cphd_Cpmc, String cphd_Ggxh, String cphd_Kw, Integer cphd_Kcsl,
			Integer cphd_Cksl, Integer cphd_ObjId) {
		// new出SYS_WorkFlow_Cphd对象
		SYS_WorkFlow_Cphd cphd = new SYS_WorkFlow_Cphd();
		cphd.setCphd_Cpmc(cphd_Cpmc);
		cphd.setCphd_Ggxh(cphd_Ggxh);
		cphd.setCphd_Kw(cphd_Kw);
		cphd.setCphd_Kcsl(cphd_Kcsl);
		cphd.setCphd_Cksl(cphd_Cksl);
		cphd.setCphd_ObjId(cphd_ObjId);
		cphdService.saveCphd(cphd);
	}

	// 跳转至成品核对编辑页
	@RequestMapping(value = "/initEditCheckProduct.do")
	public String initEditCheckProduct(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据id得到销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(Integer.parseInt(id));

		List<XZ_Product> list = new ArrayList<XZ_Product>();
		List<Integer> productIds = new ArrayList<Integer>();
		List<ERP_Stock_Status> statsusList = new ArrayList<ERP_Stock_Status>();
		/**
		 * 根据合同货物清单中的成品主键去检索库存状态成品Id一致 且状态为闲置的对象
		 */
		// 查询当前合同货物清单中的闲置成品集合
		for (ERP_Sales_Contract_Order o : orderList) {
			List<ERP_Products> productList = checkProductService.queryProductByMaterielId(o.getMaterielId());
			for (ERP_Products p : productList) {
				productIds.add(p.getProduct_Id());
			}

		}
		if (productIds.size() > 0) {
			statsusList = checkProductService.statsusList(productIds);
			for (ERP_Stock_Status s : statsusList) {
				// 根据成品id获得该成品的库存集合
				List<ERP_Stock> stocks = kcStockService.queryStockByProductId(s.getProduct_Id());
				// 遍历该集合
				for (ERP_Stock erp_stock : stocks) {
					// 根据成品Id获得闲置成品对象
					ERP_Products product = productService.queryProductById(erp_stock.getProduct_Id());
					XZ_Product ps = new XZ_Product();
					// 库存数量
					ps.setStock_Id(erp_stock.getStock_Id());
					ps.setKcNumber(erp_stock.getSl());
					ps.setProductName(product.getProduct_Name());
					ps.setGgxh(product.getSpecification_Type());
					ps.setProduct_Id(product.getProduct_Id());
					// 获得成品库存对象
					ERP_Product_Stock product_stock = productStoService.queryPro_StockById(erp_stock.getStock_Id());
					ps.setStock(product_stock.getStock());
					list.add(ps);
				}
			}
		}
		model.addAttribute("contract", contract);
		model.addAttribute("orderList", orderList);
		model.addAttribute("list", list);
		model.addAttribute("taskId", taskId);
		return "business/checkProduct/editCheckProResult";
	}

}
