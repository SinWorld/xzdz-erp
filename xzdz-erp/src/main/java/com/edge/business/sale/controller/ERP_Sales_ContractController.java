package com.edge.business.sale.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.department.service.inter.ERP_DepartmentService;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.checkProduct.entity.SYS_WorkFlow_Cphd;
import com.edge.business.checkProduct.service.inter.SYS_WorkFlow_CphdService;
import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.entity.ERP_DeliveryOrder;
import com.edge.business.ckfh.service.inter.DeliveryOrderService;
import com.edge.business.ckfh.service.inter.DeliveryService;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.entity.ProductionPlanOrder;
import com.edge.business.productionPlan.service.inter.ProductionPlanOrderService;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.entity.ERP_Sales_Contract_Order;
import com.edge.business.sale.entity.ERP_Sales_Contract_QueryVo;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.edge.currency.enclosure.entity.Enclosure;
import com.edge.currency.enclosure.service.inter.EnclosureService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.utils.FtpUtil;
import com.google.gson.Gson;

/**
 * 销售合同控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "sales")
public class ERP_Sales_ContractController {

	public static final String ftpHost = "192.168.0.106";// ftp文档服务器Ip

	public static final String ftpUserName = "administrator";// ftp文档服务器登录用户名

	public static final String ftpPassword = "123";// ftp文档服务器登录密码

	public static final int ftpPort = 21;// ftp文档服务器登录端口

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private CustomerService customerService;

	@Resource
	private ERP_Sales_Contract_OrderService orderService;

	@Resource
	private CompanyService companyService;

	@Resource
	private EnclosureService enclosureService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private ApprovalService approvalService;

	@Resource
	private SYS_WorkFlow_CphdService cphdService;

	@Resource
	private DeliveryService deliveryService;

	@Resource
	private DeliveryOrderService deliveryOrderService;

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private ProductionPlanOrderService productionPlanOrderService;

	@Resource
	private ERP_DepartmentService departmentService;

	@Resource
	private ProductService productService;

	// 跳转至销售合同列表页面
	@RequestMapping(value = "/initSalesList.do")
	public String initSalesList() {
		return "business/sale/saleList";
	}

	// 分页查询销售合同列表
	@RequestMapping(value = "/salesList.do")
	@ResponseBody
	public String salesList(Integer page, Integer limit, String htmc, String htbh, Integer spzt, Integer gf, Integer xf,
			String beginTime, String endTime) {
		// new出ERP_Sales_Contract_QueryVo查询对象
		ERP_Sales_Contract_QueryVo vo = new ERP_Sales_Contract_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setHtmc(htmc);
		vo.setHtbh(htbh);
		vo.setSpzt(spzt);
		vo.setGf(gf);
		vo.setXf(xf);
		vo.setBeginTime(beginTime);
		vo.setEndTime(endTime);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", contractService.salesContractCount(vo));
		List<ERP_Sales_Contract> list = contractService.salesContractList(vo);
		for (ERP_Sales_Contract l : list) {
			ERP_Customer customer = customerService.queryCustomerById(l.getCustomer());
			l.setCustomerName(customer.getUnit_Name());
			ERP_Our_Unit our_Unit = companyService.queryUnitById(l.getSupplier());
			l.setSupplierName(our_Unit.getUnit_Name());
			ERP_DM_Approval approval = approvalService.queryApprovalById(l.getApprovalDm());
			l.setApprovalName(approval.getApprovalmc());
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至销售合同新增页面
	@RequestMapping(value = "/initSaveSales.do")
	public String initSaveSales() {
		return "business/sale/saveSale";
	}

	// 加载需求方
	@RequestMapping(value = "/customerList.do")
	@ResponseBody
	public String customerList() {
		JSONArray jsonArray = new JSONArray();
		List<ERP_Customer> list = contractService.customerList();
		for (ERP_Customer l : list) {
			jsonArray.add(l);
		}
		return jsonArray.toString();
	}

	// 加载需求方属性
	@RequestMapping(value = "/customer.do")
	@ResponseBody
	public String customer(Integer customer_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_Customer customer = customerService.queryCustomerById(customer_Id);
		jsonObject.put("customer", customer);
		return jsonObject.toString();
	}

	// 加载供货方属性
	@RequestMapping(value = "/company.do")
	@ResponseBody
	public String company() {
		JSONObject jsonObject = new JSONObject();
		List<ERP_Our_Unit> unitList = contractService.unitList();
		for (ERP_Our_Unit l : unitList) {
			jsonObject.put("unit", l);
		}
		return jsonObject.toString();
	}

	// 合同编号生成
	@RequestMapping(value = "/htbh.do")
	@ResponseBody
	public String htbh() {
		JSONObject jsonObject = new JSONObject();
		// 编号规则 XZ-年度-月份-日期-3位流水 例如 XZ-2020-01-17-001
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
		String bh = "XZ" + "-" + time;
		// 根据该编号去模糊检索查询出最大流水号的编号
		String maxHtbh = contractService.htbh(bh);
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

	// 新增合同
	@RequestMapping(value = "/saveSales.do")
	@ResponseBody
	public String saveSales(@RequestBody ERP_Sales_Contract contract, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		// 设置待办任务描述
		contract.setTask_Describe("【任务名称：销售订单】");
		contract.setApprovalDm(2);// 1.完成 2.审批中
		// 设置销售订单状态
		contract.setStatus("已接单");
		// 新增销售合同
		contractService.saveSalesContract(contract);
		// 新增销售合同附件
		this.addXshtFj(contract.getFjsx(), request);
		// 启动流程实例
		Map<String, Object> map = new HashMap<String, Object>();
		String key = contract.getClass().getSimpleName();
		String objId = key + "." + String.valueOf(contractService.maxSalesContract());
		map.put("inputUser", user.getUserId());
		// 使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OperationFlow", objId, map);
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId())
				.orderByProcessInstanceId().desc().singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTask(task, user, objId);
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增流程审批
	private void savelcsp(Task task, ERP_User user) {
		// new出SYS_WorkFlow_PingShenYJ对象
		SYS_WorkFlow_PingShenYJ r = new SYS_WorkFlow_PingShenYJ();
		r.setPROC_INST_ID_(task.getProcessInstanceId());
		r.setTASK_ID_(task.getId());
		r.setTIME_(new Date());
		r.setUSER_ID_(user.getUserId());
		r.setTASK_NAME_(task.getName());
		r.setUserName(user.getUserName());
		r.setMESSAGE_RESULT_(null);
		r.setMESSAGE_INFOR_(null);
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
			// 根据销售合同主键获得销售合同对象
			ERP_Sales_Contract xsht = contractService.queryContractById(contractService.maxSalesContract());
			String key = xsht.getClass().getSimpleName();
			// 拼接业务数据主键
			String objId = key + "." + String.valueOf(xsht.getSales_Contract_Id());
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

	// 新增合同货物清单
	@RequestMapping(value = "/saveOrder.do")
	@ResponseBody
	public String saveOrder(@RequestBody ERP_Sales_Contract_Order[] contactOrder) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Sales_Contract_Order c : contactOrder) {
			// new 出货物清单对象对象
			ERP_Sales_Contract_Order order = new ERP_Sales_Contract_Order();
			// 根据成品货物规格型号获得物料Id
			String materielId = materielIdService.product_MaterielId(c.getSpecification_Type());
			if (materielId != null && materielId != "") {
				order.setMaterielId(materielId);
			}
			order.setMaterial_Name(c.getMaterial_Name());
			order.setSpecification_Type(c.getSpecification_Type());
			order.setSl(c.getSl());
			order.setUnit(c.getUnit());
			order.setPrice(c.getPrice());
			order.setTotal_price(c.getTotal_price());
			order.setBz(c.getBz());
			order.setSales_Contract(contractService.maxSalesContract());
			orderService.saveContract_Order(order);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();

	}

	// 上传附件操作
	@RequestMapping(value = "/upload.do")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		// new出JSONObject对象
		JSONObject jsonObject = new JSONObject();
		if (!file.isEmpty()) {
			// new出Map集合用于存放上传文件名、上传文件在ftp中的名称、上传文间地址
			Map<String, Object> map = new HashMap<String, Object>();
			// 文件名
			String fileName = file.getOriginalFilename();
			String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			// ftp不支持中文名称故生成非中文名存储在ftp中
			// String localFileName = System.currentTimeMillis() + fileSuffix;
			Random r = new Random();
			String localFileName = String.valueOf(r.nextInt(999999)) + fileSuffix;
			Map<String, Object> maps = this.MultipartFileToFile(file, request);
			String address = maps.get("path") + "\\" + maps.get("fileName");
			// 将File转化为InputStream
			InputStream inp = new FileInputStream(address);
			// 连接ftp文档服务器
			Date date = new Date();
			String path = "/" + new SimpleDateFormat("yyyy/MM/dd").format(date);
			// 上传文件
			boolean flag = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, path, localFileName, inp);
			map.put("fileName", fileName);
			map.put("localFileName", localFileName);
			map.put("path", path);
			if (flag) {
				jsonObject.put("code", 0);
				jsonObject.put("msg", "");
				jsonObject.put("data", map);
			} else {
				jsonObject.put("code", 1);
				jsonObject.put("msg", "文件上传失败");
				jsonObject.put("data", map);
			}
		}
		return jsonObject.toString();

	}

	// 将 MultipartFile文件类型转换为File类型
	private Map<String, Object> MultipartFileToFile(MultipartFile file, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
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
		String realPath = request.getSession().getServletContext()
				.getRealPath("/fj/" + year + "/" + month + "/" + day + "");
		byte[] data = null;
		try {
			data = file.getBytes();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		File out = new File(realPath, file.getOriginalFilename());
		// 文件的生成
		try {
			FileUtils.writeByteArrayToFile(out, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("path", realPath);
		map.put("fileName", file.getOriginalFilename());
		return map;
	}

	// 查看操作
	@RequestMapping(value = "/salesShow.do")
	public String salesShow(@RequestParam Integer sales_Contract_Id, Model model) {
		// 格式化计划合同签订日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		// 根据id获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(sales_Contract_Id);
		// 获得供方对象
		ERP_Our_Unit our_Unit = companyService.queryUnitById(contract.getSupplier());
		// 获得需求方对象
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
		// 获得销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(contract.getSales_Contract_Id());
		String businessKey = contract.getClass().getSimpleName() + "." + String.valueOf(sales_Contract_Id);
		// 根据businessKey获取历史流程实例对象
		HistoricProcessInstance hisp = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();
		// 若流程实例不为空,则获取流程实例主键
		String processInstanceId = null;
		List<SYS_WorkFlow_PingShenYJ> psyjList = null;
		List<SYS_WorkFlow_Cphd> cphds = null;// 成品核对流程变量数据
		List<ERP_DeliveryOrder> deliveryOrder = null;// 送货单货物项
		ERP_ProductionPlan productionPlan = null;// 生产计划对象
		List<ProductionPlanOrder> productionPlanOrders = null;// 生产计划货物项
		if (hisp != null) {
			processInstanceId = hisp.getId();
			psyjList = pingShenYjService.psyjList(processInstanceId);
			for (SYS_WorkFlow_PingShenYJ p : psyjList) {
				p.setUserName(userService.queryUserById(p.getUSER_ID_()).getUserName());
				p.setTime(sdf1.format(p.getTIME_()));
				// 判断任务节点为成品核对则取出该数据的主键
				if ("成品核对".equals(p.getTASK_NAME_())) {
					// 得到成品核对的流程数据
					cphds = cphdService.cphds(p.getID_());
				} else if ("生产计划".equals(p.getTASK_NAME_())) {
					// 得到生产计划的流程数据
					productionPlan = productionPlanService.queryPlanByXsht(sales_Contract_Id);
					if (productionPlan != null) {
						// 设置生产部门名称
						ERP_Department department = departmentService.queryDepById(productionPlan.getPlan_Department());
						productionPlan.setPlan_DepartmentName(department.getDep_Name());
						productionPlan.setXddrq(sdf.format(productionPlan.getPlan_Date()));
						productionPlan.setJhkgrq(sdf.format(productionPlan.getPlan_BeginDate()));
						productionPlan.setJhwgrq(sdf.format(productionPlan.getPlan_EndDate()));
					}
					// 获得生产计划货物项数据
					productionPlanOrders = productionPlanOrderService
							.queryPlanOrderByPlanId(productionPlan.getRow_Id());
					// 遍历该集合
					for (ProductionPlanOrder polder : productionPlanOrders) {
						// 根据成品获得成品对象
						ERP_Products product = productService.queryProductById(polder.getProduct());
						polder.setErp_product(product);
					}
				}
			}
		}
		// 根据销售合同对象获得送货单对象
		ERP_Delivery delivery = deliveryService.queryDeliveryByXsht(contract.getSales_Contract_Id());
		// 根据送货单对象加载送货单货物项
		if (delivery != null) {
			deliveryOrder = deliveryOrderService.orderList(delivery.getDelivery_Id());
		}
		model.addAttribute("contract", contract);
		model.addAttribute("our_Unit", our_Unit);
		model.addAttribute("customer", customer);
		model.addAttribute("orderList", orderList);
		model.addAttribute("qdrq", sdf.format(contract.getQd_Date()));
		model.addAttribute("OBJDM", contract.getClass().getSimpleName() + "." + String.valueOf(sales_Contract_Id));
		model.addAttribute("reviewOpinions", psyjList);
		model.addAttribute("cphds", cphds);
		model.addAttribute("deliveryOrder", deliveryOrder);
		model.addAttribute("processInstanceId", processInstanceId);
		model.addAttribute("productionPlan", productionPlan);
		model.addAttribute("productionPlanOrders", productionPlanOrders);
		return "business/sale/saleShow";
	}

	// 下载附件
	@RequestMapping(value = "/downloadFtpFile.do")
	public void downloadFile(@RequestParam String ftpPath, String rEALWJM, HttpServletResponse response,
			HttpServletRequest request) {
		String filePath = request.getSession().getServletContext()// D:\guildFile\adviceNote_1493028164967_Jellyfish.jpg
				.getRealPath("/fj/" + ftpPath);
		String file_name = null;
		try {
			file_name = new String(rEALWJM.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("application/x-download");
		// 设置response对象的头参数,attachment就是附件,filename就是文件名
		response.setHeader("Content-disposition", "attachment;filename=" + file_name);
		// 通过文件路径获得File对象(假如此路径中有一个download.pdf文件)
		String finalPath = filePath + "/" + rEALWJM;
		File file = new File(finalPath);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} // 文件输入流
			// --创建输出流,绑定到response对象
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 流复制
		byte[] b = new byte[1024];
		int len = -1;
		// 文件内容拷贝
		try {
			while ((len = inputStream.read(b, 0, 1024)) != -1) {
				outputStream.write(b, 0, b.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ajax加载所有的审批状态
	@RequestMapping(value = "/allApproval.do")
	@ResponseBody
	public String allApproval() {
		JSONArray allApproval = approvalService.allApproval();
		return allApproval.toString();
	}

	// ajax加载所有的需求方
	@RequestMapping(value = "/allCustomer.do")
	@ResponseBody
	public String allCustomer() {
		JSONArray allCustomer = customerService.allCustomer();
		return allCustomer.toString();
	}

	// ajax加载所有的供方
	@RequestMapping(value = "/allCompany.do")
	@ResponseBody
	public String allCompany() {
		JSONArray allCompany = companyService.allUnit();
		return allCompany.toString();
	}

	// ajax 加载所有的销售合同
	@RequestMapping(value = "/allxsht.do")
	@ResponseBody
	public String allxsht() {
		JSONArray allXSHT = contractService.allXSHT();
		return allXSHT.toString();
	}

	// 跳转至销售订单编辑页面
	@RequestMapping(value = "/initEditSales.do")
	public String initEditSales(@RequestParam String objId, String taskId, Model model) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 根据id得到销售合同货物清单对象
		List<ERP_Sales_Contract_Order> orderList = orderService.orderList(Integer.parseInt(id));
		// 获得供方对象
		ERP_Our_Unit our_Unit = companyService.queryUnitById(contract.getSupplier());
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("contract", contract);
		model.addAttribute("our_Unit", our_Unit);
		model.addAttribute("orderList", orderList);
		model.addAttribute("taskId", taskId);
		model.addAttribute("rq", sdf.format(contract.getQd_Date()));
		return "business/sale/editSale";
	}

	// ajax删除销售订单货物项
	@RequestMapping(value = "/deleteOrderById.do")
	@ResponseBody
	public String deleteOrderById(Integer contract_Id) {
		JSONObject jsonObject = new JSONObject();
		orderService.deleteOrderById(contract_Id);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 编辑合同
	@RequestMapping(value = "/editSales.do")
	@ResponseBody
	public String editSales(@RequestBody ERP_Sales_Contract contract, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		String key = contract.getClass().getSimpleName();
		String objId = key + "." + String.valueOf(contract.getSales_Contract_Id());
		// 编辑销售合同
		contractService.editSalesContract(contract);
		// 新增销售合同附件
		this.addXshtFj(contract.getFjsx(), request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inputUser", user.getUserId());
		// 获取流程中当前需要办理的任务
		Task task = taskService.createTaskQuery().taskId(contract.getTaskId()).singleResult();
		this.savelcsp(task, user);
		this.saveAlreadyTask(task, user, objId);
		// 启动流程
		taskService.complete(task.getId(), map);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增/编辑合同货物清单
	@RequestMapping(value = "/saveOrEditOrder.do")
	@ResponseBody
	public String saveOrEditOrder(@RequestBody ERP_Sales_Contract_Order[] contactOrder) {
		JSONObject jsonObject = new JSONObject();
		for (ERP_Sales_Contract_Order c : contactOrder) {
			// 若id不为空则为编辑否则为新增
			if (c.getSales_Contract_Id() != null) {
				orderService.editContract_Order(c);
			} else {
				// new 出货物清单对象对象
				ERP_Sales_Contract_Order order = new ERP_Sales_Contract_Order();
				order.setMaterial_Name(c.getMaterial_Name());
				order.setSpecification_Type(c.getSpecification_Type());
				order.setSl(c.getSl());
				order.setUnit(c.getUnit());
				order.setPrice(c.getPrice());
				order.setTotal_price(c.getTotal_price());
				order.setBz(c.getBz());
				order.setSales_Contract(contractService.maxSalesContract());
				orderService.saveContract_Order(order);
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();

	}

}
