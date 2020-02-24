package com.edge.business.purchase.dao;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.edge.business.purchase.entity.ERP_Purchase_List;

public interface PurchaseListDao {

	// 新增采购清单
	public void savePurchaseList(ERP_Purchase_List purchaseList);
	
	

}
