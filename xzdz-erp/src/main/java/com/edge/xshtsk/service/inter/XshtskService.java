package com.edge.xshtsk.service.inter;

import java.util.List;

import com.edge.xshtsk.entity.ERP_Xshtsk;
import com.edge.xshtsk.entity.ERP_Xshtsk_QueryVo;

public interface XshtskService {
	// 分页展现销售合同收款
	public List<ERP_Xshtsk> xshtskList(ERP_Xshtsk_QueryVo vo);

	// 查询销售合同收款总记录
	public Integer xshtskCount(ERP_Xshtsk_QueryVo vo);

	// 新增销售合同收款
	public void saveXshtsk(ERP_Xshtsk xshtsk);

	// 获取刚新增的销售合同收款主键
	public Integer maxXshtdm();

	// 根据Id获得销售合同收款对象
	public ERP_Xshtsk queryXshtskById(Integer xshtskdm);

	// 编辑销售合同收款
	public void editXshtsk(ERP_Xshtsk xshtsk);

	// 计算某一个销售合同下的所有实际收款金额
	public Double querySumSjskje(Integer xsht);

	// 查询当前销售合同下所有的累计开票金额
	public Double querySumLjkpje(Integer xsht);
}
