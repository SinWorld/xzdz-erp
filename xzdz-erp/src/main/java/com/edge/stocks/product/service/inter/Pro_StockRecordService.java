package com.edge.stocks.product.service.inter;

import java.util.List;

import com.edge.stocks.product.entity.ERP_stocks_Record;

public interface Pro_StockRecordService {

	// 新增出/入库记录
	public void saveStockRecord(ERP_stocks_Record stockRecord);

	// 查询该成品的入库记录
	public List<ERP_stocks_Record> recordList(Integer product);
}
