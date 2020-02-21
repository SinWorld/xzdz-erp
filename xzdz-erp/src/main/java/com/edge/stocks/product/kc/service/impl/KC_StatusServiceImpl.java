package com.edge.stocks.product.kc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.product.kc.dao.KC_StatusDao;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.kc.service.inter.KC_StatusService;

/**
 * 库存状态业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class KC_StatusServiceImpl implements KC_StatusService {

	@Resource
	private KC_StatusDao statusDao;

	// 新增库存状态
	public void saveStockStatus(ERP_Stock_Status status) {
		statusDao.saveStockStatus(status);
	}

	// 编辑库存
	public void editStockStatus(ERP_Stock_Status status) {
		statusDao.editStockStatus(status);
	}

	// 根据成品主键查询该成品的库存状态
	public ERP_Stock_Status queryStastusByCpId(Integer product_Id) {
		return statusDao.queryStastusByCpId(product_Id);
	}

	// 根据材料Id查询库存状态
	public ERP_Stock_Status queryStastusByClId(Integer product_Id) {
		return statusDao.queryStastusByClId(product_Id);
	}

}
