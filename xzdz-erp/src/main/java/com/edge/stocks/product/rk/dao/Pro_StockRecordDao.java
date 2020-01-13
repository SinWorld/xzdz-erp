package com.edge.stocks.product.rk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

public interface Pro_StockRecordDao {
	// 新增入库记录
	public void saveStockRecord(ERP_stocks_Record stockRecord);

	// 查询该成品的入库记录
	public List<ERP_stocks_Record> recordList(@Param("product") Integer product);
}
