package com.edge.stocks.material.ck.service.inter;

import java.util.List;

import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.material.rk.entity.ERP_MatStockRecord_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;

public interface Mat_CK_StockService {

	// 查询当前库存下所有已入库的成品(去除已全部出库的成品)
	public List<ERP_RAW_Material> queryStockWckMaterial(Integer material_Id);

	// 查询该成品在当前库存下的入库总库存量
	public Integer totalKc(Integer material_Id, Integer stock);

	// 分页查询该材料
	public List<ERP_Material_Stocks_Record> recordList(ERP_MatStockRecord_QueryVo vo);

	// 查询该材料的总数量
	public Integer recordCount(ERP_MatStockRecord_QueryVo vo);

	// 查询该成品的入库总库存量
	public Integer totalrkKc(Integer material_Id);

	// 跟据材料Id查询所有的出库记录集合
	public List<ERP_Material_Stocks_Record> queryStockRecordByMaterialId(Integer material);

	// 根据Id删除出库记录
	public void deleteStockRecord(Integer record_Id);

}
