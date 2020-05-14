package com.edge.applicationCenter.materielIdProcess.service.inter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess;
import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess_QueryVo;

public interface MaterielIdProcessService {

	// 分页查询物料Id
	public List<MaterielIdProcess> materielIdProcessList(MaterielIdProcess_QueryVo vo);

	// 查询物料Id数量
	public Integer materielIdProcessCount(MaterielIdProcess_QueryVo vo);

	// 新增物料ID评审数据
	public void saveMaterielIdProcess(MaterielIdProcess materielIdProcess);

	// 根据Id获得物料ID评审对象
	public MaterielIdProcess queryMaterielIdProcessById(Integer row_Id);

	// 获得刚新增的物料ID评审主键
	public Integer queryMaxRowId();

	// 物料Id不重复
	public MaterielIdProcess queryERP_MaterielIdByWLID(String materiel_Id, Integer materielNumber);

	// 编辑物料ID评审数据
	public void editMaterielIdProcess(MaterielIdProcess materielIdProcess);

	// 点击物料ID类型展现对应的物料ID评审数据
	public List<MaterielIdProcess> queryWLIDProcessByWLIDLX(Integer id);
}
