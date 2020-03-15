package com.edge.xshtsk.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.service.inter.XshtskService;

/**
 * 确认开票控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "qrkp")
public class QrkpController {

	@Resource
	private XshtskService xshtskService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private CustomerService customerService;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private EnclosureService enclosureService;

	// 跳转至确认开票页面
	@RequestMapping(value = "/initQrkp.do")
	public String initQrkp(@RequestParam String objId, String taskId, Model model) {
		String xshtskdm = objId.substring(objId.indexOf(".") + 1);
		ERP_Xshtsk xshtsk = xshtskService.queryXshtskById(Integer.parseInt(xshtskdm.trim()));
		ERP_Sales_Contract contract = contractService.queryContractById(xshtsk.getXsht());
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
		if (xshtsk.getIs_fpkj()) {
			model.addAttribute("fpkj", "是");
		} else {
			model.addAttribute("fpkj", "否");
		}
		model.addAttribute("xshtskdm", xshtskdm);
		model.addAttribute("taskId", taskId);
		model.addAttribute("contract", contract);
		model.addAttribute("customer", customer);
		return "xshtsk/saveQrkp";
	}

	// 提交表单推动流程
	@RequestMapping(value = "/saveQrkp.do")
	public String saveQrkp(ERP_Xshtsk xshtsk, @RequestParam String task_Id, HttpServletRequest request, Model model) {
		xshtskService.editXshtsk(xshtsk);
		this.editXshtFj(xshtsk.getFjsx(), request, xshtsk);
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		this.savelcsp(task, user, null, null);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(task_Id);
		model.addAttribute("flag", true);
		return "xshtsk/saveCwsh";
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

}
