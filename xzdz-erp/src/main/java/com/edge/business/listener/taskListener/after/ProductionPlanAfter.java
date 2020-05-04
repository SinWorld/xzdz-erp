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
import com.edge.currency.processView.entity.Sys_WorkFlow_Lcjs;
import com.edge.currency.processView.service.inter.WorkFlowLcjsService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 生产计划操作之后用于更新流程变量中的状态为待出库,且设置流程检视
 * 
 * @author NingCG
 *
 */
public class ProductionPlanAfter implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		Date endTime = new Date();
		// 获取Session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		String cphds = (String) delegateTask.getVariable("cphd");
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取 KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		WorkFlowLcjsService lcjsService = (WorkFlowLcjsService) ac.getBean("workFlowLcjsServiceImpl");
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
				// 设置库存状态
				status.setStatus("待出库");
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
