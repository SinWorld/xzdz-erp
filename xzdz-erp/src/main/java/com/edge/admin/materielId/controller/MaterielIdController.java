package com.edge.admin.materielId.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omg.CORBA.SystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.entity.MaterielType;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.materielId.service.inter.MaterielTypeService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.processView.service.inter.WorkFlowLcjsService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.utils.ExcelUtils;
import com.google.gson.Gson;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 物料Id控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "materielId")
public class MaterielIdController {

	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";// 定义MySQL的数据库驱动程序
	public static final String DBURL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";// 定义MySQL的数据库的连接地址
	public static final String DBUSER = "erp";// MySQL数据库的连接用户名
	public static final String DBPASS = "xzdz_erp";// MySQL数据库的连接用密码

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private EnclosureService enclosureService;

	@Resource
	private MaterielTypeService materielTypeService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private WorkFlowLcjsService lcjsService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ApprovalService approvalService;

	// 跳转至物料Id列表页面
	@RequestMapping(value = "/initMaterielIdList.do")
	public String initMaterielIdList() {
		return "admin/materielId/materielIdList";
	}

	// easyUI 列表查询
	@RequestMapping(value = "/materielIdList.do")
	@ResponseBody
	public String materielIdList(@RequestParam Integer page, Integer rows, String wlId, String ggxh, String bzq,
			String ckj1, String ckj2) {
		// new出查询对象
		ERP_MaterielId_QueryVo vo = new ERP_MaterielId_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		vo.setWlId(wlId);
		vo.setGgxh(ggxh);
		vo.setBzq(bzq);
		if (ckj1 != null && ckj1 != "") {
			vo.setCkj1(Double.valueOf(ckj1.trim()));
		}
		if (ckj2 != null && ckj2 != "") {
			vo.setCkj2(Double.valueOf(ckj2.trim()));
		}
		List<ERP_MaterielId> materielIdList = materielIdService.materielIdList(vo);
		for (ERP_MaterielId m : materielIdList) {
			MaterielType materielType = materielTypeService.queryMaterielTypeById(m.getMaterielNumber());
			ERP_DM_Approval approval = approvalService.queryApprovalById(m.getApprovaldm());
			m.setMaterielTypeName(materielType.getTitle());
			m.setMaterielNumberName(materielType.getTitle());
			if (approval != null) {
				m.setApprovalmc(approval.getApprovalmc());
			}
		}
		map.put("total", materielIdService.materielIdCount(vo));
		map.put("rows", materielIdList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至新增页面
	@RequestMapping(value = "/initFqsp.do")
	public String initFqsp() {
		return "admin/materielId/business/fqps";
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

	// 新增物料ID并启动物料ID流程
	@RequestMapping(value = "/saveMaterielId.do")
	public String saveMaterielId(@RequestParam String sm, ERP_MaterielId materielId, Model model,
			HttpServletRequest request) throws Exception {
		// 流程检视 开始时间
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		materielId.setTask_describe("【任务名称：物料Id评审】");
		materielId.setApprovaldm(2);
		materielIdService.saveMaterielId(materielId);
		// 新增销售合同附件
		this.addXshtFj(materielId.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String objId = materielId.getClass().getSimpleName() + "."
				+ String.valueOf(materielIdService.queryMaxMaterielId());
		map.put("inputUser", user.getUserId());
		map.put("outcome", "技术评审");
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ERP_MaterielId", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user, sm);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		model.addAttribute("flag", true);
		return "xshtsk/saveXshtsk";
	}

	// ajax物料Id不重复
	@RequestMapping(value = "/wlIdbcf.do")
	@ResponseBody
	public String wlIdbcf(String materiel_Id, Integer materielNumber) {
		JSONObject jsonObject = new JSONObject();
		ERP_MaterielId erp_MaterielId = materielIdService.queryERP_MaterielIdByWLID(materiel_Id.trim(), materielNumber);
		if (erp_MaterielId != null) {
			jsonObject.put("flag", true);
		} else {
			jsonObject.put("flag", false);
		}
		return jsonObject.toString();
	}

	// 新增物料Id
//	@RequestMapping(value = "/saveMaterielId.do")
//	public String saveMaterielId(@RequestParam String fjsx, ERP_MaterielId materielId, Model model,
//			HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		ERP_User user = (ERP_User) session.getAttribute("user");
//		materielIdService.saveMaterielId(materielId);
//		this.addXshtFj(fjsx, request);
//		model.addAttribute("flag", true);
//		model.addAttribute("userId", user.getUserId());
//		return "admin/materielId/saveMaterielId";
//	}

	// 跳转至物料Id录入页面
	@RequestMapping(value = "/initSaveMaterielId.do")
	public String initSaveMaterielId(@RequestParam String objId, String taskId, Model model) {
		String materielIdDm = objId.substring(objId.indexOf(".") + 1);
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(Integer.parseInt(materielIdDm));
		MaterielType materielType = materielTypeService.queryMaterielTypeById(materielId.getMaterielType());
		MaterielType materielNumber = materielTypeService.queryMaterielTypeById(materielId.getMaterielNumber());
		materielId.setMaterielTypeName(materielType.getTitle());
		materielId.setMaterielNumberName(materielNumber.getTitle());
		model.addAttribute("materielId", materielId);
		model.addAttribute("materielIdDm", materielIdDm);
		model.addAttribute("taskId", taskId);
		return "admin/materielId/editMaterielId";
	}

	// 提交表单编辑物料Id，推动流程
	@RequestMapping(value = "/editMaterielId.do")
	public String editMaterielId(@RequestParam String fjsx, ERP_MaterielId materielId, String task_Id, Model model,
			HttpServletRequest request) {
		Task task = taskService.createTaskQuery().taskId(task_Id).singleResult();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		String businessKey = processInstance.getBusinessKey();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		materielIdService.editMaterielId(materielId);
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		this.savelcsp(task, user, null, null);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTasks(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		this.editXshtFj(fjsx, request, materielId);
		taskService.complete(task_Id);
		model.addAttribute("flag", true);
		return "admin/materielId/editMaterielId";
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

	// 跳转至物料编辑页面
//	@RequestMapping(value = "/initEditMaterielId.do")
//	public String initEditMaterielId(@RequestParam Integer row_Id, Model model) {
//		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
//		model.addAttribute("materielId", materielId);
//		return "admin/materielId/editMaterielId";
//	}

	// 编辑操作
//	@RequestMapping(value = "/editMaterielId.do")
//	public String editMaterielId(@RequestParam String fjsx, ERP_MaterielId materielId, Model model,
//			HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		ERP_User user = (ERP_User) session.getAttribute("user");
//		materielIdService.editMaterielId(materielId);
//		this.editXshtFj(fjsx, request, materielId);
//		model.addAttribute("flag", true);
//		model.addAttribute("userId", user.getUserId());
//		return "admin/materielId/editMaterielId";
//	}

	// 删除操作
	@RequestMapping(value = "/deleteMaterielId.do")
	@ResponseBody
	public String deleteMaterielId(Integer row_Id) {
		JSONObject jsonObject = new JSONObject();
		materielIdService.deleteMaterelId(row_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 批量删除
	@RequestMapping(value = "/batchDeletePost.do")
	@ResponseBody
	public String batchDeletePost(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			materielIdService.deleteMaterelId(Integer.parseInt(id.trim()));
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 查看操作
	@RequestMapping(value = "/showMaterielId.do")
	public String showMaterielId(@RequestParam Integer row_Id, Model model) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		MaterielType materielType = materielTypeService.queryMaterielTypeById(materielId.getMaterielType());
		MaterielType materielNumber = materielTypeService.queryMaterielTypeById(materielId.getMaterielNumber());
		materielId.setMaterielTypeName(materielType.getTitle());
		materielId.setMaterielNumberName(materielNumber.getTitle());
		String businessKey = materielId.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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
		model.addAttribute("materielId", materielId);
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("processInstanceId", processInstanceId);
		return "admin/materielId/showMaterielId";
	}

	// 查询成品的物料Id
	@RequestMapping(value = "/queryProWlId.do")
	@ResponseBody
	public String queryProWlId() {
		JSONArray jsonArray = materielIdService.queryProWlId();
		return jsonArray.toString();
	}

	// 查询材料的物料Id
	@RequestMapping(value = "/queryMatWlId.do")
	@ResponseBody
	public String queryMatWlId() {
		JSONArray jsonArray = materielIdService.queryMatWlId();
		return jsonArray.toString();
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
			// 根据物料Id主键获得物料Id对象
			ERP_MaterielId materielId = materielIdService.queryMaterielIdById(materielIdService.queryMaxMaterielId());
			String key = materielId.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(materielIdService.queryMaxMaterielId());
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

	// 将上传的附件写入数据库
	private void editXshtFj(String fjsx, HttpServletRequest request, ERP_MaterielId materielId) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			String key = materielId.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(materielId.getRow_Id());
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
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		// 获得objId
		String objId = materielId.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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
		ERP_MaterielId materielId = materielIdService.queryMaterielIdById(row_Id);
		// 获得objId
		String objId = materielId.getClass().getSimpleName() + "." + String.valueOf(row_Id);
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

	// 跳转至文件导入页面
	@RequestMapping(value = "/initFileImport.do")
	public String initFileImport() {
		return "admin/materielId/fileImport";
	}

	// 导入Excel
	@RequestMapping({ "/importExcel.do" })
	public String importExcel(@RequestParam MultipartFile file, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			loadExcel(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("flag", true);
		model.addAttribute("userId", user.getUserId());
		return "admin/materielId/fileImport";
	}

	public void loadExcel(InputStream is) {
		try {
			Workbook wb = Workbook.getWorkbook(is);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			for (int i = 1; i < rows; i++) {
				List oneData = new ArrayList();
				Cell[] cells = sheet.getRow(i);
				for (int j = 0; j < cells.length; j++) {
					oneData.add(cells[j].getContents().trim());
				}
				if (oneData.size() > 0) {
					// new 出物料Id对象
					ERP_MaterielId materielId = new ERP_MaterielId();
					materielId.setMateriel_Id(String.valueOf(oneData.get(1)));
					materielId.setSpecification_Type(String.valueOf(oneData.get(2)));
					materielId.setBzq(String.valueOf(oneData.get(3)));
					Double ckdj = Double.parseDouble(String.valueOf(oneData.get(4)));
					materielId.setCkdj(ckdj);
					String materielType = (String) oneData.get(5);
					MaterielType type = null;
					if ("成品".equals(materielType.trim())) {
						type = materielTypeService.checkMaterielType(-1, "成品");
						if (type != null) {
							materielId.setMaterielType(type.getId());
						}
					} else if ("半成品".equals(materielType.trim())) {
						type = materielTypeService.checkMaterielType(-1, "半成品");
						if (type != null) {
							materielId.setMaterielType(type.getId());
						}
					} else if ("材料".equals(materielType.trim())) {
						type = materielTypeService.checkMaterielType(-1, "材料");
						if (type != null) {
							materielId.setMaterielType(type.getId());
						}
					}
					String materielNumber = (String) oneData.get(6);
					MaterielType number = materielTypeService.checkMaterielType(materielId.getMaterielType(),
							materielNumber.trim());
					if (number != null) {
						materielId.setMaterielNumber(number.getId());
					}
					materielId.setRemarks(String.valueOf(oneData.get(7)));
					materielId.setApprovaldm(null);
					materielId.setTask_describe(null);
					/**
					 * 物料id不允许重复，若重复了则将该数据修改，否则新增
					 */
					ERP_MaterielId erp_MaterielId = materielIdService.queryERP_MaterielIdByWLID(
							materielId.getMateriel_Id().trim(), materielId.getMaterielNumber());
					if (erp_MaterielId != null) {
						// 说明存在 则修改
						materielId.setRow_Id(erp_MaterielId.getRow_Id());
						materielIdService.editMaterielId(materielId);
					} else {
						materielIdService.saveMaterielId(materielId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 导出Excel
	@RequestMapping({ "/exportExcel.do" })
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("GBK");
		writeExcel(response);
	}

	public static void writeExcel(HttpServletResponse response) throws Exception {
		String[] titles = { "序号", "物料Id", "规格型号", "保质期", "参考单价", "物料ID类型", "物料ID类型号", "物料描述" };
		String sheetTitle = "物料Id";
		ExcelUtils eeu = new ExcelUtils();
		HSSFWorkbook workbook = new HSSFWorkbook();
		OutputStream os = response.getOutputStream();
		List apkDate = getApkDate(); // 取出数据
		sheetTitle = new String(sheetTitle.getBytes("gb2312"), "iso8859-1");
		response.reset();
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition", "attachment; filename=" + sheetTitle + ".xls");
		eeu.exportExcel(workbook, 0, "物料Id", titles, apkDate, os);
		workbook.write(os);
		os.close();
	}

	private static List<List<String>> getApkDate() throws SQLException, SystemException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Connection con = null;// 定义一个MySQL的连接对象
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();// MySQL驱动
		con = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPASS);// 连接本地的MySQL
		con.setAutoCommit(false);// 设置不让事务自动提交
		Statement stmt;// 创建声明
		stmt = con.createStatement();
		String sql = "select  rownum," + " a.materiel_id," + " a.specification_type," + " a.bzq," + " a.ckdj,"
				+ " t.title as wlidType," + " m.title as wlidnumber," + " a.remarks" + " from erp_materielid a"
				+ " left join erp_materieltype t" + " on a.materieltype = t.id" + " left join erp_materieltype m"
				+ " on a.materielnumber = m.id";
		ResultSet res = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = res.getMetaData();
		List datelist = new ArrayList();
		int colCnt = rsmd.getColumnCount();
		while (res.next()) {
			List list = new ArrayList();
			for (int j = 1; j < colCnt + 1; j++) {
				String colName = rsmd.getColumnName(j);
				String colValue = res.getString(colName);
				list.add(colValue);
			}
			datelist.add(list);
		}
		return datelist;
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

}
