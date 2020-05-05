package com.edge.business.materialPlan.controller;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.business.materialPlan.entity.ERP_MaterialPlan;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;
import com.edge.business.materialPlan.service.inter.MaterialPlanService;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.entity.ProductionPlanOrder;
import com.edge.business.productionPlan.service.inter.ProductionPlanOrderService;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;

/**
 * 材料计划控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materialPlan")
public class MaterialPlanController {

	@Resource
	private MaterialPlanService materialPlanService;

	@Resource
	private MaterialPlanOrderService materialPlanOrderService;

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private ProductionPlanOrderService productionPlanOrderService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ProductService productService;

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	// 跳转至材料计划页面
	@RequestMapping(value = "/initMaterialPlan.do")
	public String initMaterialPlan(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据销售合同Id获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(Integer.parseInt(id.trim()));
		// 根据生产计划获得生产计划货物项集合
		List<ProductionPlanOrder> orders = productionPlanOrderService
				.queryPlanOrderByPlanId(productionPlan.getRow_Id());
		// 遍历该集合
		for (ProductionPlanOrder o : orders) {
			// 根据成品获得成品对象
			ERP_Products product = productService.queryProductById(o.getProduct());
			o.setErp_product(product);
		}
		// 格式化订单日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 生成材料计划号
		String plan_Code = productionPlan.getPlan_Code();
		String material_Code = plan_Code.replace("SC", "CL");
		model.addAttribute("material_Code", material_Code);
		model.addAttribute("plan_Date", sdf.format(contract.getQd_Date()));
		model.addAttribute("contract", contract);
		model.addAttribute("orders", orders);
		model.addAttribute("taskId", taskId);
		return "business/materialPlan/saveMaterialPlan";
	}

	// 提交表单新增材料计划及推动流程
	@RequestMapping(value = "/saveMaterialPlan.do")
	@ResponseBody
	public String saveMaterialPlan(@RequestBody ERP_MaterialPlan materialPlan, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(materialPlan.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		materialPlanService.saveMaterialPlan(materialPlan);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "加工配料");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增生产计划货物项
	@RequestMapping(value = "/saveMaterialPlanOrder.do")
	@ResponseBody
	public String saveMaterialPlanOrder(@RequestBody MaterialPlanOrder[] materialPlanOrder) {
		JSONObject jsonObject = new JSONObject();
		for (MaterialPlanOrder m : materialPlanOrder) {
			m.setMaterialPlanId(materialPlanService.materialPlanMaxId());
			materialPlanOrderService.saveMaterialPlanOrder(m);
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

	// 跳转至材料计划编辑页面
	@RequestMapping(value = "/initEditMaterialPlan.do")
	public String initEditMaterialPlan(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据销售合同Id获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(Integer.parseInt(id.trim()));
		// 根据生产计划获得生产计划货物项集合
		List<ProductionPlanOrder> orders = productionPlanOrderService
				.queryPlanOrderByPlanId(productionPlan.getRow_Id());
		// 遍历该集合
		for (ProductionPlanOrder o : orders) {
			// 根据成品获得成品对象
			ERP_Products product = productService.queryProductById(o.getProduct());
			o.setErp_product(product);
		}
		// 根据销售合同Id获得材料计划对象
		ERP_MaterialPlan materialPlan = materialPlanService.queryMaterialPlanByXsht(contract.getSales_Contract_Id());
		// 格式化订单日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		materialPlan.setXddrq(sdf.format(materialPlan.getPlan_Date()));
		materialPlan.setJhkgrq(sdf.format(materialPlan.getPlan_BeginDate()));
		materialPlan.setJhwgrq(sdf.format(materialPlan.getPlan_EndDate()));
		// 根据材料计划对象获得材料计划货物项集合
		List<MaterialPlanOrder> materialPlanOrders = materialPlanOrderService
				.queryOrderByMaplanId(materialPlan.getRow_Id());
		model.addAttribute("materialPlan", materialPlan);
		model.addAttribute("contract", contract);
		model.addAttribute("orders", orders);
		model.addAttribute("materialPlanOrders", materialPlanOrders);
		model.addAttribute("taskId", taskId);
		return "business/materialPlan/editMaterialPlan";
	}

	// ajax删除材料计划货物项
	@RequestMapping(value = "/deleteMaterialOrderById.do")
	@ResponseBody
	public String deleteMaterialOrderById(Integer row_Id) {
		JSONObject jsonObject = new JSONObject();
		materialPlanOrderService.deleteMaterialPlanOrder(row_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 提交表单编辑材料计划及推动流程
	@RequestMapping(value = "/editMaterialPlan.do")
	@ResponseBody
	public String editMaterialPlan(@RequestBody ERP_MaterialPlan materialPlan, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(materialPlan.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		materialPlanService.editMaterialPlan(materialPlan);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "加工配料");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增/编辑生产计划货物项
	@RequestMapping(value = "/saveOrEditMaterialPlanOrder.do")
	@ResponseBody
	public String saveOrEditMaterialPlanOrder(@RequestBody MaterialPlanOrder[] materialPlanOrder) {
		JSONObject jsonObject = new JSONObject();
		for (MaterialPlanOrder m : materialPlanOrder) {
			m.setMaterialPlanId(materialPlanService.materialPlanMaxId());
			if (m.getRow_Id() != null) {
				materialPlanOrderService.editMaterialPlanOrder(m);
			} else {
				materialPlanOrderService.saveMaterialPlanOrder(m);
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
