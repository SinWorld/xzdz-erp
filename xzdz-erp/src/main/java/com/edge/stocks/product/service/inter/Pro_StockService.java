package com.edge.stocks.product.service.inter;

import java.util.List;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.entity.ERP_Product_Stock;

public interface Pro_StockService {
	// 分页展现成品库存
	public List<ERP_Product_Stock> pro_StockList(ERP_ProStock_QueryVo vo);

	// 成品库存数量
	public Integer pro_StockCount(ERP_ProStock_QueryVo vo);

	// 加载所有未入库的成品
	public List<ERP_Products> allWrkProduct();

	// 新增库存
	public void saveProStock(ERP_Product_Stock stock);

	// 查询刚入库的库存主键
	public Integer queryMaxStock_Id();
}
