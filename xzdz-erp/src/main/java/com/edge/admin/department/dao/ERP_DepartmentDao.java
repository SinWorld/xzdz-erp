package com.edge.admin.department.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.department.entity.Dep_QueryVo;
import com.edge.admin.department.entity.ERP_Department;

public interface ERP_DepartmentDao {
	// 分页显示部门列表
	public List<ERP_Department> departmentList(Dep_QueryVo vo);

	// 显示部门列表总数量
	public Integer queryDepCount(Dep_QueryVo vo);

	// 查询顶级部门
	public List<ERP_Department> topDepList();

	// 查询部门下的子部门集合
	public List<ERP_Department> childrenDeps(@Param("dep_parentId") Integer dep_parentId);
	
	//新增部门
	public void saveDepartment(ERP_Department department);
	
	//根据id获得部门对象
	public ERP_Department queryDepById(@Param("dep_Id") Integer dep_Id);
	
	//编辑部门
	public void editDepartment(ERP_Department department);
	
	//删除部门(逻辑删除)
	public void deleteDepartment(ERP_Department department);
}
