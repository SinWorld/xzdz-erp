package com.edge.stocks.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.product.dao.Pro_StockRecordDao;
import com.edge.stocks.product.entity.ERP_stocks_Record;
import com.edge.stocks.product.service.inter.Pro_StockRecordService;

/**
 * 出入库记录业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Pro_StockRecordServiceImpl implements Pro_StockRecordService {
	@Resource
	private Pro_StockRecordDao stockRecordDao;

	// 新增出/入库记录
	public void saveStockRecord(ERP_stocks_Record stockRecord) {
		stockRecordDao.saveStockRecord(stockRecord);
	}

	// 查询该成品的入库记录
	public List<ERP_stocks_Record> recordList(Integer product) {
		return stockRecordDao.recordList(product);
	}
}
