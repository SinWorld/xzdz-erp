package com.edge.admin.supplier.service.inter;

import java.util.List;

import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.entity.Supplier_QueryVo;

public interface SupplierService {

	// 分页加载供应商
	public List<ERP_Supplier> supplierList(Supplier_QueryVo vo);

	// 加载供应商数量
	public Integer supplierCount(Supplier_QueryVo vo);

	// 新增供应商
	public void saveSupplier(ERP_Supplier supplier);

	// 根据Id获得供应商对象
	public ERP_Supplier querySupplierById(Integer supplier_Id);

	// 编辑供应商
	public void editSupplier(ERP_Supplier supplier);

	// 删除供应商
	public void deleteSupplier(Integer supplier_Id);
}
