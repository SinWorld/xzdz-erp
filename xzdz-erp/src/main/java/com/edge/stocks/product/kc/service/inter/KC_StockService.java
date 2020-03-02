package com.edge.stocks.product.kc.service.inter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_QueryVo;

public interface KC_StockService {
	// 新增库存
	public void saveStock(ERP_Stock stock);

	// 根据成品Id及库位查询对应的库存对象
	public ERP_Stock queryStockByCPId(Integer product_Id, Integer stock_Id);

	// 修改库存
	public void editStock(ERP_Stock stock);

	// 根据成品id获得库存量不为0的库存对象集合
	public List<ERP_Stock> queryStockByProductId(Integer product_Id);

	// 根据材料Id获得库存量不为0的库存对象集合
	public List<ERP_Stock> queryStockByMaterialId(Integer product_Id);

	// 根据材料的Id查询库存记录
	public ERP_Stock queryStockByCLId(Integer product_Id, Integer stock_Id);

	// 分页加载成品库存列表
	public List<ERP_Stock> stockList(ERP_Stock_QueryVo vo);

	// 分页加载成品库存列表数量
	public Integer stockListCount(ERP_Stock_QueryVo vo);

	// 查询该成品总库存量
	public Integer totalKcNumber(Integer product_Id);

	// 查询库存小于100的库存数据集合(库存报警)
	public List<ERP_Stock> warnStockList();

	// 加载库存新增的主键
	public Integer queryMaxRowId();

	// 根据id删除库存数据
	public void deleteStockById(Integer row_Id);

}
