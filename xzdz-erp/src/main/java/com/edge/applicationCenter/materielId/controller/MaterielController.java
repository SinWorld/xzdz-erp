package com.edge.applicationCenter.materielId.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.admin.materielId.entity.ERP_MaterielId;
import com.edge.admin.materielId.entity.ERP_MaterielId_QueryVo;
import com.edge.admin.materielId.entity.MaterielType;
import com.edge.admin.materielId.service.inter.MaterielIdService;
import com.edge.admin.materielId.service.inter.MaterielTypeService;
import com.edge.currency.dictionary.approval.entity.ERP_DM_Approval;
import com.edge.currency.dictionary.approval.service.inter.ApprovalService;
import com.google.gson.Gson;

/**
 * 物料Id控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "wlId")
public class MaterielController {

	@Resource
	private MaterielIdService materielIdService;

	@Resource
	private MaterielTypeService materielTypeService;

	@Resource
	private ApprovalService approvalService;

	// 跳转至物料Id列表页面
	@RequestMapping(value = "/initWlId.do")
	public String initWlId() {
		return "applicationCenter/materielId/materielIdList";
	}

	// 列表查询
	@RequestMapping(value = "/materielIdList.do")
	@ResponseBody
	public String materielIdList(@RequestParam Integer page, Integer limit, String wlId, String ggxh, String bzq,
			String ckj1, String ckj2, Integer wlidlx, Integer wlidh, String remarks, Integer materielTypeId) {
		// new出查询对象
		ERP_MaterielId_QueryVo vo = new ERP_MaterielId_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// new 出List集合用于存储点击物料ID类型后对应的物料ID对象
		List<ERP_MaterielId> clickWLIDS = new ArrayList<ERP_MaterielId>();
		if (materielTypeId != null) {// 点击物料ID类型展现对应的物料ID数据
			List<Integer> wlidlxIds = queryWLIDLXId(materielTypeId);
			List<ERP_MaterielId> wlidParent = materielTypeService.queryWLIDByWLIDLX(materielTypeId);
			if (wlidParent.size() > 0) {
				for (ERP_MaterielId w : wlidParent) {
					clickWLIDS.add(w);
				}
			}
			// 遍历 wlidlxIds集合
			for (Integer dm : wlidlxIds) {
				// 根据代码查询对应的文件柜对象
				List<ERP_MaterielId> wlIds = materielTypeService.queryWLIDByWLIDLX(dm);
				if (wlIds.size() > 0) {
					for (ERP_MaterielId w : wlIds) {
						clickWLIDS.add(w);
					}
				}
			}
			vo.setPage((page - 1) * limit + 1);
			vo.setRows(page * limit);
			vo.setWlId(wlId);
			vo.setGgxh(ggxh);
			vo.setBzq(bzq);
			vo.setMaterielType(wlidlx);
			vo.setMaterielNumber(wlidh);
			vo.setRemarks(remarks);
			if (ckj1 != null && ckj1 != "") {
				vo.setCkj1(Double.valueOf(ckj1.trim()));
			}
			if (ckj2 != null && ckj2 != "") {
				vo.setCkj2(Double.valueOf(ckj2.trim()));
			}
			for (ERP_MaterielId c : clickWLIDS) {
				MaterielType materielType = materielTypeService.queryMaterielTypeById(c.getMaterielType());
				MaterielType materielNumber = materielTypeService.queryMaterielTypeById(c.getMaterielNumber());
				ERP_DM_Approval approval = approvalService.queryApprovalById(c.getApprovaldm());
				c.setMaterielTypeName(materielType.getTitle());
				c.setMaterielNumberName(materielNumber.getTitle());
				if (approval != null) {
					c.setApprovalmc(approval.getApprovalmc());
				}
			}
			map.put("code", 0);
			map.put("msg", "");
			map.put("count", clickWLIDS.size());
			map.put("data", clickWLIDS);
			String json = gson.toJson(map);
			return json.toString();
		} else {
			vo.setPage((page - 1) * limit + 1);
			vo.setRows(page * limit);
			vo.setWlId(wlId);
			vo.setGgxh(ggxh);
			vo.setBzq(bzq);
			vo.setMaterielType(wlidlx);
			vo.setMaterielNumber(wlidh);
			vo.setRemarks(remarks);
			if (ckj1 != null && ckj1 != "") {
				vo.setCkj1(Double.valueOf(ckj1.trim()));
			}
			if (ckj2 != null && ckj2 != "") {
				vo.setCkj2(Double.valueOf(ckj2.trim()));
			}
			List<ERP_MaterielId> materielIdList = materielIdService.materielIdList(vo);
			for (ERP_MaterielId m : materielIdList) {
				MaterielType materielType = materielTypeService.queryMaterielTypeById(m.getMaterielType());
				MaterielType materielNumber = materielTypeService.queryMaterielTypeById(m.getMaterielNumber());
				ERP_DM_Approval approval = approvalService.queryApprovalById(m.getApprovaldm());
				m.setMaterielTypeName(materielType.getTitle());
				m.setMaterielNumberName(materielNumber.getTitle());
				if (approval != null) {
					m.setApprovalmc(approval.getApprovalmc());
				}
			}
			map.put("code", 0);
			map.put("msg", "");
			map.put("count", materielIdService.materielIdCount(vo));
			map.put("data", materielIdList);
			String json = gson.toJson(map);
			return json.toString();
		}
	}

	// 点击物料ID类型查询该物料ID类型下所有的子节点主键
	private List<Integer> queryWLIDLXId(Integer materielTypeId) {
		// new 出List集合用于存储所有的文件夹代码
		List<Integer> wjjdms = new ArrayList<Integer>();
		// 根据文件夹代码查询子文件夹集合
		List<MaterielType> childrenMaterielTypeTree = materielTypeService.childrenMaterielTypeTree(materielTypeId);
		this.queryWJJTree(childrenMaterielTypeTree, wjjdms);
		return wjjdms;
	}

	// 递归查询该物料ID类型下所有的子节点
	private void queryWJJTree(List<MaterielType> childrens, List<Integer> wjjdms) {
		// 遍历该集合
		for (MaterielType c : childrens) {
			wjjdms.add(c.getId());
			List<MaterielType> childrenWJJ = materielTypeService.childrenMaterielTypeTree(c.getId());
			queryWJJTree(childrenWJJ, wjjdms);
		}

	}
}
