package com.edge.business.sale.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.business.sale.entity.ERP_Sales_Contract_Order;

public interface ERP_Sales_Contract_OrderDao {
	// 新增货物清单
	public void saveContract_Order(ERP_Sales_Contract_Order order);

	// 加载某一销售合同下所有的货物清单
	public List<ERP_Sales_Contract_Order> orderList(@Param("sales_Contract") Integer sales_Contract);

	// 删除某一销售合同下所有的货物清单
	public void deleteOrderByContract(@Param("sales_Contract") Integer sales_Contract);

	// 根据Id删除销售合同货物清单
	public void deleteOrderById(@Param("sales_Contract_Id") Integer sales_Contract_Id);

	// 编辑货物清单
	public void editContract_Order(ERP_Sales_Contract_Order order);
}
