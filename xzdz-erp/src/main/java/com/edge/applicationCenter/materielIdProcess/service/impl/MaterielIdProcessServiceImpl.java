package com.edge.applicationCenter.materielIdProcess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.applicationCenter.materielIdProcess.dao.MaterielIdProcessDao;
import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess;
import com.edge.applicationCenter.materielIdProcess.entity.MaterielIdProcess_QueryVo;
import com.edge.applicationCenter.materielIdProcess.service.inter.MaterielIdProcessService;

/**
 * 物料ID评审业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class MaterielIdProcessServiceImpl implements MaterielIdProcessService {

	@Resource
	private MaterielIdProcessDao materielIdProcessDao;

	// 分页查询物料Id
	public List<MaterielIdProcess> materielIdProcessList(MaterielIdProcess_QueryVo vo) {
		return materielIdProcessDao.materielIdProcessList(vo);
	}

	// 查询物料Id数量
	public Integer materielIdProcessCount(MaterielIdProcess_QueryVo vo) {
		return materielIdProcessDao.materielIdProcessCount(vo);
	}

	// 新增物料ID评审数据
	public void saveMaterielIdProcess(MaterielIdProcess materielIdProcess) {
		materielIdProcessDao.saveMaterielIdProcess(materielIdProcess);
	}

	// 根据Id获得物料ID评审对象
	public MaterielIdProcess queryMaterielIdProcessById(Integer row_Id) {
		return materielIdProcessDao.queryMaterielIdProcessById(row_Id);
	}

	// 获得刚新增的物料ID评审主键
	public Integer queryMaxRowId() {
		return materielIdProcessDao.queryMaxRowId();
	}

	// 物料ID不重复
	public MaterielIdProcess queryERP_MaterielIdByWLID(String materiel_Id, Integer materielNumber) {
		return materielIdProcessDao.queryERP_MaterielIdByWLID(materiel_Id, materielNumber);
	}

	// 编辑物料ID评审数据
	public void editMaterielIdProcess(MaterielIdProcess materielIdProcess) {
		materielIdProcessDao.editMaterielIdProcess(materielIdProcess);
	}

	// 点击物料ID类型展现对应的物料ID评审数据
	public List<MaterielIdProcess> queryWLIDProcessByWLIDLX(Integer id) {
		return materielIdProcessDao.queryWLIDProcessByWLIDLX(id);
	}

}
