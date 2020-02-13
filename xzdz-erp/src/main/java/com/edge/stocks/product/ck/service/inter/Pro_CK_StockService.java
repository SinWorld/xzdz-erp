package com.edge.stocks.product.ck.service.inter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

public interface Pro_CK_StockService {

	// 查询当前库存下所有已入库的成品
	public List<ERP_Products> queryStockWckProduct(Integer stock_Id);

	// 根据Id获得成品记录对象
	public ERP_stocks_Record queryRecordListById(Integer record_Id);

	// 查询该成品入库总库存量
	public Integer totalrkKc(@Param("product") Integer product);
}
