package com.edge.business.checkProduct.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface CheckProductDao {
	
	//根据物料id获得成品集合
	public List<ERP_Products> queryProductByMaterielId(@Param("materielId")String materielId);
	
	//根据成品集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(@Param("productIds")List<Integer>productIds);
	
	// 得到某一成品的库存集合
	public List<ERP_Product_Stock> queryStockByProduct(@Param("product") Integer product);
	
	//根据库存id获得库存对象
	public ERP_Stock queryStockById(@Param("row_Id")Integer row_Id);

}
