package com.edge.business.purchase.service.inter;

import java.util.List;

import com.edge.business.purchase.entity.ERP_Purchase_List;

public interface PurchaseListService {

	// 新增采购清单
	public void savePurchaseList(ERP_Purchase_List purchaseList);

	// 根据采购合同Id获得采购清单集合
	public List<ERP_Purchase_List> queryPurchaseListByCght(Integer pur_Order_Id);

	// 根据Id删除采购清单
	public void deletePurchaseListById(Integer pur_Id);

	// 编辑采购清单
	public void editPurchaseList(ERP_Purchase_List purchaseList);

}
