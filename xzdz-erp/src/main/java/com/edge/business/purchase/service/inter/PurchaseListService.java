package com.edge.business.purchase.service.inter;

import com.alibaba.fastjson.JSONArray;
import com.edge.business.purchase.entity.ERP_Purchase_List;

public interface PurchaseListService {

	// 新增采购清单
	public void savePurchaseList(ERP_Purchase_List purchaseList);

}
