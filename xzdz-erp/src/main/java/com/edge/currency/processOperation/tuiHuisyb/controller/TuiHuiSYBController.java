package com.edge.currency.processOperation.tuiHuisyb.controller;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 退回上一步控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "/thsyb")
public class TuiHuiSYBController {

	@Resource
	private HistoryService historyService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private TaskService taskService;

	// 退回上一步
	@RequestMapping(value = "/thsyb.do")
	public String thsyb(@RequestParam String taskId) throws Exception {
//		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
//		if (null == task) {
//			throw new Exception("无效任务ID[ " + taskId + " ]");
//		}
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
//				.processInstanceId(task.getProcessInstanceId()).singleResult();
//		if (null == processInstance) {
//			throw new Exception("该流程已完成!无法回退");
//		}
//		// 获取流程定义对象
//		BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
//		org.activiti.bpmn.model.Process process = bpmnModel.getProcesses().get(0);
//		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//		FlowNode sourceNode = (FlowNode) process.getFlowElement(task.getTaskDefinitionKey());
//		taskList.forEach(obj -> {
//			FlowNode currentNode = (FlowNode) process.getFlowElement(obj.getTaskDefinitionKey());
//			// 获取原本流程连线
//			List<SequenceFlow> outComingSequenceFlows = currentNode.getOutgoingFlows();
//
//			// 配置反转流程连线
//			SequenceFlow sequenceFlow = new SequenceFlow();
//			sequenceFlow.setTargetFlowElement(sourceNode);
//			sequenceFlow.setSourceFlowElement(currentNode);
//			sequenceFlow.setId("callback-flow");
//
//			List<SequenceFlow> newOutComingSequenceFlows = new ArrayList<>();
//			newOutComingSequenceFlows.add(sequenceFlow);
//			currentNode.setOutgoingFlows(newOutComingSequenceFlows);
//
//			// 配置任务审批人
//			Map<String, Object> variables = new HashMap<>(1);
//			variables.put(WorkFlowConfiguration.DEFAULT_USER_TASK_ASSIGNEE,
//					UserEntityService.getCurrentUserEntity().getUserId());
//			// 完成任务
//			taskService.complete(obj.getId(), variables);
//			// 复原流程
//			currentNode.setOutgoingFlows(outComingSequenceFlows);
//		});
		return null;
	}
}
