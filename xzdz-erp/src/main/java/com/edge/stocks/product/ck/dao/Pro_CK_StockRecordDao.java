package com.edge.stocks.product.ck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

public interface Pro_CK_StockRecordDao {
	// 分页查询成品的出库记录
	public List<ERP_stocks_Record> ckRecords(ERP_StockRecord_QueryVo vo);

	// 查询成品的出库总数量
	public Integer ckRecordCount(ERP_StockRecord_QueryVo vo);

	// 根据成品Id获得成品出库记录
	public List<ERP_stocks_Record> queryRecordByProductId(@Param("product") Integer product);

	// 根据Id删除出库数据
	public void deleteRecordById(@Param("record_Id") Integer record_Id);

}
