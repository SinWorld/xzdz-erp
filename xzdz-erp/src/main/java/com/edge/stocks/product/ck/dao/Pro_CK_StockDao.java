package com.edge.stocks.product.ck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface Pro_CK_StockDao {
	// 分页展现成品库存
	public List<ERP_Product_Stock> pro_CK_StockList(ERP_ProStock_QueryVo vo);

	// 成品库存数量
	public Integer pro_CK_StockCount(ERP_ProStock_QueryVo vo);
	
	// 加载所有未出库的库存
	public List<ERP_Product_Stock> allWckProductStock();
	
	//加载某一库存下成品已出库的数量
	public Integer yckCount(@Param("stock_Id")Integer stock_Id);
	
	//加载改成品的总出库数量
	public Integer totalYckCount(@Param("product")Integer product);
	
	

	

}
