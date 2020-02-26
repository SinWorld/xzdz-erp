package com.edge.business.purchase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.purchase.entity.ERP_Purchase_List;

public interface PurchaseListDao {

	// 新增采购清单
	public void savePurchaseList(ERP_Purchase_List purchaseList);

	// 根据采购合同Id获得采购清单集合
	public List<ERP_Purchase_List> queryPurchaseListByCght(@Param("pur_Order_Id") Integer pur_Order_Id);

	// 根据Id删除采购清单
	public void deletePurchaseListById(@Param("pur_Id") Integer pur_Id);

	// 编辑采购清单
	public void editPurchaseList(ERP_Purchase_List purchaseList);

}
