package com.edge.index.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.Index.entity.ERP_FunctionPoint;
import com.edge.index.dao.QTIndexDao;
import com.edge.index.service.inter.QTIndexService;

/**
 * 前台首页业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class QTServiceImpl implements QTIndexService {

	@Resource
	private QTIndexDao qtIndexDao;

	// 用户登录到首页时根据用户主键查询当前用户的顶级权限
	public List<ERP_FunctionPoint> userPrivilegeList(Integer userId) {
		return qtIndexDao.userPrivilegeList(userId);
	}

	// 查询当前用户所有顶级权限下的二级子权限或三级权限
	public List<ERP_FunctionPoint> ejChildrenList(Integer userId, Integer fp_parentId) {
		return qtIndexDao.ejChildrenList(userId, fp_parentId);
	}

}
