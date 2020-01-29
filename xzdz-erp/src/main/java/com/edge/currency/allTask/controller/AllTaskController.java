package com.edge.currency.allTask.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.edge.currency.allTask.entity.AllTask;
import com.edge.currency.allTask.entity.AllTask_QueryVo;
import com.edge.currency.allTask.service.inter.AllTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.google.gson.Gson;

/**
 * 已办控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "allTask")
public class AllTaskController {
	@Resource
	private AllTaskService allTaskService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private PingShenYJService pingShenYJService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private HistoryService historyService;

	@Resource
	private CompanyService companyService;

	@Resource
	private CustomerService customerService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private RuntimeService runtimeService;

	// 查询当前用户所有的已办任务
	@RequestMapping(value = "/allTask.do")
	@ResponseBody
	public String allTask(Integer page, Integer limit, HttpServletRequest request) {
		// 获取session
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		// new出AllTask_QueryVo查询对象
		AllTask_QueryVo vo = new AllTask_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setUserId(user.getUserId());
		// 总页数
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", allTaskService.allTaskCount(vo));
		List<AllTask> allTaskList = allTaskService.allTaskList(vo);
		// 遍历该集合 设置已办集合
		for (AllTask a : allTaskList) {
			// 取得businesskey
			String businesskey = a.getBUSINESS_KEY_();
			String id = businesskey.substring(businesskey.indexOf(".") + 1);
			a.setUserName(user.getUserName());
			// 得到业务数据类型
			String object = businesskey.substring(0, businesskey.indexOf("."));
			if ("ERP_Sales_Contract".equals(object)) {// 表示业务流程（销售订单）
				// 获得ERP_Sales_Contract对象
				ERP_Sales_Contract sales = contractService.queryContractById(Integer.parseInt(id));
				if (sales != null) {
					// 获得任务描述 设置待办任务描述
					String taskDecription = "【" + a.getNAME_() + "】" + "  " + sales.getTask_Describe();
					a.setTaskDecription(taskDecription);
				}
			}
		}
		map.put("data", allTaskList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 点击已办列表进入相应的查看页进行查看
	@RequestMapping(value = "/allTaskInfor.do")
	public String allTaskInfor(@RequestParam String id, String businessKey, Model model) {
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(id)
				.singleResult();
		String processInstanceId = historicTaskInstance.getProcessInstanceId();
		String processDefinitionId = historicTaskInstance.getProcessDefinitionId();
		String obj = businessKey.substring(0, businessKey.indexOf("."));
		String objId = businessKey.substring(businessKey.indexOf(".") + 1, businessKey.length());
		// 加载批注信息
		List<SYS_WorkFlow_PingShenYJ> psyjList = pingShenYJService.psyjList(processInstanceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");// 设置日期格式
		for (SYS_WorkFlow_PingShenYJ p : psyjList) {
			p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
			p.setTime(sdf.format(p.getTIME_()));
		}
		// 加载流程图
		ProcessDefinition pd = imgShow(processDefinitionId);
		// 流程节点高亮显示
		Map<String, Object> map = queryCoordingByTask(id);
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
			model.addAttribute("deploymentId", pd.getDeploymentId());
			model.addAttribute("imageName", pd.getDiagramResourceName());
			model.addAttribute("map", map);
			model.addAttribute("taskId", null);
			return "business/sale/saleShow";
		} else {
			return null;
		}
	}

	// 流程图显示
	public ProcessDefinition imgShow(String processDefinitionId) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询对象
				// 对应表act_re_procdef
				.processDefinitionId(processDefinitionId)// 使用流程定义Id对象
				.singleResult();
		return pd;
	}

	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
	 * map集合的key：表示坐标x,y,width,height map集合的value：表示坐标对应的值
	 */
	public Map<String, Object> queryCoordingByTask(String taskId) {
		// 存放坐标
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取流程定义Id
		String processDefinitionId = null;
		String processInstanceId = null;
		HistoricTaskInstance his = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		if (his != null) {
			processDefinitionId = his.getProcessDefinitionId();
			processInstanceId = his.getProcessInstanceId();
		}
		// 获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 流程实例ID
		// 使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()// 创建流程查询实例
				.processInstanceId(processInstanceId).singleResult();// 使用流程实例ID查询
		// 获取当前活动的ID
		if (pi != null) {
			String activityId = pi.getActivityId();
			// 获取当前活动对象
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);// 活动ID
			// 获取历史走过的节点对象
			// 获取坐标
			map.put("x", activityImpl.getX());
			map.put("y", activityImpl.getY() * 1 + 70);
			map.put("width", activityImpl.getWidth());
			map.put("height", activityImpl.getHeight());
			return map;
		} else {
			return null;
		}
	}
}
