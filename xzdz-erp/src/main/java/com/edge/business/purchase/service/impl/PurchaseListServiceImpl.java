package com.edge.business.purchase.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

	// 根据采购合同Id获得采购清单集合
	public List<ERP_Purchase_List> queryPurchaseListByCght(Integer pur_Order_Id) {
		return purchaseListDao.queryPurchaseListByCght(pur_Order_Id);
	}

	// 根据Id删除采购清单
	public void deletePurchaseListById(Integer pur_Id) {
		purchaseListDao.deletePurchaseListById(pur_Id);
	}

	public void editPurchaseList(ERP_Purchase_List purchaseList) {
		purchaseListDao.editPurchaseList(purchaseList);
	}

}
