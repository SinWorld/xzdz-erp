package com.edge.business.checkMaterial.controller;

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
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 材料检验控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "checkMaterial")
public class CheckMaterialController {

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private KC_StatusService statusService;

	@Resource
	private MaterialService materialService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private RuntimeService runtimeService;

	// 跳转至材料检验页面
	@RequestMapping(value = "/initCheckMaterial.do")
	public String initCheckMaterial(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据销售合同对象获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 根据生产计划编号获得材料库存对象集合
		List<ERP_Stock_Status> statusList = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		List<ERP_RAW_Material> materialList = new ArrayList<ERP_RAW_Material>();
		// 遍历该集合获得材料对象
		for (ERP_Stock_Status s : statusList) {
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			materialList.add(material);
		}
		model.addAttribute("materialList", materialList);
		model.addAttribute("taskId", taskId);
		return "business/checkMaterial/saveCheckMaterial";
	}

	// 提交表单(材料检验审批)
	@RequestMapping(value = "/CheckMaterial.do")
	public String CheckMaterial(@RequestParam String task_Id, String out_come, String advice_, String data,
			HttpServletRequest request, Model model) {
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		if (out_come != null && data != null) {
			variables.put("outcome", out_come);
			variables.put("jyjg", data);
		}
		this.savelcsp(task, user, out_come, advice_);
		// 获得流程变量数组 新增评审意见成品核对数据
		if (data != "" && data != null) {
			String[] jyjgs = data.split(",");
			for (String j : jyjgs) {
				Integer materialId = null;
				String result = null;
				// 将j按 :分割为数组
				String[] datas = j.split(":");
				materialId = Integer.parseInt(datas[0].trim());
				result = datas[1].trim();
				// 根据材料主键获得材料对象
				ERP_RAW_Material material = materialService.queryMaterialById(materialId);
				if (material != null) {
					// 设置材料质量结果
					material.setMaterialQuality(result);
					materialService.editMaterial(material);
				}
			}
		}
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "business/checkMaterial/saveCheckMaterial";
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

	// 领导审核（材料）重新检验流转至材料检验，加载不合格的材料
	@RequestMapping(value = "/initReExamination.do")
	public String initReExamination(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据销售合同对象获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 根据生产计划编号获得材料库存对象集合
		List<ERP_Stock_Status> statusList = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		List<ERP_RAW_Material> materialList = new ArrayList<ERP_RAW_Material>();
		// 遍历该集合获得材料对象
		for (ERP_Stock_Status s : statusList) {
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			if ("不合格".equals(material.getMaterialQuality())) {
				materialList.add(material);
			}
		}
		model.addAttribute("materialList", materialList);
		model.addAttribute("taskId", taskId);
		return "business/checkMaterial/saveCheckMaterial";
	}

	// 不合格材料反馈跳转至材料检验
	@RequestMapping(value = "/initEditCheckMaterial.do")
	public String initEditCheckMaterial(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据销售合同对象获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 根据生产计划编号获得材料库存对象集合
		List<ERP_Stock_Status> statusList = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		List<ERP_RAW_Material> materialList = new ArrayList<ERP_RAW_Material>();
		// 遍历该集合获得材料对象
		for (ERP_Stock_Status s : statusList) {
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			if ("部分不合格".equals(material.getMaterialQuality().trim())) {
				materialList.add(material);
			}
		}
		model.addAttribute("materialList", materialList);
		model.addAttribute("taskId", taskId);
		return "business/checkMaterial/saveCheckMaterial";
	}
}
