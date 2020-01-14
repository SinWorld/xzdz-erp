package com.edge.stocks.material.rk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.material.rk.dao.Mat_StockRecordDao;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.material.rk.service.inter.Mat_StockRecordService;
import com.edge.stocks.product.rk.dao.Pro_StockRecordDao;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;

/**
 * 出入库记录业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Mat_StockRecordServiceImpl implements Mat_StockRecordService {
	@Resource
	private Mat_StockRecordDao stockRecordDao;

	// 新增入库记录
	public void saveStockRecord(ERP_Material_Stocks_Record stockRecord) {
		stockRecordDao.saveStockRecord(stockRecord);
	}

	// 查询该材料的入库记录
	public List<ERP_Material_Stocks_Record> recordList(Integer material) {
		return stockRecordDao.recordList(material);
	}

}
