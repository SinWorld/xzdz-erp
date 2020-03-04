package com.edge.business.productStockCk.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.user.entity.ERP_User;
import com.edge.business.productStockCk.service.inter.ProductStockCkService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.currency.alreadyTask.entity.AlreadyTask;
import com.edge.currency.alreadyTask.service.inter.AlreadyTaskService;
import com.edge.currency.reviewOpinion.entity.SYS_WorkFlow_PingShenYJ;
import com.edge.currency.reviewOpinion.service.inter.PingShenYJService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;
import com.google.gson.Gson;

/**
 * 成品出库控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "productStockCk")
public class ProductStockCkController {

	@Resource
	private ProductStockCkService productStockCkService;

	@Resource
	private ERP_Sales_ContractService contractService;

	@Resource
	private ProductService productService;

	@Resource
	private Pro_StockService stockService;

	@Resource
	private Pro_CK_StockService ckStockService;

	@Resource
	private TaskService taskService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private PingShenYJService pingShenYjService;

	@Resource
	private AlreadyTaskService alreadyTaskService;

	// 跳转至成品出库页面
	@RequestMapping(value = "/initProductStockCk.do")
	public String initMaterialStockCk(@RequestParam String objId, String taskId, Model model) {
		String xsddId = objId.substring(objId.indexOf(".") + 1);
		model.addAttribute("xsddId", xsddId);
		model.addAttribute("taskId", taskId);
		return "business/productStockCk/productStockCkList";
	}

	@RequestMapping(value = "/initProductStockCkList.do")
	@ResponseBody
	public String initProductStockCkList(Integer xsddId) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 根据该id 获得销售合同对象
		ERP_Sales_Contract contract = contractService.queryContractById(xsddId);
		// 根据销售合同获取成品集合
		List<ERP_Products> productList = productService.queryProductsByXsht(contract.getSales_Contract_Id());
		for (ERP_Products p : productList) {
			p.setSales_Contract_Name(contract.getSales_Contract_Name());
		}
		Gson gson = new Gson();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", productList.size());
		map.put("data", productList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至材料出库界面
	@RequestMapping(value = "/initProductCk.do")
	public String initProductCk(@RequestParam Integer product_Id, Model model) {
		ERP_Products product = productService.queryProductById(product_Id);
		model.addAttribute("product", product);
		return "business/productStockCk/ckProductStock";
	}

	// 查询所有的库位
	@RequestMapping(value = "/queryAllStock.do")
	@ResponseBody
	public String queryAllStock(Integer product_Id) {
		// new 出JSONArray数组
		JSONArray jsonArray = new JSONArray();
		List<ERP_Product_Stock> stocks = productStockCkService.qeuryStockByProductId(product_Id);
		// 遍历该集合
		for (ERP_Product_Stock s : stocks) {
			// new 出JSONObject对象
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", s.getStock_Id());
			jsonObject.put("title", s.getStock());
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	// 根据选择成品查询成品对象
	@RequestMapping(value = "/queryChoseProduct.do")
	@ResponseBody
	public String queryChoseProduct(String strs, Integer product_Id) {
		JSONArray jsonArray = new JSONArray();
		// 将str进行字符截取
		String str = strs.substring(1, strs.length());
		// 将其转换为数组
		String[] productdm = str.split(",");
		for (String p : productdm) {
			// 根据Id获得库位对象
			ERP_Product_Stock stock = stockService.queryPro_StockById(Integer.parseInt(p.trim()));
			Integer totalKc = ckStockService.totalKc(product_Id, stock.getStock_Id());
			stock.setKcl(totalKc);
			stock.setCpId(product_Id);
			jsonArray.add(stock);
		}
		return jsonArray.toString();
	}

	// 提交表单推动流程
	@RequestMapping(value = "/submitForm.do")
	@ResponseBody
	public String submitForm(String taskId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		HttpSession session = request.getSession();
		ERP_User user = (ERP_User) session.getAttribute("user");
		Authentication.setAuthenticatedUserId(String.valueOf(user.getUserId()));
		this.savelcsp(task, user, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outcome", "成品发货");
		// 4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		this.saveAlreadyTask(task, user, runtimeService.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
		taskService.complete(taskId, map);
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

}
