package com.edge.business.listener.flowListener.lz;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.purchase.entity.ERP_Purchase_List;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.service.inter.PurchaseListService;
import com.edge.business.purchase.service.inter.PurchaseOrderService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 领导审核（材料）退货,流转至发起采购。原先的采购订单作废且生成的材料删除且生成的材料库存状态删除
 * 
 * @author NingCG
 *
 */
public class LZ_ReturnGoods implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "purchase/initPurchase.do");
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
		// 获取PurchaseOrderService
		PurchaseOrderService purchaseOrderService = (PurchaseOrderService) ac.getBean("purchaseOrderServiceImpl");
		// 根据销售订单获取采购合同对象
		ERP_Purchase_Order purchase_Order = purchaseOrderService
				.queryPurchaseOrderByXsht(contract.getSales_Contract_Id());
		purchase_Order.setStatus("作废");
		purchaseOrderService.editPurchaseOrder(purchase_Order);
		// 获取PurchaseListService
		PurchaseListService purchaseListService = (PurchaseListService) ac.getBean("purchaseListServiceImpl");
		// 获取采购订单材料集合
		List<ERP_Purchase_List> purchaseList = purchaseListService
				.queryPurchaseListByCght(purchase_Order.getPur_Order_Id());
		for (ERP_Purchase_List l : purchaseList) {
			l.setStatus("作废");
			purchaseListService.editPurchaseList(l);
		}
		// 获取生产计划ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 根据销售订单获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 根据生产计划编号获得库存状态集合
		List<ERP_Stock_Status> list = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		// 获取MaterialService
		MaterialService materialService = (MaterialService) ac.getBean("materialServiceImpl");
		// 遍历该集合
		for (ERP_Stock_Status l : list) {
			// 根据id获得材料对象
			ERP_RAW_Material material = materialService.queryMaterialById(l.getProduct_Id());
			materialService.deleteMaterial(material.getRaw_Material_Id());
		}
		for (ERP_Stock_Status l : list) {
			statusService.deleteStatusById(l.getRow_Id());
		}

	}

}
