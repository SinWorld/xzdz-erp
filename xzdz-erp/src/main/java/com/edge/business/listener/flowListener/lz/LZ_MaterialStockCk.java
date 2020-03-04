package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;

/**
 * 流转至材料出库，设置材料出库的formKey,设置销售订单状态为已入库，设置生产计划状态为已入库，设置采购订单状态为已入库
 * 
 * @author NingCG
 *
 */
public class LZ_MaterialStockCk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materialStockCk/initMaterialStockCk.do");
		// 获取businessKey
		String businessKey = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = businessKey.substring(businessKey.indexOf(".") + 1);
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取ERP_Sales_ContractService
		ERP_Sales_ContractService contractService = (ERP_Sales_ContractService) ac
				.getBean("ERP_Sales_ContractServiceImpl");
		// 获取销售订单对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		contract.setStatus("已入库");
		contractService.editSalesContract(contract);
		// 获取ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 根据销售合同获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		productionPlan.setStatus("已入库");
		productionPlanService.editProductionPlan(productionPlan);
		// 获取PurchaseOrderService
		PurchaseOrderService purchaseOrderService = (PurchaseOrderService) ac.getBean("purchaseOrderServiceImpl");
		// 获取采购合同
		ERP_Purchase_Order order = purchaseOrderService.queryPurchaseOrderByXsht(contract.getSales_Contract_Id());
		order.setStatus("已入库");
		purchaseOrderService.editPurchaseOrder(order);
	}

}
