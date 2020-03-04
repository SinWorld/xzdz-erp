package com.edge.business.ckfh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.ckfh.entity.ERP_DeliveryOrder;

public interface DeliveryOrderDao {

	// 新增送货货物项
	public void saveDeliveryOrder(ERP_DeliveryOrder deliveryOrder);

	// 根据送货单Id加载送货项
	public List<ERP_DeliveryOrder> orderList(@Param("delivery_Id") Integer delivery_Id);

	// 根据成品Id计算该成品所有的入库数量
	public Integer totalRkNumber(@Param("product_Id") Integer product_Id);

	// 根据成品Id计算该成品所有的库存剩余量
	public Integer totalKcNumber(@Param("product_Id") Integer product_Id);

	// 根据材料Id计算该成品所有的库存剩余量
	public Integer totalKcNumberCl(@Param("product_Id") Integer product_Id);
}
