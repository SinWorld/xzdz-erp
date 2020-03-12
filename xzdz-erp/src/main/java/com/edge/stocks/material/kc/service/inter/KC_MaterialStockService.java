package com.edge.stocks.material.kc.service.inter;

import java.util.List;

import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_QueryVo;
import com.edge.stocks.product.kc.entity.ERP_WarnStock;

public interface KC_MaterialStockService {

	// 分页加载成品库存列表
	public List<ERP_Stock> stockList(ERP_Stock_QueryVo vo);

	// 分页加载成品库存列表数量
	public Integer stockListCount(ERP_Stock_QueryVo vo);

	// 库存警报
	public List<ERP_WarnStock> warnStockList();

	// 查询该材料总库存量
	public Integer totalKcNumber(String  materielId);

}
