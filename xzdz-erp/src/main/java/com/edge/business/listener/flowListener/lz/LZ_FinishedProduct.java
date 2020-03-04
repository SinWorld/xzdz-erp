package com.edge.business.listener.flowListener.lz;

import java.util.Calendar;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.entity.ProductionPlanOrder;
import com.edge.business.productionPlan.service.inter.ProductionPlanOrderService;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 流转至成品检验用于设置该节点的formKey,且生成生产计划中的成品项
 * 
 * @author NingCG
 *
 */
public class LZ_FinishedProduct implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "finishedProduct/initFinishedProduct.do");
		// 获取businessKey
		String businessKey = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = businessKey.substring(businessKey.indexOf(".") + 1);
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取ERP_Sales_ContractService
		ERP_Sales_ContractService contractService = (ERP_Sales_ContractService) ac
				.getBean("ERP_Sales_ContractServiceImpl");
		// 获取ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 获取ProductionPlanOrderService
		ProductionPlanOrderService productionPlanOrderService = (ProductionPlanOrderService) ac
				.getBean("productionPlanOrderServiceImpl");
		// 获取ProductService
		ProductService productService = (ProductService) ac.getBean("productServiceImpl");
		// 获取KC_StatusService
		KC_StatusService KC_StatusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		// 获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 获取生产计划项
		List<ProductionPlanOrder> planOrderList = productionPlanOrderService
				.queryPlanOrderByPlanId(productionPlan.getRow_Id());
		// 生成成品（若已经存在则不新增）
		List<ERP_Products> list = productService.queryProductsByXsht(contract.getSales_Contract_Id());
		// 根据订到号查询库存状态集合
		List<ERP_Stock_Status> stockStatusList = KC_StatusService.queryStastusByDdh(contract.getContract_Code());
		// 遍历生产计划项
		for (ProductionPlanOrder p : planOrderList) {
			if (list.size() != planOrderList.size()) {
				ERP_Products products = new ERP_Products();
				products.setProduct_Name(p.getProductName());
				products.setSpecification_Type(p.getGgxh());
				products.setUnit(p.getUnit());
				products.setNumbers(p.getScsl());
				products.setSales_Contract_Id(contract.getSales_Contract_Id());
				products.setMaterielid(p.getMaterielId());
				products.setProduct_Code(this.cpbh());
				products.setMaterialQuality("待检验");
				products.setIs_rk(false);
				products.setIs_ck(false);
				products.setIs_allrk(false);
				products.setIs_allck(false);
				productService.saveProduct(products);
			}
			if (stockStatusList.size() != planOrderList.size()) {
				ERP_Stock_Status stockStatus = new ERP_Stock_Status();
				stockStatus.setProduct_Id(productService.queryMaxProductId());
				stockStatus.setStock_Type(false);
				stockStatus.setStatus("待入库");
				stockStatus.setOddNumbers(contract.getContract_Code());
				KC_StatusService.saveStockStatus(stockStatus);
			}

		}

	}

	// 生成产品编号
	private String cpbh() {
		// 1.设置项目编号 编号规则为’P’+年、月、日、时、分、秒+六位随机数 如：P20190604172610123456
		// 获取当前系统时间 并获取年月日时分秒
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
		String Hourse = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		if (now.get(Calendar.HOUR_OF_DAY) <= 9) {
			Hourse = 0 + Hourse;
		}
		String minute = String.valueOf(now.get(Calendar.MINUTE));
		if (now.get(Calendar.MINUTE) <= 9) {
			minute = 0 + minute;
		}
		String second = String.valueOf(now.get(Calendar.SECOND));
		if (now.get(Calendar.SECOND) <= 9) {
			second = 0 + second;
		}
		// 产生六位随机数
		int a = (int) ((Math.random() * 9 + 1) * 100000);
		String x = String.valueOf(a);
		String time = year + month + day + Hourse + minute + second;
		String bh = "CP" + "-" + time + x;
		return bh;
	}

}
