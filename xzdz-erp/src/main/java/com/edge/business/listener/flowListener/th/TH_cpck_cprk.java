package com.edge.business.listener.flowListener.th;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;

/**
 * 成品出库退回至成品入库节点 且设置当前货物未入库 is_allRk=false is_rk=false 删除该材料的入库记录 删除该拆料的入库数据
 * 修改该材料的入库状态为待入库
 * 
 * @author NingCG
 *
 */
public class TH_cpck_cprk implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productStock/initProductStock.do");
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
		// 获取KC_StockService
		KC_StockService stockService = (KC_StockService) ac.getBean("KC_StockServiceImpl");
		// 获取KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取Pro_StockRecordService
		Pro_StockRecordService stockRecordService = (Pro_StockRecordService) ac.getBean("pro_StockRecordServiceImpl");
		// 根据销售合同获得成品集合
		List<ERP_Products> productList = productService.queryProductsByXsht(contract.getSales_Contract_Id());
		// 获取成品库存状态集合
		List<ERP_Stock_Status> stockStatusList = statusService.queryStastusByDdh(contract.getContract_Code());
		// 更改库存状态
		for (ERP_Stock_Status s : stockStatusList) {
			// 设置库存状态为待入库
			s.setStatus("待入库");
			statusService.editStockStatus(s);
		}
		// 修改成品状态
		for (ERP_Products p : productList) {
			p.setIs_allrk(false);
			p.setIs_rk(false);
			productService.editProduct(p);
			// 根据成品Id获得入库记录集合
			List<ERP_stocks_Record> recordList = stockRecordService.recordList(p.getProduct_Id());
			// 遍历该集合删除入库记录
			for (ERP_stocks_Record r : recordList) {
				stockRecordService.deleteRecordById(r.getRecord_Id());
			}
			// 根据成品Id获得库存集合
			List<ERP_Stock> stockList = stockService.queryStockByProductId(p.getProduct_Id());
			// 遍历该集合删除
			for (ERP_Stock s : stockList) {
				stockService.deleteStockById(s.getRow_Id());
			}
		}

	}

}
