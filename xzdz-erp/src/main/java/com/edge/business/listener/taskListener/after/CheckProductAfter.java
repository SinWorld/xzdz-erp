package com.edge.business.listener.taskListener.after;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 成品核对任务操作之后用于更新对应的库存状态的单位为该销售订单号
 * 
 * @author NingCG
 *
 */
public class CheckProductAfter implements TaskListener {

	public void notify(DelegateTask delegateTask) {
		String cphds = (String) delegateTask.getVariable("cphd");
		// 获取businessKey
		String businessKey = delegateTask.getExecution().getBusinessKey();
		// 得到业务数据主键值
		String id = businessKey.substring(businessKey.indexOf(".") + 1);
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取 KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取ERP_Sales_ContractService
		ERP_Sales_ContractService contractService = (ERP_Sales_ContractService) ac
				.getBean("ERP_Sales_ContractServiceImpl");
		// 获取销售订单对象
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		if (cphds != "" && cphds != null) {
			String[] variables = cphds.split(",");
			// 遍历该集合
			for (String v : variables) {
				Integer productId = null;
				String[] datas = v.split(":");
				for (int i = 0; i < datas.length; i++) {
					productId = Integer.parseInt(datas[1].trim().trim());
				}
				// 根据成品Id获得成品库存状态对象
				ERP_Stock_Status status = statusService.queryStastusByCpId(productId);
				// 设置库存订单号为对应的销售订单号
				status.setOddNumbers(contract.getContract_Code());
				statusService.editStockStatus(status);
			}
		}
	}

}
