package com.edge.applicationCenter.materielIdProcess.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.entity.MaterielType;
import com.edge.admin.materielId.service.inter.MaterielTypeService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess;
import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess_QueryVo;
import com.edge.applicationCenter.materielIdProcess.service.inter.MaterielIdProcessService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.google.gson.Gson;

/**
 * 物料ID评审控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materielIdProcess")
public class MaterielIdProcessController {

	@Resource
	private MaterielIdProcessService materielIdProcessService;

	@Resource
	private MaterielTypeService materielTypeService;

	@Resource
	private ApprovalService approvalService;

	@Resource
	private EnclosureService enclosureService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ERP_UserService userService;

	// 跳转至物料ID评审列表页面
	@RequestMapping(value = "/initMaterielIdProcessList.do")
	public String initMaterielIdProcessList() {
		return "applicationCenter/materielIdProcess/materielIdProcessList";
	}

	// 列表查询
	@RequestMapping(value = "/materielIdProcessList.do")
	@ResponseBody
	public String materielIdProcessList(@RequestParam Integer page, Integer limit, String wlId, String ggxh, String bzq,
			String ckj1, String ckj2, Integer wlidlx, Integer wlidh, String wlIdms, String beginTime, String endTime,
			Integer materielTypeId) {
		// new出查询对象
		MaterielIdProcess_QueryVo vo = new MaterielIdProcess_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// new 出List集合用于存储点击物料ID类型后对应的物料ID对象
		List<MaterielIdProcess> clickWLIDS = new ArrayList<MaterielIdProcess>();
		if (materielTypeId != null) {// 点击物料ID类型展现对应的物料ID数据
			List<Integer> wlidlxIds = queryWLIDLXId(materielTypeId);
			List<MaterielIdProcess> wlidParent = materielIdProcessService.queryWLIDProcessByWLIDLX(materielTypeId);
			if (wlidParent.size() > 0) {
				for (MaterielIdProcess w : wlidParent) {
					clickWLIDS.add(w);
				}
			}
			// 遍历 wlidlxIds集合
			for (Integer dm : wlidlxIds) {
				// 根据代码查询对应的文件柜对象
				List<MaterielIdProcess> wlIds = materielIdProcessService.queryWLIDProcessByWLIDLX(dm);
				if (wlIds.size() > 0) {
					for (MaterielIdProcess w : wlIds) {
						clickWLIDS.add(w);
					}
				}
			}
			vo.setPage((page - 1) * limit + 1);
			vo.setRows(page * limit);
			vo.setWlId(wlId);
			vo.setGgxh(ggxh);
			vo.setBzq(bzq);
			vo.setMaterielType(wlidlx);
			vo.setMaterielNumber(wlidh);
			vo.setWlIdms(wlIdms);
			vo.setBeginTime(beginTime);
			vo.setEndTime(endTime);
			if (ckj1 != null && ckj1 != "") {
				vo.setCkj1(Double.valueOf(ckj1.trim()));
			}
			if (ckj2 != null && ckj2 != "") {
				vo.setCkj2(Double.valueOf(ckj2.trim()));
			}
			for (MaterielIdProcess c : clickWLIDS) {
				MaterielType materielType = materielTypeService.queryMaterielTypeById(c.getMaterielType());
				MaterielType materielNumber = materielTypeService.queryMaterielTypeById(c.getMaterielNumber());
				ERP_DM_Approval approval = approvalService.queryApprovalById(c.getApprovaldm());
				c.setMaterielTypeName(materielType.getTitle());
				c.setMaterielNumberName(materielNumber.getTitle());
				if (approval != null) {
					c.setApprovalmc(approval.getApprovalmc());
				}
			}
			map.put("code", 0);
			map.put("msg", "");
			map.put("count", clickWLIDS.size());
			map.put("data", clickWLIDS);
			String json = gson.toJson(map);
			return json.toString();
		} else {
			vo.setPage((page - 1) * limit + 1);
			vo.setRows(page * limit);
			vo.setWlId(wlId);
			vo.setGgxh(ggxh);
			vo.setBzq(bzq);
			vo.setMaterielType(wlidlx);
			vo.setMaterielNumber(wlidh);
			vo.setWlIdms(wlIdms);
			vo.setBeginTime(beginTime);
			vo.setEndTime(endTime);
			if (ckj1 != null && ckj1 != "") {
				vo.setCkj1(Double.valueOf(ckj1.trim()));
			}
			if (ckj2 != null && ckj2 != "") {
				vo.setCkj2(Double.valueOf(ckj2.trim()));
			}
			List<MaterielIdProcess> materielIdProcessList = materielIdProcessService.materielIdProcessList(vo);
			for (MaterielIdProcess m : materielIdProcessList) {
				MaterielType materielType = materielTypeService.queryMaterielTypeById(m.getMaterielType());
				MaterielType materielNumber = materielTypeService.queryMaterielTypeById(m.getMaterielNumber());
				ERP_DM_Approval approval = approvalService.queryApprovalById(m.getApprovaldm());
				m.setMaterielTypeName(materielType.getTitle());
				m.setMaterielNumberName(materielNumber.getTitle());
				if (approval != null) {
					m.setApprovalmc(approval.getApprovalmc());
				}
			}
			map.put("code", 0);
			map.put("msg", "");
			map.put("count", materielIdProcessService.materielIdProcessCount(vo));
			map.put("data", materielIdProcessList);
			String json = gson.toJson(map);
			return json.toString();
		}
	}

	// 点击物料ID类型查询该物料ID类型下所有的子节点主键
	private List<Integer> queryWLIDLXId(Integer materielTypeId) {
		// new 出List集合用于存储所有的文件夹代码
		List<Integer> wjjdms = new ArrayList<Integer>();
		// 根据文件夹代码查询子文件夹集合
		List<MaterielType> childrenMaterielTypeTree = materielTypeService.childrenMaterielTypeTree(materielTypeId);
		this.queryWJJTree(childrenMaterielTypeTree, wjjdms);
		return wjjdms;
	}

	// 递归查询该物料ID类型下所有的子节点
	private void queryWJJTree(List<MaterielType> childrens, List<Integer> wjjdms) {
		// 遍历该集合
		for (MaterielType c : childrens) {
			wjjdms.add(c.getId());
			List<MaterielType> childrenWJJ = materielTypeService.childrenMaterielTypeTree(c.getId());
			queryWJJTree(childrenWJJ, wjjdms);
		}

	}

	// 跳转至新增页面
	@RequestMapping(value = "/initFqsp.do")
	public String initFqsp() {
		return "applicationCenter/materielIdProcess/business/fqps";
	}

	// 新增物料ID并启动物料ID流程
	@RequestMapping(value = "/saveMaterielIdProcess.do")
	public String saveMaterielIdProcess(@RequestParam String sm, MaterielIdProcess materielIdProcess, Model model,
			HttpServletRequest request) throws Exception {
		// 流程检视 开始时间
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		materielIdProcess.setTask_describe("【任务名称：物料Id评审】");
		materielIdProcess.setApprovaldm(2);
		materielIdProcessService.saveMaterielIdProcess(materielIdProcess);
		// 新增销售合同附件
		this.addXshtFj(materielIdProcess.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = materielIdProcess.getClass().getSimpleName() + "."
				+ String.valueOf(materielIdProcessService.queryMaxRowId());
		map.put("inputUser", user.getUserId());
		map.put("outcome", "技术评审");
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("MaterielIdProcess", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user, sm);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "applicationCenter/materielIdProcess/business/fqps";
	}

	// 将上传的附件写入数据库
	private void addXshtFj(String fjsx, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			// 根据物料Id评审主键获得物料Id评审对象
			MaterielIdProcess materielIdProcess = materielIdProcessService
					.queryMaterielIdProcessById(materielIdProcessService.queryMaxRowId());
			String key = materielIdProcess.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(materielIdProcessService.queryMaxRowId());
			// 将字符串转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(value);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String localFileName = (String) obj.get("localFileName");// 上传文件名
				String path = (String) obj.get("path");// 上传文件地址
				String fileName = (String) obj.get("fileName");// 上传文件真实名
				// new 出附件对象
				Enclosure fj = new Enclosure();
				fj.setCUNCHUWJM(localFileName);// 上传文件名
				fj.setSHANGCHUANDZ(path);// 上传文件地址
				fj.setREALWJM(fileName);// 上传文件真实名称
				fj.setSHANGCHUANRQ(date);// 上传文件日期
				fj.setSHANGCHUANYHDM(user.getUserId());// 上传用户主键
				fj.setOBJDM(objId);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
	}

	// 新增流程审批
	private void savelcsp(Task task, ERP_User user, String khsm) {
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(task.getProcessInstanceId());
		r.setTASK_ID_(task.getId());
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		r.setTASK_NAME_(task.getName());
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(null);
		r.setMESSAGE_INFOR_(khsm);
		r.setTITLE_("已办理");
		pingShenYjService.savePingShenYJ(r);
	}

	// 新增已办数据集
	private void saveAlreadyTask(Task task, ERP_User user, String objId) {
		AlreadyTask alreadyTask = new AlreadyTask();
		alreadyTask.setTASK_ID_(task.getId());
		alreadyTask.setREV_(null);
		alreadyTask.setEXECUTION_ID_(task.getExecutionId());
		alreadyTask.setPROC_INST_ID_(task.getProcessInstanceId());
		alreadyTask.setPROC_DEF_ID_(task.getProcessDefinitionId());
		alreadyTask.setNAME_(task.getName());
		alreadyTask.setPARENT_TASK_ID_(task.getParentTaskId());
		alreadyTask.setDESCRIPTION_(task.getDescription());
		alreadyTask.setTASK_DEF_KEY_(task.getTaskDefinitionKey());
		alreadyTask.setOWNER_(task.getOwner());
		alreadyTask.setASSIGNEE_(String.valueOf(user.getUserId()));
		alreadyTask.setDELEGATION_(null);
		alreadyTask.setPRIORITY_(task.getPriority());
		alreadyTask.setSTART_TIME_(task.getCreateTime());
		alreadyTask.setEND_TIME_(new Date());
		alreadyTask.setFORM_KEY_(task.getFormKey());
		alreadyTask.setBUSINESS_KEY_(objId);
		alreadyTask.setCOMPLETION_STATUS_("开始");
		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
	}

	// 查看操作
	@RequestMapping(value = "/showMaterielIdProcess.do")
	public String showMaterielIdProcess(@RequestParam Integer row_Id, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		MaterielIdProcess materielIdProcess = materielIdProcessService.queryMaterielIdProcessById(row_Id);
		MaterielType materielType = materielTypeService.queryMaterielTypeById(materielIdProcess.getMaterielType());
		MaterielType materielNumber = materielTypeService.queryMaterielTypeById(materielIdProcess.getMaterielNumber());
		materielIdProcess.setMaterielTypeName(materielType.getTitle());
		materielIdProcess.setMaterielNumberName(materielNumber.getTitle());
		String businessKey = materielIdProcess.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据businessKey获取历史流程实例对象
		HistoricProcessInstance hisp = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();
		// 若流程实例不为空,则获取流程实例主键
		String processInstanceId = null;
		List<SYS_WorkFlow_PingShenYJ> psyjList = null;
		if (hisp != null) {
			processInstanceId = hisp.getId();
			psyjList = pingShenYjService.psyjList(processInstanceId);
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
			}
		}
		model.addAttribute("OBJDM", businessKey);
		model.addAttribute("materielId", materielIdProcess);
		if (materielIdProcess.getCreateTime() != null) {
			model.addAttribute("lrrq", sdf.format(materielIdProcess.getCreateTime()));
		}
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("processInstanceId", processInstanceId);
		return "applicationCenter/materielIdProcess/showMaterielIdProcess";
	}

	// 跳转至物料Id录入页面
	@RequestMapping(value = "/initEditMaterielIdProcess.do")
	public String initEditMaterielIdProcess(@RequestParam String objId, String taskId, Model model) {
		String materielIdDm = objId.substring(objId.indexOf(".") + 1);
		MaterielIdProcess materielIdProcess = materielIdProcessService
				.queryMaterielIdProcessById(Integer.parseInt(materielIdDm));
		MaterielType materielType = materielTypeService.queryMaterielTypeById(materielIdProcess.getMaterielType());
		MaterielType materielNumber = materielTypeService.queryMaterielTypeById(materielIdProcess.getMaterielNumber());
		materielIdProcess.setMaterielTypeName(materielType.getTitle());
		materielIdProcess.setMaterielNumberName(materielNumber.getTitle());
		model.addAttribute("materielId", materielIdProcess);
		model.addAttribute("materielIdDm", materielIdDm);
		model.addAttribute("taskId", taskId);
		return "applicationCenter/materielIdProcess/editMaterielIdProcess";
	}

	// 提交表单编辑物料Id评审，推动流程
	@RequestMapping(value = "/editMaterielIdProcess.do")
	public String editMaterielIdProcess(@RequestParam String fjsx, MaterielIdProcess materielIdProcess, String task_Id,
			Model model, HttpServletRequest request) {
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String businessKey = processInstance.getBusinessKey();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		materielIdProcessService.editMaterielIdProcess(materielIdProcess);
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		this.savelcsp(task, user, null, null);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTasks(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		this.editXshtFj(fjsx, request, materielIdProcess);
		taskService.complete(task_Id);
		model.addAttribute("flag", true);
		return "applicationCenter/materielIdProcess/editMaterielIdProcess";
	}

	// 新增流程审批
	private void savelcsp(Task task, ERP_User user, String outcome, String advice) {
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(task.getProcessInstanceId());
		r.setTASK_ID_(task.getId());
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		r.setTASK_NAME_(task.getName());
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(outcome);
		r.setMESSAGE_INFOR_(advice);
		r.setTITLE_("已办理");
		pingShenYjService.savePingShenYJ(r);
	}

	// 新增已办数据集
	private void saveAlreadyTasks(Task task, ERP_User user, String objId) {
		AlreadyTask alreadyTask = new AlreadyTask();
		alreadyTask.setTASK_ID_(task.getId());
		alreadyTask.setREV_(null);
		alreadyTask.setEXECUTION_ID_(task.getExecutionId());
		alreadyTask.setPROC_INST_ID_(task.getProcessInstanceId());
		alreadyTask.setPROC_DEF_ID_(task.getProcessDefinitionId());
		alreadyTask.setNAME_(task.getName());
		alreadyTask.setPARENT_TASK_ID_(task.getParentTaskId());
		alreadyTask.setDESCRIPTION_(task.getDescription());
		alreadyTask.setTASK_DEF_KEY_(task.getTaskDefinitionKey());
		alreadyTask.setOWNER_(task.getOwner());
		alreadyTask.setASSIGNEE_(String.valueOf(user.getUserId()));
		alreadyTask.setDELEGATION_(null);
		alreadyTask.setPRIORITY_(task.getPriority());
		alreadyTask.setSTART_TIME_(task.getCreateTime());
		alreadyTask.setEND_TIME_(new Date());
		alreadyTask.setFORM_KEY_(task.getFormKey());
		alreadyTask.setBUSINESS_KEY_(objId);
		alreadyTask.setCOMPLETION_STATUS_("完成");
		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
	}

	// 将上传的附件写入数据库
	private void editXshtFj(String fjsx, HttpServletRequest request, MaterielIdProcess materielIdProcess) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = materielIdProcess.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(materielIdProcess.getRow_Id());
			// 将字符串转换为json数组
			JSONArray jsonArray = JSONArray.parseArray(value);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String localFileName = (String) obj.get("localFileName");// 上传文件名
				String path = (String) obj.get("path");// 上传文件地址
				String fileName = (String) obj.get("fileName");// 上传文件真实名
				// new 出附件对象
				Enclosure fj = new Enclosure();
				fj.setCUNCHUWJM(localFileName);// 上传文件名
				fj.setSHANGCHUANDZ(path);// 上传文件地址
				fj.setREALWJM(fileName);// 上传文件真实名称
				fj.setSHANGCHUANRQ(date);// 上传文件日期
				fj.setSHANGCHUANYHDM(user.getUserId());// 上传用户主键
				fj.setOBJDM(objId);// 上传业务数据主键
				enclosureService.saveEnclosure(fj);// 添加附件
			}
		}
	}

	// ajax在编辑页面显示附件
	@RequestMapping(value = "/pageLoadFj.do")
	@ResponseBody
	public String pageLoadFj(Integer row_Id, HttpServletResponse response, HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		MaterielIdProcess materielIdProcess = materielIdProcessService.queryMaterielIdProcessById(row_Id);
		// 获得objId
		String objId = materielIdProcess.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据objId获得附件集合
		List<Enclosure> enclosureList = enclosureService.enclosureList(objId);
		// 遍历该集合
		for (Enclosure e : enclosureList) {
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + e.getSHANGCHUANDZ() + "/" + e.getREALWJM());
			File file = new File(filePath.trim());
			String fileName = file.getName();
			DecimalFormat df = new DecimalFormat("#.00");
			String fileSizeString = df.format((double) file.length() / 1024) + "KB";
			// System.out.println("fileName = " + fileName + " " + "size=" +
			// fileSizeString);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fileName", fileName);
			jsonObject.put("fileSize", fileSizeString);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 删除附件
	@RequestMapping(value = "/removeFj.do")
	@ResponseBody
	public String removeFj(Integer row_Id, String fileName, HttpServletResponse response, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		MaterielIdProcess materielIdProcess = materielIdProcessService.queryMaterielIdProcessById(row_Id);
		// 获得objId
		String objId = materielIdProcess.getClass().getSimpleName() + "." + String.valueOf(row_Id);
		// 根据objId获得附件集合
		List<Enclosure> enclosureList = enclosureService.enclosureList(objId);
		for (Enclosure e : enclosureList) {
			if (fileName.equals(e.getREALWJM())) {
				// 删除数据库中的附件
				enclosureService.deleteFjByObj(e.getFUJIANDM());
			}
			// 删除服务器端的附件文件
			String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
					.getRealPath("/fj/" + e.getSHANGCHUANDZ() + "/" + e.getREALWJM());
			File file = new File(filePath.trim());
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// ajax物料Id不重复
	@RequestMapping(value = "/wlIdbcf.do")
	@ResponseBody
	public String wlIdbcf(String materiel_Id, Integer materielNumber) {
		JSONObject jsonObject = new JSONObject();
		MaterielIdProcess materielIdProcess = materielIdProcessService.queryERP_MaterielIdByWLID(materiel_Id.trim(),
				materielNumber);
		if (materielIdProcess != null) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 根据单选按钮加载物料ID号下拉选
	@RequestMapping(value = "reloadMaterielNumber.do")
	@ResponseBody
	public String reloadMaterielNumber(Integer id) {
		JSONArray jsonArray = new JSONArray();
		List<MaterielType> list = materielTypeService.childrenMaterielTypeTree(id);
		for (MaterielType l : list) {
			jsonArray.add(l);
		}
		return jsonArray.toString();
	}

	// ajax加载单选按钮的物料Id类型
	@RequestMapping(value = "/reloadMaterielType.do")
	@ResponseBody
	public String reloadMaterielType() {
		JSONArray jsonArray = new JSONArray();
		List<MaterielType> materielType = materielTypeService.queryMaterielType();
		for (MaterielType m : materielType) {
			jsonArray.add(m);
		}
		return jsonArray.toString();
	}
}
