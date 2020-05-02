package com.edge.admin.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.customer.dao.CustomerDao;
import com.edge.admin.customer.entity.Customer_QueryVo;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.admin.customer.service.inter.CustomerService;

/**
 * 客户业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	@Resource
	private CustomerDao customerDao;

	// 加载客户联系人列表
	public List<ERP_Customer> customerList(Customer_QueryVo vo) {
		return customerDao.customerList(vo);
	}

	// 加载客户联系人列表数量
	public Integer customerCount(Customer_QueryVo vo) {
		return customerDao.customerCount(vo);
	}

	// 新增客户
	public void saveCustomer(ERP_Customer customer) {
		customerDao.saveCustomer(customer);
	}

	// 查询客户最大主键值
	public Integer Maxcustomer_Id() {
		return customerDao.Maxcustomer_Id();
	}

	// 根据主键获得客户对象
	public ERP_Customer queryCustomerById(Integer customer_Id) {
		return customerDao.queryCustomerById(customer_Id);
	}

	// 编辑客户信息
	public void editCustomer(ERP_Customer customer) {
		customerDao.editCustomer(customer);
	}

	// 删除客户信息
	public void deleteCustomer(Integer customer_Id) {
		customerDao.deleteCustomer(customer_Id);
	}

	// ajax获得客户对象
	public JSONArray allCustomer() {
		return customerDao.allCustomer();
	}

	// 根据ObjDm获得客户对象
	public ERP_Customer queryCustomerByObjDm(String objDm) {
		return customerDao.queryCustomerByObjDm(objDm);
	}
}
