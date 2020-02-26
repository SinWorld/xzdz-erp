package com.edge.business.listener.flowListener.th;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 材料计划退回到生产计划连线监听器，用于设置成品核对的formKey
 * 
 * @author NingCG
 *
 */
public class TH_cljh_scjh implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "prouctPlan/initEditProuctPlan.do");
		// 退回时修改对应的货物库存状态为闲置
		String cphds = (String) execution.getVariable("cphd");
		// 获取businessKey
		String businessKey = execution.getProcessBusinessKey();
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
				// 设置状态为闲置
				status.setStatus("闲置");
				statusService.editStockStatus(status);
			}
		}
	}

}
