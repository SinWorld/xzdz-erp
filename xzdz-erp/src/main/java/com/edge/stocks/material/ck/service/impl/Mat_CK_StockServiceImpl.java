package com.edge.stocks.material.ck.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.material.ck.dao.Mat_CK_StockDao;
import com.edge.stocks.material.ck.service.inter.Mat_CK_StockService;
import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;

/**
 * 材料出库业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Mat_CK_StockServiceImpl implements Mat_CK_StockService {

	@Resource
	private Mat_CK_StockDao stockDao;

	// 分页展现材料库存
	public List<ERP_Material_Stock> mat_CK_StockList(ERP_MatStock_QueryVo vo) {
		return stockDao.mat_CK_StockList(vo);
	}

	// 材料库存数量
	public Integer matCKStockCount(ERP_MatStock_QueryVo vo) {
		return stockDao.matCKStockCount(vo);
	}

	// 加载所有未出库的库存
	public List<ERP_Material_Stock> allWckMaterialStock() {
		return stockDao.allWckMaterialStock();
	}

	// 加载某一库存下材料已出库的数量
	public Integer yckCount(Integer stock) {
		return stockDao.yckCount(stock);
	}

	// 加载该材料的总出库数量
	public Integer totalYckCount(Integer material) {
		return stockDao.totalYckCount(material);
	}

}
