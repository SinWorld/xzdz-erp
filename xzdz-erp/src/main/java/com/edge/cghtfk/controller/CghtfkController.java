package com.edge.cghtfk.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.service.inter.SupplierService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.cghtfk.entity.ERP_Cghtfk;
import com.edge.cghtfk.entity.ERP_Cghtfk_QueryVo;
import com.edge.cghtfk.service.inter.CghtfkService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.google.gson.Gson;

/**
 * 采购合同付款控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "cghtfk")
public class CghtfkController {

	@Resource
	private CghtfkService cghtfkService;

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private ApprovalService approvalService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private EnclosureService enclosureService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ERP_UserService userServive;

	// 跳转至采购合同付款列表页面
	@RequestMapping(value = "/initCghtfkList.do")
	public String initCghtfkList() {
		return "cghtfk/cghtfkList";
	}

	// 分页查询采购合同付款列表
	@RequestMapping(value = "/cghtfkList.do")
	@ResponseBody
	public String cghtfkList(Integer page, Integer limit, Integer cght, String fklx, String ysqk, Double fkje1,
			Double fkje2, String sqfkrq1, String sqfkrq2, String fkrq1, String fkrq2, Integer spzt) {
		// new出ERP_Cghtfk_QueryVo查询对象
		ERP_Cghtfk_QueryVo vo = new ERP_Cghtfk_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setCght(cght);
		vo.setFklx(fklx);
		vo.setYsqk(ysqk);
		vo.setFkje1(fkje1);
		vo.setFkje2(fkje2);
		vo.setSqfkrq1(sqfkrq1);
		vo.setSqfkrq2(sqfkrq2);
		vo.setFkrq1(fkrq1);
		vo.setFkrq2(fkrq2);
		vo.setSpzt(spzt);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", cghtfkService.cghtfkListCount(vo));
		List<ERP_Cghtfk> cghtfkList = cghtfkService.cghtfkList(vo);
		for (ERP_Cghtfk c : cghtfkList) {
			// 采购合同
			if (c.getCght() != null) {
				ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(c.getCght());
				c.setCghtmc(purchaseOrder.getPurchaseOrderName());
			}
			if (c.getApprovalDm() != null) {
				ERP_DM_Approval approval = approvalService.queryApprovalById(c.getApprovalDm());
				c.setApprovalMc(approval.getApprovalmc());
			}
		}
		map.put("data", cghtfkList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 新增时验证采购合同付款
	@RequestMapping(value = "/checkCght.do")
	@ResponseBody
	public String checkCght(Integer cght) {
		JSONObject jsonObject = new JSONObject();
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(cght);
		if (purchaseOrder.getApprovalDm() != 1) {
			jsonObject.put("flag", true);
			jsonObject.put("infor", "当前采购合同流程未审批完成，不允许发起采购合同付款!!!");
			return jsonObject.toString();
		}
		if (purchaseOrder.getIs_wcfk()) {
			jsonObject.put("flag", true);
			jsonObject.put("infor", "当前采购合同流已完成付款，不允许发起采购合同付款!!!");
			return jsonObject.toString();
		}
		jsonObject.put("flag", false);
		return jsonObject.toString();
	}

	// 跳转至采购合同付款页面
	@RequestMapping(value = "/initSaveCghtfk.do")
	public String initSaveCghtfk(@RequestParam Integer cght, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(cght);
		ERP_Supplier supplier = supplierService.querySupplierById(purchaseOrder.getSupplier());
		// 获得累计付款金额
		Double ljfkje = cghtfkService.querySumLjfkje(purchaseOrder.getPur_Order_Id());
		if (ljfkje == null) {
			ljfkje = 0.d;
		}
		// 剩余付款金额
		model.addAttribute("ljfkje", ljfkje);
		model.addAttribute("syfkje", purchaseOrder.getTotalPrice() - ljfkje);
		model.addAttribute("ljfkjebl", (ljfkje / purchaseOrder.getTotalPrice()) * 100 + "%");
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("supplier", supplier);
		model.addAttribute("sqrq", sdf.format(new Date()));
		return "cghtfk/saveCghtfk";
	}

	// 提交表单 启动流程
	@RequestMapping(value = "/saveCghtfk.do")
	public String saveCghtfk(ERP_Cghtfk cghtfk, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		cghtfk.setTask_describe("【任务名称：采购合同付款】");
		cghtfk.setApprovalDm(2);
		cghtfkService.saveCghtfk(cghtfk);
		this.addXshtFj(cghtfk.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = cghtfk.getClass().getSimpleName() + "." + String.valueOf(cghtfkService.queryMaxCghtfk_Id());
		map.put("inputUser", user.getUserId());
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Cghtfk", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "cghtfk/saveCghtfk";
	}

	// 将上传的附件写入数据库
	private void addXshtFj(String fjsx, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			// 获取采购合同付款对象
			ERP_Cghtfk cghtfk = cghtfkService.queryCghtfkById(cghtfkService.queryMaxCghtfk_Id());
			String key = cghtfk.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(cghtfk.getCghtfk_Id());
			// 将字符串转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(value);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String localFileName = (String) obj.get("localFileName");// 上传文件名
				String path = (String) obj.get("path");// 上传文件地址
				String fileName = (String) obj.get("fileName");// 上传文件真实名
				// new 出附件对象
				Enclosure fj = new Enclosure();
				fj.setCUNCHUWJM(localFileName);// 上传文件名
				fj.setSHANGCHUANDZ(path);// 上传文件地址
				fj.setREALWJM(fileName);// 上传文件真实名称
				fj.setSHANGCHUANRQ(date);// 上传文件日期
				fj.setSHANGCHUANYHDM(user.getUserId());// 上传用户主键
				fj.setOBJDM(objId);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
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

	// 跳转至采购合同付款编辑页面
	@RequestMapping(value = "/initEditCghtfk.do")
	public String initEditCghtfk(@RequestParam String objId, String taskId, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String cghtfkdm = objId.substring(objId.indexOf(".") + 1);
		// 根据Id获得采购合同付款对象
		ERP_Cghtfk cghtfk = cghtfkService.queryCghtfkById(Integer.parseInt(cghtfkdm.trim()));
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(cghtfk.getCght());
		ERP_Supplier supplier = supplierService.querySupplierById(purchaseOrder.getSupplier());
		// 获得累计付款金额
		Double ljfkje = cghtfkService.querySumLjfkje(purchaseOrder.getPur_Order_Id());
		if (cghtfk.getSqrq() != null) {
			cghtfk.setShenqrq(sdf.format(cghtfk.getSqrq()));
		} else if (cghtfk.getFkrq() != null) {
			cghtfk.setFukrq(sdf.format(cghtfk.getFkrq()));
		}
		if (ljfkje == null) {
			ljfkje = 0.d;
		}
		// 剩余付款金额
		model.addAttribute("ljfkje", ljfkje);
		model.addAttribute("syfkje", purchaseOrder.getTotalPrice() - ljfkje);
		model.addAttribute("ljfkjebl", (ljfkje / purchaseOrder.getTotalPrice()) * 100 + "%");
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("supplier", supplier);
		model.addAttribute("cghtfk", cghtfk);
		model.addAttribute("taskId", taskId);
		return "cghtfk/editCghtfk";
	}

	// 编辑采购合同付款并推动流程
	@RequestMapping(value = "/editCghtfk.do")
	public String editCghtfk(ERP_Cghtfk cghtfk, @RequestParam String taskId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		cghtfk.setApprovalDm(2);
		cghtfkService.editCghtsk(cghtfk);
		// 新增销售合同附件
		this.editCghtfkFj(cghtfk.getFjsx(), request, cghtfk);
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = cghtfk.getClass().getSimpleName() + "." + String.valueOf(cghtfk.getCghtfk_Id());
		map.put("inputUser", user.getUserId());
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTasks(task, user, objId);
		// 启动流程
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "cghtfk/editCghtfk";
	}

	// 将上传的附件写入数据库
	private void editCghtfkFj(String fjsx, HttpServletRequest request, ERP_Cghtfk cghtfk) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = cghtfk.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(cghtfk.getCghtfk_Id());
			// 将字符串转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(value);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String localFileName = (String) obj.get("localFileName");// 上传文件名
				String path = (String) obj.get("path");// 上传文件地址
				String fileName = (String) obj.get("fileName");// 上传文件真实名
				// new 出附件对象
				Enclosure fj = new Enclosure();
				fj.setCUNCHUWJM(localFileName);// 上传文件名
				fj.setSHANGCHUANDZ(path);// 上传文件地址
				fj.setREALWJM(fileName);// 上传文件真实名称
				fj.setSHANGCHUANRQ(date);// 上传文件日期
				fj.setSHANGCHUANYHDM(user.getUserId());// 上传用户主键
				fj.setOBJDM(objId);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
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

	// 点击列表进入查看页
	@RequestMapping(value = "/cghtfkShow.do")
	public String cghtfkShow(@RequestParam Integer cghtfkdm, Model model) {
		// 格式化计划合同签订日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		ERP_Cghtfk cghtfk = cghtfkService.queryCghtfkById(cghtfkdm);
		ERP_Purchase_Order cght = purchaseOrderService.queryPurchaseOrderById(cghtfk.getCght());
		ERP_Supplier supplier = supplierService.querySupplierById(cght.getSupplier());
		// 获得付款方信息 sdf
		// 获得累计付款金额
		Double ljfkje = cghtfkService.querySumLjfkje(cght.getPur_Order_Id());
		if (cghtfk.getSqrq() != null) {
			cghtfk.setShenqrq(sdf.format(cghtfk.getSqrq()));
		}
		if (cghtfk.getFkrq() != null) {
			cghtfk.setFukrq(sdf.format(cghtfk.getFkrq()));
		}
		String businessKey = cghtfk.getClass().getSimpleName() + "." + String.valueOf(cghtfkdm);
		// 根据businessKey获取历史流程实例对象
		HistoricProcessInstance hisp = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();
		// 若流程实例不为空,则获取流程实例主键
		String processInstanceId = null;
		List<SYS_WorkFlow_PingShenYJ> psyjList = null;
		if (hisp != null) {
			processInstanceId = hisp.getId();
			psyjList = pingShenYjService.psyjList(processInstanceId);
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userServive.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
		}
		model.addAttribute("cghtfk", cghtfk);
		model.addAttribute("purchaseOrder", cght);
		model.addAttribute("supplier", supplier);
		model.addAttribute("OBJDM", businessKey);
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("processInstanceId", processInstanceId);
		// 剩余付款金额
		model.addAttribute("ljfkje", ljfkje);
		model.addAttribute("syfkje", cght.getTotalPrice() - ljfkje);
		model.addAttribute("ljfkjebl", (ljfkje / cght.getTotalPrice()) * 100 + "%");
		return "cghtfk/cghtfkShow";
	}

	// 加载所有的采购合同法
	@RequestMapping(value = "/allCght.do")
	@ResponseBody
	public String allCght() {
		JSONArray allCght = cghtfkService.allCght();
		return allCght.toString();
	}

}
