package com.edge.business.purchase.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.business.purchase.dao.PurchaseOrderDao;
import com.edge.business.purchase.entity.ERP_Purchase_Order;
import com.edge.business.purchase.entity.PurchaseOrder_QueryVo;
import com.edge.business.purchase.service.inter.PurchaseOrderService;

/**
 * 采购订单业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Resource
	private PurchaseOrderDao purchaseOrderDao;

	// 新增采购订单
	public void savePurchaseOrder(ERP_Purchase_Order purchaseOrder) {
		purchaseOrderDao.savePurchaseOrder(purchaseOrder);
	}

	// ajax加载所有的供货单位
	public JSONArray allSupplier() {
		return purchaseOrderDao.allSupplier();
	}

	// 合同编号生成
	public String htbh(String pur_Code) {
		return purchaseOrderDao.htbh(pur_Code);
	}

	// 查询新增后的采购订单主键
	public Integer queryMaxOrderId() {
		return purchaseOrderDao.queryMaxOrderId();
	}

	// 根据销售合同获取采购合同
	public ERP_Purchase_Order queryPurchaseOrderByXsht(Integer sales_Contract_Id) {
		return purchaseOrderDao.queryPurchaseOrderByXsht(sales_Contract_Id);
	}

	// 编辑采购合同
	public void editPurchaseOrder(ERP_Purchase_Order purchaseOrder) {
		purchaseOrderDao.editPurchaseOrder(purchaseOrder);
	}

	// 分页展现采购合同
	public List<ERP_Purchase_Order> purchasePrderOrderList(PurchaseOrder_QueryVo vo) {
		return purchaseOrderDao.purchasePrderOrderList(vo);
	}

	// 采购合同总数量
	public Integer purchasePrderOrderCount(PurchaseOrder_QueryVo vo) {
		return purchaseOrderDao.purchasePrderOrderCount(vo);
	}

	// 根据id获得采购合同对象
	public ERP_Purchase_Order queryPurchaseOrderById(Integer pur_Order_Id) {
		return purchaseOrderDao.queryPurchaseOrderById(pur_Order_Id);
	}

}
