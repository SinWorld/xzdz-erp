package com.edge.business.listener.flowListener.lz;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 领导审核(成品) 返工至生产领用，且删除已生成的成品数据和成品库存状态数据
 * 
 * @author NingCG
 *
 */
public class LZ_Rework implements ExecutionListener {

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
		// 获取销售合同
		ERP_Sales_Contract contract = contractService.queryContractById(Integer.parseInt(id.trim()));
		// 获取ProductService
		ProductService productService = (ProductService) ac.getBean("productServiceImpl");
		// 根据销售合同获得成品集合
		List<ERP_Products> productList = productService.queryProductsByXsht(contract.getSales_Contract_Id());
		// 遍历集合删除该成品对象
		for (ERP_Products p : productList) {
			productService.deleteProductById(p.getProduct_Id());
		}
		// 获取KC_StatusService
		KC_StatusService KC_StatusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 根据销售合同编号获得成品库存集合
		List<ERP_Stock_Status> stockStatusList = KC_StatusService.queryStastusByDdh(contract.getContract_Code());
		// 遍历该集合 删除对象
		for (ERP_Stock_Status s : stockStatusList) {
			KC_StatusService.deleteStatusById(s.getRow_Id());
		}
	}

}
