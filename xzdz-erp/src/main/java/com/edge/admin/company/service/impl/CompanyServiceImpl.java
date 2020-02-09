package com.edge.admin.company.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.admin.company.dao.CompanyDao;
import com.edge.admin.company.entity.Company_QueryVo;
import com.edge.admin.company.entity.ERP_Our_Unit;
import com.edge.admin.company.service.inter.CompanyService;

/**
 * 单位信息业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	@Resource
	private CompanyDao companyDao;

	// 加载单位信息列表
	public List<ERP_Our_Unit> unitList(Company_QueryVo vo) {
		return companyDao.unitList(vo);
	}

	// 单位列表数量
	public Integer unitCount(Company_QueryVo vo) {
		return companyDao.unitCount(vo);
	}

	// 新增单位
	public void saveUnit(ERP_Our_Unit unit) {
		companyDao.saveUnit(unit);
	}

	// 根据Id获得单位对象
	public ERP_Our_Unit queryUnitById(Integer unit_Id) {
		return companyDao.queryUnitById(unit_Id);
	}

	// 编辑单位
	public void editUnit(ERP_Our_Unit unit) {
		companyDao.editUnit(unit);
	}

	// 删除单位
	public void deleteUnit(Integer unit_Id) {
		companyDao.deleteUnit(unit_Id);
	}

	// ajax查询所有的单位信息
	public JSONArray allUnit() {
		return companyDao.allUnit();
	}

}
