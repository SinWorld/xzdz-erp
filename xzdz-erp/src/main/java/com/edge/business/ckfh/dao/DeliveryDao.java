package com.edge.business.ckfh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.entity.ERP_Delivery_QueryVo;

public interface DeliveryDao {
	// 新增送货单
	public void saveDelivery(ERP_Delivery delivery);

	// 订单编号生成
	public String htbh(@Param("delivery_Code") String delivery_Code);

	// 获得刚新增的送货单主键
	public Integer queryDeliveryId();

	// 分页加载送货单
	public List<ERP_Delivery> deliveryList(ERP_Delivery_QueryVo vo);

	// 送货单数量
	public Integer deliveryCount(ERP_Delivery_QueryVo vo);

	// 根据Id获得送货单对象
	public ERP_Delivery queryDeliveryById(@Param("delivery_Id") Integer delivery_Id);
}
