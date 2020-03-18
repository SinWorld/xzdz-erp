package com.edge.business.productionCollar.controller;

import java.io.UnsupportedEncodingException;
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
import com.edge.business.materialStockCK.service.inter.MaterialStockCkService;
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
import com.edge.stocks.material.rk.service.inter.Mat_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

/**
 * 生产领用控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "productionCollar")
public class ProductionCollarController {

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private KC_StatusService statusService;

	@Resource
	private MaterialService materialService;

	@Resource
	private KC_StockService stockService;

	@Resource
	private MaterialStockCkService materialStockCkService;

	@Resource
	private Mat_StockService mat_StockService;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	// 跳转至生产领用界面
	@RequestMapping(value = "/initProductionCollar.do")
	public String initProductionCollar(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同主键
		String xsddId = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(xsddId.trim()));
		// 根据销售合同Id获得生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 根据生产计划编号获得材料库存对象集合
		List<ERP_Stock_Status> statusList = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		List<XZ_Product> materialList = new ArrayList<XZ_Product>();
		// 遍历该集合获得材料对象
		for (ERP_Stock_Status s : statusList) {
			// 获得已出库的材料集合
			List<ERP_Stock> list = materialStockCkService.queryStockListByClId(s.getProduct_Id());
			// 遍历该集合添加材料
			for (ERP_Stock l : list) {
				XZ_Product material = new XZ_Product();
				// 设置属性
				material.setProduct_Id(l.getProduct_Id());
				material.setStock_Id(l.getStock_Id());
				material.setMaterielId(l.getMaterielId());
				material.setProductName(materialService.queryMaterialById(l.getProduct_Id()).getMaterial_Name());
				material.setGgxh(materialService.queryMaterialById(l.getProduct_Id()).getSpecification_Type());
				material.setRksl(
						materialStockCkService.queryStockRecordRkClIdAndKwId(l.getProduct_Id(), l.getStock_Id()));
				material.setCksl(
						materialStockCkService.queryStockRecordCkClIdAndKwId(l.getProduct_Id(), l.getStock_Id()));
				material.setStock(mat_StockService.queryMatStockById(l.getStock_Id()).getStock());
				materialList.add(material);
			}
		}
		model.addAttribute("materialList", materialList);
		model.addAttribute("xsddId", xsddId);
		model.addAttribute("taskId", taskId);
		return "business/productionCollar/saveProductionCollar";
	}

	// 提交表单(材料检验审批)
	@RequestMapping(value = "/productionCollar.do")
	public String productionCollar(@RequestParam String task_Id, String out_come, String advice_, String data,
			HttpServletRequest request, Model model) {
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		String jg = null;
		try {
			jg = new String(out_come.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String yj = null;
		try {
			yj = new String(advice_.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (out_come != null && data != null) {
			variables.put("outcome", jg);
			variables.put("scly", data);
		}
		this.savelcsp(task, user, jg, yj);
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
		return "business/productionCollar/saveProductionCollar";
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
