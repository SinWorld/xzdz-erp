package com.edge.cghtfk.listener.taskListener.after;

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
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.currency.processView.entity.Sys_WorkFlow_Lcjs;
import com.edge.currency.processView.service.inter.WorkFlowLcjsService;

/**
 * 发起付款申请操作之后,设置流程变量
 * 
 * @author NingCG
 *
 */
public class FqfksqAfter implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		Date beginTime = new Date();
		Date endTime = new Date();
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		ERP_UserService erp_UserService = (ERP_UserService) ac.getBean("ERP_UserServiceImpl");
		WorkFlowLcjsService lcjsService = (WorkFlowLcjsService) ac.getBean("workFlowLcjsServiceImpl");
		// 获取当前办理任务的用户对象
//		// 获取Session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		// ERP_User user =
		// erp_UserService.queryUserById(Integer.parseInt(delegateTask.getAssignee()));
		// 设置流程检视
		try {
			this.saveLcjs(delegateTask.getName(), user.getUserName(), beginTime, endTime,
					delegateTask.getExecution().getBusinessKey(), lcjsService, delegateTask.getProcessInstanceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新增流程检视数据
	private void saveLcjs(String taskName, String userNames, Date beginTime, Date endTime, String objId,
			WorkFlowLcjsService lcjsService, String processInstanceId) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		// 设置流程检视数据
		Sys_WorkFlow_Lcjs lcjs = new Sys_WorkFlow_Lcjs();
		// 设置当前节点名称
		lcjs.setNodeName(taskName);
		// 设置当前节点办理人名称
		lcjs.setProcessingUsers(userNames);
		lcjs.setNodeInfo(userNames + "-->" + "已办理");
		lcjs.setBeginTime(sdf.format(beginTime));
		lcjs.setEndTime(sdf.format(endTime));
		lcjs.setObjDm(objId);
		lcjsService.saveLcjs(lcjs);
	}

}
