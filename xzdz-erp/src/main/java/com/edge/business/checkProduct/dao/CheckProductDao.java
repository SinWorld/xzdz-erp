package com.edge.business.checkProduct.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

public interface CheckProductDao {
	// 得到销售订单下处于闲置状态的成品信息
	public ERP_Products queryXzProduct(@Param("specification_type") String specification_type);

	// 得到某一成品的库存集合
	public List<ERP_Product_Stock> queryStockByProduct(@Param("product") Integer product);

	// 取得闲置成品的入库记录对象
	public ERP_stocks_Record xzcpStockId(@Param("product") Integer product);

}
