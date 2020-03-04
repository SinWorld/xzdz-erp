package com.edge.stocks.product.ck.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.ck.dao.Pro_CK_StockDao;
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

/**
 * 成品出库业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Pro_CK_StockServiceImpl implements Pro_CK_StockService {
	@Resource
	private Pro_CK_StockDao stockDao;

	// 查询当前库存下所有已入库的成品
	public List<ERP_Products> queryStockWckProduct(Integer stock_Id) {
		return stockDao.queryStockWckProduct(stock_Id);
	}

	// 根据Id获得成品记录对象
	public ERP_stocks_Record queryRecordListById(Integer record_Id) {
		return stockDao.queryRecordListById(record_Id);
	}

	// 查询该成品入库总库存量
	public Integer totalrkKc(Integer product) {
		return stockDao.totalrkKc(product);
	}

	// 查询该成品在当前库存下的入库总库存量
	public Integer totalKc(Integer product, Integer stock) {
		return stockDao.totalKc(product, stock);
	}

}
