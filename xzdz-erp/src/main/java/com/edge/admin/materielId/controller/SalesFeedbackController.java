package com.edge.admin.materielId.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;

/***
 * 销售反馈控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "SalesFeedback")
public class SalesFeedbackController {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";// 定义MySQL的数据库驱动程序
	public static final String DBURL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";// 定义MySQL的数据库的连接地址
	public static final String DBUSER = "erp";// MySQL数据库的连接用户名
	public static final String DBPASS = "xzdz_erp";// MySQL数据库的连接用密码

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

	// 跳转至销售反馈界面
	@RequestMapping(value = "/initSalesFeedback.do")
	public String initSalesFeedback(@RequestParam String objId, String taskId, Model model) {
		String materielIdDm = objId.substring(objId.indexOf(".") + 1);
		model.addAttribute("materielIdDm", materielIdDm);
		model.addAttribute("taskId", taskId);
		return "admin/materielId/business/xsfk";
	}

	// 提交表单推动流程
	@RequestMapping(value = "/saveSalesFeedback.do")
	public String saveCwsh(@RequestParam String task_Id, String advice_, String fjsx, HttpServletRequest request,
			Model model) {
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String businessKey = processInstance.getBusinessKey();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("outcome", "物料Id录入");
		this.savelcsp(task, user, null, advice_);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		this.editXshtFj(fjsx, request, businessKey);
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "admin/materielId/business/xsfk";
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
	private void editXshtFj(String fjsx, HttpServletRequest request, String businessKey) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
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
				fj.setOBJDM(businessKey);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
	}

	// 跳转至销售反馈的编辑页
	@RequestMapping(value = "/initEditSalesFeedback.do")
	public String initEditSalesFeedback(@RequestParam String objId, String taskId, Model model,
			HttpServletRequest request)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Connection con = null;// 定义一个Oracle的连接对象
		PreparedStatement pstmt = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();// Oracle驱动
		con = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPASS);// 连接本地的Oracle
		con.setAutoCommit(false);// 设置不让事务自动提交
		String sql = "select p.message_infor_ from sys_workflow_pingshenyj p where p.user_id_=? and p.task_name_=? and p.proc_inst_id_=?";
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, user.getUserId());
		pstmt.setString(2, task.getName());
		pstmt.setString(3, task.getProcessInstanceId());
		ResultSet resultSet = pstmt.executeQuery();
		String message_infor_ = null;
		// 5.结果集解析
		if (resultSet.next()) {
			message_infor_ = resultSet.getString("message_infor_");
		}
		String materielIdDm = objId.substring(objId.indexOf(".") + 1);
		model.addAttribute("materielIdDm", materielIdDm);
		model.addAttribute("taskId", taskId);
		model.addAttribute("message_infor_", message_infor_);
		return "admin/materielId/business/editXsfk";
	}

	// 提交表单推动流程
	@RequestMapping(value = "/editSalesFeedback.do")
	public String editSalesFeedback(@RequestParam String task_Id, String advice_, String fjsx,
			HttpServletRequest request, Model model) {
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String businessKey = processInstance.getBusinessKey();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("outcome", "物料Id录入");
		this.savelcsp(task, user, null, advice_);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		this.editXshtFj(fjsx, request, businessKey);
		taskService.complete(task_Id, variables);
		model.addAttribute("flag", true);
		return "admin/materielId/business/editXsfk";
	}

}
