package com.edge.admin.company.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.company.entity.Company_QueryVo;
import com.edge.admin.company.entity.ERP_Our_Unit;

public interface CompanyDao {
	// 加载单位信息列表
	public List<ERP_Our_Unit> unitList(Company_QueryVo vo);

	// 单位列表数量
	public Integer unitCount(Company_QueryVo vo);
	
	//新增单位列表
	public void saveUnit(ERP_Our_Unit unit);
	
	//根据Id获得单位对象
	public ERP_Our_Unit queryUnitById(@Param("unit_Id")Integer unit_Id);
	
	//编辑单位
	public void editUnit(ERP_Our_Unit unit);
	
	//删除单位
	public void deleteUnit(@Param("unit_Id")Integer unit_Id);
}
