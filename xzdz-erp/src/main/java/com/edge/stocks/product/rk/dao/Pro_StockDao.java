package com.edge.stocks.product.rk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface Pro_StockDao {
	// 分页展现成品库存
	public List<ERP_Product_Stock> pro_StockList(ERP_ProStock_QueryVo vo);

	// 成品库存数量
	public Integer pro_StockCount(ERP_ProStock_QueryVo vo);

	// 新增库存
	public void saveProStock(ERP_Product_Stock stock);

	// 编辑库存
	public void editProStock(ERP_Product_Stock stock);
	
	//根据Id删除库存
	public void deleteProStock(@Param("stock_Id") Integer stock_Id);

	// 根据Id获得成品入库对象
	public ERP_Product_Stock queryPro_StockById(@Param("stock_Id") Integer stock_Id);

	// 查询所有的库存
	public List<ERP_Product_Stock> queryAllStock();

	// 库存名不重复校验
	public ERP_Product_Stock queryStockByStock(@Param("stock") String stock);
	
	
	

}
