package com.edge.checkDelivery.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.checkDelivery.dao.CheckDeliveryDao;
import com.edge.checkDelivery.entity.CheckDelivery;
import com.edge.checkDelivery.entity.CheckDelivery_QueryVo;
import com.edge.checkDelivery.service.inter.CheckDeliveryService;

/**
 * 送货单核对业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class CheckDeliveryServiceImpl implements CheckDeliveryService {

	@Resource
	private CheckDeliveryDao checkDeliveryDao;

	// 分页显示送货单核对列表
	public List<CheckDelivery> checkDeliveryList(CheckDelivery_QueryVo vo) {
		return checkDeliveryDao.checkDeliveryList(vo);
	}

	// 显示送货单核对列表数量
	public Integer checkDeliveryCount(CheckDelivery_QueryVo vo) {
		return checkDeliveryDao.checkDeliveryCount(vo);
	}

	// 新增送货单核对
	public void saveCheckDelivery(CheckDelivery checkDelivery) {
		checkDeliveryDao.saveCheckDelivery(checkDelivery);
	}

	// 获取新增后的送货单核对主键
	public Integer maxCheckDeliveryId() {
		return checkDeliveryDao.maxCheckDeliveryId();
	}

	// 根据Id获得送货单核对对象
	public CheckDelivery queryCheckDeliveryById(Integer row_Id) {
		return checkDeliveryDao.queryCheckDeliveryById(row_Id);
	}

	// 编辑送货单核对
	public void editCheckDelivery(CheckDelivery checkDelivery) {
		checkDeliveryDao.editCheckDelivery(checkDelivery);
	}

}
