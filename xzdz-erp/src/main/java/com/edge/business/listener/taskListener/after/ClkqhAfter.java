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

/**
 * 材料库取货操作之后，设置流程检视
 * 
 * @author NingCG
 *
 */
public class ClkqhAfter implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		Date endTime = new Date();
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		WorkFlowLcjsService lcjsService = (WorkFlowLcjsService) ac.getBean("workFlowLcjsServiceImpl");
		// 获取Session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
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
