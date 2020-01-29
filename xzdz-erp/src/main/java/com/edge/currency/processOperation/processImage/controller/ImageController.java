package com.edge.currency.processOperation.processImage.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edge.currency.processOperation.processImage.service.impl.HistoryProcessInstanceDiagramCmd;

/**
 * 自定义流程图
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "viewImage")
public class ImageController {

	@Resource
	private TaskService taskService;

	@Resource
	private ManagementService managementService;

	/**
	 * <功能简述>流程跟踪图 <功能详细描述>
	 * 
	 * @param taskId   任务id
	 * @param response
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping("/graphHistoryProcessInstance.do")
	public void graphHistoryProcessInstance(@RequestParam("taskId") String taskId, HttpServletResponse response)
			throws Exception {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);
		InputStream is = managementService.executeCommand(cmd);
		response.setContentType("image/png");

		int len = 0;
		byte[] b = new byte[1024];

		while ((len = is.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		is.close();
	}
}
