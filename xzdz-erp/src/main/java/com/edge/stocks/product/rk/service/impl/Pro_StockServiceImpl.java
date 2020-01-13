package com.edge.stocks.product.rk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.product.entity.ERP_Products;
import com.edge.stocks.product.rk.dao.Pro_StockDao;
import com.edge.stocks.product.rk.entity.ERP_ProStock_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_Product_Stock;
import com.edge.stocks.product.rk.service.inter.Pro_StockService;

/**
 * 成品库存业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Pro_StockServiceImpl implements Pro_StockService {

	@Resource
	private Pro_StockDao stockDao;

	// 分页展现成品库存
	public List<ERP_Product_Stock> pro_StockList(ERP_ProStock_QueryVo vo) {
		return stockDao.pro_StockList(vo);
	}

	// 成品库存数量
	public Integer pro_StockCount(ERP_ProStock_QueryVo vo) {
		return stockDao.pro_StockCount(vo);
	}

	// 加载所有未入库的成品
	public List<ERP_Products> allWrkProduct() {
		return stockDao.allWrkProduct();
	}

	// 新增库存
	public void saveProStock(ERP_Product_Stock stock) {
		stockDao.saveProStock(stock);
	}

	// 查询刚入库的库存主键
	public Integer queryMaxStock_Id() {
		return stockDao.queryMaxStock_Id();
	}

	// 剩余成品入库
	public void syrkProduct(ERP_Product_Stock stock) {
		stockDao.syrkProduct(stock);
	}

	// 根据Id获得入库成品对象
	public ERP_Product_Stock queryPro_StockById(Integer stock_Id) {
		return stockDao.queryPro_StockById(stock_Id);
	}

}
