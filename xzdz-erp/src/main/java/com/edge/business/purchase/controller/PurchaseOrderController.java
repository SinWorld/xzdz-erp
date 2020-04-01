package com.edge.business.purchase.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
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
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.purchase.entity.ERP_Purchase_List;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseListService;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/**
 * 采购合同控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "purchaseOrder")
public class PurchaseOrderController {

	@Resource
	private TaskService taskService;

	@Resource
	private CompanyService companyService;

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PurchaseListService purchaseListService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private EnclosureService enclosureService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ERP_UserService userService;

	// 跳转至采购订单页面
	@RequestMapping(value = "/initSavePurchaseOrder.do")
	public String initSavePurchaseOrder() {
		return "business/purchase/purchaseOrder/savePurchaseOrder";
	}

	// 提交表单新增合同且启动采购流程
	@RequestMapping(value = "/savePurchaseOrder.do")
	@ResponseBody
	public String savePurchaseOrder(@RequestBody ERP_Purchase_Order purchaseOrder, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		List<ERP_Our_Unit> units = companyService.ourUnitList();
		for (ERP_Our_Unit u : units) {
			purchaseOrder.setOur_uint(u.getUnit_Id());
		}
		purchaseOrder.setStatus("已下单");
		purchaseOrder.setIs_Availability(false);
		purchaseOrder.setQd_Date(new Date());
		purchaseOrder.setGfqd_Date(new Date());
		purchaseOrder.setSub_Date(new Date());
		purchaseOrder.setUserId(user.getUserId());
		purchaseOrder.setApprovalDm(2);
		purchaseOrder.setTask_Describe("【任务名称：采购合同】");
		purchaseOrder.setIs_Availability(true);
		purchaseOrder.setIs_wcfk(false);
		purchaseOrderService.savePurchaseOrder(purchaseOrder);
		// 新增采购合同附件
		this.addXshtFj(purchaseOrder.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String key = purchaseOrder.getClass().getSimpleName();
		String objId = key + "." + String.valueOf(purchaseOrderService.queryMaxOrderId());
		map.put("inputUser", user.getUserId());
		map.put("outcome", "领导审核");
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("PurchaseOrder", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user, null, null);
		boolean kg = true;
		this.saveAlreadyTask(task, user, objId, kg);
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增采购计划货物项
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
	private void saveAlreadyTask(Task task, ERP_User user, String objId, boolean kg) {
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
		if (kg) {
			alreadyTask.setCOMPLETION_STATUS_("开始");
		} else {
			alreadyTask.setCOMPLETION_STATUS_("完成");
		}

		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
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
			// 根据采购合同主键获得采购合同对象
			ERP_Purchase_Order cght = purchaseOrderService
					.queryPurchaseOrderById(purchaseOrderService.queryMaxOrderId());
			String key = cght.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(cght.getPur_Order_Id());
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

	// 查看操作
	@RequestMapping(value = "/purchaseOrderShow.do")
	public String purchaseOrderShow(@RequestParam Integer pur_Order_Id, Model model) {
		// 格式化计划合同签订日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		// 根据id获得采购合同对象
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(pur_Order_Id);
		List<ERP_Purchase_List> purchaseList = null;
		if (purchaseOrder != null) {
			// 设置属性
			ERP_Supplier supplier = supplierService.querySupplierById(purchaseOrder.getSupplier());
			purchaseOrder.setSupplierName(supplier.getSupplier_Name());
			purchaseOrder.setTelPhone(supplier.getPhone());
			purchaseOrder.setDgrq(sdf.format(purchaseOrder.getPur_Date()));
			// 获得采购清单集合
			purchaseList = purchaseListService.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
		}
		String businessKey = purchaseOrder.getClass().getSimpleName() + "." + String.valueOf(pur_Order_Id);
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
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
		}
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("OBJDM", businessKey);
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("processInstanceId", processInstanceId);
		return "business/purchase/purchaseOrder/purchaseOrderShow";
	}

	// 跳转至领导审核页面
	@RequestMapping(value = "/initLdsh.do")
	public String initLdsh(@RequestParam String objId, String taskId, Model model) {
		String chgtId = objId.substring(objId.indexOf(".") + 1);
		model.addAttribute("chgtId", chgtId);
		model.addAttribute("taskId", taskId);
		return "business/purchase/purchaseOrder/saveLdsh";
	}

	// 领导审核提交表单推动流程
	@RequestMapping(value = "/ldsh.do")
	public String ldsh(@RequestParam String task_Id, String out_come, String advice_, HttpServletRequest request,
			Model model) {
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		if (out_come != null) {
			variables.put("outcome", out_come);
		}
		this.savelcsp(task, user, out_come, advice_);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		boolean kg = false;
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey(), kg);
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "business/purchase/purchaseOrder/saveLdsh";
	}

	// 跳转至采购合同编辑页面
	@RequestMapping(value = "/initEditPurchase.do")
	public String initEditPurchase(@RequestParam String objId, String taskId, Model model) {
		// 得到采购合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据销售合同获得采购合同对象
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(Integer.parseInt(id.trim()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		purchaseOrder.setDgrq(sdf.format(purchaseOrder.getPur_Date()));
		// 根据采购合同对象获得采购合同清单集合
		// 获得采购清单集合
		List<ERP_Purchase_List> purchaseList = purchaseListService
				.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("taskId", taskId);
		model.addAttribute("dgrq", sdf.format(purchaseOrder.getPur_Date()));
		return "business/purchase/purchaseOrder/editPurchaseOrder";

	}

	// 提交表单编辑采购合同并推动流程
	@RequestMapping(value = "/editPurchaseOrder.do")
	@ResponseBody
	public String editPurchaseOrder(@RequestBody ERP_Purchase_Order purchaseOrder, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(purchaseOrder.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		List<ERP_Our_Unit> units = companyService.ourUnitList();
		for (ERP_Our_Unit u : units) {
			purchaseOrder.setOur_uint(u.getUnit_Id());
		}
		purchaseOrder.setStatus("已下单");
		purchaseOrder.setIs_Availability(false);
		purchaseOrder.setQd_Date(new Date());
		purchaseOrder.setGfqd_Date(new Date());
		purchaseOrder.setSub_Date(new Date());
		purchaseOrder.setUserId(user.getUserId());
		purchaseOrder.setApprovalDm(2);
		purchaseOrder.setTask_Describe("【任务名称：采购合同】");
		purchaseOrder.setIs_Availability(true);
		purchaseOrder.setIs_wcfk(false);
		purchaseOrderService.editPurchaseOrder(purchaseOrder);
		// 新增采购合同附件
		this.editXshtFj(purchaseOrder.getFjsx(), request, purchaseOrder);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTasks(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "领导审核");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增或编辑生产计划货物项
	@RequestMapping(value = "/saveOrEditPurchaseList.do")
	@ResponseBody
	public String saveOrEditPurchaseList(@RequestBody ERP_Purchase_List[] purchaseList) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Purchase_List p : purchaseList) {
			if (p.getPur_Id() != null) {
				purchaseListService.editPurchaseList(p);
			} else {
				purchaseListService.savePurchaseList(p);
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
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

	// 将上传的附件写入数据库
	private void editXshtFj(String fjsx, HttpServletRequest request, ERP_Purchase_Order purchaseOrder) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = purchaseOrder.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(purchaseOrder.getPur_Order_Id());
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

	// ajax在编辑页面显示附件
	@RequestMapping(value = "/pageLoadFj.do")
	@ResponseBody
	public String pageLoadFj(Integer row_Id, HttpServletResponse response, HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		// 获得objId
		ERP_Purchase_Order cght = purchaseOrderService.queryPurchaseOrderById(row_Id);
		String objId = cght.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据objId获得附件集合
		List<Enclosure> enclosureList = enclosureService.enclosureList(objId);
		// 遍历该集合
		for (Enclosure e : enclosureList) {
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + e.getSHANGCHUANDZ() + "/" + e.getREALWJM());
			File file = new File(filePath.trim());
			String fileName = file.getName();
			DecimalFormat df = new DecimalFormat("#.00");
			String fileSizeString = df.format((double) file.length() / 1024) + "KB";
			// System.out.println("fileName = " + fileName + " " + "size=" +
			// fileSizeString);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fileName", fileName);
			jsonObject.put("fileSize", fileSizeString);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 删除附件
	@RequestMapping(value = "/removeFj.do")
	@ResponseBody
	public String removeFj(Integer row_Id, String fileName, HttpServletResponse response, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 获得objId
		ERP_Purchase_Order cght = purchaseOrderService.queryPurchaseOrderById(row_Id);
		String objId = cght.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据objId获得附件集合
		List<Enclosure> enclosureList = enclosureService.enclosureList(objId);
		for (Enclosure e : enclosureList) {
			if (fileName.equals(e.getREALWJM())) {
				// 删除数据库中的附件
				enclosureService.deleteFjByObj(e.getFUJIANDM());
			}
			// 删除服务器端的附件文件
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + e.getSHANGCHUANDZ() + "/" + e.getREALWJM());
			File file = new File(filePath.trim());
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

}
