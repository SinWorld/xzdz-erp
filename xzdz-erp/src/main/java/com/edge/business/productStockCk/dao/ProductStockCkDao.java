package com.edge.business.productStockCk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface ProductStockCkDao {

	// 跟据成品查询所有入库的库位
	public List<ERP_Product_Stock> qeuryStockByProductId(@Param("product_Id") Integer product_Id);

	// 根据成品Id获得库存集合
	public List<ERP_Stock> queryStockListByCpId(@Param("product_Id") Integer product_Id);

	// 根据成品Id和库位Id获得入库总数据
	public Integer queryStockRecordRkCpIdAndKwId(@Param("product_Id") Integer product_Id,
			@Param("stock") Integer stock);

}
