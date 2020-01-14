package com.edge.stocks.material.ck.service.inter;

import java.util.List;

import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;

public interface Mat_CK_StockService {
	// 分页展现材料库存
	public List<ERP_Material_Stock> mat_CK_StockList(ERP_MatStock_QueryVo vo);

	// 材料库存数量
	public Integer matCKStockCount(ERP_MatStock_QueryVo vo);

	// 加载所有未出库的库存
	public List<ERP_Material_Stock> allWckMaterialStock();

	// 加载某一库存下材料已出库的数量
	public Integer yckCount(Integer stock);

	// 加载该材料的总出库数量
	public Integer totalYckCount(Integer material);

}
