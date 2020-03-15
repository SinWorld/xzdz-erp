package com.edge.stocks.material.kc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.material.kc.dao.KC_MaterialStockDao;
import com.edge.stocks.material.kc.service.inter.KC_MaterialStockService;
import com.edge.stocks.product.kc.entity.ERP_Stock;
import com.edge.stocks.product.kc.entity.ERP_Stock_QueryVo;
import com.edge.stocks.product.kc.entity.ERP_WarnStock;

/**
 * 成品库存业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class KC_MaterialStockServiceImpl implements KC_MaterialStockService {

	@Resource
	private KC_MaterialStockDao stockDao;

	// 分页加载成品库存列表
	public List<ERP_Stock> stockList(ERP_Stock_QueryVo vo) {
		return stockDao.stockList(vo);
	}

	// 分页加载成品库存列表数量
	public Integer stockListCount(ERP_Stock_QueryVo vo) {
		return stockDao.stockListCount(vo);
	}

	// 查询库存小于100的库存数据集合(库存报警)
	public List<ERP_WarnStock> warnStockList() {
		return stockDao.warnStockList();
	}

	// 查询该材料总库存量
	public Integer totalKcNumber(String materielId) {
		return stockDao.totalKcNumber(materielId);
	}

}
