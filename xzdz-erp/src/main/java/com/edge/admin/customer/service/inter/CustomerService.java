package com.edge.admin.customer.service.inter;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.customer.entity.Customer_QueryVo;
import com.edge.admin.customer.entity.ERP_Customer;

public interface CustomerService {
	// 加载客户联系人列表
	public List<ERP_Customer> customerList(Customer_QueryVo vo);

	// 加载客户联系人列表数量
	public Integer customerCount(Customer_QueryVo vo);

	// 新增客户
	public void saveCustomer(ERP_Customer customer);

	// 查询客户最大主键值
	public Integer Maxcustomer_Id();

	// 根据主键获得客户对象
	public ERP_Customer queryCustomerById(Integer customer_Id);

	// 编辑客户信息
	public void editCustomer(ERP_Customer customer);

	// 删除客户(物理删除)
	public void deleteCustomer(Integer customer_Id);

	// ajax获得客户对象
	public JSONArray allCustomer();
}
