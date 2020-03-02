package com.edge.business.materialStockCK.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.entity.ERP_Material_Stocks_Record;
import com.edge.stocks.product.kc.entity.ERP_Stock;

public interface MaterialStockCkDao {

	// 跟据材料查询所有入库的库位
	public List<ERP_Material_Stock> qeuryStockByMaterialId(@Param("raw_Material_Id") Integer raw_Material_Id);

	// 根据材料Id获得库存集合
	public List<ERP_Stock> queryStockListByClId(@Param("raw_Material_Id") Integer raw_Material_Id);

	// 根据材料Id和库位Id获得入库总数据
	public Integer queryStockRecordRkClIdAndKwId(@Param("raw_Material_Id") Integer raw_Material_Id,
			@Param("stock") Integer stock);

	// 根据材料Id和库位Id获得出库总数据
	public Integer queryStockRecordCkClIdAndKwId(@Param("raw_Material_Id") Integer raw_Material_Id,
			@Param("stock") Integer stock);
}
