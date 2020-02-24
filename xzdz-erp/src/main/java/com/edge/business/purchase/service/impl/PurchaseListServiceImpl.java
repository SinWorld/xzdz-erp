package com.edge.business.purchase.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.edge.business.purchase.dao.PurchaseListDao;
import com.edge.business.purchase.entity.ERP_Purchase_List;
import com.edge.business.purchase.service.inter.PurchaseListService;

/**
 * 采购清单业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class PurchaseListServiceImpl implements PurchaseListService {

	@Resource
	private PurchaseListDao purchaseListDao;

	// 新增采购清单
	public void savePurchaseList(ERP_Purchase_List purchaseList) {
		purchaseListDao.savePurchaseList(purchaseList);
	}

}
