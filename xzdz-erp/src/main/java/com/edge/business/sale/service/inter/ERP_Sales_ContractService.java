package com.edge.business.sale.service.inter;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.customer.entity.ERP_Customer;
import com.edge.business.sale.entity.ERP_Sales_Contract;
import com.edge.business.sale.entity.ERP_Sales_Contract_QueryVo;

public interface ERP_Sales_ContractService {
	// 加载销售合同列表
	public List<ERP_Sales_Contract> salesContractList(ERP_Sales_Contract_QueryVo vo);

	// 加载销售合同列表数量
	public Integer salesContractCount(ERP_Sales_Contract_QueryVo vo);

	// 加载需求方
	public List<ERP_Customer> customerList();

	// 加载所有的供货方
	public List<ERP_Our_Unit> unitList();

	// 合同编号生成
	public String htbh(String contract_Code);

	// 新增合同
	public void saveSalesContract(ERP_Sales_Contract contract);

	// 编辑合同
	public void editSalesContract(ERP_Sales_Contract contract);

	// 查询新增后的销售合同主键
	public Integer maxSalesContract();

	// 根据Id获得销售合同
	public ERP_Sales_Contract queryContractById(Integer sales_Contract_Id);

	// 根据Id删除销售合同
	public void deleteConractById(Integer sales_Contract_Id);

	// ajax加载所有的销售合同
	public JSONArray allXSHT();
}
