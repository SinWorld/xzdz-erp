package com.edge.business.listener.taskListener.after;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.edge.admin.user.entity.ERP_User;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.processView.entity.Sys_WorkFlow_Lcjs;
import com.edge.currency.processView.service.inter.WorkFlowLcjsService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 成品核对任务操作之后用于更新对应的库存状态的单位为该销售订单号,且设置流程检视
 * 
 * @author NingCG
 *
 */
public class CheckProductAfter implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		// 获取Session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		Date endTime = new Date();
		String cphds = (String) delegateTask.getVariable("cphd");
		// 获取businessKey
		String businessKey = delegateTask.getExecution().getBusinessKey();
		// 得到业务数据主键值
		String id = businessKey.substring(businessKey.indexOf(".") + 1);
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取 KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取ERP_Sales_ContractService
		ERP_Sales_ContractService contractService = (ERP_Sales_ContractService) ac
				.getBean("ERP_Sales_ContractServiceImpl");
		WorkFlowLcjsService lcjsService = (WorkFlowLcjsService) ac.getBean("workFlowLcjsServiceImpl");
		// 获取销售订单对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		if (cphds != "" && cphds != null) {
			String[] variables = cphds.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer productId = null;
				String[] datas = v.split(":");
				for (int i = 0; i < datas.length; i++) {
					productId = Integer.parseInt(datas[1].trim().trim());
				}
				// 根据成品Id获得成品库存状态对象
				ERP_Stock_Status status = statusService.queryStastusByCpId(productId);
				// 设置库存订单号为对应的销售订单号
				status.setOddNumbers(contract.getContract_Code());
				statusService.editStockStatus(status);
			}
		}
		// 获取流程检视对象
		Sys_WorkFlow_Lcjs lcjs = lcjsService.queryLcjsByInfor(delegateTask.getExecution().getBusinessKey(),
				delegateTask.getName());
		try {
			this.editLcjs(lcjs, delegateTask.getName(), user.getUserName(), endTime,
					delegateTask.getExecution().getBusinessKey(), lcjsService, delegateTask.getProcessInstanceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 编辑流程检视数据
	private void editLcjs(Sys_WorkFlow_Lcjs lcjs, String taskName, String userNames, Date endTime, String objId,
			WorkFlowLcjsService lcjsService, String processInstanceId) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		lcjs.setNodeName(taskName);
		// TaskDefinition taskDefinition =
		// lcjsService.getNextTaskInfo(processInstanceId);
		lcjs.setNodeInfo(userNames + " -->" + "已办理");
		lcjs.setEndTime(sdf.format(endTime));
		lcjs.setObjDm(objId);
		lcjsService.editLcjs(lcjs);
	}

}
