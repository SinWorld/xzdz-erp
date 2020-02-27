package com.edge.business.listener.flowListener.th;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 材料入库退回到材料检验,且设置对应的材料物料质量状态为待检验
 * 
 * @author NingCG
 *
 */
public class TH_clrk_cljy implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkMaterial/initEditLeadershipAudit.do");
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
		// 获取ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 根据销售合同获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 获取MaterialService
		MaterialService materialService = (MaterialService) ac.getBean("materialServiceImpl");
		// 获取KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		List<ERP_Stock_Status> stastusList = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		for (ERP_Stock_Status s : stastusList) {
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			material.setMaterialQuality("待检验");
			materialService.editMaterial(material);
		}
	}

}
