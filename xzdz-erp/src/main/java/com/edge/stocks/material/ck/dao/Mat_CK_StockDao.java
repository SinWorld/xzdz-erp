package com.edge.stocks.material.ck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.material.rk.entity.ERP_MatStockRecord_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;

public interface Mat_CK_StockDao {
	// 查询当前库存下所有已入库的成品(去除已全部出库的成品)
	public List<ERP_RAW_Material> queryStockWckMaterial(@Param("material_Id") Integer material_Id);

	// 查询该成品在当前库存下的入库总库存量
	public Integer totalKc(@Param("material_Id") Integer material_Id, @Param("stock") Integer stock);

	// 分页查询成品的出库记录
	public List<ERP_Material_Stocks_Record> stockRecordList(ERP_MatStockRecord_QueryVo vo);

	// 查询成品的出库记录数
	public Integer recordCount(ERP_MatStockRecord_QueryVo vo);

	// 查询该成品的入库总库存量
	public Integer totalrkKc(@Param("material_Id") Integer material_Id);

}
