package com.edge.business.listener.taskListener;

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

/**
 * 生产计划监听器 监听任务办理人的自动设置
 * 
 * @author NingCG
 *
 */
public class ProductionPlan implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		// 获取Session
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		// 获取当前登录系统的用户
		ERP_User user = (ERP_User) session.getAttribute("user");
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		String processDefinitionId = delegateTask.getProcessDefinitionId();
		System.out.println(processDefinitionId);
		// 获取 OpeantionService
		OpeantionService opeantionService = (OpeantionService) ac.getBean("opeantionServiceImpl");
		// 获取ERP_UserService
		ERP_UserService erp_UserService = (ERP_UserService) ac.getBean("ERP_UserServiceImpl");
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
		}
	}

}
