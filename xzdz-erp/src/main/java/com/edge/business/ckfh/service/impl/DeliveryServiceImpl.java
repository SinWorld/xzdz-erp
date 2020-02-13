package com.edge.business.ckfh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.ckfh.dao.DeliveryDao;
import com.edge.business.ckfh.entity.ERP_Delivery;
import com.edge.business.ckfh.entity.ERP_Delivery_QueryVo;
import com.edge.business.ckfh.service.inter.DeliveryService;

/**
 * 送货业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Resource
	private DeliveryDao deliveryDao;

	// 新增收货单
	public void saveDelivery(ERP_Delivery delivery) {
		deliveryDao.saveDelivery(delivery);
	}

	// 订单编号生成
	public String htbh(String delivery_Code) {
		return deliveryDao.htbh(delivery_Code);
	}

	// 获得刚新增的送货单主键
	public Integer queryDeliveryId() {
		return deliveryDao.queryDeliveryId();
	}

	// 分页加载送货单
	public List<ERP_Delivery> deliveryList(ERP_Delivery_QueryVo vo) {
		return deliveryDao.deliveryList(vo);
	}

	// 送货单数量
	public Integer deliveryCount(ERP_Delivery_QueryVo vo) {
		return deliveryDao.deliveryCount(vo);
	}

	// 根据Id获得送货单对象
	public ERP_Delivery queryDeliveryById(Integer delivery_Id) {
		return deliveryDao.queryDeliveryById(delivery_Id);
	}

	// 根据销售合同Id获得送货单对象
	public ERP_Delivery queryDeliveryByXsht(Integer sales_Contract_Id) {
		return deliveryDao.queryDeliveryByXsht(sales_Contract_Id);
	}

}
