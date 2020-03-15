package com.edge.business.purchase.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;
import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.service.inter.SupplierService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;
import com.edge.business.purchase.entity.ERP_Purchase_List;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.entity.PurchaseOrder_QueryVo;
import com.edge.business.purchase.service.inter.PurchaseListService;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.google.gson.Gson;

/**
 * 采购模块控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "purchase")
public class PurchaseController {

	public static final String ftpHost = "192.168.0.106";// ftp文档服务器Ip

	public static final String ftpUserName = "administrator";// ftp文档服务器登录用户名

	public static final String ftpPassword = "123";// ftp文档服务器登录密码

	public static final int ftpPort = 21;// ftp文档服务器登录端口

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private PurchaseListService purchaseListService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private TaskService taskService;

	@Resource
	private MaterialPlanOrderService materialPlanOrderService;

	@Resource
	private CompanyService companyService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private EnclosureService enclosureService;

	// 跳转至采购订单页面
	@RequestMapping(value = "/initPurchase.do")
	public String initPurchase(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 获得加工配料流程变量
		String variable = (String) taskService.getVariable(taskId, "jgpl");
		List<MaterialPlanOrder> orders = new ArrayList<MaterialPlanOrder>();
		if (variable != "" && variable != null) {
			String[] variables = variable.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer row_Id = null;
				Integer cgsl = null;
				String[] datas = v.split(":");
				row_Id = Integer.parseInt(datas[0].trim().trim());
				cgsl = Integer.parseInt(datas[1].trim().trim());
				// 获得row_Id获得材料计划货物项对象
				MaterialPlanOrder order = materialPlanOrderService.queryOrderById(row_Id);
				order.setCgsl(cgsl);
				orders.add(order);
			}
		}
		model.addAttribute("orders", orders);
		model.addAttribute("sales_Contract_Id", id);
		model.addAttribute("taskId", taskId);
		return "business/purchase/savePurchase";
	}

	// ajax加载所有的供货单位
	@RequestMapping(value = "/allSupplier.do")
	@ResponseBody
	public String allSupplier() {
		JSONArray jsonArray = purchaseOrderService.allSupplier();
		return jsonArray.toString();
	}

	// ajax加载所有的我方单位
	@RequestMapping(value = "/allUnit.do")
	@ResponseBody
	public String allUnit() {
		JSONArray allUnit = companyService.allUnit();
		return allUnit.toString();
	}

	// 合同编号生成
	@RequestMapping(value = "/htbh.do")
	@ResponseBody
	public String htbh() {
		JSONObject jsonObject = new JSONObject();
		// 编号规则 CG-年度-月份-日期-3位流水 例如 XZ-2020-01-17-001
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String time = year + "-" + month + "-" + day;
		String bh = "CG" + "-" + time;
		// 根据该编号去模糊检索查询出最大流水号的编号
		String maxHtbh = purchaseOrderService.htbh(bh);
		// 如果最大合同编号为空则流水号为001否则需要切割字符串进行新增
		String newHtbh = null;
		if (maxHtbh == null) {
			newHtbh = bh + "-001";
		} else {
			String maxLiuShuiH = maxHtbh.substring(bh.length() + 1);
			int nextLiuShuiH = Integer.parseInt(maxLiuShuiH) + 1;
			newHtbh = bh + "-" + String.format("%03d", nextLiuShuiH);
		}
		jsonObject.put("htbh", newHtbh);
		return jsonObject.toString();
	}

	// ajax查询供应商设置其属性
	@RequestMapping(value = "/supplier.do")
	@ResponseBody
	public String supplier(Integer supplier_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Supplier supplier = supplierService.querySupplierById(supplier_Id);
		jsonObject.put("supplier", supplier);
		return jsonObject.toString();
	}

	// 提价表单并推动流程
	@RequestMapping(value = "/savePurchaseOrder.do")
	@ResponseBody
	public String savePurchaseOrder(@RequestBody ERP_Purchase_Order purchaseOrder, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(purchaseOrder.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		// 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(purchaseOrder.getSales_Contract_Id());
		ERP_Our_Unit unit = companyService.queryUnitById(contract.getSupplier());
		if (unit != null) {
			purchaseOrder.setOur_uint(unit.getUnit_Id());
		}
		purchaseOrder.setStatus("已下单");
		purchaseOrder.setIs_Availability(false);
		purchaseOrder.setQd_Date(new Date());
		purchaseOrder.setGfqd_Date(new Date());
		purchaseOrder.setSub_Date(new Date());
		purchaseOrder.setUserId(user.getUserId());
		purchaseOrderService.savePurchaseOrder(purchaseOrder);
		// 新增采购合同附件
		this.addXshtFj(purchaseOrder.getFjsx(), request, purchaseOrder.getSales_Contract_Id());
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "领导审核");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增生产计划货物项
	@RequestMapping(value = "/savePurchaseList.do")
	@ResponseBody
	public String savePurchaseList(@RequestBody ERP_Purchase_List[] purchaseList) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Purchase_List p : purchaseList) {
			// 根据销售合同获得采购合同对象
			ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderByXsht(p.getXshtdm());
			p.setPur_Order_Id(purchaseOrder.getPur_Order_Id());
			purchaseListService.savePurchaseList(p);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
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
		alreadyTask.setCOMPLETION_STATUS_("审批中");
		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
	}

	// 跳转至采购合同列表页面
	@RequestMapping(value = "/initPurchaseOrderList.do")
	public String initPurchaseOrderList() {
		return "business/purchase/purchaseList";
	}

	// 分页查询采购合同列表
	@RequestMapping(value = "/purchaseOrderList.do")
	@ResponseBody
	public String purchaseOrderList(Integer page, Integer limit, String htmc, String htbh, Integer ghdw, Integer wfdw,
			Integer xsht, Integer jbr, String htzt, String beginTime, String endTime) {
		// new出PurchaseOrder_QueryVo查询对象
		PurchaseOrder_QueryVo vo = new PurchaseOrder_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setHtmc(htmc);
		vo.setHtbh(htbh);
		vo.setGhdw(ghdw);
		vo.setWfdw(wfdw);
		vo.setXsht(xsht);
		vo.setJbr(jbr);
		vo.setHtzt(htzt);
		vo.setBeginTime(beginTime);
		vo.setEndTime(endTime);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", purchaseOrderService.purchasePrderOrderCount(vo));
		List<ERP_Purchase_Order> list = purchaseOrderService.purchasePrderOrderList(vo);
		for (ERP_Purchase_Order l : list) {
			// 获得销售合同对象
			ERP_Sales_Contract contract = contractService.queryContractById(l.getSales_Contract_Id());
			// 获得我方单位
			ERP_Our_Unit unit = companyService.queryUnitById(l.getOur_uint());
			// 获得供应商
			ERP_Supplier supplier = supplierService.querySupplierById(l.getSupplier());
			// 操作员
			ERP_User user = userService.queryUserById(l.getUserId());
			if (contract != null) {
				l.setSales_Contract_Name(contract.getSales_Contract_Name());
			}
			l.setSupplierName(supplier.getSupplier_Name());
			l.setUserName(user.getUserName());
			l.setUintName(unit.getUnit_Name());
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 点击采购合同列表进入查看
	@RequestMapping(value = "/purchaseOrderShow.do")
	public String purchaseOrderShow(@RequestParam Integer pur_Order_Id, Model model) {
		// 根据id获得采购合同对象
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(pur_Order_Id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<ERP_Purchase_List> purchaseList = null;
		if (purchaseOrder != null) {
			// 设置属性
			ERP_Supplier supplier = supplierService.querySupplierById(purchaseOrder.getSupplier());
			purchaseOrder.setSupplierName(supplier.getSupplier_Name());
			purchaseOrder.setTelPhone(supplier.getPhone());
			purchaseOrder.setDgrq(sdf.format(purchaseOrder.getPur_Date()));
			// 获得采购清单集合
			purchaseList = purchaseListService.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
		}
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("purchaseList", purchaseList);
		String businessKey = purchaseOrder.getClass().getSimpleName() + "." + purchaseOrder.getPur_Order_Id();
		model.addAttribute("OBJDM", businessKey);

		return "business/purchase/purchaseShow";
	}

	// 跳转至采购合同编辑页面
	@RequestMapping(value = "/initEditPurchase.do")
	public String initEditPurchase(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 获得加工配料流程变量
		String variable = (String) taskService.getVariable(taskId, "jgpl");
		List<MaterialPlanOrder> orders = new ArrayList<MaterialPlanOrder>();
		if (variable != "" && variable != null) {
			String[] variables = variable.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer row_Id = null;
				Integer cgsl = null;
				String[] datas = v.split(":");
				row_Id = Integer.parseInt(datas[0].trim().trim());
				cgsl = Integer.parseInt(datas[1].trim().trim());
				// 获得row_Id获得材料计划货物项对象
				MaterialPlanOrder order = materialPlanOrderService.queryOrderById(row_Id);
				order.setCgsl(cgsl);
				orders.add(order);
			}
		}
		// 根据销售合同获得采购合同对象
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderByXsht(Integer.parseInt(id.trim()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		purchaseOrder.setDgrq(sdf.format(purchaseOrder.getPur_Date()));
		// 根据采购合同对象获得采购合同清单集合
		// 获得采购清单集合
		List<ERP_Purchase_List> purchaseList = purchaseListService
				.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
		model.addAttribute("orders", orders);
		model.addAttribute("purchaseOrder", purchaseOrder);
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("taskId", taskId);
		return "business/purchase/editPurchase";

	}

	// 根据id删除采购清单对象
	@RequestMapping(value = "/deletePuraseListById.do")
	@ResponseBody
	public String deletePuraseListById(Integer pur_Id) {
		JSONObject jsonObject = new JSONObject();
		purchaseListService.deletePurchaseListById(pur_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 提交表单编辑采购合同并推动流程
	@RequestMapping(value = "/editPurchaseOrder.do")
	@ResponseBody
	public String editPurchaseOrder(@RequestBody ERP_Purchase_Order purchaseOrder, HttpServletRequest request,
			Model model) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(purchaseOrder.getTaskId()).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		// 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(purchaseOrder.getSales_Contract_Id());
		ERP_Our_Unit unit = companyService.queryUnitById(contract.getSupplier());
		if (unit != null) {
			purchaseOrder.setOur_uint(unit.getUnit_Id());
		}
		purchaseOrder.setStatus("已下单");
		purchaseOrder.setIs_Availability(false);
		purchaseOrder.setQd_Date(new Date());
		purchaseOrder.setGfqd_Date(new Date());
		purchaseOrder.setSub_Date(new Date());
		purchaseOrder.setUserId(user.getUserId());
		purchaseOrderService.editPurchaseOrder(purchaseOrder);
		// 新增采购合同附件
		this.addXshtFj(purchaseOrder.getFjsx(), request, purchaseOrder.getSales_Contract_Id());
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.savelcsp(task, user, null, null);
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 设置流程变量
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "领导审核");
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增或编辑生产计划货物项
	@RequestMapping(value = "/saveOrEditPurchaseList.do")
	@ResponseBody
	public String saveOrEditPurchaseList(@RequestBody ERP_Purchase_List[] purchaseList) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Purchase_List p : purchaseList) {
			if (p.getPur_Id() != null) {
				purchaseListService.editPurchaseList(p);
			} else {
				// 根据销售合同获得采购合同对象
				ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderByXsht(p.getXshtdm());
				p.setPur_Order_Id(purchaseOrder.getPur_Order_Id());
				purchaseListService.savePurchaseList(p);
			}

		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 将上传的附件写入数据库
	private void addXshtFj(String fjsx, HttpServletRequest request, Integer xsht) {
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		List<String> list = new ArrayList<String>();
		// 将fjsx进行字符截取
		if (fjsx.hashCode() != 0) {
			String fjvalue = fjsx.substring(1, fjsx.length());
			list.add(fjvalue);
			String value = list.toString();
			Date date = new Date();
			// 根据采购合同主键获得采购合同对象
			ERP_Purchase_Order cght = purchaseOrderService.queryPurchaseOrderByXsht(xsht);
			String key = cght.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(cght.getPur_Order_Id());
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

}
