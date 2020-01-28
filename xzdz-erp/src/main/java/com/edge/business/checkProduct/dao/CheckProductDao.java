package com.edge.business.checkProduct.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface CheckProductDao {
	// 得到处于闲置状态的成品信息集合
	public List<ERP_Products> queryXzProduct();

	// 得到某一成品的库存集合
	public List<ERP_Product_Stock> queryStockByProduct(@Param("product") Integer product);
}
