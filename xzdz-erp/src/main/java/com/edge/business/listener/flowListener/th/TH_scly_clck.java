package com.edge.business.listener.flowListener.th;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.materialStockCK.service.inter.MaterialStockCkService;
import com.edge.business.productionPlan.entity.ERP_ProductionPlan;
import com.edge.business.productionPlan.service.inter.ProductionPlanService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.service.inter.MaterialService;
import com.edge.stocks.material.ck.service.inter.Mat_CK_StockService;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

/**
 * 生产领用退回至材料出库，设置其formkey，且将入库的材料记录删除并恢复其入库前的状态
 * 
 * @author NingCG
 *
 */
public class TH_scly_clck implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "materialStockCk/initMaterialStockCk.do");
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
		Mat_CK_StockService stockRecordService = (Mat_CK_StockService) ac.getBean("mat_CK_StockServiceImpl");
		// 获取MaterialStockCkService
		MaterialStockCkService materialStockCkService = (MaterialStockCkService) ac
				.getBean("materialStockCkServiceImpl");
		KC_StockService KC_StockService = (KC_StockService) ac.getBean("KC_StockServiceImpl");
		// 根据生产计划订单号获得库存状态集合
		List<ERP_Stock_Status> status = statusService.queryStastusByDdh(productionPlan.getPlan_Code());
		for (ERP_Stock_Status s : status) {
			// 设置库存状态为待入库
			s.setStatus("待出库");
			statusService.editStockStatus(s);
			// 根据主键获得材料对象
			ERP_RAW_Material material = materialService.queryMaterialById(s.getProduct_Id());
			// 设置材料对象为未入库
			material.setIs_ck(false);
			material.setIs_allck(false);
			materialService.editMaterial(material);
			// 根据材料Id获得入库记录集合
			List<ERP_Material_Stocks_Record> recordList = stockRecordService
					.queryStockRecordByMaterialId(material.getRaw_Material_Id());
			// 遍历集合删除材料入库记录数据
			for (ERP_Material_Stocks_Record r : recordList) {
				stockRecordService.deleteStockRecord(r.getRecord_Id());
			}
			// 根据材料Id获得库存对象集合
			List<ERP_Stock> stockList = materialStockCkService.queryStockListByClId(material.getRaw_Material_Id());
			// 遍历该集合
			for (ERP_Stock stock : stockList) {
				// 获得该材料的入库数据
				Integer rkNumber = materialStockCkService.queryStockRecordRkClIdAndKwId(stock.getProduct_Id(),
						stock.getStock_Id());
				stock.setSl(rkNumber);
				KC_StockService.editStock(stock);
			}

		}

	}

}
