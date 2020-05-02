package com.edge.admin.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.entity.Supplier_QueryVo;

public interface SupplierDao {

	// 分页加载供应商
	public List<ERP_Supplier> supplierList(Supplier_QueryVo vo);

	// 加载供应商数量
	public Integer supplierCount(Supplier_QueryVo vo);

	// 新增供应商
	public void saveSupplier(ERP_Supplier supplier);

	// 根据Id获得供应商对象
	public ERP_Supplier querySupplierById(@Param("supplier_Id") Integer supplier_Id);

	// 编辑供应商
	public void editSupplier(ERP_Supplier supplier);

	// 删除供应商
	public void deleteSupplier(@Param("supplier_Id") Integer supplier_Id);

	// 获得供应商新增后的主键
	public Integer maxSupplierId();

}
