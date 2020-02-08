package com.edge.stocks.product.ck.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.stocks.product.ck.dao.Pro_CK_StockRecordDao;
import com.edge.stocks.product.ck.service.inter.Pro_CK_StockRecordService;
import com.edge.stocks.product.rk.entity.ERP_StockRecord_QueryVo;
import com.edge.stocks.product.rk.entity.ERP_stocks_Record;

/**
 * 成品出库记录业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class Pro_CK_StockRecordServiceImpl implements Pro_CK_StockRecordService {

	@Resource
	private Pro_CK_StockRecordDao stockRecordDao;

	// 分页查询成品的出库记录
	public List<ERP_stocks_Record> ckRecords(ERP_StockRecord_QueryVo vo) {
		return stockRecordDao.ckRecords(vo);
	}

	// 查询成品的出库总数量
	public Integer ckRecordCount(ERP_StockRecord_QueryVo vo) {
		return stockRecordDao.ckRecordCount(vo);
	}

}
