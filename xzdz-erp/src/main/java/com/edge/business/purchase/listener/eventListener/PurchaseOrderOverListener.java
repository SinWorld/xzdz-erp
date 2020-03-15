package com.edge.business.purchase.listener.eventListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseOrderService;

/**
 * 设置采购合同状态
 * 
 * @author NingCG
 *
 */
public class PurchaseOrderOverListener implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// 取得businessKey
		String key = execution.getProcessBusinessKey();
		// 得到业务数据主键值
		String id = key.substring(key.indexOf(".") + 1);
		// 2.从spring容器中获取UserServiceImpl
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		// 获取PurchaseOrderService
		PurchaseOrderService purchaseOrderService = (PurchaseOrderService) ac.getBean("purchaseOrderServiceImpl");
		ERP_Purchase_Order purchaseOrder = purchaseOrderService.queryPurchaseOrderById(Integer.parseInt(id.trim()));
		purchaseOrder.setApprovalDm(3);
		purchaseOrderService.editPurchaseOrder(purchaseOrder);
	}

}
