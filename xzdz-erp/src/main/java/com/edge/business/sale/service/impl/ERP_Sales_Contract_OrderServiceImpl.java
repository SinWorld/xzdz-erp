package com.edge.business.sale.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.sale.dao.ERP_Sales_Contract_OrderDao;
import com.edge.business.sale.entity.ERP_Sales_Contract_Order;
import com.edge.business.sale.service.inter.ERP_Sales_Contract_OrderService;

/**
 * 货物清单业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ERP_Sales_Contract_OrderServiceImpl implements ERP_Sales_Contract_OrderService {

	@Resource
	private ERP_Sales_Contract_OrderDao orderDao;

	// 新增货物清单
	public void saveContract_Order(ERP_Sales_Contract_Order order) {
		orderDao.saveContract_Order(order);
	}

}
