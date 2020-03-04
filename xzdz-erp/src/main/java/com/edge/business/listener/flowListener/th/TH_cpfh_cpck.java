package com.edge.business.listener.flowListener.th;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.edge.business.productStockCk.service.inter.ProductStockCkService;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;
import com.edge.product.entity.ERP_Products;
import com.edge.product.service.inter.ProductService;
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockRecordService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;
import com.edge.stocks.product.kc.service.inter.KC_StockService;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

/**
 * 成品发货退回至成品出库节点,且将入库的成品记录删除并恢复其入库前的状态
 * 
 * @author NingCG
 *
 */
public class TH_cpfh_cpck implements ExecutionListener {

	public void notify(DelegateExecution execution) throws Exception {
		execution.setVariable("url", "productStockCk/initProductStockCk.do");
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
		// 获取KC_StatusService
		KC_StatusService statusService = (KC_StatusService) ac.getBean("KC_StatusServiceImpl");
		// 获取Pro_CK_StockRecordService
		Pro_CK_StockRecordService stockRecordService = (Pro_CK_StockRecordService) ac
				.getBean("pro_CK_StockRecordServiceImpl");
		// 获取ProductStockCkService
		ProductStockCkService productStockCkService = (ProductStockCkService) ac.getBean("productStockCkServiceImpl");
		// 获取KC_StockService
		KC_StockService KC_StockService = (KC_StockService) ac.getBean("KC_StockServiceImpl");
		// 根据销售订单号获取库存状态集合
		List<ERP_Stock_Status> statusList = statusService.queryStastusByDdh(contract.getContract_Code());
		for (ERP_Stock_Status s : statusList) {
			// 设置状态为待出库
			s.setStatus("待出库");
			statusService.editStockStatus(s);
			// 根据主键获得成品对象
			ERP_Products product = productService.queryProductById(s.getProduct_Id());
			// 设置材料对象为未出库
			product.setIs_ck(false);
			product.setIs_allck(false);
			productService.editProduct(product);
			// 根据成品Id获得成品出库记录集合
			List<ERP_stocks_Record> recordList = stockRecordService.queryRecordByProductId(product.getProduct_Id());
			// 遍历集合删除材料入库记录数据
			for (ERP_stocks_Record r : recordList) {
				stockRecordService.deleteRecordById(r.getRecord_Id());
			}
			// 根据成品Id获得库存对象集合
			List<ERP_Stock> stockList = productStockCkService.queryStockListByCpId(product.getProduct_Id());
			// 遍历该集合
			for (ERP_Stock stock : stockList) {
				// 获得该成品的入库数据
				Integer rksl = productStockCkService.queryStockRecordRkCpIdAndKwId(stock.getProduct_Id(),
						stock.getStock_Id());
				stock.setSl(rksl);
				KC_StockService.editStock(stock);
			}
		}

	}

}
