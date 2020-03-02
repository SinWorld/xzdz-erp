package com.edge.business.materialStockCK.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.materialStockCK.dao.MaterialStockCkDao;
import com.edge.business.materialStockCK.service.inter.MaterialStockCkService;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.product.kc.entity.ERP_Stock;

/**
 * 材料出库控制跳转层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterialStockCkServiceImpl implements MaterialStockCkService {

	@Resource
	private MaterialStockCkDao materialStockCkDao;

	// 跟据材料查询所有入库的库位
	public List<ERP_Material_Stock> qeuryStockByMaterialId(Integer raw_Material_Id) {
		return materialStockCkDao.qeuryStockByMaterialId(raw_Material_Id);
	}

	// 根据材料Id获得库存集合
	public List<ERP_Stock> queryStockListByClId(Integer raw_Material_Id) {
		return materialStockCkDao.queryStockListByClId(raw_Material_Id);
	}

	// 根据材料Id和库位Id获得入库总数据
	public Integer queryStockRecordRkClIdAndKwId(Integer raw_Material_Id, Integer stock) {
		return materialStockCkDao.queryStockRecordRkClIdAndKwId(raw_Material_Id, stock);
	}

	// 根据材料Id和库位Id获得出库总数据
	public Integer queryStockRecordCkClIdAndKwId(Integer raw_Material_Id, Integer stock) {
		return materialStockCkDao.queryStockRecordCkClIdAndKwId(raw_Material_Id, stock);
	}

}
