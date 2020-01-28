package com.edge.admin.processDefinition.controller;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.processDefinition.entity.SYS_WorkFlow_Operation;
import com.edge.admin.processDefinition.service.inter.OpeantionService;

/**
 * 流程权限操作控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "opeantion")
public class OpeantionController {
	@Resource
	private OpeantionService opeantionService;

	@Resource
	private RepositoryService repositoryService;

	// 跳转至授权列表页面
	@RequestMapping(value = "/empowerList.do")
	public String empower(@RequestParam String pdId, Model mod) {
		// 根据流程部署主键获取该流程操作集合
		List<SYS_WorkFlow_Operation> list = opeantionService.queryOperationByProId(pdId);
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId)
				.singleResult();
		String processName = processDefinition.getName();
		// 如果该集合不为空则新增流程节点信息及流程部署主键
		if (list == null || list.size() == 0) {
			// 获取流程部署对象
			BpmnModel model = repositoryService.getBpmnModel(pdId);
			if (model != null) {
				Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
				for (FlowElement e : flowElements) {
					if (e instanceof UserTask) {
						// new出流程操作对象
						SYS_WorkFlow_Operation operation = new SYS_WorkFlow_Operation();
						operation.setS_Id(e.getId());
						operation.setActiviti_Name(e.getName());
						operation.setProcdef_Id(pdId);
						opeantionService.saveWorkOperation(operation);
//							System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:"
//									+ e.getClass().toString());
					}
				}

			}
			mod.addAttribute("list", opeantionService.queryOperationByProId(pdId));
			mod.addAttribute("processName", processName);
			return "admin/processDefinition/operationList";
		} else {
			mod.addAttribute("list", opeantionService.queryOperationByProId(pdId));
			mod.addAttribute("processName", processName);
			return "admin/processDefinition/operationList";

		}

	}

	// 编辑授权列表(授权)
	@RequestMapping(value = "/empower.do")
	@ResponseBody
	public String empower(@RequestBody SYS_WorkFlow_Operation[] opeantion) {
		JSONObject jsonObject = new JSONObject();
		for (SYS_WorkFlow_Operation o : opeantion) {
			opeantionService.editWorkOperation(o);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}
}
