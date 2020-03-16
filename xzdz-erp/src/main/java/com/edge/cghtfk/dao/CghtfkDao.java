package com.edge.cghtfk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;
import com.edge.cghtfk.entity.ERP_Cghtfk;
import com.edge.cghtfk.entity.ERP_Cghtfk_QueryVo;

public interface CghtfkDao {

	// 分页加载采购合同付款
	public List<ERP_Cghtfk> cghtfkList(ERP_Cghtfk_QueryVo vo);

	// 加载采购合同付款总数量
	public Integer cghtfkListCount(ERP_Cghtfk_QueryVo vo);

	// 计算某一个采购合同下累计付款金额
	public Double querySumLjfkje(@Param("cght") Integer cght);

	// 新增采购合同付款
	public void saveCghtfk(ERP_Cghtfk cghtfk);

	// 获取新增后的主键
	public Integer queryMaxCghtfk_Id();

	// 根据Id获得采购合同付款对象
	public ERP_Cghtfk queryCghtfkById(@Param("cghtfk_Id") Integer cghtfk_Id);

	// 编辑采购合同收款
	public void editCghtsk(ERP_Cghtfk cghtfk);

	// 加载所有的采购合同
	public JSONArray allCght();
}
