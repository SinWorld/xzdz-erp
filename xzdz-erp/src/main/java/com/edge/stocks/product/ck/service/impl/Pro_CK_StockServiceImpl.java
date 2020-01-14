package com.edge.stocks.product.ck.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.ck.dao.Pro_CK_StockDao;
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockService;
import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;

/**
 * 成品出库业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Pro_CK_StockServiceImpl implements Pro_CK_StockService {
	@Resource
	private Pro_CK_StockDao stockDao;

	// 分页展现成品库存
	public List<ERP_Product_Stock> pro_CK_StockList(ERP_ProStock_QueryVo vo) {
		return stockDao.pro_CK_StockList(vo);
	}

	// 成品库存数量
	public Integer pro_CK_StockCount(ERP_ProStock_QueryVo vo) {
		return stockDao.pro_CK_StockCount(vo);
	}

	// 加载所有未出库的库存
	public List<ERP_Product_Stock> allWckProductStock() {
		return stockDao.allWckProductStock();
	}

	// 加载某一成品已出库的数量
	public Integer yckCount(Integer stock_Id) {
		return stockDao.yckCount(stock_Id);
	}

	// 加载改成品的总出库数量
	public Integer totalYckCount(Integer product) {
		return stockDao.totalYckCount(product);
	}

}
