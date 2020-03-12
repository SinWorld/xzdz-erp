package com.edge.business.sale.listener.eventListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;

/**
 * 销售订单流程结束设置该销售订单状态为终止
 * 
 * @author NingCG
 *
 */
public class SaleContractOverListener implements JavaDelegate {

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
		contract.setApprovalDm(3);// 1.完成 2.审批中 3.终止
		contractService.editSalesContract(contract);
	}

}
