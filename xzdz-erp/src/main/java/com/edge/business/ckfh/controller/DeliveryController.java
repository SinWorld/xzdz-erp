package com.edge.business.ckfh.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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

import com.alibaba.fastjson.JSONObject;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.service.inter.ERP_UserService;
import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.entity.ERP_DeliveryOrder;
import com.edge.business.ckfh.entity.ERP_Delivery_QueryVo;
import com.edge.business.ckfh.service.inter.DeliveryOrderService;
import com.edge.business.ckfh.service.inter.DeliveryService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;
import com.google.gson.Gson;

/**
 * 出库发货控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "delivery")
public class DeliveryController {

	@Resource
	private DeliveryService deliveryService;

	@Resource
	private DeliveryOrderService deliveryOrderService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private TaskService taskService;

	@Resource
	private Pro_CK_StockService stockService;

	@Resource
	private ProductService productService;

	@Resource
	private CustomerService customerService;

	@Resource
	private Pro_StockRecordService stockRecordService;

	@Resource
	private Pro_CK_StockService ckStockService;

	@Resource
	private RuntimeService runTimeService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private ERP_UserService userService;

	@Resource
	private KC_StockService kc_stockService;

	// 跳转至送货单列表页面
	@RequestMapping(value = "/initDeliveryList.do")
	public String initDeliveryList() {
		return "business/delivery/deliveryList";
	}

	// 分页查询送货单列表
	@RequestMapping(value = "/deliveryList.do")
	@ResponseBody
	public String deliveryList(Integer page, Integer limit, String ddbh, Integer shdw, Integer jbr, Integer xsht,
			String beginTime, String endTime) {
		// new出ERP_Delivery_QueryVo查询对象
		ERP_Delivery_QueryVo vo = new ERP_Delivery_QueryVo();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 每页数
		vo.setPage((page - 1) * limit + 1);
		vo.setRows(page * limit);
		vo.setDdbh(ddbh);
		vo.setShdw(shdw);
		vo.setXsht(xsht);
		vo.setJbr(jbr);
		vo.setBeginTime(beginTime);
		vo.setEndTime(endTime);
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", deliveryService.deliveryCount(vo));
		List<ERP_Delivery> list = deliveryService.deliveryList(vo);
		for (ERP_Delivery l : list) {
			ERP_Sales_Contract contract = contractService.queryContractById(l.getSales_Contract_Id());
			l.setContractName(contract.getSales_Contract_Name());
			ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
			l.setShdwName(customer.getUnit_Name());
			ERP_User user = userService.queryUserById(l.getSongHuojbr());
			l.setShjbr(user.getUserName());
		}
		map.put("data", list);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至出库发货页面
	@RequestMapping(value = "/initDelivery.do")
	public String initDelivery(@RequestParam String objId, String taskId, Model model, HttpServletRequest request) {
		// 得到销售合同Id
		String id = objId.substring(objId.indexOf(".") + 1);
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id));
		// 加载收货单位
		Integer customerId = contract.getCustomer();
		ERP_Customer customer = customerService.queryCustomerById(customerId);
		// 获得送货经办人
		ERP_User user = (ERP_User) request.getSession().getAttribute("user");
		// 获取流程变量中的需要出库货物
		String variable = (String) taskService.getVariable(taskId, "cphd");
		String[] variables = variable.split(",");
		List<ERP_DeliveryOrder> orderList = new ArrayList<ERP_DeliveryOrder>();
		// 遍历该集合
		for (String v : variables) {
			Integer stockId = null;
			Integer productId = null;
			Integer cksl = null;
			String[] datas = v.split(":");
			for (int i = 0; i < datas.length; i++) {
				stockId = Integer.parseInt(datas[0].trim().trim());
				productId = Integer.parseInt(datas[1].trim().trim());
				cksl = Integer.parseInt(datas[2].trim().trim());
				break;
			}
			// 获得成品对象
			ERP_Products products = productService.queryProductById(productId);
			// new出送货项对象
			ERP_DeliveryOrder order = new ERP_DeliveryOrder();
			// 设置属性
			order.setMaterial_Name(products.getProduct_Name());
			order.setSpecification_Type(products.getSpecification_Type());
			order.setCompany(products.getUnit());
			order.setDelivery_Number(cksl);
			order.setStock(stockId);
			order.setProduct(productId);
			if (cksl != 0) {
				orderList.add(order);
			}
		}
		model.addAttribute("orderList", orderList);
		model.addAttribute("customerName", customer.getUnit_Name());
		model.addAttribute("ddbh", this.deliveryCode());
		model.addAttribute("shjbrName", user.getUserName());
		model.addAttribute("shjbr", user.getUserId());
		model.addAttribute("taskId", taskId);
		model.addAttribute("contractId", contract.getSales_Contract_Id());
		model.addAttribute("customerId", customer.getCustomer_Id());
		return "business/delivery/saveDelivery";

	}

	// 送货单编码生成
	private String deliveryCode() {
		// 编号规则 FH-年度-月份-日期-3位流水 例如 FH-20-02-11-001
		Calendar now = Calendar.getInstance();
		String year = new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance().getTime());
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String time = year + "-" + month + "-" + day;
		String bh = "FH" + "-" + time;
		// 根据该编号去模糊检索查询出最大流水号的编号
		String maxHtbh = deliveryService.htbh(bh);
		// 如果最大合同编号为空则流水号为001否则需要切割字符串进行新增
		String newHtbh = null;
		if (maxHtbh == null) {
			newHtbh = bh + "-001";
		} else {
			String maxLiuShuiH = maxHtbh.substring(bh.length() + 1);
			int nextLiuShuiH = Integer.parseInt(maxLiuShuiH) + 1;
			newHtbh = bh + "-" + String.format("%03d", nextLiuShuiH);
		}
		return newHtbh;
	}

	// 新增送货订单
	@RequestMapping(value = "/saveDelivery.do")
	@ResponseBody
	public String saveDelivery(@RequestBody ERP_Delivery delivery) {
		JSONObject jsonObject = new JSONObject();
		deliveryService.saveDelivery(delivery);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 新增送货订单项/成品出库
	@RequestMapping(value = "/saveDeliveryOrder.do")
	@ResponseBody
	public String saveDeliveryOrder(@RequestBody ERP_DeliveryOrder[] deliveryOrder, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		ERP_User user = (ERP_User) request.getSession().getAttribute("user");
		// 遍历该集合
		for (ERP_DeliveryOrder d : deliveryOrder) {
			// 设置送货单外键
			d.setDelivery_Id(deliveryService.queryDeliveryId());
			deliveryOrderService.saveDeliveryOrder(d);
			// 新增出库记录
			if (d.getDelivery_Number() != 0) {
				ERP_stocks_Record record = new ERP_stocks_Record();
				record.setProduct(d.getProduct());
				record.setStock(d.getStock());
				record.setSl(d.getDelivery_Number());
				record.setSj(new Date());
				record.setRecord_Type(true);// false为入库 true 为出库
				record.setJbr(user.getUserId());
				record.setRemarks(d.getRemarks());
				stockRecordService.saveStockRecord(record);
				/**
				 * 库存出库
				 */
				// 更新成品的入库标志位
				ERP_Products product = productService.queryProductById(d.getProduct());
				ERP_Stock kc = kc_stockService.queryStockByCPId(product.getProduct_Id(),d.getStock());
				if (kc != null) {
					kc.setSl(kc.getSl() - d.getDelivery_Number());
					kc_stockService.editStock(kc);
				}
				product.setIs_ck(true);
				productService.editProduct(product);
				// 该成品已全部出库
				if (ckStockService.totalrkKc(product.getProduct_Id()) == 0) {
					// 更新该成品的入库标志位
					product.setIs_allck(true);
					productService.editProduct(product);
				}
			}
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 推动流程
	@RequestMapping(value = "/tdlc.do")
	@ResponseBody
	public String tdlc(String taskId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		this.savelcsp(task, user, null, null);
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runTimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId);
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
		alreadyTask.setCOMPLETION_STATUS_("完成");
		// 设置任务发起人
		Integer createUserId = (Integer) taskService.getVariable(task.getId(), "inputUser");
		alreadyTask.setCREATE_USER_(String.valueOf(createUserId));
		alreadyTaskService.saveAlreadyTask(alreadyTask);
	}

	// 查看操作
	@RequestMapping(value = "/deliveryShow.do")
	public String deliveryShow(@RequestParam Integer delivery_Id, Model model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 根据Id获得送货单对象
		ERP_Delivery delivery = deliveryService.queryDeliveryById(delivery_Id);
		ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
		ERP_Customer customer = customerService.queryCustomerById(contract.getCustomer());
		delivery.setShdwName(customer.getUnit_Name());
		delivery.setQdrq(sdf.format(delivery.getDelivery_Date()));
		ERP_User user = userService.queryUserById(delivery.getSongHuojbr());
		delivery.setShjbr(user.getUserName());
		// 根据Id获得送货项集合
		List<ERP_DeliveryOrder> orderList = deliveryOrderService.orderList(delivery_Id);
		model.addAttribute("delivery", delivery);
		model.addAttribute("orderList", orderList);
		return "business/delivery/deliveryShow";
	}

}
