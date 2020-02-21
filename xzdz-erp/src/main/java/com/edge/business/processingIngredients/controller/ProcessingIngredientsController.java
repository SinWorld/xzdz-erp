package com.edge.business.processingIngredients.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.admin.user.entity.ERP_User;
import com.edge.business.checkProduct.entity.XZ_Product;
import com.edge.business.materialPlan.entity.ERP_MaterialPlan;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;
import com.edge.business.materialPlan.service.inter.MaterialPlanService;
import com.edge.business.processingIngredients.service.inter.ProcessingIngredientsService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

/**
 * 加工配料控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "processingIngredients")
public class ProcessingIngredientsController {

	@Resource
	private ProcessingIngredientsService processingIngredientsService;

	@Resource
	private MaterialPlanService materialPlanService;

	@Resource
	private MaterialPlanOrderService materialPlanOrderService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private KC_StockService kcStockService;

	@Resource
	private MaterialService materialService;

	@Resource
	private Mat_StockService mat_stockService;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	// 跳转至加工配料页面
	@RequestMapping(value = "/initProcessingIngredients.do")
	public String initProcessingIngredients(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据销售订单主键获得材料计划对象
		ERP_MaterialPlan materialPlan = materialPlanService.queryMaterialPlanByXsht(Integer.parseInt(id));
		// 根据材料计划对象获得材料计划货物集合
		List<MaterialPlanOrder> orders = materialPlanOrderService.queryOrderByMaplanId(materialPlan.getRow_Id());
		// 所有物料Id一致的闲置材料集合
		List<Integer> xzclIds = new ArrayList<Integer>();
		List<ERP_Stock_Status> statsusList = new ArrayList<ERP_Stock_Status>();
		List<XZ_Product> list = new ArrayList<XZ_Product>();
		for (MaterialPlanOrder o : orders) {
			// 加载当前仓库中处于闲置状态且物料Id一致的闲置材料集合
			List<ERP_RAW_Material> materiels = processingIngredientsService
					.queryMaterialByMaterielId(o.getMaterielId());
			for (ERP_RAW_Material m : materiels) {
				xzclIds.add(m.getRaw_Material_Id());
			}
		}
		if (xzclIds.size() > 0) {
			statsusList = processingIngredientsService.statsusList(xzclIds);
			for (ERP_Stock_Status s : statsusList) {
				// 根据材料id获得该材料的库存集合
				List<ERP_Stock> stocks = kcStockService.queryStockByMaterialId(s.getProduct_Id());
				// 遍历该集合
				for (ERP_Stock stock : stocks) {
					// 根据材料Id获得闲置材料对象
					ERP_RAW_Material material = materialService.queryMaterialById(stock.getProduct_Id());
					XZ_Product ps = new XZ_Product();
					// 库存数量
					ps.setStock_Id(stock.getStock_Id());
					ps.setKcNumber(stock.getSl());
					ps.setProductName(material.getMaterial_Name());
					ps.setGgxh(material.getSpecification_Type());
					ps.setProduct_Id(material.getRaw_Material_Id());
					// 获得材料库存对象
					ERP_Material_Stock material_stock = mat_stockService.queryMatStockById(stock.getStock_Id());
					ps.setStock(material_stock.getStock());
					ps.setMaterielId(material.getMaterielId());
					list.add(ps);
				}
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("taskId", taskId);
		model.addAttribute("orders", orders);
		return "business/processingIngredients/saveProcessingIngredients";

	}

	// 提交表单推动流程
	@RequestMapping(value = "/processingIngredientsSubmit.do")
	public String processingIngredientsSubmit(@RequestParam String task_Id, String out_come, String advice_,
			String jgpl, HttpServletRequest request, Model model) {
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		if (out_come != null && jgpl != null) {
			variables.put("outcome", out_come);
			variables.put("jgpl", jgpl);
		}
		this.savelcsp(task, user, out_come, advice_);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "business/processingIngredients/saveProcessingIngredients";

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
