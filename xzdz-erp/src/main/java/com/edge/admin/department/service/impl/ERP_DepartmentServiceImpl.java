package com.edge.admin.department.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.department.dao.ERP_DepartmentDao;
import com.edge.admin.department.entity.Dep_QueryVo;
import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.department.service.inter.ERP_DepartmentService;

/**
 * 部门业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ERP_DepartmentServiceImpl implements ERP_DepartmentService {
	@Resource
	private ERP_DepartmentDao erp_DepartmentDao;

	// 分页显示部门列表
	public List<ERP_Department> departmentList(Dep_QueryVo vo) {
		return erp_DepartmentDao.departmentList(vo);
	}

	// 显示部门列表总数量
	public Integer queryDepCount(Dep_QueryVo vo) {
		return erp_DepartmentDao.queryDepCount(vo);
	}

	// 查询顶级部门
	public List<ERP_Department> topDepList() {
		return erp_DepartmentDao.topDepList();
	}

	// 查询部门下的子部门集合
	public List<ERP_Department> childrenDeps(Integer dep_parentId) {
		return erp_DepartmentDao.childrenDeps(dep_parentId);
	}

	// 新增部门
	public void saveDepartment(ERP_Department department) {
		erp_DepartmentDao.saveDepartment(department);
	}

	// 根据id获得部门对象
	public ERP_Department queryDepById(Integer dep_Id) {
		return erp_DepartmentDao.queryDepById(dep_Id);
	}

	// 编辑部门
	public void editDepartment(ERP_Department department) {
		erp_DepartmentDao.editDepartment(department);
	}

	// 删除部门(逻辑删除)
	public void deleteDepartment(ERP_Department department) {
		erp_DepartmentDao.deleteDepartment(department);
	}

}
