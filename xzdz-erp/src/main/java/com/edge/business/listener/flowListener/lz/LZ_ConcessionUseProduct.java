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

/**
 * 领导审核(成品)让步使用/不合格入库,且设置该批材料状态为让步使用或不合格入库
 * 
 * @author NingCG
 *
 */
public class LZ_ConcessionUseProduct implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productStock/initProductStock.do");
		// 获取提交的流程变量,根据变量值得不同设置不同的状态
		String variable = (String) execution.getVariable("outcome");
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
		// 获取ProductService
		ProductService productService = (ProductService) ac.getBean("productServiceImpl");
		// 根据销售合同获得成品集合
		List<ERP_Products> productList = productService.queryProductsByXsht(contract.getSales_Contract_Id());
		// 遍历该集合
		for (ERP_Products p : productList) {
			if ("不合格".equals(p.getMaterialQuality())) {
				if ("让步使用".contentEquals(variable)) {
					p.setMaterialQuality("让步使用");
				} else if ("不合格入库".equals(variable)) {
					p.setMaterialQuality("不合格");
				}
				productService.editProduct(p);
			}

		}
	}

}
