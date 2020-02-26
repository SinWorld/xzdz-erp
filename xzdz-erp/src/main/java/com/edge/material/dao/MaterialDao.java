package com.edge.material.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.entity.ERP_RAW_Material_QueryVo;

public interface MaterialDao {
	// 加载材料信息列表页面
	public List<ERP_RAW_Material> materialList(ERP_RAW_Material_QueryVo vo);

	// 加载材料信息数量
	public Integer materialCount(ERP_RAW_Material_QueryVo vo);

	// 新增材料信息
	public void saveMaterial(ERP_RAW_Material material);

	// 根据Id获得材料对象
	public ERP_RAW_Material queryMaterialById(@Param("raw_Material_Id") Integer raw_Material_Id);

	// 编辑操作
	public void editMaterial(ERP_RAW_Material material);

	// 删除操作
	public void deleteMaterial(@Param("raw_Material_Id") Integer raw_Material_Id);

	// 加载所有未入库的原材料
	public List<ERP_RAW_Material> allWrkMaterial();
	
	//获取材料最大主键
	public Integer queryMaxMaterialId();
}
