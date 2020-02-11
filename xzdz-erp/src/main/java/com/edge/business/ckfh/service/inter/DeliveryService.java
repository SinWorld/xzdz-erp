package com.edge.business.ckfh.service.inter;

import java.util.List;

import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.entity.ERP_Delivery_QueryVo;

public interface DeliveryService {

	// 新增送货单
	public void saveDelivery(ERP_Delivery delivery);

	// 订单编号生成
	public String htbh(String delivery_Code);

	// 获得刚新增的送货单主键
	public Integer queryDeliveryId();

	// 分页加载送货单
	public List<ERP_Delivery> deliveryList(ERP_Delivery_QueryVo vo);

	// 送货单数量
	public Integer deliveryCount(ERP_Delivery_QueryVo vo);

	// 根据Id获得送货单对象
	public ERP_Delivery queryDeliveryById(Integer delivery_Id);
}
