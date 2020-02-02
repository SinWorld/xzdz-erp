package com.edge.currency.processOperation.TakeBack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 流程收回的控制跳转层
 *
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "takeBack")
public class TakeBackController {

	@Resource
	private HistoryService historyService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private TaskService taskService;

	@RequestMapping(value = "/recall.do")
	public void recall(@RequestParam("processInstanceId") String processInstanceId, String destTaskkey) {
		// 取得当前任务.当前任务节点
		destTaskkey = "usertask1";
		// HistoricTaskInstance currTask =
		// historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		Map<String, Object> variables;
		ExecutionEntity entity = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(entity.getProcessDefinitionId());
		variables = entity.getProcessVariables();
		// 当前活动环节
		ActivityImpl currActivityImpl = definition.findActivity(entity.getActivityId());
		// 目标活动节点
		ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition).findActivity(destTaskkey);
		if (currActivityImpl != null) {
			// 所有的出口集合
			List<PvmTransition> pvmTransitions = currActivityImpl.getOutgoingTransitions();
			List<PvmTransition> oriPvmTransitions = new ArrayList<PvmTransition>();
			for (PvmTransition transition : pvmTransitions) {
				oriPvmTransitions.add(transition);
			}
			// 清除所有出口
			pvmTransitions.clear();
			// 建立新的出口
			List<TransitionImpl> transitionImpls = new ArrayList<TransitionImpl>();
			TransitionImpl tImpl = currActivityImpl.createOutgoingTransition();
			tImpl.setDestination(nextActivityImpl);
			transitionImpls.add(tImpl);

			List<Task> list = taskService.createTaskQuery().processInstanceId(entity.getProcessInstanceId())
					.taskDefinitionKey(entity.getActivityId()).list();
			for (Task task : list) {
				taskService.complete(task.getId(), variables);
				historyService.deleteHistoricTaskInstance(task.getId());
			}

			for (TransitionImpl transitionImpl : transitionImpls) {
				currActivityImpl.getOutgoingTransitions().remove(transitionImpl);
			}

			for (PvmTransition pvmTransition : oriPvmTransitions) {
				pvmTransitions.add(pvmTransition);
			}
		}
	}
}
