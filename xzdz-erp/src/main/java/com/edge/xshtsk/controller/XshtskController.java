package com.edge.xshtsk.controller;

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
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.entity.ERP_Xshtsk_QueryVo;
import com.edge.xshtsk.service.inter.XshtskService;
import com.google.gson.Gson;

/**
 * 销售合同收款控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "xshtsk")
public class XshtskController {

	public static final String ftpHost = "192.168.0.106";// ftp文档服务器Ip

	public static final String ftpUserName = "administrator";// ftp文档服务器登录用户名

	public static final String ftpPassword = "123";// ftp文档服务器登录密码

	public static final int ftpPort = 21;// ftp文档服务器登录端口

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private CustomerService customerService;

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

	@Resource
	private ApprovalService approvalService;

	// 销售合同收款验证（若完成收款则不能在发起收款申请）
	@RequestMapping(value = "/checkXshtsk.do")
	@ResponseBody
	public String checkXshtsk(Integer xshtdm) {
		JSONObject jsonObject = new JSONObject();
		// 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(xshtdm);
		if (contract.getStatus().trim() != "已发货") {
			jsonObject.put("flag", true);
			jsonObject.put("infor", "当前销售合同下的销售订单流程未审核完，不允许发起收款！！！");
		}
		if (contract.getIs_wcsk()) {
			jsonObject.put("flag", true);
			jsonObject.put("infor", "当前销售合同已完成收款，不允许发起收款！！！");
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 跳转至销售合同收款页面
	@RequestMapping(value = "/initSaveXshtsk.do")
	public String initSaveXshtsk(@RequestParam Integer xsht, Model model) {
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(xsht);
		// 获得付款方信息
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
		model.addAttribute("contract", contract);
		model.addAttribute("customer", customer);
		model.addAttribute("ljkpje", xshtskService.querySumLjkpje(xsht));
		model.addAttribute("ljkpjebl", (xshtskService.querySumLjkpje(xsht) / contract.getHtje()) * 100 + "%");
		model.addAttribute("sykpje", contract.getHtje() - xshtskService.querySumLjkpje(xsht));
		model.addAttribute("ljskje", xshtskService.querySumSjskje(xsht));
		model.addAttribute("ljskjebl", (xshtskService.querySumSjskje(xsht) / contract.getHtje()) * 100 + "%");
		model.addAttribute("syskje", contract.getHtje() - xshtskService.querySumSjskje(xsht));
		return "xshtsk/saveXshtsk";
	}

	// 跳转至销售合同收款列表页面
	@RequestMapping(value = "/initXshtskList.do")
	public String initXshtskList() {
		return "xshtsk/xshtskList";
	}

	// 分页查询销售合同收款列表
	@RequestMapping(value = "/xshtskList.do")
	@ResponseBody
	public String xshtskList(Integer page, Integer limit) {
		// new出ERP_Xshtsk_QueryVo查询对象
		ERP_Xshtsk_QueryVo vo = new ERP_Xshtsk_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", xshtskService.xshtskCount(vo));
		List<ERP_Xshtsk> xshtskList = xshtskService.xshtskList(vo);
		for (ERP_Xshtsk x : xshtskList) {
			// 销售订单
			if (x.getXsht() != null) {
				ERP_Sales_Contract contract = contractService.queryContractById(x.getXsht());
				x.setXshtName(contract.getSales_Contract_Name());
			}
			if (x.getIs_fpkj()) {
				x.setSfkpkj("是");
			} else {
				x.setSfkpkj("否");
			}
			if (x.getIs_fplb()) {
				x.setFplb("增值税专用发票");
			} else {
				x.setFplb("增值税普通发票");
			}
			ERP_DM_Approval approval = approvalService.queryApprovalById(x.getApprovaldm());
			x.setApprovalmc(approval.getApprovalmc());
		}
		map.put("data", xshtskList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 提交表单启动销售合同收款流程
	@RequestMapping(value = "/saveXshtsk.do")
	public String saveXshtsk(ERP_Xshtsk xshtsk, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		xshtsk.setTask_describe("【任务名称：销售合同收款】");
		xshtsk.setApprovaldm(2);
		xshtskService.saveXshtsk(xshtsk);
		// 新增销售合同附件
		this.addXshtFj(xshtsk.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = xshtsk.getClass().getSimpleName() + "." + String.valueOf(xshtskService.maxXshtdm());
		map.put("inputUser", user.getUserId());
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Xshtsk", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "xshtsk/saveXshtsk";
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
			// 根据销售合同收款主键获得销售合同收款对象
			ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(xshtskService.maxXshtdm());
			String key = xshtsk.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(xshtsk.getXshtskdm());
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

	// 跳转至销售合同收款编辑页
	@RequestMapping(value = "/initEditXshtsk.do")
	public String initEditXshtsk(@RequestParam String objId, String taskId, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");// 设置日期格式
		String xshtskdm = objId.substring(objId.indexOf(".") + 1);
		// 根据Id获得销售合同收款页面
		ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(Integer.parseInt(xshtskdm.trim()));
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
		// 获得付款方信息
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
		if (xshtsk.getSqkprq() != null) {
			xshtsk.setShenqkprq(sdf.format(xshtsk.getSqkprq()));
		}
		if (xshtsk.getKprq() != null) {
			xshtsk.setKaiprq(sdf.format(xshtsk.getKprq()));
		}
		model.addAttribute("contract", contract);
		model.addAttribute("customer", customer);
		model.addAttribute("xshtsk", xshtsk);
		model.addAttribute("taskId", taskId);
		return "xshtsk/editXshtsk";
	}

	// 编辑销售合同收款推动流程
	@RequestMapping(value = "/editXshtsk.do")
	public String editXshtsk(ERP_Xshtsk xshtsk, @RequestParam String taskId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		xshtsk.setApprovaldm(2);
		xshtskService.editXshtsk(xshtsk);
		// 新增销售合同附件
		this.editXshtFj(xshtsk.getFjsx(), request, xshtsk);
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = xshtsk.getClass().getSimpleName() + "." + String.valueOf(xshtsk.getXshtskdm());
		map.put("inputUser", user.getUserId());
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTasks(task, user, objId);
		// 启动流程
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "xshtsk/editXshtsk";
	}

	// 将上传的附件写入数据库
	private void editXshtFj(String fjsx, HttpServletRequest request, ERP_Xshtsk xshtsk) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = xshtsk.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(xshtsk.getXshtskdm());
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
	@RequestMapping(value = "/xshtskShow.do")
	public String xshtskShow(@RequestParam Integer xshtskdm, Model model) {
		// 格式化计划合同签订日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(xshtskdm);
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
		// 获得付款方信息 sdf
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
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
		if (xshtsk.getSqkprq() != null) {
			xshtsk.setShenqkprq(sdf.format(xshtsk.getSqkprq()));
		}
		if (xshtsk.getKprq() != null) {
			xshtsk.setKaiprq(sdf.format(xshtsk.getKprq()));
		}
		String businessKey = xshtsk.getClass().getSimpleName() + "." + String.valueOf(xshtskdm);
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
		model.addAttribute("xshtsk", xshtsk);
		model.addAttribute("contract", contract);
		model.addAttribute("customer", customer);
		model.addAttribute("OBJDM", businessKey);
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("ljkpje", xshtskService.querySumLjkpje(xshtsk.getXsht()));
		model.addAttribute("ljkpjebl",
				(xshtskService.querySumLjkpje(xshtsk.getXsht()) / contract.getHtje()) * 100 + "%");
		model.addAttribute("sykpje", contract.getHtje() - xshtskService.querySumLjkpje(xshtsk.getXsht()));
		model.addAttribute("ljskje", xshtskService.querySumSjskje(xshtsk.getXsht()));
		model.addAttribute("ljskjebl",
				(xshtskService.querySumSjskje(xshtsk.getXsht()) / contract.getHtje()) * 100 + "%");
		model.addAttribute("syskje", contract.getHtje() - xshtskService.querySumSjskje(xshtsk.getXsht()));
		return "xshtsk/xshtskShow";
	}

	// 跳转至补款页面
	@RequestMapping(value = "/initBk.do")
	public String initBk(@RequestParam Integer xshtskdm, Model model) {
		ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(xshtskdm);
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
		model.addAttribute("xshtskdm", xshtskdm);
		model.addAttribute("syskje", contract.getHtje() - xshtskService.querySumSjskje(xshtsk.getXsht()));
		return "xshtsk/cwbk";
	}

	// 销售合同收款验证（若完成收款则不能在发起收款申请）
	@RequestMapping(value = "/checkbk.do")
	@ResponseBody
	public String checkbk(Integer xshtskdm) {
		JSONObject jsonObject = new JSONObject();
		ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(xshtskdm);
		// 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
		if (xshtskService.querySumSjskje(contract.getSales_Contract_Id()).equals(contract.getHtje())) {
			jsonObject.put("flag", true);
			jsonObject.put("infor", "当前销售合同已完成收款，不允许发起补款！！！");
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 补款操作
	@RequestMapping(value = "/bk.do")
	public String bl(ERP_Xshtsk xshtsk, Model model) {
		xshtskService.editXshtsk(xshtsk);
		model.addAttribute("flag", true);
		return "xshtsk/cwbk";
	}

}
