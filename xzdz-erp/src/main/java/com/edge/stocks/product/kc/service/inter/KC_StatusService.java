package com.edge.stocks.product.kc.service.inter;

import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

public interface KC_StatusService {
	// 新增库存状态
	public void saveStockStatus(ERP_Stock_Status status);

	// 编辑库存
	public void editStockStatus(ERP_Stock_Status status);

	// 根据成品id查询库存状态
	public ERP_Stock_Status queryStastusByCpId(Integer product_Id);
}
