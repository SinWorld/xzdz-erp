package com.edge.business.listener.eventListener;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.ckfh.service.inter.DeliveryOrderService;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 流程结束监听事件用于更新业务数据审批状态,且设置剩余的材料和成品为闲置状态,设置销售订单状态为已发货
 * 
 * @author NingCG
 *
 */
public class BusinessEventHandler implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// 取得businessKey
		String key = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = key.substring(key.indexOf(".") + 1);
		// 2.从spring容器中获取UserServiceImpl
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取ERP_Sales_ContractService 接口
		ERP_Sales_ContractService contractService = (ERP_Sales_ContractService) ac
				.getBean("ERP_Sales_ContractServiceImpl");
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		contract.setStatus("已发货");
		contractService.editSalesContract(contract);
		// 获取ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 根据销售合同获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 获取KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取 DeliveryOrderService
		DeliveryOrderService deliveryOrderService = (DeliveryOrderService) ac.getBean("deliveryOrderServiceImpl");
		// 根据生产计划订单号获得库存状态集合
		List<ERP_Stock_Status> status = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		// 遍历该集合
		for (ERP_Stock_Status s : status) {
			// 获得材料Id,获得该材料所有的库存量
			Integer kcNumber = deliveryOrderService.totalKcNumberCl(s.getProduct_Id());
			if (kcNumber != 0) {
				s.setStatus("闲置");
				s.setOddNumbers(null);
				statusService.editStockStatus(s);
			} else {
				s.setStatus("已出库");
				s.setOddNumbers(productionPlan.getPlan_Code());
				statusService.editStockStatus(s);
			}
		}
		// 根据销售订单号获得库存状态集合
		List<ERP_Stock_Status> stockStatus = statusService.queryStastusByDdh(contract.getContract_Code());
		for (ERP_Stock_Status s : stockStatus) {
			// 获得材料Id,获得该材料所有的库存量
			Integer kcNumber = deliveryOrderService.totalKcNumber(s.getProduct_Id());
			if (kcNumber != 0) {
				s.setStatus("闲置");
				s.setOddNumbers(null);
				statusService.editStockStatus(s);
			} else {
				s.setStatus("已出库");
				s.setOddNumbers(contract.getContract_Code());
				statusService.editStockStatus(s);
			}
		}

	}

}
