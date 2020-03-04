package com.edge.business.listener.flowListener.lz;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;

/**
 * 流转至生产领用节点，设置生产领用的formKey，设置生产计划状态为已出库
 * 
 * @author NingCG
 *
 */
public class LZ_ProductionCollar implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productionCollar/initProductionCollar.do");
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
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		// 根据销售合同获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		productionPlan.setStatus("已出库");
		productionPlanService.editProductionPlan(productionPlan);
	}

}
