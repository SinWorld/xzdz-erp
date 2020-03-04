package com.edge.stocks.product.ck.service.inter;

import java.util.List;

import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

public interface Pro_CK_StockRecordService {
	// 分页查询成品的出库记录
	public List<ERP_stocks_Record> ckRecords(ERP_StockRecord_QueryVo vo);

	// 查询成品的出库总数量
	public Integer ckRecordCount(ERP_StockRecord_QueryVo vo);

	// 根据成品Id获得成品出库记录
	public List<ERP_stocks_Record> queryRecordByProductId(Integer product);

	// 根据Id删除出库数据
	public void deleteRecordById(Integer record_Id);
}
