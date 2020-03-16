package com.edge.cghtfk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.cghtfk.dao.CghtfkDao;
import com.edge.cghtfk.entity.ERP_Cghtfk;
import com.edge.cghtfk.entity.ERP_Cghtfk_QueryVo;
import com.edge.cghtfk.service.inter.CghtfkService;

/**
 * 采购合同付款业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class CghtfkServiceImpl implements CghtfkService {

	@Resource
	private CghtfkDao cghtfkDao;

	// 分页加载采购合同付款
	public List<ERP_Cghtfk> cghtfkList(ERP_Cghtfk_QueryVo vo) {
		return cghtfkDao.cghtfkList(vo);
	}

	// 加载采购合同付款总数量
	public Integer cghtfkListCount(ERP_Cghtfk_QueryVo vo) {
		return cghtfkDao.cghtfkListCount(vo);
	}

	// 计算某一个采购合同下累计付款金额
	public Double querySumLjfkje(Integer cght) {
		return cghtfkDao.querySumLjfkje(cght);
	}

	// 新增采购合同付款
	public void saveCghtfk(ERP_Cghtfk cghtfk) {
		cghtfkDao.saveCghtfk(cghtfk);
	}

	// 获取新增后的主键
	public Integer queryMaxCghtfk_Id() {
		return cghtfkDao.queryMaxCghtfk_Id();
	}

	// 根据Id获得采购合同付款对象
	public ERP_Cghtfk queryCghtfkById(Integer cghtfk_Id) {
		return cghtfkDao.queryCghtfkById(cghtfk_Id);
	}

	// 编辑采购合同收款
	public void editCghtsk(ERP_Cghtfk cghtfk) {
		cghtfkDao.editCghtsk(cghtfk);
	}

	// 加载所有的采购合同
	public JSONArray allCght() {
		return cghtfkDao.allCght();
	}

}
