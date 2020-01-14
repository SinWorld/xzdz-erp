package com.edge.stocks.material.rk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;

public interface Mat_StockRecordDao {
	// 新增入库记录
	public void saveStockRecord(ERP_Material_Stocks_Record stockRecord);

	// 查询该材料的入库记录
	public List<ERP_Material_Stocks_Record> recordList(@Param("material") Integer material);
}
