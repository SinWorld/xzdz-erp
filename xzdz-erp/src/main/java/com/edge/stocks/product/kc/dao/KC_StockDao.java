package com.edge.stocks.product.kc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_QueryVo;

public interface KC_StockDao {
	// 新增库存
	public void saveStock(ERP_Stock stock);

	// 根据成品Id及库位查询对应的库存对象
	public ERP_Stock queryStockByCPId(@Param("product_Id") Integer product_Id,
			@Param("stock_Id") Integer stock_Id);

	// 修改库存
	public void editStock(ERP_Stock stock);

	// 根据成品id获得库存量不为0的库存对象集合
	public List<ERP_Stock> queryStockByProductId(@Param("product_Id") Integer product_Id);

	// 根据材料的Id查询库存记录
	public ERP_Stock queryStockByCLId(@Param("product_Id") Integer product_Id,
			@Param("stock_Id") Integer stock_Id);

	// 分页加载成品库存列表
	public List<ERP_Stock> stockList(ERP_Stock_QueryVo vo);

	// 分页加载成品库存列表数量
	public Integer stockListCount(ERP_Stock_QueryVo vo);

	// 查询该成品总库存量
	public Integer totalKcNumber(@Param("product_Id") Integer product_Id);

	// 查询库存小于100的库存数据集合(库存报警)
	public List<ERP_Stock> warnStockList();

	// 加载库存新增的主键
	public Integer queryMaxRowId();
}
