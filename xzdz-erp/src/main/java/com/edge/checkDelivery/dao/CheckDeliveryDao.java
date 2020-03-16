package com.edge.checkDelivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.checkDelivery.entity.CheckDelivery;
import com.edge.checkDelivery.entity.CheckDelivery_QueryVo;

public interface CheckDeliveryDao {

	// 分页显示送货单核对列表
	public List<CheckDelivery> checkDeliveryList(CheckDelivery_QueryVo vo);

	// 显示送货单核对列表数量
	public Integer checkDeliveryCount(CheckDelivery_QueryVo vo);

	// 新增送货单核对
	public void saveCheckDelivery(CheckDelivery checkDelivery);

	// 获取新增后的送货单核对主键
	public Integer maxCheckDeliveryId();

	// 根据Id获得送货单核对对象
	public CheckDelivery queryCheckDeliveryById(@Param("row_Id") Integer row_Id);

	// 编辑送货单核对
	public void editCheckDelivery(CheckDelivery checkDelivery);

	
}
