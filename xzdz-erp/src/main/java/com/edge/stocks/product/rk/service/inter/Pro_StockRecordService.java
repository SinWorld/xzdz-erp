package com.edge.stocks.product.rk.service.inter;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

public interface Pro_StockRecordService {

	// 新增出/入库记录
	public void saveStockRecord(ERP_stocks_Record stockRecord);

	// 查询该成品的入库记录
	public List<ERP_stocks_Record> recordList(Integer product);

	// 查询改成品的入库总数量
	public Integer queryProRkNumber(Integer product);

	// 分页查询成品的入库记录
	public List<ERP_stocks_Record> stockRecordList(ERP_StockRecord_QueryVo vo);

	// 查询成品的入库记录数
	public Integer recordCount(ERP_StockRecord_QueryVo vo);

	// 根据Id获得成品入库记录对象
	public ERP_stocks_Record queryRecordById(Integer record_Id);

	// ajax查询所有的成品
	public JSONArray allCpList();

	// ajax查询所有的库位
	public JSONArray allKwList();

	// ajax查询所有的经办人
	public JSONArray allJbrList();

	// 根据Id删除成品入库记录
	public void deleteRecordById(Integer record_Id);
}
