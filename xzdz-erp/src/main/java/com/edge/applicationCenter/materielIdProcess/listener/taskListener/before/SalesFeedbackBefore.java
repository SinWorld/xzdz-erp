package com.edge.applicationCenter.materielIdProcess.listener.taskListener.before;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;
import com.edge.admin.processDefinition.service.inter.OpeantionService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.currency.processView.entity.Sys_WorkFlow_Lcjs;
import com.edge.currency.processView.service.inter.WorkFlowLcjsService;

/**
 * 销售反馈操作之前，用于设置该节点的任务办理人
 * 
 * @author NingCG
 *
 */
public class SalesFeedbackBefore implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		Date beginTime = new Date();
		// 获取Session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> userNames = new ArrayList<String>();
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		String processDefinitionId = delegateTask.getProcessDefinitionId();
		System.out.println(processDefinitionId);
		// 获取 OpeantionService
		OpeantionService opeantionService = (OpeantionService) ac.getBean("opeantionServiceImpl");
		// 获取ERP_UserService
		ERP_UserService erp_UserService = (ERP_UserService) ac.getBean("ERP_UserServiceImpl");
		WorkFlowLcjsService lcjsService = (WorkFlowLcjsService) ac.getBean("workFlowLcjsServiceImpl");
		// 获取当前流程下的所有操作集合
		List<SYS_WorkFlow_Operation> list = opeantionService.queryOperationByProId(processDefinitionId);
		List<ERP_User> userList = null;
		// 遍历该集合
		for (SYS_WorkFlow_Operation l : list) {
			// 若操作集合中的节点主键与当前任务节点的主键一致则设置该节点的办理人
			if (delegateTask.getTaskDefinitionKey().equals(l.getS_Id())) {
				// 根据岗位主键获得用户候选组集合
				userList = erp_UserService.queryUserByPost(l.getPost_Id());
				break;
			}
		}
		for (ERP_User u : userList) {
			// 设置下一节点拥有该角色的用户进行办理
			delegateTask.addCandidateUser(String.valueOf(u.getUserId()));
			userNames.add(u.getUserName());
		}
		try {
			this.saveLcjs(delegateTask.getName(), userNames.toString(), beginTime,
					delegateTask.getExecution().getBusinessKey(), lcjsService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新增流程检视数据
	private void saveLcjs(String taskName, String userNames, Date beginTime, String objId,
			WorkFlowLcjsService lcjsService) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		// 设置流程检视数据
		Sys_WorkFlow_Lcjs lcjs = new Sys_WorkFlow_Lcjs();
		// 设置当前节点名称
		lcjs.setNodeName(taskName);
		// 设置当前节点办理人名称
		lcjs.setProcessingUsers(userNames);
		lcjs.setNodeInfo(null);
		lcjs.setBeginTime(sdf.format(beginTime));
		lcjs.setEndTime("未完成");
		lcjs.setObjDm(objId);
		lcjsService.saveLcjs(lcjs);
	}

}
