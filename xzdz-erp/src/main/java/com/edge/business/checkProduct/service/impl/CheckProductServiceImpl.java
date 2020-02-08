package com.edge.business.checkProduct.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.business.checkProduct.dao.CheckProductDao;
import com.edge.business.checkProduct.service.inter.CheckProductService;
import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

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

	// 得到处于闲置状态的成品信息集合
	public ERP_Products queryXzProduct(String specification_type) {
		return checkProductDao.queryXzProduct(specification_type);
	}

	// 得到某一成品的库存集合
	public List<ERP_Product_Stock> queryStockByProduct(Integer product) {
		return checkProductDao.queryStockByProduct(product);
	}

	// 取得闲置成品的入库记录对象
	public ERP_stocks_Record xzcpStockId(Integer product) {
		return checkProductDao.xzcpStockId(product);
	}

}
