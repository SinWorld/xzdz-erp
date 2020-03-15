package com.edge.xshtsk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.xshtsk.dao.XshtskDao;
import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.entity.ERP_Xshtsk_QueryVo;
import com.edge.xshtsk.service.inter.XshtskService;

/**
 * 销售合同收款业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class XshtskServiceImpl implements XshtskService {

	@Resource
	private XshtskDao xshtskDao;

	// 分页展现销售合同收款
	public List<ERP_Xshtsk> xshtskList(ERP_Xshtsk_QueryVo vo) {
		return xshtskDao.xshtskList(vo);
	}

	// 查询销售合同收款总记录
	public Integer xshtskCount(ERP_Xshtsk_QueryVo vo) {
		return xshtskDao.xshtskCount(vo);
	}

	// 新增销售合同收款
	public void saveXshtsk(ERP_Xshtsk xshtsk) {
		xshtskDao.saveXshtsk(xshtsk);
	}

	// 获取刚新增的销售合同收款主键
	public Integer maxXshtdm() {
		return xshtskDao.maxXshtdm();
	}

	// 根据Id获得销售合同收款对象
	public ERP_Xshtsk queryXshtskById(Integer xshtskdm) {
		return xshtskDao.queryXshtskById(xshtskdm);
	}

	// 编辑销售合同收款
	public void editXshtsk(ERP_Xshtsk xshtsk) {
		xshtskDao.editXshtsk(xshtsk);
	}

	// 计算某一个销售合同下的所有实际收款金额
	public Double querySumSjskje(Integer xsht) {
		return xshtskDao.querySumSjskje(xsht);
	}

	// 查询当前销售合同下所有的累计开票金额
	public Double querySumLjkpje(Integer xsht) {
		return xshtskDao.querySumLjkpje(xsht);
	}

}
