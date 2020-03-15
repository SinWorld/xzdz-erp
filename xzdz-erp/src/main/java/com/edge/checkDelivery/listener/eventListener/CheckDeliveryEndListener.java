package com.edge.checkDelivery.listener.eventListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.service.inter.DeliveryService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.checkDelivery.entity.CheckDelivery;
import com.edge.checkDelivery.service.inter.CheckDeliveryService;

/**
 * 送货单核对流程结束，设置流程状态,且设置销售合同送货单核对状态
 * 
 * @author NingCG
 *
 */
public class CheckDeliveryEndListener implements JavaDelegate {

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
		// 获取CheckDelivery接口
		CheckDeliveryService checkDeliveryService = (CheckDeliveryService) ac.getBean("checkDeliveryServiceImpl");
		// 获取DeliveryService接口
		DeliveryService deliveryService = (DeliveryService) ac.getBean("deliveryServiceImpl");
		// 获取送货单核对对象
		CheckDelivery checkDelivery = checkDeliveryService.queryCheckDeliveryById(Integer.parseInt(id.trim()));
		// 获取送货单对象
		ERP_Delivery delivery = deliveryService.queryDeliveryById(checkDelivery.getDelivery_Id());
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(delivery.getSales_Contract_Id());
		checkDelivery.setApprovalDm(1);
		checkDeliveryService.editCheckDelivery(checkDelivery);
		contract.setIs_delivery(true);
		contractService.editSalesContract(contract);
	}

}
