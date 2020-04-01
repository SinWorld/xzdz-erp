package com.edge.checkDelivery.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.service.inter.DeliveryService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.checkDelivery.entity.CheckDelivery;
import com.edge.checkDelivery.entity.CheckDelivery_QueryVo;
import com.edge.checkDelivery.service.inter.CheckDeliveryService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.xshtsk.service.inter.XshtskService;
import com.google.gson.Gson;

/**
 * 送货单核对控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "checkDelivery")
public class CheckDeliveryController {

	@Resource
	private CheckDeliveryService checkDeliveryService;

	@Resource
	private DeliveryService deliveryService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ApprovalService approvalService;

	@Resource
	private XshtskService xshtskService;

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
	private ERP_UserService userService;

	// 跳转至送货单核对列表
	@RequestMapping(value = "/initCheckDeliveryList.do")
	public String initCheckDeliveryList() {
		return "checkDelivery/checkDeliveryList";
	}

	// 分页显示送货单核对
	@RequestMapping(value = "/checkDeliveryList.do")
	@ResponseBody
	public String checkDeliveryList(Integer page, Integer limit, Integer xsht, Integer spzt, String beginTime,
			String endTime) {
		// new出CheckDelivery_QueryVo查询对象
		CheckDelivery_QueryVo vo = new CheckDelivery_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setXsht(xsht);
		vo.setSpzt(spzt);
		vo.setBeginTime(beginTime);
		vo.setEndTime(endTime);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", checkDeliveryService.checkDeliveryCount(vo));
		List<CheckDelivery> checkDeliveryList = checkDeliveryService.checkDeliveryList(vo);
		for (CheckDelivery c : checkDeliveryList) {
			// 获得送货单对象
			ERP_Delivery delivery = deliveryService.queryDeliveryById(c.getDelivery_Id());
			// 获得销售合同
			ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
			ERP_DM_Approval approval = approvalService.queryApprovalById(c.getApprovalDm());
			c.setXshtmc(contract.getSales_Contract_Name());
			c.setApprovalMc(approval.getApprovalmc());
		}
		map.put("data", checkDeliveryList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 送货单核对检验
	@RequestMapping(value = "/checkDelivery.do")
	@ResponseBody
	public String checkDelivery(Integer delivery_Id) {
		JSONObject jsonObject = new JSONObject();
		// 获取送货单对象
		ERP_Delivery delivery = deliveryService.queryDeliveryById(delivery_Id);
		// 获得销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
		if (contract.getIs_delivery()) {
			jsonObject.put("flag", true);
			jsonObject.put("infor", "当前销售合同下的送货单已核对，不允许在发起送货单核对!!!");
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 跳转至送货单核对发起页面
	@RequestMapping(value = "/initSaveCheckDelivery.do")
	public String initSaveCheckDelivery(Integer delivery_Id, Model model) {
		// 获取送货单对象
		ERP_Delivery delivery = deliveryService.queryDeliveryById(delivery_Id);
		// 获得销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
		model.addAttribute("contract", contract);
		model.addAttribute("delivery_Id", delivery_Id);
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
		return "checkDelivery/saveCheckDelivery";
	}

	// 提交表单启动销售合同收款流程
	@RequestMapping(value = "/saveCheckDelivery.do")
	public String saveCheckDelivery(CheckDelivery checkDelivery, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		checkDelivery.setTask_Describe("【任务名称：送货单核对】");
		checkDelivery.setApprovalDm(2);
		checkDelivery.setCreateUser(user.getUserId());
		checkDeliveryService.saveCheckDelivery(checkDelivery);
		// 新增销售合同附件
		this.addXshtFj(checkDelivery.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = checkDelivery.getClass().getSimpleName() + "."
				+ String.valueOf(checkDeliveryService.maxCheckDeliveryId());
		map.put("inputUser", user.getUserId());
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("CheckDelivery", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "checkDelivery/saveCheckDelivery";
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
			// 获取送货单核对对象
			CheckDelivery checkDelivery = checkDeliveryService
					.queryCheckDeliveryById(checkDeliveryService.maxCheckDeliveryId());
			String key = checkDelivery.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(checkDelivery.getRow_Id());
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

	// 跳转至送货单上传编辑页面
	@RequestMapping(value = "/initEditCheckDelivery.do")
	public String initEditCheckDelivery(@RequestParam String objId, String taskId, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
		String rowId = objId.substring(objId.indexOf(".") + 1);
		// 根据Id获得送货单核对对象
		CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(Integer.parseInt(rowId.trim()));
		if (checkDelivery.getCreateTime() != null) {
			checkDelivery.setStartTime(sdf.format(checkDelivery.getCreateTime()));
		}
		ERP_Delivery delivery = deliveryService.queryDeliveryById(checkDelivery.getDelivery_Id());
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
		model.addAttribute("checkDelivery", checkDelivery);
		model.addAttribute("contract", contract);
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
		model.addAttribute("taskId", taskId);
		return "checkDelivery/editCheckDelivery";
	}

	// 提交表单推动流程
	@RequestMapping(value = "/editCheckDelivery.do")
	public String editCheckDelivery(CheckDelivery checkDelivery, @RequestParam String taskId, Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		checkDelivery.setApprovalDm(2);
		checkDeliveryService.editCheckDelivery(checkDelivery);
		// 新增销售合同附件
		this.ediCheckDeliveryFj(checkDelivery.getFjsx(), request, checkDelivery);
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = checkDelivery.getClass().getSimpleName() + "." + String.valueOf(checkDelivery.getRow_Id());
		map.put("inputUser", user.getUserId());
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTasks(task, user, objId);
		// 启动流程
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "checkDelivery/editCheckDelivery";
	}

	// 将上传的附件写入数据库
	private void ediCheckDeliveryFj(String fjsx, HttpServletRequest request, CheckDelivery checkDelivery) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = checkDelivery.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(checkDelivery.getRow_Id());
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

	// 列表进入查看
	@RequestMapping(value = "/checkDeliveryShow.do")
	public String checkDeliveryShow(@RequestParam Integer row_Id, Model model) {
		// 格式化计划合同签订日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		CheckDelivery checkDeivery = checkDeliveryService.queryCheckDeliveryById(row_Id);
		// 获取送货单
		ERP_Delivery delivery = deliveryService.queryDeliveryById(checkDeivery.getDelivery_Id());
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());

		if (checkDeivery.getCreateTime() != null) {
			checkDeivery.setStartTime(sdf.format(checkDeivery.getCreateTime()));
		}
		String businessKey = checkDeivery.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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
		model.addAttribute("checkDelivery", checkDeivery);
		model.addAttribute("contract", contract);
		model.addAttribute("OBJDM", businessKey);
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("processInstanceId", processInstanceId);
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
	}

	// 加载所有的审批状态
	@RequestMapping(value = "/allSpzt.do")
	@ResponseBody
	public String allSpzt() {
		JSONArray approval = approvalService.allApproval();
		return approval.toString();
	}

	// ajax在编辑页面显示附件
	@RequestMapping(value = "/pageLoadFj.do")
	@ResponseBody
	public String pageLoadFj(Integer row_Id, HttpServletResponse response, HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		// 获得objId
		CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(row_Id);
		String objId = checkDelivery.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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
		CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(row_Id);
		String objId = checkDelivery.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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
