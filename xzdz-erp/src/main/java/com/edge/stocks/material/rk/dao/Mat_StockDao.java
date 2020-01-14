package com.edge.stocks.material.rk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

public interface Mat_StockDao {
	// 分页展现成品库存
	public List<ERP_Material_Stock> mat_StockList(ERP_MatStock_QueryVo vo);

	// 成品库存数量
	public Integer mat_StockCount(ERP_MatStock_QueryVo vo);

	// 新增库存
	public void saveMatStock(ERP_Material_Stock stock);

	// 查询刚入库的库存主键
	public Integer queryMaxStock_Id();

	// 根据id获得库存对象
	public ERP_Material_Stock queryMatStockById(@Param("material_Id") Integer material_Id);

	// 剩余材料入库
	public void syrkMaterial(ERP_Material_Stock stock);

}
