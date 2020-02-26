package com.edge.stocks.product.kc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.stocks.product.kc.entity.ERP_Stock_Status;

public interface KC_StatusDao {

	// 新增库存状态
	public void saveStockStatus(ERP_Stock_Status status);

	// 编辑库存
	public void editStockStatus(ERP_Stock_Status status);

	// 根据成品id查询库存状态
	public ERP_Stock_Status queryStastusByCpId(@Param("product_Id") Integer product_Id);

	// 根据材料Id查询库存状态
	public ERP_Stock_Status queryStastusByClId(@Param("product_Id") Integer product_Id);

	// 根据订单编号查询库存状态集合
	public List<ERP_Stock_Status> queryStastusByDdh(@Param("oddNumbers") String oddNumbers);

	// 删除库存对象
	public void deleteStatusById(@Param("row_Id") Integer row_Id);
}
