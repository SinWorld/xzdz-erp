package com.edge.business.sale.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.sale.entity.ERP_Sales_Contract_Order;

public interface ERP_Sales_Contract_OrderDao {
	// 新增货物清单
	public void saveContract_Order(ERP_Sales_Contract_Order order);

	// 加载某一销售合同下所有的货物清单
	public List<ERP_Sales_Contract_Order> orderList(@Param("sales_Contract") Integer sales_Contract);
}
