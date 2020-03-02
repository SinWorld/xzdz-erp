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
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

/**
 * 材料出库退回至材料入库 且设置当前货物未入库 is_allRk=false is_rk=false 删除该材料的入库记录 删除该拆料的入库数据
 * 修改该材料的入库状态为待入库
 * 
 * @author NingCG
 *
 */
public class TH_clck_clrk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materialStock/initMaterialStock.do");
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
		// 获取MaterialService
		MaterialService materialService = (MaterialService) ac.getBean("materialServiceImpl");
		// 获取ProductionPlanService
		ProductionPlanService productionPlanService = (ProductionPlanService) ac.getBean("productionPlanServiceImpl");
		// 根据销售合同获取生产计划对象
		ERP_ProductionPlan productionPlan = productionPlanService.queryPlanByXsht(contract.getSales_Contract_Id());
		// 获取KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取Mat_StockRecordService
		Mat_StockRecordService stockRecordService = (Mat_StockRecordService) ac.getBean("mat_StockRecordServiceImpl");
		// 获取KC_StockService
		KC_StockService stockService = (KC_StockService) ac.getBean("KC_StockServiceImpl");
		// 根据生产计划订单号获得库存状态集合
		List<ERP_Stock_Status> status = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		for (ERP_Stock_Status s : status) {
			// 设置库存状态为待入库
			s.setStatus("待入库");
			statusService.editStockStatus(s);
			// 根据主键获得材料对象
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			// 设置材料对象为未入库
			material.setIs_rk(false);
			material.setIs_allrk(false);
			materialService.editMaterial(material);
			// 根据材料Id获得入库记录集合
			List<ERP_Material_Stocks_Record> recordList = stockRecordService
					.queryStockRecordByMaterialId(material.getRaw_Material_Id());
			// 遍历集合删除材料入库记录数据
			for (ERP_Material_Stocks_Record r : recordList) {
				stockRecordService.deleteStockRecord(r.getRecord_Id());
			}
			// 根据材料Id获得库存数据集合
			List<ERP_Stock> stockList = stockService.queryStockByMaterialId(material.getRaw_Material_Id());
			// 遍历该集合删除
			for (ERP_Stock stock : stockList) {
				stockService.deleteStockById(stock.getRow_Id());
			}
		}
	}

}
