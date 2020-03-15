package com.edge.admin.company.service.inter;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.company.entity.Company_QueryVo;
import com.edge.admin.company.entity.ERP_Our_Unit;

public interface CompanyService {
	// 加载单位信息列表
	public List<ERP_Our_Unit> unitList(Company_QueryVo vo);

	// 单位列表数量
	public Integer unitCount(Company_QueryVo vo);

	// 新增单位列表
	public void saveUnit(ERP_Our_Unit unit);

	// 根据Id获得单位对象
	public ERP_Our_Unit queryUnitById(Integer unit_Id);

	// 编辑单位
	public void editUnit(ERP_Our_Unit unit);

	// 删除单位
	public void deleteUnit(Integer unit_Id);

	// ajax查询所有的单位信息
	public JSONArray allUnit();

	// 查询单位列表
	public List<ERP_Our_Unit> ourUnitList();
}
