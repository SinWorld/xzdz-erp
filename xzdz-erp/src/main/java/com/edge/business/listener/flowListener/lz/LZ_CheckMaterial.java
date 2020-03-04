package com.edge.business.listener.flowListener.lz;

import java.util.Calendar;
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
 * 流转材料检验连线监听器，用于设置成品核对的formKey，且生成采购并设置状态,且设置销售订单状态,且设置生产计划状态
 * 
 * @author NingCG
 *
 */
public class LZ_CheckMaterial implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "checkMaterial/initCheckMaterial.do");
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
		contract.setStatus("已排产");
		contractService.editSalesContract(contract);
		// 获取PurchaseOrderService
		PurchaseOrderService purchaseOrderService = (PurchaseOrderService) ac.getBean("purchaseOrderServiceImpl");
		// 获取purchaseListService
		PurchaseListService purchaseListService = (PurchaseListService) ac.getBean("purchaseListServiceImpl");
		// 获取MaterialService
		MaterialService materialService = (MaterialService) ac.getBean("materialServiceImpl");
		// 获取KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 根据销售合同获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		productionPlan.setStatus("已排产");
		productionPlanService.editProductionPlan(productionPlan);
		// 根据销售合同获得采购合同对象
		ERP_Purchase_Order purchaseOrder = purchaseOrderService
				.queryPurchaseOrderByXsht(contract.getSales_Contract_Id());
		// 编辑采购合同
		purchaseOrder.setStatus("已到货");
		purchaseOrder.setIs_Availability(true);
		purchaseOrderService.editPurchaseOrder(purchaseOrder);
		// 根据采购合同对象获得采购清单
		List<ERP_Purchase_List> list = purchaseListService.queryPurchaseListByCght(purchaseOrder.getPur_Order_Id());
		// 遍历该集合生成材料
		for (ERP_Purchase_List l : list) {
			// 根据生产计划订单号获得库存状态集合
			List<ERP_Stock_Status> status = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
			// 编辑该集合获得材料
			ERP_RAW_Material material = new ERP_RAW_Material();
			// 设置属性
			material.setMaterial_Name(l.getPro_Name());
			material.setSpecification_Type(l.getModel());
			material.setUnit(l.getCompany());
			material.setNumbers(l.getSl());
			material.setRemarks(l.getBz());
			material.setMaterielId(l.getMaterielId());
			material.setMaterial_Code(this.clbh());
			material.setIs_ck(false);
			material.setIs_rk(false);
			material.setIs_allrk(false);
			material.setIs_allck(false);
			material.setMaterialQuality("待检验");
			materialService.saveMaterial(material);
			// 新增库存状态
			ERP_Stock_Status stockStatus = new ERP_Stock_Status();
			stockStatus.setProduct_Id(materialService.queryMaxMaterialId());
			stockStatus.setStock_Type(true);
			stockStatus.setStatus("待入库");
			stockStatus.setOddNumbers(productionPlan.getPlan_Code());
			statusService.saveStockStatus(stockStatus);
		}
	}

	// 生成材料编号
	private String clbh() {
		// 1.设置项目编号 编号规则为’P’+年、月、日、时、分、秒+六位随机数 如：P20190604172610123456
		// 获取当前系统时间 并获取年月日时分秒
		Calendar now = Calendar.getInstance();
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (now.get(Calendar.MONTH) + 1 <= 9) {
			month = 0 + month;
		}
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		if (now.get(Calendar.DAY_OF_MONTH) <= 9) {
			day = 0 + day;
		}
		String Hourse = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		if (now.get(Calendar.HOUR_OF_DAY) <= 9) {
			Hourse = 0 + Hourse;
		}
		String minute = String.valueOf(now.get(Calendar.MINUTE));
		if (now.get(Calendar.MINUTE) <= 9) {
			minute = 0 + minute;
		}
		String second = String.valueOf(now.get(Calendar.SECOND));
		if (now.get(Calendar.SECOND) <= 9) {
			second = 0 + second;
		}
		// 产生六位随机数
		int a = (int) ((Math.random() * 9 + 1) * 100000);
		String x = String.valueOf(a);
		String time = year + month + day + Hourse + minute + second;
		String bh = "CL" + "-" + time + x;
		return bh;
	}

}
