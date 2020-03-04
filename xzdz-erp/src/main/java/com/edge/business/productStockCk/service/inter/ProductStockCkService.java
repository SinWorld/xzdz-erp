package com.edge.business.productStockCk.service.inter;

import java.util.List;

import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface ProductStockCkService {
	// 跟据成品查询所有入库的库位
	public List<ERP_Product_Stock> qeuryStockByProductId(Integer product_Id);

	// 根据成品Id获得库存集合
	public List<ERP_Stock> queryStockListByCpId(Integer product_Id);

	// 根据成品Id和库位Id获得入库总数据
	public Integer queryStockRecordRkCpIdAndKwId(Integer product_Id, Integer stock);

}
