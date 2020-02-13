package com.edge.stocks.product.rk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

	// 新增库存
	public void saveProStock(ERP_Product_Stock stock) {
		stockDao.saveProStock(stock);
	}

	// 编辑库存
	public void editProStock(ERP_Product_Stock stock) {
		stockDao.editProStock(stock);
	}

	// 根据Id获得入库成品对象
	public ERP_Product_Stock queryPro_StockById(Integer stock_Id) {
		return stockDao.queryPro_StockById(stock_Id);
	}

	// 查询所有的库存
	public List<ERP_Product_Stock> queryAllStock() {
		return stockDao.queryAllStock();
	}

	// 库存名重复验证
	public ERP_Product_Stock queryStockByStock(String stock) {
		return stockDao.queryStockByStock(stock);
	}

	// 删除库存
	public void deleteProStock(Integer stock_Id) {
		stockDao.deleteProStock(stock_Id);
	}

}
