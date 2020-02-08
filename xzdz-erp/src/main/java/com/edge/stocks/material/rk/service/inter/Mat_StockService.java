package com.edge.stocks.material.rk.service.inter;

import java.util.List;

import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;

public interface Mat_StockService {
	// 分页展现材料库存
	public List<ERP_Material_Stock> mat_StockList(ERP_MatStock_QueryVo vo);

	// 材料库存数量
	public Integer mat_StockCount(ERP_MatStock_QueryVo vo);

	// 新增库存
	public void saveMatStock(ERP_Material_Stock stock);

	// 编辑库存
	public void editMatStock(ERP_Material_Stock stock);

	// 根据Id删除库存
	public void deleteMatStock(Integer material_Id);

	// 材料库存重复名检测
	public ERP_Material_Stock checkKw(String stock);

	// 查询所有的库存
	public List<ERP_Material_Stock> queryAllStock();

	// 根据id获得库存对象
	public ERP_Material_Stock queryMatStockById(Integer material_Id);

}
