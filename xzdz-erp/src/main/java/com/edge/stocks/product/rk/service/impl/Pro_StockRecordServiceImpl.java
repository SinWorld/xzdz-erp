package com.edge.stocks.product.rk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.stocks.product.rk.dao.Pro_StockRecordDao;
import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;
import com.edge.stocks.product.rk.service.inter.Pro_StockRecordService;

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

	// 查询改成品的入库总数量
	public Integer queryProRkNumber(Integer product) {
		return stockRecordDao.queryProRkNumber(product);
	}

	// 分页查询成品的入库记录
	public List<ERP_stocks_Record> stockRecordList(ERP_StockRecord_QueryVo vo) {
		return stockRecordDao.stockRecordList(vo);
	}

	// 查询成品的入库记录数
	public Integer recordCount(ERP_StockRecord_QueryVo vo) {
		return stockRecordDao.recordCount(vo);
	}

	// 根据Id获得成品入库记录对象
	public ERP_stocks_Record queryRecordById(Integer record_Id) {
		return stockRecordDao.queryRecordById(record_Id);
	}

	// ajax查询所有的成品
	public JSONArray allCpList() {
		return stockRecordDao.allCpList();
	}

	// ajax查询所有的库位
	public JSONArray allKwList() {
		return stockRecordDao.allKwList();
	}

	// ajax查询所有的经办人
	public JSONArray allJbrList() {
		return stockRecordDao.allJbrList();
	}
}
