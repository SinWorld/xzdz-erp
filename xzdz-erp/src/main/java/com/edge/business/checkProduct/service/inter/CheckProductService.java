package com.edge.business.checkProduct.service.inter;

import java.util.List;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface CheckProductService {
	// 得到处于闲置状态的成品信息集合
	public ERP_Products queryXzProduct(String specification_type);

	// 得到某一成品的库存集合
	public List<ERP_Product_Stock> queryStockByProduct(Integer product);
}
