package com.edge.business.sale.service.impl;

import java.util.List;

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

	// 加载某一销售合同下所有的货物清单
	public List<ERP_Sales_Contract_Order> orderList(Integer sales_Contract) {
		return orderDao.orderList(sales_Contract);
	}

	// 删除某一销售合同下所有的货物清单
	public void deleteOrderByContract(Integer sales_Contract) {
		orderDao.deleteOrderByContract(sales_Contract);
	}

	// 根据Id删除销售合同货物清单
	public void deleteOrderById(Integer sales_Contract_Id) {
		orderDao.deleteOrderById(sales_Contract_Id);
	}

	// 编辑货物清单
	public void editContract_Order(ERP_Sales_Contract_Order order) {
		orderDao.editContract_Order(order);
	}

}
