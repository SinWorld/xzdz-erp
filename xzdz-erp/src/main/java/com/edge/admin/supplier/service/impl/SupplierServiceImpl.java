package com.edge.admin.supplier.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.supplier.dao.SupplierDao;
import com.edge.admin.supplier.entity.ERP_Supplier;
import com.edge.admin.supplier.entity.Supplier_QueryVo;
import com.edge.admin.supplier.service.inter.SupplierService;

/**
 * 供应商业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class SupplierServiceImpl implements SupplierService {

	@Resource
	private SupplierDao supplierDao;

	// 分页加载供应商
	public List<ERP_Supplier> supplierList(Supplier_QueryVo vo) {
		return supplierDao.supplierList(vo);
	}

	// 加载供应商数量
	public Integer supplierCount(Supplier_QueryVo vo) {
		return supplierDao.supplierCount(vo);
	}

	// 新增供应商
	public void saveSupplier(ERP_Supplier supplier) {
		supplierDao.saveSupplier(supplier);
	}

	// 根据Id获得供应商对象
	public ERP_Supplier querySupplierById(Integer supplier_Id) {
		return supplierDao.querySupplierById(supplier_Id);
	}

	// 编辑供应商
	public void editSupplier(ERP_Supplier supplier) {
		supplierDao.editSupplier(supplier);
	}

	// 删除供应商
	public void deleteSupplier(Integer supplier_Id) {
		supplierDao.deleteSupplier(supplier_Id);
	}

	// 获得供应商新增后的主键
	public Integer maxSupplierId() {
		return supplierDao.maxSupplierId();
	}

}
