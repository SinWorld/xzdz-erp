package com.edge.currency.myTask.controller;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.entity.ERP_Sales_Contract_Order;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;
import com.edge.currency.alreadyTask.entity.AlreadyTask_QueryVo;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.completedTask.service.inter.CompletedTaskService;
import com.edge.currency.myTask.entity.MyTask;
import com.edge.currency.myTask.entity.MyTask_QueryVo;
import com.edge.currency.myTask.service.inter.MyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.google.gson.Gson;
import com.sun.star.text.RubyAdjust;

/**
 * 我的待办控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "myTask")
public class MyTaskController {
	@Resource
	private MyTaskService myTaskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private HistoryService historyService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private CompanyService companyService;

	@Resource
	private CustomerService customerService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private PingShenYJService pingShenYJService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private CompletedTaskService completedTaskService;

	// 跳转至系统首页
	@RequestMapping(value = "/indexPage.do")
	public String indexPage(HttpServletRequest request, Model model) {
		// 从request作用域中得到session
		HttpSession session = request.getSession();
		// 从session中得到当前登录用户的主键
		ERP_User user = (ERP_User) session.getAttribute("user");
		MyTask_QueryVo myTaskvo = new MyTask_QueryVo();
		myTaskvo.setUserId(user.getUserId());
		AlreadyTask_QueryVo allTaskvo = new AlreadyTask_QueryVo();
		allTaskvo.setUserId(user.getUserId());
		model.addAttribute("dbCount", myTaskService.myTaskCount(myTaskvo));
		model.addAttribute("ybCount", alreadyTaskService.userAlreadyTaskCount(allTaskvo));
		model.addAttribute("ywcCount", completedTaskService.CompletedTaskCount());
		return "currency/myTask/myTask";
	}

	// 分页查询我的代办
	@RequestMapping(value = "/myTaskList.do")
	@ResponseBody
	public String myTaskList(Integer page, Integer limit, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// new出MyTask_QueryVo查询对象
		MyTask_QueryVo vo = new MyTask_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setUserId(user.getUserId());
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", myTaskService.myTaskCount(vo));
		List<MyTask> myTaskList = myTaskService.myTaskList(vo);
		// 遍历该集合 设置代办集合
		for (MyTask myTask : myTaskList) {
			// 得到myTask中的PROC_DEF_ID_值得到流程实例对象
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(myTask.getPROC_INST_ID_()).singleResult();
			// 从流程变量中获得任务发起人主键
			Integer createUserId = (Integer) taskService.getVariable(myTask.getID_(), "inputUser");
			if (createUserId != null) {
				// 设置任务发起人姓名
				myTask.setCreateUser(userService.queryUserById(createUserId).getUserName());
			}
			// 取得businesskey
			String businesskey = processInstance.getBusinessKey();
			String id = businesskey.substring(businesskey.indexOf(".") + 1);
			myTask.setASSIGNEE_(user.getUserName());
			// 得到业务数据类型
			String object = businesskey.substring(0, businesskey.indexOf("."));
			if ("ERP_Sales_Contract".equals(object)) {// 表示业务流程（销售订单）
				// 获得ERP_Sales_Contract对象
				ERP_Sales_Contract sales = contractService.queryContractById(Integer.parseInt(id));
				// 获得任务描述 设置待办任务描述
				String taskDecription = "【" + myTask.getNAME_() + "】" + "  " + sales.getTask_Describe();
				myTask.setTaskDecription(taskDecription);
			}
		}
		map.put("data", myTaskList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 通过我的代办主键去关联查询运行时流程执行实例表从而得到业务数据表主键
	@RequestMapping(value = "/querObjId.do")
	@ResponseBody
	public String querObjId(@RequestParam String task_id) {
		// 1,使用任务ID，查询对象task
		Task task = taskService.createTaskQuery().taskId(task_id).singleResult();
		// 2.使用任务ID，获取实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3.使用流程实例，查询
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		// 4.使用流程实例对象获取BusinessKey
		String business_key = pi.getBusinessKey();
		String id = business_key.substring(business_key.indexOf(".") + 1);
		// new出JSONObject对象
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("taskId", task_id);
		jsonObject.put("obj", business_key.substring(0, business_key.indexOf(".")));
		return jsonObject.toString();
	}

	// 点击代办列表进入相应的查看页进行查看
	@RequestMapping(value = "/taskInfor.do")
	public String taskInfor(@RequestParam String taskId, Model model) {
		// 通过任务Id获取实例变量中的businessKey变量的值
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 获取流程实例Id
		String processInstanceId = null;
		if (task.getProcessInstanceId() != null) {
			processInstanceId = task.getProcessInstanceId();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String businessKey = processInstance.getBusinessKey();
		String obj = businessKey.substring(0, businessKey.indexOf("."));
		String objId = businessKey.substring(businessKey.indexOf(".") + 1, businessKey.length());
		// 加载批注信息
		List<SYS_WorkFlow_PingShenYJ> psyjList = pingShenYJService.psyjList(processInstanceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");// 设置日期格式
		for (SYS_WorkFlow_PingShenYJ p : psyjList) {
			p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
			p.setTime(sdf.format(p.getTIME_()));
		}
		if ("ERP_Sales_Contract".equals(obj)) {
			ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(objId));
			// 获得供方对象
			ERP_Our_Unit our_Unit = companyService.queryUnitById(contract.getSupplier());
			// 获得需求方对象
			ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
			// 获得销售合同货物清单对象
			List<ERP_Sales_Contract_Order> orderList = orderService.orderList(contract.getSales_Contract_Id());
			model.addAttribute("contract", contract);
			model.addAttribute("our_Unit", our_Unit);
			model.addAttribute("customer", customer);
			model.addAttribute("orderList", orderList);
			model.addAttribute("qdrq", sdf.format(contract.getQd_Date()));
			model.addAttribute("OBJDM", businessKey);
			model.addAttribute("reviewOpinions", psyjList);
			model.addAttribute("taskId", taskId);
			model.addAttribute("processInstanceId", processInstanceId);
			return "business/sale/saleShow";
		} else {
			return null;
		}
	}

	// 点击处理打开任务表单
	@RequestMapping(value = "/dealWith.do")
	@ResponseBody
	public String dealWith(String taskId) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String result = task.getFormKey();
		// 2.使用任务ID，获取实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3.使用流程实例，查询
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		// 4.使用流程实例对象获取BusinessKey
		String business_key = pi.getBusinessKey();
		jsonObject.put("business_key", business_key);
		jsonObject.put("result", result);
		jsonObject.put("taskId", taskId);
		return jsonObject.toString();
	}
}
