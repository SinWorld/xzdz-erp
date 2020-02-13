package com.edge.stocks.product.kc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.kc.entity.ERP_Stock;

public interface KC_StockDao {
	// 新增库存
	public void saveStock(ERP_Stock stock);

	// 根据成品及库位查询库存记录
	public ERP_Stock queryStockByCPAndKw(@Param("product_Id") Integer product_Id, @Param("stock_Id") Integer stock_Id);

	// 修改库存
	public void editStock(ERP_Stock stock);

	// 根据成品主键获得成品库存对象集合
	public List<ERP_Stock> queryStockByCp(@Param("product_Id") Integer product_Id);
	
	//根据材料及库位查询库存记录
	public ERP_Stock queryStockByCLAndKw(@Param("product_Id") Integer product_Id, @Param("stock_Id") Integer stock_Id);
}
