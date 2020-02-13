package com.edge.stocks.product.kc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.product.kc.dao.KC_StockDao;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.service.inter.KC_StockService;

/**
 * 成品库存业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class KC_StockServiceImpl implements KC_StockService {

	@Resource
	private KC_StockDao stockDao;

	// 新增库存
	public void saveStock(ERP_Stock stock) {
		stockDao.saveStock(stock);
	}

	// 根据成品及库位查询库存记录
	public ERP_Stock queryStockByCPAndKw(Integer product_Id, Integer stock_Id) {
		return stockDao.queryStockByCPAndKw(product_Id, stock_Id);
	}

	// 修改库存
	public void editStock(ERP_Stock stock) {
		stockDao.editStock(stock);
	}

	// 根据成品主键获得成品库存对象集合
	public List<ERP_Stock> queryStockByCp(Integer product_Id) {
		return stockDao.queryStockByCp(product_Id);
	}

	// 根据材料及库位查询库存记录
	public ERP_Stock queryStockByCLAndKw(Integer product_Id, Integer stock_Id) {
		return stockDao.queryStockByCLAndKw(product_Id, stock_Id);
	}

}
