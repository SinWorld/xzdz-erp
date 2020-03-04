package com.edge.business.productStockCk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.productStockCk.dao.ProductStockCkDao;
import com.edge.business.productStockCk.service.inter.ProductStockCkService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

/**
 * 成品出库业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ProductStockCkServiceImpl implements ProductStockCkService {

	@Resource
	private ProductStockCkDao productStockCkDao;

	// 跟据成品查询所有入库的库位
	public List<ERP_Product_Stock> qeuryStockByProductId(Integer product_Id) {
		return productStockCkDao.qeuryStockByProductId(product_Id);
	}

	// 根据成品Id获得库存集合
	public List<ERP_Stock> queryStockListByCpId(Integer product_Id) {
		return productStockCkDao.queryStockListByCpId(product_Id);
	}

	// 根据成品Id和库位Id获得入库总数据
	public Integer queryStockRecordRkCpIdAndKwId(Integer product_Id, Integer stock) {
		return productStockCkDao.queryStockRecordRkCpIdAndKwId(product_Id, stock);
	}

}
