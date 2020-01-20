package com.edge.business.sale.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.business.sale.dao.ERP_Sales_ContractDao;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.entity.ERP_Sales_Contract_QueryVo;
import com.edge.business.sale.service.inter.ERP_Sales_ContractService;

/**
 * 销售合同业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ERP_Sales_ContractServiceImpl implements ERP_Sales_ContractService {
	@Resource
	private ERP_Sales_ContractDao contractDao;

	// 加载销售合同列表
	public List<ERP_Sales_Contract> salesContractList(ERP_Sales_Contract_QueryVo vo) {
		return contractDao.salesContractList(vo);
	}

	// 加载销售合同列表数量
	public Integer salesContractCount(ERP_Sales_Contract_QueryVo vo) {
		return contractDao.salesContractCount(vo);
	}

	// 加载需求方
	public List<ERP_Customer> customerList() {
		return contractDao.customerList();
	}

	// 加载供货方
	public List<ERP_Our_Unit> unitList() {
		return contractDao.unitList();
	}

	// 合同编号
	public String htbh(String contract_Code) {
		return contractDao.htbh(contract_Code);
	}

	// 新增合同
	public void saveSalesContract(ERP_Sales_Contract contract) {
		contractDao.saveSalesContract(contract);
	}

	// 查询新增后的销售合同主键
	public Integer maxSalesContract() {
		return contractDao.maxSalesContract();
	}

	// 根据Id获得销售合同
	public ERP_Sales_Contract queryContractById(Integer sales_Contract_Id) {
		return contractDao.queryContractById(sales_Contract_Id);
	}
}
