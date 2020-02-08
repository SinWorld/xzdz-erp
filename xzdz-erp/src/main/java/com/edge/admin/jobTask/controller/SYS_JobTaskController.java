package com.edge.admin.jobTask.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.jobTask.entity.SYS_JobTask;
import com.edge.admin.jobTask.service.inter.SYS_JobTaskService;
import com.google.gson.Gson;

/**
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "jobTask")
public class SYS_JobTaskController {
	@Resource
	private SYS_JobTaskService jobTaskService;

	// 跳转至定时任务列表页面
	@RequestMapping(value = "/initjobTaskList.do")
	public String initjobTaskList() {
		return "admin/jobTask/jobTaskList";
	}

	// 定时任务列表查询
	@RequestMapping(value = "/jobTaskList.do")
	@ResponseBody
	public String jobTaskList(@RequestParam Integer page, Integer rows) {
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<SYS_JobTask> jobTaskList = jobTaskService.jobTaskList(page, rows);
		map.put("total", jobTaskService.jobTaskListCount());
		map.put("rows", jobTaskList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至定时任务新增页面
	@RequestMapping(value = "/initSaveJobTaks.do")
	public String initSaveJobTaks() {
		return "admin/jobTask/saveJobTask";
	}

	// 新增定时任务
	@RequestMapping(value = "/saveJobTask.do")
	public String saveJobTask(SYS_JobTask jobTask, Model model) {
		jobTaskService.saveJobTask(jobTask);
		model.addAttribute("flag", true);
		return "admin/jobTask/saveJobTask";
	}

	// 跳转至编辑页面
	@RequestMapping(value = "/initEditJobTask.do")
	public String initEditJobTask(@RequestParam Integer job_Task_Id_, Model model) {
		SYS_JobTask jobTask = jobTaskService.queryJobTaskById(job_Task_Id_);
		model.addAttribute("jobTask", jobTask);
		return "admin/jobTask/editJobTask";
	}

	// 编辑操作
	@RequestMapping(value = "/editJobTask.do")
	public String editJobTask(SYS_JobTask jobTask, Model model) {
		jobTaskService.editJobTask(jobTask);
		model.addAttribute("flag", true);
		return "admin/jobTask/editJobTask";
	}

	// 删除操作
	@RequestMapping(value = "/deleteJobTask.do")
	@ResponseBody
	public String deleteJobTask(Integer job_Task_Id_) {
		JSONObject jsonObject = new JSONObject();
		jobTaskService.deleteJobTask(job_Task_Id_);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}
}
