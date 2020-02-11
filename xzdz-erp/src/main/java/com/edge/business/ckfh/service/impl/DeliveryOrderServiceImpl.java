package com.edge.business.ckfh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.ckfh.dao.DeliveryOrderDao;
import com.edge.business.ckfh.entity.ERP_DeliveryOrder;
import com.edge.business.ckfh.service.inter.DeliveryOrderService;

/**
 * 送货项业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

	@Resource
	private DeliveryOrderDao deliveryOrderDao;

	// 新增送货项
	public void saveDeliveryOrder(ERP_DeliveryOrder deliveryOrder) {
		deliveryOrderDao.saveDeliveryOrder(deliveryOrder);
	}

	// 根据送货单Id加载送货项
	public List<ERP_DeliveryOrder> orderList(Integer delivery_Id) {
		return deliveryOrderDao.orderList(delivery_Id);
	}

}
