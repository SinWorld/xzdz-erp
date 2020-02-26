package com.edge.business.listener.flowListener.th;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.materialPlan.entity.ERP_MaterialPlan;
import com.edge.business.materialPlan.entity.MaterialPlanOrder;
import com.edge.business.materialPlan.service.inter.MaterialPlanOrderService;
import com.edge.business.materialPlan.service.inter.MaterialPlanService;
import com.edge.business.processingIngredients.service.inter.ProcessingIngredientsService;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 发起采购退回到加工配料连线监听器，用于设置加工配料的formKey
 * 
 * @author NingCG
 *
 */
public class TH_fqcg_jgpl implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "processingIngredients/initEditProcessingIngredients.do");
		// 退回时修改对应的材料为闲置状态且单号为空
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
		// 获得材料计划Service
		MaterialPlanService materialPlanService = (MaterialPlanService) ac.getBean("materialPlanServiceImpl");
		// 获得材料计划货物项Service
		MaterialPlanOrderService materialPlanOrderService = (MaterialPlanOrderService) ac
				.getBean("materialPlanOrderServiceImpl");
		// 获得加工配料Service
		ProcessingIngredientsService processingIngredientsService = (ProcessingIngredientsService) ac
				.getBean("processingIngredientsServiceImpl");
		// 根据销售订单获得对应的材料计划对象
		ERP_MaterialPlan materialPlan = materialPlanService.queryMaterialPlanByXsht(contract.getSales_Contract_Id());
		// 获得对应的生产计划对象
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 设置生产计划状态为已配料
		productionPlan.setStatus("已接单");
		productionPlanService.editProductionPlan(productionPlan);
		// 根据材料计划对象获得对应的材料计划货物项集合
		List<MaterialPlanOrder> orders = materialPlanOrderService.queryOrderByMaplanId(materialPlan.getRow_Id());
		// 用于存储所有该材料计划内的材料主键
		List<Integer> clIds = new ArrayList<Integer>();
		// 遍历该集合
		for (MaterialPlanOrder o : orders) {
			// 获得材料的物料Id
			String materielId = o.getMaterielId();
			// 根据物料Id获得材料集合
			List<ERP_RAW_Material> cljhs = processingIngredientsService.queryMaterialByMaterielId(materielId);
			// 遍历该集合
			for (ERP_RAW_Material cl : cljhs) {
				clIds.add(cl.getRaw_Material_Id());
			}
		}
		if (clIds != null && clIds.size() > 0) {
			// 获得对应的闲置状态的材料库存集合
			List<ERP_Stock_Status> statsusList = materialPlanService.statsusList(clIds);
			// 遍历该集合
			for (ERP_Stock_Status s : statsusList) {
				// 设置状态为闲置且设置单号为null
				s.setStatus("闲置");
				s.setOddNumbers(null);
				statusService.editStockStatus(s);
			}
		}
	}
}
