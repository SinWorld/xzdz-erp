package com.edge.stocks.material.ck.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.material.entity.ERP_RAW_Material;
import com.edge.stocks.material.ck.dao.Mat_CK_StockDao;
import com.edge.stocks.material.ck.service.inter.Mat_CK_StockService;
import com.edge.stocks.material.rk.entity.ERP_MatStockRecord_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

/**
 * 材料出库业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Mat_CK_StockServiceImpl implements Mat_CK_StockService {

	@Resource
	private Mat_CK_StockDao stockDao;

	// 查询当前库存下所有已入库的成品(去除已全部出库的成品)
	public List<ERP_RAW_Material> queryStockWckMaterial(Integer material_Id) {
		return stockDao.queryStockWckMaterial(material_Id);
	}

	// 查询该成品在当前库存下的入库总库存量
	public Integer totalKc(Integer material_Id, Integer stock) {
		return stockDao.totalKc(material_Id, stock);
	}

	// 分页查询成品的出库记录
	public List<ERP_Material_Stocks_Record> recordList(ERP_MatStockRecord_QueryVo vo) {
		return stockDao.stockRecordList(vo);
	}

	// 查询成品的出库记录数
	public Integer recordCount(ERP_MatStockRecord_QueryVo vo) {
		return stockDao.recordCount(vo);
	}

	// 查询该成品的入库总库存量
	public Integer totalrkKc(Integer material_Id) {
		return stockDao.totalrkKc(material_Id);
	}

	// 跟据材料Id查询所有的出库记录集合
	public List<ERP_Material_Stocks_Record> queryStockRecordByMaterialId(Integer material) {
		return stockDao.queryStockRecordByMaterialId(material);
	}

	// 根据Id删除出库记录
	public void deleteStockRecord(Integer record_Id) {
		stockDao.deleteStockRecord(record_Id);
	}

}
