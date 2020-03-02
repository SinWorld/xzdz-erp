package com.edge.business.materialStockCK.service.inter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.product.kc.entity.ERP_Stock;

public interface MaterialStockCkService {

	// 跟据材料查询所有入库的库位
	public List<ERP_Material_Stock> qeuryStockByMaterialId(Integer raw_Material_Id);

	// 根据材料Id获得库存集合
	public List<ERP_Stock> queryStockListByClId(Integer raw_Material_Id);

	// 根据材料Id和库位Id获得入库总数据
	public Integer queryStockRecordRkClIdAndKwId(Integer raw_Material_Id, Integer stock);

	// 根据材料Id和库位Id获得出库总数据
	public Integer queryStockRecordCkClIdAndKwId(Integer raw_Material_Id, Integer stock);

}
