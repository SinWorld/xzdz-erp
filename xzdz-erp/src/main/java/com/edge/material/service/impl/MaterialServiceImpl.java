package com.edge.material.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.material.dao.MaterialDao;
import com.edge.material.entity.ERP_RAW_Material;
import com.edge.material.entity.ERP_RAW_Material_QueryVo;
import com.edge.material.service.inter.MaterialService;

/**
 * 原材料业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterialServiceImpl implements MaterialService {

	@Resource
	private MaterialDao materialDao;

	// 加载材料信息列表页面
	public List<ERP_RAW_Material> materialList(ERP_RAW_Material_QueryVo vo) {
		return materialDao.materialList(vo);
	}

	// 加载材料信息数量
	public Integer materialCount(ERP_RAW_Material_QueryVo vo) {
		return materialDao.materialCount(vo);
	}

	// 新增材料信息
	public void saveMaterial(ERP_RAW_Material material) {
		materialDao.saveMaterial(material);
	}

	// 根据Id获得材料对象
	public ERP_RAW_Material queryMaterialById(Integer raw_Material_Id) {
		return materialDao.queryMaterialById(raw_Material_Id);
	}

	// 编辑操作
	public void editMaterial(ERP_RAW_Material material) {
		materialDao.editMaterial(material);
	}

	// 删除操作
	public void deleteMaterial(Integer raw_Material_Id) {
		materialDao.deleteMaterial(raw_Material_Id);
	}

	// 加载所有未入库的成品
	public List<ERP_RAW_Material> allWrkMaterial() {
		return materialDao.allWrkMaterial();
	}

	// 获取材料最大主键
	public Integer queryMaxMaterialId() {
		return materialDao.queryMaxMaterialId();
	}

}
