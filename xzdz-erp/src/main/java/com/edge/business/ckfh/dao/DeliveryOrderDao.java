package com.edge.business.ckfh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.ckfh.entity.ERP_DeliveryOrder;

public interface DeliveryOrderDao {
	// 新增送货货物项
	public void saveDeliveryOrder(ERP_DeliveryOrder deliveryOrder);

	// 根据送货单Id加载送货项
	public List<ERP_DeliveryOrder> orderList(@Param("delivery_Id") Integer delivery_Id);
}
