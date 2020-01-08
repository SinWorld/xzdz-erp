package com.edge.admin.Index.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.Index.dao.IndexDao;
import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.admin.Index.service.inter.IndexService;

/**
 * 首页业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class IndexServiceImpl implements IndexService {

	@Resource
	private IndexDao indexDao;

	// 加载顶级菜单权限
	public List<ERP_FunctionPoint> topFunctionPointList() {
		return indexDao.topFunctionPointList();
	}

	// 加载当前功能点的所有子功能点
	public List<ERP_FunctionPoint> childrenFList(Integer fp_Id) {
		return indexDao.childrenFList(fp_Id);
	}

}
