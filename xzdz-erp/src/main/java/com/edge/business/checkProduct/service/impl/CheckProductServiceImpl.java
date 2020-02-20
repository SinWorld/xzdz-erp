package com.edge.business.checkProduct.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.checkProduct.dao.CheckProductDao;
import com.edge.business.checkProduct.service.inter.CheckProductService;
import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_Status;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

/**
 * 成品核对业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class CheckProductServiceImpl implements CheckProductService {

	@Resource
	private CheckProductDao checkProductDao;

	// 根据物料id获得成品集合
	public List<ERP_Products> queryProductByMaterielId(String materielId) {
		return checkProductDao.queryProductByMaterielId(materielId);
	}

	// 根据成品集合获得库存状态对象集合
	public List<ERP_Stock_Status> statsusList(List<Integer> productIds) {
		return checkProductDao.statsusList(productIds);
	}

	// 得到某一成品的库存集合
	public List<ERP_Product_Stock> queryStockByProduct(Integer product) {
		return checkProductDao.queryStockByProduct(product);
	}

	// 根据库存id获得库存对象
	public ERP_Stock queryStockById(Integer row_Id) {
		return checkProductDao.queryStockById(row_Id);
	}
}
