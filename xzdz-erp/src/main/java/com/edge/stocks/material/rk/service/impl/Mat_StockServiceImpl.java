package com.edge.stocks.material.rk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.material.rk.dao.Mat_StockDao;
import com.edge.stocks.material.rk.entity.ERP_MatStock_QueryVo;
import com.edge.stocks.material.rk.entity.ERP_Material_Stock;
import com.edge.stocks.material.rk.service.inter.Mat_StockService;

/**
 * 原材料库存业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Mat_StockServiceImpl implements Mat_StockService {
	@Resource
	private Mat_StockDao stockDao;

	// 分页展现材料库存
	public List<ERP_Material_Stock> mat_StockList(ERP_MatStock_QueryVo vo) {
		return stockDao.mat_StockList(vo);
	}

	// 材料库存数量
	public Integer mat_StockCount(ERP_MatStock_QueryVo vo) {
		return stockDao.mat_StockCount(vo);
	}

	// 新增库存
	public void saveMatStock(ERP_Material_Stock stock) {
		stockDao.saveMatStock(stock);
	}

	// 不重复库存名检测
	public ERP_Material_Stock checkKw(String stock) {
		return stockDao.checkKw(stock);
	}

	// 查询所有的材料库存
	public List<ERP_Material_Stock> queryAllStock() {
		return stockDao.queryAllStock();
	}

	// 根据id获得库存对象
	public ERP_Material_Stock queryMatStockById(Integer material_Id) {
		return stockDao.queryMatStockById(material_Id);
	}

	// 编辑库存
	public void editMatStock(ERP_Material_Stock stock) {
		stockDao.editMatStock(stock);
	}

	// 根据Id删除库存
	public void deleteMatStock(Integer material_Id) {
		stockDao.deleteMatStock(material_Id);
	}

}
